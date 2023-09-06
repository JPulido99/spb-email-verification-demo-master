package com.dailycodework.sbemailverificationdemo.user;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dailycodework.sbemailverificationdemo.entities.Rol;
import com.dailycodework.sbemailverificationdemo.registration.RegistrationRequest;
import com.dailycodework.sbemailverificationdemo.registration.token.IVerificationTokenRepository;
import com.dailycodework.sbemailverificationdemo.registration.token.VerificationToken;
import com.dailycodework.sbemailverificationdemo.repositories.IRolRepository;
import com.dailycodework.sbemailverificationdemo.services.IRolService;

import lombok.RequiredArgsConstructor;

/**
 * @author Sampson Alfred
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	@Autowired
    private final IUserRepository userRepository;
    private final IVerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder; 

    @Autowired
    private IRolService rolService;

    @Autowired
	private IRolRepository rolRepository;
    
    @Override
    public List<User> list() {
        return userRepository.findAll();
    }


    @Override
    public User registerUser(RegistrationRequest request) {
       Optional<User> user = this.findByUsername(request.getUsername());
       var newUser = new User();
       newUser.setFirstName(request.getFirstName());
       newUser.setLastName(request.getLastName());
       newUser.setUsername(request.getUsername());
       newUser.setPassword(passwordEncoder.encode(request.getPassword()));

       // Asignar el rol deseado en base al valor de request.getRole()
       String requestedRole = request.getRole();
       if (requestedRole.equals("Administrador") ||
           requestedRole.equals("Interesado") ||
           requestedRole.equals("Docente") ||
           requestedRole.equals("Secretario Docente") ||
           requestedRole.equals("Secretario ")) {
           newUser.setRol(rolService.findByNombre(requestedRole));
       } else {
           throw new IllegalArgumentException("Rol inválido: " + requestedRole);
       }

       return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            if (!user.isEnabled()) {
                userRepository.delete(user);
                return "Token already expired. User deleted.";
            }
            return "Token already expired. User remains registered.";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
    
    @Override
    public void deleteUnverifiedUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isEnabled()) {
                userRepository.delete(user);
            }
        }
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        Calendar calendar = Calendar.getInstance();
        if (verificationToken != null && verificationToken.getExpirationTime().getTime() > calendar.getTime().getTime()) {
            return verificationToken;
        }
        var newToken = new VerificationToken();
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpirationTime(newToken.getTokenExpirationTime());
        return tokenRepository.save(newToken);
    }
    
    
    @Autowired
    private ILoginTokenRepository loginTokenRepository;

    @Override
    public LoginToken generateLoginToken(User user) {

        LoginToken loginToken = new LoginToken();
        loginToken.setToken(generateToken());
        loginToken.setExpirationTime(calculateExpirationTime());
        loginToken.setUser(user);
        return loginTokenRepository.save(loginToken);
    }
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
    
    private Date calculateExpirationTime() {
        // Implementa tu lógica para calcular la fecha de expiración aquí
        // Puedes utilizar la clase Calendar o LocalDateTime para manipular fechas
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 20); // Establece una expiración de 20 minutos desde el momento actual

        // Convierte java.util.Date a java.sql.Date
        Date utilDate = calendar.getTime();
        Date sqlDate = new Date(utilDate.getTime());
        return sqlDate;
    }
    
    @Override
    public boolean validateCredentials(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encodedPassword = user.getPassword();
            return passwordEncoder.matches(password, encodedPassword);
        }
        return false;
    }

    	@Override
    public User obtenerUsuarioPorId(Long id) {
        Optional<User> usuarioOptional = userRepository.findById(id);
        return usuarioOptional.orElseThrow();
    }
    

    @Override
    public User getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null); // Devuelve el usuario o null si no se encuentra
        // Otra opción: userOptional.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    @Override
    public void invalidateLoginToken(String token) {
    	
    	LoginToken loginToken = loginTokenRepository.findByToken(token);
        
        // Si el token existe, eliminarlo de la base de datos
        if (loginToken != null) {
            loginTokenRepository.delete(loginToken);
        }
    }

	@Override
	public User register(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUsuario(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUsuarioById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUsuarioByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findUsuarioByEscuela(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

//J-verificar docentes
	

    @Override
    public List<User> getUsersByRoleId(Long roleId) {
        return userRepository.findByRolId(roleId);
    }
	
    
    @Override
    public List<User> buscarDocentesPorNombre(String firstName, Long rolId) {
        Rol rol = rolRepository.findById(rolId).orElseThrow(() -> new IllegalArgumentException("ID de rol inválido: " + rolId));
        return userRepository.findByNombresContainingAndRol(firstName, rol);
    }


	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	
	
    

}

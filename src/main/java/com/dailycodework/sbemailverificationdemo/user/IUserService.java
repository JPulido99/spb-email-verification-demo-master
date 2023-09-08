package com.dailycodework.sbemailverificationdemo.user;



import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dailycodework.sbemailverificationdemo.registration.RegistrationRequest;
import com.dailycodework.sbemailverificationdemo.registration.token.VerificationToken;


@Service
public interface IUserService {
    List<User> list();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    void saveUserVerificationToken(User theUser, String verificationToken);

    String validateToken(String theToken);

    //Lo de usuario
    User obtenerUsuarioPorId(Long id);
	
    User register(User entity);
    User updateUsuario(User entity);
    User findUsuarioById(Long id);
	//Usuario findUsuarioByCodigo(String codigo);
    User findUsuarioByFirstName(String firstName);
	List<User> findUsuarioByEscuela(Long id);
	User updateUserPasswordByUsername(String username, String newPassword);
	
	//J-verificar docentes
	List<User> getUsersByRoleId(Long roleId);
	List<User> buscarDocentesPorNombre(String firstName, Long rolId);
    VerificationToken generateNewVerificationToken(String oldToken);
	void deleteUnverifiedUser(String username);
	
	LoginToken generateLoginToken(User user);
	boolean validateCredentials(String username, String password);
	User getUserByUsername(String username);
	void invalidateLoginToken(String token);
	//PARA RESTABLECER CONTRASEÑA	
//	void initiatePasswordReset(String username);
	boolean isLoginTokenNotExpired(String token);
	boolean validateLoginToken(String token);
	
	
    	
    
}

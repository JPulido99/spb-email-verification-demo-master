package com.dailycodework.sbemailverificationdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.sbemailverificationdemo.entities.Escuela;
import com.dailycodework.sbemailverificationdemo.entities.ModalidadIngreso;
import com.dailycodework.sbemailverificationdemo.entities.Rol;
import com.dailycodework.sbemailverificationdemo.services.IModalidadIngresoService;


@RestController
@RequestMapping("/api/modalidadIngreso")
public class ModalidadIngresoController {
	
	@Autowired
	private IModalidadIngresoService modalidadIngresoService;
	
	
	@GetMapping("/list")
	@CrossOrigin(origins = "https://victorious-rock-009afba10.3.azurestaticapps.net")
    public List<ModalidadIngreso> listarModalidad() {
        return modalidadIngresoService.list();
    }
	
	@GetMapping("/{id}")
    @CrossOrigin(origins = "https://victorious-rock-009afba10.3.azurestaticapps.net")
    public ModalidadIngreso obtenerModalidadPorID(@PathVariable("id") Long id) {
        return modalidadIngresoService.modalidadById(id);
    }
	
	@PostMapping
    @CrossOrigin(origins = "https://victorious-rock-009afba10.3.azurestaticapps.net")
    public ResponseEntity<ModalidadIngreso> register(@RequestBody ModalidadIngreso modalidadIngreso) {
        ModalidadIngreso nuevaModalidadIngreso = modalidadIngresoService.register(modalidadIngreso);
        return new ResponseEntity<>(nuevaModalidadIngreso, HttpStatus.CREATED);
    }

}

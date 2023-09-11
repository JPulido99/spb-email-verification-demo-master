package com.dailycodework.sbemailverificationdemo.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.sbemailverificationdemo.entities.Tramite;
import com.dailycodework.sbemailverificationdemo.services.ITramiteService;



@RestController
@RequestMapping("/api/tramite")
public class TramiteController {

	@Autowired
	private ITramiteService tramiteService;
	
	@GetMapping
	@CrossOrigin(origins = "https://zealous-sea-0c3294610.3.azurestaticapps.net")
    public List<Tramite> listarTramites() {
        return tramiteService.listTramite();
    }

    @PostMapping
    @CrossOrigin(origins = "https://zealous-sea-0c3294610.3.azurestaticapps.net")
    public Tramite crearTramite(@RequestBody Tramite tramite) {
        return tramiteService.registerTramite(tramite);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "https://zealous-sea-0c3294610.3.azurestaticapps.net")
    public Tramite obtenerTramite(@PathVariable("id") Long id) {
        return tramiteService.tramiteById(id);
    }
    
    @PutMapping("/{id}")
    @CrossOrigin(origins = "https://zealous-sea-0c3294610.3.azurestaticapps.net")
    public Tramite actualizarTramite(@PathVariable("id") Long id, @RequestBody Tramite tramite) {
        return tramiteService.updateTramite(id, tramite);
    }
    
    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "https://zealous-sea-0c3294610.3.azurestaticapps.net")
    public boolean eliminarTramite(@PathVariable("id") Long id) {
        return tramiteService.deleteTramite(id);
    }
}

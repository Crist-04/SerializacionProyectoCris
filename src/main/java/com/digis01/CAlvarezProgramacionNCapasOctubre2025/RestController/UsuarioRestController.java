package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetAll() {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.GetAll();
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.ex = ex;
            result.errorMessage = "Error interno";
            result.status = 500;

        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping
    public ResponseEntity Add(@RequestBody UsuarioJPA usuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Add(usuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al agregar al Usuario";
            result.status = 500;
            result.ex = ex;
        }

        return ResponseEntity.status(result.status).body(result);
    }
    
    @PutMapping
    public ResponseEntity Update(@RequestBody UsuarioJPA usuario) {
        Result result = new Result();
        try{
            result = usuarioJPADAOImplementation.Update(usuario);
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "No se actualizo el usuario";
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity Delete(@PathVariable int idUsuario){
        Result result = new Result();
        try{
            result = usuarioJPADAOImplementation.Delete(idUsuario);
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "Eror No se Elimino el usuario";
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @GetMapping("/{idUsuario}")
    public ResponseEntity GetById(@PathVariable int idUsuario){
        Result result = new Result();
        try{
            result = usuarioJPADAOImplementation.GetById(idUsuario);
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "Error, el usuario no se encontro";
            result.status = 500;
        }
        
        
        return ResponseEntity.status(result.status).body(result);
    }
 
}

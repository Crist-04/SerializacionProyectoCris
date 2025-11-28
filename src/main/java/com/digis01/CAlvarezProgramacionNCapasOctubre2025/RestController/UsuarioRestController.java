package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
            System.out.println("=== API REST - Recibiendo Usuario ===");
            System.out.println("Nombre: " + usuario.Nombre);
            System.out.println("UserName: " + usuario.UserName);
            System.out.println("Imagen recibida: " + (usuario.Imagen != null && !usuario.Imagen.isEmpty() ? "SÍ (" + usuario.Imagen.length() + " chars)" : "NO"));

            result = usuarioJPADAOImplementation.Add(usuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al agregar al Usuario: " + ex.getMessage();
            result.status = 500;
            result.ex = ex;
            ex.printStackTrace();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping
    public ResponseEntity Update(@RequestBody UsuarioJPA usuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Update(usuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se actualizo el usuario";
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity Delete(@PathVariable int idUsuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.Delete(idUsuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Eror No se Elimino el usuario";
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity GetById(@PathVariable int idUsuario) {
        Result result = new Result();
        try {
            result = usuarioJPADAOImplementation.GetById(idUsuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error, el usuario no se encontro";
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
    
    @PatchMapping("/{idUsuario}/imagen")
    public ResponseEntity UpdateImagen(@PathVariable int idUsuario, @RequestBody String imagenBase64){
        Result result = new Result();
        
        try{
            result = usuarioJPADAOImplementation.GetById(idUsuario);
            
            UsuarioJPA usuario = (UsuarioJPA) result.object;
            usuario.Imagen = imagenBase64;
            
            result = usuarioJPADAOImplementation.Update(usuario);
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "No se actualizo la imagen";
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

}

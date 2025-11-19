
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        Result result = new Result();
        try{
            result = usuarioJPADAOImplementation.GetAll();
            result.correct = true;
            result.status = 200;
            
        }catch(Exception ex){
            result.ex = ex;
            result.errorMessage = "Error interno";
            result.status = 500;
            
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
}

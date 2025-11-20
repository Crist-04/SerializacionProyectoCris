package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.DireccionJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController {

    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @PostMapping("/{idUsuario}")
    public ResponseEntity AddDireccion(@RequestBody DireccionJPA direccion, @PathVariable int idUsuario){
        Result result = new Result();
        result = direccionJPADAOImplementation.AddDireccion(direccion, idUsuario);
        
        return ResponseEntity.status(result.status).body(result);
    }

}

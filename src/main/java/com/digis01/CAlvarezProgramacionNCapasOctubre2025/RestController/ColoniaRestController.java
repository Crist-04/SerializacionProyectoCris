
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.ColoniaJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colonia")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    
    @GetMapping("/municipio/{idMunicipio}")
    public ResponseEntity GetByIdMunicipio(@PathVariable int idMunicipio){
        Result result = new Result();
        
        result = coloniaJPADAOImplementation.GetByIdMunicipio(idMunicipio);
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @GetMapping("/codigoPostal/{codigoPostal}")
    public ResponseEntity GetByCodigoPostal(@PathVariable String codigoPostal){
        Result result = new Result();
        result = coloniaJPADAOImplementation.GetByCodigoPostal(codigoPostal);
        
        return ResponseEntity.status(result.status).body(result);
    }
    
}

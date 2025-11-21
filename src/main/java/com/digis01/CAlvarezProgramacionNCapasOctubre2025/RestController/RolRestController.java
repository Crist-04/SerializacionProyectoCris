
package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.RolJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rol")
public class RolRestController {
    
    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
        Result result = new Result();
        result = rolJPADAOImplementation.GetAll();
        
        return ResponseEntity.status(result.status).body(result);
    }
    
}

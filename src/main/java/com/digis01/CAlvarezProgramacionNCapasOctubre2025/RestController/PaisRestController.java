package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.PaisJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
public class PaisRestController {

    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;

    @GetMapping()
    public ResponseEntity GetAll() {
        Result result = new Result();
       
            result = paisJPADAOImplementation.GetAll();

            result.correct = true;
            result.status = 200;

        return ResponseEntity.status(result.status).body(result);
    }

}

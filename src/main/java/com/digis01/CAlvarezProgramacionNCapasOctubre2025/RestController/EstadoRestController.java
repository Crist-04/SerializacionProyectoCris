package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.EstadoDAOJPAImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/estado")
public class EstadoRestController {

    @Autowired
    private EstadoDAOJPAImplementation estadoDAOJPAImplementation;

    @GetMapping("/idPais/{idPais}")
    public ResponseEntity GetByIdPais(@PathVariable int idPais) {
        Result result = new Result();
        result = estadoDAOJPAImplementation.GetByIdPais(idPais);

        return ResponseEntity.status(result.status).body(result);
    }

}

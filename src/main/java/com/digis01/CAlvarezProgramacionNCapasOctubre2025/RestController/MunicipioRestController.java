package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.MunicipioDAOJPAImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController {

    @Autowired
    private MunicipioDAOJPAImplementation municipioDAOJPAImplementation;

    @GetMapping("idEstado/{idEstado}")
    public ResponseEntity GetByIdEstado(@PathVariable int idEstado) {
        Result result = new Result();
        result = municipioDAOJPAImplementation.GetByIdEstado(idEstado);

        return ResponseEntity.status(result.status).body(result);
    }

}

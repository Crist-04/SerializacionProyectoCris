package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.MunicipioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MunicipioDAOJPAImplementation {

    @PersistenceContext
    private EntityManager entityManager;
    
    

    public Result GetByIdEstado(int idEstado) {
        Result result = new Result();
        
        
        return result;
    }
}
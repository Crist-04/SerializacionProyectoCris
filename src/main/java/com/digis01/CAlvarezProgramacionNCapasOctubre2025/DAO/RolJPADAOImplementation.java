package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        
        
        
        return result;
    }
}
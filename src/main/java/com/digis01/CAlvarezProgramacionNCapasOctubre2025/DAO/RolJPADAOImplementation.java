package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        try{
            TypedQuery<RolJPA> queryRol = entityManager.createQuery("FROM RolJPA", RolJPA.class);
            List<RolJPA> roles = queryRol.getResultList();
            
            result.correct = true;
            result.object = roles;
            result.status = 200;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "No se encontro ningun Rol";
            result.status = 500;
        }
        
        
        return result;
    }
}
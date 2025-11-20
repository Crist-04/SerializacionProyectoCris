package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ColoniaJPADAOImplementation {

    @PersistenceContext
    private EntityManager entityManager;

    public Result GetByIdMunicipio(int idMunicipio) {
        Result result = new Result();

        try {
            List<ColoniaJPA> colonias = entityManager
                    .createQuery("SELECT c FROM ColoniaJPA c WHERE  c.MunicipioJPA.IdMunicipio = :idMunicipio", ColoniaJPA.class)
                    .setParameter("idMunicipio", idMunicipio)
                    .getResultList();
            
            result.correct = true;
            result.status = 200;
            result.object = colonias;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.status = 500;
            
        }

        return result;
    }

}

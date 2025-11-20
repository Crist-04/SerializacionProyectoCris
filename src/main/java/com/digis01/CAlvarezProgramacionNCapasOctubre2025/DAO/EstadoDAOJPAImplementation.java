package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.EstadoJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EstadoDAOJPAImplementation implements IEstadoJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Result GetByIdPais(int idPais) {
        Result result = new Result();
        try {
            List<EstadoJPA> estados = entityManager.createQuery("SELECT c FROM EstadoJPA c WHERE c.PaisJPA.IdPais = :idPais", EstadoJPA.class)
                    .setParameter("idPais", idPais)
                    .getResultList();

            result.correct = true;
            result.object = estados;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Estado no Encontrado";
            result.status = 500;
        }

        return result;
    }
}

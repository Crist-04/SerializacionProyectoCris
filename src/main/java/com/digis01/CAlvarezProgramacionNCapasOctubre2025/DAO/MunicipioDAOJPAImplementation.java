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
public class MunicipioDAOJPAImplementation implements IMunicipioJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Result GetByIdEstado(int idEstado) {
        Result result = new Result();
        try {
            List<MunicipioJPA> municipios = entityManager
                    .createQuery("SELECT c FROM MunicipioJPA c WHERE c.EstadoJPA.IdEstado = :idEstado", MunicipioJPA.class)
                    .setParameter("idEstado", idEstado)
                    .getResultList();
            
            result.correct = true;
            result.object = municipios;
            result.status = 200;
            

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se encontro el municipio";
            result.status = 500;
        }

        return result;
    }
}

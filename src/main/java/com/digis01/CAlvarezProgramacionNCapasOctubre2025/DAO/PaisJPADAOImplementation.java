package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.PaisJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<PaisJPA> queryPais = entityManager.createQuery("FROM PaisJPA", PaisJPA.class);
            List<PaisJPA> paises = queryPais.getResultList();

            result.correct = true;
            result.object = paises;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se encontro ningun Pais";
            result.status = 500;
        }

        return result;
    }

}

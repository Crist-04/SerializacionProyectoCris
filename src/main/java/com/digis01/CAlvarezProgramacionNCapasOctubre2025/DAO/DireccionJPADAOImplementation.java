package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Result AddDireccion(DireccionJPA direccion, int idUsuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);
            direccion.setUsuarioJPA(usuario);

            entityManager.persist(direccion);
            entityManager.flush();
            
            result.correct = true;
            result.status = 201;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se añadio la direccion";
            result.status = 500;
        }

        return result;
    }

}

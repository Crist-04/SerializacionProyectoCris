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

    @Override
    @Transactional
    public Result UpdateDireccion(DireccionJPA direccion, int idUsuario) {
        Result result = new Result();
        try {
            DireccionJPA direccionActualizada = entityManager.find(DireccionJPA.class, direccion.getIdDireccion());
            direccionActualizada.setCalle(direccion.getCalle());
            direccionActualizada.setNumeroInterior(direccion.getNumeroInterior());
            direccionActualizada.setNumeroExterior(direccion.getNumeroExterior());
            direccionActualizada.setColoniaJPA(direccion.getColoniaJPA());

            entityManager.merge(direccionActualizada);
            entityManager.flush();

            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "La Direccion no se actualizo";
            result.status = 500;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteDireccion(int idDireccion) {
        Result result = new Result();

        try {
            DireccionJPA direccion = entityManager.find(DireccionJPA.class, idDireccion);

            entityManager.remove(direccion);
            entityManager.flush();

            result.correct = true;
            result.status = 200;
            result.errorMessage = "Direccion Eliminada";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "La direccion no se elimino";
            result.status = 500;
        }

        return result;
    }

}

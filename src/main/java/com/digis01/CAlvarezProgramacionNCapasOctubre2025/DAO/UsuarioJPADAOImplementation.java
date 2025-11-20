package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuarios = queryUsuario.getResultList();

            result.correct = true;
            result.status = 200;
            result.object = usuarios;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Add(UsuarioJPA usuario) {
        Result result = new Result();
        try {
            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "no puede ser nulo";
                result.status = 400;
                return result;
            }
            entityManager.persist(usuario);
            entityManager.flush();

            result.correct = true;
            result.status = 201;
            result.object = usuario;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result Update(UsuarioJPA usuario) {
        Result result = new Result();
        try {
            if (usuario == null || usuario.IdUsuario <= 0) {
                result.correct = false;
                result.errorMessage = "Usuario no existente";
                result.status = 400;
                return result;
            }

            entityManager.merge(usuario);
            entityManager.flush();

            result.correct = true;
            result.status = 200;
            result.object = usuario;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Usuario no actualizado";
            result.status = 404;
            return result;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Delete(int idUsuario) {
        Result result = new Result();
        try {
            if (idUsuario <= 0) {
                result.correct = false;
                result.errorMessage = "Usuario no Existente";
                result.status = 400;
                return result;
            }
            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);
            entityManager.remove(usuario);
            entityManager.flush();
            
            result.correct = true;
            result.status = 200;
            result.errorMessage = "El Usuario se Elimino";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "No se elimino el usuario";
            result.status = 500;
        }
        return result;
    }
    
    @Override
    public Result GetById(int idUsuario){
        Result result = new Result();
        try{
            
            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);
            
            result.correct = true;
            result.status = 200;
            result.object = usuario;
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = "El Usuario no se encontro";
            result.status = 500;
        }
        
        
        return result;
    }

}

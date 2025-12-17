package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.ColoniaJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.RolJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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
                result.errorMessage = "El usuario no puede ser nulo";
                result.status = 400;
                return result;
            }

//            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
//                result.correct = false;
//                result.errorMessage = "El correo es obligatirio";
//                result.status = 400;
//                return result;
//
//            }
            TypedQuery<Long> emailQuery = entityManager.createQuery(
                    "SELECT COUNT(u) FROM UsuarioJPA u WHERE u.Email = :email", Long.class);
            emailQuery.setParameter("email", usuario.getEmail().trim());
            Long emailCount = emailQuery.getSingleResult();

            if (emailCount > 0) {
                result.correct = false;
                result.errorMessage = "El correo electrónico ya está registrado";
                result.status = 400;
                return result;
            }

            if (usuario.getUserName() != null && !usuario.getUserName().trim().isEmpty()) {
                TypedQuery<Long> usernameQuery = entityManager.createQuery(
                        "SELECT COUNT(u) FROM UsuarioJPA u WHERE u.UserName = :username", Long.class);
                usernameQuery.setParameter("username", usuario.getUserName().trim());
                Long usernameCount = usernameQuery.getSingleResult();

                if (usernameCount > 0) {
                    result.correct = false;
                    result.errorMessage = "El nombre de usuario ya está registrado";
                    result.status = 400;
                    return result;
                }
            }

            if (usuario.Password != null && !usuario.Password.isEmpty()) {
                if (!usuario.Password.startsWith("$2a$")) {
                    // String passwordOriginal = usuario.Password;
                    usuario.Password = passwordEncoder.encode(usuario.Password);

                } else {
                    System.out.println("La contraseña ya está encriptada");
                }
            }

            if (usuario.direccionesJPA != null && !usuario.direccionesJPA.isEmpty()) {
                for (DireccionJPA direccion : usuario.direccionesJPA) {
                    direccion.setUsuarioJPA(usuario);

                    if (direccion.getColoniaJPA() != null && direccion.getColoniaJPA().getIdColonia() > 0) {
                        ColoniaJPA coloniaManaged = entityManager.find(
                                ColoniaJPA.class,
                                direccion.getColoniaJPA().getIdColonia()
                        );
                        if (coloniaManaged != null) {
                            direccion.setColoniaJPA(coloniaManaged);
                        }
                    }
                }
            }

            if (usuario.getRol() != null && usuario.getRol().getIdRol() > 0) {
                RolJPA rolManaged = entityManager.find(RolJPA.class, usuario.getRol().getIdRol());
                if (rolManaged != null) {
                    usuario.setRol(rolManaged);
                }
            }

            usuario.Estatus = 1;
            usuario.IsVerified = 0;

            entityManager.persist(usuario);
            entityManager.flush();

            result.correct = true;
            result.status = 201;
            result.object = usuario;

        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = "Error al guardar usuario: " + ex.getMessage();
            result.ex = ex;
            result.status = 500;
            return result;
        }
        if (result.correct) {
            try {
                emailService.enviarCorreo(usuario);
                System.out.println("Correo enviado a: " + usuario.getEmail());
            } catch (MessagingException e) {
                System.err.println(" Se creo el Usuario pero no se mando el correo" + e.getMessage());
                e.printStackTrace();
                result.errorMessage = "Se creo el usuario pero no se envio el correo";
            }

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

            UsuarioJPA usuarioExiste = entityManager.find(UsuarioJPA.class, usuario.IdUsuario);

            if (usuarioExiste == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                result.status = 404;
                return result;
            }

            if (usuario.Password != null && !usuario.Password.isEmpty()) {
                if (!usuario.Password.startsWith("$2a$")) {
                    usuario.Password = passwordEncoder.encode(usuario.Password);
                }
            } else {
                usuario.Password = usuarioExiste.Password;
            }

            usuario.direccionesJPA = usuarioExiste.direccionesJPA;

            entityManager.merge(usuario);
            entityManager.flush();

            result.correct = true;
            result.status = 200;
            result.object = usuario;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Usuario no actualizado";
            result.status = 500;
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
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {

            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);

            result.correct = true;
            result.status = 200;
            result.object = usuario;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "El Usuario no se encontro";
            result.status = 500;
        }

        return result;
    }

    @Override
    public Result Login(String username, String password) {
        Result result = new Result();
        try {
            TypedQuery<UsuarioJPA> query = entityManager.createQuery(
                    "FROM UsuarioJPA u WHERE u.UserName = :username AND u.Password = :password",
                    UsuarioJPA.class
            );
            query.setParameter("username", username);
            query.setParameter("password", password);

            List<UsuarioJPA> usuarios = query.getResultList();

            if (!usuarios.isEmpty()) {
                UsuarioJPA usuario = usuarios.get(0);

                if (usuario.Estatus != null && usuario.Estatus == 1) {
                    result.correct = true;
                    result.status = 200;
                    result.object = usuario;
                    result.errorMessage = "Login exitoso";
                } else {
                    result.correct = false;
                    result.status = 403;
                    result.errorMessage = "Usuario inactivo";
                }
            } else {
                result.correct = false;
                result.status = 401;
                result.errorMessage = "Credenciales incorrectas";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error en el login: " + ex.getMessage();
            result.ex = ex;
            result.status = 500;
        }

        return result;
    }

    @Override
    @Transactional
    public Result VerficarCuenta(int idUsuario) {
        Result result = new Result();
        try {
            UsuarioJPA usuario = entityManager.find(UsuarioJPA.class, idUsuario);

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                result.status = 404;
                return result;
            }

            if (usuario.IsVerified != null && usuario.IsVerified == 1) {
                result.correct = false;
                result.errorMessage = "La cuenta ya está verificada";
                result.status = 400;
                return result;
            }

            usuario.IsVerified = 1;
            entityManager.merge(usuario);
            entityManager.flush();

            result.correct = true;
            result.status = 200;
            result.errorMessage = "Cuenta verificada exitosamente";
            result.object = usuario;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al verificar la cuenta: " + ex.getMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }

    @Override
    @Transactional
    public Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, int idRol) {
        Result result = new Result();
        try {
            StringBuilder jpql = new StringBuilder("SELECT DISTINCT u FROM UsuarioJPA u ");
            jpql.append("LEFT JOIN FETCH u.rol ");
            jpql.append("LEFT JOIN FETCH u.direccionesJPA d ");
            jpql.append("LEFT JOIN FETCH d.ColoniaJPA c ");
            jpql.append("LEFT JOIN FETCH c.MunicipioJPA m ");
            jpql.append("LEFT JOIN FETCH m.EstadoJPA e ");
            jpql.append("LEFT JOIN FETCH e.PaisJPA ");
            jpql.append("WHERE 1=1 ");

            if (nombre != null && !nombre.trim().isEmpty()) {
                jpql.append("AND UPPER (u.Nombre) LIKE UPPER (:nombre) ");
            }
            if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) {
                jpql.append("AND UPPER(u.ApellidoPaterno) LIKE UPPER(:apellidoPaterno) ");
            }
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                jpql.append("AND UPPER(u.ApellidoMaterno) LIKE UPPER(:apellidoMaterno) ");
            }
            if (idRol > 0) {
                jpql.append("AND u.rol.IdRol = :idRol ");
            }

            TypedQuery<UsuarioJPA> query = entityManager.createQuery(jpql.toString(), UsuarioJPA.class);

            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }
            if (apellidoPaterno != null && !apellidoPaterno.trim().isEmpty()) {
                query.setParameter("apellidoPaterno", "%" + apellidoPaterno + "%");
            }
            if (apellidoMaterno != null && !apellidoMaterno.trim().isEmpty()) {
                query.setParameter("apellidoMaterno", "%" + apellidoMaterno + "%");
            }
            if (idRol > 0) {
                query.setParameter("idRol", idRol);
            }

            List<UsuarioJPA> usuariosJPA = query.getResultList();

            result.object = usuariosJPA;
            result.correct = true;
            result.status = 200;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return result;
    }

}

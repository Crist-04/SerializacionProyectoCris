package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.UsuarioJPADAOImplementation;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.LoginRequest;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.LoginResponse;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        try {
            System.out.println("\n=== LOGIN API ===");
            System.out.println("Username recibido: " + loginRequest.getUsername());

            // Validar credenciales
            Result result = usuarioJPADAOImplementation.Login(loginRequest.getUsername(), loginRequest.getPassword());

            if (result.correct) {
                UsuarioJPA usuario = (UsuarioJPA) result.object;

                // Generar token JWT

                String token = jwtUtil.generateToken(
                    usuario.UserName, 
                    usuario.IdUsuario
                );

                loginResponse.setCorrect(true);
                loginResponse.setToken(token);
                loginResponse.setMensaje("Login exitoso");
                loginResponse.setIdUsuario(usuario.IdUsuario);
                loginResponse.setUsername(usuario.UserName);
                
                String rolNombre = (usuario.getRol()!= null) ? usuario.getRol().getNombre():"Sin Rol";
                loginResponse.setRol(rolNombre);
                


                System.out.println("✅ Login exitoso - Token generado");
                System.out.println("Token: " + token.substring(0, 50) + "...");
                System.out.println("Rol: " +loginResponse.getRol());

                return ResponseEntity.ok(loginResponse);

            } else {
                // Credenciales incorrectas o usuario inactivo
                loginResponse.setCorrect(false);
                loginResponse.setMensaje(result.errorMessage);

                System.out.println("❌ Login fallido: " + result.errorMessage);

                return ResponseEntity.status(result.status).body(loginResponse);
            }

        } catch (Exception ex) {
            System.out.println("❌ ERROR en login:");
            ex.printStackTrace();

            loginResponse.setCorrect(false);
            loginResponse.setMensaje("Error en el servidor: " + ex.getMessage());

            return ResponseEntity.status(500).body(loginResponse);
        }
    }
}
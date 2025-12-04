package com.digis01.CAlvarezProgramacionNCapasOctubre2025.RestController;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO.IUsuarioRepositoryDAO;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.LoginRequest;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.LoginResponse;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class LoginRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUsuarioRepositoryDAO usuarioRepositoryDAO;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UsuarioJPA usuario = usuarioRepositoryDAO.findByUsername(loginRequest.getUsername());

            if (usuario == null) {
                loginResponse.setCorrect(false);
                loginResponse.setMensaje("Usuario no encontrado");
                return ResponseEntity.status(404).body(loginResponse);
            }

            if (usuario.Estatus == null || usuario.Estatus != 1) {
                loginResponse.setCorrect(false);
                loginResponse.setMensaje("Usuario inactivo");
                return ResponseEntity.status(403).body(loginResponse);
            }

            String token = jwtUtil.generateToken(
                    usuario.UserName,
                    usuario.IdUsuario
            );

            String rolNombre = (usuario.getRol() != null) ? usuario.getRol().getNombre() : "Sin rol";

            loginResponse.setCorrect(true);
            loginResponse.setToken(token);
            loginResponse.setMensaje("Login exitoso");
            loginResponse.setIdUsuario(usuario.IdUsuario);
            loginResponse.setUsername(usuario.UserName);
            loginResponse.setRol(rolNombre);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            loginResponse.setCorrect(false);
            loginResponse.setMensaje("Usuario o contraseña incorrectos");
            return ResponseEntity.status(401).body(loginResponse);

        } catch (Exception ex) {
            ex.printStackTrace();
            loginResponse.setCorrect(false);
            loginResponse.setMensaje("Error en el servidor: " + ex.getMessage());
            return ResponseEntity.status(500).body(loginResponse);
        }
    }
}

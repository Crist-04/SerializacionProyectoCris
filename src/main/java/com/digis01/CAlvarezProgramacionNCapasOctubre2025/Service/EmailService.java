package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Service;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarCorreo(UsuarioJPA usuario) throws MessagingException {
        String destinatario = usuario.getEmail();
        String asunto = "Bienvenido al Sistema";

        String contenidoHtml = construirPlantillaCorreo(usuario);

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(contenidoHtml, true);

        mailSender.send(mensaje);
        
        System.out.println("Correo enviado a: " + destinatario);
    }

    public String construirPlantillaCorreo(UsuarioJPA usuario) {

        String nombreCompleto = usuario.getNombre() + " "
                + usuario.getApellidoPaterno() + " "
                + usuario.getApellidoMaterno();

        return "<!DOCTYPE html>\n"
                + "<html lang=\"es\">\n"
                + "<head>\n"
                + "  <meta charset=\"UTF-8\">\n"
                + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "  <title>Bienvenida</title>\n"
                + "  <style>\n"
                + "    body { font-family: Arial, sans-serif; background-color: #f0f4ff; margin: 0; padding: 0; }\n"
                + "    .container { max-width: 600px; margin: 50px auto; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }\n"
                + "    .header { background: linear-gradient(90deg, #8FA3BD, #5c6bc0); color: white; padding: 30px; text-align: center; }\n"
                + "    .header h1 { margin: 0; font-size: 24px; }\n"
                + "    .content { padding: 40px 30px; color: #333; }\n"
                + "    .content h2 { color: #5c6bc0; margin-bottom: 20px; }\n"
                + "    .info-box { background: #e7f3ff; border-left: 4px solid #8FA3BD; padding: 15px; margin: 20px 0; border-radius: 4px; }\n"
                + "    .credentials { background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0; border: 1px solid #e0e0e0; }\n"
                + "    .credentials p { margin: 8px 0; }\n"
                + "    .credentials strong { color: #5c6bc0; }\n"
                + "    .footer { background: #f8f9fa; padding: 20px; text-align: center; color: #6c757d; font-size: 14px; border-top: 1px solid #e0e0e0; }\n"
                + "    .welcome-icon { font-size: 64px; text-align: center; margin: 20px 0; }\n"
                + "  </style>\n"
                + "</head>\n"
                + "<body>\n"
                + "  <div class=\"container\">\n"
                + "    <div class=\"header\">\n"
                + "      <h1> ¡Bienvenido al Sistema!</h1>\n"
                + "    </div>\n"
                + "    <div class=\"content\">\n"
                + "      <div class=\"welcome-icon\">👋</div>\n"
                + "      <h2>¡Hola " + usuario.getNombre() + "!</h2>\n"
                + "      <p>Tu cuenta ha sido creada exitosamente por el administrador del sistema.</p>\n"
                + "      \n"
                + "      <div class=\"info-box\">\n"
                + "        <strong> Tu cuenta está lista para usar</strong><br>\n"
                + "        Ya puedes iniciar sesión en el sistema con tus credenciales.\n"
                + "      </div>\n"
                + "      \n"
                + "      <div class=\"credentials\">\n"
                + "        <p><strong> Nombre Completo:</strong> " + nombreCompleto + "</p>\n"
                + "        <p><strong> Usuario:</strong> " + usuario.getUsername() + "</p>\n"
                + "        <p><strong> Email:</strong> " + usuario.getEmail() + "</p>\n"
                + "        <p><strong> Rol:</strong> " + (usuario.getRol() != null ? usuario.getRol().getNombre() : "Usuario") + "</p>\n"
                + "      </div>\n"
                + "      \n"
                + "      <p style=\"margin-top: 30px;\"><strong>Nota importante:</strong> Por seguridad, tu contraseña no se incluye en este correo. Fue proporcionada por el administrador al momento de crear tu cuenta.</p>\n"
                + "      \n"
                + "      <p style=\"margin-top: 20px; color: #6c757d; font-size: 14px;\">Si tienes alguna duda o no solicitaste esta cuenta, por favor contacta al administrador del sistema.</p>\n"
                + "    </div>\n"
                + "    <div class=\"footer\">\n"
                + "      <p>&copy; 2025 Sistema de Usuarios. Todos los derechos reservados.</p>\n"
                + "    </div>\n"
                + "  </div>\n"
                + "</body>\n"
                + "</html>";
    }
}
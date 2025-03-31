package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.DetalleVenta;

import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Stateless
public class MailService {

    public void enviarCorreo(String destinatario, String nombreCliente, String fechaCompra,
                             double totalCompra, List<DetalleVenta> detalles) throws MessagingException {
        final String remitente = "your_email@example.com";
        final String clave = "password";

        // Configuración de propiedades para SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        // Construcción del mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Confirmación de compra");

        String mensajeHtml = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }" +
                ".container { background-color: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px #ddd; padding: 20px; }" +
                "h2 { color: #333; }" +
                "p { font-size: 16px; }" +
                ".detalle { margin-top: 10px; padding: 10px; background-color: #eee; border-radius: 5px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>Confirmación de compra</h2>" +
                "<p>Gracias por tu compra, <strong>" + nombreCliente + "</strong>!</p>" +
                "<p><strong>Fecha:</strong> " + fechaCompra + "</p>" +
                "<p><strong>Total:</strong> Gs " + String.format("%,.2f",totalCompra) + "</p>" +
                "<h3>Detalles de la compra:</h3>";

        for (DetalleVenta detalle : detalles) {
            double totalDetalle = detalle.getPrecio() * detalle.getCantidad();

            mensajeHtml += "<div class='detalle'>" +
                    "<p><strong>" + detalle.getProducto().getNombre() + "</strong></p>" +
                    "<p>Cantidad: " + detalle.getCantidad() + " - Precio unitario: Gs " + detalle.getPrecio() + "</p>" +
                    "<p><strong>Total detalle: Gs " + String.format("%,.2f", totalDetalle) + "</strong></p>" +
                    "</div>";
        }

        mensajeHtml += "</div></body></html>";

        // Crear cuerpo del mensaje en HTML
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mensajeHtml, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);

        // Envío del correo
        Transport.send(message);
    }
}

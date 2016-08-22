package datos;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendEmail {


public static void SendEmail(int is)
{
try
        {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            if(is==1){
                props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            }
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "logycus.noreply@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("Logycus Colombia <logycus.noreply@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 
             html.setContent("<!DOCTYPE html>\n" +
"    <div style=\"width:100%;height:550;\">\n" +
"      <FONT FACE=\"tahoma\">\n" +
"        <div style=\"width:100%;height:20px;left:0;background-color:#000000;\">\n" +
"        </div>    \n" +
"        <div style=\"background-color:#F0F0F0;padding:1px;\">\n" +
"          <h2 style=\"text-align:center;\">Servicio iniciado</h2>\n" +
"          <p style=\"padding:10px;\">\n" +
"            Se ha activado un servicio por medio de la plataforma Logycus, con la siguiente\n" +
"            información.\n" +
"          </p>\n" +
"          <table style=\"width:100%;\">\n" +
"              <tr>\n" +
"                  <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DE VIAJE</th>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;\">NO. SOLICITUD</td>\n" +
"                  <td>00000001</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;\">NO. SERVICIO</td>\n" +
"                  <td>00000001</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">ORIGEN</td>\n" +
"                <td>Carrera 53 # 64 - 72, Barranquilla - Atlántico, Colombia</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">DESTINO</td>\n" +
"                <td>McDonalds Prado, Barranquilla, Alto Prado, Atlántico, Colombia</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">HORA DE CARGUE</td>\n" +
"                <td>2016/07/18 05:30PM</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">HORA DE DESCARGUE</td>\n" +
"                <td>2016/07/20 01:00PM</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;\">ESTADO</td>\n" +
"                  <td>Cargando</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DEL CONDUCTOR</th>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">CC</td>\n" +
"                  <td>1047229748</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">NO. DE LICENCIA C3</td>\n" +
"                  <td>102452525</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">NOMBRE Y APELLIDO</td>\n" +
"                  <td>Roberto Ojeda Lopez</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">TELEFONO</td>\n" +
"                  <td>300 830 0794</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DEL VEHICULO</th>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">PLACA</td>\n" +
"                  <td>ABC152</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">PROPIETARIO</td>\n" +
"                  <td>Roberto Carlos Ojeda Lopez</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\">TIPO CARGA</td>\n" +
"                  <td>Seca</td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                  <td style=\"background-color: #E8E8E8;width: 40%;\"># POLIZA</td>\n" +
"                  <td>FALL55114</td>\n" +
"              </tr>\n" +
"          </table>\n" +
"      <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"       <center>Por favor no responder a este email.</center>\n" +
"       <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"        <center>© 2016 Logycus360</center>\n" +
"      </div>\n" +
"        </div>\n" +
"      </FONT>\n" +
"    </div>\n", "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
 
         message.addRecipient(Message.RecipientType.CC, new InternetAddress("rojedalopez@gmail.com"));
         message.setSubject("por que se");
         message.setContent(alternative);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect("logycus.noreply@gmail.com", "l0gycus360");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        }
        catch (Exception e)
        {
            System.out.println("ERRORRRRR : " + e.toString());
        }
    }
}
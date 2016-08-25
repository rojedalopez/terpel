package datos;
 
import bean.Servicio;
import bean.Solicitud;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class Mails {
    public static void SendMail(String user, String token, String as, String textoBoton){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "tuconductor.noreplay@gmail.com";
        String password = "tcsas2016";
        String asunto = as;
 
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("Tuconductor Colombia <tuconductor.noreplay@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 
             html.setContent("<div style='font-size: 12px;color: #424242;width:100%;margin-left:auto;margin-right:auto;padding:10px;background-color: #E0E0E0;'>\n" +
"            <div style='width: 70%; margin: 0 auto;background-color: #ffffff;'>\n" +
"    <div style='border-bottom: 1px solid #E0E0E0;'>\n" +
"      <div style='width: 100%;margin-left: auto;margin-right: auto;background-color: navy;height: 15px;'>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style='box-shadow: 0 2px 5px rgba(0, 0, 0, 0.26);width: 100%;margin-left: auto;margin-right: auto;'>\n" +
"      <div style='box-sizing: border-box;'>\n" +
"        <center><h2 style='font-weight: 250;font-size: 2.5em;margin: 0.5em 0.25em;display: inline-block;color: #3E4156;'>\n" +
              as + 
"        </h2></center>\n" +
"       <center style='padding: 20px 10px;'>\n" +
"               <a style='color:white; background-color:red; border: solid 1px white;padding: 17px;text-decoration:none; \n" +
"                  cursor:pointer;font-weight:bold;' href='http://tuconductor.xyz/validate?user="+user+"&tkn="+token+"'>\n" +
                    textoBoton +
"               </a>\n" +
"       </center>\n" +
"       <p style='padding: 15px;'>\n" +
"       Para completar su registro es necesario que active su cuenta, para esto debe darle clic al boton anterior.<br/>\n" +
"       Si no es posible ingresar al boton, ingrese al siguiente link:<br/>\n" +
"       <a href='http://tuconductor.xyz/validate?user="+user+"&tkn="+token+"'>href='http://tuconductor.xyz/validate?user="+user+"&tkn="+token+"'</a>\n" +
"       <br/>\n" +
"       <br/>\n" +
"       Si ya confirmaste tu cuenta o no has solicitado un restablecimiento de contraseña o usted siente que ha recibido este mensaje por error, \n" +
"       por favor llame a nuestro equipo de soporte 24/7 de inmediato. <br/>\n" +
"       Si usted no hace nada, no se preocupes; nadie va a ser capaz de cambiar su contraseña sin acceso a este correo electrónico.\n" +
"       </p>\n" +
"      </div>\n" +
"      <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"       <center>Por favor no responder a este email.</center>\n" +
"       <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"        <center>© 2016 TuConductor</center>\n" +
"      </div>\n" +
"    </div>\n" +
"    </div>        \n" +
"  </div>", "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
         //se recorre la lista de correos del contrato
 
 
         message.addRecipient(Message.RecipientType.CC, new InternetAddress(user));
         message.setSubject(asunto);
         message.setContent(alternative);
 
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
        }
    }
    
    public static void SendMailForgot(String user, String token, String as, String textoBoton){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "tuconductor.noreplay@gmail.com";
        String password = "tcsas2016";
        String asunto = as;
 
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("TuConductor Colombia <tuconductor.noreplay@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 
             html.setContent("<div style='font-size: 12px;color: #424242;width:100%;margin-left:auto;margin-right:auto;padding:10px;background-color: #E0E0E0;'>\n" +
"            <div style='width: 70%; margin: 0 auto;background-color: #ffffff;'>\n" +
"    <div style='border-bottom: 1px solid #E0E0E0;'>\n" +
"      <div style='width: 100%;margin-left: auto;margin-right: auto;background-color: navy;height: 15px;'>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style='box-shadow: 0 2px 5px rgba(0, 0, 0, 0.26);width: 100%;margin-left: auto;margin-right: auto;'>\n" +
"      <div style='box-sizing: border-box;'>\n" +
"        <center><h2 style='font-weight: 250;font-size: 2.5em;margin: 0.5em 0.25em;display: inline-block;color: #3E4156;'>\n" +
              as + 
"        </h2></center>\n" +
"       <center style='padding: 20px 10px;'>\n" +
"               <a style='color:white; background-color:red; border: solid 1px white;padding: 17px;text-decoration:none; \n" +
"                  cursor:pointer;font-weight:bold;' href='http://tuconductor.xyz/validateforgot?user="+user+"&tkn="+token+"'>\n" +
                    textoBoton +
"               </a>\n" +
"       </center>\n" +
"       <p style='padding: 15px;'>\n" +
"       Para completar su registro es necesario que active su cuenta, para esto debe darle clic al boton anterior.<br/>\n" +
"       Si no es posible ingresar al boton, ingrese al siguiente link:<br/>\n" +
"       <a href='http://tuconductor.xyz/validateforgot?user="+user+"&tkn="+token+"'>href='http://tuconductor.xyz/validateforgot?user="+user+"&tkn="+token+"'</a>\n" +
"       <br/>\n" +
"       <br/>\n" +
"       Si ya confirmaste tu cuenta o no has solicitado un restablecimiento de contraseña o usted siente que ha recibido este mensaje por error, \n" +
"       por favor llame a nuestro equipo de soporte 24/7 de inmediato. <br/>\n" +
"       Si usted no hace nada, no se preocupes; nadie va a ser capaz de cambiar su contraseña sin acceso a este correo electrónico.\n" +
"       </p>\n" +
"      </div>\n" +
"      <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"       <center>Por favor no responder a este email.</center>\n" +
"       <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"        <center>© 2016 TuConductor</center>\n" +
"      </div>\n" +
"    </div>\n" +
"    </div>        \n" +
"  </div>", "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
         //se recorre la lista de correos del contrato
 
 
         message.addRecipient(Message.RecipientType.CC, new InternetAddress(user));
         message.setSubject(asunto);
         message.setContent(alternative);
 
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
        }
    }
    public static void main(String[] args) {
        SendMailOferta("rojedalopez@gmail.com", "prueba", "");
    }
    
    
    public static void SendMailOferta(String user, String as, String mensaje){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "logycus.noreply@gmail.com";
        String password = "l0gycus360";
        String asunto = as;
 System.out.println("0 ");
        Properties props = new Properties();
 System.out.println("0 ");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        //props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 System.out.println("0 ");
        Session session = Session.getInstance(props, null);
 System.out.println("0 ");
        try {
 
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
 System.out.println("0 ");
         //se recorre la lista de correos del contrato
 System.out.println("0 ");
 
         message.addRecipient(Message.RecipientType.CC, new InternetAddress(user));
         message.setSubject(asunto);
         message.setContent(alternative);
 
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 System.out.println("0 ");
        } catch (MessagingException e) {
            System.out.println("0 " + e.toString());
        }
    }
    
    public static void SendMailSolicitudTransportadoras(List<String> users, Solicitud solicitud){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "rojedalopez@gmail.com";
        String password = "r0b3rto,.,1047";
        String asunto = "Nueva solicitud generada.";
 System.out.println("0 ");
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("Logycus Colombia <logycus.noreply@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 System.out.println("1 ");
             html.setContent("<!DOCTYPE html>\n" +
" <div style=\"width:100%;height:550;\">\n" +
"   <FONT FACE=\"tahoma\">\n" +
"     <div style=\"width:100%;height:20px;left:0;background-color:#000000;\">\n" +
"     </div>    \n" +
"     <div style=\"background-color:#F0F0F0;padding:10px;\">\n" +
"       <h2 style=\"text-align:center;\">SOLICITUD DE SERVICIO</h2>\n" +
"       <center><img width=\"200px\" src=\""+solicitud.getUrlempresa()+"\"/></center>"+
"       <p style=\"padding:10px;\">\n" +
"           La empresa <b>"+solicitud.getEmpresa()+"</b> ha ingresado al sistema una solicitud de transporte, cuya información aparece a continuación.<br>\n" +
"           Si estas interesado en atender la solicitud, te invitamos a dirigirte al boton que aparece al final de la información.\n" +
"       </p>\n" +
"       <table style=\"width:100%; border: #BDBDBD solid 1px;\" rules=\"all\">\n" +
"           <tr>\n" +
"               <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DE VIAJE</th>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">NO. SOLICITUD</td>\n" +
"               <td>"+solicitud.getId()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">ORIGEN</td>\n" +
"             <td>"+solicitud.getOrigen()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">DESTINO</td>\n" +
"             <td>"+solicitud.getDestino()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">NO. DE EQUIPOS</td>\n" +
"               <td>"+solicitud.getNo_equipos()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">TIPO DE CARGUE</td>\n" +
"               <td>"+solicitud.getNombre_cargue()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">RANGO ESTIMADO DE CARGUE</td>\n" +
"             <td>"+solicitud.getCarguemin()+" - "+solicitud.getCarguemax()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">RANGO ESTIMADO DE DESCARGUE</td>\n" +
"             <td>"+solicitud.getDescarguemin()+" - "+solicitud.getDescarguemax()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VALOR DEL FLETE</td>\n" +
"             <td>$"+solicitud.getDes_flete()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">KMS ESTIMADOS DE VIAJE</td>\n" +
"             <td>"+solicitud.getKms()+"</td>\n" +
"           </tr>\n" +
"       </table>\n" +
"       <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"           <center><a style=\"background-color: black; color:white; padding: 7px;\" href=\"http://logycus360.com/forward?url_forward=/transporter/solicitudes.jsp\"><b>MAS INFORMACIÓN AQUI</b></a></center>\n" +
"       </div>\n" +
"   <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"    <center>Por favor no responder a este email.</center>\n" +
"    <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"     <center>© 2016 Logycus360</center>\n" +
"   </div>\n" +
"     </div>\n" +
"   </FONT>\n" +
" </div>" , "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
         //se recorre la lista de correos del contrato
 System.out.println("2 ");
         for(String user:users){
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));
            System.out.println(user);
         }
         message.setSubject(asunto);
         message.setContent(alternative);
 System.out.println("3 ");
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
            System.out.println("error en " + e);
        }
    }
    
    
    public static void SendMailServiciosGeneradores(List<String> users, Servicio servicio){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "logycus.noreply@gmail.com";
        String password = "l0gycus360";
        String asunto = "Asignación de vehiculo a solicitud.";
 System.out.println("0 ");
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        //
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("Logycus Colombia <logycus.noreply@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 System.out.println("1 ");
             html.setContent("<!DOCTYPE html>\n" +
" <div style=\"width:100%;height:550;\">\n" +
"   <FONT FACE=\"tahoma\">\n" +
"     <div style=\"width:100%;height:20px;left:0;background-color:#000000;\">\n" +
"     </div>    \n" +
"     <div style=\"background-color:#F0F0F0;padding:10px;\">\n" +
"       <h2 style=\"text-align:center;\">ASIGNACIÓN DE VEHICULO</h2>\n" +
"       <center><img width=\"200px\" src=\""+servicio.getUrl_transportadora()+"\"/></center>\n" +
"       <p style=\"padding:10px;\">\n" +
"           La empresa <b>"+servicio.getTransportadora()+"</b> ha asignado uno de sus vehiculos para cubrir la solicitud NO. "+servicio.getSolicitud()+".\n" +
"       </p>\n" +
"       <table style=\"width:100%; border: #BDBDBD solid 1px;\" rules=\"all\">\n" +
"            <tr>\n" +
"                <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DE LA SOLICITUD</th>\n" +
"            </tr>\n" +
"            <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">RUTA</td>\n" +
"                <td>"+servicio.getDesc_inicio()+" - "+servicio.getDesc_fin()+"</td>\n" +
"            </tr>\n" +
"            <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">RANGO DE CARGUE</td>\n" +
"                <td>"+servicio.getMin_carg()+" - "+servicio.getMax_carg()+"</td>\n" +
"            </tr>\n" +
"            <tr>\n" +
"                <td style=\"background-color: #E8E8E8;\">RANGO DE DESCARGUE</td>\n" +
"                <td>"+servicio.getMin_desc()+" - "+servicio.getMax_desc()+"</td>\n" +
"            </tr>\n" +                     
"        </table>"+                     
"       <table style=\"width:100%;margin-top: 15px; border: #BDBDBD solid 1px;\" rules=\"all\" >\n" +
"           <tr>\n" +
"               <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DEL CONDUCTOR</th>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td colspan=\"2\" style=\"background-color:#000000;padding: 4px;\">\n" +
"                    <center><img width=\"150px\" src=\""+servicio.getUrl_conductor()+"\"/></center>\n" +
"               </td> \n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">NOMBRE</td>\n" +
"               <td>"+servicio.getNombre_completo()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">DOCUMENTO</td>\n" +
"               <td>"+servicio.getDoc()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">NO. LICENCIA</td>\n" +
"             <td>"+servicio.getLicencia()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VIGENCIA LICENCIA</td>\n" +
"             <td>"+servicio.getExp_lic() +" - "+ servicio.getVence_lic() +"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">TELEFONO</td>\n" +
"               <td>"+servicio.getTelefono()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <td style=\"background-color: #E8E8E8;\">DIRECCION</td>\n" +
"               <td>"+servicio.getDireccion()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"               <th colspan=\"2\" style=\"background-color: #E8E8E8;text-align: center;\">DATOS DEL VEHICULO</th>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">TIPO EQUIPO</td>\n" +
"               <td>"+servicio.getTipo_equipo()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">PLACA</td>\n" +
"               <td>"+servicio.getPlaca()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">LIC. DE TRANSITO</td>\n" +
"               <td>"+servicio.getLic_transito()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">MARCA</td>\n" +
"               <td>"+servicio.getMarca()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">REFERENCIA</td>\n" +
"               <td>"+servicio.getReferencia()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">MODELO</td>\n" +
"               <td>"+servicio.getModelo()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">TIPO REMOLQUE</td>\n" +
"               <td>"+servicio.getTipo_remolque()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">PLACA REMOLQUE</td>\n" +
"               <td>"+servicio.getPlaca_rem()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">LIC. TRANSITO REMOLQUE</td>\n" +
"               <td>"+servicio.getLic_transito_r()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">POLIZA DE SEGURO</td>\n" +
"               <td>"+servicio.getPoliza()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">COMPAÑIA ASEGURADORA</td>\n" +
"               <td>"+servicio.getComp()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VIGENCIA POLIZA</td>\n" +
"             <td>"+servicio.getExp_poliza()+" - "+ servicio.getVence_poliza()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">POLIZA DE SEGURO H.C.</td>\n" +
"               <td>"+servicio.getPoliza_hc()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">COMPAÑIA ASEGURADORA</td>\n" +
"               <td>"+servicio.getPoliza_hc()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VIGENCIA POLIZA H.C.</td>\n" +
"             <td>"+servicio.getExp_poliza_hc()+" - "+ servicio.getVence_poliza_hc()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">SOAT</td>\n" +
"               <td>"+servicio.getSoat()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VIGENCIA SOAT</td>\n" +
"             <td>"+servicio.getExp_soat()+" - "+ servicio.getVence_soat()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">TECNOMECANICA</td>\n" +
"               <td>"+servicio.getTecno()+"</td>\n" +
"           </tr>\n" +
"           <tr>\n" +
"             <td style=\"background-color: #E8E8E8;\">VIGENCIA TECNOMECANICA</td>\n" +
"             <td>"+servicio.getExp_tecno()+" - "+ servicio.getVence_tecno()+"</td>\n" +
"           </tr>\n" +
"       </table>\n" +
"       <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"           <center><a style=\"background-color: black; color:white; padding: 7px;\" href=\"http://logycus360.com/forward?url_forward=/empresas/solicitudes.jsp\"><b>MAS INFORMACIÓN AQUI</b></a></center>\n" +
"       </div>\n" +
"   <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"    <center>Por favor no responder a este email.</center>\n" +
"    <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"     <center>© 2016 Logycus360</center>\n" +
"   </div>\n" +
"     </div>\n" +
"   </FONT>\n" +
" </div>" , "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
         //se recorre la lista de correos del contrato
 System.out.println("2 ");
         for(String user:users){
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));
            System.out.println(user);
         }
         message.setSubject(asunto);
         message.setContent(alternative);
 System.out.println("3 ");
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
            System.out.println("error en " + e);
        }
    }
    
    
    public static void SendCompraEmpleado(String user, String as, String mensaje, String archivo, String ruta, String hojavida){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "tuconductor.noreplay@gmail.com";
        String password = "tcsas2016";
        String asunto = as;
 
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("TuConductor Colombia <tuconductor.noreplay@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 
             html.setContent("<div style='font-size: 12px;color: #424242;width:100%;margin-left:auto;margin-right:auto;padding:10px;background-color: #E0E0E0;'>\n" +
"            <div style='width: 70%; margin: 0 auto;background-color: #ffffff;'>\n" +
"    <div style='border-bottom: 1px solid #E0E0E0;'>\n" +
"      <div style='width: 100%;margin-left: auto;margin-right: auto;background-color: navy;height: 15px;'>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style='box-shadow: 0 2px 5px rgba(0, 0, 0, 0.26);width: 100%;margin-left: auto;margin-right: auto;'>\n" +
"      <div style='box-sizing: border-box;'>\n" +
"        <center><h2 style='font-weight: 250;font-size: 2.5em;margin: 0.5em 0.25em;display: inline-block;color: #3E4156;'>\n" +
              as + 
"        </h2></center>\n" +
"       <p style='padding: 15px;'>\n" +
        mensaje+
"       </p>\n" +
"      </div>\n" +
"      <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"       <center>Por favor no responder a este email.</center>\n" +
"       <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"        <center>© 2016 TuConductor</center>\n" +
"      </div>\n" +
"    </div>\n" +
"    </div>        \n" +
"  </div>", "text/html");
 
        BodyPart adjunto = new MimeBodyPart();
        BodyPart adjunto2 = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource(ruta+"/excel/"+archivo)));
        adjunto.setFileName(archivo);
        
        if( hojavida.length() != 0 ){
            adjunto2.setDataHandler(new DataHandler(new FileDataSource(ruta+"/upload/"+hojavida)));
            adjunto2.setFileName(hojavida);
        }
        /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
         alternative.addBodyPart(adjunto);
         if( hojavida.length() != 0 ){
            alternative.addBodyPart(adjunto2);
         }
         //se recorre la lista de correos del contrato
 
 
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(user));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress("jmcastilla91@gmail.com"));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress("rojedalopez@gmail.com"));
         message.setSubject(asunto);
         message.setContent(alternative);
 
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
        }
    }
    
    
    public static void SendMailAgregoHV(String as, String mensaje, String cod, List<String> correos){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "tuconductor.noreplay@gmail.com";
        String password = "tcsas2016";
        String asunto = as;
 
        Properties props = new Properties();
 
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);
 
        Session session = Session.getInstance(props, null);
 
        try {
 
         MimeMultipart alternative = new MimeMultipart("alternative");
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("TuConductor Colombia <tuconductor.noreplay@gmail.com>"));
         MimeBodyPart html = new MimeBodyPart();
 
             html.setContent("<div style='font-size: 12px;color: #424242;width:100%;margin-left:auto;margin-right:auto;padding:10px;background-color: #E0E0E0;'>\n" +
"            <div style='width: 70%; margin: 0 auto;background-color: #ffffff;'>\n" +
"    <div style='border-bottom: 1px solid #E0E0E0;'>\n" +
"      <div style='width: 100%;margin-left: auto;margin-right: auto;background-color: navy;height: 15px;'>\n" +
"      </div>\n" +
"    </div>\n" +
"    <div style='box-shadow: 0 2px 5px rgba(0, 0, 0, 0.26);width: 100%;margin-left: auto;margin-right: auto;'>\n" +
"      <div style='box-sizing: border-box;'>\n" +
"        <center><h2 style='font-weight: 250;font-size: 2.5em;margin: 0.5em 0.25em;display: inline-block;color: #3E4156;'>\n" +
              as + 
"        </h2></center>\n" +
"       <center style='padding: 20px 10px;'>\n" +
"               <a style='color:white; background-color:red; border: solid 1px white;padding: 17px;text-decoration:none; \n" +
"                  cursor:pointer;font-weight:bold;' href='http://tuconductor.xyz/forward?url_forward=/admin/editprofile-employed.jsp?cod="+cod+"'>Revisar</a>\n" +
"       </center>\n" +                     
"       <p style='padding: 15px;'>\n" +
        mensaje+
"       </p>\n" +
"      </div>\n" +
"      <div style='border-top: 1px solid #E0E0E0;padding:25px;'>\n" +
"       <center>Por favor no responder a este email.</center>\n" +
"       <center>Los correos electrónicos enviados a esta dirección no serán contestados.</center>\n" +
"        <center>© 2016 TuConductor</center>\n" +
"      </div>\n" +
"    </div>\n" +
"    </div>        \n" +
"  </div>", "text/html");
 
         /*BodyPart imgPart = new MimeBodyPart();
         String fileName = imagen;
 
 
 
         DataSource ds = new FileDataSource(fileName);
         imgPart.setDataHandler(new DataHandler(ds));
         imgPart.setHeader("Content-ID", "<logoimg>");
         alternative.addBodyPart(imgPart); */
         alternative.addBodyPart(html);
 
         //se recorre la lista de correos del contrato
         for(String x: correos){
             if(!x.isEmpty()){
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(x));
             }
         }
         message.addRecipient(Message.RecipientType.TO, new InternetAddress("jmcastilla91@gmail.com"));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress("rojedalopez@gmail.com"));
         message.setSubject(asunto);
         message.setContent(alternative);
 
         Transport tr = session.getTransport("smtp");
         tr.connect(servidorSMTP, usuario, password);
         message.saveChanges();   
         tr.sendMessage(message, message.getAllRecipients());
         tr.close();
 
        } catch (MessagingException e) {
        }
    }
}
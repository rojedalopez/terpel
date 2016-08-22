package servletsSave;

import bean.Usuario;
import datos.save.Guardar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class saveConductor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, ClassNotFoundException, SQLException, InvalidKeyException {
        response.setContentType("text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        
        try
        {
          BufferedReader reader = request.getReader();
          String line = null;
          while ((line = reader.readLine()) != null)
          {
            sb.append(line);
          }
        } catch (Exception e) {}
 
        JSONParser parser = new JSONParser();
        JSONObject joConductor = null;
        System.out.println(sb.toString());
        joConductor = (JSONObject) parser.parse(sb.toString());
        
        String tipo_doc = (String) joConductor.get("tipo_doc");
        String doc = (String) joConductor.get("doc");
        String num_licencia = (String) joConductor.get("lic");
        String exp_lic = (String) joConductor.get("exp_lic");
        String vence_lic = (String) joConductor.get("vence_lic");
        String nombre = (String) joConductor.get("nombre");
        String apellido = (String) joConductor.get("apellido");
        String telefono = (String) joConductor.get("telefono");
        String direccion = (String) joConductor.get("direccion");
        String mail = (String) joConductor.get("mail");
        String contrasena = (String) joConductor.get("contrasena");
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                    boolean operacion = Guardar.InsertConductor(mail, tipo_doc, doc, num_licencia, exp_lic, vence_lic, contrasena,
                            nombre, apellido, telefono, direccion, u.getNit(), "");
                    out.print(operacion);
                    
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(saveConductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(saveConductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(saveConductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(saveConductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

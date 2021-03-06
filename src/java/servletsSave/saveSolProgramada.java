package servletsSave;

import bean.Usuario;
import datos.save.Guardar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class saveSolProgramada extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, ClassNotFoundException, SQLException {
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
        JSONObject joSolicitud = null;
        System.out.println(sb.toString());
        joSolicitud = (JSONObject) parser.parse(sb.toString());
        
        String inicio = (String)joSolicitud.get("id_inicio").toString();
        
        String fin = (String)joSolicitud.get("id_fin").toString();
        
        int tipo_carga = Integer.parseInt(joSolicitud.get("id_tipocargue").toString());
                
       
        String carguemin = (String) joSolicitud.get("min_carg");
        String carguemax = (String) joSolicitud.get("max_carg");
        String descarguemax = (String) joSolicitud.get("min_desc");
        String descarguemin = (String) joSolicitud.get("max_desc");
        
        int equipos = Integer.parseInt(joSolicitud.get("equipos").toString());
        int id = Integer.parseInt(joSolicitud.get("id").toString());
        
        int mes = Integer.parseInt(joSolicitud.get("mes").toString());
        int dia = Integer.parseInt(joSolicitud.get("dia").toString());
        int anio = Integer.parseInt(joSolicitud.get("anio").toString());
        String ccosto = (String) joSolicitud.get("id_ccosto");
        
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject x = Guardar.SaveSolProgramada(id, anio, mes, dia, carguemin, carguemax, descarguemin, descarguemax,  
                equipos, tipo_carga, ccosto, inicio, fin);
                System.out.println(x);
                out.println(x.toJSONString());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(saveSolProgramada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(saveSolProgramada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(saveSolProgramada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

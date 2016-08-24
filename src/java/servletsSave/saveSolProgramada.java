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
        
        JSONObject origen = (JSONObject) joSolicitud.get("inicio");
        float lat_origen = Float.parseFloat(origen.get("lat").toString());
        float lng_origen = Float.parseFloat(origen.get("lng").toString());
        String norigen = (String) origen.get("desc");
        String inicio = (String)origen.get("id").toString();
        
        JSONObject destino = (JSONObject) joSolicitud.get("fin");
        float lat_destino = Float.parseFloat(destino.get("lat").toString());
        float lng_destino = Float.parseFloat(destino.get("lng").toString());
        String ndestino = (String) destino.get("desc");
        String fin = (String)destino.get("id").toString();
        
        JSONObject obj_carga = (JSONObject) joSolicitud.get("carga");
        int tipo_carga = Integer.parseInt(obj_carga.get("id").toString());
                
       
        String carguemin = (String) joSolicitud.get("min_carg");
        String carguemax = (String) joSolicitud.get("max_carg");
        String descarguemax = (String) joSolicitud.get("min_desc");
        String descarguemin = (String) joSolicitud.get("max_desc");
        
        int equipos = Integer.parseInt(joSolicitud.get("equipos").toString());
        
        int mes = Integer.parseInt(joSolicitud.get("mes").toString());
        int dia = Integer.parseInt(joSolicitud.get("dia").toString());
        int anio = Integer.parseInt(joSolicitud.get("anio").toString());
        String ccosto = (String) joSolicitud.get("ccosto");
        
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject x = Guardar.SaveSolProgramada(anio, mes, dia, carguemin, carguemax, descarguemin, descarguemax,  
                equipos, tipo_carga, ccosto, inicio, fin);
                System.out.println(x);
                out.println(x.toJSONString());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

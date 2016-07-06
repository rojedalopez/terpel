package servletsSave;

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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class saveServicio extends HttpServlet {

   
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
        
        JSONObject origen = (JSONObject) joSolicitud.get("addressOrigin");
        float lat_origen = Float.parseFloat(origen.get("lat").toString());
        float lng_origen = Float.parseFloat(origen.get("lng").toString());
        String norigen = (String) joSolicitud.get("nameOrigin");
        JSONObject destino = (JSONObject) joSolicitud.get("addressDestination");
        float lat_destino = Float.parseFloat(destino.get("lat").toString());
        float lng_destino = Float.parseFloat(destino.get("lng").toString());
        String ndestino = (String) joSolicitud.get("nameDestination");
        int tipo_carga = Integer.parseInt(joSolicitud.get("carga").toString());
        String cargue = (String) joSolicitud.get("dcargue");
        System.out.println(cargue);
        String descargue = (String) joSolicitud.get("ddescargue");
        System.out.println(descargue);
        int equipos = Integer.parseInt(joSolicitud.get("equipos").toString());
        String orden = (String) joSolicitud.get("orden");
        String empresa = "123456789";
                
        try (PrintWriter out = response.getWriter()) {
            
            String x = Guardar.SaveServicio(norigen, lat_origen, lng_origen, ndestino, lat_destino, lng_destino, cargue, descargue, equipos, tipo_carga, orden, empresa);
            System.out.println(x);
            out.println(x);
        }
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(saveServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(saveServicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(saveServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

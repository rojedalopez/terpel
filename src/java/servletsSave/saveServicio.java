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
        String carguemin = (String) joSolicitud.get("dcarguemin");
        String carguemax = (String) joSolicitud.get("dcarguemax");
        String descarguemax = (String) joSolicitud.get("ddescarguemax");
        String descarguemin = (String) joSolicitud.get("ddescarguemin");
        int equipos = Integer.parseInt(joSolicitud.get("equipos").toString());
        int flete = Integer.parseInt(joSolicitud.get("flete").toString());
        String orden = (String) joSolicitud.get("orden");
        String nota_detalle = (String) joSolicitud.get("nota_detalle");
        String nota_pago = (String) joSolicitud.get("nota_pago");
        String kms = (String) joSolicitud.get("kms");
        String time = (String) joSolicitud.get("time");
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject x = Guardar.SaveServicio(norigen, lat_origen, lng_origen, ndestino, lat_destino, lng_destino, 
                carguemin, carguemax, descarguemin, descarguemax, equipos, tipo_carga, orden, nota_detalle, u.getCodigo(),
                flete, nota_pago, kms, time);
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
        } catch (ParseException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(saveServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

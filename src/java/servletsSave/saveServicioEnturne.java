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

public class saveServicioEnturne extends HttpServlet {

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
        
        int origen = Integer.parseInt(joSolicitud.get("origen").toString());
        String n_origen = (String)joSolicitud.get("n_origen");
        float lat_origen = Float.parseFloat(joSolicitud.get("lat_origen").toString());
        float lat_destino = Float.parseFloat(joSolicitud.get("lat_destino").toString());
        float lng_origen = Float.parseFloat(joSolicitud.get("lng_origen").toString());
        float lng_destino = Float.parseFloat(joSolicitud.get("lng_destino").toString());
        int destino = Integer.parseInt(joSolicitud.get("destino").toString());
        String n_destino = (String)joSolicitud.get("n_destino");
        String dcargue = (String) joSolicitud.get("dcargue");
        String ddescargue= (String) joSolicitud.get("ddescargue");
        int equipo_conductor = Integer.parseInt(joSolicitud.get("equipo_conductor").toString());
        String guia = (String) joSolicitud.get("guia");
        String nota = (String) joSolicitud.get("nota");
        String kms = (String) joSolicitud.get("kms");
        String time = (String) joSolicitud.get("time");
        String registro = (String) joSolicitud.get("reg_enturne");
        String cargue = Integer.parseInt(joSolicitud.get("cargue").toString())+"";
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject x = Guardar.SaveServicioEnturne(origen, n_origen, lat_origen, lng_origen, destino, n_destino,
                lat_destino, lng_destino, dcargue, ddescargue, equipo_conductor, guia, nota, u.getNit(), kms, time, registro, cargue,
                u.getRazon_social(), u.getUrl_imagen());
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
}

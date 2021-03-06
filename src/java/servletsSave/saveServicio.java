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
        System.err.println("bandera 1");
        int tipo_carga = Integer.parseInt(joSolicitud.get("carga").toString());
        
        String carguemin = (String) joSolicitud.get("dcarguemin");
        String carguemax = (String) joSolicitud.get("dcarguemax");
        String descarguemax = (String) joSolicitud.get("ddescarguemax");
        String descarguemin = (String) joSolicitud.get("ddescarguemin");
        System.err.println("bandera 2");
        int equipos = Integer.parseInt(joSolicitud.get("equipos").toString());
        int vlr_flete = 0;
        
        System.err.println("bandera 3");
        String flete = (String) joSolicitud.get("flete");
        String unidad = "GAL";
        System.err.println("bandera 4");
        String orden = (String) joSolicitud.get("orden");
        String nota_detalle = (String) joSolicitud.get("nota_detalle");
        
        System.err.println("bandera5");
        String kms = (String) joSolicitud.get("kms");
        String ccosto = (String) joSolicitud.get("ccosto");
        int kms_value = Integer.parseInt(joSolicitud.get("kms_value").toString());
        String time = (String) joSolicitud.get("time");
        int time_value = Integer.parseInt(joSolicitud.get("time_value").toString());
        JSONArray remol = (JSONArray) joSolicitud.get("remolques");
        String remolques = "";
        for(int i = 0; i < remol.size(); i++){
            remolques += remol.get(i)+",";
        }
        if(!remolques.equals("")){
            remolques = remolques.substring(0, remolques.length()-1);
        }
        System.err.println("bandera 6");
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject x = Guardar.SaveServicio(inicio, fin, carguemin, carguemax, descarguemin, descarguemax, 
                equipos, tipo_carga, orden, nota_detalle, u.getNit(), vlr_flete, flete, kms, kms_value, time, time_value, unidad, remolques, ccosto);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

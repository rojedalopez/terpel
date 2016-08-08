package servletsList;

import bean.Usuario;
import datos.json.Listas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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

public class list_vehiculos_disp_e extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
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
        JSONArray remol = (JSONArray) joSolicitud.get("remolques");
        String remolques = "";
        for(int i = 0; i < remol.size(); i++){
            remolques += remol.get(i)+",";
        }
        if(!remolques.equals("")){
            remolques = remolques.substring(0, remolques.length()-1);
        }
        
        JSONArray equi = (JSONArray) joSolicitud.get("equipos");
        String equipos = "";
        for(int i = 0; i < equi.size(); i++){
            equipos += equi.get(i)+",";
        }
        if(!equipos.equals("")){
            equipos = equipos.substring(0, equipos.length()-1);
        }
        
        int tipo_carga = 0;
        
        if(joSolicitud.get("tipocarga")!=null){
            if(joSolicitud.get("tipocarga").toString().equals("")){
                tipo_carga = 0;
            }else{
                tipo_carga = Integer.parseInt(joSolicitud.get("tipocarga").toString());
            }
        }
        

        int radio = 100000;
        if(joSolicitud.get("radio")!=null){
            if(joSolicitud.get("radio").toString().equals("")){
                radio = 100000;
            }else{
                radio = Integer.parseInt(joSolicitud.get("radio").toString());
            }
        }
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONArray lista = Listas.listaVehiculosDispEnturne(lat_origen, lng_origen, tipo_carga, remolques, equipos, radio, u.getNit());
                String x = lista.toJSONString();
                out.println(x);
            }else{
                response.sendRedirect("../");
            }
        }catch(Exception e){
            System.out.println("Entro aqui en el error! " + e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(list_vehiculos_disp_e.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

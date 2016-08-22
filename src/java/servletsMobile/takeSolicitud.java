package servletsMobile;

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

public class takeSolicitud extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String id = (String) request.getParameter("id_servicio");
            int equipo_conductor = Integer.parseInt(request.getParameter("equipo_conductor"));
            float latitud = Float.parseFloat(request.getParameter("latitud"));
            float longitud = Float.parseFloat(request.getParameter("longitud"));
            float velocidad = Float.parseFloat(request.getParameter("velocidad"));


            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                String servicio = Guardar.AsignarSolicitud(id, equipo_conductor, latitud, longitud, velocidad, 1);
                out.println(servicio);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(takeSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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

            String id = (String) joSolicitud.get("id_servicio");
            int equipo_conductor = Integer.parseInt(joSolicitud.get("equipo_conductor").toString());
            float latitud = Float.parseFloat(joSolicitud.get("latitud").toString());
            float longitud = Float.parseFloat(joSolicitud.get("longitud").toString());
            float velocidad = Float.parseFloat(joSolicitud.get("velocidad").toString());


            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                String servicio = Guardar.AsignarSolicitud(id, equipo_conductor, latitud, longitud, velocidad, 2);
                JSONObject retorno = new JSONObject();
                if(servicio.equals("0")){
                    retorno.put("mensaje", "error");
                }else if(servicio.equals("1")){
                    retorno.put("mensaje", "nocupo");
                }else{
                    retorno.put("mensaje", "OK");
                    retorno.put("servicio", servicio);
                }
                
                out.println(retorno.toJSONString());
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(takeSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(takeSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

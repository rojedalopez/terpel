
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


public class list_servicios_act extends HttpServlet {


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
        } catch (Exception e) { System.out.println(e.toString()); }
 
        JSONParser parser = new JSONParser();
        JSONObject joEmploye = null;
         
        joEmploye = (JSONObject) parser.parse(sb.toString());
        String servicio = (String) joEmploye.get("servicio");
        String solicitud = (String) joEmploye.get("solicitud");
        String transportadora = (String) joEmploye.get("transportadora");
        String placa = (String) joEmploye.get("placa");
        String conductor = (String) joEmploye.get("conductor");
        String inicio = (String) joEmploye.get("inicio");
        String fin = (String) joEmploye.get("fin");
        
        int carga = -1;
        int estado = -1;
        
        if(joEmploye.get("estado")!=null){
            if(joEmploye.get("estado").toString().equals("")){
                estado = -1;
            }else{
                estado = Integer.parseInt(joEmploye.get("estado").toString());
            }
        }
        
        if(joEmploye.get("carga")!=null){
            if(joEmploye.get("carga").toString().equals("")){
                carga = -1;
            }else{
                carga = Integer.parseInt(joEmploye.get("carga").toString());
            }
        }
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user");
                JSONArray lista;
                if(u.getTipo()==1){
                    lista = Listas.ServiciosActivosByGeneradora(u.getNit(), solicitud, servicio, placa, transportadora, conductor, inicio, fin, carga, estado);
                }else{
                    lista = Listas.ServiciosActivosByTransportadora(u.getNit(), solicitud, servicio, placa, transportadora, conductor, inicio, fin, carga, estado);
                }
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
            Logger.getLogger(list_servicios_act.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(list_servicios_act.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

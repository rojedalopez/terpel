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

public class list_vehiculos_disp extends HttpServlet {
//listaVehiculosDispLogycus
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session =  null;
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
        System.out.println(sb.toString());
        
        String hora = (String) joEmploye.get("hora");
        String solicitud = (String) joEmploye.get("solicitud");
        JSONArray remol = (JSONArray) joEmploye.get("remolques");
        String remolques = "";
        for(int i = 0; i < remol.size(); i++){
            remolques += remol.get(i)+",";
        }
        if(!remolques.equals("")){
            remolques = remolques.substring(0, remolques.length()-1);
        }
        
        float lat = 0;
        float lng = 0;
        
        if(joEmploye.get("lat_punto")!=null){
            if(joEmploye.get("lat_punto").toString().equals("")){
                lat = 0;
            }else{
                lat = Float.parseFloat(joEmploye.get("lat_punto").toString());
            }
        }
        
       
        if(joEmploye.get("lng_punto")!=null){
            if(joEmploye.get("lng_punto").toString().equals("")){
                lng = 0;
            }else{
                lng = Float.parseFloat(joEmploye.get("lng_punto").toString());
            }
        }
        
        int horas = 0;
        if(!hora.equals("")){
            horas = datos.Metodos.calcularHoras(hora);
        }
        
        session = request.getSession(false);
        
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                System.out.println(session.getAttribute("user"));
                if(session.getAttribute("user")!=null){
                    Usuario u = (Usuario)session.getAttribute("user"); 
                    String x = Listas.listaVehiculosDispLogycus(lat, lng, remolques, solicitud , horas, u.getNit()).toJSONString();

                    out.println(x);
                }else{
                    out.println("sesion");
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
            Logger.getLogger(list_enturnes_estados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

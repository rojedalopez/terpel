package servletsList;

import bean.Usuario;
import datos.json.Listas;
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

public class list_solicitudes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
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
        System.out.println(sb.toString());
        int porpage = Integer.parseInt(joEmploye.get("porpage").toString());
        int pageno = Integer.parseInt(joEmploye.get("pageno").toString());
        String q = (String) joEmploye.get("q");
        String cargue = (String) joEmploye.get("cargue");
        String descargue = (String) joEmploye.get("descargue");
        String orden = (String) joEmploye.get("orden");
        
        int carga = -1;
        int estado = -1;
        
        if(joEmploye.get("carga")!=null){
            if(joEmploye.get("carga").toString().equals("")){
                carga = -1;
            }else{
                carga = Integer.parseInt(joEmploye.get("carga").toString());
            }
        }
        if(joEmploye.get("estado")!=null){
            if(joEmploye.get("estado").toString().equals("")){
                estado = -1;
            }else{
                estado = Integer.parseInt(joEmploye.get("estado").toString());
            }
        }
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                out.println(Listas.listaSolicitudes(porpage, pageno, q, cargue, descargue, carga, estado, u.getNit()).toJSONString());
            }
        }
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(list_solicitudes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

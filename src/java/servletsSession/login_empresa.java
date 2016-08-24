
package servletsSession;

import bean.Usuario;
import datos.Aplicacion;
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
import servletsMobile.login;

public class login_empresa extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        JSONObject joUsuario = null;
        joUsuario = (JSONObject) parser.parse(sb.toString());
        
        String nick = (String) joUsuario.get("nick");
        String pass = (String) joUsuario.get("pass");
        
        HttpSession session =  null;

        session = request.getSession(true);

        Usuario u = Aplicacion.obtenerUsuario(nick, pass, 2);
        
        JSONObject json = new JSONObject();
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(u!=null){
                if(u.getMensaje().equals("true")){
                    session.setAttribute("user", u);
                    session.setAttribute("cod", u.getCodigo());
                    session.setAttribute("session", true);
                    String url = "";
                    boolean url_forward = false;
                    
                    if(session.getAttribute("url_forward")!=null){
                        url_forward = true;
                        url = session.getAttribute("url_forward")+"";
                    }
                    
                    json.put("mensaje", "OK");
                    session.setAttribute("usr", u.getNombre() + " " + u.getApellido());
                    if(u.getRol()==3){
                        if(u.getTipo()==1){
                            json.put("url", (url_forward)?url:"/empresa/servicios.jsp");
                        }else{
                            json.put("url", (url_forward)?url:"/transporter/servicios.jsp");
                        }
                    }else{
                        json.put("url", (url_forward)?url:"/empresa/servicios.jsp");
                    }
                    
                 }else{
                    json.put("mensaje", "badinfo");
                }
            }else{
                json.put("mensaje", "nouser");
            }
            
            out.print(json.toJSONString());
        }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(login_empresa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

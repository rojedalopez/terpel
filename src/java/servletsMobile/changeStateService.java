package servletsMobile;

import static datos.save.Guardar.CambioEstadoServicio;
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


public class changeStateService extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        //servicio
        //estado
        String servicio = (String) request.getParameter("servicio");
        int estado = Integer.parseInt(request.getParameter("estado"));
        int equipo_conductor = Integer.parseInt(request.getParameter("equipo_conductor"));
        int tipo = Integer.parseInt(request.getParameter("tipo_serv"));
        int valor = Integer.parseInt(request.getParameter("valor"));
        
        try (PrintWriter out = response.getWriter()) {
            JSONObject jSONObject = new JSONObject();
            /* TODO output your page here. You may use following sample code. */
            int resultado = CambioEstadoServicio(servicio, estado, equipo_conductor, "", tipo, valor);
            if(resultado<=-1){
                jSONObject.put("mensaje", false);
            }else{
                jSONObject.put("mensaje", true);
                jSONObject.put("retorno", resultado);
            }
            
            out.println(jSONObject.toJSONString());
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(changeStateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeStateService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(changeStateService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(changeStateService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

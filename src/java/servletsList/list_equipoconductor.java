
package servletsList;

import bean.Usuario;
import datos.json.Listas;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;


public class list_equipoconductor extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONArray objeto = Listas.listaEquiposConductoresByPropietario(u.getNit(), true);
                out.println(objeto.toJSONString());
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
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
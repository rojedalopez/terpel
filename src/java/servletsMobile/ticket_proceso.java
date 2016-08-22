
package servletsMobile;

import datos.save.Guardar;
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

public class ticket_proceso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        int equipo_conductor = Integer.parseInt(request.getParameter("equipo_conductor"));
        String operacion = (String)request.getParameter("operacion");
        int tipo_serv = Integer.parseInt(request.getParameter("tipo_serv"));
        
        try (PrintWriter out = response.getWriter()) {
            String retorno = Guardar.InsertTicketProceso(equipo_conductor, operacion, tipo_serv);
            System.out.println(retorno);
            JSONObject json = new JSONObject();
            if(!retorno.equals("-1") && !retorno.equals("0")){
                String[] vector = retorno.split("\\|");
                json.put("mensaje", 1);
                json.put("ticket", vector[0]);
                json.put("turno", vector[1]);
                json.put("fecha", vector[2]);
            }else{
                json.put("mensaje", Integer.parseInt(retorno));
            }
            
            out.println(json.toJSONString());
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ticket_proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ticket_proceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ticket_proceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ticket_proceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

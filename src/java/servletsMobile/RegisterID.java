
package servletsMobile;

import datos.save.Guardar;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegisterID extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, InvalidKeyException {
        response.setContentType("text/html;charset=UTF-8");
        String id = (String) request.getParameter("id_register");
        String imei = (String) request.getParameter("imei");
        String cod = (String) request.getParameter("cod");
        
        try (PrintWriter out = response.getWriter()) {
            System.out.println(id);
            System.out.println(imei);
            System.out.println(cod);
            out.print(Guardar.InsertRegistro(id, imei, cod));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | InvalidKeyException ex) {
            Logger.getLogger(RegisterID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | InvalidKeyException ex) {
            Logger.getLogger(RegisterID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

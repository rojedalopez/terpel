package servletsSession;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class forward extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String url_ = request.getParameter("url_forward");
        System.out.println(url_);
        HttpSession session =  null;
 
        session = request.getSession(true);
        
        try (PrintWriter out = response.getWriter()) {
            if(session.getAttribute("user")!=null){
                response.sendRedirect(url_);
            }else{
                session.setAttribute("url_forward", url_);
                response.sendRedirect("/");
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

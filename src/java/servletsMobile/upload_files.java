package servletsMobile;

import datos.save.Guardar;
import static datos.save.Guardar.CambioEstadoServicio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.simple.JSONObject;

@MultipartConfig
public class upload_files extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); 
        int tipo =  Integer.parseInt(request.getParameter("tipo"));
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String filename = getFilename(filePart);
        String[] vector_nombre = filename.split("\\.");
        String nuevo_nombre = vector_nombre[0]+"_"+tipo+"."+vector_nombre[1];
        InputStream filecontent = filePart.getInputStream();
        String c = "";
        
        try{
            c = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();    
            System.out.println(c);
            File f = new File(c+"/upload/"+nuevo_nombre).getCanonicalFile();
            FileOutputStream ous = new FileOutputStream(f);
            int dato = filecontent.read();
            while(dato != -1){
                ous.write(dato);
                dato = filecontent.read();
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
        HttpSession session =  null;
 
        session = request.getSession(false);
         
        try (PrintWriter out = response.getWriter()) {
            JSONObject jSONObject = new JSONObject();
            /* TODO output your page here. You may use following sample code. */
            int resultado = CambioEstadoServicio(vector_nombre[0], (tipo==1)?5:10, 0, filename);
            if(resultado<=-1){
                jSONObject.put("mensaje", false);
            }else{
                jSONObject.put("mensaje", true);
                jSONObject.put("retorno", resultado);
            }

        }
    }
    
    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(upload_files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(upload_files.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

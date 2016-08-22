package servletsSave;

import bean.Usuario;
import datos.save.Guardar;
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

public class saveVehiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, ClassNotFoundException, SQLException {
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
        JSONObject joVehiculo = null;
        System.out.println(sb.toString());
        joVehiculo = (JSONObject) parser.parse(sb.toString());
        
        String placa = (String) joVehiculo.get("placa");
        int n_tipocarga = Integer.parseInt(joVehiculo.get("n_tipocarga").toString());
        int n_tipoequipo = Integer.parseInt(joVehiculo.get("n_tipoequipo").toString());
        String lic_transito = (String) joVehiculo.get("lic_transito");
        String trailer = (String) joVehiculo.get("trailer");
        String lic_transito_r = (String) joVehiculo.get("lic_transito_r");
        int n_remolque = Integer.parseInt(joVehiculo.get("n_remolque").toString());
        int capacidad = Integer.parseInt(joVehiculo.get("capacidad").toString());
        String unidad = (String) joVehiculo.get("unidad");
        String marca = (String) joVehiculo.get("marca");
        String modelo = (String) joVehiculo.get("modelo");
        String referencia = (String) joVehiculo.get("referencia");
        String poliza = (String) joVehiculo.get("poliza");
        String compania = (String) joVehiculo.get("compania");
        String exp_poliza = (String) joVehiculo.get("exp_poliza");
        String vence_poliza = (String) joVehiculo.get("vence_poliza");
        String soat = (String) joVehiculo.get("soat");
        String exp_soat = (String) joVehiculo.get("exp_soat");
        String vence_soat = (String) joVehiculo.get("vence_soat");
        String tecno = (String) joVehiculo.get("tecno");
        String exp_tecno = (String) joVehiculo.get("exp_tecno");
        String vence_tecno = (String) joVehiculo.get("vence_tecno");
        String poliza_hc = (String) joVehiculo.get("poliza_hc");
        String exp_poliza_hc = (String) joVehiculo.get("exp_poliza_hc");
        String vence_poliza_hc = (String) joVehiculo.get("vence_poliza_hc");
        String compania_hc = (String) joVehiculo.get("compania_hc");
        
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                    boolean operacion = Guardar.InsertVehiculo(placa, n_tipocarga, n_tipoequipo, lic_transito, 
                            trailer, lic_transito_r, n_remolque, capacidad, unidad, marca, modelo, referencia, 
                            poliza, compania, exp_poliza, vence_poliza, soat, exp_soat, vence_soat, tecno, 
                            exp_tecno, vence_tecno, poliza_hc, compania_hc, exp_poliza_hc, vence_poliza_hc, u.getNit());
                    out.print(operacion);
                    
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(saveVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(saveVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(saveVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

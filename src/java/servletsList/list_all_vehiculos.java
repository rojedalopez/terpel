/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class list_all_vehiculos extends HttpServlet {

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
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
        JSONObject joSolicitud = null;
        System.out.println(sb.toString());
        joSolicitud = (JSONObject) parser.parse(sb.toString());
        
        JSONObject origen = (JSONObject) joSolicitud.get("addressOrigin");
        float lat_origen = Float.parseFloat(origen.get("lat").toString());
        float lng_origen = Float.parseFloat(origen.get("lng").toString());
        
        int tipo_carga = 0;
        
        if(joSolicitud.get("carga")!=null){
            if(joSolicitud.get("carga").toString().equals("")){
                tipo_carga = 0;
            }else{
                tipo_carga = Integer.parseInt(joSolicitud.get("carga").toString());
            }
        }
        
        int radio = 100000;
        if(joSolicitud.get("radio")!=null){
            if(joSolicitud.get("radio").toString().equals("")){
                radio = 100000;
            }else{
                radio = Integer.parseInt(joSolicitud.get("radio").toString());
            }
        }
        boolean todo=Boolean.parseBoolean(joSolicitud.get("todos").toString());
        String solicitud = (String)joSolicitud.get("solicitud");
        
        HttpSession session =  null;
 
        session = request.getSession(false);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(session.getAttribute("user")!=null){
                Usuario u = (Usuario)session.getAttribute("user"); 
                JSONObject objeto = Listas.listaVehiculosBusquedas(lat_origen, lng_origen, tipo_carga, (float)radio/1000, u.getCodigo(), todo, solicitud);
                JSONArray lista = (JSONArray) objeto.get("lista");
                String x = lista.toJSONString();
                out.println(x);
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
        try {
            processRequest(request, response);
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(list_vehiculos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

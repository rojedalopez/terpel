package datos.save;

import bean.Servicio;
import bean.Solicitud;
import datos.Aplicacion;
import static datos.Aplicacion.conexion;
import datos.Mails;
import datos.Metodos;
import java.security.InvalidKeyException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Guardar {
    
    public static JSONObject SaveServicio(String origen, String destino, String fecha_cargue_min, String fecha_cargue_max, String fecha_descargue_min, String fecha_descargue_max, 
            int no_equipos, int tipo_equipos, String orden, String nota_detalle, String empresa, float flete, 
            String txt_flete, String kms, float vlr_kms, String time, float vlr_time, String unidad, String remolques, String ccosto) throws ClassNotFoundException, SQLException{
            boolean b=false;
            Solicitud sol = new Solicitud();
            Connection conn=null;
            PreparedStatement insertar=null;
            JSONObject retorno = new JSONObject();
        
            conn=Aplicacion.conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_solicitude(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)};")) {
                cs.setString(1, origen);
                cs.setString(2, destino);
                cs.setString(3, fecha_cargue_min);
                cs.setString(4, fecha_cargue_max);
                cs.setString(5, fecha_descargue_min);
                cs.setString(6, fecha_descargue_max);
                cs.setInt(7, no_equipos);
                cs.setInt(8, tipo_equipos);
                cs.setString(9, orden);
                cs.setString(10, nota_detalle);
                cs.setString(11, empresa);
                cs.setFloat(12, flete);
                cs.setString(13, txt_flete);
                cs.setString(14, kms);
                cs.setFloat(15, vlr_kms);
                cs.setString(16, time);
                cs.setFloat(17, vlr_time);
                cs.setString(18, unidad);
                cs.setString(19, remolques);
                cs.setString(20, ccosto);
                
                cs.registerOutParameter(21, Types.VARCHAR);
                cs.executeQuery();

                String num_solicitud = cs.getString(21);
                sol.setId(num_solicitud);
                
                if(!num_solicitud.equals("0")){
                    Solicitud solicitud = datos.get.Objetos.DatosSolicitud(num_solicitud);
                    List<String> correos = datos.get.Listas.listaMailsDespachadores(empresa);
                    Mails.SendMailSolicitudTransportadoras(correos, solicitud);
                    retorno.put("id_solicitud", num_solicitud);
                    retorno.put("mensaje", "OK");
                    return retorno;
                }else{
                    retorno.put("mensaje", "Error");
                    return retorno;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en SaveServicio");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en SaveServicio");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            
            retorno.put("mensaje", "Error");
            return retorno;

    }
    
    public static JSONObject SaveSolProgramada(int anio, int mes, int dia, String fecha_cargue_min, String fecha_cargue_max, String fecha_descargue_min, String fecha_descargue_max, 
            int no_equipos, int tipo_cargue, String ccosto, String origen, String destino) throws ClassNotFoundException, SQLException{
            boolean b=false;
            Solicitud sol = new Solicitud();
            Connection conn=null;
            PreparedStatement insertar=null;
            JSONObject retorno = new JSONObject();
        
            conn=Aplicacion.conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_solicitud_dia(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)};")) {
                cs.setString(1, origen);
                cs.setString(2, destino);
                cs.setString(3, fecha_cargue_min);
                cs.setString(4, fecha_cargue_max);
                cs.setString(5, fecha_descargue_min);
                cs.setString(6, fecha_descargue_max);
                cs.setInt(7, no_equipos);
                cs.setInt(8, tipo_cargue);
                cs.setString(9, ccosto);
                cs.setInt(10, mes);
                cs.setInt(11, anio);
                cs.setInt(12, dia);
                
                cs.registerOutParameter(13, Types.INTEGER);
                cs.executeQuery();

                int num_solicitud = cs.getInt(13);
                
                if(num_solicitud!=0){
                    retorno.put("id", num_solicitud);
                    retorno.put("mensaje", "OK");
                    return retorno;
                }else{
                    retorno.put("mensaje", "Error");
                    return retorno;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en SaveServicio");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en SaveServicio");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            
            retorno.put("mensaje", "Error");
            return retorno;

    }
    
    public static JSONObject SaveServicioEnturne(String origen, String n_origen, float lat_origen, float lng_origen, String destino, String n_destino,
            float lat_destino, float lng_destino, String fecha_cargue, String fecha_descargue, int equipo_conductor, 
            String guia, String nota, String empresa, String kms, String time, String registro, String cargue,
            String nombre, String url, String n_cargue) throws ClassNotFoundException, SQLException{
            boolean b=false;
            Solicitud sol = new Solicitud();
            Connection conn=null;
            PreparedStatement insertar=null;
            JSONObject retorno = new JSONObject();
        
            conn=Aplicacion.conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_servicio(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)};")) {
                cs.setString(1, origen);
                cs.setString(2, destino);
                cs.setString(3, fecha_cargue);
                cs.setString(4, fecha_descargue);
                cs.setInt(5, equipo_conductor);
                cs.setString(6, guia);
                cs.setString(7, nota);
                cs.setString(8, empresa);
                cs.setString(9, kms);
                cs.setString(10, time);
                cs.setString(11, cargue);
                
                sol.setOrigen(n_origen);
                sol.setLat_origen(lat_origen);
                sol.setLng_origen(lng_origen);
                sol.setDestino(n_destino);
                sol.setLat_destino(lat_destino);
                sol.setLng_destino(lng_destino);
                sol.setCarguemin(fecha_cargue);
                sol.setDescarguemin(fecha_descargue);
               
                sol.setOrden(guia);
                sol.setNota_detalle(nota);
                sol.setNombre_cargue(n_cargue);
                                
                cs.registerOutParameter(12, Types.VARCHAR);
                cs.executeQuery();

                String num_solicitud = cs.getString(12);
                sol.setId(num_solicitud);
                
                if(!num_solicitud.equals("0")){
                    Metodos.EnvioNotificacionEnturne(sol, registro, empresa, nombre, url);
                    retorno.put("id_solicitud", num_solicitud);
                    retorno.put("mensaje", "OK");
                    return retorno;
                }else{
                    retorno.put("mensaje", "Error");
                    return retorno;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en SaveServicioEnturne");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en SaveServicioEnturne");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            
            retorno.put("mensaje", "Error");
            return retorno;

    }
    
    public static boolean InsertCompany(String nit, String rzn_social, String dir, String tel, String url,
            int tipo, String codigo) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.save_register_company(?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, nit);
                cs.setString(2, rzn_social);
                cs.setString(3, dir);
                cs.setString(4, tel);
                cs.setString(5, url);
                cs.setInt(6, tipo);
                cs.setString(7, codigo);
                cs.registerOutParameter(8, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(8);
                
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertCompany");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertCompany");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    
    public static boolean InsertVehiculo(String placa, int tipocarga, int tipoequipo, String lic_transito, 
            String placa_r, String lic_transito_r, int tiporemolque, int capacidad, String unidad, 
            String marca, String modelo, String referencia, String poliza, String compania, String exp_pol,
            String vence_pol, String soat, String exp_soat, String vence_soat, String tecno, String exp_tecno,
            String vence_tecno, String poliza_hc, String compania_hc, String exp_pol_hc, String vence_pol_hc,
            String empresa) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_vehiculo(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, placa);
                cs.setString(2, lic_transito);
                cs.setInt(3, tipocarga);
                cs.setInt(4, tipoequipo);
                cs.setString(5, placa_r);
                cs.setString(6, lic_transito_r);
                cs.setInt(7, tiporemolque);
                cs.setInt(8, capacidad);
                cs.setString(9, unidad);
                cs.setString(10, marca);
                cs.setString(11, modelo);
                cs.setString(12, referencia);
                cs.setString(13, poliza);
                cs.setString(14, compania);
                cs.setString(15, exp_pol);
                cs.setString(16, vence_pol);
                cs.setString(17, poliza_hc);
                cs.setString(18, compania_hc);
                cs.setString(19, exp_pol_hc);
                cs.setString(20, vence_pol_hc);
                cs.setString(21, soat);
                cs.setString(22, exp_soat);
                cs.setString(23, vence_soat);
                cs.setString(24, tecno);
                cs.setString(25, exp_tecno);
                cs.setString(26, vence_tecno);
                cs.setString(27, empresa);
                cs.registerOutParameter(28, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(28);
                
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertVehiculo");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertVehiculo");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static boolean InsertUsuarioEmpresa(String correo, int rol, String nit, String nombre,
            String apellido, String contrasena, String url) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        String hash = Metodos.RandomString(25, false);
        String pass = Metodos.sha512(contrasena, hash);
        String cod = Metodos.RandomString(20, false);
        String token = Metodos.RandomString(50, false);
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.save_register_user_empresa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, cod);
                System.out.println(cod);
                cs.setString(2, correo);
                System.out.println(correo);
                cs.setInt(3, rol);
                System.out.println(rol);
                cs.setString(4, nit);
                System.out.println(nit);
                cs.setString(5, nombre);
                System.out.println(nombre);
                cs.setString(6, apellido);
                System.out.println(apellido);
                cs.setString(7, hash);
                System.out.println(hash);
                cs.setString(8, pass);
                System.out.println(pass);
                cs.setString(9, token);
                System.out.println(token);
                cs.setString(10, url);
                System.out.println(url);
                cs.registerOutParameter(11, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(11);
                System.out.println(retorno);
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertUsuarioEmpresa");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertUsuarioEmpresa");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static boolean InsertConductor(String correo, String tipo_doc, String doc, String num_lic,
            String fecha_exp, String fecha_vence, String contrasena, String nomb, String apellido, String telefono, 
            String direccion, String empresa, String path) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        String hash = Metodos.RandomString(25, false);
        String cont = contrasena;
        if(contrasena.isEmpty()){
            cont = Metodos.RandomString(8, false);
        }
        String pass = Metodos.sha512(cont, hash);
        String cod = Metodos.RandomString(25, false);
        String token = Metodos.RandomString(50, false);
        System.out.println(pass);
        System.out.println(cod);
        System.out.println(token);
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_conductor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, cod);
                cs.setString(2, tipo_doc);
                cs.setString(3, doc);
                cs.setString(4, num_lic);
                cs.setString(5, fecha_exp);
                cs.setString(6, fecha_vence);
                cs.setString(7, nomb);
                cs.setString(8, apellido);
                cs.setString(9, telefono);
                cs.setString(10, direccion);
                cs.setString(11, correo);
                cs.setString(12, hash);
                cs.setString(13, pass);
                cs.setString(14, empresa);
                cs.registerOutParameter(15, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(15);
                
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertConductor");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertConductor");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
     public static boolean UpdateConductor(String correo, String tipo_doc, String doc, String num_lic,
            String fecha_exp, String fecha_vence, String nomb, String apellido, String telefono, 
            String direccion, String empresa) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        String cod = Metodos.RandomString(25, false);
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.edit_conductor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, cod);
                cs.setString(2, tipo_doc);
                cs.setString(3, doc);
                cs.setString(4, num_lic);
                cs.setString(5, fecha_exp);
                cs.setString(6, fecha_vence);
                cs.setString(7, nomb);
                cs.setString(8, apellido);
                cs.setString(9, telefono);
                cs.setString(10, direccion);
                cs.setString(11, correo);
                cs.setString(12, empresa);
                cs.registerOutParameter(13, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(13);
                
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en UpdateConductor");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en UpdateConductor");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
     
    public static boolean AsignTurno(String ticket, int zona, int bahia, String fecha, String nota) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;

        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.asignTurno(?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, ticket);
                cs.setInt(2, zona);
                cs.setInt(3, bahia);
                cs.setString(4, fecha);
                cs.setString(5, nota);
                cs.registerOutParameter(6, Types.VARCHAR);
                cs.executeQuery();
                
                String retorno = cs.getString(6);
                
                if(!retorno.equals("false")){
                    String[] vector = retorno.split("\\|");
                    Metodos.EnvioNotificacion(vector[0], vector[1], vector[2], vector[3], vector[4], vector[5], vector[6], Integer.parseInt(vector[7]));
                    return true;
                }else{
                    return false;
                }
                
            }catch (SQLException e) {
                System.out.println("error SQLException en AsignTurno");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en AsignTurno");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            
            return false;
    }
    
    public static boolean InsertRegistro(String id, String imei, String cod, int tipo) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_device_id(?, ?, ?, ?)}")) {
                cs.setString(1, id);
                cs.setString(2, imei);
                cs.setString(3, cod);
                cs.setInt(4, tipo);
                
                cs.executeQuery();

                return true;
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertRegistro");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertRegistro");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static boolean InsertEquipoConductor(int id, String equipo, String conductor) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.asign_equipo_conductor(?, ?, ?, ?)}")) {
                cs.setInt(1, id);
                cs.setString(2, equipo);
                cs.setString(3, conductor);
                cs.registerOutParameter(4, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(4);
                if(retorno == 1){
                    return true;
                }else{  
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertEquipoConductor");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertEquipoConductor");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    
    public static boolean DispEquipoConductor(int id, boolean disponible) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.disp_equipo_conductor(?, ?, ?)}")) {
                cs.setInt(1, id);
                cs.setInt(2, (disponible)?1:0);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(3);
                if(retorno == 1){
                    return true;
                }else{  
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertEquipoConductor");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertEquipoConductor");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
     public static boolean InsertPunto(String id, String nit, String desc, String nota, float lat, float lng) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_punto(?, ?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, id);
                cs.setString(2, nit);
                cs.setString(3, desc);
                cs.setString(4, nota);
                cs.setFloat(5, lat);
                cs.setFloat(6, lng);
                cs.registerOutParameter(7, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(7);
                
                if(retorno==1){
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertPunto");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertPunto");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
     
    public static boolean InsertCCosto(String id, String desc, String empresa)  throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_centrocosto(?, ?, ?, ?)}")) {
                cs.setString(1, id);
                cs.setString(2, desc);
                cs.setString(3, empresa);
                
                cs.registerOutParameter(4, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(4);
                
                if(retorno==1){
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertZona");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertZona");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
     
    public static boolean InsertZona(int id, int punto, String desc, String nota) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_zona(?, ?, ?, ?, ?)}")) {
                cs.setInt(1, id);
                cs.setInt(2, punto);
                cs.setString(3, desc);
                cs.setString(4, nota);
                cs.registerOutParameter(5, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(5);
                
                if(retorno==1){
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertZona");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertZona");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static boolean InsertBahia(int id, int zona, String desc, String nota) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_bahia(?, ?, ?, ?, ?)}")) {
                cs.setInt(1, id);
                cs.setInt(2, zona);
                cs.setString(3, desc);
                cs.setString(4, nota);
                cs.registerOutParameter(5, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(5);
                
                if(retorno==1){
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertBahia");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertBahia");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static boolean finishTurno(String ticket, String nota) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.finishTurno(?, ?, ?)}")) {
                cs.setString(1, ticket);
                cs.setString(2, nota);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.executeQuery();
                
                int retorno = cs.getInt(3);
                
                if(retorno==1){
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en finishTurno");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en finishTurno");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    public static String InsertTicketProceso(int equipo_conductor, String operacion, int tipo_serv) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.giveEnturne(?, ?, ?, ?)}")) {
                cs.setString(1, operacion);
                cs.setInt(2, equipo_conductor);
                cs.setInt(3, tipo_serv);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.executeQuery();

                String retorno = cs.getString(4);

                return retorno;
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertTicketProceso");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertTicketProceso");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return "";

    }
    
    public static boolean InsertArchivo(String id, String url) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.upload_cumplido(?, ?, ?)}")) {
                cs.setString(1, id);
                cs.setString(2, url);
                cs.registerOutParameter(3, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(3);
                
                if(retorno==1){
                    //Mails.SendMail(correo, token, "CONFIRMACIÓN DE CUENTA", "ACTIVAR");
                    return true;
                }else{
                    return false;
                }
                

            }catch (SQLException e) {
                System.out.println("error SQLException en InsertArchivo");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en InsertArchivo");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return false;

    }
    
    
    public static String AsignarSolicitud(String solicitud, int equipo_conductor, float latitud, float 
            longitud, float velocidad, int tipo) throws ClassNotFoundException, SQLException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        
        conn=conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.take_solicitude(?, ?, ?, ?, ?, ?)}")) {
                cs.setString(1, solicitud);
                cs.setInt(2, equipo_conductor);
                cs.setFloat(3, latitud);
                cs.setFloat(4, longitud);
                cs.setFloat(5, velocidad);
                cs.registerOutParameter(6, Types.VARCHAR);
                
                cs.executeQuery();
                
                String retorno = cs.getString(6);
                
                //SendMailServiciosGeneradores
                
                if(!retorno.equals("0") && !retorno.equals("1")){
                    if(tipo==1){
                            Servicio servicio = datos.get.Objetos.DatosServicio(retorno);
                            List<String> lista = datos.get.Listas.listaMailsDespachadoresEmpresa(servicio.getGenerador());
                            datos.Mails.SendMailServiciosGeneradores(lista, servicio);
                    }else{
                            System.err.println(retorno);
                            Servicio servicio = datos.get.Objetos.DatosServicio(retorno);
                            System.err.println("paso obtencion de servicio");
                            List<String> lista = datos.get.Listas.listaMailsDespachadoresEmpresa(servicio.getNit_generador());
                            System.err.println("paso lista de despachadores");
                            datos.Mails.SendMailServiciosGeneradores(lista, servicio);
                            System.err.println("paso lista envio el mail");
                            JSONArray TO = new JSONArray();
                            TO.add(servicio.getReg_logycus());
                            System.err.println("agrego apra quien va");
                            datos.Metodos.EnvioNotificacion(servicio, TO);
                            System.err.println("envio y temino proceso");
                    }
                }
                
                return retorno;
                

            }catch (SQLException e) {
                System.out.println("error SQLException en AsignarSolicitud");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en AsignarSolicitud");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return "0";

    }
    
    public static void Envio(String retorno) throws SQLException{
            
    }
   
    
    public static int CambioEstadoServicio(String servicio, int estado, int equipo_conductor, String imagen, int tipo, int valor) throws ClassNotFoundException, SQLException{
        Connection conn=null;
        PreparedStatement insertar=null;
        conn=conexion();
        String pp = "";
        if(tipo==1){
            pp = "{CALL logycus360.change_state_service(?, ?, ?, ?, ?, ?)}";
        }else{
            pp = "{CALL logycus360.change_state_serviceenturne(?, ?, ?, ?, ?, ?)}";
        }
            try (CallableStatement cs = conn.prepareCall(pp)) {
                cs.setString(1, servicio);
                cs.setInt(2, estado);
                cs.setInt(3, equipo_conductor);
                cs.setString(4, imagen);
                cs.setInt(5, valor);
                cs.registerOutParameter(6, Types.INTEGER);
                
                cs.executeQuery();
                
                int retorno = cs.getInt(6);
                
                return retorno;
                

            }catch (SQLException e) {
                System.out.println("error SQLException en CambioEstadoServicio");
                System.out.println(e.toString());
            }catch (Exception e){
                System.out.println("error Exception en CambioEstadoServicio");
                System.out.println(e.toString());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return -1;

    }
    
}

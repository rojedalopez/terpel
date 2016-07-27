
package datos.json;

import static datos.Aplicacion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Listas {
    static SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
    public static JSONObject listaVehiculos(float lat, float lng, int tipo) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONArray TOs=new JSONArray();   
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_equipoconductor, cod_conductor, ec.plca_equipo, lat_equipoconductor, long_equipoconductor, " +
                "velo_equipoconductor, ult_actualizacion, id_tipocarga, regs_imei_conductor "+
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo "+
                "WHERE id_tipocarga = "+tipo+" AND disp_equipoconductor = 1 AND " +
                "(acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<100;";	        
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("cod", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    posicion.add(datos.getFloat(4));
                    posicion.add(datos.getFloat(5));
                    objeto.put("position", posicion);
                    objeto.put("velocidad", datos.getFloat(6));                    
                    objeto.put("ult_reporte", formateador.format(formateador.parse(datos.getString(7))));
                    TOs.add(datos.getString(9));
                    lista.add(objeto);
                }
                
                retorno.put("lista",lista);
                retorno.put("TOs", TOs);
            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return retorno;
    }
    
    public static JSONObject listaVehiculosBusquedas(float lat, float lng, int tipo, float zona, String empresa, 
            boolean todo, String solicitud) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ec.id_equipoconductor, cod_conductor, ec.plca_equipo, lat_equipoconductor, long_equipoconductor, " +
                "velo_equipoconductor, ult_actualizacion, eq.id_tipocarga, regs_imei_conductor, disp_equipoconductor, "+
                "se.id_solicitud, se.id_servicio, so.orig_solicitud, so.dest_solicitud, CASE " +
                "WHEN disp_equipoconductor = 0 THEN 'css/images/ic_truckicon_ocup.png' " +
                "WHEN disp_equipoconductor = 1 THEN 'css/images/ic_truckicon_disp.png' END AS icono "+
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo " +
                "LEFT JOIN tblServicio AS se ON ec.id_equipoconductor = se.id_equipoconductor " +
                "LEFT JOIN tblSolicitud AS so ON se.id_solicitud = so.id_solicitud "+
                "WHERE " ;
                if(tipo!=0){
                    instruccion += " eq.id_tipocarga = "+tipo+" AND ";
                }
                instruccion += "(acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<"+zona;	
                
                if(solicitud.isEmpty()){
                    instruccion += " AND (disp_equipoconductor = 1 OR nit_empresa = '"+empresa+"') ";
                }else{
                    instruccion += " AND se.id_solicitud = "+solicitud;
                }
                
                if(!todo){
                    instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -17 HOUR);";
                }
                
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("cod", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    posicion.add(datos.getFloat(4));
                    posicion.add(datos.getFloat(5));
                    objeto.put("position", posicion);
                    objeto.put("velocidad", datos.getFloat(6));                    
                    objeto.put("ult_reporte", formateador.format(formateador.parse(datos.getString(7))));
                    objeto.put("carga", datos.getString(8));
                    objeto.put("estado", datos.getString(10));
                    objeto.put("solicitud", datos.getString(11));
                    objeto.put("servicio", datos.getString(12));
                    objeto.put("origen", datos.getString(13));
                    objeto.put("destino", datos.getString(14));
                    objeto.put("icono", datos.getString(15));
                    lista.add(objeto);
                }
                
                retorno.put("lista",lista);
            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return retorno;
    }
    
    public static JSONArray listaVehiculosBySolicitud(String id) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ec.plca_equipo, CONCAT(nomb_conductor, ' ', apll_conductor), tipo_doc_conductor, doc_conductor,  " +
                "tipo_lic_conductor, num_lic_conductor, tel_conductor, img_conductor, CONCAT(nomb_prop_equipo, ' ', apll_prop_equipo), " +
                "polz_segu_equipo, t.desc_tipocarga, turn_carg_servicio, turn_desc_servicio, id_servicio FROM tblServicio AS s INNER JOIN tblEquipoConductor AS ec ON s.id_equipoconductor = ec.id_equipoconductor  " +
                "INNER JOIN tblConductor AS c ON c.cod_conductor = ec.cod_conductor  " +
                "INNER JOIN tblEquipo AS e ON e.plca_equipo = ec.plca_equipo INNER JOIN tblTipoCarga AS t ON t.id_tipocarga = e.id_tipocarga " +
                "WHERE id_solicitud = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("servicio", datos.getString(14));
                    objeto.put("placa", datos.getString(1));
                    objeto.put("nombre", datos.getString(2));
                    objeto.put("tipo_doc", datos.getString(3));
                    objeto.put("doc", datos.getString(4));
                    objeto.put("tipo_lic", datos.getString(5));
                    objeto.put("lic",datos.getString(6));
                    objeto.put("telefono",datos.getString(7));
                    objeto.put("imagen",datos.getString(8));
                    objeto.put("propietario",datos.getString(9));
                    objeto.put("poliza",datos.getString(10));
                    objeto.put("carga",datos.getString(11));
                    objeto.put("turno_cargue",datos.getString(12));
                    objeto.put("turno_descargue",datos.getString(13));
                    objeto.put("fotos",listaFotosByServicio(datos.getString(14)));
                    lista.add(objeto);
                }
                System.out.println(lista.toJSONString());
            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
           System.out.println();
        return lista;
    }
    
    public static JSONArray listaFotosByServicio(String id) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_fotos, url_fotos, fech_fotos, desc_fotos, desc_tipofoto " +
                "FROM tblFotos AS f INNER JOIN tblTipoFoto AS t ON f.id_tipofoto = t.id_tipofoto " +
                "WHERE id_servicio = ?;";	        

                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("url", datos.getString(2));
                    objeto.put("fecha", datos.getString(3));
                    objeto.put("desc", datos.getString(4));
                    objeto.put("tipo_foto", datos.getString(5));
                    lista.add(objeto);
                }
                System.out.println(lista.toJSONString());
            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
           System.out.println();
        return lista;
    }
    
    public static JSONObject infoServicio(String id) throws SQLException{
        JSONObject objeto= new JSONObject();      
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT s.id_servicio, s.id_solicitud, o.orig_solicitud, o.dest_solicitud, o.fech_carg_solicitud,\n" +
                "o.fech_desc_solicitud, tipo_doc_conductor, tipo_lic_conductor, num_lic_conductor, doc_conductor,\n" +
                "CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, e.plca_equipo, \n" +
                " img_conductor, CONCAT(nomb_prop_equipo, ' ', apll_prop_equipo), \n" +
                "polz_segu_equipo, t.desc_tipocarga, turn_carg_servicio, turn_desc_servicio, img_cump_servicio, img_remi_servicio \n" +
                "FROM tblServicio AS s INNER JOIN tblSolicitud AS o ON s.id_solicitud = o.id_solicitud\n" +
                "INNER JOIN tblEquipoConductor AS ec ON s.id_equipoconductor = ec.id_equipoconductor  \n" +
                "INNER JOIN tblConductor AS c ON c.cod_conductor = ec.cod_conductor  \n" +
                "INNER JOIN tblEquipo AS e ON e.plca_equipo = ec.plca_equipo \n" +
                "INNER JOIN tblTipoCarga AS t ON t.id_tipocarga = e.id_tipocarga \n" +
                "WHERE id_servicio = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    objeto.put("origen", datos.getString(3));
                    objeto.put("destino", datos.getString(4));
                    objeto.put("cargue", datos.getString(5));
                    objeto.put("descargue",datos.getString(6));
                    objeto.put("tipo_doc",datos.getString(7));
                    objeto.put("tipo_lic",datos.getString(8));
                    objeto.put("licencia",datos.getString(9));
                    objeto.put("documento",datos.getString(10));
                    objeto.put("nombre",datos.getString(11));
                    objeto.put("telefono",datos.getString(12));
                    objeto.put("placa",datos.getString(13));
                    objeto.put("imagen_conductor", datos.getString(14));
                    objeto.put("propietario", datos.getString(14));
                    objeto.put("poliza", datos.getString(14));
                    objeto.put("tipo_carga", datos.getString(14));
                    objeto.put("imagen_cumplido", datos.getString(14));
                    objeto.put("imagen_remision", datos.getString(14));
                    
                }
                
            }catch (SQLException e) {
                    System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return objeto;
    }
    
    public static JSONObject listaSolicitudes(int porpage, int pageno, String q, String cargue, String descargue, int carga, int estado, String nit) throws SQLException{
        JSONArray lista=new JSONArray();
        JSONObject retorno = new JSONObject();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                int desde = ((pageno-1)*porpage);
                String instruccion="SELECT  id_solicitud, s.id_estados, e.desc_estados, orden_solicitud, fech_carg_solicitud, fech_desc_solicitud, orig_solicitud, dest_solicitud ";
                instruccion+=      "FROM tblSolicitud AS s INNER JOIN tblEstados AS e ON s.id_estados = e.id_estados WHERE nit_empresa = ? " ;
                if(!q.isEmpty()){
                    instruccion += " AND (orig_solicitud like '%"+q+"%' OR dest_solicitud like '%"+q+"%') ";
                }
                
                if(!cargue.isEmpty()){
                    instruccion += " AND (fech_carg_solicitud  BETWEEN '"+cargue+" 00:00:00' AND '"+cargue+" 23:59:59') ";
                }
                
                if(!descargue.isEmpty()){
                    instruccion += " AND (fech_desc_solicitud  BETWEEN '"+descargue+" 00:00:00' AND '"+descargue+" 23:59:59') ";
                }
                
                if(carga!=-1){
                    instruccion += " AND id_tipocarga = " + carga;
                }
                
                if(estado!=-1){
                    instruccion += " AND e.id_estados = " + estado;
                }
                
                instruccion+=      " ORDER BY fech_carg_solicitud DESC ";	        
                instruccion+=      " LIMIT "+desde+","+porpage+";";
                
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, nit);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getString(1));
                    objeto.put("estado", datos.getInt(2));
                    objeto.put("nestado", datos.getString(3));
                    objeto.put("orden",datos.getString(4));
                    objeto.put("cargue",formateador.format(formateador.parse(datos.getString(5))));
                    objeto.put("descargue",formateador.format(formateador.parse(datos.getString(6))));
                    objeto.put("origen", datos.getString(7));
                    objeto.put("destino", datos.getString(8));
                    objeto.put("vehiculos", listaVehiculosBySolicitud(datos.getString(1)));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalFiltrados(nit, q, cargue, descargue, carga, estado));
                retorno.put("data", lista);
                retorno.put("error", 0);
                return retorno;

            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        retorno.put("total_count", -1);
        retorno.put("data", "[]");
        retorno.put("error", -1);
        return retorno;
    }
    
    
    public static JSONArray listaVehiculosByEmpresa(String q, int carga, int estado, String nit) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ser.id_servicio, ser.id_solicitud, plca_equipo, CONCAT(nomb_conductor,' ', apll_conductor), " +
                "est.desc_estados, ult_esta_servicio, fech_inic_servicio, ser.ult_actu_servicio, ult_lat_servicio, ult_long_servicio, img_conductor  " +
                "FROM tblServicio AS ser INNER JOIN tblSolicitud AS sol ON ser.id_solicitud = sol.id_solicitud " +
                "INNER JOIN tblEquipoConductor AS eqpcon ON ser.id_equipoconductor = eqpcon.id_equipoconductor " +
                "INNER JOIN tblConductor AS con ON con.cod_conductor = eqpcon.cod_conductor " +
                "INNER JOIN tblEstados AS est ON ser.id_estados = est.id_estados " +
                "WHERE sol.nit_empresa = ? and ser.id_estados < 9 " ;
                if(!q.isEmpty()){
                    instruccion += " AND ( nomb_conductor like '%"+q+"%' OR apll_conductor like '%"+q+"%') ";
                }
                
                if(carga!=0){
                    instruccion += " AND id_tipocarga = " + carga;
                }
                
                if(estado!=0){
                    instruccion += " AND ser.id_estados = " + estado;
                }
                
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, nit);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    objeto.put("nombre",datos.getString(4));
                    objeto.put("estado",datos.getString(5));
                    objeto.put("act_estado",datos.getString(6));
                    objeto.put("inicio_serv",datos.getString(7));
                    objeto.put("act_serv",datos.getString(8));
                    posicion.add(datos.getFloat(9));
                    posicion.add(datos.getFloat(10));
                    objeto.put("position", posicion);
                    objeto.put("conductor",datos.getString(11));
                    lista.add(objeto);
                }
                
                return lista;

            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return lista;
    }
    
    public static int totalFiltrados(String nit, String q, String cargue, String descargue, int carga, int estado) throws SQLException{
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT  count(1) ";
                instruccion+=      "FROM tblSolicitud AS s INNER JOIN tblEstados AS e ON s.id_estados = e.id_estados WHERE nit_empresa = ? " ;
                if(!q.isEmpty()){
                    instruccion += " AND (orig_solicitud like '%"+q+"%' OR dest_solicitud like '%"+q+"%') ";
                }
                
                if(!cargue.isEmpty()){
                    instruccion += " AND (fech_carg_solicitud  BETWEEN '"+cargue+" 00:00:00' AND '"+cargue+" 23:59:59') ";
                }
                
                if(!descargue.isEmpty()){
                    instruccion += " AND (fech_desc_solicitud  BETWEEN '"+descargue+" 00:00:00' AND '"+descargue+" 23:59:59') ";
                }
                
                if(carga!=-1){
                    instruccion += " AND id_tipocarga = " + carga;
                }
                
                if(estado!=-1){
                    instruccion += " AND e.id_estados = " + estado;
                }
                
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, nit);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    return datos.getInt(1);
                }
                
                
            }catch (SQLException e) {
                    System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return 0;
    }
    
    public static void main(String[] args) throws SQLException {
        System.out.println(listaPuntos(""));
    }
    
    public static JSONArray listaEmpresasEnturne() throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT nit_empresa, razn_soci_empresa, dir_empresa, tel_empresa, url_img_empresa " +
                                    "FROM logycus360.tblEmpresa WHERE acti_empresa = 1 AND enturn_empresa = 1;" ;
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("nit", datos.getString(1));
                    objeto.put("razon_social", datos.getString(2));
                    objeto.put("direccion", datos.getString(3));
                    objeto.put("telefono",datos.getString(4));
                    objeto.put("url_imagen",datos.getString(5));
                    objeto.put("procesos", listaProcesosEmpresasEnturne(datos.getString(1)));
                    lista.add(objeto);
                }
                
                return lista;

            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return lista;
    }
    
    public static JSONArray listaProcesosEmpresasEnturne(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_tipocargue, nit_empresa, desc_tipocargue FROM tblTipoCargue WHERE nit_empresa = ?;" ;
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getString(1));
                    objeto.put("id_empresa", datos.getString(2));
                    objeto.put("desc", datos.getString(3));
                    lista.add(objeto);
                }
                
                return lista;

            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return lista;
    }
    
    public static String listaPuntos(String servicio) throws SQLException{
        JSONArray lista=new JSONArray();  
        JSONObject objeto_= new JSONObject();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_mensaje, imei_mensaje, lati_mensaje, long_mensaje, vel_mensaje, " +
                "date_mensaje FROM logycus360.tblMensajes WHERE id_servicio = '00000001';";	        
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    //JSONArray posicion= new JSONArray();
                    JSONObject posicion= new JSONObject();
                    /*objeto.put("id", datos.getInt(1));
                    objeto.put("imei", datos.getString(2));
                    objeto.put("latitud", datos.getFloat(3));
                    objeto.put("logitud", datos.getFloat(4));
                    objeto.put("velocidad", datos.getFloat(5));
                    objeto.put("datetime", formateador.format(formateador.parse(datos.getString(6))));*/
                    posicion.put("lat",datos.getFloat(3));
                    posicion.put("lng",datos.getFloat(4));
                    objeto.put("location", posicion);
                    
                    lista.add(objeto);
                }

            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario");
                    System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return lista.toJSONString();
    }
    
}


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
                String instruccion="SELECT ec.id_equipoconductor, cod_conductor, ec.plca_equipo, lat_equipoconductor, long_equipoconductor, " +
                "velo_equipoconductor, ult_actualizacion, id_tipocarga, IFNULL(regs_imei_conductor,'-1') "+
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo "+
                "WHERE id_tipocarga = "+tipo+" AND disp_equipoconductor = 1 AND " +
                "(acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<100;";	        
                
                st=conn.prepareStatement(instruccion);
                System.out.println(instruccion);
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
                    if(!datos.getString(9).equals("-1")){ 
                        TOs.add(datos.getString(9));
                    }
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
                String instruccion="SELECT ec.plca_equipo, lat_equipoconductor, long_equipoconductor, ult_actualizacion, " +
                "desc_remolque, desc_tipocarga, desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, " +
                "plac_trailer_equipo, CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, img_conductor, " +
                "se.id_solicitud, se.id_servicio, so.orig_solicitud, so.dest_solicitud, CASE " +
                "WHEN disp_equipoconductor = 0 THEN 'css/images/ic_truckicon_ocup.png' " +
                "WHEN disp_equipoconductor = 1 THEN 'css/images/ic_truckicon_disp.png' END AS icono " +
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo " +
                "INNER JOIN tblConductor AS co ON co.cod_conductor = ec.cod_conductor " +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = eq.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
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
                    instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -12 HOUR);";
                }
                
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("placa", datos.getString(1));
                    posicion.add(datos.getFloat(2));
                    posicion.add(datos.getFloat(3));
                    objeto.put("position", posicion);
                    objeto.put("ult_reporte", formateador.format(formateador.parse(datos.getString(4))));
                    objeto.put("remolque", datos.getString(5));                    
                    objeto.put("tipocarga", datos.getString(6));                    
                    objeto.put("tipoequipo", datos.getString(7));
                    objeto.put("marca", datos.getString(8));
                    objeto.put("modelo", datos.getString(9));
                    objeto.put("referencia", datos.getString(10));
                    objeto.put("trailer", datos.getString(11));
                    objeto.put("nombre", datos.getString(12));
                    objeto.put("telefono", datos.getString(13));
                    objeto.put("imagen", datos.getString(14));
                    objeto.put("solicitud", datos.getString(15));
                    objeto.put("servicio", datos.getString(16));
                    objeto.put("origen", datos.getString(17));
                    objeto.put("destino", datos.getString(18));
                    objeto.put("icono", datos.getString(19));
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
    
    public static JSONArray listaVehiculosDispEnturne(float lat, float lng, int carga, String remolques, String equipos, 
        float zona, String empresa) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ec.plca_equipo, lat_equipoconductor, long_equipoconductor, ult_actualizacion, desc_remolque, " +
                "desc_tipocarga, desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, " +
                "CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, img_conductor, ec.id_equipoconductor, imei_conductor , regs_imei_conductor, regs_imei_entur_conductor " +
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo " +
                "INNER JOIN tblConductor AS co ON co.cod_conductor = ec.cod_conductor " +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = eq.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "WHERE disp_equipoconductor = 1 AND pila_equipoconductor = 1 AND eq.nit_empresa = '"+ empresa +"'";
                instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -12 HOUR)";
                if(zona>0){
                    instruccion += " AND (acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                    "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                    "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<"+zona;	
                }
                if(!remolques.equals("")){
                    instruccion += " AND (";
                        String[] r = remolques.split(",");
                        for(int i=0; i < r.length; i++){
                            if(r.length-1 == i){
                                instruccion += " eq.id_remolque = "+r[i];
                            }else{
                                instruccion += " eq.id_remolque = "+r[i] + " OR "; 
                            }
                        }
                    instruccion += ")";
                }
                if(carga!=0){
                    instruccion += " AND eq.id_tipocarga  = "+ carga ;
                }
                if(!equipos.equals("")){
                    instruccion += " AND (";
                        String[] r = equipos.split(",");
                        for(int i=0; i < r.length; i++){
                            if(r.length-1 == i){
                                instruccion += " eq.id_tipoequipo = "+r[i];
                            }else{
                                instruccion += " eq.id_tipoequipo = "+r[i] + " OR "; 
                            }
                        }
                    instruccion += ")";
                }

                
                
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("placa", datos.getString(1));
                    posicion.add(datos.getFloat(2));
                    posicion.add(datos.getFloat(3));
                    objeto.put("position", posicion);
                    objeto.put("ult_reporte", formateador.format(formateador.parse(datos.getString(4))));
                    objeto.put("remolque", datos.getString(5));                    
                    objeto.put("tipocarga", datos.getString(6));                    
                    objeto.put("tipoequipo", datos.getString(7));
                    objeto.put("marca", datos.getString(8));
                    objeto.put("modelo", datos.getString(9));
                    objeto.put("referencia", datos.getString(10));
                    objeto.put("trailer", datos.getString(11));
                    objeto.put("nombre", datos.getString(12));
                    objeto.put("telefono", datos.getString(13));
                    objeto.put("imagen", datos.getString(14));
                    objeto.put("id", datos.getString(15));                    
                    objeto.put("imei", datos.getString(16));                    
                    objeto.put("reg_logycus", datos.getString(17));                    
                    objeto.put("reg_enturne", datos.getString(18));                    
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
        return lista;
    }
    
    
    public static JSONArray listaServiciosBySolicitud(String id) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT * FROM logycus360.SolicitudesAll WHERE id_solicitud = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    // datos de servicio y solicitud
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    // datos de equipo
                    objeto.put("placa", datos.getString(3));
                    objeto.put("marca", datos.getString(4));
                    objeto.put("referencia", datos.getString(5));
                    objeto.put("modelo", datos.getString(6));
                    objeto.put("trailer", datos.getString(7));
                    objeto.put("poliza", datos.getString(8));
                    objeto.put("compania", datos.getString(9));
                    objeto.put("exp_poliza", datos.getString(10));
                    objeto.put("vence_poliza", datos.getString(11));
                    objeto.put("soat", datos.getString(12));
                    objeto.put("exp_soat",datos.getString(13));
                    objeto.put("vence_soat",datos.getString(14));
                    objeto.put("tecno",datos.getString(15));
                    objeto.put("exp_tecno",datos.getString(16));
                    objeto.put("vence_tecno",datos.getString(17));
                    // datos de conductor
                    objeto.put("nombre",datos.getString(18));
                    objeto.put("tipo_doc",datos.getString(19));
                    objeto.put("doc",datos.getString(20));
                    objeto.put("num_lic",datos.getString(21));
                    objeto.put("exp_lic",datos.getString(22));
                    objeto.put("vence_lic",datos.getString(23));
                    objeto.put("telefono",datos.getString(24));
                    objeto.put("direccion",datos.getString(25));
                    objeto.put("imagen",datos.getString(26));
                    objeto.put("tipo_carga",datos.getInt(27));
                    objeto.put("ntipo_carga",datos.getString(28));
                    objeto.put("tipo_remolque",datos.getInt(29));
                    objeto.put("ntipo_remolque",datos.getString(30));
                    objeto.put("tipo_equipo",datos.getInt(31));
                    objeto.put("ntipo_equipo",datos.getString(32));
                    objeto.put("turno_cargue",datos.getInt(33));
                    objeto.put("turno_descargue",datos.getInt(34));
                    objeto.put("fotos",listaFotosByServicio(datos.getString(1)));
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
                
                System.out.println(instruccion);
                System.out.println(nit);
                
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
                    objeto.put("vehiculos", listaServiciosBySolicitud(datos.getString(1)));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalFiltrados(nit, q, cargue, descargue, carga, estado));
                retorno.put("data", lista);
                retorno.put("error", 0);
                System.out.println(retorno.toJSONString());
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
                
                String instruccion="SELECT id_tipocargue, nit_empresa, desc_tipocargue FROM tblTipoCargue "
                        + " WHERE nit_empresa = ?;" ;
                
                
                
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
    
    public static int totalEnturneEmpresas(String empresa) throws SQLException{
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT COUNT(1) " +
                "FROM tblTicketEnturne AS tk INNER JOIN tblTipoCargue AS tc ON tk.id_tipocargue = tc.id_tipocargue " +
                "INNER JOIN tblEquipoConductor AS ec ON ec.id_equipoconductor = tk.id_equipoconductor " +
                "INNER JOIN tblEquipo AS eq ON eq.plca_equipo = ec.plca_equipo " +
                "INNER JOIN tblRemolque AS rm ON rm.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "WHERE tk.nit_empresa = ?;" ;
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
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
    
    public static JSONObject listaEnturneEmpresas(int porpage, int pageno, String empresa) throws SQLException{
        JSONArray lista=new JSONArray();
        JSONObject retorno = new JSONObject();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                int desde = ((pageno-1)*porpage);
                String instruccion="SELECT id_ticketenturne, fech_enturne, CASE WHEN oper_enturne = 'C' THEN 'Cargue' ELSE 'Descargue' END, " +
                "tc.desc_tipocargue, fech_ticket_enturne, " +
                "eq.plca_equipo, rm.desc_remolque, te.desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, " +
                "plac_trailer_equipo, poliza_equipo, soat_equipo, tecno_equipo " +
                "FROM tblTicketEnturne AS tk INNER JOIN tblTipoCargue AS tc ON tk.id_tipocargue = tc.id_tipocargue " +
                "INNER JOIN tblEquipoConductor AS ec ON ec.id_equipoconductor = tk.id_equipoconductor " +
                "INNER JOIN tblEquipo AS eq ON eq.plca_equipo = ec.plca_equipo " +
                "INNER JOIN tblRemolque AS rm ON rm.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "WHERE tk.nit_empresa = ? " ;
                instruccion+=      " ORDER BY fech_ticket_enturne DESC ";	        
                instruccion+=      " LIMIT "+desde+","+porpage+";";
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                System.out.println(instruccion);
                System.out.println(empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("ticket", datos.getString(1));
                    objeto.put("fecha", datos.getString(2));
                    objeto.put("operacion", datos.getString(3));
                    objeto.put("tipo_cargue", datos.getString(4));
                    objeto.put("fecha_enturne", datos.getString(5));
                    objeto.put("placa", datos.getString(6));
                    objeto.put("remolque", datos.getString(7));
                    objeto.put("tipo_equipo", datos.getString(8));
                    objeto.put("marca", datos.getString(9));
                    objeto.put("modelo", datos.getString(10));
                    objeto.put("referencia", datos.getString(11));
                    objeto.put("trailer", datos.getString(12));
                    objeto.put("poliza", datos.getString(12));
                    objeto.put("soat", datos.getString(14));
                    objeto.put("tecno", datos.getString(15));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalEnturneEmpresas(empresa));
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
            
            retorno.put("total_count", 0);
            retorno.put("data", "[]");
            retorno.put("error", 0);
            return retorno;
    }
    
    public static int totalPuntosEmpresas(String empresa, String q) throws SQLException{
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_punto, desc_punto, nota_punto, lat_punto, lng_punto " +
                "FROM tblPunto WHERE nit_empresa = ? " ;
                if(!q.isEmpty()){
                    instruccion+=      " AND desc_punto LIKE '%"+q+"%'";	        
                }
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
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
    
    public static JSONObject listaPuntosEmpresas(int porpage, int pageno, String empresa, String q) throws SQLException{
        JSONArray lista=new JSONArray();
        JSONObject retorno = new JSONObject();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                int desde = ((pageno-1)*porpage);   
                String instruccion="SELECT id_punto, desc_punto, nota_punto, lat_punto, lng_punto " +
                "FROM tblPunto WHERE nit_empresa = ? " ;
                if(!q.isEmpty()){
                    instruccion+=      " AND desc_punto LIKE '%"+q+"%'";	        
                }
                instruccion+=      " ORDER BY desc_punto DESC ";	        
                instruccion+=      " LIMIT "+desde+","+porpage+";";
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("desc", datos.getString(2));
                    objeto.put("nota", datos.getString(3));
                    objeto.put("lat",datos.getFloat(4));
                    objeto.put("lng",datos.getFloat(5));
                    objeto.put("zonas", listaZonasPuntos(datos.getInt(1)));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalPuntosEmpresas(empresa, q));
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
            
            retorno.put("total_count", 0);
            retorno.put("data", "[]");
            retorno.put("error", 0);
            return retorno;
    }

    public static JSONArray listaAllPuntosEmpresas(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_punto, desc_punto, nota_punto, lat_punto, lng_punto " +
                "FROM tblPunto WHERE nit_empresa = ? " ;
                instruccion+=      " ORDER BY desc_punto DESC ";	        

                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("desc", datos.getString(2));
                    objeto.put("nota", datos.getString(3));
                    objeto.put("lat",datos.getFloat(4));
                    objeto.put("lng",datos.getFloat(5));
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
            
            return lista;
    }
    
    
    public static JSONArray listaCarguesEmpresas(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_tipocargue, desc_tipocargue FROM logycus360.tblTipoCargue WHERE nit_empresa = ? " ;
                instruccion+=      " ORDER BY desc_tipocargue DESC ";	        

                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("desc", datos.getString(2));
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
            
            return lista;
    }
    
    
    public static JSONObject listaEnturneEmpresasPorEstados(int porpage, int pageno, String empresa, int tipo) throws SQLException{
        JSONArray lista=new JSONArray();
        JSONObject retorno = new JSONObject();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                int desde = ((pageno-1)*porpage);
                String instruccion="SELECT id_ticketenturne, fech_enturne, CASE WHEN oper_enturne = 'C' THEN 'Cargue' ELSE 'Descargue' END, " +
                "tc.desc_tipocargue, fech_ticket_enturne, fecha_enturne,  desc_zona, desc_habia, " +
                "eq.plca_equipo, rm.desc_remolque, te.desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, " +
                "plac_trailer_equipo, poliza_equipo, soat_equipo, tecno_equipo, fech_term_enturne, tk.id_zona, tk.id_bahia, turno_enturne, pu.desc_punto, tk.id_punto " +
                "FROM tblTicketEnturne AS tk INNER JOIN tblTipoCargue AS tc ON tk.id_tipocargue = tc.id_tipocargue " +
                "INNER JOIN tblEquipoConductor AS ec ON ec.id_equipoconductor = tk.id_equipoconductor " +
                "INNER JOIN tblEquipo AS eq ON eq.plca_equipo = ec.plca_equipo " +
                "INNER JOIN tblRemolque AS rm ON rm.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "LEFT JOIN tblPunto AS pu ON pu.id_punto = tk.id_punto "+
                "LEFT JOIN tblZona AS ze ON ze.id_zona = tk.id_zona "+
                "LEFT JOIN tblBahia AS ba ON ba.id_bahia = tk.id_bahia "+
                "WHERE tk.nit_empresa = ? AND fech_enturne = CURDATE() " ;
                
                if(tipo == 1){
                    instruccion+= " AND term_enturne = 0 AND fecha_enturne IS NULL ";	        
                }else if(tipo == 2){
                    instruccion+= " AND term_enturne = 0 AND fecha_enturne IS NOT NULL ";	        
                }else if(tipo == 3){
                    instruccion+= " AND term_enturne = 1 ";	        
                }
                
                instruccion+= " ORDER BY fech_ticket_enturne DESC ";	        
                instruccion+= " LIMIT "+desde+","+porpage+";";
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                System.out.println(instruccion);
                System.out.println(empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("ticket", datos.getString(1));
                    objeto.put("fecha", datos.getString(2));
                    objeto.put("operacion", datos.getString(3));
                    objeto.put("tipo_cargue", datos.getString(4));
                    objeto.put("fecha_enturne", datos.getString(5));
                    objeto.put("fecha_enturnado", datos.getString(6));
                    objeto.put("nzona", datos.getString(7));
                    objeto.put("nbahia", datos.getString(8));
                    objeto.put("npunto", datos.getString(24));
                    objeto.put("zona", datos.getString(20));
                    objeto.put("bahia", datos.getString(21));
                    objeto.put("turno", datos.getString(22));
                    objeto.put("punto", datos.getString(23));
                    objeto.put("placa", datos.getString(9));
                    objeto.put("remolque", datos.getString(10));
                    objeto.put("tipo_equipo", datos.getString(11));
                    objeto.put("marca", datos.getString(12));
                    objeto.put("modelo", datos.getString(13));
                    objeto.put("referencia", datos.getString(14));
                    objeto.put("trailer", datos.getString(15));
                    objeto.put("poliza", datos.getString(16));
                    objeto.put("soat", datos.getString(17));
                    objeto.put("tecno", datos.getString(18));
                    objeto.put("fecha_termina", datos.getString(19));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalEnturneEmpresas(empresa));
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
            
            retorno.put("total_count", 0);
            retorno.put("data", "[]");
            retorno.put("error", 0);
            return retorno;
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
    
    public static JSONArray listaZonasPuntos(int punto) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                
                String instruccion="SELECT id_zona, id_punto, desc_zona nota_zona FROM tblZona WHERE id_punto = ?;" ;
                
                
                
                st=conn.prepareStatement(instruccion);
                st.setInt(1, punto);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("id_punto", datos.getInt(2));
                    objeto.put("desc", datos.getString(3));
                    objeto.put("bahias", listaBahiasZona(datos.getInt(1)));
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
    
    public static JSONArray listaBahiasZona(int zona) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                
                String instruccion="SELECT id_bahia, id_zona, desc_habia, nota_bahia FROM tblBahia WHERE id_zona = ?;" ;
                
                
                
                st=conn.prepareStatement(instruccion);
                st.setInt(1, zona);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("id_zona", datos.getInt(2));
                    objeto.put("desc", datos.getString(3));
                    objeto.put("nota", datos.getString(4));
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
}

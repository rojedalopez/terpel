
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
    static SimpleDateFormat formateadorDate = new SimpleDateFormat("yyyy/MM/dd");
  
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
            System.out.println("error SQLException en listaVehiculos");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculos");
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
    
    
    public static JSONArray listaVehiculos() throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ec.plca_equipo, lat_equipoconductor, long_equipoconductor, ult_actualizacion, desc_remolque, " +
                "desc_tipocarga, desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, " +
                "CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, img_conductor, ec.id_equipoconductor, " +
                "imei_conductor , regs_imei_conductor, regs_imei_entur_conductor, cap_equipo, und_equipo " +
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo " +
                "INNER JOIN tblConductor AS co ON co.cod_conductor = ec.cod_conductor " +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = eq.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "WHERE disp_equipoconductor = 1 AND pila_equipoconductor = 1 ";
                instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -12 HOUR)";        
                
                st=conn.prepareStatement(instruccion);
                System.out.println(instruccion);
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
                    objeto.put("capacidad", datos.getString(19)); 
                    objeto.put("unidad", datos.getString(20));  
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaVehiculos");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculos");
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
    
    public static JSONArray listaVehiculosAsignar(String empresa, int rango, float lat, float lng, String solicitud) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT ec.plca_equipo, lat_equipoconductor, long_equipoconductor, ult_actualizacion, desc_remolque, " +
                "desc_tipocarga, desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, " +
                "CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, img_conductor, ec.id_equipoconductor, " +
                "imei_conductor , regs_imei_conductor, regs_imei_entur_conductor, cap_equipo, und_equipo, lat_equipoconductor, long_equipoconductor, velo_equipoconductor " +
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo " +
                "INNER JOIN tblConductor AS co ON co.cod_conductor = ec.cod_conductor " +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = eq.id_remolque " +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = eq.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "WHERE disp_equipoconductor = 1 AND eq.id_remolque IN (select substring_index(substring_index(id_remolques, ',', id_remolque), ',', -1) as remolques\n" +
                "from tblSolicitud join tblRemolque on id_solicitud = '"+solicitud+"' AND char_length(id_remolques) - char_length(replace(id_remolques, ',', '')) >= id_remolque - 1) AND pila_equipoconductor = 1 AND eq.nit_empresa = '"+empresa+"' ";
                instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -12 HOUR) AND " +
                "(acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<"+rango+";";        
                
                st=conn.prepareStatement(instruccion);
                System.out.println(instruccion);
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
                    objeto.put("capacidad", datos.getString(19)); 
                    objeto.put("unidad", datos.getString(20));  
                    objeto.put("latitud", datos.getFloat(21));
                    objeto.put("longitud", datos.getFloat(22));
                    objeto.put("velocidad", datos.getFloat(23));
                    
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaVehiculosAsignar");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosAsignar");
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
    
    public static JSONArray listaEnturneActivos(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_servicioenturne, ec.plca_equipo, lat_equipoconductor, long_equipoconductor, ult_actualizacion, " +
                "desc_remolque, desc_tipocarga, desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, " +
                " CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, img_conductor, pila_equipoconductor, " +
                "cap_carg_servicioenturne, und_equipo, fech_m_carg_servicioenturne, fech_m_desc_servicioenturne, " +
                "guia_servicioenturne, desc_estados, CONCAT(p_i.desc_punto,' - ', p_f.desc_punto) " +
                "FROM tblServicioEnturne AS se INNER JOIN tblEquipoConductor AS ec ON ec.id_equipoconductor = se.id_equipoconductor " +
                "INNER JOIN tblEquipo AS eq ON eq.plca_equipo = ec.plca_equipo " +
                "INNER JOIN tblConductor AS co ON co.cod_conductor = ec.cod_conductor  " +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = eq.id_remolque  " +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = eq.id_tipocarga  " +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = eq.id_tipoequipo " +
                "INNER JOIN tblEstados AS es ON es.id_estados = se.id_estados " +
                "INNER JOIN tblPunto AS p_i ON p_i.id_punto = se.id_punto_inicio " +
                "INNER JOIN tblPunto AS p_f ON p_f.id_punto = se.id_punto_fin " +
                "WHERE se.id_estados < 11 AND se.nit_empresa = ?;";        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    JSONArray posicion= new JSONArray();
                    objeto.put("id", datos.getString(1));
                    objeto.put("placa", datos.getString(2));
                    posicion.add(datos.getFloat(3));
                    posicion.add(datos.getFloat(4));
                    objeto.put("position", posicion);
                    objeto.put("ult_reporte", formateador.format(formateador.parse(datos.getString(5))));
                    objeto.put("remolque", datos.getString(6));                    
                    objeto.put("tipocarga", datos.getString(7));                    
                    objeto.put("tipoequipo", datos.getString(8));
                    objeto.put("marca", datos.getString(9));
                    objeto.put("modelo", datos.getString(10));
                    objeto.put("referencia", datos.getString(11));
                    objeto.put("trailer", datos.getString(12));
                    objeto.put("nombre", datos.getString(13));
                    objeto.put("telefono", datos.getString(14));
                    objeto.put("imagen", datos.getString(15));
                    objeto.put("pila", datos.getBoolean(16));                    
                    objeto.put("capacidad", datos.getString(17)); 
                    objeto.put("unidad", datos.getString(18));  
                    objeto.put("fecha_cargue", formateador.format(formateador.parse(datos.getString(19))));
                    objeto.put("fecha_descargue", formateador.format(formateador.parse(datos.getString(20))));
                    objeto.put("guia", datos.getString(21));  
                    objeto.put("estado", datos.getString(22));  
                    objeto.put("ruta", datos.getString(23));  
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaEnturneActivos");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaEnturneActivos");
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
            System.out.println("error SQLException en listaVehiculosBusquedas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosBusquedas");
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
            System.out.println("error SQLException en listaVehiculosDispEnturne");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosDispEnturne");
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
    
    public static JSONArray listaVehiculosDispLogycus(float lat, float lng, String remolques, 
        String solicitud, int horas, String empresa) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        int zona = (horas>=0)?350:35*horas;
        
            try{
                conn=conexion();
                String instruccion="SELECT cond.cod_conductor, equ.plca_equipo, lic_trans_equipo, lat_equipoconductor, long_equipoconductor, ult_pos_equipoconductor,\n" +
                "ult_actualizacion, regs_imei_conductor, tr.desc_remolque, tc.desc_tipocarga,\n" +
                "te.desc_tipoequipo, marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, lic_trans_r_equipo, poliza_equipo, comp_poliza_equipo,\n" +
                "fech_venc_poliz_equipo, soat_equipo, fech_venc_soat_equipo, tecno_equipo, fech_venc_tecno_equipo, \n" +
                " poliza_hc_equipo, comp_poliza_hc_equipo,vence_poliza_hc_equipo, CONCAT(CAST(cap_equipo AS CHAR), ' ',und_equipo), tipo_doc_conductor, doc_conductor, num_lic_conductor, fech_venc_lic_conductor,\n" +
                "CONCAT(nomb_conductor, ' ', apll_conductor), tel_conductor, dire_conductor, img_conductor, razn_soci_empresa, url_img_empresa, CASE WHEN soli.id_servicio IS NULL THEN '../css/images/ic_truckicon.png' ELSE '../css/images/ic_truckicon_ocup.png' END \n" +
                "FROM tblEquipoConductor AS eqco INNER JOIN tblEquipo AS equ ON eqco.plca_equipo = equ.plca_equipo\n" +
                "INNER JOIN tblConductor AS cond ON cond.cod_conductor = eqco.cod_conductor\n" +
                "INNER JOIN tblEmpresa AS trans ON  trans.nit_empresa = equ.nit_empresa\n" +
                "INNER JOIN tblRemolque AS tr ON tr.id_remolque = equ.id_remolque \n" +
                "INNER JOIN tblTipoCarga AS tc ON tc.id_tipocarga = equ.id_tipocarga \n" +
                "INNER JOIN tblTipoEquipo AS te ON te.id_tipoequipo = equ.id_tipoequipo\n" +
                "LEFT JOIN tblAsocio AS aso ON aso.nit_empresa = '"+ empresa +"' AND aso.nit_transportadora = equ.nit_empresa\n" +
                "LEFT JOIN (SELECT id_servicio, serv.id_equipoconductor\n" +
                "FROM tblSolicitud AS sol INNER JOIN tblServicio AS serv ON sol.id_solicitud = serv.id_solicitud\n" +
                "WHERE sol.id_solicitud = '"+solicitud+"' ) AS soli ON soli.id_equipoconductor = eqco.id_equipoconductor " +
                "WHERE fech_venc_poliz_equipo > NOW() AND fech_venc_soat_equipo > NOW() AND fech_venc_tecno_equipo > NOW() AND \n" +
                "vence_poliza_hc_equipo > NOW() AND fech_venc_lic_conductor > NOW() AND (disp_equipoconductor = 1 OR soli.id_equipoconductor = eqco.id_equipoconductor) AND pila_equipoconductor = 1 ";
                instruccion += " AND ult_actualizacion > DATE_ADD(NOW(),INTERVAL -12 HOUR)";
                if(lat>0 || lng>0){
                    instruccion += " AND (acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                    "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                    "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<"+zona;	
                }
                if(!remolques.equals("")){
                    instruccion += " AND (";
                        String[] r = remolques.split(",");
                        for(int i=0; i < r.length; i++){
                            if(r.length-1 == i){
                                instruccion += " equ.id_remolque = "+r[i];
                            }else{
                                instruccion += " equ.id_remolque = "+r[i] + " OR "; 
                            }
                        }
                    instruccion += ")";
                }
                instruccion += " ORDER BY soli.id_servicio DESC";
                
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto = new JSONObject();
                    JSONArray posicion = new JSONArray();
                    objeto.put("codigo", datos.getString(1));
                    objeto.put("placa", datos.getString(2));
                    objeto.put("lic_transito", datos.getString(3));
                    posicion.add(datos.getFloat(4));
                    posicion.add(datos.getFloat(5));
                    objeto.put("position", posicion);
                    objeto.put("ult_pos", datos.getString(6));
                    if(datos.getString(7) != null){
                        objeto.put("ult_act", formateador.format(datos.getDate(7)));
                    }
                    objeto.put("reg_logycus", datos.getString(8));
                    objeto.put("tipo_remolque", datos.getString(9));
                    objeto.put("tipo_carga", datos.getString(10));
                    objeto.put("tipo_equipo", datos.getString(11));
                    objeto.put("modelo", datos.getString(12));
                    objeto.put("marca", datos.getString(13));
                    objeto.put("referencia", datos.getString(14));
                    objeto.put("placa_rem", datos.getString(15));
                    objeto.put("lic_transito_r", datos.getString(16));
                    objeto.put("poliza", datos.getString(17));
                    objeto.put("comp_pol", datos.getString(18));
                    if(datos.getString(19) != null){
                        objeto.put("vence_pol", formateadorDate.format(datos.getDate(19)));
                    }
                    objeto.put("soat", datos.getString(20));
                    if(datos.getString(21) != null){
                        objeto.put("vence_soat", formateadorDate.format(datos.getDate(21)));
                    }
                    objeto.put("tecno", datos.getString(22));
                    if(datos.getString(23) != null){
                        objeto.put("vence_tecno", formateadorDate.format(datos.getDate(23)));
                    }
                    objeto.put("poliza_hc", datos.getString(24));
                    objeto.put("comp_pol_hc", datos.getString(25));
                    if(datos.getString(26) != null){
                        objeto.put("vence_pol_hc", formateadorDate.format(datos.getDate(26)));
                    }
                    objeto.put("capacidad", datos.getString(27));        
                    objeto.put("tipo_doc", datos.getString(28));
                    objeto.put("doc", datos.getString(29));
                    objeto.put("lic", datos.getString(30));
                    if(datos.getString(31) != null){
                        objeto.put("vence_lic", formateadorDate.format(datos.getDate(31)));
                    }
                    objeto.put("nombre", datos.getString(32));
                    objeto.put("telefono", datos.getString(33));
                    objeto.put("direccion", datos.getString(34));
                    objeto.put("img_cond", datos.getString(35));
                    objeto.put("empresa", datos.getString(36));
                    objeto.put("img_empresa", datos.getString(37));
                    objeto.put("icono", datos.getString(38));
                    lista.add(objeto);
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaVehiculosDispLogycus");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosDispLogycus");
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
                String instruccion="SELECT * FROM logycus360.SelectServicioBySolicitud WHERE id_solicitud = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    // datos de servicio y solicitud
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    objeto.put("marca", datos.getString(4));
                    objeto.put("referencia", datos.getString(5));
                    objeto.put("modelo", datos.getString(6));
                    objeto.put("placa_rem", datos.getString(7));
                    objeto.put("lic_transito", datos.getString(8));
                    objeto.put("lic_transito_r", datos.getString(9));
                    objeto.put("poliza", datos.getString(10));
                    objeto.put("comp", datos.getString(11));
                    if(datos.getString(12) != null){
                        objeto.put("exp_poliza", formateadorDate.format(datos.getDate(12)));
                    }
                    if(datos.getString(13) != null){
                        objeto.put("vence_poliza", formateadorDate.format(datos.getDate(13)));
                    }
                    objeto.put("poliza_hc", datos.getString(14));
                    objeto.put("comp_hc", datos.getString(15));
                    if(datos.getString(16) != null){
                        objeto.put("exp_poliza_hc", formateadorDate.format(datos.getDate(16)));
                    }
                    if(datos.getString(17) != null){
                        objeto.put("vence_poliza_hc", formateadorDate.format(datos.getDate(17)));
                    }
                    objeto.put("soat", datos.getString(18));
                    if(datos.getString(19) != null){
                        objeto.put("exp_soat", formateadorDate.format(datos.getDate(19)));
                    }
                    if(datos.getString(20) != null){
                        objeto.put("vence_soat", formateadorDate.format(datos.getDate(20)));
                    }
                    objeto.put("tecno", datos.getString(21));
                    if(datos.getString(22) != null){
                        objeto.put("exp_tecno", formateadorDate.format(datos.getDate(22)));
                    }
                    if(datos.getString(23) != null){
                        objeto.put("vence_tecno", formateadorDate.format(datos.getDate(23)));
                    }
                    
                    objeto.put("url_conductor", datos.getString(24));
                    objeto.put("nombre", datos.getString(25));
                    objeto.put("apellido", datos.getString(26));
                    objeto.put("nombre_completo", datos.getString(27));
                    objeto.put("tipo_doc", datos.getString(28));
                    objeto.put("doc", datos.getString(29));
                    objeto.put("licencia", datos.getString(30));
                    if(datos.getString(31) != null){
                        objeto.put("exp_lic", formateadorDate.format(datos.getDate(31)));
                    }
                    if(datos.getString(32) != null){
                        objeto.put("vence_lic", formateadorDate.format(datos.getDate(32)));
                    }
                    objeto.put("telefono", datos.getString(33));
                    objeto.put("direccion", datos.getString(34));
                    objeto.put("tipo_carga", datos.getString(35));
                    objeto.put("tipo_remolque", datos.getString(37));
                    objeto.put("tipo_equipo", datos.getString(39));
                    objeto.put("tipo_cargue", datos.getString(41));
                    objeto.put("turno_cargue", datos.getInt(43));
                    objeto.put("turno_descague", datos.getInt(44));
                    objeto.put("ticket_cargue", datos.getString(45));
                    objeto.put("ticket_descargue", datos.getString(46));
                    objeto.put("reg_logycus", datos.getString(48));
                    objeto.put("reg_enturnex", datos.getString(49));
                    objeto.put("nit_generador", datos.getString(50));
                    objeto.put("generador", datos.getString(51));
                    objeto.put("nit_transportadora", datos.getString(52));
                    objeto.put("transportadora", datos.getString(53));
                    objeto.put("url_generador", datos.getString(54));
                    objeto.put("url_transportadora", datos.getString(55));
                    objeto.put("id_inicio", datos.getString(56));
                    objeto.put("desc_inicio", datos.getString(57));
                    objeto.put("lat_inicio", datos.getFloat(58));
                    objeto.put("lng_inicio", datos.getFloat(59));
                    objeto.put("id_fin", datos.getString(60));
                    objeto.put("desc_fin", datos.getString(61));
                    objeto.put("lat_fin", datos.getFloat(62));
                    objeto.put("lng_fin", datos.getFloat(63));
                    objeto.put("lat_actual", datos.getFloat(64));
                    objeto.put("lng_actual", datos.getFloat(65));
                    objeto.put("vel_actual", datos.getFloat(66));
                    objeto.put("pos_actual", datos.getString(67));
                    if(datos.getString(68) != null){
                        objeto.put("ult_actual", formateador.format(datos.getDate(68)));
                    }
                    objeto.put("cap_carg", datos.getInt(69));
                    if(datos.getString(70) != null){
                        objeto.put("min_carg", formateador.format(datos.getDate(70)));
                    }
                    if(datos.getString(71) != null){
                        objeto.put("max_carg", formateador.format(datos.getDate(71)));
                    }
                    if(datos.getString(72) != null){
                        objeto.put("min_desc", formateador.format(datos.getDate(72)));
                    }
                    if(datos.getString(73) != null){
                        objeto.put("max_desc", formateador.format(datos.getDate(73)));
                    }
                    objeto.put("operacion", datos.getInt(74));  
                    objeto.put("fotos",listaFotosByServicio(datos.getString(1)));
                    lista.add(objeto);
                }
                System.out.println(lista.toJSONString());
            }catch (SQLException e) {
            System.out.println("error SQLException en listaServiciosBySolicitud");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaServiciosBySolicitud");
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
            System.out.println("error SQLException en listaFotosByServicio");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaFotosByServicio");
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
                String instruccion="SELECT  id_solicitud, s.id_estados, e.desc_estados, orden_solicitud, fech_carg_solicitud, fech_desc_solicitud, p_i.desc_punto, p_f.desc_punto, car.desc_tipocargue, s.id_tipocargue " +
                "FROM tblSolicitud AS s INNER JOIN tblEstados AS e ON s.id_estados = e.id_estados " +
                "INNER JOIN tblPunto AS p_i ON p_i.id_punto = s.id_punto_inicio " +
                "INNER JOIN tblPunto AS p_f ON p_f.id_punto = s.id_punto_fin " +
                "INNER JOIN tblTipoCargue AS car ON car.id_tipocargue = s.id_tipocargue " +
                "WHERE s.nit_empresa = ? ";
                if(!q.isEmpty()){
                    instruccion += " AND (p_i.desc_punto like '%"+q+"%' OR p_f.desc_punto like '%"+q+"%') ";
                }
                
                if(!cargue.isEmpty()){
                    instruccion += " AND (fech_carg_solicitud  BETWEEN '"+cargue+" 00:00:00' AND '"+cargue+" 23:59:59') ";
                }
                
                if(!descargue.isEmpty()){
                    instruccion += " AND (fech_desc_solicitud  BETWEEN '"+descargue+" 00:00:00' AND '"+descargue+" 23:59:59') ";
                }
                
                if(carga!=-1){
                    instruccion += " AND id_tipocargue = " + carga;
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
                    objeto.put("n_cargue", datos.getString(9));
                    objeto.put("carga", datos.getInt(10));
                    objeto.put("vehiculos", listaServiciosBySolicitud(datos.getString(1)));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalFiltrados(nit, q, cargue, descargue, carga, estado));
                retorno.put("data", lista);
                retorno.put("error", 0);
                System.out.println(retorno.toJSONString());
                return retorno;

            }catch (SQLException e) {
            System.out.println("error SQLException en listaSolicitudes");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaSolicitudes");
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
            System.out.println("error SQLException en listaVehiculosByEmpresa");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosByEmpresa");
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
    
     public static JSONArray listaSolicitudesTransportador(String nit) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT  id_solicitud, s.id_estados, e.desc_estados, orden_solicitud, fech_carg_solicitud, " +
                "fech_desc_solicitud, p_i.desc_punto, p_f.desc_punto, p_i.lat_punto, p_i.lng_punto, p_f.lat_punto, p_f.lng_punto, " +
                "ROUND( (UNIX_TIMESTAMP(fech_max_carg_solicitud) - UNIX_TIMESTAMP(NOW())) / 3600) * 35, emp.razn_soci_empresa, car.desc_tipocargue, s.cup_eqpos_solicitud " +
                "FROM tblSolicitud AS s INNER JOIN tblEstados AS e ON s.id_estados = e.id_estados " +
                "INNER JOIN tblPunto AS p_i ON p_i.id_punto = s.id_punto_inicio " +
                "INNER JOIN tblPunto AS p_f ON p_f.id_punto = s.id_punto_fin " +
                "INNER JOIN tblAsocio AS aso ON aso.nit_empresa = s.nit_empresa " +
                "INNER JOIN tblEmpresa AS emp ON emp.nit_empresa = aso.nit_empresa " +
                "INNER JOIN tblTipoCargue AS car ON car.id_tipocargue = s.id_tipocargue " +
                "WHERE nit_transportadora = ?; " ;
                    
                System.out.println(instruccion);
                
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
                    objeto.put("lat_origen", datos.getFloat(9));
                    objeto.put("lng_origen", datos.getFloat(10));
                    objeto.put("lat_destino", datos.getFloat(11));
                    objeto.put("lng_destino", datos.getFloat(12));
                    objeto.put("rango", (datos.getInt(13)>0)?datos.getInt(13):35);
                    objeto.put("empresa", datos.getString(14));
                    objeto.put("producto", datos.getString(15));
                    objeto.put("cupos", datos.getInt(16));
                    //objeto.put("vehiculos", listaServiciosBySolicitud(datos.getString(1)));
                    lista.add(objeto);
                }
                
                return lista;

            }catch (SQLException e) {
            System.out.println("error SQLException en listaSolicitudesTransportador");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaSolicitudesTransportador");
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
                    System.out.println("error SQLException en totalFiltrados");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en totalFiltrados");
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
            System.out.println("error SQLException en listaEmpresasEnturne");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaEmpresasEnturne");
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
            System.out.println("error SQLException en listaProcesosEmpresasEnturne");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaProcesosEmpresasEnturne");
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
            System.out.println("error SQLException en totalEnturneEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en totalEnturneEmpresas");
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
            System.out.println("error SQLException en listaEnturneEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaEnturneEmpresas");
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
            System.out.println("error SQLException en totalPuntosEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en totalPuntosEmpresas");
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
                    objeto.put("zonas", listaZonasPuntos(datos.getString(1)));
                    lista.add(objeto);
                }
                
                retorno.put("total_count", totalPuntosEmpresas(empresa, q));
                retorno.put("data", lista);
                retorno.put("error", 0);
                
                return retorno;

            }catch (SQLException e) {
            System.out.println("error SQLException en listaPuntosEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaPuntosEmpresas");
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
                "FROM tblPunto WHERE nit_empresa = '830095213' " ;
                instruccion+=      " ORDER BY desc_punto DESC ";	        

                
                st=conn.prepareStatement(instruccion);
                //st.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getString(1));
                    objeto.put("desc", datos.getString(2));
                    objeto.put("nota", datos.getString(3));
                    objeto.put("lat",datos.getFloat(4));
                    objeto.put("lng",datos.getFloat(5));
                    objeto.put("zonas",listaZonasPuntos(datos.getString(1)));
                    lista.add(objeto);
                }
                

            }catch (SQLException e) {
            System.out.println("error SQLException en listaAllPuntosEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaAllPuntosEmpresas");
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
        JSONArray remolques = new JSONArray();
        JSONArray remolquesJ = new JSONArray();
        JSONObject r1 = new JSONObject();
        r1.put("ID",8); r1.put("Value","Tanque acero inoxidable");
        JSONObject r2 = new JSONObject();
        r2.put("ID",9); r2.put("Value","Tanque aluminio");
        JSONObject r3 = new JSONObject();
        r3.put("ID",10); r3.put("Value","Tanque lamina");
        remolques.add(r1);
        remolques.add(r2);
        remolques.add(r3);
        remolquesJ.add(r1);
        remolquesJ.add(r2);
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_tipocargue, desc_tipocargue FROM logycus360.tblTipoCargue WHERE nit_empresa = '830095213' " ;
                instruccion+=      " ORDER BY desc_tipocargue DESC ";	        

                
                st=conn.prepareStatement(instruccion);
                //Sst.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("desc", datos.getString(2));
                    if(datos.getInt(1)!=4){
                        objeto.put("remolques", remolques);
                    }else{
                        objeto.put("remolques", remolquesJ);
                    }
                    lista.add(objeto);
                }
                

            }catch (SQLException e) {
            System.out.println("error SQLException en listaCarguesEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaCarguesEmpresas");
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
    
    public static JSONArray listaCCostoEmpresas(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_centrocosto, desc_centrocosto FROM tblCentroCosto WHERE nit_empresa = ? " ;
                instruccion+=      " ORDER BY desc_centrocosto DESC ";	        

                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);

                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getString(1));
                    objeto.put("desc", datos.getString(2));
                    lista.add(objeto);
                }
                

            }catch (SQLException e) {
            System.out.println("error SQLException en listaCCostoEmpresas");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaCCostoEmpresas");
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
                    objeto.put("npunto", datos.getString(23));
                    objeto.put("zona", datos.getString(20));
                    objeto.put("bahia", datos.getInt(21));
                    objeto.put("turno", datos.getInt(22));
                    objeto.put("punto", datos.getString(24));
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
            System.out.println("error SQLException en listaEnturneEmpresasPorEstados");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaEnturneEmpresasPorEstados");
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
            System.out.println("error SQLException en listaPuntos");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaPuntos");
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
    
    public static JSONArray listaZonasPuntos(String punto) throws SQLException{
        JSONArray lista=new JSONArray();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                
                String instruccion="SELECT id_zona, id_punto, desc_zona nota_zona FROM tblZona WHERE id_punto = ?;" ;
                
                
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, punto);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("id_punto", datos.getString(2));
                    objeto.put("desc", datos.getString(3));
                    objeto.put("bahias", listaBahiasZona(datos.getInt(1)));
                    lista.add(objeto);
                }
                
                return lista;

            }catch (SQLException e) {
            System.out.println("error SQLException en listaZonasPuntos");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaZonasPuntos");
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
            System.out.println("error SQLException en listaBahiasZona");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaBahiasZona");
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
    
    public static JSONArray listaVehiculosByPropietario(String propietario) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT plca_equipo, eq.id_tipocarga, tc.desc_tipocarga, eq.id_tipoequipo, "+
                "te.desc_tipoequipo, eq.id_remolque, re.desc_remolque, " +
                "marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, poliza_equipo, " +
                "comp_poliza_equipo, fech_exp_poliz_equipo, fech_venc_poliz_equipo, soat_equipo, " +
                "fech_exp_soat_equipo, fech_venc_soat_equipo,  tecno_equipo, fech_exp_tecno_equipo, " +
                "fech_venc_tecno_equipo, cap_equipo, und_equipo " +
                "FROM tblEquipo AS eq INNER JOIN tblTipoCarga AS tc ON eq.id_tipocarga = tc.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON eq.id_tipoequipo = te.id_tipoequipo " +
                "INNER JOIN tblRemolque AS re ON eq.id_remolque = re.id_remolque " +
                "WHERE nit_empresa = ?;";        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, propietario);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("placa", datos.getString(1));
                    objeto.put("n_tipocarga", datos.getInt(2)); 
                    objeto.put("tipocarga", datos.getString(3));                    
                    objeto.put("n_tipoequipo", datos.getInt(4));
                    objeto.put("tipoequipo", datos.getString(5));
                    objeto.put("n_remolque", datos.getInt(6)); 
                    objeto.put("remolque", datos.getString(7));                    
                    objeto.put("marca", datos.getString(8));
                    objeto.put("modelo", datos.getString(9));
                    objeto.put("referencia", datos.getString(10));
                    objeto.put("trailer", datos.getString(11));
                    objeto.put("poliza", datos.getString(12));
                    objeto.put("compania", datos.getString(13));
                    objeto.put("exp_poliza", datos.getString(14));
                    objeto.put("vence_poliza", datos.getString(15));
                    objeto.put("soat", datos.getString(16));
                    objeto.put("exp_soat", datos.getString(17));
                    objeto.put("vence_soat", datos.getString(18));
                    objeto.put("tecno", datos.getString(19));
                    objeto.put("exp_tecno", datos.getString(20));
                    objeto.put("vence_tecno", datos.getString(21));
                    objeto.put("capacidad", datos.getString(22));     
                    objeto.put("unidad", datos.getString(23));     
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaVehiculosByPropietario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaVehiculosByPropietario");
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
    
    public static JSONArray listaConductoresByPropietario(String propietario) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT cod_conductor, tipo_doc_conductor, doc_conductor, num_lic_conductor, fech_exp_lic_conductor, " +
                "fech_venc_lic_conductor, nomb_conductor, apll_conductor, tel_conductor, " +
                "dire_conductor, mail_conductor, img_conductor " +
                "FROM tblConductor WHERE nit_empresa = ?;";        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, propietario);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("codigo", datos.getString(1));
                    objeto.put("tipo_doc", datos.getString(2));                    
                    objeto.put("doc", datos.getString(3));
                    objeto.put("lic", datos.getString(4));                    
                    objeto.put("exp_lic", datos.getString(5));
                    objeto.put("venc_lic", datos.getString(6));
                    objeto.put("nombre", datos.getString(7));
                    objeto.put("apellido", datos.getString(8));
                    objeto.put("n_completo", datos.getString(7) + " " + datos.getString(8));
                    objeto.put("telefono", datos.getString(9));
                    objeto.put("direccion", datos.getString(10));
                    objeto.put("mail", datos.getString(11));
                    objeto.put("imagen", datos.getString(12));             
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaConductoresByPropietario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaConductoresByPropietario");
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
    
    public static JSONArray listaEquiposConductoresByPropietario(String propietario, boolean activo) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT IFNULL(ec.id_equipoconductor, -1), eq.plca_equipo, ec.cod_conductor, ec.fech_equipoconductor, acti_equipoconductor, " +
                "CASE WHEN se.activ_servicio = 1 OR sen.activ_servicioenturne = 1 THEN 1 ELSE 0 END AS en_servicio, ec.regs_imei_conductor, " +
                "ec.pila_equipoconductor, ec.disp_equipoconductor " +
                "FROM tblEquipo AS eq LEFT JOIN tblEquipoConductor AS ec ON eq.plca_equipo = ec.plca_equipo " +
                "LEFT JOIN (SELECT id_servicio, id_equipoconductor, activ_servicio " +
                "FROM tblServicio WHERE activ_servicio = 1) AS se ON se.id_equipoconductor = ec.id_equipoconductor " +
                "LEFT JOIN (SELECT id_servicioenturne, id_equipoconductor, activ_servicioenturne " +
                "FROM tblServicioEnturne WHERE activ_servicioenturne = 1) AS sen ON sen.id_equipoconductor = ec.id_equipoconductor " +
                "WHERE eq.nit_empresa = ? ";   
                
                if(activo){
                    instruccion += "AND acti_equipoconductor = 1";
                }
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, propietario);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("id", datos.getInt(1));
                    objeto.put("placa", datos.getString(2));                    
                    objeto.put("conductor", datos.getString(3));
                    objeto.put("fecha_desde", datos.getString(4));                    
                    objeto.put("activo", datos.getBoolean(5));
                    objeto.put("en_servicio", datos.getString(6));
                    objeto.put("imei", datos.getString(7));
                    objeto.put("pila", datos.getString(8));
                    objeto.put("disponible", datos.getBoolean(9));
                    objeto.put("editar", false);
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaEquiposConductoresByPropietario");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaEquiposConductoresByPropietario");
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
    
    public static JSONArray listaFletes(String empresa) throws SQLException{
        JSONArray lista=new JSONArray();        
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="select id_punto_inicio, id_punto_fin, vlr_flete, desc_flete, und_flete from tblFlete where nit_empresa = ?; ";   

                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto= new JSONObject();
                    objeto.put("inicio", datos.getString(1));
                    objeto.put("fin", datos.getString(2));                    
                    objeto.put("valor", datos.getFloat(3));
                    objeto.put("descripcion", datos.getString(4));                    
                    objeto.put("unidad", datos.getString(5));
                    lista.add(objeto);                
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en listaFletes");
                    System.out.println(e.getMessage());
            }catch (Exception e){
                    System.out.println("error Exception en listaFletes");
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
    
    public static JSONArray ServiciosActivosByGeneradora(String id, String solitud, String servicio, String placa,
            String transportadora, String conductor, String inicio, String fin, int carga, int estado) throws SQLException{
        JSONArray lista= new JSONArray();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT * FROM logycus360.SelectServicioBySolicitud WHERE nit_empresa = ? AND id_estados < 11;";	        
                if(!solitud.isEmpty()){
                    instruccion += " AND id_solicitud like '%" + solitud + "%'";
                }
                if(!servicio.isEmpty()){
                    instruccion += " AND id_servicio like '%" + servicio + "%'";
                }
                if(!placa.isEmpty()){
                    instruccion += " AND plca_equipo like '%" + placa + "%'";
                }
                if(!conductor.trim().isEmpty()){
                    String[] nom = conductor.trim().split(" ");
                    instruccion += " AND ( ";
                        for(int i=0; i < nom.length; i++){
                            if(i== nom.length -1 ){
                                instruccion += " nombre_completo like '"+ nom[i]+"'";
                            }else{
                                instruccion += " nombre_completo like '"+ nom[i]+"' OR ";
                            }
                        }
                    instruccion += " )";
                }
                if(!inicio.isEmpty()){
                    instruccion += " AND id_inicio = '" + inicio + "'";
                }
                if(!fin.isEmpty()){
                    instruccion += " AND id_fin = '" + fin + "'";
                }
                if(carga != -1){
                    instruccion += " AND id_tipocargue = " + carga ;
                }
                if(estado != -1){
                    instruccion += " AND id_estado = " + estado ;
                }
                 System.out.println(instruccion);
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto = new JSONObject();
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    objeto.put("marca", datos.getString(4));
                    objeto.put("referencia", datos.getString(5));
                    objeto.put("modelo", datos.getString(6));
                    objeto.put("placa_rem", datos.getString(7));
                    objeto.put("lic_transito", datos.getString(8));
                    objeto.put("lic_transito_r", datos.getString(9));
                    objeto.put("poliza", datos.getString(10));
                    objeto.put("comp", datos.getString(11));
                    if(datos.getString(12) != null){
                        objeto.put("exp_poliza", formateadorDate.format(datos.getDate(12)));
                    }
                    if(datos.getString(13) != null){
                        objeto.put("vence_poliza", formateadorDate.format(datos.getDate(13)));
                    }
                    objeto.put("poliza_hc", datos.getString(14));
                    objeto.put("comp_hc", datos.getString(15));
                    if(datos.getString(16) != null){
                        objeto.put("exp_poliza_hc", formateadorDate.format(datos.getDate(16)));
                    }
                    if(datos.getString(17) != null){
                        objeto.put("vence_poliza_hc", formateadorDate.format(datos.getDate(17)));
                    }
                    objeto.put("soat", datos.getString(18));
                    if(datos.getString(19) != null){
                        objeto.put("exp_soat", formateadorDate.format(datos.getDate(19)));
                    }
                    if(datos.getString(20) != null){
                        objeto.put("vence_soat", formateadorDate.format(datos.getDate(20)));
                    }
                    objeto.put("tecno", datos.getString(21));
                    if(datos.getString(22) != null){
                        objeto.put("exp_tecno", formateadorDate.format(datos.getDate(22)));
                    }
                    if(datos.getString(23) != null){
                        objeto.put("vence_tecno", formateadorDate.format(datos.getDate(23)));
                    }
                    
                    objeto.put("url_conductor", datos.getString(24));
                    objeto.put("nombre", datos.getString(25));
                    objeto.put("apellido", datos.getString(26));
                    objeto.put("nombre_completo", datos.getString(27));
                    objeto.put("tipo_doc", datos.getString(28));
                    objeto.put("doc", datos.getString(29));
                    objeto.put("licencia", datos.getString(30));
                    if(datos.getString(31) != null){
                        objeto.put("exp_lic", formateadorDate.format(datos.getDate(31)));
                    }
                    if(datos.getString(32) != null){
                        objeto.put("vence_lic", formateadorDate.format(datos.getDate(32)));
                    }
                    objeto.put("telefono", datos.getString(33));
                    objeto.put("direccion", datos.getString(34));
                    objeto.put("tipo_carga", datos.getString(35));
                    objeto.put("tipo_remolque", datos.getString(37));
                    objeto.put("tipo_equipo", datos.getString(39));
                    objeto.put("tipo_cargue", datos.getString(41));
                    objeto.put("turno_cargue", datos.getInt(43));
                    objeto.put("turno_descague", datos.getInt(44));
                    objeto.put("ticket_cargue", datos.getString(45));
                    objeto.put("ticket_descargue", datos.getString(46));
                    objeto.put("reg_logycus", datos.getString(48));
                    objeto.put("reg_enturnex", datos.getString(49));
                    objeto.put("nit_generador", datos.getString(50));
                    objeto.put("generador", datos.getString(51));
                    objeto.put("nit_transportadora", datos.getString(52));
                    objeto.put("transportadora", datos.getString(53));
                    objeto.put("url_generador", datos.getString(54));
                    objeto.put("url_transportadora", datos.getString(55));
                    objeto.put("id_inicio", datos.getString(56));
                    objeto.put("desc_inicio", datos.getString(57));
                    objeto.put("lat_inicio", datos.getFloat(58));
                    objeto.put("lng_inicio", datos.getFloat(59));
                    objeto.put("id_fin", datos.getString(60));
                    objeto.put("desc_fin", datos.getString(61));
                    objeto.put("lat_fin", datos.getFloat(62));
                    objeto.put("lng_fin", datos.getFloat(63));
                    objeto.put("lat_actual", datos.getFloat(64));
                    objeto.put("lng_actual", datos.getFloat(65));
                    objeto.put("vel_actual", datos.getFloat(66));
                    objeto.put("pos_actual", datos.getString(67));
                    if(datos.getString(32) != null){
                        objeto.put("ult_actual", formateador.format(datos.getDate(68)));
                    }
                    objeto.put("cap_carg", datos.getInt(69));
                    if(datos.getString(70) != null){
                        objeto.put("min_carg", formateador.format(datos.getDate(70)));
                    }
                    if(datos.getString(71) != null){
                        objeto.put("max_carg", formateador.format(datos.getDate(71)));
                    }
                    if(datos.getString(72) != null){
                        objeto.put("min_desc", formateador.format(datos.getDate(72)));
                    }
                    if(datos.getString(73) != null){
                        objeto.put("max_desc", formateador.format(datos.getDate(73)));
                    }
                    objeto.put("operacion", datos.getInt(74));
                    objeto.put("guia", datos.getString(75));
                    objeto.put("nota", datos.getString(76));
                    objeto.put("desc_tarifa", datos.getString(77));
                    objeto.put("tarifa", datos.getInt(78));
                    objeto.put("id_estado", datos.getInt(79));
                    objeto.put("estado", datos.getString(80));
                    lista.add(objeto);
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en ServiciosActivosByGeneradora");
                    System.out.println(e.toString());
            }catch (Exception e){
                    System.out.println("error Exception en ServiciosActivosByGeneradora");
                    System.out.println(e.toString());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return lista;
    }

    
    public static JSONArray ServiciosActivosByTransportadora(String id, String solitud, String servicio, String placa,
            String transportadora, String conductor, String inicio, String fin, int carga, int estado) throws SQLException{
        JSONArray lista= new JSONArray();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT * FROM logycus360.SelectServicioBySolicitud WHERE nit_transportadora = ? AND id_estados < 11 ";	        
                if(!solitud.isEmpty()){
                    instruccion += " AND id_solicitud like '%" + solitud + "%'";
                }
                if(!servicio.isEmpty()){
                    instruccion += " AND id_servicio like '%" + servicio + "%'";
                }
                if(!placa.isEmpty()){
                    instruccion += " AND plca_equipo like '%" + placa + "%'";
                }
                if(!conductor.trim().isEmpty()){
                    String[] nom = conductor.trim().split(" ");
                    instruccion += " AND ( ";
                        for(int i=0; i < nom.length; i++){
                            if(i== nom.length -1 ){
                                instruccion += " nombre_completo like '"+ nom[i]+"'";
                            }else{
                                instruccion += " nombre_completo like '"+ nom[i]+"' OR ";
                            }
                        }
                    instruccion += " )";
                }
                if(!inicio.isEmpty()){
                    instruccion += " AND id_inicio = '" + inicio + "'";
                }
                if(!fin.isEmpty()){
                    instruccion += " AND id_fin = '" + fin + "'";
                }
                if(carga != -1){
                    instruccion += " AND id_tipocargue = " + carga ;
                }
                if(estado != -1){
                    instruccion += " AND id_estado = " + estado ;
                }
                System.out.println(instruccion);
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    JSONObject objeto = new JSONObject();
                    objeto.put("servicio", datos.getString(1));
                    objeto.put("solicitud", datos.getString(2));
                    objeto.put("placa", datos.getString(3));
                    objeto.put("marca", datos.getString(4));
                    objeto.put("referencia", datos.getString(5));
                    objeto.put("modelo", datos.getString(6));
                    objeto.put("placa_rem", datos.getString(7));
                    objeto.put("lic_transito", datos.getString(8));
                    objeto.put("lic_transito_r", datos.getString(9));
                    objeto.put("poliza", datos.getString(10));
                    objeto.put("comp", datos.getString(11));
                    if(datos.getString(12) != null){
                        objeto.put("exp_poliza", formateadorDate.format(datos.getDate(12)));
                    }
                    if(datos.getString(13) != null){
                        objeto.put("vence_poliza", formateadorDate.format(datos.getDate(13)));
                    }
                    objeto.put("poliza_hc", datos.getString(14));
                    objeto.put("comp_hc", datos.getString(15));
                    if(datos.getString(16) != null){
                        objeto.put("exp_poliza_hc", formateadorDate.format(datos.getDate(16)));
                    }
                    if(datos.getString(17) != null){
                        objeto.put("vence_poliza_hc", formateadorDate.format(datos.getDate(17)));
                    }
                    objeto.put("soat", datos.getString(18));
                    if(datos.getString(19) != null){
                        objeto.put("exp_soat", formateadorDate.format(datos.getDate(19)));
                    }
                    if(datos.getString(20) != null){
                        objeto.put("vence_soat", formateadorDate.format(datos.getDate(20)));
                    }
                    objeto.put("tecno", datos.getString(21));
                    if(datos.getString(22) != null){
                        objeto.put("exp_tecno", formateadorDate.format(datos.getDate(22)));
                    }
                    if(datos.getString(23) != null){
                        objeto.put("vence_tecno", formateadorDate.format(datos.getDate(23)));
                    }
                    
                    objeto.put("url_conductor", datos.getString(24));
                    objeto.put("nombre", datos.getString(25));
                    objeto.put("apellido", datos.getString(26));
                    objeto.put("nombre_completo", datos.getString(27));
                    objeto.put("tipo_doc", datos.getString(28));
                    objeto.put("doc", datos.getString(29));
                    objeto.put("licencia", datos.getString(30));
                    if(datos.getString(31) != null){
                        objeto.put("exp_lic", formateadorDate.format(datos.getDate(31)));
                    }
                    if(datos.getString(32) != null){
                        objeto.put("vence_lic", formateadorDate.format(datos.getDate(32)));
                    }
                    objeto.put("telefono", datos.getString(33));
                    objeto.put("direccion", datos.getString(34));
                    objeto.put("tipo_carga", datos.getString(35));
                    objeto.put("tipo_remolque", datos.getString(37));
                    objeto.put("tipo_equipo", datos.getString(39));
                    objeto.put("tipo_cargue", datos.getString(41));
                    objeto.put("turno_cargue", datos.getInt(43));
                    objeto.put("turno_descague", datos.getInt(44));
                    objeto.put("ticket_cargue", datos.getString(45));
                    objeto.put("ticket_descargue", datos.getString(46));
                    objeto.put("reg_logycus", datos.getString(48));
                    objeto.put("reg_enturnex", datos.getString(49));
                    objeto.put("nit_generador", datos.getString(50));
                    objeto.put("generador", datos.getString(51));
                    objeto.put("nit_transportadora", datos.getString(52));
                    objeto.put("transportadora", datos.getString(53));
                    objeto.put("url_generador", datos.getString(54));
                    objeto.put("url_transportadora", datos.getString(55));
                    objeto.put("id_inicio", datos.getString(56));
                    objeto.put("desc_inicio", datos.getString(57));
                    objeto.put("lat_inicio", datos.getFloat(58));
                    objeto.put("lng_inicio", datos.getFloat(59));
                    objeto.put("id_fin", datos.getString(60));
                    objeto.put("desc_fin", datos.getString(61));
                    objeto.put("lat_fin", datos.getFloat(62));
                    objeto.put("lng_fin", datos.getFloat(63));
                    objeto.put("lat_actual", datos.getFloat(64));
                    objeto.put("lng_actual", datos.getFloat(65));
                    objeto.put("vel_actual", datos.getFloat(66));
                    objeto.put("pos_actual", datos.getString(67));
                    if(datos.getString(68) != null){
                        objeto.put("ult_actual", formateador.format(datos.getDate(68)));
                    }
                    objeto.put("cap_carg", datos.getInt(69));
                    if(datos.getString(70) != null){
                        objeto.put("min_carg", formateador.format(datos.getDate(70)));
                    }
                    if(datos.getString(71) != null){
                        objeto.put("max_carg", formateador.format(datos.getDate(71)));
                    }
                    if(datos.getString(72) != null){
                        objeto.put("min_desc", formateador.format(datos.getDate(72)));
                    }
                    if(datos.getString(73) != null){
                        objeto.put("max_desc", formateador.format(datos.getDate(73)));
                    }
                    objeto.put("operacion", datos.getInt(74));
                    objeto.put("guia", datos.getString(75));
                    objeto.put("nota", datos.getString(76));
                    objeto.put("desc_tarifa", datos.getString(77));
                    objeto.put("tarifa", datos.getInt(78));
                    objeto.put("id_estado", datos.getInt(79));
                    objeto.put("estado", datos.getString(80));
                    lista.add(objeto);
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en ServiciosActivosByTransportadora");
                    System.out.println(e.toString());
            }catch (Exception e){
                    System.out.println("error Exception en ServiciosActivosByTransportadora");
                    System.out.println(e.toString());
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

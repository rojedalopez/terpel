package datos.get;

import bean.Servicio;
import bean.Solicitud;
import static datos.Aplicacion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.json.simple.JSONObject;

public class Objetos {
    
    static SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat formateadorDate = new SimpleDateFormat("yyyy/MM/dd");
    
    public static JSONObject DatosEmpresaBySolicitud(String id) throws SQLException{
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT  e.nit_empresa, razn_soci_empresa, url_img_empresa, dir_empresa, tel_empresa " +
                "FROM tblSolicitud AS s INNER JOIN tblEmpresa AS e ON s.nit_empresa = e.nit_empresa " +
                "WHERE id_solicitud = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    retorno.put("nit", datos.getString(1));
                    retorno.put("empresa", datos.getString(2));
                    retorno.put("url", datos.getString(3));
                    retorno.put("direccion", datos.getString(4));
                    retorno.put("telefono", datos.getString(5));                    
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
        return retorno;
    }
    
    
    public static Solicitud DatosSolicitud(String id) throws SQLException{
        Solicitud retorno= new Solicitud();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT  id_solicitud, fech_carg_solicitud, fech_max_carg_solicitud, fech_desc_solicitud, fech_max_desc_solicitud, \n" +
                "p_i.desc_punto, p_f.desc_punto, cant_eqpos_solicitud, desc_tipocargue, IFNULL(desc_tari_solicitud,'-'), IFNULL(not_det_solicitud,'-'), kms_esti_solicitud, em.razn_soci_empresa, url_img_empresa \n" +
                "FROM tblSolicitud AS s \n" +
                "INNER JOIN tblPunto AS p_i ON p_i.id_punto = s.id_punto_inicio\n" +
                "INNER JOIN tblPunto AS p_f ON p_f.id_punto = s.id_punto_fin\n" +
                "INNER JOIN tblTipoCargue AS tc ON tc.id_tipocargue = s.id_tipocargue\n" +
                "INNER JOIN tblEmpresa AS em ON em.nit_empresa = s.nit_empresa\n" +
                "WHERE id_solicitud =?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    retorno.setId( datos.getString(1));
                    retorno.setCarguemin(formateador.format(formateador.parse(datos.getString(2))));
                    retorno.setCarguemax( formateador.format(formateador.parse(datos.getString(3))));
                    retorno.setDescarguemin(formateador.format(formateador.parse(datos.getString(4))));
                    retorno.setDescarguemax( formateador.format(formateador.parse(datos.getString(5))));
                    retorno.setOrigen(datos.getString(6));
                    retorno.setDestino(datos.getString(7));
                    retorno.setNo_equipos(datos.getInt(8));
                    retorno.setNombre_cargue(datos.getString(9));
                    retorno.setDes_flete(datos.getString(10));
                    retorno.setNota_detalle(datos.getString(11));
                    retorno.setKms(datos.getString(12));
                    retorno.setEmpresa(datos.getString(13));
                    retorno.setUrlempresa(datos.getString(14));
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
        return retorno;
    }
    
    public static Servicio DatosVehiculo(String id) throws SQLException{
        Servicio retorno= new Servicio();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT plca_equipo, eq.id_tipocarga, tc.desc_tipocarga, eq.id_tipoequipo, "+
                "te.desc_tipoequipo, eq.id_remolque, re.desc_remolque, " +
                "marca_equipo, modelo_equipo, refer_equipo, plac_trailer_equipo, poliza_equipo, " +
                "comp_poliza_equipo, fech_exp_poliz_equipo, fech_venc_poliz_equipo, poliza_hc_equipo, exp_poliza_hc_equipo, vence_poliza_hc_equipo, comp_poliza_hc_equipo, soat_equipo, " +
                "fech_exp_soat_equipo, fech_venc_soat_equipo,  tecno_equipo, fech_exp_tecno_equipo, " +
                "fech_venc_tecno_equipo, cap_equipo, und_equipo, " +
                "lic_trans_equipo, lic_trans_r_equipo, razn_soci_empresa, url_img_empresa, eq.nit_empresa "+
                "FROM tblEquipo AS eq INNER JOIN tblTipoCarga AS tc ON eq.id_tipocarga = tc.id_tipocarga " +
                "INNER JOIN tblTipoEquipo AS te ON eq.id_tipoequipo = te.id_tipoequipo " +
                "INNER JOIN tblRemolque AS re ON eq.id_remolque = re.id_remolque " +
                "JOIN tblEmpresa AS em ON em.nit_empresa = eq.nit_empresa "+
                "WHERE plca_equipo = ? ";        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    retorno.setPlaca(datos.getString(1));
                    retorno.setTipo_carga(datos.getString(3));
                    retorno.setTipo_equipo( datos.getString(5));
                    retorno.setTipo_remolque(datos.getString(7));
                    retorno.setMarca(datos.getString(8));
                    retorno.setModelo(datos.getString(9));
                    retorno.setReferencia(datos.getString(10));
                    retorno.setPlaca_rem(datos.getString(11));
                    retorno.setPoliza(datos.getString(12));
                    retorno.setComp(datos.getString(13));
                    retorno.setExp_poliza(datos.getString(14));
                    retorno.setVence_poliza(datos.getString(15));
                    retorno.setPoliza_hc(datos.getString(16));
                    retorno.setComp_hc(datos.getString(17));
                    retorno.setExp_poliza_hc(datos.getString(18));
                    retorno.setVence_poliza_hc(datos.getString(19));
                    retorno.setSoat(datos.getString(20));
                    retorno.setExp_soat(datos.getString(21));
                    retorno.setVence_soat(datos.getString(22));
                    retorno.setTecno(datos.getString(23));
                    retorno.setExp_tecno(datos.getString(24));
                    retorno.setVence_tecno(datos.getString(25));
                    retorno.setCapacidad(datos.getInt(26));
                    retorno.setLic_transito(datos.getString(27));
                    retorno.setLic_transito_r(datos.getString(28));
                    retorno.setTransportadora(datos.getString(29));
                    retorno.setUrl_transportadora(datos.getString(30));
                    retorno.setNit_transportadora(datos.getString(31));
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
        return retorno;
    }
    
    public static Servicio DatosConductor(String id) throws SQLException{
        Servicio retorno= new Servicio();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT cod_conductor, tipo_doc_conductor, doc_conductor, num_lic_conductor, fech_exp_lic_conductor, " +
                "fech_venc_lic_conductor, nomb_conductor, apll_conductor, tel_conductor, " +
                "dire_conductor, mail_conductor, img_conductor, razn_soci_empresa, url_img_empresa, co.nit_empresa " +
                "FROM tblConductor AS co JOIN tblEmpresa AS em ON em.nit_empresa = co.nit_empresa WHERE cod_conductor = ? ";        

                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    retorno.setDoc(datos.getString(2));
                    retorno.setLicencia(datos.getString(4));                    
                    retorno.setExp_lic(datos.getString(5));
                    retorno.setVence_lic(datos.getString(6));
                    retorno.setNombre(datos.getString(7));
                    retorno.setApellido(datos.getString(8));
                    retorno.setNombre_completo(datos.getString(7) + " " + datos.getString(8));
                    retorno.setTelefono(datos.getString(9));
                    retorno.setDireccion(datos.getString(10));
                    retorno.setUrl_conductor(datos.getString(12));
                    retorno.setTransportadora(datos.getString(13));
                    retorno.setUrl_transportadora(datos.getString(14));
                    retorno.setNit_transportadora(datos.getString(15));
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
        return retorno;
    }
    
    public static Servicio DatosServicio(String id) throws SQLException{
        Servicio retorno= new Servicio();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT * FROM logycus360.SelectServicioBySolicitud WHERE id_servicio = ?;";	        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, id);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    
                    retorno.setServicio(datos.getString(1));
                    retorno.setSolicitud(datos.getString(2));
                    retorno.setPlaca(datos.getString(3));
                    retorno.setMarca(datos.getString(4));
                    retorno.setReferencia(datos.getString(5));
                    retorno.setModelo(datos.getString(6));
                    retorno.setPlaca_rem(datos.getString(7));
                    retorno.setLic_transito(datos.getString(8));
                    retorno.setLic_transito_r(datos.getString(9));
                    retorno.setPoliza(datos.getString(10));
                    retorno.setComp(datos.getString(11));
                    if(datos.getString(12) != null){
                        retorno.setExp_poliza(formateadorDate.format(datos.getDate(12)));
                    }
                    if(datos.getString(13) != null){
                        retorno.setVence_poliza(formateadorDate.format(datos.getDate(13)));
                    }
                    retorno.setPoliza_hc(datos.getString(14));
                    retorno.setComp_hc(datos.getString(15));
                    if(datos.getString(16) != null){
                        retorno.setExp_poliza_hc(formateadorDate.format(datos.getDate(16)));
                    }
                    if(datos.getString(17) != null){
                        retorno.setVence_poliza_hc(formateadorDate.format(datos.getDate(17)));
                    }
                    retorno.setSoat(datos.getString(18));
                    if(datos.getString(19) != null){
                        retorno.setExp_soat(formateadorDate.format(datos.getDate(19)));
                    }
                    if(datos.getString(20) != null){
                        retorno.setVence_soat(formateadorDate.format(datos.getDate(20)));
                    }
                    retorno.setTecno(datos.getString(21));
                    if(datos.getString(22) != null){
                        retorno.setExp_tecno(formateadorDate.format(datos.getDate(22)));
                    }
                    if(datos.getString(23) != null){
                        retorno.setVence_tecno(formateadorDate.format(datos.getDate(23)));
                    }
                    
                    retorno.setUrl_conductor(datos.getString(24));
                    retorno.setNombre(datos.getString(25));
                    retorno.setApellido(datos.getString(26));
                    retorno.setNombre_completo(datos.getString(27));
                    retorno.setTipo_doc(datos.getString(28));
                    retorno.setDoc(datos.getString(29));
                    retorno.setLicencia(datos.getString(30));
                    if(datos.getString(31) != null){
                        retorno.setExp_lic(formateadorDate.format(datos.getDate(31)));
                    }
                    if(datos.getString(32) != null){
                        retorno.setVence_lic(formateadorDate.format(datos.getDate(32)));
                    }
                    retorno.setTelefono(datos.getString(33));
                    retorno.setDireccion(datos.getString(34));
                    retorno.setTipo_carga(datos.getString(35));
                    retorno.setTipo_remolque(datos.getString(37));
                    retorno.setTipo_equipo(datos.getString(39));
                    retorno.setTipo_cargue(datos.getString(41));
                    retorno.setTurno_cargue(datos.getInt(43));
                    retorno.setTurno_descague(datos.getInt(44));
                    retorno.setTicket_cargue(datos.getString(45));
                    retorno.setTicket_descargue(datos.getString(46));
                    retorno.setReg_logycus(datos.getString(48));
                    retorno.setReg_enturnex(datos.getString(49));
                    retorno.setNit_generador(datos.getString(50));
                    retorno.setGenerador(datos.getString(51));
                    retorno.setNit_transportadora(datos.getString(52));
                    retorno.setTransportadora(datos.getString(53));
                    retorno.setUrl_generador(datos.getString(54));
                    retorno.setUrl_transportadora(datos.getString(55));
                    retorno.setId_inicio(datos.getString(56));
                    retorno.setDesc_inicio(datos.getString(57));
                    retorno.setLat_inicio(datos.getFloat(58));
                    retorno.setLng_inicio(datos.getFloat(59));
                    retorno.setId_fin(datos.getString(60));
                    retorno.setDesc_fin(datos.getString(61));
                    retorno.setLng_fin(datos.getFloat(62));
                    retorno.setLng_fin(datos.getFloat(63));
                    retorno.setLng_act(datos.getFloat(64));
                    retorno.setLng_act(datos.getFloat(65));
                    retorno.setLng_act(datos.getFloat(66));
                    retorno.setPos_act(datos.getString(67));
                    if(datos.getString(68) != null){
                        retorno.setUlt_act(formateador.format(datos.getDate(68)));
                    }
                    retorno.setCapacidad(datos.getInt(69));
                    if(datos.getString(70) != null){
                        retorno.setMin_carg(formateador.format(datos.getDate(70)));
                    }
                    if(datos.getString(71) != null){
                        retorno.setMax_carg(formateador.format(datos.getDate(71)));
                    }
                    if(datos.getString(72) != null){
                        retorno.setMin_desc( formateador.format(datos.getDate(72)));
                    }
                    if(datos.getString(73) != null){
                        retorno.setMax_desc(formateador.format(datos.getDate(73)));
                    }
                    retorno.setOperacion(datos.getInt(74));
                    retorno.setGuia(datos.getString(75));
                    retorno.setNota(datos.getString(76));
                    retorno.setDesc_flete(datos.getString(77));
                    retorno.setVlr_flete(datos.getFloat(78));
                    
                }
                
            }catch (SQLException e) {
            System.out.println("error SQLException en ObtenerUsuario - ");
                    System.out.println(e.toString());
            }catch (Exception e){
                    System.out.println("error Exception en ObtenerUsuario - ");
                    System.out.println(e.toString());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
        return retorno;
    }
    
    
}

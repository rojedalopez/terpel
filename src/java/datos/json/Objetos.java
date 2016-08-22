package datos.json;

import static datos.Aplicacion.conexion;
import static datos.json.Listas.formateador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Objetos {
     public static JSONObject getServiciobyId(String servicio, int tipo) throws SQLException{
        JSONObject retorno= new JSONObject();       
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_servicio, p_i.desc_punto, p_i.lat_punto, p_i.lng_punto,  \n" +
                "p_f.desc_punto, p_f.lat_punto, p_f.lng_punto, fech_max_carg_solicitud, fech_max_desc_solicitud,  \n" +
                "orden_solicitud, not_det_solicitud, em.nit_empresa, em.razn_soci_empresa, em.url_img_empresa, tc.desc_tipocargue  \n" +
                "FROM tblSolicitud AS sol INNER JOIN tblServicio AS se \n" +
                "INNER JOIN tblPunto AS p_i ON sol.id_punto_inicio = p_i.id_punto  \n" +
                "INNER JOIN tblPunto AS p_f ON sol.id_punto_fin = p_f.id_punto  \n" +
                "INNER JOIN tblEmpresa AS em ON sol.nit_empresa = em.nit_empresa  \n" +
                "INNER JOIN tblTipoCargue AS tc ON tc.id_tipocargue = sol.id_tipocargue \n" +
                "WHERE id_servicio = ?;";        
                
                if(tipo == 2 ){
                    instruccion="SELECT id_servicioenturne, p_i.desc_punto, p_i.lat_punto, p_i.lng_punto, " +
                    "p_f.desc_punto, p_f.lat_punto, p_f.lng_punto, fech_m_carg_servicioenturne, fech_m_desc_servicioenturne, " +
                    "guia_servicioenturne, nota_servicioenturne, em.nit_empresa, em.razn_soci_empresa, em.url_img_empresa, tc.desc_tipocargue " +
                    "FROM tblServicioEnturne AS se INNER JOIN tblPunto AS p_i ON se.id_punto_inicio = p_i.id_punto " +
                    "INNER JOIN tblPunto AS p_f ON se.id_punto_fin = p_f.id_punto " +
                    "INNER JOIN tblEmpresa AS em ON se.nit_empresa = em.nit_empresa " +
                    "INNER JOIN tblTipoCargue AS tc ON tc.id_tipocargue = se.id_tipocargue "+
                    "WHERE id_servicioenturne = ?;";
                }
                st=conn.prepareStatement(instruccion);
                st.setString(1, servicio);
                datos=(ResultSet) st.executeQuery();
                if (datos.next()) {
                    retorno.put("servicio", datos.getString(1));
                    retorno.put("origen", datos.getString(2));
                    retorno.put("lat_origen", datos.getFloat(3));
                    retorno.put("lng_origen", datos.getFloat(4));
                    retorno.put("destino", datos.getString(5));
                    retorno.put("lat_destino", datos.getFloat(6));
                    retorno.put("lng_destino", datos.getFloat(7));
                    retorno.put("cargue",formateador.format(formateador.parse(datos.getString(8))));
                    retorno.put("descargue",formateador.format(formateador.parse(datos.getString(9))));
                    retorno.put("guia", datos.getString(10));
                    retorno.put("nota", datos.getString(11));
                    retorno.put("nit", datos.getString(12));
                    retorno.put("empresa", datos.getString(13));
                    retorno.put("url", datos.getString(14));
                    retorno.put("carga", datos.getString(15));
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
}

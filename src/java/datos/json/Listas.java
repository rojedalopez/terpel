
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
  
    public static String ObtenerDestinos(float lat, float lng, int tipo) throws SQLException{
        JSONArray lista=new JSONArray();        
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT id_equipoconductor, cod_conductor, ec.plca_equipo, lat_equipoconductor, long_equipoconductor, " +
                "velo_equipoconductor, ult_actualizacion, imei_conductor, id_tipocarga "+
                "FROM tblEquipoConductor AS ec INNER JOIN tblEquipo AS eq ON ec.plca_equipo = eq.plca_equipo "+
                "WHERE id_tipocarga = "+tipo+" AND " +
                "(acos(sin(radians("+lat+")) * sin(radians(lat_equipoconductor)) + " +
                "cos(radians("+lat+")) * cos(radians(lat_equipoconductor)) * " +
                "cos(radians("+lng+") - radians(long_equipoconductor))) * 6378)<0.10;";	        
                
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
                    objeto.put("ult_reporte", formateador.format(datos.getDate(7)));
                    objeto.put("imei", datos.getString(8));
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

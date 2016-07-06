package datos.save;

import datos.Aplicacion;
import datos.json.Listas;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Guardar {
    
    public static String SaveServicio(String origen, float lat_origen, float lng_origen, String destino, float lat_destino,
            float lng_destino, String fecha_cargue, String fecha_descarga, int no_equipos, int tipo_equipos, String orden, String empresa) throws ClassNotFoundException, SQLException{
            boolean b=false;
            Connection conn=null;
            PreparedStatement insertar=null;
        
            conn=Aplicacion.conexion();
            try (CallableStatement cs = conn.prepareCall("{CALL logycus360.new_solicitude(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)};")) {
                cs.setString(1, origen);
                cs.setFloat(2, lat_origen);
                cs.setFloat(3, lng_origen);
                cs.setString(4, destino);
                cs.setFloat(5, lat_destino);
                cs.setFloat(6, lng_destino);
                cs.setString(7, fecha_cargue);
                cs.setString(8, fecha_descarga);
                cs.setInt(9, no_equipos);
                cs.setInt(10, tipo_equipos);
                cs.setString(11, orden);
                cs.setString(12, empresa);
                cs.registerOutParameter(13, Types.INTEGER);
                cs.executeQuery();

                int retorno = cs.getInt(13);
                
                if(retorno==1){
                    return Listas.ObtenerDestinos(lat_origen, lng_origen, retorno);
                }else{
                    return "false";
                }

            }catch (SQLException e) {
                System.out.println("error SQLException en SAVE FORMACION USUARIO");
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println("error Exception en SAVE FORMACION USUARIO");
                System.out.println(e.getMessage());
            }finally{
                if(!conn.isClosed()){
                    conn.close();
                }
            }
            return "false";

    }
    
    
}

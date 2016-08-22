package datos;

import bean.Usuario;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import org.json.simple.JSONObject;

public class Aplicacion {
    
    static SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static Connection conexion() throws ClassNotFoundException{
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Nombre del servidor. localhost:3306 es la ruta y el puerto de la conexión MySQL
            //panamahitek_text es el nombre que le dimos a la base de datos
            String servidor = "jdbc:mysql://45.55.254.29:3306/logycus360?noAccessToProcedureBodies=true";
            //String servidor = "jdbc:mysql://localhost:3306/"+bd;
            //El root es el nombre de Usuario por default. No hay contraseña
            //Se inicia la conexión
            conn = DriverManager.getConnection(servidor, "logycus360_platf", "5c026fde033e58a1747437ee8468f4b624dab3ee");
                      
        }catch ( SQLException excepcionSql){ //excepcionSql = puede ponerle otro nombre
            System.out.println("error en la conexion a la base de datos"+excepcionSql.getMessage());           
        }
        return conn;
    }
    
        public static Usuario obtenerUsuario(String correo, String pass, int tipo) throws SQLException{
        Usuario u = null;
        Connection conn=null;
        PreparedStatement insertar=null;
        Statement stm=null;
        ResultSet datos=null;
             
                try{
                    conn=conexion();
                    String instruccion = "";
                    if(tipo==1){
                        instruccion= "SELECT salt_conductor, pass_conductor, cod_conductor, '', mail_conductor, nomb_conductor,  apll_conductor, " +
                                        "'', tel_conductor, img_conductor, '', 0, tipo_doc_conductor, doc_conductor, tipo_lic_conductor, num_lic_conductor, 0 " +
                                        "FROM logycus360.tblConductor WHERE mail_conductor = ? AND acti_conductor = 1;";
                    }else{
                        instruccion="SELECT salt_usuario, pass_usuario, cod_usuario, e.nit_empresa, mail_usuario, nomb_usuario, apll_usuario, " +
                                    "razn_soci_empresa, tel_empresa, url_img_usuario, url_img_empresa, id_rol, tipo_empresa " +
                                    "FROM tblUsuario AS u INNER JOIN tblEmpresa AS e ON e.nit_empresa = u.nit_empresa " +
                                    "WHERE mail_usuario = ? AND acti_empresa = 1;";
                    }
                    insertar=conn.prepareStatement(instruccion);
                    insertar.setString(1, correo);
                    datos=insertar.executeQuery();
                    if (datos.next()) {
                        //valido las credenciales
                        String hash = datos.getString(1);
                        String pasw = datos.getString(2);
                        if(pasw.equals(Metodos.sha512(pass, hash))){
                            //obtengo el Usuario
                            u = new Usuario();
                            u.setCodigo(datos.getString(3));
                            u.setNit(datos.getString(4));
                            u.setCorreo(datos.getString(5));
                            u.setNombre(datos.getString(6));
                            u.setApellido(datos.getString(7));
                            u.setRazon_social(datos.getString(8));
                            u.setTelefono(datos.getString(9));
                            u.setUrl_imagen(datos.getString(10));
                            u.setUrl_empresa(datos.getString(11));
                            u.setRol(datos.getInt(12));
                            u.setTipo(datos.getInt(13));
                            u.setMensaje("true");
                            return u;
                        }else{
                            u = new Usuario();
                            u.setCodigo("-1");
                            u.setMensaje("autenticacion");
 
                            return u;
                        }
 
                    }
                    datos.close();
                    conn.close();
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
                     
        return u;
    }

    public static JSONObject obtenerUsuarioJSON(String correo, String pass) throws SQLException{
        JSONObject u = null;
        Connection conn=null;
        PreparedStatement insertar=null;
        Statement stm=null;
        ResultSet datos=null;
             
                try{
                    conn=conexion();
                    String instruccion="SELECT c.cod_conductor, tipo_doc_conductor, doc_conductor, " +
                    "num_lic_conductor, nomb_conductor, apll_conductor, tel_conductor, mail_conductor, salt_conductor, pass_conductor, img_conductor, id_equipoconductor, e.plca_equipo, pila_equipoconductor, cap_equipo, und_equipo " +
                    "FROM tblConductor AS c LEFT JOIN tblEquipoConductor AS e ON c.cod_conductor = e.cod_conductor AND acti_equipoconductor = 1 LEFT JOIN tblEquipo AS eq ON eq.plca_equipo = e.plca_equipo WHERE mail_conductor = ? AND acti_conductor = 1;";
                    insertar=conn.prepareStatement(instruccion);
                    insertar.setString(1, correo);
                    datos=insertar.executeQuery();
                    if (datos.next()) {
                        //valido las credenciales
                        String hash = datos.getString(9);
                        String pasw = datos.getString(10);
                        if(pasw.equals(Metodos.sha512(pass, hash))){
                            //obtengo el Usuario
                            u = new JSONObject();
                            u.put("cod",datos.getString(1));
                            u.put("correo",datos.getString(8));
                            u.put("nombre",datos.getString(5));
                            u.put("apellido",datos.getString(6));
                            u.put("telefono",datos.getString(7));
                            u.put("tipo_doc",datos.getString(2));
                            u.put("doc",datos.getString(3));
                            u.put("num_lic",datos.getString(4));
                            u.put("url_imagen",datos.getString(11));
                            u.put("equipo_conductor",datos.getInt(12));
                            u.put("placa",datos.getString(13));
                            u.put("pila",datos.getBoolean(14));
                            u.put("capacidad",datos.getInt(15));
                            u.put("unidad",datos.getString(16));
                            u.put("mensaje","true");
                            
                            return u;
                        }else{
                            u = new JSONObject();
                            u.put("cod","-1");
                            u.put("mensaje","autenticacion");
 
                            return u;
                        }
 
                    }
                    datos.close();
                    conn.close();
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
                     
        return u;
    }
    
    public static boolean ActivarCuentaUsuario(String mail) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        try{
        conn=conexion();
        
        String instruccion="UPDATE tblUsuario SET tkn_ver_usuario = NULL, ver_usuario = 1 WHERE eml_usuario = ?;";
         
        insertar=conn.prepareStatement(instruccion);
        insertar.setString(1, mail);
                 
        if(insertar.executeUpdate()==1){
            b=true;
        }
         
        insertar.close();
        conn.close();
         
         
         
        }catch (SQLException ex) {
                System.out.println("error en InsertUsuario");
            System.out.println(ex.getMessage());
        }finally{
            if(!conn.isClosed()){
                conn.close();
            }
        }
         
        return b;
    }
    
        
    public static boolean ForgotPassword(String mail) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        
        try{
        conn=conexion();
        String hash = Metodos.RandomString(10, false);
        String instruccion="UPDATE tblUsuario SET tkn_ver_usuario = ?, hsh_usuario = NULL, pwd_usuario = NULL, ver_usuario = 0 WHERE eml_usuario = ?;";
         
        insertar=conn.prepareStatement(instruccion);
        
        insertar.setString(1, hash);
        insertar.setString(2, mail);         
        
        if(insertar.executeUpdate()==1){
            //Mails.SendMailForgot(mail, hash, "CAMBIO DE CONTRASEÑA", "CAMBIAR");
            b=true;
        }
         
        insertar.close();
        conn.close();
         
         
         
        }catch (SQLException ex) {
                System.out.println("error en InsertUsuario");
            System.out.println(ex.getMessage());
        }finally{
            if(!conn.isClosed()){
                conn.close();
            }
        }
         
        return b;
    }
    
    
    public static boolean validarReset(String mail, String tkn) throws  SQLException{
        Connection conn=null;
        PreparedStatement insertar=null;
        Statement stm=null;
        ResultSet datos=null;
             
            try{
                conn=conexion();
                String instruccion="SELECT eml_usuario FROM tblUsuario WHERE eml_usuario = ? AND tkn_ver_usuario = ? AND ver_usuario = 0;";
                insertar=conn.prepareStatement(instruccion);
                insertar.setString(1, mail);
                insertar.setString(2, tkn);
                datos=insertar.executeQuery();
                if (datos.next()) {
                    return true;
                }
                datos.close();
                conn.close();

            }catch (SQLException e) {
                System.out.println("error SQLException en ObtenerPropietario");
                        System.out.println(e.getMessage());
            }catch (Exception e){
                        System.out.println("error Exception en ObtenerPropietario");
                        System.out.println(e.getMessage());
            }finally{
                if(conn!=null){
                    if(!conn.isClosed()){
                        conn.close();
                    }
                }
            }
                     
        return false;
    }
    
     public static boolean ActivarUsuario(String nick, String passw) throws ClassNotFoundException, SQLException, InvalidKeyException{
        boolean b=false;
        Connection conn=null;
        PreparedStatement insertar=null;
        conn=conexion();
        try{
        
        String instruccion="UPDATE tblUsuario SET hsh_usuario = ?, pwd_usuario = ?, ver_usuario = 1, tkn_ver_usuario = NULL WHERE eml_usuario = ?;";
        
        String hash = Metodos.RandomString(25, true);
        String pass = Metodos.sha512(passw, hash);
        
        insertar=conn.prepareStatement(instruccion);
        insertar.setString(1, hash);
        insertar.setString(2, pass);
        insertar.setString(3, nick);
                
        if(insertar.executeUpdate()==1){
            b=true;
        }
        
        insertar.close();
        conn.close();
        
        
        
        }catch (SQLException ex) {
                System.out.println("error en InsertUsuario");
        	System.out.println(ex.getMessage());
        }finally{
            if(!conn.isClosed()){
                conn.close();
            }
        }
        
        return b;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP I7
 */
public class Aplicacion {
    
    public static Connection conexion() throws ClassNotFoundException{
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Nombre del servidor. localhost:3306 es la ruta y el puerto de la conexión MySQL
            //panamahitek_text es el nombre que le dimos a la base de datos
            String servidor = "jdbc:mysql://104.236.81.248:3306/logycus360?noAccessToProcedureBodies=true";
            //String servidor = "jdbc:mysql://localhost:3306/"+bd;
            //El root es el nombre de usuario por default. No hay contraseña
            //Se inicia la conexión
            conn = DriverManager.getConnection(servidor, "logycus360_platf", "5c026fde033e58a1747437ee8468f4b624dab3ee");
            System.out.println("conexion perfecta");           
        }catch ( SQLException excepcionSql){ //excepcionSql = puede ponerle otro nombre
            System.out.println("error en la conexion a la base de datos"+excepcionSql.getMessage());           
        }
        return conn;
    }
}

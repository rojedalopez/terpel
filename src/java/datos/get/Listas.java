/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.get;

import static datos.Aplicacion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SISTEMAS
 */
public class Listas {
    
    public static List<String> listaMailsDespachadores(String empresa) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT distinct mail_usuario FROM \n" +
                "tblUsuario AS us INNER JOIN tblAsocio AS aso ON us.nit_empresa = aso.nit_transportadora\n" +
                "WHERE id_rol = 3 AND aso.nit_empresa = '"+empresa+"';";        
                System.out.println(instruccion);
                
                st=conn.prepareStatement(instruccion);
                
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    System.out.println(datos.getString(1));
                    lista.add(datos.getString(1));                
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
    
    public static List<String> listaSolicitudes(String empresa) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="select * from tblSolicitud where enviado = 0;";        
                
                st=conn.prepareStatement(instruccion);
                
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    lista.add(datos.getString(1));                
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
    
    public static List<String> listaServicios(String empresa) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="select * from tblServicio where enviado = 0;";        
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    lista.add(datos.getString(1));                
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
    
    public static List<String> listaConductores(String empresa, int tipo) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="";        
                if(tipo==1){
                    instruccion="select cod_conductor from tblConductor where enviado = 0;";        
                }else{
                    instruccion="select cod_conductor from tblConductor where enviado_aprov = 0;";        
                }
                                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    lista.add(datos.getString(1));                
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
    
    public static List<String> listaEquiposs(String empresa, int tipo) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="";        
                if(tipo==1){
                    instruccion="select plca_equipo from tblEquipo where enviado = 0;";        
                }else{
                    instruccion="select plca_equipo from tblEquipo where enviado_aprov = 0;";        
                }
                
                st=conn.prepareStatement(instruccion);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    lista.add(datos.getString(1));                
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
    
    public static List<String> listaMailsDespachadoresEmpresa(String empresa) throws SQLException{
        List<String> lista=new ArrayList<>();
        PreparedStatement st = null;
        Connection conn=null;
        ResultSet datos=null;
        
            try{
                conn=conexion();
                String instruccion="SELECT distinct mail_usuario FROM tblUsuario WHERE id_rol = 3 AND nit_empresa = ?;";        
                
                st=conn.prepareStatement(instruccion);
                st.setString(1, empresa);
                datos=(ResultSet) st.executeQuery();
                while (datos.next()) {
                    lista.add(datos.getString(1));                
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
    
    
}

package datos;

import bean.Servicio;
import bean.Solicitud;
import datos.get.Listas;
import static datos.get.Listas.listaSolicitudes;
import static datos.save.Guardar.ActualizarCorreo;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class tarea {

    Timer timer;
    Timer timer2;
    Timer timer3;
    Timer timer4;
    Timer timer5;
    Timer timer6;
    
    Date fecha;
    boolean finTarea = false;

    public tarea ( ) {
        //se crea un planificador que planificara 2 tareas
        timer = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer.schedule ( new Tarea1 ( ) , 5000, 10000) ;
        
        timer2 = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer2.schedule ( new Tarea2 ( ) , 5000, 10000) ;
    
         timer3 = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer3.schedule ( new Tarea3 ( ) , 5000, 10000) ;
        
         timer4 = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer4.schedule ( new Tarea4 ( ) , 5000, 10000) ;
        
        timer5 = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer5.schedule ( new Tarea5 ( ) , 5000, 10000) ;
        
         timer6 = new Timer ( ) ;
        //la Tarea1 se ejecuta pasado 1 segundo y luego periódicamente cada segundo
        timer6.schedule ( new Tarea6 ( ) , 5000, 10000) ;
    }

    //Tarea1: Muestra el valor de un contador y lo incrementa

    //cuando el contador llega a 10 se desplanifica la tarea

    class Tarea1 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = listaSolicitudes(empresa);
                for(String num_solicitud : lista){
                    System.err.println("paso 1 en " + num_solicitud);
                    Solicitud solicitud = datos.get.Objetos.DatosSolicitud(num_solicitud);
                    System.err.println("paso 2 en " + num_solicitud);
                    List<String> correos = datos.get.Listas.listaMailsDespachadores(empresa);
                    System.err.println(correos.size() + " correos");
                    Mails.SendMailSolicitudTransportadoras(correos, solicitud);
                    System.err.println("paso 4 en " + num_solicitud);
                    ActualizarCorreo(num_solicitud, 1);
                    System.err.println("paso 5 en " + num_solicitud);
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Tarea2 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = Listas.listaServicios(empresa);
                for(String retorno : lista){
                    System.err.println("paso 1 en " + retorno);
                    Servicio servicio = datos.get.Objetos.DatosServicio(retorno);
                    System.err.println("paso 2 en " + retorno);
                    System.err.println("paso 2 en " + servicio.getNit_generador());
                    List<String> lista_ = datos.get.Listas.listaMailsDespachadoresEmpresa(servicio.getNit_generador());
                    System.err.println("paso 3 en " + lista_.size());
                    datos.Mails.SendMailServiciosGeneradores(lista_, servicio);
                    System.err.println("paso 4 en " + retorno);
                    ActualizarCorreo(retorno, 2);
                    System.err.println("paso 5 en " + retorno);
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Tarea3 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = Listas.listaEquiposs(empresa,1);
                for(String retorno : lista){
                    System.err.println("paso 1 en " + retorno);
                    Servicio servicio = datos.get.Objetos.DatosVehiculo(retorno);
                    List<String> lista_ = datos.get.Listas.listaMailsDespachadoresEmpresa(empresa);
                    System.err.println("paso 3 en " + lista_.size());
                    if(lista_.size()>0){
                        datos.Mails.SendMailVehiculoGeneradores(lista_, servicio);
                        System.err.println("paso 4 en " + retorno);
                        ActualizarCorreo(retorno, 3);
                        System.err.println("paso 5 en " + retorno);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Tarea4 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = Listas.listaConductores(empresa,1);
                for(String retorno : lista){
                    System.err.println("paso 1 en " + retorno);
                    Servicio servicio = datos.get.Objetos.DatosConductor(retorno);
                    List<String> lista_ = datos.get.Listas.listaMailsDespachadoresEmpresa(empresa);
                    System.err.println("paso 3 en " + lista_.size());
                    if(lista_.size()>0){
                        datos.Mails.SendMailConductoresGeneradores(lista_, servicio);
                        System.err.println("paso 4 en " + retorno);
                        ActualizarCorreo(retorno, 4);
                        System.err.println("paso 5 en " + retorno);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Tarea5 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = Listas.listaEquiposs(empresa,2);
                for(String retorno : lista){
                    System.err.println("paso 1 en " + retorno);
                    Servicio servicio = datos.get.Objetos.DatosVehiculo(retorno);
                    List<String> lista_ = datos.get.Listas.listaMailsDespachadoresEmpresa(servicio.getNit_transportadora());
                    System.err.println("paso 3 en " + lista_.size());
                    if(lista_.size()>0){
                        datos.Mails.SendMailVehiculoGeneradoresAprov(lista_, servicio);
                        System.err.println("paso 4 en " + retorno);
                        ActualizarCorreo(retorno, 5);
                        System.err.println("paso 5 en " + retorno);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class Tarea6 extends TimerTask {

        public void run ( ) {
            try {
                String empresa = "830095213";
                List<String> lista = Listas.listaConductores(empresa,2);
                for(String retorno : lista){
                    System.err.println("paso 1 en " + retorno);
                    Servicio servicio = datos.get.Objetos.DatosConductor(retorno);
                    System.out.println(servicio.getNit_transportadora());
                    List<String> lista_ = datos.get.Listas.listaMailsDespachadoresEmpresa(servicio.getNit_transportadora());
                    System.err.println("paso 3 en " + lista_.size());
                    if(lista_.size()>0){
                        datos.Mails.SendMailConductoresGeneradoresAprov(lista_, servicio);
                        System.err.println("paso 4 en " + retorno);
                        ActualizarCorreo(retorno, 6);
                        System.err.println("paso 5 en " + retorno);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(tarea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}





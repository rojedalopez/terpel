/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import bean.Servicio;
import datos.pushy.PushyAPI;
import datos.pushy.PushyPushRequest;
import bean.Solicitud;
import static datos.get.Objetos.DatosEmpresaBySolicitud;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author SISTEMAS
 */
public class Metodos {
        
    static SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat formateador_ = new SimpleDateFormat("yyyy/MM/dd hh:mm a");
        
    //CANCULA CON EL ALGORITMO SHA512
    public static String sha512(String usuario, String key) throws InvalidKeyException{
    	String sha="";
    	String secretAccessKey = key;
        String data = usuario;
        byte[] secretKey = secretAccessKey.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA512");
        Mac mac=null;
		try {
			mac = Mac.getInstance("HmacSHA512");
			mac.init(signingKey);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        byte[] bytes = data.getBytes();
        byte[] rawHmac = mac.doFinal(bytes);
        sha=Base64.encode(rawHmac);
    	return sha;
    }
    
   
    
    //GENERA UN NUMERO RAnDOM DE TAMAÑO X
    public static String RandomString(int tamano, boolean especiales){
        String dCase = "abcdefghijklmnopqrstuvwxyz";
        String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String sChar = "!@#$%^&*";
        String intChar = "0123456789";
        Random r = new Random();
        String pass = "";

        while (pass.length () != tamano){
            int rPick = r.nextInt(4);
            if (rPick == 0){
                int spot = r.nextInt(25);
                pass += dCase.charAt(spot);
            } else if (rPick == 1) {
                int spot = r.nextInt (25);
                pass += uCase.charAt(spot);
            } else if (rPick == 2 && especiales) {
                int spot = r.nextInt (7);
                pass += sChar.charAt(spot);
            } else if (rPick == 3){
                int spot = r.nextInt (9);
                pass += intChar.charAt (spot);
            }
        }
        return pass;
    }
    
    public static String remove(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ,.-_:;/?¡¿!&*+";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC              ";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
    
    public static int ObtenerMes(){
        Calendar cal = Calendar.getInstance();
        int thisMonth = cal.get(Calendar.MONTH) + 1;
        return thisMonth;
    }
    
    public static String calcular(String fecha_){

        Date fecha = null;

        try {
                fecha = formateador.parse(fecha_);
        } catch (ParseException e) {
                e.printStackTrace();
        }
                
        long[] vector = new long[4];

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        final long MILLSECS_PER_HOUR = 60 * 60 * 1000; //Milisegundos a la hora
        final long MILLSECS_PER_MINUTE = 60 * 1000; //Milisegundos al minuto
        final long MILLSECS_PER_SECOND = 1000; //Milisegundos al minuto
        Date hoy = new Date(); //Fecha de hoy

        long diferencia = (hoy.getTime() - fecha.getTime());
        long dias = diferencia / MILLSECS_PER_DAY;
        long horas = (diferencia % MILLSECS_PER_DAY) / MILLSECS_PER_HOUR;
        long minutos = (diferencia % MILLSECS_PER_HOUR) / MILLSECS_PER_MINUTE;
        long segundos = (diferencia % MILLSECS_PER_MINUTE) / MILLSECS_PER_SECOND;

        vector[3] = segundos;
        vector[2] = minutos;
        vector[1] = horas;
        vector[0] = dias;
        
        String desde = "";
        String[] medidas = {" dias", " horas", " minutos", " segundos"};
        for(int i=0; i<vector.length; i++){
            if(vector[i]!=0){
                    desde += vector[i] + "" + medidas[i];
                    break;
            }
        }

        return desde;
    }

    public static String calcular(int minutos){
  
        long[] vector = new long[4];

        final long MILLSECS_PER_DAY = 24 * 60; //Milisegundos al día
        final long MILLSECS_PER_HOUR = 60; //Milisegundos a la hora
        final long MILLSECS_PER_MINUTE = 1; //Milisegundos a la hora
        

        long dias = minutos / MILLSECS_PER_DAY;
        long horas = (minutos % MILLSECS_PER_DAY) / MILLSECS_PER_HOUR;
        long min = (minutos % MILLSECS_PER_HOUR) / MILLSECS_PER_MINUTE;

        vector[2] = min;
        vector[1] = horas;
        vector[0] = dias;
        
        String desde = "";
        String[] medidas = {" dia(s)", " hora(s)", " minuto(s)"};
        for(int i=0; i<vector.length; i++){
            if(vector[i]!=0){
                    desde += vector[i] + "" + medidas[i];
                    break;
            }
        }

        return desde;
    }
    
    
    public static int calcularHoras(String fecha_){

        Date fecha = null;

        try {
                fecha = formateador.parse(fecha_);
        } catch (ParseException e) {
                e.printStackTrace();
        }
                
        long[] vector = new long[4];

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        final long MILLSECS_PER_HOUR = 60 * 60 * 1000; //Milisegundos a la hora
        Date hoy = new Date(); //Fecha de hoy

        long diferencia = (hoy.getTime() - fecha.getTime());
        long horas = (diferencia % MILLSECS_PER_DAY) / MILLSECS_PER_HOUR;

        return (int)horas;
    }
    
    public static String SHA256(String valor) throws NoSuchAlgorithmException{
   	
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(valor.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();
    }
    
    public static void EnvioNotificacion(Servicio serv, JSONArray TOs) throws SQLException{
        // Prepare list of target registration IDs
	List<String> registrationIDs = new ArrayList<>();
        
       // Add your registration IDs here
        for(int i=0;i<TOs.size();i++){
            registrationIDs.add(TOs.get(i).toString());
            System.out.println(TOs.get(i).toString());
        }
        

	// Set payload (any object, it will be serialized to JSON)
	Map<String, String> payload = new HashMap<>();

	// Add "message" parameter to payload
	payload.put("servicio", serv.getServicio());
        payload.put("origen", serv.getDesc_inicio());
        payload.put("lat_origen", serv.getLat_inicio()+"");
        payload.put("lng_origen", serv.getLng_inicio()+"");
        payload.put("destino", serv.getDesc_fin());
        payload.put("lat_destino", serv.getLat_fin()+"");
        payload.put("lng_destino", serv.getLng_fin()+"");
        payload.put("cargue_min", serv.getMin_carg());
        payload.put("cargue_max", serv.getMax_carg());
        payload.put("descargue_min", serv.getMin_desc());
        payload.put("descargue_max", serv.getMax_desc());
        payload.put("orden", serv.getGuia());
        payload.put("nota_detalle", serv.getNota());
        payload.put("flete", serv.getDesc_flete());
        payload.put("vlr_flete", serv.getVlr_flete()+"");
        payload.put("cargue", serv.getTipo_cargue());
        payload.put("nit", serv.getNit_generador());
        payload.put("empresa", serv.getGenerador());
        payload.put("url", serv.getUrl_generador());

        System.out.println(payload);
        
	// Prepare the push request
	PushyPushRequest push = new PushyPushRequest(payload, registrationIDs.toArray(new String[registrationIDs.size()]));

	try {
		// Try sending the push notification
		PushyAPI.sendPush(push, 1);
	}
	catch (Exception exc) {
		// Error, print to console
		System.out.println(exc.toString());
	}
    }
    
     public static void EnvioNotificacionEnturne(Solicitud sol, String TO, String nit, String nombre, String url) throws SQLException{
        // Prepare list of target registration IDs
	List<String> registrationIDs = new ArrayList<>();
        
        registrationIDs.add(TO);
        

	// Set payload (any object, it will be serialized to JSON)
	Map<String, String> payload = new HashMap<>();

	// Add "message" parameter to payload
            payload.put("servicio", sol.getId());
            payload.put("origen", sol.getOrigen());
            payload.put("lat_origen", sol.getLat_origen()+"");
            payload.put("lng_origen", sol.getLng_origen()+"");
            payload.put("destino", sol.getDestino());
            payload.put("lat_destino", sol.getLat_destino()+"");
            payload.put("lng_destino", sol.getLng_destino()+"");
            payload.put("cargue", sol.getCarguemin());
            payload.put("descargue", sol.getDescarguemin());
            payload.put("carga", sol.getNombre_cargue());
            payload.put("guia", sol.getOrden());
            payload.put("nota", sol.getNota_detalle());
            payload.put("nit", nit);
            payload.put("empresa", nombre);
            payload.put("url", url);
        
        System.out.println(payload);
        
	// Prepare the push request
	PushyPushRequest push = new PushyPushRequest(payload, registrationIDs.toArray(new String[registrationIDs.size()]));

	try {
		// Try sending the push notification
		PushyAPI.sendPush(push, 2);
	}
	catch (Exception exc) {
		// Error, print to console
		System.out.println(exc.toString());
	}
    }

    public static void EnvioNotificacion(String TO, String zona, String bahia, String fecha, String retorno,
            String operacion, String servicio, int tipo) throws SQLException{
        // Prepare list of target registration IDs
	List<String> registrationIDs = new ArrayList<>();
        
        registrationIDs.add(TO);
        

	// Set payload (any object, it will be serialized to JSON)
	Map<String, String> payload = new HashMap<>();

	// Add "message" parameter to payload
	payload.put("zona", zona);
        payload.put("bahia", bahia);
        payload.put("fecha_estimada", fecha);
        payload.put("retorno", retorno);
        payload.put("operacion", operacion);
        payload.put("servicio", servicio);
        
        
	// Prepare the push request
	PushyPushRequest push = new PushyPushRequest(payload, registrationIDs.toArray(new String[registrationIDs.size()]));

	try {
		// Try sending the push notification
		PushyAPI.sendPush(push, tipo);
	}
	catch (Exception exc) {
		// Error, print to console
		System.out.println(exc.toString());
	}
    }

    public static void main(String[] args) throws SQLException {
        /*Solicitud sol = new Solicitud();
        sol.setId("00000001");
        sol.setOrigen("CASA");
        sol.setLat_origen((float)10.3443);
        sol.setLng_origen((float)-74.6454);
        sol.setDestino("OTRO");
        sol.setLat_destino((float)10.3453);
        sol.setLng_destino((float)-75.2345);
        sol.setCarguemin("2016-08-08 18:00");
        sol.setDescarguemin("2016-08-12 18:00");
        sol.setOrden("SOL-09876");
        sol.setNota_detalle("Prueba");
            
        EnvioNotificacionEnturne(sol, "83540c69033865e50b2d17", "123456789", "Bimbo.com", "http://www.brandemia.org/wp-content/uploads/2012/12/nuevo_logo_terpel.jpg");*/
        System.out.println(calcular(1));
    }
}

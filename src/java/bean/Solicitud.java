
package bean;


public class Solicitud {
    private String id;
    private String origen;
    private float lat_origen;
    private float lng_origen;
    private String destino;
    private float lat_destino;
    private float lng_destino;
    private String carguemin;
    private String carguemax;
    private String descarguemin;
    private String descarguemax;
    private String orden;
    private int cargue;
    private String nombre_cargue;
    private int no_equipos;
    private String des_flete;
    private String kms;
    private String nit;
    private String empresa;
    private String urlempresa;
    private String url;
    private String nota_detalle;
    private float flete;
    private String nota_pago;
    private float adelanto;

    public String getUrlempresa() {
        return urlempresa;
    }

    public void setUrlempresa(String urlempresa) {
        this.urlempresa = urlempresa;
    }

    
    public int getNo_equipos() {
        return no_equipos;
    }

    public void setNo_equipos(int no_equipos) {
        this.no_equipos = no_equipos;
    }

    public String getDes_flete() {
        return des_flete;
    }

    public void setDes_flete(String des_flete) {
        this.des_flete = des_flete;
    }

    public String getKms() {
        return kms;
    }

    public void setKms(String kms) {
        this.kms = kms;
    }

    
    public String getNombre_cargue() {
        return nombre_cargue;
    }

    public void setNombre_cargue(String nombre_cargue) {
        this.nombre_cargue = nombre_cargue;
    }
    

    public Solicitud() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public float getLat_origen() {
        return lat_origen;
    }

    public void setLat_origen(float lat_origen) {
        this.lat_origen = lat_origen;
    }

    public float getLng_origen() {
        return lng_origen;
    }

    public void setLng_origen(float lng_origen) {
        this.lng_origen = lng_origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public float getLat_destino() {
        return lat_destino;
    }

    public void setLat_destino(float lat_destino) {
        this.lat_destino = lat_destino;
    }

    public float getLng_destino() {
        return lng_destino;
    }

    public void setLng_destino(float lng_destino) {
        this.lng_destino = lng_destino;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public int getCargue() {
        return cargue;
    }

    public void setCargue(int cargue) {
        this.cargue = cargue;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCarguemin() {
        return carguemin;
    }

    public void setCarguemin(String carguemin) {
        this.carguemin = carguemin;
    }

    public String getCarguemax() {
        return carguemax;
    }

    public void setCarguemax(String carguemax) {
        this.carguemax = carguemax;
    }

    public String getDescarguemin() {
        return descarguemin;
    }

    public void setDescarguemin(String descarguemin) {
        this.descarguemin = descarguemin;
    }

    public String getDescarguemax() {
        return descarguemax;
    }

    public void setDescarguemax(String descarguemax) {
        this.descarguemax = descarguemax;
    }

    public String getNota_detalle() {
        return nota_detalle;
    }

    public void setNota_detalle(String nota_detalle) {
        this.nota_detalle = nota_detalle;
    }

    public float getFlete() {
        return flete;
    }

    public void setFlete(float flete) {
        this.flete = flete;
    }

    public String getNota_pago() {
        return nota_pago;
    }

    public void setNota_pago(String nota_pago) {
        this.nota_pago = nota_pago;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(float adelanto) {
        this.adelanto = adelanto;
    }
    
    
    
}

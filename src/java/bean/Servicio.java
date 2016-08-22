
package bean;

public class Servicio {
    private String servicio;
    private String solicitud;
    private String placa;
    private String marca;
    private String referencia;
    private String modelo; 
    private String placa_rem;
    private String lic_transito;
    private String lic_transito_r;
    private String poliza;
    private String comp;
    private String exp_poliza;
    private String vence_poliza;
    private String poliza_hc;
    private String comp_hc;
    private String exp_poliza_hc;
    private String vence_poliza_hc;
    private String soat;
    private String exp_soat;
    private String vence_soat;
    private String tecno;
    private String exp_tecno;
    private String vence_tecno;
    private String nombre;
    private String apellido;
    private String nombre_completo;
    private String tipo_doc;
    private String doc;
    private String licencia;
    private String exp_lic;
    private String vence_lic;
    private String telefono;
    private String direccion;
    private String tipo_carga;
    private String tipo_cargue;
    private String tipo_remolque;
    private String tipo_equipo;
    private int turno_cargue;
    private int turno_descague;
    private String ticket_cargue;
    private String ticket_descargue;
    private String reg_logycus;
    private String reg_enturnex;
    private String generador;
    private String transportadora;
    private String nit_generador;
    private String nit_transportadora;
    private String url_generador;
    private String url_transportadora;
    private String url_conductor;
    private String id_inicio;
    private String desc_inicio;
    private float lat_inicio;
    private float lng_inicio;
    private String id_fin;
    private String desc_fin;
    private float lat_fin;
    private float lng_fin;
    private float lat_act;
    private float lng_act;
    private float vel_act;
    private String pos_act;
    private String ult_act;
    private String min_carg;
    private String max_carg;
    private String min_desc;
    private String max_desc;
    private int operacion;
    private int capacidad;
    private String guia;
    private String nota;
    private String desc_flete;
    private float vlr_flete;
    
    public Servicio(){
        servicio = "";
        solicitud = "";
        placa = "";
        marca = "";
        referencia = "";
        modelo = ""; 
        placa_rem = "";
        lic_transito = "";
        lic_transito_r = "";
        poliza = "";
        comp = "";
        exp_poliza = "";
        vence_poliza = "";
        poliza_hc = "";
        comp_hc = "";
        exp_poliza_hc = "";
        vence_poliza_hc = "";
        soat = "";
        exp_soat = "";
        vence_soat = "";
        tecno = "";
        exp_tecno = "";
        vence_tecno = "";
        nombre = "";
        apellido = "";
        nombre_completo = "";
        tipo_doc = "";
        doc = "";
        licencia = "";
        exp_lic = "";
        vence_lic = "";
        telefono = "";
        direccion = "";
        tipo_carga = "";
        tipo_cargue = "";
        tipo_remolque = "";
        tipo_equipo = "";
        turno_cargue = 0;
        turno_descague = 0;
        ticket_cargue = "";
        ticket_descargue = "";
        reg_logycus = "";
        reg_enturnex = "";
        generador = "";
        transportadora = "";
        url_generador = "";
        url_transportadora = "";
        url_conductor = "";
    };

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getDesc_flete() {
        return desc_flete;
    }

    public void setDesc_flete(String desc_flete) {
        this.desc_flete = desc_flete;
    }

    public float getVlr_flete() {
        return vlr_flete;
    }

    public void setVlr_flete(float vlr_flete) {
        this.vlr_flete = vlr_flete;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getId_inicio() {
        return id_inicio;
    }

    public void setId_inicio(String id_inicio) {
        this.id_inicio = id_inicio;
    }

    public String getDesc_inicio() {
        return desc_inicio;
    }

    public void setDesc_inicio(String desc_inicio) {
        this.desc_inicio = desc_inicio;
    }

    public float getLat_inicio() {
        return lat_inicio;
    }

    public void setLat_inicio(float lat_inicio) {
        this.lat_inicio = lat_inicio;
    }

    public float getLng_inicio() {
        return lng_inicio;
    }

    public void setLng_inicio(float lng_inicio) {
        this.lng_inicio = lng_inicio;
    }

    public String getId_fin() {
        return id_fin;
    }

    public void setId_fin(String id_fin) {
        this.id_fin = id_fin;
    }

    public String getDesc_fin() {
        return desc_fin;
    }

    public void setDesc_fin(String desc_fin) {
        this.desc_fin = desc_fin;
    }

    public float getLat_fin() {
        return lat_fin;
    }

    public void setLat_fin(float lat_fin) {
        this.lat_fin = lat_fin;
    }

    public float getLng_fin() {
        return lng_fin;
    }

    public void setLng_fin(float lng_fin) {
        this.lng_fin = lng_fin;
    }

    public float getLat_act() {
        return lat_act;
    }

    public void setLat_act(float lat_act) {
        this.lat_act = lat_act;
    }

    public float getLng_act() {
        return lng_act;
    }

    public void setLng_act(float lng_act) {
        this.lng_act = lng_act;
    }

    public float getVel_act() {
        return vel_act;
    }

    public void setVel_act(float vel_act) {
        this.vel_act = vel_act;
    }

    public String getPos_act() {
        return pos_act;
    }

    public void setPos_act(String pos_act) {
        this.pos_act = pos_act;
    }

    public String getUlt_act() {
        return ult_act;
    }

    public void setUlt_act(String ult_act) {
        this.ult_act = ult_act;
    }

    public String getMin_carg() {
        return min_carg;
    }

    public void setMin_carg(String min_carg) {
        this.min_carg = min_carg;
    }

    public String getMax_carg() {
        return max_carg;
    }

    public void setMax_carg(String max_carg) {
        this.max_carg = max_carg;
    }

    public String getMin_desc() {
        return min_desc;
    }

    public void setMin_desc(String min_desc) {
        this.min_desc = min_desc;
    }

    public String getMax_desc() {
        return max_desc;
    }

    public void setMax_desc(String max_desc) {
        this.max_desc = max_desc;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }

    public String getNit_generador() {
        return nit_generador;
    }

    public void setNit_generador(String nit_generador) {
        this.nit_generador = nit_generador;
    }

    public String getNit_transportadora() {
        return nit_transportadora;
    }

    public void setNit_transportadora(String nit_transportadora) {
        this.nit_transportadora = nit_transportadora;
    }

    
    public String getGenerador() {
        return generador;
    }

    public String getUrl_generador() {
        return url_generador;
    }

    public String getUrl_conductor() {
        return url_conductor;
    }

    public void setUrl_conductor(String url_conductor) {
        this.url_conductor = url_conductor;
    }

    public void setUrl_generador(String url_generador) {
        this.url_generador = url_generador;
    }

    public String getUrl_transportadora() {
        return url_transportadora;
    }

    public void setUrl_transportadora(String url_transportadora) {
        this.url_transportadora = url_transportadora;
    }

    public void setGenerador(String generador) {
        this.generador = generador;
    }

    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }
    
    
    
    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca_rem() {
        return placa_rem;
    }

    public void setPlaca_rem(String placa_rem) {
        this.placa_rem = placa_rem;
    }

    public String getLic_transito() {
        return lic_transito;
    }

    public void setLic_transito(String lic_transito) {
        this.lic_transito = lic_transito;
    }

    public String getLic_transito_r() {
        return lic_transito_r;
    }

    public void setLic_transito_r(String lic_transito_r) {
        this.lic_transito_r = lic_transito_r;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getExp_poliza() {
        return exp_poliza;
    }

    public void setExp_poliza(String exp_poliza) {
        this.exp_poliza = exp_poliza;
    }

    public String getVence_poliza() {
        return vence_poliza;
    }

    public void setVence_poliza(String vence_poliza) {
        this.vence_poliza = vence_poliza;
    }

    public String getPoliza_hc() {
        return poliza_hc;
    }

    public void setPoliza_hc(String poliza_hc) {
        this.poliza_hc = poliza_hc;
    }

    public String getComp_hc() {
        return comp_hc;
    }

    public void setComp_hc(String comp_hc) {
        this.comp_hc = comp_hc;
    }

    public String getExp_poliza_hc() {
        return exp_poliza_hc;
    }

    public void setExp_poliza_hc(String exp_poliza_hc) {
        this.exp_poliza_hc = exp_poliza_hc;
    }

    public String getVence_poliza_hc() {
        return vence_poliza_hc;
    }

    public void setVence_poliza_hc(String vence_poliza_hc) {
        this.vence_poliza_hc = vence_poliza_hc;
    }

    public String getSoat() {
        return soat;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public String getExp_soat() {
        return exp_soat;
    }

    public void setExp_soat(String exp_soat) {
        this.exp_soat = exp_soat;
    }

    public String getVence_soat() {
        return vence_soat;
    }

    public void setVence_soat(String vence_soat) {
        this.vence_soat = vence_soat;
    }

    public String getTecno() {
        return tecno;
    }

    public void setTecno(String tecno) {
        this.tecno = tecno;
    }

    public String getExp_tecno() {
        return exp_tecno;
    }

    public void setExp_tecno(String exp_tecno) {
        this.exp_tecno = exp_tecno;
    }

    public String getVence_tecno() {
        return vence_tecno;
    }

    public void setVence_tecno(String vence_tecno) {
        this.vence_tecno = vence_tecno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getExp_lic() {
        return exp_lic;
    }

    public void setExp_lic(String exp_lic) {
        this.exp_lic = exp_lic;
    }

    public String getVence_lic() {
        return vence_lic;
    }

    public void setVence_lic(String vence_lic) {
        this.vence_lic = vence_lic;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo_carga() {
        return tipo_carga;
    }

    public void setTipo_carga(String tipo_carga) {
        this.tipo_carga = tipo_carga;
    }

    public String getTipo_cargue() {
        return tipo_cargue;
    }

    public void setTipo_cargue(String tipo_cargue) {
        this.tipo_cargue = tipo_cargue;
    }

    public String getTipo_remolque() {
        return tipo_remolque;
    }

    public void setTipo_remolque(String tipo_remolque) {
        this.tipo_remolque = tipo_remolque;
    }

    public String getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(String tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public int getTurno_cargue() {
        return turno_cargue;
    }

    public void setTurno_cargue(int turno_cargue) {
        this.turno_cargue = turno_cargue;
    }

    public int getTurno_descague() {
        return turno_descague;
    }

    public void setTurno_descague(int turno_descague) {
        this.turno_descague = turno_descague;
    }

    public String getTicket_cargue() {
        return ticket_cargue;
    }

    public void setTicket_cargue(String ticket_cargue) {
        this.ticket_cargue = ticket_cargue;
    }

    public String getTicket_descargue() {
        return ticket_descargue;
    }

    public void setTicket_descargue(String ticket_descargue) {
        this.ticket_descargue = ticket_descargue;
    }

    public String getReg_logycus() {
        return reg_logycus;
    }

    public void setReg_logycus(String reg_logycus) {
        this.reg_logycus = reg_logycus;
    }

    public String getReg_enturnex() {
        return reg_enturnex;
    }

    public void setReg_enturnex(String reg_enturnex) {
        this.reg_enturnex = reg_enturnex;
    }
    
    
}

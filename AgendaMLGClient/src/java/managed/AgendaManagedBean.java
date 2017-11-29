/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;


import client.Evento;
import client.UsuarioService_Service;
import client.Usuarios;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Angela Martina Padrón Pol & Emilio Sánchez Serrano
 */
@Named(value = "agendaManagedBean")
@SessionScoped
public class AgendaManagedBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/AgendaMLGServer-war/UsuarioService.wsdl")
    private UsuarioService_Service service;

    protected Integer ordenacion;
    protected Date fechaOrdenacion;
    protected Integer distanciaOrdenacion;
    
    protected Usuarios usuario;
    protected Evento eventoAModificar;
    
    protected String autenticacionEmailIntroducido;
    protected String mensajeError;
    
    protected List<Evento> lista;
    

    protected double localizacionLongitud;
    protected double localizacionLatitud;
    
    /*Variables de crear evento*/
    protected Evento nuevoEvento;
    protected Date fechainicio;
    protected Date fechafin;
    protected List<String> palabrasClave;
    protected String mensajeDeError;
    
    /*Variables de ver perfil*/
    protected String tipoUsuario;

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public AgendaManagedBean() {
    }
    
    @PostConstruct
    public void init() {
       usuario = new Usuarios();
       nuevoEvento = new Evento();
       this.lista =  this.verEventos(usuario);
    }
    
   

    public Integer getOrdenacion() {
        return ordenacion;
    }

    public void setOrdenacion(Integer ordenacion) {
        this.ordenacion = ordenacion;
    }

    public Date getFechaOrdenacion() {
        return fechaOrdenacion;
    }

    public void setFechaOrdenacion(Date fechaOrdenacion) {
        this.fechaOrdenacion = fechaOrdenacion;
    }

    public Integer getDistanciaOrdenacion() {
        return distanciaOrdenacion;
    }

    public void setDistanciaOrdenacion(Integer distanciaOrdenacion) {
        this.distanciaOrdenacion = distanciaOrdenacion;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getAutenticacionEmailIntroducido() {
        return autenticacionEmailIntroducido;
    }

    public void setAutenticacionEmailIntroducido(String autenticacionEmailIntroducido) {
        this.autenticacionEmailIntroducido = autenticacionEmailIntroducido;
    }

 
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public double getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    public void setLocalizacionLongitud(double localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }

    public double getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    public void setLocalizacionLatitud(double localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
    }
    
    public Evento getEventoAModificar() {
        return eventoAModificar;
    }

    public void setEventoAModificar(Evento eventoAModificar) {
        this.eventoAModificar = eventoAModificar;
    }

    public Evento getNuevoEvento() {
        return nuevoEvento;
    }

    public void setNuevoEvento(Evento nuevoEvento) {
        this.nuevoEvento = nuevoEvento;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public List<String> getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(List<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getMensajeDeError() {
        return mensajeDeError;
    }

    public void setMensajeDeError(String mensajeDeError) {
        this.mensajeDeError = mensajeDeError;
    }

    
    
    
    
    
    
    
    public void anadirPalabrasClaveEvento(){
        String pcEvento = ""; //Palabras clave del Evento
        
        for(String palabra : this.palabrasClave){
            pcEvento += palabra;    
            pcEvento += ";";
        }
        
       
        this.nuevoEvento.setPalabrasclave(pcEvento);
        
    }
    
    public void validarCamposCrearEventoYCrear(){
        if(this.fechainicio.after(fechafin)){
            this.mensajeDeError = "La fecha de fin debe ser posterior a la fecha de inicio";
        }else{
            nuevoEvento.setFechainicio(fechainicio);
            nuevoEvento.setFechafin(fechafin);
            crearEvento(nuevoEvento, usuario);
        }
    }

    public String verModificarEvento(Evento e){
        this.setEventoAModificar(e);
        return "/eventos/modificarEvento?faces-redirect=true";
    }

    public List<Evento> getLista() {
        return lista;
    }

    public void setLista(List<Evento> lista) {
        this.lista = lista;
    }
    
    public String verValidarEventos(){
        return "/eventos/listaEventosSinValidar?faces-redirect=true";
    }
    
    public boolean comprobarEstado(Evento e){
        return e.getEstado() == 0 && comprobarPeriodista();
    }
    
    public boolean comprobarPeriodista(){
        return this.usuario.getTipoUsuario() == 3;
    }
        
    public String iniciarSesion2(){
        usuario = iniciarSesion(autenticacionEmailIntroducido, usuario);
        if(usuario==null){
            mensajeError="El email no es correcto";
            return "/usuario/autenticacion?faces-redirect=true";
            
        }else if(autenticacionEmailIntroducido.isEmpty() || autenticacionEmailIntroducido==null){
            mensajeError="Debe introducir un email";
            return "/usuario/autenticacion?faces-redirect=true";
        }
        //return "/usuario/perfilUsuario?faces-redirect=true";
        lista = new ArrayList<>();
        lista.addAll(this.verEventos(usuario));
        return "/eventos/listaEventos?faces-redirect=true";
        //return "/usuario/perfilUsuario?faces-redirect=true";
    }
    
    public void ordenar(){
        if(this.ordenacion == 1){
            Collections.sort(lista, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getId().compareTo(o2.getId());
            }
            });
        } else if(this.ordenacion == 2){
            Collections.sort(lista, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getFechainicio().compareTo(o2.getFechainicio());
            }
            });
        } else if(this.ordenacion == 3){
            Collections.sort(lista, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getLocalizacion().compareTo(o2.getLocalizacion());
            }
            });
        } else if(this.ordenacion == 4){
            Collections.sort(lista, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getId().compareTo(o2.getId());
            }
            });
        }
    }
    
     public String verPerfilUsuario() {
        //TODO write your implementation code here:
        switch (this.usuario.getTipoUsuario()) {
            case 1:
                this.tipoUsuario = "Usuario Normal";
                break;
            case 2:
                this.tipoUsuario = "Superusuario";
                break;
            default:
                this.tipoUsuario = "Periodista";
                break;
        }
        return "/usuario/perfilUsuario?faces-redirect=true";
    }
      public String verListaEventos() {
        //TODO write your implementation code here:
        return "/eventos/listaEventos?faces-redirect=true";
    }
      
      public Date formateoFecha(XMLGregorianCalendar fechaAParsear){
        return fechaAParsear.toGregorianCalendar().getTime();

    }

    public java.util.List<client.Usuarios> obtenerUsuarios() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerUsuarios();
    }

    public String subirEvento() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.subirEvento();
    }

    private Usuarios iniciarSesion(String arg0, Usuarios arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.iniciarSesion(arg0, arg1, null);
    }

    public java.util.List<client.Evento> obtenerEventosDeUsuario(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosDeUsuario(arg0);
    }

    
    /*
    
    private List<Evento> buscarPorGeolocalizacion(){
        List<Evento> eventosCercanos = new ArrayList<>();
        List<Evento> todosEventos = obtenerEventos(null, null, null, 0, 0);
        
        for(int i =0; i<todosEventos.size();i++){
            int distancia = ditanciaAEvento(todosEventos.get(i), localizacionLatitud, localizacionLongitud);
            if(distancia<=distancia){
                eventosCercanos.add(todosEventos.get(i));
            }
            
        }
        return eventosCercanos;
    } 
    
    */

   

    public String validarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        //arg0.setEstado(1);
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.validarEvento(arg0); 
    }

    public java.util.List<client.Evento> obtenerEventosSinValidar() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosSinValidar();
    }

    public String modificarEvento() throws DatatypeConfigurationException {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        /*GregorianCalendar inicio = new GregorianCalendar();
        GregorianCalendar fin = new GregorianCalendar();
        inicio.setTime(this.fechaInicioAModificar);
        inicio.add(GregorianCalendar.DAY_OF_MONTH, +1);
        fin.setTime(this.fechaFinAModificar);
        fin.add(GregorianCalendar.DAY_OF_MONTH, +1);
        XMLGregorianCalendar date1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(inicio);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(fin);
        eventoAModificar.setFechainicio(date1);
        eventoAModificar.setFechafin(date2);*/
        lista.set(lista.indexOf(eventoAModificar), eventoAModificar);
        eventoAModificar.getFechainicio().setDate(eventoAModificar.getFechainicio().getDate()+ 1);
        eventoAModificar.getFechafin().setDate(eventoAModificar.getFechafin().getDate()+ 1);
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.modificarEvento(eventoAModificar);
    }

    public java.util.List<client.Evento> verEventos(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.verEventos(arg0);
    }

    public String borrarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        lista.remove(arg0);
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.borrarEvento(arg0);
    }

   

    public XMLGregorianCalendar fechaAGregorianCalendar(Date fecha) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fecha);
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return date2;
    }

    public String crearEvento(client.Evento arg0, client.Usuarios arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.crearEvento(arg0, arg1);
    }

    

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;


import client.Evento;
import client.UsuarioService_Service;
import client.Usuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Angela Martina Padrón Pol & Emilio Sánchez Serrano
 */
@Named(value = "testBean")
@SessionScoped
public class TestBean implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/AgendaMLGServer-war/UsuarioService.wsdl")
    private UsuarioService_Service service;
    private Evento evento;
    private Usuarios usuario;
    private List<Evento> eventosList;
    private List<Usuarios> usuariosList;
    protected List<String> palabrasClave;
    protected List<String> notificaciones;
    protected Integer ordenacion;
    protected Integer busqueda;
    protected Date fechaOrdenacion;
    protected Integer codigoPostalBuscar;
    protected String autenticacionEmailIntroducido;
    protected String mensaje;
    protected int cp;
    protected Date fecha;
    protected String email;
    protected String mail;
    protected String notificacion;

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    @PostConstruct
    public void init(){
        this.evento = new Evento();
        this.usuario = iniciarSesion("juanlopez@yahoo.es");
        this.eventosList = new ArrayList<>();
        this.usuariosList = new ArrayList();
    }
    
    public TestBean() {
        
    }
    
        
    public void anadirPalabrasClaveEvento(){
        String pcEvento = ""; //Palabras clave del Evento
        
        for(String palabra : this.palabrasClave){
            pcEvento += palabra;    
            pcEvento += ";";
        }
        
       
        this.evento.setPalabrasclave(pcEvento);
        mensaje = "Se han fijado correctamente las palabras clave";
    }

    public String validarCamposCrearEventoYCrear(){
        if(evento.getFechainicio().after(evento.getFechafin())){
            this.mensaje = "La fecha de fin debe ser posterior a la fecha de inicio";
        }else{
            Date aux = evento.getFechainicio();
            evento.setFechainicio(evento.getFechafin());
            evento.setFechafin(aux);
            crearEvento(evento, usuario);
            eventosList.add(evento);
            return verEventos_T();
        }
        return null;
    }
    
    public void ordenar(){
        if(this.ordenacion == 1){
            Collections.sort(eventosList, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getFechainicio().compareTo(o2.getFechainicio());
            }
            });
        } else if(this.ordenacion == 2){
            Collections.sort(eventosList, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getFechainicio().compareTo(o2.getFechainicio());
            }
            });
        } else if(this.ordenacion == 3){
            Collections.sort(eventosList, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getLocalizacion().compareTo(o2.getLocalizacion());
            }
            });
        } else if(this.ordenacion == 4){
            Collections.sort(eventosList, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getId().compareTo(o2.getId());
            }
            });
        }
    }
    
     
  public void buscarPor(){
        if(null!=this.busqueda)switch (this.busqueda) {
           
            case 1: //Por fecha
                if(fechaOrdenacion!=null){
                    List<Evento> listaFecha = buscarPorFecha(fechaOrdenacion);
                    eventosList.removeAll(eventosList);
                    listaFecha.forEach((e) -> {
                        if(usuario.getTipoUsuario()!=3){
                            if(e.getEstado()!=0){
                                eventosList.add(e);
                            }
                        }else{
                            eventosList.add(e);
                        } }
                );}
                break;
            case 2: //Por codigoPostal
                //Collections.copy(lista, buscarPorCP(codigoPostal));
                if(codigoPostalBuscar!=null){
                    List<Evento> listacp = buscarPorCP(codigoPostalBuscar);
                    eventosList.removeAll(eventosList);
                    listacp.forEach((e) -> {
                        if(usuario.getTipoUsuario()!=3){
                            if(e.getEstado()!=0){
                                eventosList.add(e);
                            }
                        }else{
                            eventosList.add(e);
                        }
            });}
                break;
                
            case 3: //todos los eventos
                eventosList.removeAll(eventosList);
                eventosList=verEventos(usuario);
                break;
            case 4:
                 List<Evento> listaPreferencias = listaEventosPreferencia(usuario);
                eventosList.removeAll(eventosList);
                listaPreferencias.forEach((e) -> {
                    if(usuario.getTipoUsuario()!=3){
                        if(e.getEstado()!=0){
                            eventosList.add(e);
                        }
                    }else{
                        eventosList.add(e);
                    } }
                );
                break;
            case 5: 
                ordenacion=4;
                ordenar();
                break;
        }
    }  
    public List<Evento> listaEventosPreferencia(Usuarios user){
        List<Evento> listaEventos = new ArrayList<>();
        boolean eventoAñadido;
        String[] preferencias = user.getPreferencias().split(";");
        
        for(Evento e: eventosList){
            String[] pC;
            if(e.getPalabrasclave()!=null ){
                pC = e.getPalabrasclave().split(";");
            
                eventoAñadido = false;

                for(int i = 0; i < preferencias.length-1 && !eventoAñadido; i++){
                    for(int j = 0; j < pC.length-1 && !eventoAñadido; j++){
                        if(pC[j].equals(preferencias[i])){
                            listaEventos.add(e);
                            eventoAñadido = true;
                        }
                    }
                }
            }
        }
        
        return listaEventos;
    } 
    private String crearEvento(client.Evento arg0, client.Usuarios arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.crearEvento(arg0, arg1);
    }

    //Devuelve una lista de eventos en base al tipo de u que accede a ellos
    public String verEventos_T(){
        Usuarios u = iniciarSesion("juanlopez@yahoo.es");
        this.eventosList = verEventos(usuario);
        return "/pages/listaEventos?faces-redirect=true";
    }
    
    private java.util.List<client.Evento> verEventos(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.verEventos(arg0);
    }
    
    public String modificarEvento_T(Evento evento) throws DatatypeConfigurationException {
        evento.getFechainicio().setDate(evento.getFechainicio().getDate()+ 1);
        evento.getFechafin().setDate(evento.getFechafin().getDate()+ 1);
        this.evento = evento;
        return "/pages/evento?faces-redirect=true";
    }

    public String modificarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        port.modificarEvento(arg0);
        return verEventos_T();
    }
    
    public String borrarEvento_T(Evento evento) {
        borrarEvento(evento);
        Usuarios u = iniciarSesion("juanlopez@yahoo.es");
        this.eventosList = obtenerEventosDeUsuario(u);
        return "/pages/listaEventos?faces-redirect=true";
    }

    private String borrarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.borrarEvento(arg0);
    }
    
    public String buscarPorCP_T(int i) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        this.eventosList = buscarPorCP(i);
        return "/pages/listaEventos?faces-redirect=true";
    }

    private java.util.List<client.Evento> buscarPorCP(java.lang.Integer arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.buscarPorCP(arg0);
    }
    
     public String buscarPorPreferencias_T() {
        this.usuario= iniciarSesion("juanlopez@yahoo.es");
        Usuarios u=iniciarSesion(this.autenticacionEmailIntroducido);
        this.eventosList = verEventos(usuario);
        this.eventosList = listaEventosPreferencia(u);
        return "/pages/listaEventos?faces-redirect=true";
    }
    
    public String buscarPorFecha_T(Date date) {
        this.eventosList = buscarPorFecha(date);
        return "/pages/listaEventos?faces-redirect=true";
    }

    private java.util.List<client.Evento> buscarPorFecha(java.util.Date arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.buscarPorFecha(arg0);
    }
    
    public String notificarUsuario_T() {
        Usuarios u = iniciarSesion(mail);
        notificarUsuario(u, notificacion);
        this.usuariosList = obtenerUsuarios();
        return "/pages/listaUsuarios?faces-redirect=true";
    }
    
    public String obtenerEventosDeUsuario_T(String s) {
        Usuarios u = iniciarSesion(s);
        this.eventosList = obtenerEventosDeUsuario(u);
        return "/pages/listaEventos?faces-redirect=true";
    }

    private java.util.List<client.Evento> obtenerEventosDeUsuario(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosDeUsuario(arg0);
    }
    
    public String obtenerEventosSinValidar_T() {
        this.eventosList = obtenerEventosSinValidar();
        return "/pages/listaEventos?faces-redirect=true";
    }

    private java.util.List<client.Evento> obtenerEventosSinValidar() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosSinValidar();
    }
    
    public String obtenerUsuarios_T() {
        this.usuariosList = obtenerUsuarios();
        return "/pages/listaUsuarios?faces-redirect=true";
    }

    private java.util.List<client.Usuarios> obtenerUsuarios() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerUsuarios();
    }
    
    public String rechazarEvento_T(Evento evento) {
        rechazarEvento(evento);
        return verEventos_T();
    }

    private String rechazarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.rechazarEvento(arg0);
    }
    
    public String validarEvento_T(Evento evento) {
        validarEvento(evento);
        return verEventos_T();
    }

    private String validarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.validarEvento(arg0);
    }

    public UsuarioService_Service getService() {
        return service;
    }

    public Evento getEvento() {
        return evento;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public List<Evento> getEventosList() {
        return eventosList;
    }

    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public List<String> getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(List<String> palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<String> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Integer getOrdenacion() {
        return ordenacion;
    }

    public void setOrdenacion(Integer ordenacion) {
        this.ordenacion = ordenacion;
    }

    public Integer getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(Integer busqueda) {
        this.busqueda = busqueda;
    }

    public Date getFechaOrdenacion() {
        return fechaOrdenacion;
    }

    public void setFechaOrdenacion(Date fechaOrdenacion) {
        this.fechaOrdenacion = fechaOrdenacion;
    }

    public Integer getCodigoPostalBuscar() {
        return codigoPostalBuscar;
    }

    public void setCodigoPostalBuscar(Integer codigoPostalBuscar) {
        this.codigoPostalBuscar = codigoPostalBuscar;
    }

    public String getAutenticacionEmailIntroducido() {
        return autenticacionEmailIntroducido;
    }

    public void setAutenticacionEmailIntroducido(String autenticacionEmailIntroducido) {
        this.autenticacionEmailIntroducido = autenticacionEmailIntroducido;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(String notificacion) {
        this.notificacion = notificacion;
    }

    private void notificarUsuario(client.Usuarios arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        port.notificarUsuario(arg0, arg1);
    }

    private Usuarios iniciarSesion(java.lang.String arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.iniciarSesion(arg0);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
}

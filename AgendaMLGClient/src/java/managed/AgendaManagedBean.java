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
import java.util.Arrays;
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
    protected Integer busqueda;
    protected Date fechaOrdenacion;
    protected Integer codigoPostalBuscar;
    
    protected Usuarios usuario;
    protected Evento eventoAModificar;
    
    protected String autenticacionEmailIntroducido;
    protected String mensajeError;
    
    protected List<Evento> lista;
    protected List<Evento> eventosSinValidar;
    protected List<String> notificacionesDelUsuario;
    
    
    /*Variables de crear evento*/
    protected Evento nuevoEvento;
    protected Date fechainicio;
    protected Date fechafin;
    protected List<String> palabrasClave;
    protected String mensajeDeError;
    protected String mensajeKW;
    /*Variables de ver perfil*/
    protected String tipoUsuario;

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    @PostConstruct
    public void init(){
        this.autenticacionEmailIntroducido = "";
        nuevoEvento=new Evento();
    }
    public AgendaManagedBean() {
        
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

    public String getMensajeKW() {
        return mensajeKW;
    }

    public void setMensajeKW(String mensajeKW) {
        this.mensajeKW = mensajeKW;
    }

    
    public void anadirPalabrasClaveEventoModificar(){
        String pcEvento = ""; //Palabras clave del Evento
        
        for(String palabra : this.palabrasClave){
            pcEvento += palabra;    
            pcEvento += ";";
        }
        
       
        this.eventoAModificar.setPalabrasclave(pcEvento);
        mensajeKW = "Se han fijado correctamente las palabras clave";
    }
    
    
    
    
    
    public void anadirPalabrasClaveEvento(){
        String pcEvento = ""; //Palabras clave del Evento
        
        for(String palabra : this.palabrasClave){
            pcEvento += palabra;    
            pcEvento += ";";
        }
        
       
        this.nuevoEvento.setPalabrasclave(pcEvento);
        mensajeKW = "Se han fijado correctamente las palabras clave";
    }
    
    public String validarCamposCrearEventoYCrear(){
        if(this.fechainicio.after(fechafin)){
            this.mensajeDeError = "La fecha de fin debe ser posterior a la fecha de inicio";
        }else{
            nuevoEvento.setFechainicio(fechainicio);
            nuevoEvento.setFechafin(fechafin);
            crearEvento(nuevoEvento, usuario);
            return verListaEventos();
        }
        return null;
    }

    public List<Evento> getEventosSinValidar() {
        return eventosSinValidar;
    }

    public void setEventosSinValidar(List<Evento> eventosSinValidar) {
        this.eventosSinValidar = eventosSinValidar;
    }

    public List<Evento> getLista() {
        return lista;
    }

    public void setLista(List<Evento> lista) {
        this.lista = lista;
    }

    public List<String> getNotificacionesDelUsuario() {
        return notificacionesDelUsuario;
    }

    public void setNotificacionesDelUsuario(List<String> notificacionesDelUsuario) {
        this.notificacionesDelUsuario = notificacionesDelUsuario;
    }
    
    public String verValidarEventos(){
        this.setEventosSinValidar(obtenerEventosSinValidar());
        return "/eventos/listaEventosSinValidar?faces-redirect=true";
    }

    public String verModificarEvento(Evento e){
        this.setEventoAModificar(e);
        return "/eventos/modificarEvento?faces-redirect=true";
    }
          
    public String verListaEventos() {
        //TODO write your implementation code here:
        this.setLista(verEventos(usuario));
        return "/eventos/listaEventos?faces-redirect=true";
    }
    public String verPerfilUsuario() {
        //TODO write your implementation code here:    
        for(String n : Arrays.asList(usuario.getNotificaciones().split("\\|\\|"))){
            notificacionesDelUsuario.add(n);
        }
        //las ultimas son las primeras
        Collections.reverse(notificacionesDelUsuario);
        
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
    
    public boolean comprobarEstado(Evento e){
        return e.getEstado() == 0 && comprobarPeriodista();
    }
    
    public boolean comprobarPeriodista(){
        return this.usuario.getTipoUsuario() == 3;
    }
    
    public boolean comprobarInvitado(){
        return this.usuario.getTipoUsuario() != 0;
    }
        
  public String iniciarSesion2(){
        usuario = iniciarSesion(autenticacionEmailIntroducido);
        if(usuario==null){
            mensajeError="El email no es correcto";
            return "/usuario/autenticacion?faces-redirect=true";
            
        }else if(autenticacionEmailIntroducido.isEmpty() || autenticacionEmailIntroducido==null){
            mensajeError="Debe introducir un email";
            return "/usuario/autenticacion?faces-redirect=true";
        }
        lista = new ArrayList<>();
        eventosSinValidar = new ArrayList<>();
        notificacionesDelUsuario = new ArrayList<>();
        return verListaEventos();
    }
    
    public String accesoInvitado(){
        this.setAutenticacionEmailIntroducido("anonimo@mail.com");
        
        return iniciarSesion2();
    }
    
    public String salir(){
        this.setAutenticacionEmailIntroducido("");
        return "/usuario/autenticacion?faces-redirect=true";
    }
   
    
    public void ordenar(){
        if(this.ordenacion == 1){
            Collections.sort(lista, new Comparator<Evento>() {
            @Override
            public int compare(Evento o1, Evento o2) {
                return o1.getFechainicio().compareTo(o2.getFechainicio());
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
    
   
  public void buscarPor(){
        if(null!=this.busqueda)switch (this.busqueda) {
           
            case 1: //Por fecha
                if(fechaOrdenacion!=null){
                    List<Evento> listaFecha = buscarPorFecha(fechaOrdenacion);
                    lista.removeAll(lista);
                    listaFecha.forEach((e) -> {
                        if(usuario.getTipoUsuario()!=3){
                            if(e.getEstado()!=0){
                                lista.add(e);
                            }
                        }else{
                            lista.add(e);
                        } }
                );}
                break;
            case 2: //Por codigoPostal
                //Collections.copy(lista, buscarPorCP(codigoPostal));
                if(codigoPostalBuscar!=null){
                    List<Evento> listacp = buscarPorCP(codigoPostalBuscar);
                    lista.removeAll(lista);
                    listacp.forEach((e) -> {
                        if(usuario.getTipoUsuario()!=3){
                            if(e.getEstado()!=0){
                                lista.add(e);
                            }
                        }else{
                            lista.add(e);
                        }
            });}
                break;
                
            case 3: //todos los eventos
                lista.removeAll(lista);
                lista=verEventos(usuario);
                break;
            case 4:
                 List<Evento> listaPreferencias = listaEventosPreferencia(usuario);
                lista.removeAll(lista);
                listaPreferencias.forEach((e) -> {
                    if(usuario.getTipoUsuario()!=3){
                        if(e.getEstado()!=0){
                            lista.add(e);
                        }
                    }else{
                        lista.add(e);
                    } }
                );
                break;
            case 5: 
                ordenacion=4;
                ordenar();
                break;
        }
    } 
    public List<Evento> listaEventosPreferencia(Usuarios usuario){
        List<Evento> listaEventos = new ArrayList<>();
        boolean eventoAñadido;
        String[] preferencias = usuario.getPreferencias().split(";");
        
        for(Evento evento: lista){
            String[] palabrasClave = evento.getPalabrasclave().split(";");
            eventoAñadido = false;
            
            for(int i = 0; i < preferencias.length-1 && !eventoAñadido; i++){
                for(int j = 0; j < palabrasClave.length-1 && !eventoAñadido; j++){
                    if(palabrasClave[j].equals(preferencias[i])){
                        listaEventos.add(evento);
                        eventoAñadido = true;
                    }
                }
            }
        }
        
        return listaEventos;
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

    private Usuarios iniciarSesion(String arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.iniciarSesion(arg0);
    }

    public java.util.List<client.Evento> obtenerEventosDeUsuario(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosDeUsuario(arg0);
    }

    public java.util.List<client.Evento> obtenerEventosSinValidar() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosSinValidar();
    }

    public String modificarEvento() throws DatatypeConfigurationException {
        if(eventoAModificar.getFechafin().compareTo(eventoAModificar.getFechainicio()) == -1){
            Date aux = eventoAModificar.getFechainicio();
            eventoAModificar.setFechainicio(eventoAModificar.getFechafin());
            eventoAModificar.setFechafin(aux);
        }
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

    private java.util.List<client.Evento> buscarPorCP(java.lang.Integer arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.buscarPorCP(arg0);
    }

    private java.util.List<client.Evento> buscarPorFecha(java.util.Date arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.buscarPorFecha(arg0);
    }
    
    
    public String preValidarEvento(Evento evento){
        Evento aux = null;
        for(Evento e : lista){
            if(e.getId() == evento.getId()){
                aux = e;
                break;
            }
        }
        if(aux != null){
            aux.setEstado(1);
        } else{
            //el evento no esta en la lista
        }        
        Usuarios u = evento.getEmailusuario();
        String notificacion = "Se ha VALIDADO su evento "+evento.getDescripcion()+".";
        notificarUsuario(u, notificacion);
        validarEvento(evento);
        return verValidarEventos();
    }
    
    public String validarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.validarEvento(arg0);
    }
    
    public String preRechazarEvento(Evento evento) {
        Evento aux = null;
        for(Evento e : lista){
            if(e.getId() == evento.getId()){
                aux = e;
                break;
            }
        }
        if(aux != null){
            lista.remove(aux);
        } else{
            //el evento no esta en la lista
        }
        Usuarios u = evento.getEmailusuario();
        String notificacion = "Se ha RECHAZADO su evento "+evento.getDescripcion()+".";
        notificarUsuario(u, notificacion);
        rechazarEvento(evento);
        return verValidarEventos();
    }

    private String rechazarEvento(client.Evento arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.rechazarEvento(arg0);
    }

    private void notificarUsuario(client.Usuarios arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        port.notificarUsuario(arg0, arg1);
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

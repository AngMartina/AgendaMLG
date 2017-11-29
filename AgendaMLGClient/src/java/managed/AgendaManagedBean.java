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
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
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
    

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public AgendaManagedBean() {
        this.autenticacionEmailIntroducido = "";
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
        if(autenticacionEmailIntroducido.isEmpty() || autenticacionEmailIntroducido==null || autenticacionEmailIntroducido==""){
            autenticacionEmailIntroducido="anonimo@mail.com";
        }
            
        usuario = iniciarSesion(autenticacionEmailIntroducido);
        
        
        if(usuario==null){
            mensajeError="El email no es correcto";
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
     
  /*  public String buscarPorPreferencias(){//Por preferencias
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
        return "/eventos/listaEventos?faces-redirect=true";
    }*/
   
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
   
    
     public String verPerfilUsuario() {
        //TODO write your implementation code here:
        return "/usuario/perfilUsuario?faces-redirect=true";
    }
      public String verListaEventos() {
        //TODO write your implementation code here:
        return "/eventos/listaEventos?faces-redirect=true";
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
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.UsuarioService_Service;
import client.Usuarios;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
    
    protected String autenticacionEmailIntroducido;
    protected String mensajeError;
    
    public String hola;
    
    protected long localizacionLongitud;
    protected long localizacionLatitud;
    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public AgendaManagedBean() {
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

    public long getLocalizacionLongitud() {
        return localizacionLongitud;
    }

    public void setLocalizacionLongitud(long localizacionLongitud) {
        this.localizacionLongitud = localizacionLongitud;
    }

    public long getLocalizacionLatitud() {
        return localizacionLatitud;
    }

    public void setLocalizacionLatitud(long localizacionLatitud) {
        this.localizacionLatitud = localizacionLatitud;
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
        
        return "/eventos/listaEventos?faces-redirect=true";
    }

    public java.util.List<client.Evento> obtenerEventos(java.lang.Integer arg0, client.Usuarios arg1, javax.xml.datatype.XMLGregorianCalendar arg2, int arg3, int arg4) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventos(arg0, arg1, arg2, arg3, arg4);
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

    private Usuarios iniciarSesion(java.lang.String arg0, client.Usuarios arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.iniciarSesion(arg0, arg1);
    }
    
    
}

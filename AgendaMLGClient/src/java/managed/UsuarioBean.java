/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import client.Evento;
import client.UsuarioService_Service;
import client.Usuarios;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Antonio
 */
@Named(value = "usuarioBean")
@RequestScoped
public class UsuarioBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/AgendaMLGServer-war/UsuarioService.wsdl")
    private UsuarioService_Service service;


    protected Usuarios usuarioLoggeado;
    protected List<Evento> eventosDelUsuario;
    
    @PostConstruct
    public void init(){
        //initusuario
        this.eventosDelUsuario = obtenerEventosDeUsuario(usuarioLoggeado);
    }

    public Usuarios getUsuarioLoggeado() {
        return usuarioLoggeado;
    }

    public void setUsuarioLoggeado(Usuarios usuario) {
        this.usuarioLoggeado = usuario;
    }

    public List<Evento> getEventosDelUsuario() {
        return eventosDelUsuario;
    }

    public void setEventosDelUsuario(List<Evento> eventosDelUsuario) {
        this.eventosDelUsuario = eventosDelUsuario;
    }
    
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    private java.util.List<client.Evento> obtenerEventosDeUsuario(client.Usuarios arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.obtenerEventosDeUsuario(arg0);
    }

   
    
    
}

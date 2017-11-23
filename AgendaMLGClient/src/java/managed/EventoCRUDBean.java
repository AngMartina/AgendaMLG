package managed;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import client.Evento;
import client.UsuarioService_Service;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Antonio
 */
@Named(value = "eventoCRUDBean")
@RequestScoped
public class EventoCRUDBean {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/AgendaMLGServer-war/UsuarioService.wsdl")
    private UsuarioService_Service service;
    
    protected Evento nuevoEvento;
    
    @PostConstruct
    public void init(){
        nuevoEvento = new Evento();
    }

    public Evento getNuevoEvento() {
        return nuevoEvento;
    }

    public void setNuevoEvento(Evento nuevoEvento) {
        this.nuevoEvento = nuevoEvento;
    }
    
   
    
    /**
     * Creates a new instance of EventoCRUDBean
     */
    public EventoCRUDBean() {
    }

    private String crearEvento(client.Evento arg0, client.Usuarios arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        client.UsuarioService port = service.getUsuarioServicePort();
        return port.crearEvento(arg0, arg1);
    }

    

   
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.managedbean;

import ejb.EventoFacade;
import ejb.UsuariosFacade;
import entity.Evento;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Antonio
 */
@Named(value = "eventoCRUDBean")
@RequestScoped
public class EventoCRUDBean {

    @EJB
    private UsuariosFacade usuariosFacade;

    @EJB
    private EventoFacade eventoFacade;
    
    protected Evento nuevoEvento;
    
    @PostConstruct
    public void init(){
        nuevoEvento = new Evento();
        //nuevoEvento.setEmailusuario(emailusuario);  /*Email del usuario que inició sesioón*/
    }

    public Evento getNuevoEvento() {
        return nuevoEvento;
    }

    public void setNuevoEvento(Evento nuevoEvento) {
        this.nuevoEvento = nuevoEvento;
    }
    
    public String subirEvento(){
        /*Codigo Antonio*/
        this.eventoFacade.create(nuevoEvento);
        return "ListaEventos";
    }
    
    /**
     * Creates a new instance of EventoCRUDBean
     */
    public EventoCRUDBean() {
    }
    
}

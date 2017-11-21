/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.managedbean;

import ejb.EventoFacade;
import entity.Evento;
import entity.Usuarios;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Antonio
 */
@Named(value = "usuarioBean")
@RequestScoped
public class UsuarioBean {

    @EJB
    private EventoFacade eventoFacade;
    
    

    protected Usuarios usuarioLoggeado;
    protected List<Evento> eventosDelUsuario;
    
    @PostConstruct
    public void init(){
        this.eventosDelUsuario = this.eventoFacade.EventosDeUsuario(usuarioLoggeado);
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
    
    
    
    
    
    
    
}

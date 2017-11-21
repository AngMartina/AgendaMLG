/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.managedbean;

import ejb.EventoFacade;
import ejb.UsuariosFacade;
import entity.Evento;
import entity.Usuarios;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Emilio
 */
@Named(value = "agendaManagedBean")
@SessionScoped
public class AgendaManagedBean implements Serializable {

    @EJB
    private EventoFacade eventoFacade;
    private UsuariosFacade usuarioFacade;
    /**
     * Creates a new instance of AgendaManagedBean
     */
    public AgendaManagedBean() {
    }
    
    public List<Usuarios> obtenerUsuarios(){
        return usuarioFacade.findAll();
    }
    
    public List<Evento> obtenerEventos(){
        return eventoFacade.findAll();
    }
    
   public String subirEvento(){
       return "crearEvento";
   }
}

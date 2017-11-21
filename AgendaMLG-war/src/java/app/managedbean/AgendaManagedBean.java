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
import java.util.Date;
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
    private UsuariosFacade usuariosFacade;

    @EJB
    private EventoFacade eventoFacade;
    
    
    protected Integer ordenacion;
    protected Date fechaOrdenacion;
    protected Integer distanciaOrdenacion;
    
    protected Usuarios usuario;
    
    protected String autenticacionEmailIntroducido;
    protected String autenticacionContrasennaIntroducida;
    protected String mensajeError;
    /**
     * Creates a new instance of AgendaManagedBean
     */
    public AgendaManagedBean() {
    }
    
    public List<Usuarios> obtenerUsuarios(){
            return usuariosFacade.findAll();
        
    }
    
    public List<Evento> obtenerEventos(){
       if(null==ordenacion){
           return eventoFacade.findAll();
       } else switch (ordenacion) {
            case 1:
                //Ordeno por preferencias
                return eventoFacade.buscarEventoPorPreferencias(usuario);
            case 2:
                //Ordeno por fecha
                return eventoFacade.buscarEventoPorFecha(fechaOrdenacion);
            case 3:
                //Ordeno por distancia
                return null; //FALTA POR HACER
            default:
                return eventoFacade.findAll();
        }
    }
    
   public String subirEvento(){
       return "crearEvento";
   }
   
    public String iniciarSesion() {

        Usuarios usuario;
        if (!autenticacionEmailIntroducido.isEmpty() && autenticacionEmailIntroducido != null) {
            usuario = usuariosFacade.findByEmail(autenticacionEmailIntroducido);
            if (usuario == null) {
                mensajeError = "EmailNoRegistrado";
                return "autenticacion";
            }
        } else {
            mensajeError = "PorFavorIntroduceElEmail";
            return "autenticacion";
        }

       
        return "listaEventos";

    }

    public String getAutenticacionEmailIntroducido() {
        return autenticacionEmailIntroducido;
    }

    public void setAutenticacionEmailIntroducido(String autenticacionEmailIntroducido) {
        this.autenticacionEmailIntroducido = autenticacionEmailIntroducido;
    }

    public String getAutenticacionContrasennaIntroducida() {
        return autenticacionContrasennaIntroducida;
    }

    public void setAutenticacionContrasennaIntroducida(String autenticacionContrasennaIntroducida) {
        this.autenticacionContrasennaIntroducida = autenticacionContrasennaIntroducida;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
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
   
   
   
   
   
}

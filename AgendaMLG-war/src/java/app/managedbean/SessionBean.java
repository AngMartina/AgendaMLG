/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.managedbean;

import ejb.EventoFacade;
import ejb.UsuariosFacade;
import entity.Usuarios;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Angela Martina Padrón Pol
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    @EJB
    private UsuariosFacade usuariosFacade;

    @EJB
    private EventoFacade eventoFacade;
    
    protected Usuarios usuario;
    
    protected String autenticacionEmailIntroducido;
    protected String autenticacionContrasennaIntroducida;
    protected String mensajeError;

    
    
    public SessionBean() {
    }

    public String iniciarSesion() {

        Usuarios usuario = null;
        if (!autenticacionEmailIntroducido.isEmpty() && autenticacionEmailIntroducido != null) {
            usuario = usuariosFacade.findByEmail(autenticacionEmailIntroducido);
            if (usuario == null) {
                mensajeError = "EmailNoRegistrado";
                return "Autenticacion";
            }
        } else {
            mensajeError = "PorFavorIntroduceElEmail";
            return "Autenticacion";
        }

       
        return "ListaEventos";

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
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import ejb.EventoFacade;
import ejb.UsuariosFacade;
import entity.Evento;
import entity.Usuarios;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.datatype.XMLGregorianCalendar;



/**
 *
 * @author Angela Martina Padrón Pol
 */
@WebService(serviceName = "UsuarioService")
public class UsuarioService {

    @EJB
    private UsuariosFacade usuariosFacade;

    @EJB
    private EventoFacade eventoFacade;

   

    /**
     * Web service operation
     *
     * @param autenticacionEmailIntroducido
     * @param usuario
     * @param mensajeError
     * @return
     */
    @WebMethod(operationName = "iniciarSesion")
    public Usuarios iniciarSesion(String autenticacionEmailIntroducido, Usuarios usuario, String mensajeError) {
        if (!autenticacionEmailIntroducido.isEmpty() && autenticacionEmailIntroducido != null) {
            usuario = usuariosFacade.findByEmail(autenticacionEmailIntroducido);
        } else { 
            usuario = null;
        }
        return usuario;
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "obtenerUsuarios")
    public List<Usuarios> obtenerUsuarios() {
        return usuariosFacade.findAll();

    }

    /*
     * @param usuario
     * @return 
     */
    @WebMethod(operationName = "verEventos")
    public List<Evento> verEventos(Usuarios usuario){
       if(usuario.getTipoUsuario() != 3){
           return eventoFacade.eventosVisibles();
       } else {
            return eventoFacade.findAll();
        }
    }
     
    @WebMethod(operationName = "obtenerEventosSinValidar")
    public List<Evento> obtenerEventosSinValidar(){
        return this.eventoFacade.EventosSinValidar();
    }
    

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "subirEvento")
    public String subirEvento() {
        return "crearEvento";
    }

    /**
     * Web service operation
     *
     * @param nuevoEvento
     * @param usuario
     * @return
     */
    @WebMethod(operationName = "crearEvento")
    public String crearEvento(Evento nuevoEvento, Usuarios usuario) {
        if (usuario.getTipoUsuario() != 1) {//Usuario normal y Superusuario
            nuevoEvento.setEstado(1); //Validado   
        } else {//Periodista
            nuevoEvento.setEstado(0);//Sin validar 
        }
      
        //nuevoEvento.setFechainicio(fechainicio);
       // nuevoEvento.setFechafin(fechafin);
        nuevoEvento.setEmailusuario(usuario); //No me deja hacer getEmail()
        this.eventoFacade.create(nuevoEvento);
        
        return "listaEventos";
    }


    public Date formateoFecha(XMLGregorianCalendar fechaAParsear) {
        return fechaAParsear.toGregorianCalendar().getTime();

    }


    
     /**
     * Web service operation
     * @param evento
     * @return 
     */
    @WebMethod(operationName = "modificarEvento")
    public String modificarEvento(Evento evento){
        this.eventoFacade.edit(evento);
        return "listaEventos";
    }
    
    /**
     * Web service operation
     * @param evento
     * @return 
     */
    @WebMethod(operationName = "borrarEvento")
    public String borrarEvento(Evento evento){
        this.eventoFacade.remove(evento);
        return "listaEventos";
    }
    
     /**
     * Web service operation
     * @param evento
     * @return 
     */
    @WebMethod(operationName = "validarEvento")
    public String validarEvento(Evento evento){
        evento.setEstado(1);
        this.eventoFacade.edit(evento);
        return "listaEventosSinValidar";
    }
    

    /**
     * Web service operation
     *
     * @param usuarioLoggeado
     * @return
     */
    @WebMethod(operationName = "obtenerEventosDeUsuario")
    public List<Evento> obtenerEventosDeUsuario(Usuarios usuarioLoggeado) {
        return this.eventoFacade.EventosDeUsuario(usuarioLoggeado);
    }

}

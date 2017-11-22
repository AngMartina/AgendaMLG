/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import ejb.EtiquetasFacade;
import ejb.EventoFacade;
import ejb.UsuariosFacade;
import entity.Evento;
import entity.Usuarios;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

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

    @EJB
    private EtiquetasFacade etiquetasFacade;
    


    /**
     * Web service operation
     * @param autenticacionEmailIntroducido
     * @param usuario
     * @param mensajeError
     * @return 
     */
    @WebMethod(operationName = "iniciarSesion")
     public Usuarios iniciarSesion(String autenticacionEmailIntroducido, Usuarios usuario, String mensajeError) {
        if (!autenticacionEmailIntroducido.isEmpty() && autenticacionEmailIntroducido != null) {
            usuario = usuariosFacade.findByEmail(autenticacionEmailIntroducido);
        }else{
            usuario =null;
        }
        return usuario;
    }
     
     /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "obtenerUsuarios")
     public List<Usuarios> obtenerUsuarios(){
            return usuariosFacade.findAll();
        
    }
    
      /**
     * Web service operation
     * @param ordenacion
     * @param usuario
     * @param fechaOrdenacion
     * @param localizacionLatitud
     * @param localizacionLongitud
     * @return 
     */
    @WebMethod(operationName = "obtenerEventos")
    public List<Evento> obtenerEventos(Integer ordenacion, Usuarios usuario, Date fechaOrdenacion, int localizacionLatitud, int localizacionLongitud){
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
                long a = localizacionLatitud;
                long b = localizacionLongitud;
                return null; //FALTA POR HACER
            default:
                return eventoFacade.findAll();
        }
    }
    
    /**
    * Web service operation
    * @return 
    */
    @WebMethod(operationName = "subirEvento")
   public String subirEvento(){
       return "crearEvento";
   }
   
   
        /**
     * Web service operation
     * @param nuevoEvento
     * @return 
     */
    @WebMethod(operationName = "crearEvento")
    public String crearEvento(Evento nuevoEvento){
        /*Codigo Antonio*/
        this.eventoFacade.create(nuevoEvento);
        return "ListaEventos";
    }
    
    
    /**
     * Web service operation
     * @param usuarioLoggeado
     * @param eventosDelUsuario
     * @return 
     */
    @WebMethod(operationName = "initUsuario")
    public void initUsuario(Usuarios usuarioLoggeado,  List<Evento> eventosDelUsuario){
        eventosDelUsuario = this.eventoFacade.EventosDeUsuario(usuarioLoggeado);
    }
}

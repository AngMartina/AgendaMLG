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
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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

    @EJB
    private EtiquetasFacade etiquetasFacade;

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

    /**
     * Web service operation
     *
     * @param ordenacion
     * @param usuario
     * @param fechaOrdenacion
     * @param localizacionLatitud
     * @param localizacionLongitud
     * @return
     * @throws javax.xml.datatype.DatatypeConfigurationException
     */
    @WebMethod(operationName = "obtenerEventos")
    public List<Evento> obtenerEventos(Integer ordenacion, Usuarios usuario, Date fechaOrdenacion, double localizacionLatitud, double localizacionLongitud) throws DatatypeConfigurationException {
        if (null == ordenacion) {
            //if (usuario.getTipoUsuario() == 3) { // Periodista
                return eventoFacade.findAll();
            //} else {
            //    GregorianCalendar fechaActual = new GregorianCalendar();
            //    return eventoFacade.EventosNoCaducados(fechaActual);
                 
            //}
        } else {
            switch (ordenacion) {
                case 1:
                    //Ordeno por preferencias
                    return eventoFacade.buscarEventoPorPreferencias(usuario);
                case 2:
                    //Ordeno por fecha
                   return eventoFacade.buscarEventoPorFecha(fechaOrdenacion);
                case 3:
                    //Ordeno por distancia
                    double a = localizacionLatitud;
                    double b = localizacionLongitud;
                    return null; //FALTA POR HACER
                case 4:
                    return eventoFacade.findAll();
                default:
                    return eventoFacade.findAll();
            }
        }
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
     * @param fechainicio
     * @param fechafin
     * @return
     */
    @WebMethod(operationName = "crearEvento")
    public String crearEvento(Evento nuevoEvento, Usuarios usuario, Date fechainicio, Date fechafin) {
        if (usuario.getTipoUsuario() != 1) {//Usuario normal y Superusuario
            nuevoEvento.setEstado(1); //Validado   
        } else {//Periodista
            nuevoEvento.setEstado(0);//Sin validar 
        }
        nuevoEvento.setFechainicio(fechainicio);
        nuevoEvento.setFechafin(fechafin);
        nuevoEvento.setEmailusuario(usuario); //No me deja hacer getEmail()
        this.eventoFacade.create(nuevoEvento);
        return "/eventos/listaEventos?faces-redirect=true";
    }

    public Date formateoFecha(XMLGregorianCalendar fechaAParsear) {
        return fechaAParsear.toGregorianCalendar().getTime();

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

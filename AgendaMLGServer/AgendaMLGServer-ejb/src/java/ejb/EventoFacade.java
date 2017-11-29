/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Evento;
import entity.Usuarios;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Antonio
 */
@Stateless
public class EventoFacade extends AbstractFacade<Evento> {

    @PersistenceContext(unitName = "AgendaMLGServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EventoFacade() {
        super(Evento.class);
    }
    
    public List<Evento> buscarEventoPorFecha(Date fecha) throws DatatypeConfigurationException{
        Query q;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        XMLGregorianCalendar fechaXMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);

        q = em.createQuery("SELECT e FROM Evento e WHERE :fecha BETWEEN e.fechainicio AND e.fechafin");
        q.setParameter("fecha", fechaXMLGregorianCalendar);
        return q.getResultList();
        
    }
    
    public List<Evento> EventosDeUsuario(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :email=e.emailusuario");
        q.setParameter("email", u);
        
        return q.getResultList();
    }
    
    public List<Evento> EventosNoCaducados(Date fechaActual){
        
        Query q;
        
        //Si falla puede ser que tengamos que formatear la fecha actual para que sea igual.
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(fechaActual);
        try {
            XMLGregorianCalendar fechaGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(EventoFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        q = em.createQuery("SELECT e FROM Evento e Where e.fechafin >= :fechaActual");
        q.setParameter("fechaActual", fechaActual);
        
        return q.getResultList();
    
    }
    
    public List<Evento> eventosVisibles(){
        Query q;
        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "UTC+01:00" ) );
        java.sql.Date today = java.sql.Date.valueOf(todayLocalDate);
        
        q = em.createQuery("SELECT e FROM Evento e WHERE e.fechafin >= :today AND e.estado = 1");
        q.setParameter("today", today);
        return q.getResultList();
        
    }
    
    public List<Evento> EventosSinValidar(){
        Query q;
        q = em.createQuery("SELECT e FROM Evento e WHERE e.estado= :estado");
        q.setParameter("estado", 0);
        
        return q.getResultList();
    }
    
    public void ValidarEvento(Evento evento){
        Query q;
        q = em.createQuery("UPDATE Evento SET estado = :estado WHERE id = :id");
        q.setParameter("estado", 1);
        q.setParameter("id", evento.getId()).executeUpdate();
    }
}

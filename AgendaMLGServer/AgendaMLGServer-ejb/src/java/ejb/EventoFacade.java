/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Evento;
import entity.Usuarios;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Angela
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
    
       public List<Evento> buscarEventoPorPreferencias(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE e.palabrasclave LIKE :u.preferencias");
        q.setParameter("u", u);
        return q.getResultList();
        
    }
    
    public List<Evento> buscarEventoPorFecha(Date fecha){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :fecha BETWEEN e.fechainicio AND e.fechafin");
        q.setParameter("fecha", fecha);
        return q.getResultList();
        
    }
    
    public List<Evento> EventosDeUsuario(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :email=e.emailusuario");
        q.setParameter("email", u);
        
        return q.getResultList();
    }
    
    public void ModificarEvento(Evento evento){
        Query q;
        Date inicio = evento.getFechainicio();
        Date fin = evento.getFechafin();
        q = em.createQuery("UPDATE Evento SET fechainicio = :fechainicio, fechafin = :fechafin, descripcion = :descripcion, localizacion = :localizacion, latitud = :latitud, longitud = :longitud, precio = :precio, url = :url WHERE id = :id");
        q.setParameter("fechainicio", inicio);
        q.setParameter("fechafin", fin);
        q.setParameter("descripcion", evento.getDescripcion());
        q.setParameter("localizacion", evento.getLocalizacion());
        q.setParameter("latitud", evento.getLatitud());
        q.setParameter("longitud", evento.getLongitud());
        q.setParameter("precio", evento.getPrecio());
        q.setParameter("url", evento.getUrl());
        q.setParameter("id", evento.getId()).executeUpdate();
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

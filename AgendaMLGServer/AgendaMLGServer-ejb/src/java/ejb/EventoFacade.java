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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Charlie
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
    
       public List<Evento> eventosVisibles(){
        Query q;
        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "UTC+01:00" ) );
        java.sql.Date today = java.sql.Date.valueOf(todayLocalDate);
        
        q = em.createQuery("SELECT e FROM Evento e WHERE e.fechafin >= :today AND e.estado = 1");
        q.setParameter("today", today);
        return q.getResultList();
        
    }
    
       public List<Evento> buscarEventoPorPreferencias(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE e.palabrasclave LIKE :u.preferencias");
        q.setParameter("u", u);
        return q.getResultList();
        
    }
    
    
    public List<Evento> buscarEventoPorFecha(Date fecha){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :fecha BETWEEN e.fechainicio AND e.fechafin ORDER BY e.fechafin");
        q.setParameter("fecha", fecha);
        return q.getResultList();
        
    }
     public List<Evento> buscarEventoPorCP(Integer cp){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :cp = e.codigopostal");
        q.setParameter("cp", cp);
        return q.getResultList();
        
    }
    
    
    public List<Evento> EventosDeUsuario(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento e WHERE :email=e.emailusuario");
        q.setParameter("email", u);
        
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

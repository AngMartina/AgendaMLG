/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Evento;
import entity.Usuarios;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Angela
 */
@Stateless
public class EventoFacade extends AbstractFacade<Evento> {

    @PersistenceContext(unitName = "AgendaMLG-ejbPU")
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
        
        q = em.createQuery("SELECT e FROM Evento WHERE e.palabrasclave LIKE u.preferencias");
        q.setParameter("u", u);
        return q.getResultList();
        
    }
    
    public List<Evento> buscarEventoPorFecha(Date fecha){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento WHERE fecha BETWEEN e.fechainicio AND e.fechafin");
        q.setParameter("fecha", fecha);
        return q.getResultList();
        
    }
    
    public List<Evento> EventosDeUsuario(Usuarios u){
        Query q;
        
        q = em.createQuery("SELECT e FROM Evento WHERE e.emailusuario LIKE u.email");
        q.setParameter("u", u);
        return q.getResultList();
    }
    
}

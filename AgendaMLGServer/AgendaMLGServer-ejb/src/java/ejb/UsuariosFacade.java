/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Angela
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "AgendaMLGServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
       public Usuarios findByEmail(String email) {
        try{
        Query q;
        
        q = em.createQuery("SELECT u FROM Usuarios u WHERE :email = u.email");
        q.setParameter("email", email);

        return (Usuarios)q.getSingleResult();
        }catch(NullPointerException e){
        return null;
    }}

}

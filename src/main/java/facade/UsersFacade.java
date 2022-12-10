/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Aprendiz
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeLocal {

    @PersistenceContext(unitName = "tostadoraSJ")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    @Override
    public Users validarUsuario(String correoIn, String claveIn){
        try {
            Query qt = em.createQuery("SELECT u FROM Users u WHERE u.email = :d1 AND u.password = :d2");
            qt.setParameter("d1", correoIn);
            qt.setParameter("d2", claveIn);
            return (Users)qt.getSingleResult();            
        } catch (Exception e) {
            return null;
        }
    }
    @Override
     public List<Users> leerTodos(){
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT u FROM Users u");
        return q.getResultList();         
    }
     
    @Override
    public Users validarRecovery(String correoIn){
        try {
            Query qt = em.createQuery("SELECT u FROM Users u WHERE u.email = :d1 ");
            qt.setParameter("d1", correoIn);
            
            return (Users)qt.getSingleResult();     
            
        } catch (Exception e) {
            return null;
        }
    }
    
    
   
}

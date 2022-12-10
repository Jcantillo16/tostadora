/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Roles;
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
public class RolesFacade extends AbstractFacade<Roles> implements RolesFacadeLocal {

    @PersistenceContext(unitName = "tostadoraSJ")
    private EntityManager em;
    Query q;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Roles findRolByName(String rolname) {
        q = em.createQuery("SELECT r FROM Roles r WHERE r.nameRole = :rolename");
        q.setParameter("rolename", rolname);
        return (Roles) q.getSingleResult();
    }
    
    public RolesFacade() {
        super(Roles.class);
    }
    
}

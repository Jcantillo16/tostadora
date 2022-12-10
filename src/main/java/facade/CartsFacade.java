/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Carts;
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
public class CartsFacade extends AbstractFacade<Carts> implements CartsFacadeLocal {

    @PersistenceContext(unitName = "tostadoraSJ")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CartsFacade() {
        super(Carts.class);
    }

    @Override
    public List<Carts> leerTodos() {
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT c FROM Carts c");
        return q.getResultList();
    }

    @Override
    public Carts findProductInCart(Integer id, Integer id1) {
        return null;
    }
}


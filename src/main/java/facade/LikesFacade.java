/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Likes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aprendiz
 */
@Stateless
public class LikesFacade extends AbstractFacade<Likes> implements LikesFacadeLocal {

    @PersistenceContext(unitName = "tostadoraSJ")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LikesFacade() {
        super(Likes.class);
    }
    
}

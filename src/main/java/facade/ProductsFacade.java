/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;


import entity.Products;
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
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {

    @PersistenceContext(unitName = "tostadoraSJ")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }
    
    @Override
    public List<Products> findAllData() {
        em.getEntityManagerFactory().getCache().evictAll();
        return em.createQuery("SELECT p FROM Products p").getResultList();
    }
    
    @Override
    public Products validarProducto(String nameIn){
         try {
            Query qt = em.createQuery("SELECT p FROM Products p WHERE  p.nameProduct = :d1");
  
            qt.setParameter("d1", nameIn);
            
            return (Products)qt.getSingleResult();            
        } catch (Exception e) {
            return null;
        }
    
    }
    
    
    
    @Override
    public List<Products> leerTodos(){
        em.getEntityManagerFactory().getCache().evictAll();
        Query q = em.createQuery("SELECT p FROM Products p");
        return q.getResultList();         
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Carts;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aprendiz
 */
@Local
public interface CartsFacadeLocal {

    void create(Carts carts);

    void edit(Carts carts);

    void remove(Carts carts);

    Carts find(Object id);

    List<Carts> findAll();

    List<Carts> findRange(int[] range);

    int count();
    public List<Carts> leerTodos();

    Carts findProductInCart(Integer id, Integer id1);

}

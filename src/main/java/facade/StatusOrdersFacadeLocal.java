/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.StatusOrders;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aprendiz
 */
@Local
public interface StatusOrdersFacadeLocal {

    void create(StatusOrders statusOrders);

    void edit(StatusOrders statusOrders);

    void remove(StatusOrders statusOrders);

    StatusOrders find(Object id);

    List<StatusOrders> findAll();

    List<StatusOrders> findRange(int[] range);

    int count();
    
}

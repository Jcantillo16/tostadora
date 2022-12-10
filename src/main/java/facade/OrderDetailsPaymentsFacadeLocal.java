/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.OrderDetailsPayments;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aprendiz
 */
@Local
public interface OrderDetailsPaymentsFacadeLocal {

    void create(OrderDetailsPayments orderDetailsPayments);

    void edit(OrderDetailsPayments orderDetailsPayments);

    void remove(OrderDetailsPayments orderDetailsPayments);

    OrderDetailsPayments find(Object id);

    List<OrderDetailsPayments> findAll();

    List<OrderDetailsPayments> findRange(int[] range);

    int count();
    
}

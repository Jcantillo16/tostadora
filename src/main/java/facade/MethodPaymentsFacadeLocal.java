/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.MethodPayments;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aprendiz
 */
@Local
public interface MethodPaymentsFacadeLocal {

    void create(MethodPayments methodPayments);

    void edit(MethodPayments methodPayments);

    void remove(MethodPayments methodPayments);

    MethodPayments find(Object id);

    List<MethodPayments> findAll();

    List<MethodPayments> findRange(int[] range);

    int count();
    
}

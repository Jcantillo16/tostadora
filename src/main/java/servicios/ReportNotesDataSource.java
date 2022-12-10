/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import entity.Carts;
import entity.Products;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author sergi
 */
public class ReportNotesDataSource implements JRDataSource {

    private List<Products> product;
    private List<Carts> carts;
    private int indice;

    public ReportNotesDataSource(List<Carts> carts) {
        this.carts = carts;
        this.product = product;
        indice = -1;
    }

    @Override
    public boolean next() throws JRException {
        indice++;
        return indice < carts.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Carts c = carts.get(indice);
        
        if (jrf.getName().equals("nameProduct")) {
            return c.getProductId().getNameProduct();
        } else if (jrf.getName().equals("description")) {
            return c.getProductId().getDescription();
        } else if (jrf.getName().equals("price")) {
            return c.getPrice();

        } else if (jrf.getName().equals("quantity")) {
            return c.getQuantity();

        } else if (jrf.getName().equals("total")) {
            return c.getTotal();
        }
        return "";
    
    }



}
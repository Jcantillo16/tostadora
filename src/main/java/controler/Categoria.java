/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entity.Category;
import facade.CategoryFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.PF;

/**
 *
 * @author juane
 */
@Named(value = "categoria")
@ViewScoped
public class Categoria implements Serializable {

    @EJB
    CategoryFacadeLocal ct;

    private Category categoria = new Category();

    public Categoria() {
    }
    public void crearCategoria(){
        ct.create(categoria);
          try {
            PF.current().executeScript(
                    "  swal.fire({"
                    + "  position: 'top-end',"
                    + "  icon: 'success',"
                    + "  title: ' Se registro la categoria correctamente',"
                    + "  showConfirmButton: false,"
                    + "  timer: 1500"
                    + "  })");
        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                    + "  position: 'top-end',"
                    + "  icon: 'error',"
                    + "  title: 'No se registro la categoria',"
                    + "  showConfirmButton: false,"
                    + "  timer: 1500"
                    + "  })");
        }
        
    }

    

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    /**
     * Creates a new instance of Categoria
     */
}

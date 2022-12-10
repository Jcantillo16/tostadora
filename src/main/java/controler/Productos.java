/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entity.Products;
import facade.ProductsFacadeLocal;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PF;

/**
 * @author juane
 */
@Named(value = "productos")
@ViewScoped
public class Productos implements Serializable {

    @EJB
    ProductsFacadeLocal pd;

    private String name;
    private String description;
    private String price;
    private String image;
    private Products producto = new Products();
    private List<Products> productos = new ArrayList<>();

    public Productos() {
    }

    @PostConstruct
    public void postConstruct() {
        setProductos(pd.findAllData());
        producto = (Products) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productSelect");
        if (producto == null) {
            producto = new Products();
        }
    }

    public void crearProducto() {
        pd.create(producto);
        producto = new Products();
        try {

            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Registro de Producto Exitoso',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Valide los datos del producto',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }
    }

    //eliminar producto
    public void eliminarProducto(Products producto) {
        pd.remove(producto);
        producto = new Products();
        try {

            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Eliminacion de Producto Exitoso',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al eliminar el producto',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }
    }
    public int cantidadProductos(){
        return productos.size();
    }

    public List<Products> leerTodos() {
        return pd.leerTodos();
    }

    public String cargarData(Products productoSeleccionado) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productSelect", productoSeleccionado);
        return "edit_product.xhtml";
    }

    //editar producto
    public void editarProducto() {
        try {
            pd.edit(producto);

            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Edicion de Producto Exitoso',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al editar el producto',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }

    }
    
   

    //get producto
    public Products getProducto() {
        return producto;
    }

    public Products getIdProduct(int id) {
        return pd.find(id);
    }

    public void setProducto(Products producto) {
        this.producto = producto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the productos
     */
    public List<Products> getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(List<Products> productos) {
        this.productos = productos;
    }

}

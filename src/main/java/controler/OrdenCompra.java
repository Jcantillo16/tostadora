//exportar a pdf

package controler;

import entity.Carts;
import entity.Products;
import entity.Users;
import entity.OrderDetails;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import facade.ProductsFacadeLocal;
import facade.UsersFacadeLocal;
import facade.CartsFacadeLocal;
import facade.OrderDetailsFacadeLocal;

import org.primefaces.PF;

import static java.lang.Integer.parseInt;

/**
 * @author SENA
 */
@Named(value = "ordenCompra")
@SessionScoped
public class OrdenCompra implements Serializable {

    @EJB
    CartsFacadeLocal cfl;

    @EJB
    UsersFacadeLocal ufl;

    @EJB
    ProductsFacadeLocal pfl;

    @EJB
    OrderDetailsFacadeLocal ofl;

    private OrderDetails orden = new OrderDetails();
    private List<OrderDetails> ordenes = new ArrayList<>();

    private Carts carrito = new Carts();
    private List<Carts> car = new ArrayList<>();

    private Users usuario = new Users();
    private List<Users> usuarios = new ArrayList<>();

    private Products producto = new Products();
    private List<Products> productos = new ArrayList<>();

    private Integer cantidad = 1;
    private int total;

    public OrdenCompra() {

    }

    public List<OrderDetails> leerTodos() {
        return ofl.leerTodos();
    }

}

















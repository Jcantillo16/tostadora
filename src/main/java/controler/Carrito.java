/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entity.Carts;
import entity.Products;
import entity.Users;
import facade.CartsFacadeLocal;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

import facade.ProductsFacadeLocal;
import facade.UsersFacadeLocal;

import java.io.IOException;

import org.primefaces.PF;

import static java.lang.Integer.parseInt;

/**
 * @author SENA
 */
@Named(value = "carrito")
@ViewScoped
public class Carrito implements Serializable {

    @EJB
    CartsFacadeLocal cfl;

    @EJB
    UsersFacadeLocal ufl;

    @EJB
    ProductsFacadeLocal pfl;


    private Carts carrito = new Carts();
    private List<Carts> car = new ArrayList<>();
    private Integer cantidad = 1;
    private int total;


    public Carrito() {

    }

    public List<Carts> leerTodos() {
        return cfl.leerTodos();
    }


    public Users getUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        InicioSesion inicioSesion = context.getApplication().evaluateExpressionGet(
                context, "#{inicioSesion}", InicioSesion.class);
        Users usuario = inicioSesion.getUsuarioLogin();
        return usuario;
    }

    public Products getProducto() {
        FacesContext context = FacesContext.getCurrentInstance();
        Productos productos = context.getApplication().evaluateExpressionGet(
                context, "#{productos}", Productos.class);
        Products producto = productos.getProducto();
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }


    public void addCarrito(Products p) throws IOException {
        boolean existe = false;
        for (Carts c : car) {
            if (c.getProductId().equals(p) && c.getUsers().equals(getUsuario())) {
                System.out.println("existe");
                c.setQuantity(c.getQuantity() + cantidad);
                c.setTotal(c.getQuantity() * c.getProductId().getPrice());
                c.setCreatedAt(new java.util.Date());
                cfl.edit(c);
                existe = true;
                FacesContext fx = FacesContext.getCurrentInstance();
                fx.getExternalContext().redirect("shopcart.xhtml");
                break;

            }
        }
        if (!existe) {
            Carts c = new Carts();
            c.setProductId(p);
            c.setUsers(ufl.find(getUsuario().getId()));
            c.setPrice(p.getPrice());
            c.setQuantity(cantidad);
            c.setTotal(c.getPrice() * c.getQuantity());
            c.setCreatedAt(new java.util.Date());
            car.add(c);
            cfl.create(c);
            PF.current().executeScript("swal('Producto agregado', 'El producto se ha agregado al carrito', 'success')");
            FacesContext fx = FacesContext.getCurrentInstance();
            fx.getExternalContext().redirect("shopcart.xhtml");
        }
    }

    public int getSubtotal(Carts c) {
        return c.getQuantity() * c.getProductId().getPrice();
    }


    public int total() {
        total = 0;
        for (Carts c : cfl.leerTodos()) {
            total += c.getTotal();
        }
        return total;
    }

    public void eliminar(Carts c, Users u) {
        cfl.remove(c);
        car.remove(c);
        PF.current().executeScript("swal('Producto eliminado', 'El producto se ha eliminado del carrito', 'success')");
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public List<Carts> getCar() {
        return car;
    }

    public void setCar(List<Carts> car) {
        this.car = car;
    }

    public void orden() throws IOException {
        FacesContext fx = FacesContext.getCurrentInstance();
        fx.getExternalContext().redirect("orden_compra.xhtml");
    }

    public void vaciarCarrito() {
        for (Carts c : cfl.leerTodos()) {
            cfl.remove(c);
        }
        PF.current().executeScript("Swal.fire('Carrito vaciado')");
    }

    public int cantidadProductos(Users u, Products p) {
        int cantidad = 0;
        for (Carts c : cfl.leerTodos()) {
            if (c.getUsers().equals(u) && c.getProductId().equals(p)) {
                cantidad = c.getQuantity();
            }
        }
        return cantidad;
    }
}







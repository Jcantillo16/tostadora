/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import entity.Users;
import facade.RolesFacadeLocal;
import facade.UsersFacadeLocal;

import java.io.IOException;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.*;

import org.primefaces.PF;

/**
 * @author Aprendiz
 */
@Named(value = "inicioSesion")
@SessionScoped
public class InicioSesion implements Serializable {

    @EJB
    UsersFacadeLocal user;

    @EJB
    RolesFacadeLocal rfl;

    private String correo;
    private String clave;

    private Users usuarioLogin = new Users();
    private Users usuarioRecovery = new Users();
    private Users usuarioRegister = new Users();
    private List<Users> getUsuario;


    public InicioSesion() {
    }

    public void inicio() {
        try {
            FacesContext fx = FacesContext.getCurrentInstance();
            fx.getExternalContext().redirect("catalogo/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redireccionarRegistro() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
        } catch (IOException ex) {
            System.out.println("Error al redireccionar");
        }
    }

    public void redireccionarRecovery() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("recovery.xhtml");
        } catch (IOException ex) {
            System.out.println("Error al redireccionar");
        }
    }

    public void crearUsuario() {

        try {
            getUsuario = user.leerTodos();
            for (Users getUsuario1 : getUsuario) {
                if (getUsuario1.getEmail().equals(usuarioRegister.getEmail())) {
                    PF.current().executeScript(
                            "  swal.fire({"
                                    + "  position: 'top-end',"
                                    + "  icon: 'error',"
                                    + "  title: 'Este ususario ya se encuentra registrado',"
                                    + "  showConfirmButton: false,"
                                    + "  timer: 1500"
                                    + "  })");
                    return;
                }
            }
            usuarioRegister.setRoleId(rfl.findRolByName("CLIENTE"));
            if (validarPassword(usuarioRegister.getPassword())) {
                user.create(usuarioRegister);
                usuarioRegister = new Users();
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'success',"
                                + "  title: 'Usuario creado con exito',"
                                + "  showConfirmButton: false,"
                                + "  timer: 2500"
                                + "  })");
                if (usuarioRegister != null) {
                    correoBienvenida(usuarioRegister.getEmail());
                    FacesContext fx = FacesContext.getCurrentInstance();
                    fx.getExternalContext().redirect("cliente/catalogo.xhtml");
                }
            } else {
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'error',"
                                + "  title: 'La contraseña debe tener al menos 8 caracteres, entre mayuscula, miniscula y numeros',"
                                + "  showConfirmButton: false,"
                                + "  timer: 2500"
                                + "  })");
            }
        } catch (Exception e) {
            System.out.println("Error al crear usuario");
        }
    }

    public boolean validarPassword(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public void editarPerfil() {
        user.edit(usuarioLogin);
        try {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Actualizado',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Intente Nuevamente',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }

    }

    public void validarUsuario() {
        try {

            usuarioLogin = user.validarUsuario(correo, clave);
            if (usuarioLogin == null) {
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'error',"
                                + "  title: 'Correo y/o Contraseña Incorrecta ',"
                                + "  showConfirmButton: false,"
                                + "  timer: 1500"
                                + "  })");

            } else {
                FacesContext fx = FacesContext.getCurrentInstance();
                if (usuarioLogin.getRoleId().getNameRole().equals("ADMIN")) {
                    fx.getExternalContext().redirect("administrador/index.xhtml");
                } else {
                    fx.getExternalContext().redirect("cliente/catalogo.xhtml");
                }

            }
        } catch (IOException e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Usuario Inexistente',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }
    }

    public void validarRecovery() {
        try {
            usuarioRecovery = user.validarRecovery(correo);
            if (usuarioRecovery == null) {
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'error',"
                                + "  title: 'Verifique su Correo Electronico',"
                                + "  showConfirmButton: false,"
                                + "  timer: 1500"
                                + "  })");
            } else {
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'success',"
                                + "  title: 'Recuperacion Exitosa',"
                                + "  showConfirmButton: false,"
                                + "  timer: 1500"
                                + "  })");
            }

        } catch (Exception e) {

        }
    }

    public void eliminarUsuario(Users usuario) {
        user.remove(usuario);

        try {

            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Eliminacion de Usuario Exitoso',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al eliminar el Usuario',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }
    }

    public List<Users> leerTodos() {
        return user.leerTodos();
    }

    public void editarUsuario(Users usuarioLogin) {

        try {
            user.edit(usuarioLogin);
            usuarioLogin = new Users();
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Edicion de Usuario Exitoso',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        } catch (Exception e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al editar el Usuario',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }

    }

    public void cargarData(Users usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
        mostrarFormularioEditar();
    }

    public void mostrarDatos(Users usuarioLogin) {
        this.usuarioLogin = usuarioLogin;

    }

    public void mostrarFormularioEditar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit_user.xhtml");
        } catch (IOException ex) {
            System.out.println("Error al redireccionar");
        }
        mostrarDatos(usuarioLogin);
    }

    public int cantidadUsuarios() {
        int cantidad = 0;
        try {
            cantidad = user.leerTodos().size();
        } catch (Exception e) {
        }
        return cantidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Users getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(Users usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Users getUsuarioRecovery() {
        return usuarioRecovery;
    }

    public void setUsuarioRecovery(Users usuarioRecovery) {
        this.usuarioRecovery = usuarioRecovery;
    }

    public Users getUsuarioRegister() {
        return usuarioRegister;
    }

    public void setUsuarioRegister(Users usuarioRegister) {
        this.usuarioRegister = usuarioRegister;
    }

    public void recuperarClaveHTML() {
        try {
            usuarioRecovery = user.validarRecovery(correo);
            if (usuarioRecovery == null) {
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'error',"
                                + "  title: 'Verifique su Correo Electronico',"
                                + "  showConfirmButton: false,"
                                + "  timer: 1500"
                                + "  })");
            } else {
                String clave = usuarioRecovery.getPassword();
                String correo = usuarioRecovery.getEmail();
                String asunto = "Recuperacion de Contraseña";
                String mensaje = "<!DOCTYPE html>"
                        + "<html lang='en'>"
                        + "<head>"
                        + "<meta charset='UTF-8'>"
                        + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                        + "<title>recovery</title>"
                        + "<style>"
                        + ".cuerpo{"
                        + "background-color: #f2f2f2;"
                        + "text-align: center;"
                        + "}"
                        + ".titulo{"
                        + "color: #000;"
                        + "text-align: center;"
                        + "}"
                        + "p{"
                        + "color: #000;"
                        + "text-align: center;"
                        + "}"
                        + "a{"
                        + "color: #bf9000;"
                        + "text-align: center;"
                        + "font-weight: bold;"
                        + "}"
                        + ".imge{"
                        + "width: 150px;"
                        + "height: 100px;"
                        + "alt style: display;"
                        + "block;"
                        + "font-size: 0px;"
                        + "}"
                        + "h5{"
                        + "color: #bf9000;"
                        + "text-align: center;"
                        + "}"
                        + "h2{"
                        + "color: #bf9000;"
                        + "text-align: center;"
                        + "}"
                        + "button{"
                        + "width: 90px;"
                        + "height: 50px;"
                        + "border-radius: 10px;"
                        + "background-color: #E6EBEF;"
                        + "color: #FFFFFF;"
                        + "}"
                        + "td{"
                        + "text-align: center;"
                        + "width: 100%;"
                        + "height: 100%;"
                        + "alt style: display;"
                        + "block;"
                        + "font-size: 0px;"
                        + "}"
                        + "</style>"
                        + "</head>"
                        + "<body>"
                        + "<center><h1>Recuperacion de Contraseña</h1></center>"
                        + "<center><img src='https://vabzeh.stripocdn.email/content/guids/CABINET_65075e980aae7fb4f3e981a4e6101ed1/images/cafe.png' alt='Logo'    width='150px' ></center>"
                        + "<center><p>Estimado Usuario Su contraseña es: </p></center>"
                        + "<center><h2> " + clave + "</h2></center>"
                        + "<center><p>Gracias por preferirnos</p></center>"
                        + "<center><p>Saludos Cordiales</p></center>"
                        + "<center><button type='button'> <a href='http://localhost:8080/JSF_Tostadora2-1.0-SNAPSHOT/login.xhtml'>login</a></button></center>"
                        + "<center><p>Equipo Tostadora De Cafe San Jorge Gaes III</p></center>"
                        + "<br/>"
                        + "<center><p>Para mas informacion visite nuestra pagina web</p></center>"
                        + "<center><a href='https://www.tostadorasanjorgegaes3.com/'>Tostadora San Jorge Gaes III</a></center>"
                        + "<br/>"
                        + "<center><p>Gaes III : </p></center>"
                        + "<center><p> Juan Jose Cantillo</p></center>"
                        + "<center><p> Juan Esteban Quintero</p></center>"
                        + "<center><p> Javier Contreras</p></center>"
                        + "<center><p> Valentina alvarado</p></center>"
                        + "</body>"
                        + "</html>";

                String destinatario = correo;
                String remitente = "tostadorasanjorgegaes3@gmail.com";
                String claveRemitente = "apemhfluacbxroqh";
                String host = "smtp.gmail.com";
                Properties props = System.getProperties();
                props.put("mail.smtp.user", remitente);
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.clave", claveRemitente);
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                Session session = Session.getDefaultInstance(props);
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(remitente));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                message.setSubject(asunto);
                message.setContent(mensaje, "text/html; charset=utf-8");
                Transport transport = session.getTransport("smtp");
                transport.connect(host, remitente, claveRemitente);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                PF.current().executeScript(
                        "  swal.fire({"
                                + "  position: 'top-end',"
                                + "  icon: 'success',"
                                + "  title: 'Contraseña Enviada',"
                                + "  showConfirmButton: false,"
                                + "  timer: 1500"
                                + "  })");
            }
        } catch (MessagingException e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al enviar la contraseña',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        }
    }

    public void correoBienvenida(String correo) {
        try {
            String asunto = "Bienvenido a Tostadora San Jorge Gaes III";
            String mensaje = "<!DOCTYPE html>"
                    + "<html lang='en'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<title>recovery</title>"
                    + "<style>"
                    + ".cuerpo{"
                    + "background-color: #f2f2f2;"
                    + "text-align: center;"
                    + "}"
                    + ".titulo{"
                    + "color: #000;"
                    + "text-align: center;"
                    + "}"
                    + "p{"
                    + "color: #000;"
                    + "text-align: center;"
                    + "}"
                    + "a{"
                    + "color: #bf9000;"
                    + "text-align: center;"
                    + "font-weight: bold;"
                    + "}"
                    + ".imge{"
                    + "width: 150px;"
                    + "height: 100px;"
                    + "alt style: display;"
                    + "block;"
                    + "font-size: 0px;"
                    + "}"
                    + "h5{"
                    + "color: #bf9000;"
                    + "text-align: center;"
                    + "}"
                    + "h2{"
                    + "color: #bf9000;"
                    + "text-align: center;"
                    + "}"
                    + "button{"
                    + "width: 90px;"
                    + "height: 50px;"
                    + "border-radius: 10px;"
                    + "background-color: #E6EBEF;"
                    + "color: #FFFFFF;"
                    + "}"
                    + "td{"
                    + "text-align: center;"
                    + "width: 100%;"
                    + "height: 100%;"
                    + "alt style: display;"
                    + "block;"
                    + "font-size: 0px;"
                    + "}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<center><h1>Bienvenido a Tostadora San Jorge Gaes III</h1></center>"
                    + "<center><img class='imge' src='https://i.ibb.co/0nZ3Z3T/logo.png' alt='logo'></center>"
                    + "<center><h5>Gracias por preferirnos</h5></center>"
                    + "<center><p>Saludos Cordiales</p></center>"
                    + "<center><button type='button'> <a href='http://localhost:8080/JSF_Tostadora2-1.0-SNAPSHOT/login.xhtml'>login</a></button></center>"
                    + "<center><p>Equipo Tostadora De Cafe San Jorge Gaes III</p></center>"
                    + "<br/>"
                    + "<center><p>Para mas informacion visite nuestra pagina web</p></center>"
                    + "<center><a href='https://www.tostadorasanjorgegaes3.com/'>Tostadora San Jorge Gaes III</a></center>"
                    + "<br/>"
                    + "<center><p>Gaes III : </p></center>"
                    + "<center><p> Juan Jose Cantillo</p></center>"
                    + "<center><p> Juan Esteban Quintero</p></center>"
                    + "<center><p> Javier Contreras</p></center>"
                    + "<center><p> Valentina alvarado</p></center>"
                    + "</body>"
                    + "</html>";

            String destinatario = correo;
            String remitente = "tostadorasanjorgegaes3@gmail.com";
            String claveRemitente = "apemhfluacbxroqh";
            String host = "smtp.gmail.com";
            Properties props = System.getProperties();
            props.put("mail.smtp.user", remitente);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.clave", claveRemitente);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setContent(mensaje, "text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, remitente, claveRemitente);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'success',"
                            + "  title: 'Correo Enviado',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");
        } catch (MessagingException e) {
            PF.current().executeScript(
                    "  swal.fire({"
                            + "  position: 'top-end',"
                            + "  icon: 'error',"
                            + "  title: 'Error al enviar el correo',"
                            + "  showConfirmButton: false,"
                            + "  timer: 1500"
                            + "  })");

        }


    }

    public List<Users> getGetUsuario() {
        return getUsuario;
    }

    public void setGetUsuario(List<Users> getUsuario) {
        this.getUsuario = getUsuario;
    }

}





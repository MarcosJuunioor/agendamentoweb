/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Usuario;
import descorp.agendamentoweb.models.ClienteModel;
import descorp.agendamentoweb.models.UsuarioModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author taoal
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    private final UsuarioModel bean;
    private final ClienteModel clienteBean;
    
    private Usuario usuarioLogin;
    private Cliente novoCliente;

    public LoginController() {
        this.bean = new UsuarioModel();
        this.clienteBean = new ClienteModel();
    }

    public void login(String e, String s, HttpSession session) throws IOException {
        String URLCliente = session.getServletContext().getContextPath()+"/index.xhtml";
        String URLEstabelecimento = session.getServletContext().getContextPath()+"/calendario-estabelecimento.xhtml";
        Usuario mUsuario = bean.validarUsuario(e, s);
        
        if (mUsuario != null) {
            session.setAttribute("idUsuario", mUsuario.getId());
            Cliente cliente = this.clienteBean.consultarClientePorId(mUsuario.getId());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Sucesso",
                    "Login realizado com sucesso!"));
            
            if(cliente!=null){
                session.setAttribute("tipoUsuario", "cliente");
                FacesContext.getCurrentInstance().getExternalContext().redirect(URLCliente);
            }else{
                session.setAttribute("tipoUsuario", "estabelecimento");
                FacesContext.getCurrentInstance().getExternalContext().redirect(URLEstabelecimento);
            }
            PrimeFaces.current().ajax().update("loginForm:msg");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Falha",
                    "Email ou senha incorretos!"));
            PrimeFaces.current().ajax().update("loginForm:msg");
        }
    }

    public void registrar(HttpSession session, String email, String senha, String nome, String cpf, String telefone){
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senha);
        cliente.setNome(nome);
        cliente.setCPF(cpf);
        cliente.setTelefone(telefone);
        try {
            clienteBean.registrarCliente(cliente);
            FacesContext.getCurrentInstance().getExternalContext().redirect(session.getServletContext().getContextPath()+"/login.xhtml");
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro",
                    violation.getMessage()));
                PrimeFaces.current().ajax().update("cadastroForm:msgs");
            });
        } catch (Exception ex){
                System.out.println("Erro: "+ex.getMessage());
        }
    }

    public Usuario getUsuario(String e, String p) {
        return this.usuarioLogin;
    }
    
    public void logout(HttpSession session){
        session.invalidate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(session.getServletContext().getContextPath()+"/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

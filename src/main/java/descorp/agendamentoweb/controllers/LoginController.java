/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.controllers;

import com.sun.faces.util.Util;
import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.entities.Usuario;
import descorp.agendamentoweb.models.AgendamentoModel;
import descorp.agendamentoweb.models.UsuarioModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author taoal
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private final UsuarioModel bean;

    private Usuario usuarioLogin;
    private Cliente novoCliente;

    public LoginController() {
        this.bean = new UsuarioModel();
    }

    public void login(String e, String s) throws IOException {
        String URL = "http://localhost:8080/agendamentoweb/index.xhtml";
        Usuario mUsuario = bean.validarUsuario(e, s);
        if (mUsuario != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(URL);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "LoginDAO!",
                    "Email ou senha incorretos!"));
        }
    }

    public void registrar(String email, String senha) throws IOException {
        bean.registrar(email, senha);
    }

    public Usuario getUsuario(String e, String p) {
        return this.usuarioLogin;
    }
    
     public void cadastrarUsuario() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        PrimeFaces.current().dialog().openDynamic("Cadastro de Clientes", options, null);
    }

}

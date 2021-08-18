/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Usuario;
import static descorp.agendamentoweb.models.GenericModel.em;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 *
 * @author taoal
 */
public class UsuarioModel extends GenericModel implements Serializable {

    public UsuarioModel() {
        super();
    }

    public Usuario validarUsuario(String email, String senha) {
        try {
            TypedQuery<Usuario> query = em.createNamedQuery("Usuario.PorEmail", Usuario.class).setParameter("email", email).setParameter("senha", senha);
            Usuario usuario = query.getSingleResult();
            return usuario;
        } catch (Exception e) {
            System.out.println("Erro no login:" + e.getMessage());
            return null;
        }
    }

   /* public void registrar(String email, String senha) throws IOException {

        Cliente cliente = new Cliente();

        String msgSucesso = "Agendamento criado com sucesso!";

        cliente.setEmail(email);
        cliente.setSenha(senha);
                        
        try {
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(cliente);
            em.flush();
            et.commit();
            em.close();
        } catch (Exception e) {
            System.out.println("Erro ao persistir cliente: " + e.getMessage());
        } finally {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro realizado com sucesso!", ""));
        }

    } */
}

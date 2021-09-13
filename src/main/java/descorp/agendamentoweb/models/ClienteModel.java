/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Usuario;
import static descorp.agendamentoweb.models.GenericModel.em;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author marco
 */
@Stateless
public class ClienteModel extends GenericModel implements Serializable {

    public ClienteModel() {
        super();
    }
    
    public List<Cliente> todosClientes(){
        checkEM();
        List<Cliente> clientes = em.createNamedQuery(Cliente.CLIENTES_TODOS).getResultList();
        return clientes;
    }

    public Cliente consultarClientePorId(Long idUsuario) {
        try {
            TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorIdUsuario", Cliente.class).setParameter("idUsuario", idUsuario);
            Cliente cliente = query.getSingleResult();
            return cliente;
        } catch (Exception e) {
            System.out.println("Erro no login:" + e.getMessage());
            return null;
        }
    }

    public void registrarCliente(Cliente cliente) {
        try {
            this.beginTransaction();
            this.em.persist(cliente);
            this.commitTransaction();
        } catch (Exception e) {
            System.out.println("Erro ao persistir cliente:" + e.getMessage());
        }
    }

}

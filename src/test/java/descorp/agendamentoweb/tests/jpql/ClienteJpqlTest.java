/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Usuario;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author marco
 */
public class ClienteJpqlTest extends GenericTest{
//Consulta de cliente por CPF
    @Test
    public void clientePorCPF(){
        TypedQuery<Cliente> query = em.createQuery("select c from Cliente c where c.CPF = :cpf", Cliente.class);
        query.setParameter("cpf", "468.381.220-73");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);
    }
//Consulta da quantidade de clientes cadastrados
    @Test
    public void getQuantClientesCadastrados(){
        TypedQuery<Long> query = em.createQuery("select count(c) from Cliente c", Long.class);
        
        long quantidadeClientes = query.getSingleResult();
        assertEquals(3,quantidadeClientes);
    }

    //Consulta do cliente com agendamento mais próximo
    @Test
    public void getClienteComAgendamentoMaisProximo(){
        TypedQuery<Date> queryData = em.createQuery("select min(a.data) from Cliente c "
                + "join Agendamento a "
                + "on a.usuario.id = c.id "
                ,
                Date.class);
        
        Date data = queryData.getSingleResult();
        assertNotNull(data);
        TypedQuery<Cliente> queryCliente = em.createQuery("select c from Cliente c "
                + "join Agendamento a "
                + "on a.usuario.id = c.id "
                + "where a.data = :data"
                ,
                Cliente.class);
        queryCliente.setParameter("data", data);
        Cliente cliente = queryCliente.getSingleResult();
        assertNotNull(cliente);
        assertEquals("Tayná Alexandra",cliente.getNome());
    }
    
    //Consulta do cliente com agendamento mais distante
    @Test
    public void getClienteComAgendamentoMaisDistante(){
        TypedQuery<Date> queryData = em.createQuery("select max(a.data) from Cliente c "
                + "join Agendamento a "
                + "on a.usuario.id = c.id "
                ,
                Date.class);
        
        Date data = queryData.getSingleResult();
        assertNotNull(data);
        TypedQuery<Cliente> queryCliente = em.createQuery("select c from Cliente c "
                + "join Agendamento a "
                + "on a.usuario.id = c.id "
                + "where a.data = :data"
                ,
                Cliente.class);
        queryCliente.setParameter("data", data);
        Cliente cliente = queryCliente.getSingleResult();
        assertNotNull(cliente);
        assertEquals("Tayná Alexandra",cliente.getNome());
    }
}

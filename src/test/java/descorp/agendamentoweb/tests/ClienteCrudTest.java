/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Usuario;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class ClienteCrudTest extends GenericTest{
   
    @Test
    public void persistirCliente(){
        Cliente cliente = criarCliente();
        em.persist(cliente);
        em.flush();
        assertNotNull(cliente.getId());
    }
    
   @Test
    public void consultarCliente(){
        Cliente cliente = em.find(Cliente.class, 1L);
        assertNotNull(cliente);
        assertEquals("marcosjuunioor2@gmail.com", cliente.getEmail());
        assertEquals("8191501944", cliente.getTelefone());
        assertEquals("123456", cliente.getSenha());
        assertEquals("Marcos Antonio", cliente.getNome());
        assertEquals("01230637445", cliente.getCPF());
        
    } 
    
    @Test
    public void atualizarCliente() {
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorCpf", Cliente.class);
        query.setParameter("CPF", "09972624436");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);
        cliente.setNome("José Rodrigues da Silva");
        em.flush();
        cliente = query.getSingleResult();
        assertEquals(cliente.getNome(), "José Rodrigues da Silva");
    }
    
    @Test
    public void removerCliente(){
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorCpf", Cliente.class);
        query.setParameter("CPF", "94144359900");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);
        em.remove(cliente);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("jose@gmail.com");
        cliente.setSenha("1234");
        cliente.setTelefone("81988771456");
        cliente.setNome("Jose");
        cliente.setCPF("12345678910");
        return cliente;
    }
    
}

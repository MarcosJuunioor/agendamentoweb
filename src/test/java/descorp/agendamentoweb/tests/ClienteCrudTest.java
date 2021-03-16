/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Cliente;
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
        assertEquals("468.381.220-73", cliente.getCPF());
        
    } 
    
    @Test
    public void atualizarCliente() {
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorCpf", Cliente.class);
        query.setParameter("CPF", "400.102.560-40");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);
        cliente.setNome("Jose Rodrigues Da Silva");
        em.flush();
        cliente = query.getSingleResult();
        assertEquals(cliente.getNome(), "Jose Rodrigues Da Silva");
    }
    
    @Test
    public void removerCliente(){
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorCpf", Cliente.class);
        query.setParameter("CPF", "067.787.510-02");
        Cliente cliente = query.getSingleResult();
        assertNotNull(cliente);
        em.remove(cliente);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    public static Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("jose@gmail.com");
        cliente.setSenha("1234");
        cliente.setTelefone("81988771456");
        cliente.setNome("Jose");
        cliente.setCPF("014.797.760-68");
        return cliente;
    }
    
}

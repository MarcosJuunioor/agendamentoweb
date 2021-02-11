/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Cliente;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class ClienteTest extends GenericTest{
   
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
        assertEquals("jose@gmail.com", cliente.getEmail());
        assertEquals("81988771456", cliente.getTelefone());
        assertEquals("1234", cliente.getSenha());
        assertEquals("José", cliente.getNome());
        assertEquals("12345678910", cliente.getCPF());
        
    }
    
        
    
    private static Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("jose@gmail.com");
        cliente.setSenha("1234");
        cliente.setTelefone("81988771456");
        cliente.setNome("José");
        cliente.setCPF("12345678910");
        return cliente;
    }
    
}

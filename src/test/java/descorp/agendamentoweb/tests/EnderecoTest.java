/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Endereco;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandra
 */
public class EnderecoTest extends GenericTest {
    @Test
    public void persistirEndereco() {
        Endereco endereco = criarEndereco();
        em.persist(endereco);
        em.flush();
        assertNotNull(endereco.getId());
        System.out.println();
        System.out.println(endereco.getId());
    }
    /*
    @Test
    public void consultarEndereco() {
        Endereco endereco = em.find(Endereco.class, 1L);
        assertEquals("11111111", endereco.getCep());
        assertEquals("Rua 1", endereco.getRua());
        assertEquals(41, (int)endereco.getNumero());
        assertEquals("Boa Vista", endereco.getBairro());
        assertEquals("Recife", endereco.getCidade());
        assertEquals("PE", endereco.getEstado());
    }*/

    private static Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("11111111");
        endereco.setRua("Rua 1");
        endereco.setNumero(41);
        endereco.setBairro("Boa Vista");
        endereco.setCidade("Recife");
        endereco.setEstado("PE");
        
        return endereco;
    }

}
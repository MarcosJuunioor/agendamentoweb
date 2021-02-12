/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Estabelecimento;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandra
 */
public class EstabelecimentoTest extends GenericTest{

    @Test
    public void persistirEstabelecimento(){
        Estabelecimento estabelecimento = criarEstabelecimento();
        em.persist(estabelecimento);
        em.flush();
        assertNotNull(estabelecimento.getId());
    }

    @Test
    public void consultarEstabelecimento(){
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 3L);
        assertEquals("eveline@gmail.com", estabelecimento.getEmail());
        assertEquals("8188441409", estabelecimento.getTelefone());
        assertEquals("128322", estabelecimento.getSenha());
        assertEquals("Eveline Esteticista", estabelecimento.getRazaoSocial());
        assertEquals("1234567891011", estabelecimento.getCNPJ());
    }
    
    private static Estabelecimento criarEstabelecimento() {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setEmail("recanto-beleza@gmail.com");
        estabelecimento.setTelefone("81988771548");
        estabelecimento.setSenha("5487");
        estabelecimento.setRazaoSocial("Recanto da Beleza");
        estabelecimento.setCNPJ("00156458000199");
        
        return estabelecimento;
    }
}

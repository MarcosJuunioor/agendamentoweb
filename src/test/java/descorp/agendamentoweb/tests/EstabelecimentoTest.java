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
        System.out.println();
        System.out.print(estabelecimento.getId());
    }
/*
    @Test
    public void consultarEstabelecimento(){
        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 1L);
        System.out.println(estabelecimento.getEmail());
        assertEquals("recanto-beleza@gmail.com", estabelecimento.getEmail());
        assertEquals("81988771548", estabelecimento.getTelefone());
        assertEquals("5487", estabelecimento.getSenha());
        assertEquals("Recanto da Beleza", estabelecimento.getRazaoSocial());
        assertEquals("00156458000199", estabelecimento.getCNPJ());
    }*/
    
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

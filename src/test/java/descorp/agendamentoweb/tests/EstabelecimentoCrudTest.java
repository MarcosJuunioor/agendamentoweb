/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Estabelecimento;
import descorp.agendamentoweb.entities.Endereco;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandra
 */
public class EstabelecimentoCrudTest extends GenericTest{

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
        assertEquals("estabelecimento@gmail.com", estabelecimento.getEmail());
        assertEquals("8188441409", estabelecimento.getTelefone());
        assertEquals("123456", estabelecimento.getSenha());
        assertEquals("Eveline Esteticista", estabelecimento.getRazaoSocial());
        assertEquals("12.345.678/9101-11", estabelecimento.getCNPJ());
    }

    @Test
    public void atualizarEstabelecimento() {
        TypedQuery<Estabelecimento> query = em.createNamedQuery("Estabelecimento.PorCnpj", Estabelecimento.class);
        query.setParameter("CNPJ", "12.345.678/9101-11");
        Estabelecimento estabelecimento = query.getSingleResult();
        assertNotNull(estabelecimento);
        estabelecimento.setRazaoSocial("Recanto da Beleza 2");
        em.flush();
        estabelecimento = query.getSingleResult();
        assertEquals(estabelecimento.getRazaoSocial(), "Recanto da Beleza 2");
    }

    @Test
    public void removerEstabelecimento(){
        TypedQuery<Estabelecimento> query = em.createNamedQuery("Estabelecimento.PorCnpj", Estabelecimento.class);
        query.setParameter("CNPJ", "12.345.678/9101-11");
        Estabelecimento estabelecimento = query.getSingleResult();
        assertNotNull(estabelecimento);
        em.remove(estabelecimento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static Estabelecimento criarEstabelecimento() {
        Estabelecimento estabelecimento = new Estabelecimento();
        estabelecimento.setEmail("recanto-beleza@gmail.com");
        estabelecimento.setTelefone("81988771548");
        estabelecimento.setSenha("5487");
        estabelecimento.setRazaoSocial("Recanto da Beleza");
        estabelecimento.setCNPJ("00.156.458/0001-99");

        Endereco endereco = em.find(Endereco.class, 3L);
        estabelecimento.setEndereco(endereco);
        
        return estabelecimento;
    }
}

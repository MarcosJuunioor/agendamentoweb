/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Sala;
import descorp.agendamentoweb.entities.Estabelecimento;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandra
 */
public class SalaCrudTest extends GenericTest {

    @Test
    public void persistirSala(){
        Sala sala = criarSala();
        em.persist(sala);
        em.flush();
        assertNotNull(sala.getId());
        System.out.print(sala.getId());
    }
    
    @Test
    public void consultarSala(){
        Sala sala = em.find(Sala.class, 1L);
        assertEquals(300, (int)sala.getNumSala());
    }

    @Test
    public void atualizarSala() {
        TypedQuery<Sala> query = em.createNamedQuery("Sala.PorNumSala", Sala.class);
        query.setParameter("numSala", 200);
        Sala sala = query.getSingleResult();
        assertNotNull(sala);
        sala.setNumSala(400);
        em.flush();
        assertEquals((int)sala.getNumSala(), 400);
    }

    @Test
    public void removerSala(){
        TypedQuery<Sala> query = em.createNamedQuery("Sala.PorNumSala", Sala.class);
        query.setParameter("numSala", 400);
        Sala sala = query.getSingleResult();
        assertNotNull(sala);
        em.remove(sala);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static Sala criarSala() {
        Sala sala = new Sala();
        sala.setNumSala(144);

        Estabelecimento estabelecimento = em.find(Estabelecimento.class, 6L);
        sala.setEstabelecimento(estabelecimento);
        
        return sala;
    }

}
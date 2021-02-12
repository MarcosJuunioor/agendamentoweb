/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Sala;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexandra
 */
public class SalaTest extends GenericTest {

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
    
    private static Sala criarSala() {
        Sala sala = new Sala();
        sala.setNumSala(144);
        
        return sala;
    }

}
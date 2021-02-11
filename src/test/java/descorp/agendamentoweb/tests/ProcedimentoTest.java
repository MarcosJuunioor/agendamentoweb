/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Procedimento;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class ProcedimentoTest extends GenericTest {

    @Test
    public void persistirProcedimento(){
        Procedimento procedimento = criarProcedimento();
        em.persist(procedimento);
        em.flush();
        assertNotNull(procedimento.getId());
    }
    
    /*@Test
    public void consultarProcedimento(){
        Procedimento procedimento = em.find(Procedimento.class, 1L);
        assertEquals("Estético", procedimento.getNatureza());
        assertEquals("Limpeza de pele.", procedimento.getNome());
        assertEquals(getDuracao(), procedimento.getDuracao());
    } */
    
            
    
    private static Procedimento criarProcedimento() {
        Procedimento procedimento = new Procedimento();
        procedimento.setNatureza("Estético");
        procedimento.setNome("Limpeza de pele.");
        procedimento.setDuracao(getDuracao());
        return procedimento;
    }

    private static Date getDuracao(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 2);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        
        return calendar.getTime();
    }
}

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
    
    @Test
    public void consultarProcedimento(){
        Procedimento procedimento = em.find(Procedimento.class, 1L);
        assertEquals("Est�tico", procedimento.getNatureza());
        assertEquals("Limpeza de sobrancelha", procedimento.getNome());
        assertEquals(getDuracao().getHours(), procedimento.getDuracao().getHours());
    } 
    
            
    
    private static Procedimento criarProcedimento() {
        Procedimento procedimento = new Procedimento();
        procedimento.setNatureza("Est�tico");
        procedimento.setNome("Limpeza de pele.");
        procedimento.setDuracao(getDuracao());
        return procedimento;
    }

    private static Date getDuracao(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 1);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 00);
        
        return calendar.getTime();
    }
}

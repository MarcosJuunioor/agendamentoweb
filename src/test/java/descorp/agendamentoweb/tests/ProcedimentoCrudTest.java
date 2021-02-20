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
public class ProcedimentoCrudTest extends GenericTest {

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
        assertEquals("Estético", procedimento.getNatureza());
        assertEquals("Limpeza de sobrancelha", procedimento.getNome());
        assertEquals(criarHora(1,0,0).getHours(), procedimento.getDuracao().getHours());
    } 
    
            
    
    private static Procedimento criarProcedimento() {
        Procedimento procedimento = new Procedimento();
        procedimento.setNatureza("Estético");
        procedimento.setNome("Limpeza de pele.");
        procedimento.setDuracao(criarHora(2,0,0));
        return procedimento;
    }
}

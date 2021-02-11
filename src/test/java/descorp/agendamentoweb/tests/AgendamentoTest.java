/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class AgendamentoTest extends GenericTest{
    
   /* @Test
    public void persistirAgendamento(){
        Agendamento agendamento = criarAgendamento();
        em.persist(agendamento);
        em.flush();
        assertNotNull(agendamento.getId());
    } 
    
    @Test
    public void consultarAgendamento(){
        Agendamento agendamento = em.find(Agendamento.class, 1L);
        assertEquals("2021-02-15", agendamento.getData());
        assertEquals("14:00:00", agendamento.getHora());
        assertEquals(criarCliente(), agendamento.getUsuario());
        assertEquals(criarProfissional(), agendamento.getPrifissional());
        assertEquals(criarProcedimento(), agendamento.getProcedimento());
    } 
    
    private static Agendamento criarAgendamento() {
        Agendamento agendamento = new Agendamento();
        return agendamento;
    } */

}

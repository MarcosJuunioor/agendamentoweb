/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.entities.Usuario;
import java.text.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class AgendamentoTest extends GenericTest{
    
    @Test
    public void persistirAgendamento(){
        Agendamento agendamento = criarAgendamento();
        em.persist(agendamento);
        em.flush();
        assertNotNull(agendamento.getId());
    } 
    
    @Test
    public void consultarAgendamento() throws ParseException{
        Agendamento agendamento = em.find(Agendamento.class, 1L);
        int dia = agendamento.getData().getDate();
        int mes = agendamento.getData().getMonth();
        int ano = (agendamento.getData().getYear() - 100)+2000;
        assertEquals("15-2-2021", dia+"-"+(mes+1)+"-"+ano);
        assertEquals(criarHora(9,0,0).getHours(), agendamento.getHora().getHours());
        assertEquals(em.find(Usuario.class, 2L), agendamento.getUsuario());
        assertEquals(em.find(Profissional.class, 1L), agendamento.getProfissional());
        assertEquals(em.find(Procedimento.class, 1L), agendamento.getProcedimento());
    } 
    
    private static Agendamento criarAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setData(criarData(15, 02, 2021));
        agendamento.setHora(criarHora(10,0,0));
        
        Usuario usuario = em.find(Usuario.class, 2L);
        agendamento.setUsuario(usuario);
        
        Procedimento procedimento = em.find(Procedimento.class, 1L);
        agendamento.setProcedimento(procedimento);
        
        Profissional profissional = em.find(Profissional.class, 1L);
        agendamento.setProfissional(profissional);
        
        return agendamento;
    } 

}

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
import java.util.Calendar;
import java.util.Date;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
 */
public class AgendamentoCrudTest extends GenericTest{
    
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
        String dataFormatada = formatarData(agendamento.getData());
        assertEquals("15-2-2021", dataFormatada);
        assertEquals(criarHora(9,0,0).getHours(), agendamento.getHora().getHours());
        assertEquals(em.find(Usuario.class, 2L), agendamento.getUsuario());
        assertEquals(em.find(Profissional.class, 1L), agendamento.getProfissional());
        assertEquals(em.find(Procedimento.class, 1L), agendamento.getProcedimento());
    } 
    
    @Test
    public void atualizarAgendamento() {
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorData", Agendamento.class);
        query.setParameter("data", criarData(19,Calendar.APRIL,2021));
        Agendamento agendamento = query.getSingleResult();
        assertNotNull(agendamento);
        Date dataNova = criarData(28,Calendar.FEBRUARY,2021);
        agendamento.setData(dataNova);
        em.flush();
        
        query.setParameter("data", dataNova);
        agendamento = query.getSingleResult();
        String dataFormatada = formatarData(agendamento.getData());
        assertEquals("28-2-2021", dataFormatada);
    }
    
    @Test
    public void removerAgendamento(){
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorData", Agendamento.class);
        query.setParameter("data", criarData(9,Calendar.MARCH,2021));
        Agendamento agendamento = query.getSingleResult();
        em.remove(agendamento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static Agendamento criarAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setData(criarData(15, Calendar.FEBRUARY, 2021));
        agendamento.setHora(criarHora(10,0,0));
        
        Usuario usuario = em.find(Usuario.class, 2L);
        agendamento.setUsuario(usuario);
        
        Procedimento procedimento = em.find(Procedimento.class, 1L);
        agendamento.setProcedimento(procedimento);
        
        Profissional profissional = em.find(Profissional.class, 1L);
        agendamento.setProfissional(profissional);
        
        return agendamento;
    } 
    
    private static String formatarData(Date data){
        int dia = data.getDate();
        int mes = data.getMonth();
        int ano = (data.getYear() - 100)+2000;
        return dia+"-"+(mes+1)+"-"+ano;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Procedimento;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.TypedQuery;
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
        assertEquals("Limpeza de pele", procedimento.getNome());
        String horaFormatada = formatarHora(procedimento.getDuracao());
        assertEquals("1:0:0", horaFormatada);
    } 
    
    @Test
    public void atualizarProcedimento(){
        TypedQuery<Procedimento> query = em.createNamedQuery("Procedimento.PorNome", Procedimento.class);
        query.setParameter("nome", "Limpeza de sobrancelha");
        Procedimento procedimento = query.getSingleResult();
        assertNotNull(procedimento);
        procedimento.setDuracao(criarHora(1, 40, 0));
        em.flush();
        procedimento = query.getSingleResult();
        String horaFormatada = formatarHora(procedimento.getDuracao());
        assertEquals("1:40:0", horaFormatada);
    } 
    @Test
    public void removerProcedimento(){
        TypedQuery<Procedimento> query = em.createNamedQuery("Procedimento.PorNome", Procedimento.class);
        query.setParameter("nome", "Branqueamento de dentes");
        Procedimento procedimento = query.getSingleResult();
        assertNotNull(procedimento);
        em.remove(procedimento);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static Procedimento criarProcedimento() {
        Procedimento procedimento = new Procedimento();
        procedimento.setNatureza("Estético");
        procedimento.setNome("Limpeza de pele.");
        procedimento.setDuracao(criarHora(2,0,0));
        return procedimento;
    }
    
    private static String formatarHora(Date time){
        int hora = time.getHours();
        int min = time.getMinutes();
        int seg = time.getSeconds();
        return hora+":"+min+":"+seg;
    }
}

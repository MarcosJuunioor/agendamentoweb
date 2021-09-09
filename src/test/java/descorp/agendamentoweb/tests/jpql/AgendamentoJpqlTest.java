/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Calendar;
import java.util.List;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author marco
 */
public class AgendamentoJpqlTest extends GenericTest {
    //Consulta de agendamento de uma data específica
    @Test
    public void agendamentoPorData() {
        TypedQuery<Agendamento> query = em.createQuery("select a from Agendamento a "
                + "where a.data = :data",
                Agendamento.class);
        query.setParameter("data", criarData(10, Calendar.MARCH, 2021));
        Agendamento agendamento = query.getSingleResult();
        assertNotNull(agendamento);
    }

    //Consulta da quantidade de agendamentos por cliente  
    @Test
    public void getQuantAgendamentosPorCliente() {
        TypedQuery<Long> query = em.createQuery("select count(c) from Cliente c "
                + "join Agendamento a "
                + "on a.usuario.id = c.id "
                + "group by c.nome",
                Long.class);

        List<Long> listaQuantAgendamentosPorCliente = query.getResultList();
        //O primeiro cliente possui 8 agendamentos
        assertEquals("9", listaQuantAgendamentosPorCliente.get(0).toString());
        //O segundo cliente possui 6 agendamentos
        assertEquals("6", listaQuantAgendamentosPorCliente.get(1).toString());
    }
}

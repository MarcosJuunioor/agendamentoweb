/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.List;
import javax.persistence.Query;
import org.junit.Test;
import descorp.agendamentoweb.entities.DiasSemana;
import descorp.agendamentoweb.entities.Profissional;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;

/**
 *
 * @author evellinne
 */
public class DiasSemanaJpqlTest extends GenericTest{
//Consulta de todos os dias da semamana
    @Test
    public void diasSemanaCadastrados(){
        Query query = em.createQuery(
                "SELECT d FROM DiasSemana d");
        List<DiasSemana> diasSemana = query.getResultList();
        assertNotNull(diasSemana);
        assertEquals(7, diasSemana.size());
    }
//Consulta dos profissionais que trabalham em um determinado dia da semana

    @Test
    public void profissionaisDiasSemana(){
        TypedQuery<Profissional> query = em.createQuery(
                "SELECT p FROM Profissional p "
                + "JOIN DiasSemana d "
                + "WHERE p MEMBER OF d.profissionais "
                + "AND d.nome = :nome_dia",
                Profissional.class);
        query.setParameter("nome_dia", "sexta");
        
        List<Profissional> profissionais = query.getResultList();
        assertNotNull(profissionais);
        assertEquals("Rafaela Silva", profissionais.get(0).getNome());
    }
}

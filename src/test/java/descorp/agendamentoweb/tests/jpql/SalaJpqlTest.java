/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;

import descorp.agendamentoweb.tests.GenericTest;
import descorp.agendamentoweb.entities.Sala;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
/**
 *
 * @author tayná
 */
public class SalaJpqlTest extends GenericTest{
    //Consulta do estabelecimento associado a ela
    @Test
    public void estabelecimentoAssociado() {
        TypedQuery<Sala> query;
        query = em.createQuery(
                "SELECT s FROM Sala s WHERE s.estabelecimento.razaoSocial = :razaoSocial",
                Sala.class);
        query.setParameter("razaoSocial", "Eveline Esteticista");
        List<Sala> salas = query.getResultList();
        
        assertEquals("Eveline Esteticista", salas.get(0).getEstabelecimento().getRazaoSocial());
        assertEquals(1, salas.size());
    }

    //Consulta das salas cadastradas  
    @Test
    public void salasCadastradas() {
        TypedQuery<Sala> query;
        query = em.createQuery(
                "SELECT s FROM Sala s WHERE s.numSala IS NOT NULL",
                Sala.class);
        List<Sala> salas = query.getResultList();

        assertEquals(300, (int)salas.get(0).getNumSala());
        assertEquals(200, (int)salas.get(1).getNumSala());
        assertEquals(2, salas.size());
    }
}

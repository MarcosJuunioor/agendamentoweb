/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;

import descorp.agendamentoweb.tests.GenericTest;
import descorp.agendamentoweb.entities.Estabelecimento;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tayná
 */
public class EstabelecimentoJpqlTest extends GenericTest{
    //Consulta de estabelecimento por CNPJ
    @Test
    public void estabelecimentoPorCNPJ() {
        TypedQuery<Estabelecimento> query = em.createQuery(
                "SELECT e FROM Estabelecimento e WHERE e.CNPJ LIKE :CNPJ",
                Estabelecimento.class);
        query.setParameter("CNPJ", "1234567584797");
        Estabelecimento estabelecimento = query.getSingleResult();

        assertNotNull(estabelecimento);
    }

    //Consulta da quantidade de estabelecimentos cadastrados
    @Test
    public void quantidadeEstabelecimentosCadastrados() {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Estabelecimento e WHERE e.CNPJ IS NOT NULL", Long.class);
        Long resultado = query.getSingleResult();

        assertEquals(new Long(3), resultado);
    }
}

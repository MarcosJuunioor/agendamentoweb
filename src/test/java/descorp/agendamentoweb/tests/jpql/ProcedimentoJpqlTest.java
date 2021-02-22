/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.List;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author marco
 */
public class ProcedimentoJpqlTest extends GenericTest{
    //Consulta de todos os procedimentos de uma determinada natureza
    @Test
    public void procedimentosPorNatureza(){
        TypedQuery<Procedimento> query = em.createQuery("select p from Procedimento p where p.natureza = :natureza", 
                Procedimento.class);
        query.setParameter("natureza", "Estético");
        List<Procedimento> procedimentos = query.getResultList();
        assertNotNull(procedimentos);
        assertEquals(2, procedimentos.size());
    }
    
    //Consulta dos procedimentos que possuem duração superior a X horas
    @Test
    public void procedimentosComDuracaoMaiorQue(){
        TypedQuery<Procedimento> query = em.createQuery("select p from Procedimento p where p.duracao > :duracao", 
                Procedimento.class);
        query.setParameter("duracao", criarHora(1,0,0));
        List<Procedimento> procedimentos = query.getResultList();
        assertNotNull(procedimentos);
        assertEquals(2, procedimentos.size());
    }
    
    
    //Consulta da quantidade de procedimentos existentes
    @Test
    public void getQuantidadeDeProcedimentos(){
        TypedQuery<Long> query = em.createQuery("select count(p) from Procedimento p", 
                Long.class);
        
        long quantidade = query.getSingleResult();
        assertNotNull(quantidade);
        assertEquals(3, quantidade);
    }
}

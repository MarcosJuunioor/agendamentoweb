/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.DiasSemana;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Evellinne
 */
public class DiasSemanaCrudTest extends GenericTest{
    @Test
    public void persistirDiasSemana(){
        DiasSemana diasSemana = criarDiasSemana();
        em.persist(diasSemana);
        em.flush();
        assertNotNull(diasSemana.getId());
    }
    
    @Test
    public void consultarDiasSemana(){
        DiasSemana diasSemana = em.find(DiasSemana.class, 1L);
        assertEquals("Segunda", diasSemana.getNome());      
       
    }
          
    @Test
    public void atualizarDiasSemana(){
        TypedQuery<DiasSemana> query = em.createNamedQuery("DiasSemana.PorNome", DiasSemana.class);
        query.setParameter("nome", "Segunda");
        DiasSemana dia = query.getSingleResult();
        assertNotNull(dia);
        dia.setNome("Segunda");
        em.flush();
        query.setParameter("nome", "Segunda");
        dia = query.getSingleResult();
        assertEquals("Segunda", dia.getNome());
    }
    
    @Test
    public void removerProcedimento(){
        TypedQuery<DiasSemana> query = em.createNamedQuery("DiasSemana.PorNome", DiasSemana.class);
        query.setParameter("nome", "Quarta");
        DiasSemana dia = query.getSingleResult();
        assertNotNull(dia);
        em.remove(dia);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    
    private static DiasSemana criarDiasSemana() {
        DiasSemana diasSemana = new DiasSemana();
        diasSemana.setNome("Ter�a");
        return diasSemana;
    }


}

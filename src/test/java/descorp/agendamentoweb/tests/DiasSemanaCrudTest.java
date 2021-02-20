/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.DiasSemana;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marco
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
        assertEquals("segunda", diasSemana.getNome());      
       
    }
                
    private static DiasSemana criarDiasSemana() {
        DiasSemana diasSemana = new DiasSemana();
        diasSemana.setNome("segunda");
        return diasSemana;
    }


}

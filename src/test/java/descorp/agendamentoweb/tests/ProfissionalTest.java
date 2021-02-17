/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Profissional;
import java.util.Calendar;
import java.util.Date;
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
public class ProfissionalTest extends GenericTest{
     @Test
    public void persistirProfissional(){
        Profissional profissional = criarProfissional();
        em.persist(profissional);
        em.flush();
        assertNotNull(profissional.getId());
        System.out.print(profissional.getId());
    }
    
    @Test
    public void consultarProfissional(){
        Profissional profissional = em.find(Profissional.class, 1L);
        assertEquals("Rafaela Silva", profissional.getNome());
        assertEquals("Esteticista", profissional.getProfissao());
        assertEquals("Limpeza de Pele", profissional.getEspecializacao());
        assertEquals(criarHora(8,0,0).getHours(), profissional.getHoraInicial().getHours());
        assertEquals(criarHora(17,0,0).getHours(), profissional.getHoraFinal().getHours()); 
    }
                
    private static Profissional criarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Maria José");
        profissional.setProfissao("Médico");
        profissional.setEspecializacao("Otorrinolaringologista");
        profissional.setHoraInicial(criarHora(8,0,0));
        profissional.setHoraFinal(criarHora(17,0,0));
                
        return profissional;
    }
    

   
}

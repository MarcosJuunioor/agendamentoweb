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
        assertEquals(getHoraInicial().getHours(), profissional.getHoraInicial().getHours());
        assertEquals(getHoraFinal().getHours(), profissional.getHoraFinal().getHours()); 
    }
                
    private static Profissional criarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Maria José");
        profissional.setProfissao("Médico");
        profissional.setEspecializacao("Otorrinolaringologista");
        profissional.setHoraInicial(getHoraInicial());
        profissional.setHoraFinal(getHoraFinal());
                
        return profissional;
    }
    
    private static Date getHoraInicial(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        
        return calendar.getTime();
    }
    
    private static Date getHoraFinal(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 17);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        
        return calendar.getTime();
    }
   
}

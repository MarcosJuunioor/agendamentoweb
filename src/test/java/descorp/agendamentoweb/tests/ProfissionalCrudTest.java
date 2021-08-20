/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import descorp.agendamentoweb.entities.Profissional;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.TypedQuery;
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
public class ProfissionalCrudTest extends GenericTest{
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
        assertEquals(criarHora(18,0,0).getHours(), profissional.getHoraFinal().getHours()); 
    }
      
    @Test
    public void atualizarProfissional(){
        TypedQuery<Profissional> query = em.createNamedQuery("Profissional.PorNome", Profissional.class);
        query.setParameter("nome", "Maria Eduarda");
        Profissional profissional = query.getSingleResult();
        assertNotNull(profissional);
        profissional.setNome("Maria Eduarda Silva");
        em.flush();
        query.setParameter("nome", "Maria Eduarda Silva");
        profissional = query.getSingleResult();
        assertEquals("Maria Eduarda Silva", profissional.getNome());
    }
    
    @Test
    public void removerProfissional(){
        TypedQuery<Profissional> query = em.createNamedQuery("Profissional.PorNome", Profissional.class);
        query.setParameter("nome", "Julia Maria Silva");
        Profissional profissional = query.getSingleResult();
        assertNotNull(profissional);
        em.remove(profissional);
        em.flush();
        assertEquals(0, query.getResultList().size());
    }
    private static Profissional criarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Maria Jose");
        profissional.setProfissao("Médico");
        profissional.setEspecializacao("Otorrinolaringologista");
        profissional.setHoraInicial(criarHora(8,0,0));
        profissional.setHoraFinal(criarHora(17,0,0));
                
        return profissional;
    }
    

   
}

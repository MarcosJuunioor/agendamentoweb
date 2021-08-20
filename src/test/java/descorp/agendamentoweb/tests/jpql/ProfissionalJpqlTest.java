/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.tests.GenericTest;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author evellinne
 */
public class ProfissionalJpqlTest extends GenericTest{
        
//Consulta de profissionais por profissão
    @Test
        public void profissionalPorProfissao(){
            TypedQuery<Profissional> query = em.createQuery("select p from Profissional p WHERE p.profissao= :profissao", Profissional.class);
            query.setParameter("profissao", "Dentista");
            Profissional profissionais = query.getSingleResult();
            assertNotNull(profissionais);
            assertEquals("Dentista", profissionais.getProfissao());
        }   
    
//Consulta de profissionais por especialização
        @Test
        public void profissionalPorEspecializacao(){
            TypedQuery<Profissional> query = em.createQuery("select p from Profissional p WHERE p.especializacao= :especializacao", Profissional.class);
            query.setParameter("especializacao", "Branqueamento dentário");
            Profissional profissionais = query.getSingleResult();
            assertNotNull(profissionais);
            assertEquals("Branqueamento dentário", profissionais.getEspecializacao());
        }

}






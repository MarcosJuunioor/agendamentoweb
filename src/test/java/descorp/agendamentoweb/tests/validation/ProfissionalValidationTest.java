/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Evellinne
 */
public class ProfissionalValidationTest extends GenericTest {
 
    //Consulta de profissionais por profissão
    @Test
        public void profissionalPorProfissao(){
            TypedQuery<Profissional> query = em.createQuery("select p from Profissional p WHERE p.profissao= :profissao", Profissional.class);
            query.setParameter("profissao", "Esteticista");
            Profissional profissionais = query.getSingleResult();
            assertNotNull(profissionais);
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
    
  //Consulta de profissionais pelo horário em que trabalha
        @Test
        public void profissionalPorHorario(){
            TypedQuery<Profissional> query = em.createQuery("select p from Profissional p WHERE p.horaInicial= :hora_inicial AND p.horaFinal= :hora_final", Profissional.class);
                      
            query.setParameter("hora_inicial", criarHora(8,0,0));
            query.setParameter("hora_final", criarHora(13,0,0));
            Profissional profissionais = query.getSingleResult();
            assertNotNull(profissionais);
            assertEquals("Júlia Maria da Silva", profissionais.getNome());
        }  
    

    
}


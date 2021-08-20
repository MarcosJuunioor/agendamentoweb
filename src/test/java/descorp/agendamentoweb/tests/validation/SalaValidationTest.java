/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Estabelecimento;
import descorp.agendamentoweb.entities.Sala;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author alexandra
 */
public class SalaValidationTest extends GenericTest{
    @Test(expected = ConstraintViolationException.class)
    public void persistirSalaInvalido() {
        Sala sala = null;
        try {
            sala = new Sala();
            sala.setNumSala(null);//Número de sala inválido
            
            Estabelecimento estabelecimento = em.find(Estabelecimento.class, 6L);
            sala.setEstabelecimento(estabelecimento);
            
            em.persist(sala);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Sala.numSala: não deve ser nulo")   
                        )
                );
            });

            assertEquals(1, constraintViolations.size());
            assertNull(sala.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarSalaInvalido() {
        TypedQuery<Sala> query = em.createNamedQuery("Sala.PorNumSala", Sala.class);
        query.setParameter("numSala", 300);
        Sala sala = query.getSingleResult();
        sala.setNumSala(null); //Número de sala inválido

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("não deve ser nulo", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}

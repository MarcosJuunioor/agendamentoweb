/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author marco
 */
public class ProcedimentoValidationTest extends GenericTest {
    @Test(expected = ConstraintViolationException.class)
    public void persistirProcedimentoInvalido() {
        Procedimento procedimento = null;

        try {
            procedimento = new Procedimento();
            procedimento.setNatureza(null);
            procedimento.setNome(null);
            procedimento.setDuracao(null);
            em.persist(procedimento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {
                assertEquals(violation.getMessage(),
                "não deve ser nulo"
                ); 
            });

            assertEquals(3, constraintViolations.size());
            assertNull(procedimento.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarProcedimentoInvalido() {
        Procedimento procedimento = em.find(Procedimento.class, 1L);
        procedimento.setNatureza(null);//Natureza inválida

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

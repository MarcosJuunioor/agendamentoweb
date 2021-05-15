
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.DiasSemana;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Set;
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
 * @author Evellinne
 */
public class DiasSemanaValidationTest extends GenericTest {
    
     @Test(expected = ConstraintViolationException.class) 
    public void persistirDiasSemanaInvalido() {
        DiasSemana diasSemana = null;
        
        try {
            diasSemana = new DiasSemana();
            diasSemana.setNome("Quarta123");            
            em.persist(diasSemana);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {               
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),                    
                        CoreMatchers.anyOf(
                            startsWith("class descorp.agendamentoweb.entities.DiasSemana.nome: Cada palavra deve iniciar com letra maiúscula, e as demais minúsculas, podendo ser palavras acentuadas ou não.")
                        )
                ); 
            });
            assertEquals(1, constraintViolations.size());
            assertNull(diasSemana.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarDiasSemanaInvalido() {
        DiasSemana diasSemana= em.find(DiasSemana.class, 1L);
        diasSemana.setNome("Segunda2");//Nome inválido

        try {
            em.flush();
        }catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Cada palavra deve iniciar com letra maiúscula, e as demais minúsculas, podendo ser palavras acentuadas ou não.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    } 
    
}

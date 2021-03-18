
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Profissional;
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
public class ProfissionalValidationTest extends GenericTest {
 
     @Test(expected = ConstraintViolationException.class) 
    public void persistirProfissionalInvalido() {
        Profissional profissional = null;
        
        try {
            profissional = new Profissional();
            profissional.setNome("Andre Souza");
            profissional.setProfissao("Dentist@");//Profissão inválida
            profissional.setEspecializacao("Branqueamento1dentario");//Especialização inválida
            profissional.setHoraInicial(criarHora(8,0,0));
            profissional.setHoraFinal(criarHora(13,0,0));
            
            em.persist(profissional);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach(violation -> {               
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),                    
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Profissional.profissao: Apenas palavras com ou sem acentuação, não sendo permitido números e caracteres especiais."),
                                startsWith("class descorp.agendamentoweb.entities.Profissional.especializacao: Apenas palavras com ou sem acentuação, não sendo permitido números e caracteres especiais.")
                        )
                );
            });
            assertEquals(2, constraintViolations.size());
            assertNull(profissional.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarProfissionalInvalido() {
        Profissional profissional= em.find(Profissional.class, 1L);
        profissional.setNome("Rafaela 1 Silva");//Nome inválido

        try {
            em.flush();
        }catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Cada palavra deve iniciar com letra maiúscula, seguida por letras minúsculas, não sendo permitido números e caracteres especiais.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    } 
    
}


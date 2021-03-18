package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Endereco;
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
public class EnderecoValidationTest extends GenericTest{
    @Test(expected = ConstraintViolationException.class)
    public void persistirEnderecoInvalido() {
        Endereco endereco = null;
        try {
            endereco = new Endereco();
            endereco.setBairro("C�u Azul");
            endereco.setCep("57465-547");//CEP inv�lido
            endereco.setCidade("Camaragibe");
            endereco.setEstado("JA");//Estado Inv�lido
            endereco.setNumero(51);
            endereco.setRua("Rua do C�u");
            
            em.persist(endereco);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Endereco.cep: CEP inv�lido. Deve estar no formado NN.NNN-NNN, onde N � n�mero natural"),
                                startsWith("class descorp.agendamentoweb.entities.Endereco.estado: Estado Inv�lido")
                                
                        )
                );
            });

            assertEquals(2, constraintViolations.size());
            assertNull(endereco.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void atualizarEnderecoInvalido() {
        TypedQuery<Endereco> query = em.createQuery("SELECT e FROM Endereco e WHERE e.cep like :cep", Endereco.class);
        query.setParameter("cep", "51.250-490");
        Endereco endereco = query.getSingleResult();
        endereco.setEstado("PP");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Estado Inv�lido", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}

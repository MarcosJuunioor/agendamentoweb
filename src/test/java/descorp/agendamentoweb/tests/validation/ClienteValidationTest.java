package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Set;
import javax.persistence.TypedQuery;
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
public class ClienteValidationTest extends GenericTest {
/*
    @Test(expected = ConstraintViolationException.class)
    public void persistirClienteInvalido() {
        Cliente cliente = null;

        try {
            cliente = new Cliente();
            cliente.setEmail("julio@gmail.com");
            cliente.setSenha("Juli0@123");
            cliente.setTelefone("81978571456");
            cliente.setNome("julio andrade"); //Nome inválido
            cliente.setCPF("123"); //Cpf inválido
            em.persist(cliente);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Cliente.nome: Cada palavra deve iniciar com letra maiúscula, seguida por letras minúsculas."),
                                startsWith("class descorp.agendamentoweb.entities.Cliente.CPF: número do registro de contribuinte individual brasileiro (CPF) inválido")
                        )
                );
            });
            assertEquals(2, constraintViolations.size());
            assertNull(cliente.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarClienteInvalido() {
        TypedQuery<Cliente> query = em.createQuery("select c from Cliente c where c.CPF like :cpf", Cliente.class);
        query.setParameter("cpf", "468.381.220-73");
        Cliente cliente = query.getSingleResult();
        cliente.setNome("tayna");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Cada palavra deve iniciar com letra maiúscula, seguida por letras minúsculas.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    } */

}

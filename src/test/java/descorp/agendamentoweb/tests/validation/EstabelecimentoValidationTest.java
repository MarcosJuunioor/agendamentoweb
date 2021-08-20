package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Endereco;
import descorp.agendamentoweb.entities.Estabelecimento;
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
public class EstabelecimentoValidationTest extends GenericTest {

    @Test(expected = ConstraintViolationException.class)
    public void persistirEstabelecimentoInvalido() {
        Estabelecimento estabelecimento = null;
        try {
            estabelecimento = new Estabelecimento();
            estabelecimento.setEmail("recanto-beleza@gmail.com");
            estabelecimento.setTelefone("81988771548");
            estabelecimento.setSenha("5487");
            estabelecimento.setRazaoSocial("Recanto da Beleza");
            estabelecimento.setCNPJ("00156458000199");//CNPJ inválido

            Endereco endereco = em.find(Endereco.class, 1L);
            estabelecimento.setEndereco(endereco);

            em.persist(estabelecimento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Estabelecimento.CNPJ: CNPJ inválido. Deve estar no formado NN.NNN.NNN/NNNN-NN, onde N é número natural")
                        )
                );
            });

            assertEquals(1, constraintViolations.size());
            assertNull(estabelecimento.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void atualizarEstabelecimentoInvalido() {
        TypedQuery<Estabelecimento> query = em.createQuery("SELECT e FROM Estabelecimento e WHERE e.CNPJ like :cnpj", Estabelecimento.class);
        query.setParameter("cnpj", "12.345.675/8479-07");
        Estabelecimento estabelecimento = query.getSingleResult();
        estabelecimento.setCNPJ("12.345.675/847907");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("CNPJ inválido. Deve estar no formado NN.NNN.NNN/NNNN-NN, onde N é número natural", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}

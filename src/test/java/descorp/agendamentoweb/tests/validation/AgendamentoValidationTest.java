/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.entities.Usuario;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.Calendar;
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
public class AgendamentoValidationTest extends GenericTest {
/*
    @Test(expected = ConstraintViolationException.class)
    public void persistirAgendamentoInvalido() {
        Agendamento agendamento = null;

        try {
            agendamento = new Agendamento();
            agendamento.setData(criarData(15, Calendar.FEBRUARY, 2021));//data inválida
            agendamento.setHora(criarHora(10, 0, 0)); 

            Usuario usuario = em.find(Usuario.class, 2L);
            agendamento.setUsuario(usuario);

            Procedimento procedimento = em.find(Procedimento.class, 1L);
            agendamento.setProcedimento(procedimento);

            Profissional profissional = em.find(Profissional.class, 1L);
            agendamento.setProfissional(profissional);
            em.persist(agendamento);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.agendamentoweb.entities.Agendamento.data: deve ser uma data futura")
                        )
                ); 
            });

            assertEquals(1, constraintViolations.size());
            assertNull(agendamento.getId());
            throw ex;
        }
    }
    /*
    @Test(expected = ConstraintViolationException.class)
    public void atualizarVendedorInvalido() {
        TypedQuery<Vendedor> query = em.createQuery("SELECT v FROM Vendedor v WHERE v.cpf like :cpf", Vendedor.class);
        query.setParameter("cpf", "484.854.847-03");
        Vendedor vendedor = query.getSingleResult();
        vendedor.setSenha("teste123456");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("A senha deve possuir pelo menos um caractere de: pontuação, maiúscula, minúscula e número.", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }*/
}

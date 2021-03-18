
package descorp.agendamentoweb.tests.validation;

import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.tests.GenericTest;
import java.util.List;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


/**
 *
 * @author Evellinne
 */
public class DiasSemanaValidationTest extends GenericTest {
    
    //Consulta dos profissionais que trabalham em um determinado dia da semana
    @Test
    public void profissionaisDiasSemana(){
        TypedQuery<Profissional> query = em.createQuery(
                "SELECT p FROM Profissional p "
                + "JOIN DiasSemana d "
                + "WHERE p MEMBER OF d.profissionais "
                + "AND d.nome = :nome_dia",
                Profissional.class);
        query.setParameter("nome_dia", "Sexta");
        
        List<Profissional> profissionais = query.getResultList();
        assertNotNull(profissionais);
        assertEquals("Rafaela Silva", profissionais.get(0).getNome());
    }
    
}

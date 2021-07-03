package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Procedimento;
import static descorp.agendamentoweb.models.GenericModel.em;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
/**
 *
 * @author marco
 */
@Stateless
public class ProcedimentoModel extends GenericModel implements Serializable{
    
    public Procedimento consultarProcediemnto(Long id) {
        return em.find(Procedimento.class, id);
    }
    
    public List<Procedimento> todosProcedimentos() {
        return em.createNamedQuery(Procedimento.PROCEDIMENTO_TODOS).getResultList();
    }
    
    public void atualizarProcedimento(Procedimento procedimento){
        beginTransaction();
        procedimento = em.merge(procedimento);
        em.flush();
        commitTransaction();
    }
}
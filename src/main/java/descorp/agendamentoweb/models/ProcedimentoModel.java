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
    
    public Procedimento persistirProcedimento(Procedimento procedimento){
        beginTransaction();
        em.persist(procedimento);
        em.flush();
        commitTransaction();
        return procedimento;
    }
    
    public void atualizarProcedimento(Procedimento procedimento){
        beginTransaction();
        procedimento = em.merge(procedimento);
        em.flush();
        commitTransaction();
    }
    
    public void deletarProcedimento(Procedimento procedimento){
        beginTransaction();
        if(!em.contains(procedimento)){
            procedimento = em.merge(procedimento);
        }
        em.remove(procedimento);
        em.flush();
        commitTransaction();
    }
    
    public void deletarProcediemntos(List<Procedimento> procedimentos) {
        beginTransaction();
        int tam = procedimentos.size();
        for(int i=0; i<tam; i++){
            if(!em.contains(procedimentos.get(i))){
                procedimentos.set(i, em.merge(procedimentos.get(i)));
            }
            em.remove(procedimentos.get(i));
        }
        em.flush();
        commitTransaction();
    }
}
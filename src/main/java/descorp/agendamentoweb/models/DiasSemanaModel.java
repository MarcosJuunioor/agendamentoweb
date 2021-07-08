package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.DiasSemana;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Beatriz Silva
 */
@Stateless
public class DiasSemanaModel extends GenericModel implements Serializable{
    
    public List<DiasSemana> todosDiasSemana(){
        return em.createNamedQuery(DiasSemana.DIASEMANA_TODOS).getResultList();
    }
    
    public DiasSemana diaSemanaPorNome(String nome) {
        TypedQuery<DiasSemana> query = em.createNamedQuery("DiasSemana.PorNome", DiasSemana.class);
        query.setParameter("nome", nome);
        return query.getSingleResult();
    }
}

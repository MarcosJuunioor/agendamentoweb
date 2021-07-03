package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Profissional;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author marco
 */
@Stateless
public class ProfissionalModel extends GenericModel implements Serializable{
    
    public ProfissionalModel(){
        super();
    }

    public Profissional consultarProfissional(Long id) {
        return em.find(Profissional.class, id);
    }
    
    public List<Profissional> todosProfissionais(){
        return em.createNamedQuery(Profissional.PROFISSIONAL_TODOS).getResultList();
    }
    public void atualizarProfissional(Profissional profissional){
        beginTransaction();
        profissional = em.merge(profissional);
        em.flush();
        commitTransaction();
    }
}

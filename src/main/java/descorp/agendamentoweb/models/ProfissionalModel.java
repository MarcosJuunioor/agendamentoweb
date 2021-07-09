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
    public Profissional persistirProfissional(Profissional profissional){
        beginTransaction();
        em.persist(profissional);
        em.flush();
        commitTransaction();
        return profissional;
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
    public void deletarProfissional(Profissional profissional){
        beginTransaction();
        if(!em.contains(profissional)){
            profissional = em.merge(profissional);
        }
        em.remove(profissional);
        em.flush();
        commitTransaction();
    }
    
    public void deletarProfissionais(List<Profissional> profissionais){
        beginTransaction();
        int tam = profissionais.size();
        for(int i=0; i<tam; i++){
            if (!em.contains(profissionais.get(i))) {
                profissionais.set(i, em.merge(profissionais.get(i)));
            }
            em.remove(profissionais.get(i));
        }
        em.flush();
        commitTransaction();
    }
}

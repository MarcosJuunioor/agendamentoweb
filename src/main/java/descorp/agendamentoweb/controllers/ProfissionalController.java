package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.ProfissionalModel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;


/**
 *
 * @author Beatriz Silva
 */
@ManagedBean
@ViewScoped
public class ProfissionalController implements Serializable{

    @EJB
    private ProfissionalModel bean;
    
    private List<Profissional> listaProfissional;
    
    public ProfissionalController(){
        this.bean = new ProfissionalModel();
    }

    public List<Profissional> getListaProfissional() {
        if(this.listaProfissional == null || this.listaProfissional.isEmpty()){
            this.listaProfissional = bean.todosProfissionais();
        }
        return listaProfissional;
    }

    public void setListaProfissional(List<Profissional> listaProfissional) {
        this.listaProfissional = listaProfissional;
    }
    
    public void editarProfissional(RowEditEvent<Profissional> event){
        FacesMessage msg = new FacesMessage("Profissional Editado!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        bean.atualizarProfissional(event.getObject());
    }
    
    
    
}

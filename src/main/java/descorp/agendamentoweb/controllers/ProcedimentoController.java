package descorp.agendamentoweb.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ejb.EJB;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.models.ProcedimentoModel;
import java.util.List;

/**
 * @author Beatriz Lima
 */
@ManagedBean
@ViewScoped
public class ProcedimentoController {

    @EJB
    private ProcedimentoModel bean;
    
    private List<Procedimento> listProcedimento;
    private Procedimento procedimento;

    public List<Procedimento> getListProcedimento() {
        if(this.listProcedimento == null || this.listProcedimento.isEmpty()){
            this.bean = new ProcedimentoModel();
            this.listProcedimento = bean.todosProcedimentos();
        }
        return listProcedimento;
    }

    public void setListProcedimento(List<Procedimento> listProcedimento) {
        this.listProcedimento = listProcedimento;
    }

    /*public Procedimento getProcedimento(Long id) {
        return bean.;
    }*/

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }


    

    }

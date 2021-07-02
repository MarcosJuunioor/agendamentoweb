package descorp.agendamentoweb.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ejb.EJB;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.models.ProcedimentoModel;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 * @author Beatriz Lima
 */
@ManagedBean
@ViewScoped
public class ProcedimentoController implements Serializable{

    @EJB
    private ProcedimentoModel bean;
    
    private List<Procedimento> listProcedimento;
    private Procedimento procedimento;
    private List<Date> duracoes;
    private String duracao;
    
    public ProcedimentoController() throws ParseException{
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm"); 
        this.procedimento = new Procedimento();
        duracoes = new ArrayList<Date>();
            duracoes.add(fmt.parse("00:30"));
            duracoes.add(fmt.parse("00:40"));
            duracoes.add(fmt.parse("01:00"));
            duracoes.add(fmt.parse("01:30"));
            duracoes.add(fmt.parse("02:00"));
            duracoes.add(fmt.parse("02:30"));
            duracoes.add(fmt.parse("03:00"));
            duracoes.add(fmt.parse("03:30"));
            duracoes.add(fmt.parse("04:00"));
    }
    
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

   public Procedimento getProcedimento() {
        return this.procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public List<Date> getDuracoes() {
        return duracoes;
    }

    public void setDuracoes(List<Date> duracoes) {
        this.duracoes = duracoes;
    }

    public String getDuracaoFMT(Date duracao){
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");  
        String duracaoSTR = fmt.format(duracao);
        if(duracaoSTR.startsWith("12:")){
            duracaoSTR = "00:"+duracaoSTR.split(":")[1];
        }
        return duracaoSTR;
    }
    
    public void editarProcedimento(RowEditEvent<Procedimento> event) throws ParseException {
        FacesMessage msg = new FacesMessage("Procedimento Editado!");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if(duracao != null){
            SimpleDateFormat fmt = new SimpleDateFormat("hh:mm"); 
            event.getObject().setDuracao(fmt.parse(duracao));
        }
        bean.atualizarProcedimento(event.getObject());
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    
}

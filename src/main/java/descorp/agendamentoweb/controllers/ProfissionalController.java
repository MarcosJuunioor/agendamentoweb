package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.DiasSemana;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.DiasSemanaModel;
import descorp.agendamentoweb.models.ProfissionalModel;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;


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
    private List<String> diasSelecionados;
    private Profissional profissionalSelecionado;
    private List<Profissional> profissionaisSelecionados;
    private String hrInicial;
    private String hrFinal;
    
    public ProfissionalController(){
        this.bean = new ProfissionalModel();
        this.profissionalSelecionado = new Profissional();
        this.diasSelecionados = new ArrayList<>();
        this.hrInicial = "";
        this.hrFinal = "";
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
    
    public void editarProfissional() throws ParseException{
        FacesMessage msg;
        this.profissionalSelecionado.setDiasSemana(new ArrayList<>());
        DiasSemanaModel dsBean = new DiasSemanaModel();
        for(String dia: diasSelecionados){
            this.profissionalSelecionado.getDiasSemana().add(dsBean.diaSemanaPorNome(dia));
        }
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        this.profissionalSelecionado.setHoraInicial(fmt.parse(hrInicial));
        this.profissionalSelecionado.setHoraFinal(fmt.parse(hrFinal));
        if(this.profissionalSelecionado.getId() == null){
            bean.persistirProfissional(this.profissionalSelecionado);
            this.listaProfissional.add(this.profissionalSelecionado);
            msg = new FacesMessage("Profissional Criado!"); 
        } else {
            msg = new FacesMessage("Profissional Editado!"); 
            bean.atualizarProfissional(this.profissionalSelecionado);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public String listarDiasSemana(List<DiasSemana> diasSemana){
        String dias="";
        if (!diasSemana.isEmpty()){
            dias = diasSemana.get(0).getNome();
            int tam = diasSemana.size();
            for(int i=1; i<tam; i++){
                dias += ", "+diasSemana.get(i).getNome();
            }
        }
        return dias;
    }
    
    public void setProfissionalSelecionado(Profissional profissionalSelecionado) {
        this.diasSelecionados = new ArrayList<>();
        for(DiasSemana ds: profissionalSelecionado.getDiasSemana()){
            this.diasSelecionados.add(ds.getNome());
        }
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        this.hrInicial = fmt.format(profissionalSelecionado.getHoraInicial());
        this.hrFinal = fmt.format(profissionalSelecionado.getHoraFinal());
        this.profissionalSelecionado = profissionalSelecionado;
    }
    
    public void novoProfissional(){
        this.profissionalSelecionado = new Profissional();
        this.diasSelecionados = new ArrayList<>();
        this.hrInicial = "";
        this.hrFinal = "";
    }
    
    public void apagarProfissional(){
        this.listaProfissional.remove(this.profissionalSelecionado);
        bean.deletarProfissional(this.profissionalSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Funcionário Removido"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-funcs", "btnApagarFuncionarios");
    }
    public boolean selecionouProfissionais(){
        return this.profissionaisSelecionados != null && !this.profissionaisSelecionados.isEmpty();
    }    
    public String getMensagemBotao() {
        if(this.selecionouProfissionais()){
            int qtd = this.profissionaisSelecionados.size();
            return qtd > 1 ? qtd + " funcionários selecionados" : "1 funcionário selecionado";
        }
        return "Excluir";
    }
    
    public void apagarProfissionais(){
        this.listaProfissional.removeAll(this.profissionaisSelecionados);
        bean.deletarProfissionais(this.profissionaisSelecionados);
        this.profissionaisSelecionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Funcionários Removidos"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-funcs", "btnApagarFuncionarios");
    }
    
    public List<String> getDiasSelecionados() {
        return diasSelecionados;
    }

    public void setDiasSelecionados(List<String> diasSelecionados) {
        this.diasSelecionados = diasSelecionados;
    }

    public Profissional getProfissionalSelecionado() {
        return profissionalSelecionado;
    }

    public String getHrInicial() {
        return hrInicial;
    }

    public void setHrInicial(String hrInicial) {
        this.hrInicial = hrInicial;
    }

    public String getHrFinal() {
        return hrFinal;
    }

    public void setHrFinal(String hrFinal) {
        this.hrFinal = hrFinal;
    }

    public List<Profissional> getProfissionaisSelecionados() {
        return profissionaisSelecionados;
    }

    public void setProfissionaisSelecionados(List<Profissional> profissionaisSelecionados) {
        this.profissionaisSelecionados = profissionaisSelecionados;
    }
}

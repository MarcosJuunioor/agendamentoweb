package descorp.agendamentoweb.controllers;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.DiasSemana;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.DiasSemanaModel;
import descorp.agendamentoweb.models.ProfissionalModel;
import descorp.agendamentoweb.utilities.EmailSender;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
    private Long profissionalRealocado;
    private List<Profissional> profissionaisSelecionados;
    private List<Agendamento> listaAgendamentosFuturos; 
    private Long idProfissionalRealocado;
    private String hrInicial;
    private String hrFinal;
    
    public ProfissionalController(){
        this.bean = new ProfissionalModel();
        this.profissionalSelecionado = new Profissional();
        this.diasSelecionados = new ArrayList<>();
        this.hrInicial = "";
        this.hrFinal = "";
    }
    
    public String formatarData(Date data){
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        return fmt.format(data);
    }   
    
    public boolean exibirMenuTodos(){
        if (this.listaAgendamentosFuturos == null)
            return false;
        return (this.listaAgendamentosFuturos.size() > 1);
    }
    
    public String clienteNome(Long usuarioID){
        return bean.clienteAgendamento(usuarioID).getNome();
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
    
    public void novoProfissional(){
        this.profissionalSelecionado = new Profissional();
        this.diasSelecionados = new ArrayList<>();
        this.hrInicial = "";
        this.hrFinal = "";
    }
    
    public void apagarProfissional(){
        //this.listaProfissional.remove(this.profissionalSelecionado);
        bean.deletarProfissional(this.profissionalSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("FuncionÃ¡rio Removido"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-funcs", "btnApagarFuncionarios");
    }
    public boolean selecionouProfissionais(){
        return this.profissionaisSelecionados != null && !this.profissionaisSelecionados.isEmpty();
    }
    //Realoca agendamento a nível de Objeto, enquanto o usuário modifica a tela
    public void realocarAgendamento(Agendamento agendamento){
        Profissional profissional = bean.consultarProfissional(this.idProfissionalRealocado);
        agendamento.setProfissional(profissional);
        //colocar null no menu de todos
        this.profissionalRealocado = null;
        PrimeFaces.current().ajax().update("form:todos");
    }
    //Realoca todos agendamentos a nível de Objeto, enquanto o usuário modifica a tela todos
    public void realocarTodosAgendamentos(){
        Profissional profissional = bean.consultarProfissional(this.profissionalRealocado);
        for(Agendamento agendamento: this.listaAgendamentosFuturos){
            agendamento.setProfissional(profissional);
        }
        this.idProfissionalRealocado = this.profissionalRealocado;
        PrimeFaces.current().ajax().update("form:realocacao-content");
    }
    //Realiza a realocão em todos os agendamentos futuros e depois apaga o profissional e avisa ao cliente
    public void realocarAgendamentosEApaga(){
        EmailSender es = new EmailSender();
        
        for(Agendamento agendamento: this.listaAgendamentosFuturos){
            //Atualiza o agendamento com novo profissional
            bean.agendamentoProfissional(agendamento);
            //Tira do profissional anterior
            this.profissionalSelecionado.getAgendamentos().remove(agendamento);
            //Enviar email
            int hora = new Date(System.currentTimeMillis()).getHours();
            ArrayList<String> enderecos = new ArrayList<>();
            enderecos.add(agendamento.getUsuario().getEmail());
            String saudacao = (hora<12)?("Bom dia"):( (hora<18)?("Boa tarde"):("Boa Noite") );
            String assuntoEmail = "Alteração do profissional no seu agendamento - " + agendamento.getProcedimento().getNome();
            String mensagemEmail = saudacao+", "+this.clienteNome(agendamento.getUsuario().getId())+"!<br><br>"
                    + "O profissional, "+this.profissionalSelecionado.getNome()+", para seu agendamento de "
                    + agendamento.getProcedimento().getNome()+", "
                    + "não vai mais poder te atender por força maior.<br>Quem irá te atender será "
                    + agendamento.getProfissional().getNome()+".<br><br>"
                    + "Para mais informações, entre em contato com o estabelecimento.";

            es.enviarEmail(assuntoEmail, mensagemEmail, enderecos);
        }
        this.apagarProfissional();
    }
    public ArrayList<Integer> loopAgendamento(){
        ArrayList<Integer> lista = new ArrayList<>();
        int tam = this.listaAgendamentosFuturos==null?0:this.listaAgendamentosFuturos.size();
        for(int i= 0; i<tam; i++){
            lista.add(i);
        }
        return lista;
    }
    public String getMensagemBotao() {
        if(this.selecionouProfissionais()){
            int qtd = this.profissionaisSelecionados.size();
            return qtd > 1 ? qtd + " funcionÃ¡rios selecionados" : "1 funcionÃ¡rio selecionado";
        }
        return "Excluir";
    }
    
    public void apagarProfissionais(){
        this.listaProfissional.removeAll(this.profissionaisSelecionados);
        bean.deletarProfissionais(this.profissionaisSelecionados);
        this.profissionaisSelecionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("FuncionÃ¡rios Removidos"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-funcs", "btnApagarFuncionarios");
    }
    
    //Checa se há agendamento futuros de uma profissional
    public List<Agendamento> agendamentosFuturos(Profissional profissional){
        List<Agendamento> agendFuturos = new ArrayList<>();
        Date dataAtual = new Date(System.currentTimeMillis());
        this.idProfissionalRealocado = null;
        this.profissionalRealocado = null;
        for(Agendamento agendamento: profissional.getAgendamentos()){
            if(agendamento.getData().after(dataAtual)){
                agendFuturos.add(agendamento);
            }
        }
        return agendFuturos;
    }
    
    //Checar profissionalSelecionado antes de deletar
    public void checarDeleteProfissionalSelecionado(){
        this.listaAgendamentosFuturos = agendamentosFuturos(profissionalSelecionado);
        if(this.listaAgendamentosFuturos.size()>0){
            PrimeFaces.current().ajax().update("form:realocacao");
            PrimeFaces.current().executeScript("PF('RealocarDialog').show();");
        } else {
            this.apagarProfissional();
        }
    } 
    
    public List<Profissional> getListaProfissional() {
        this.listaProfissional = bean.todosProfissionais();
        return listaProfissional;
    }

    public void setListaProfissional(List<Profissional> listaProfissional) {
        this.listaProfissional = listaProfissional;
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

    public List<Agendamento> getListaAgendamentosFuturos() {
        return listaAgendamentosFuturos;
    }

    public void setListaAgendamentosFuturos(List<Agendamento> listaAgendamentosFuturos) {
        this.listaAgendamentosFuturos = listaAgendamentosFuturos;
    }

    public Long getProfissionalRealocado() {
        return profissionalRealocado;
    }

    public void setProfissionalRealocado(Long profissionalRealocado) {
        this.profissionalRealocado = profissionalRealocado;
    }

    public Long getIdProfissionalRealocado() {
        return idProfissionalRealocado;
    }

    public void setIdProfissionalRealocado(Long idProfissionalRealocado) {
        this.idProfissionalRealocado = idProfissionalRealocado;
    }
}

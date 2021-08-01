package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Agendamento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ejb.EJB;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.ProcedimentoModel;
import descorp.agendamentoweb.models.ProfissionalModel;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import descorp.agendamentoweb.utilities.EmailSender;

/**
 * @author Beatriz Silva
 */
@ManagedBean
@ViewScoped
public class ProcedimentoController implements Serializable {

    @EJB
    private ProcedimentoModel bean;

    private List<String> profissionais;
    private List<String> profissionaisSelecionados;
    private List<Procedimento> listaProcedimento;
    private List<Procedimento> procedimentosSelecionados;
    private Procedimento procedimentoSelecionado;
    private String duracao;

    public ProcedimentoController() {
        this.procedimentoSelecionado = new Procedimento();
        this.duracao = "";
        this.profissionais = new ArrayList<>();
        ProfissionalModel pbean = new ProfissionalModel();
        for (Profissional profissional : pbean.todosProfissionais()) {
            profissionais.add(profissional.getNome() + " - " + profissional.getEspecializacao() + ";" + profissional.getId());
        }

    }

    public List<Procedimento> getListaProcedimento() {
        if (this.listaProcedimento == null || this.listaProcedimento.isEmpty()) {
            this.bean = new ProcedimentoModel();
            this.listaProcedimento = bean.todosProcedimentos();
        }
        return listaProcedimento;
    }

    public void setListaProcedimento(List<Procedimento> listaProcedimento) {
        this.listaProcedimento = listaProcedimento;
    }

    public Procedimento getProcedimentoSelecionado() {
        return this.procedimentoSelecionado;
    }

    public void setProcedimentoSelecionado(Procedimento procedimentoSelecionado) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        this.duracao = fmt.format(procedimentoSelecionado.getDuracao());
        this.procedimentoSelecionado = procedimentoSelecionado;

        this.profissionaisSelecionados = new ArrayList<>();
        for (Profissional profissional : procedimentoSelecionado.getProfissionais()) {
            this.profissionaisSelecionados.add(profissional.getNome() + " - " + profissional.getEspecializacao() + ";" + profissional.getId());
        }
    }

    public String getDuracaoFMT(Date duracao) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        return fmt.format(duracao);
    }

    public String nomeProcedimento(String procedimento) {
        return procedimento.split(";")[0];
    }

    public void editarProcedimento() throws ParseException {
        FacesMessage msg;
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");
        this.procedimentoSelecionado.setDuracao(fmt.parse(duracao));

        ProfissionalModel pbean = new ProfissionalModel();
        ArrayList<Profissional> profissionais = new ArrayList<>();
        for (String prof : this.profissionaisSelecionados) {
            profissionais.add(pbean.consultarProfissional(Long.valueOf(prof.split(";")[1])));
        }
        this.procedimentoSelecionado.setProfissionais(profissionais);
        if (this.procedimentoSelecionado.getId() == null) {
            bean.persistirProcedimento(this.procedimentoSelecionado);
            this.listaProcedimento.add(this.procedimentoSelecionado);
            msg = new FacesMessage("Procedimento Criado!");
        } else {
            bean.atualizarProcedimento(this.procedimentoSelecionado);
            msg = new FacesMessage("Procedimento Editado!");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void novoProcedimento() {
        this.duracao = "";
        this.profissionaisSelecionados = new ArrayList<>();
        this.procedimentoSelecionado = new Procedimento();
    }

    public boolean selecionouProcedimentos() {
        return this.procedimentosSelecionados != null && !this.procedimentosSelecionados.isEmpty();
    }

    public String getMensagemBotao() {
        if (this.selecionouProcedimentos()) {
            int qtd = this.procedimentosSelecionados.size();
            return qtd > 1 ? qtd + " procedimentos selecionados" : "1 procedimento selecionado";
        }
        return "Excluir";
    }

    public void apagarProcedimento() {
        this.listaProcedimento.remove(this.procedimentoSelecionado);
        bean.deletarProcedimento(this.procedimentoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Procedimento Removido"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-procs");

        EmailSender es = new EmailSender();
        ArrayList<String> enderecos = new ArrayList<String>();

        for (Agendamento a : this.procedimentoSelecionado.getAgendamentos()) {
            enderecos.add(a.getUsuario().getEmail());
        }

        String assuntoEmail = "Cancelamento de Agendamento - " + this.procedimentoSelecionado.getNome();
        String mensagemEmail = "O seu agendamento para "
                + this.procedimentoSelecionado.getNome()
                + "foi cancelado por força maior. Para mais informações, "
                + "entre em contato com o estabelecimento.";

        es.enviarEmail(assuntoEmail, mensagemEmail, enderecos);

    }

    public void apagarProcedimentos() {
        this.listaProcedimento.removeAll(this.procedimentosSelecionados);
        bean.deletarProcediemntos(this.procedimentosSelecionados);
        this.procedimentosSelecionados = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Procedimentos Removidos"));
        PrimeFaces.current().ajax().update("form:msgs", "form:dt-procs");

        EmailSender es = new EmailSender();
        ArrayList<String> enderecos = new ArrayList<String>();

        for (Procedimento p : this.procedimentosSelecionados) {
            for (Agendamento a : p.getAgendamentos()) {
                enderecos.add(a.getUsuario().getEmail());
            }
        }

        String assuntoEmail = "Cancelamento de Agendamento - " + this.procedimentoSelecionado.getNome();
        String mensagemEmail = "O seu agendamento para "
                + this.procedimentoSelecionado.getNome()
                + " foi cancelado por força maior. Para mais informações, "
                + "entre em contato com o estabelecimento.";

        es.enviarEmail(assuntoEmail, mensagemEmail, enderecos);

    }
    
    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public List<Procedimento> getProcedimentosSelecionados() {
        return procedimentosSelecionados;
    }

    public void setProcedimentosSelecionados(List<Procedimento> procedimentosSelecionados) {
        this.procedimentosSelecionados = procedimentosSelecionados;
    }

    public List<String> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<String> profissionais) {
        this.profissionais = profissionais;
    }

    public List<String> getProfissionaisSelecionados() {
        return profissionaisSelecionados;
    }

    public void setProfissionaisSelecionados(List<String> profissionaisSelecionados) {
        this.profissionaisSelecionados = profissionaisSelecionados;
    }

}

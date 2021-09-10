/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.AgendamentoModel;
import descorp.agendamentoweb.models.ClienteModel;
import descorp.agendamentoweb.models.ProcedimentoModel;
import descorp.agendamentoweb.servlets.AgendamentoServlet;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import static java.util.concurrent.TimeUnit.*;
import javax.faces.context.FacesContext;
import org.eclipse.persistence.sdo.helper.extension.SDOUtil;

/**
 *
 * @author marco
 */
@ManagedBean
@ViewScoped
public class AgendamentoController implements Serializable {

    List<Agendamento> agendamentosDia;

    private final AgendamentoModel bean;
    private final ClienteModel clienteModel;
    private final ProcedimentoModel procedimentoModel;
    private Cliente cliente;
    private final ArrayList<String> horariosEstabelecimento;
    private final ArrayList<Agendamento> horariosDisponiveis;
    private List<Agendamento> agendamentos;
    private Agendamento agendamentoSelecionado;
    private List<Agendamento> agendamentosSelecionados;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String horarioStrSelecionado;
    private Date dtSelecionada;
    private ArrayList<Long> idProfissionais;
    private Long idUsuario; 
    private Long idCliente; 
    private Long idProcedimento; 
    private Long idProfissional; 
    private String data;
    private ArrayList<String> horarioDisponiveis;

    public AgendamentoController() {
        this.bean = new AgendamentoModel ();
        this.clienteModel = new ClienteModel();
        this.procedimentoModel = new ProcedimentoModel();
        this.horariosEstabelecimento = new ArrayList<String>();
        this.horariosDisponiveis = new ArrayList<Agendamento>();
        this.agendamentoSelecionado = new Agendamento();
        this.horarioDisponiveis = new ArrayList<>();
    }
    
    public ArrayList<String> getHorarioEstabelecimento(){
        ArrayList<String> horarios = new ArrayList<>();

        //Horários nos quais o estabelecimento faz agendamentos     
        horarios.add("08:00");
        horarios.add("10:00");
        horarios.add("12:00");
        horarios.add("14:00");
        horarios.add("16:00");
        horarios.add("18:00");
        
        return horarios;
    }

    public List<Agendamento> getHorariosIndisponiveis(String dataSelecionada, String idProf, String idProc, HttpSession session) {

        AgendamentoModel agendamentoModel = new AgendamentoModel();

        if (this.horariosDisponiveis == null || this.horariosDisponiveis.isEmpty()) {

            Long idProcedimento = Long.valueOf(idProc);
            Long idProfissional = Long.valueOf(idProf);

            //Horários nos quais o estabelecimento faz agendamentos   
            this.horariosEstabelecimento.add("08:00");
            this.horariosEstabelecimento.add("10:00");
            this.horariosEstabelecimento.add("12:00");
            this.horariosEstabelecimento.add("14:00");
            this.horariosEstabelecimento.add("16:00");
            this.horariosEstabelecimento.add("18:00");

            try {
                String startDateString = dataSelecionada;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String saida = sdf2.format(sdf.parse(startDateString));

                Date dataSaida = sdf2.parse(saida);

                //Recupera os agendamentos para o dia selecionado
                agendamentosDia = this.bean.consultarHorariosIndisponiveis(idProfissional, idProcedimento, dataSaida);

                //Cria a lista de objetos que serão exibidos na tela
                for (int a = 0; a < horariosEstabelecimento.size(); a++) {
                    this.horariosDisponiveis.add(agendamentoModel.criarAgendamento(dataSaida, agendamentoModel.criarHora(Integer.parseInt(this.horariosEstabelecimento.get(a).substring(0, 2)), 0, 0), idProcedimento, idProfissional, (Long) session.getAttribute("idUsuario")));
                }

                //Remove da lista de horários disponíveis os horário que já estão ocupados
                if (agendamentosDia.size() > 0) {

                    for (Agendamento a : agendamentosDia) {
                        String hora = getDuracaoFMT(a.getHora());
                        for (int i = 0; i < horariosDisponiveis.size(); i++) {
                            if (horariosDisponiveis.get(i).getHora().getHours() == (Integer.parseInt(hora.substring(0, 2)))) {
                                horariosDisponiveis.remove(i);
                            }
                        }
                    }
                }

            } catch (NumberFormatException e) {
                System.err.println("Parâmetros não localizados: " + e.getMessage());
            } catch (ParseException ex) {
                Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.horariosDisponiveis;
    }

    public List<Agendamento> getAgendamentosUsuario(Long id, String dt) {
        if(idUsuario==null && data==null){
            this.idUsuario = id;
            this.data = dt;
        }
        try {
            Date dataObj = this.getDateByString(data);
            this.agendamentos = this.bean.getAgendamentosUsuario(idUsuario, dataObj);
        } catch (NumberFormatException e) {
            System.err.println("Parâmetros não localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
    }
    
    public ArrayList<String> listarHorarios(){
        ArrayList<String> horarios = this.getHorarioEstabelecimento();

        Long idProc = this.agendamentoSelecionado.getProcedimento().getId();
        Long idProf = this.agendamentoSelecionado.getProfissional().getId();


        try {

            //Recupera os agendamentos para o dia selecionado
            agendamentosDia = this.bean.consultarHorariosIndisponiveis(idProf, idProc, this.dtSelecionada);

            //Remove da lista de horários disponíveis os horário que já estão ocupados
            if (agendamentosDia.size() > 0) {
                for (Agendamento a : agendamentosDia) {
                    String hora = getDuracaoFMT(a.getHora());
                    for (int i = 0; i < horarios.size(); i++) {
                        if (horarios.get(i).equals(hora)) {
                            horarios.remove(i);
                        }
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.err.println("Parâmetros não localizados: " + e.getMessage());
        }

        return horarios;
    }
    
    //Realiza a troca da data e hora de uma agendamento
    public void reagendar(){
        //Trocar data e hora
        this.agendamentoSelecionado.setData(this.dtSelecionada);
        this.data = getDataFMT(dtSelecionada);
        Date hora = agendamentoSelecionado.getHora();
        hora.setHours(Integer.parseInt(this.horarioStrSelecionado.split(":")[0]));
        hora.setMinutes(Integer.parseInt(this.horarioStrSelecionado.split(":")[1]));
        this.agendamentoSelecionado.setHora(hora);
        //Atualizar agendamento
        bean.atualizarAgendamento(agendamentoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamento Editado!", "Nova data e hora: "+getDataFMT(this.dtSelecionada)+" "+getDuracaoFMT(hora)));
        
        this.dtSelecionada = null;
    }
    public List<String> getAgendamentosDoDia(Date data){
        
        List<String> mListaEmail = this.bean.getDatasAgendamentosDoDia(data);
        return mListaEmail;
        
    }

    public List<Agendamento> consultarAgendamentosPorData(String data) {
        try {
            Date dataObj = this.getDateByString(data);
            this.agendamentos = this.bean.consultarAgendamentosPorData(dataObj);
        } catch (NumberFormatException e) {
            System.err.println("ParÃ¢metros não localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
    }

    public Cliente consultarClientePorIdUsuario(Long idUsuario) {
        try {
            cliente = this.clienteModel.consultarClientePorId(idUsuario);
        } catch (Exception e) {
            System.err.println("ExceÃ§ão ao consultar cliente por ID: "+e.getMessage());
        } 
        return this.cliente;
    }

    public String getDuracaoFMT(Date duracao) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        String duracaoSTR = fmt.format(duracao);
        return duracaoSTR;
    }
    
    public String getDataFMT(Date data) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        String dataSTR = fmt.format(data);
        return dataSTR;
    }

    public Date getDateByString(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String saida = sdf2.format(sdf.parse(data));
        return sdf2.parse(saida);
    }

    public void criarAgendamento(Agendamento agendamento) throws IOException {
        bean.persistirAgendamento(agendamento);
        if (scheduler.isShutdown()) {
            verificarSeTemNotificacao();
        }
    }

    /* A cada 24h, verifica se há agendamentos próximos.
       Se faltar um dia, o usuário deve ser notificado.
     */
    public static void verificarSeTemNotificacao() {
        final Runnable notification = new Runnable() {
            public void run() {
                /* Notifica caso haja agendamentos para o dia atual
                   ou posterior.
                 */
                AgendamentoModel agendamentoModel = new AgendamentoModel();

            }
        };
        final ScheduledFuture<?> notificationHandle
                = scheduler.scheduleAtFixedRate(notification, 0, 1, DAYS);

        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //Cancela caso não haja agendamentos atuais ou futuros.
                notificationHandle.cancel(true);
            }
        }, 0, 1, DAYS);
    }

    public void apagarAgendamento() {
        String agora = getDataFMT(Calendar.getInstance().getTime());
        String dataSelecionada = getDataFMT(this.agendamentoSelecionado.getData());
        if(agora.equals(dataSelecionada)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não pode apagar!", "Agendamento na data atual não podem ser cancelados"));
            PrimeFaces.current().ajax().update("form:msgs");
        } else {
            this.bean.apagarAgendamento(this.agendamentoSelecionado);
            this.agendamentos.remove(this.agendamentoSelecionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamento Removido"));
            PrimeFaces.current().ajax().update("form:msgs", "form:agendamentos", "btnApagarAgendamentos");
        }
    }

    public void apagarAgendamentos() {
        String agora = getDataFMT(Calendar.getInstance().getTime());
        boolean apagar = true;
        for(Agendamento agendamento: this.agendamentosSelecionados){
            //Verifica se há algum agendamento na data atual
            if(agora.equals( getDataFMT(agendamento.getData()) )){
                apagar = false;
            }
        }
        if(apagar){
            this.bean.apagarAgendamentos(this.agendamentosSelecionados);
            this.agendamentos.removeAll(this.agendamentosSelecionados);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamentos Removidos"));
            PrimeFaces.current().ajax().update("form:msgs", "form:agendamentos", "btnApagarAgendamentos");
            this.agendamentosSelecionados = null;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não pode apagar!", "Há algum agendamento para data de hoje, e este não pode ser cancelado, desmarque e tente novamente"));
            PrimeFaces.current().ajax().update("form:msgs");
        }
        
    }

    public boolean selecionouAgendamentos() {
        return this.getAgendamentosSelecionados() != null && !this.agendamentosSelecionados.isEmpty();
    }

    public String getMensagemBotao() {
        if (this.selecionouAgendamentos()) {
            int qtd = this.getAgendamentosSelecionados().size();
            return qtd > 1 ? qtd + " Agendamentos selecionados" : "1 agendamento selecionado";
        }
        return "Excluir";
    }
    
    //Metodo que faz a pesquisa de clientes por nome
    public List<Cliente> pesquisaClientes(String query){
        String q = query.toLowerCase();
        ArrayList<Cliente> clientes = new ArrayList<>();
        //if(q.length() > 2){
            for(Cliente c: clienteModel.todosClientes()){
                if(c.getCPF().contains(q) || c.getNome().toLowerCase().contains(q)){
                    clientes.add(c);
                }
            }
        //}
        return clientes;
    }
    
    
    //Método chamado quando a variavel idCliente sofrer alteração do valor na interface
    public void clienteChange(){
        this.horarioDisponiveis = new ArrayList<>();
        this.agendamentoSelecionado = new Agendamento();
        this.cliente = clienteModel.consultarClientePorId(this.idCliente);
        this.agendamentoSelecionado.setUsuario(this.cliente);
    }
    
    //Método chamado quando a variavel idProcedimento sofrer alteração do valor na interface
    public void procedimentoChange(){
        this.agendamentoSelecionado.setProcedimento(procedimentoModel.consultarProcediemnto(idProcedimento));
        this.agendamentoSelecionado.setProfissional(null);
        this.agendamentoSelecionado.setData(null);
        this.agendamentoSelecionado.setHora(null);
        this.horarioDisponiveis = new ArrayList<>();
        this.idProfissional = null;
        this.idProfissionais = new ArrayList<>();
        //salva na lista de id, os ids dos profissionais do procedimento selecionado
        for(Profissional profissional: this.agendamentoSelecionado.getProcedimento().getProfissionais()){
            this.idProfissionais.add(profissional.getId());
        }
    }
    
    //Método chamado quando a variavel idProfissional sofrer alteração do valor na interface
    public void profissionalChange(HttpSession sessao){
        sessao.setAttribute("idProfissional", this.idProfissional);
        this.agendamentoSelecionado.setData(null);
        this.agendamentoSelecionado.setHora(null);
        this.horarioDisponiveis = new ArrayList<>();
        for(Profissional profissional: this.agendamentoSelecionado.getProcedimento().getProfissionais()){
            if(profissional.getId().equals(this.idProfissional)){
                 this.agendamentoSelecionado.setProfissional(profissional);
                 break;
            }
        }
    }
    //Método chamado quando a data do agendamento sofrer alteração do valor na interface
    public void dataChange(){
        this.agendamentoSelecionado.setHora(null);
        this.dtSelecionada = this.agendamentoSelecionado.getData();
        this.horarioDisponiveis = this.listarHorarios();
        for(String h: this.horarioDisponiveis){
            System.out.print(h+" |");
        }
                
    }

    public void criarAgendamento(HttpSession sessao) throws IOException{
        String[] hora = this.horarioStrSelecionado.split(":");
        this.agendamentoSelecionado.setHora(bean.criarHora(Integer.parseInt(hora[0]), Integer.parseInt(hora[1]), 0));
        this.bean.persistirAgendamento(agendamentoSelecionado);
        String URLEstabelecimento = sessao.getServletContext().getContextPath()+"/agendamentos-estabelecimento.xhtml?dataSelecionada="+getDataFMT(agendamentoSelecionado.getData());
        FacesContext.getCurrentInstance().getExternalContext().redirect(URLEstabelecimento);
        
    }
    /**
     * @return the agendamentoSelecionado
     */
    public Agendamento getAgendamentoSelecionado() {
        return agendamentoSelecionado;
    }

    /**
     * @param agendamentoSelecionado the agendamentoSelecionado to set
     */
    public void setAgendamentoSelecionado(Agendamento agendamentoSelecionado) {
        this.agendamentoSelecionado = agendamentoSelecionado;
    }

    /**
     * @return the agendamentosSelecionados
     */
    public List<Agendamento> getAgendamentosSelecionados() {
        return agendamentosSelecionados;
    }

    /**
     * @param agendamentosSelecionados the agendamentosSelecionados to set
     */
    public void setAgendamentosSelecionados(List<Agendamento> agendamentosSelecionados) {
        this.agendamentosSelecionados = agendamentosSelecionados;
    }

    public Date getDtSelecionada() {
        return dtSelecionada;
    }

    public void setDtSelecionada(Date dataSelecionada) {
        this.dtSelecionada = dataSelecionada;
    }

    public String getHorarioStrSelecionado() {
        return horarioStrSelecionado;
    }

    public void setHorarioStrSelecionado(String horarioStrSelecionado) {
        this.horarioStrSelecionado = horarioStrSelecionado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdProcedimento() {
        return idProcedimento;
    }

    public void setIdProcedimento(Long idProcedimento) {
        this.idProcedimento = idProcedimento;
    }

    public Long getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(Long idProfissional) {
        this.idProfissional = idProfissional;
    }

    public ArrayList<Long> getIdProfissionais() {
        return idProfissionais;
    }

    public void setIdProfissionais(ArrayList<Long> idProfissionais) {
        this.idProfissionais = idProfissionais;
    }

    public ArrayList<String> getHorarioDisponiveis() {
        return horarioDisponiveis;
    }

    public void setHorarioDisponiveis(ArrayList<String> horarioDisponiveis) {
        this.horarioDisponiveis = horarioDisponiveis;
    }
    
    public List<Agendamento> getAgendamentos(String data) {
        
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        return agendamentos;
    }
}

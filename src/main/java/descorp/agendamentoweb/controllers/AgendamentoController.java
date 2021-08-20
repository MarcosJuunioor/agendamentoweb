/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Cliente;
import descorp.agendamentoweb.models.AgendamentoModel;
import descorp.agendamentoweb.models.ClienteModel;
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

/**
 *
 * @author marco
 */
@ManagedBean
@ViewScoped
public class AgendamentoController implements Serializable {

    List<Agendamento> agendamentosDia;

    @EJB
    private final AgendamentoModel bean;
    private final ClienteModel clienteModel;
    private Cliente cliente;
    private final ArrayList<String> horariosEstabelecimento;
    private final ArrayList<Agendamento> horariosDisponiveis;
    private List<Agendamento> agendamentos;
    private Agendamento agendamentoSelecionado;
    private List<Agendamento> agendamentosSelecionados;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private String reagendamentoSelecionado;
    private Date dtSelecionada;
    
    private Long idUsuario; 
    private String data;

    public AgendamentoController() {
        this.bean = new AgendamentoModel ();
        this.clienteModel = new ClienteModel();
        this.horariosEstabelecimento = new ArrayList<String>();
        this.horariosDisponiveis = new ArrayList<Agendamento>();
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

                //Cria a lista de objetos que serÃƒÂ£o exibidos na tela
                for (int a = 0; a < horariosEstabelecimento.size(); a++) {
                    this.horariosDisponiveis.add(agendamentoModel.criarAgendamento(dataSaida, agendamentoModel.criarHora(Integer.parseInt(this.horariosEstabelecimento.get(a).substring(0, 2)), 0, 0), idProcedimento, idProfissional, (Long) session.getAttribute("idUsuario")));
                }

                //Remove da lista de horÃƒÂ¡rios disponÃƒÂ­veis os horÃƒÂ¡rio que jÃƒÂ¡ estÃƒÂ£o ocupados
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
                System.err.println("ParÃƒÂ¢metros nÃƒÂ£o localizados: " + e.getMessage());
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
            System.err.println("ParÃƒÂ¢metros nÃƒÂ£o localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
    }
    
    public ArrayList<String> listarHorarios(){
        ArrayList<String> horarios = new ArrayList<>();

        Long idProcedimento = this.agendamentoSelecionado.getProcedimento().getId();
        Long idProfissional = this.agendamentoSelecionado.getProfissional().getId();

        //Horários nos quais o estabelecimento faz agendamentos     
        horarios.add("08:00");
        horarios.add("10:00");
        horarios.add("12:00");
        horarios.add("14:00");
        horarios.add("16:00");
        horarios.add("18:00");

        try {

            //Recupera os agendamentos para o dia selecionado
            agendamentosDia = this.bean.consultarHorariosIndisponiveis(idProfissional, idProcedimento, this.dtSelecionada);

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
        hora.setHours(Integer.parseInt(this.reagendamentoSelecionado.split(":")[0]));
        hora.setMinutes(Integer.parseInt(this.reagendamentoSelecionado.split(":")[1]));
        this.agendamentoSelecionado.setHora(hora);
        //Atualizar agendamento
        bean.atualizarAgendamento(agendamentoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamento Editado!", "Nova data e hora: "+getDataFMT(this.dtSelecionada)+" "+getDuracaoFMT(hora)));
        
        this.dtSelecionada = null;
        
        //window.location.href = "http://localhost:8080/agendamentoweb/agendamentos/horarios?profissional=" + 1 + "&procedimento=" + 1 + "&dataSelecionada=" + data;
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
            System.err.println("ParÃ¢metros nÃ£o localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
    }

    public Cliente consultarClientePorIdUsuario(Long idUsuario) {
        try {
            cliente = this.clienteModel.consultarClientePorId(idUsuario);
        } catch (Exception e) {
            System.err.println("ExceÃ§Ã£o ao consultar cliente por ID: "+e.getMessage());
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

    /* A cada 24h, verifica se hÃ¡ agendamentos prÃ³ximos.
       Se faltar um dia, o usuÃ¡rio deve ser notificado.
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
                //Cancela caso nÃ£o haja agendamentos atuais ou futuros.
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
            return;
        }
        this.bean.apagarAgendamento(this.agendamentoSelecionado);
        this.agendamentos.remove(this.agendamentoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamento Removido"));
        PrimeFaces.current().ajax().update("form:msgs", "form:agendamentos", "btnApagarAgendamentos");
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

    public String getReagendamentoSelecionado() {
        return reagendamentoSelecionado;
    }

    public void setReagendamentoSelecionado(String reagendamentoSelecionado) {
        this.reagendamentoSelecionado = reagendamentoSelecionado;
    }
 
    public List<Agendamento> getAgendamentos(String data) {
        
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        
        
        
        return agendamentos;
    }
}

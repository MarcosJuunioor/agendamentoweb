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

            //HorÃ¡rios nos quais o estabelecimento faz agendamentos            
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

                //Cria a lista de objetos que serÃ£o exibidos na tela
                for (int a = 0; a < horariosEstabelecimento.size(); a++) {
                    this.horariosDisponiveis.add(agendamentoModel.criarAgendamento(dataSaida, agendamentoModel.criarHora(Integer.parseInt(this.horariosEstabelecimento.get(a).substring(0, 2)), 0, 0), idProcedimento, idProfissional, (Long) session.getAttribute("idUsuario")));
                }

                //Remove da lista de horÃ¡rios disponÃ­veis os horÃ¡rio que jÃ¡ estÃ£o ocupados
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
                System.err.println("ParÃ¢metros nÃ£o localizados: " + e.getMessage());
            } catch (ParseException ex) {
                Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.horariosDisponiveis;
    }

    public List<Agendamento> getAgendamentosUsuario(Long idUsuario, String data) {
        try {
            Date dataObj = this.getDateByString(data);
            this.agendamentos = this.bean.getAgendamentosUsuario(idUsuario, dataObj);
        } catch (NumberFormatException e) {
            System.err.println("ParÃ¢metros nÃ£o localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
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
            System.err.println("Parâmetros não localizados: " + e.getMessage());
        } catch (ParseException ex) {
            Logger.getLogger(AgendamentoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.agendamentos;
    }

    public Cliente consultarClientePorIdUsuario(Long idUsuario) {
        try {
            cliente = this.clienteModel.consultarClientePorId(idUsuario);
        } catch (Exception e) {
            System.err.println("Exceção ao consultar cliente por ID: "+e.getMessage());
        } 
        return this.cliente;
    }

    public String getDuracaoFMT(Date duracao) {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        String duracaoSTR = fmt.format(duracao);
        return duracaoSTR;
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
        this.bean.apagarAgendamento(this.agendamentoSelecionado);
        this.agendamentos.remove(this.agendamentoSelecionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamento Removido"));
        PrimeFaces.current().ajax().update("form:msgs", "form:agendamentos", "btnApagarAgendamentos");
    }

    public void apagarAgendamentos() {
        this.bean.apagarAgendamentos(this.agendamentosSelecionados);
        this.agendamentos.removeAll(this.agendamentosSelecionados);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agendamentos Removidos"));
        PrimeFaces.current().ajax().update("form:msgs", "form:agendamentos", "btnApagarAgendamentos");
        this.agendamentosSelecionados = null;
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

    public List<Agendamento> getAgendamentos(String data) {
        
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        
        
        
        return agendamentos;
    }

}

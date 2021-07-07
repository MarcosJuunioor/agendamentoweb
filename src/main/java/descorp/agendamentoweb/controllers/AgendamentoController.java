/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.controllers;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.models.AgendamentoModel;
import descorp.agendamentoweb.servlets.AgendamentoServlet;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author marco
 */
@ManagedBean
@ViewScoped
public class AgendamentoController implements Serializable {

    @EJB
    private final AgendamentoModel bean;
    private final ArrayList<String> horariosDisponiveis;
    private List<Agendamento> agendamentos;

    public AgendamentoController() {
        this.bean = new AgendamentoModel();
        this.horariosDisponiveis = new ArrayList<String>();
    }

    public List<String> getHorariosIndisponiveis(String dataSelecionada, String idProf, String idProc) {
        if (this.horariosDisponiveis == null || this.horariosDisponiveis.isEmpty()) {
            Long idProcedimento = Long.valueOf(idProc);
            Long idProfissional = Long.valueOf(idProf);

            //Horários nos quais o estabelecimento faz agendamentos
            this.horariosDisponiveis.add("08:00");
            this.horariosDisponiveis.add("10:00");
            this.horariosDisponiveis.add("12:00");
            this.horariosDisponiveis.add("14:00");
            this.horariosDisponiveis.add("16:00");
            this.horariosDisponiveis.add("18:00");

            try {
                String startDateString = dataSelecionada;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String saida = sdf2.format(sdf.parse(startDateString));

                Date dataSaida = sdf2.parse(saida);

                List<Agendamento> agendamentos = this.bean.consultarHorariosIndisponiveis(idProfissional, idProcedimento, dataSaida);

                for (Agendamento a : agendamentos) {
                    String hora = getDuracaoFMT(a.getHora());
                    if (this.horariosDisponiveis.contains(hora)) {
                        this.horariosDisponiveis.remove(hora);
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

    public List<Agendamento> getAgendamentosUsuario(Long idUsuario, String data) {
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
}

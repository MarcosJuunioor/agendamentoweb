/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Agendamento;
import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.entities.Usuario;
import static descorp.agendamentoweb.models.GenericModel.em;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author marco
 */
public class AgendamentoModel extends GenericModel {

    public AgendamentoModel() {
        super();
    }

    public List<Agendamento> consultarDatasIndisponiveis() {
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.Indisponiveis", Agendamento.class);
        List<Agendamento> agendamentos = query.getResultList();
        return agendamentos;
    }

    public List<Date> consultarDatasAgendadasEstabelecimento() {
        TypedQuery<Date> query = em.createNamedQuery("Agendamento.DatasAgendadas", Date.class);
        return query.getResultList();
    }
    
    public List<Agendamento> consultarAgendamentosPorData(Date data){
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorData", Agendamento.class)
                .setParameter("data", data);
        return query.getResultList();
    }

    public List<Agendamento> consultarHorariosIndisponiveis(Long idProfissional, Long idProcedimento, Date data) {
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorProfissional", Agendamento.class)
                .setParameter("idProfissional", idProfissional)
                .setParameter("idProcedimento", idProcedimento)
                .setParameter("dataSelecionada", data);

        List<Agendamento> agendamentos = query.getResultList();

        return agendamentos;

    }

    public List<Date> getDatasAgendamentos(Long idUsuario) {
        TypedQuery<Date> query = em.createNamedQuery("Agendamento.PorIdUsuario", Date.class)
                .setParameter("idUsuario", idUsuario);

        return query.getResultList();

    }

    public List<String> getDatasAgendamentosDoDia(Date data) {
        TypedQuery<String> query = em.createNamedQuery("Agendamento.agendamentosDoDia", String.class)
                .setParameter("diaAgendamento", data);
        //
        List<String> mList = new ArrayList<String>();
        //
        for (String s : query.getResultList()) {
            mList.add(s);
        }
        return mList;
    }

    public List<Agendamento> getAgendamentosUsuario(Long idUsuario, Date data) {
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorIdUsuarioEData", Agendamento.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("data", data);

        return query.getResultList();

    }

    public Agendamento criarAgendamento(Date data, Date horario, Long idProc, Long idProf, Long idUsuario) {

        Agendamento agendamento = new Agendamento();

        try {

            agendamento.setData(criarData(data.getDay(), data.getMonth(), data.getYear()));
            agendamento.setHora(criarHora(horario.getHours(), horario.getMinutes(), horario.getSeconds()));

            Usuario usuario = em.find(Usuario.class, idUsuario);
            agendamento.setUsuario(usuario);

            Procedimento procedimento = em.find(Procedimento.class, idProc);
            agendamento.setProcedimento(procedimento);

            Profissional profissional = em.find(Profissional.class, idProf);
            agendamento.setProfissional(profissional);
            agendamento.setData(data);

        } catch (Exception e) {

            System.out.println("Erro na criação do agendamento: " + e.getMessage());

        }

        return agendamento;

    }

    public void persistirAgendamento(Agendamento agendamento) throws IOException {
        try {
            this.beginTransaction();
            em.persist(agendamento);
            em.flush();
            this.commitTransaction();
        } catch (Exception e) {
            System.out.println("Erro ao persistir o agendamento: " + e.getMessage());
        }
    }

    public void apagarAgendamento(Agendamento agendamento) {
        try {
            this.beginTransaction();
            em.remove(agendamento);
            this.commitTransaction();
        } catch (Exception e) {
            System.out.println("Erro ao apagar o agendamento: " + e.getMessage());
        }

    }

    public void apagarAgendamentos(List<Agendamento> agendamentos) {
        try {
            this.beginTransaction();
            for (Agendamento a : agendamentos) {
                em.remove(a);
            }
            this.commitTransaction();
        } catch (Exception e) {
            System.out.println("Erro ao apagar o agendamento: " + e.getMessage());
        }
    }

    public Date criarData(int dia, int mes, int ano) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.YEAR, ano);
        return calendar.getTime();
    }

    public Date criarHora(int hora, int min, int seg) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, seg);
        return calendar.getTime();
    }

}

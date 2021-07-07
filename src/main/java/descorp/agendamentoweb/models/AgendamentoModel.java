/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Agendamento;
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

    public List<Agendamento> getAgendamentosUsuario(Long idUsuario, Date data) {
        TypedQuery<Agendamento> query = em.createNamedQuery("Agendamento.PorIdUsuarioEData", Agendamento.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("data", data);

        return query.getResultList();

    }
}

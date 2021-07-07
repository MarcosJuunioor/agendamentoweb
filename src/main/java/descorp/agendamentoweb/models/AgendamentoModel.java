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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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

    public Agendamento criarAgendamento(Date data, Date horario, Long idProc, Long idProf) {

        Agendamento agendamento = new Agendamento();

        try {

            agendamento.setData(criarData(data.getDay(), data.getMonth(), data.getYear()));
            agendamento.setHora(criarHora(horario.getHours(), horario.getMinutes(), horario.getSeconds()));

            Usuario usuario = em.find(Usuario.class, 1L);
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

    public void persistirAgendamento(Agendamento a) {
     
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(a);
            em.flush();
            tx.commit();                
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

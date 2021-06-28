/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Agendamento;
import java.util.Calendar;
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
    
    
}

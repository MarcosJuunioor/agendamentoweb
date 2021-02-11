/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 *
 * @author Tayná
 */
@Entity
@Table(name="profissional")
public class Profissional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_profissional", nullable = false, length = 45)
    private String nome;
    @Column(name = "profissao", nullable = false, length = 45)
    private String profissao;
    @Column(name = "especializacao", nullable = false, length = 45)
    private String especializacao;
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicial", nullable = false)
    private Date horaInicial;
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_final", nullable = false)
    private Date horaFinal;
    
    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profissional_has_dias_semana", joinColumns = {
        @JoinColumn(name = "profissional_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "dias_semana_id")})
    private List<DiasSemana> diasSemana; 
    
    @ManyToMany(mappedBy = "profissionais")
    private List<Procedimento> procedimentos;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public Date getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Date horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }

    public List<DiasSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DiasSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profissional)) {
            return false;
        }
        Profissional other = (Profissional) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.Profissional[ id=" + id + " ]";
    }
    
}

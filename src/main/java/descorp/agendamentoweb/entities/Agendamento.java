/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tayná
 */
@Entity
@Table(name = "agendamento")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Agendamento.PorData",
                    query = "SELECT a FROM Agendamento a "
                    + "WHERE a.data = :data"
            ),
            @NamedQuery(
                    name = "Agendamento.Indisponiveis",
                    query = "SELECT a FROM Agendamento a "
                    + "WHERE a.data >= CURRENT_DATE "
                    + "ORDER BY a.data"
            ),
            @NamedQuery(
                    name = "Agendamento.PorProfissional",
                    query = "SELECT a FROM Agendamento a "
                    + "WHERE a.profissional.id = :idProfissional "
                    + "AND a.procedimento.id = :idProcedimento "
                    + "AND a.data = :dataSelecionada "
            ),
            @NamedQuery(
                    name = "Agendamento.PorIdUsuario",
                    query = "SELECT DISTINCT(a.data) FROM Agendamento a "
                    + "WHERE a.usuario.id = :idUsuario "
                    + "AND a.data >= CURRENT_DATE"
            ),
            @NamedQuery(
                    name = "Agendamento.PorIdUsuarioEData",
                    query = "SELECT a FROM Agendamento a "
                    + "WHERE a.usuario.id = :idUsuario "
                    + "AND a.data = :data"
            ),
            @NamedQuery(
                    name = "Agendamento.DatasAgendadas",
                    query = "SELECT DISTINCT(a.data) FROM Agendamento a "
                    + "ORDER BY a.data"
            ),}
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    @Future
    @NotNull
    private Date data;
    @Temporal(TemporalType.TIME)
    @Column(name = "hora", nullable = false, length = 45)
    @NotNull
    private Date hora;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procedimento_id", referencedColumnName = "id")
    private Procedimento procedimento;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profissional_id", referencedColumnName = "id")
    private Profissional profissional;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
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
        if (!(object instanceof Agendamento)) {
            return false;
        }
        Agendamento other = (Agendamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.Agendamento[ id=" + id + " ]";
    }

}

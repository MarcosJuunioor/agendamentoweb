/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Tayná
 */
@Entity
@Table(name="procedimento")
@NamedQueries(
        {
            @NamedQuery(
                    name = Procedimento.PROCEDIMENTO_POR_NOME,
                    query = "SELECT p FROM Procedimento p "
                            + "WHERE p.nome = :nome"
            ),
            @NamedQuery(
                    name = Procedimento.PROCEDIMENTO_TODOS,
                    query = "SELECT p FROM Procedimento p"
            )
        }
)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Procedimento implements Serializable {
    
    public static final String PROCEDIMENTO_POR_NOME = "Procedimento.PorNome";
    public static final String PROCEDIMENTO_TODOS = "Procedimento.TodosProcedimentos";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "natureza_procedimento", nullable = false, length = 45)
    @NotNull
    private String natureza;
    @Column(name = "nome_procedimento", nullable = false, length = 45)
    @NotNull
    private String nome;
    @Temporal(TemporalType.TIME)
    @Column(name = "duracao_procedimento", nullable = false)
    @NotNull
    private Date duracao;
    
    @OneToMany(mappedBy = "procedimento", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "procedimento_profissional", joinColumns = {
        @JoinColumn(name = "procedimento_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "profissional_id")})
    private List<Profissional> profissionais;
    
    @ManyToMany(mappedBy = "procedimentos")
    private List<Cliente> clientes;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDuracao() {
        return duracao;
    }

    public void setDuracao(Date duracao) {
        this.duracao = duracao;
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
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
        if (!(object instanceof Procedimento)) {
            return false;
        }
        Procedimento other = (Procedimento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.Procedimento[ id=" + id + " ]";
    }
    
}

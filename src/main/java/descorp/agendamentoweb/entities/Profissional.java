
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 
 * @author TaynÃ¡
 * @author Evellinne;
 */
@Entity
@Table(name="profissional")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Profissional.PorNome",
                    query = "SELECT p FROM Profissional p "
                            + "WHERE p.nome = :nome"
            ),
            @NamedQuery(
                    name = "Profissional.PorId",
                    query = "SELECT p FROM Profissional p "
                            + "WHERE p.id = :id"
            ),
            @NamedQuery(
                    name = Profissional.PROFISSIONAL_TODOS,
                    query = "SELECT p FROM Profissional p"
            )
        }
)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Profissional implements Serializable {
    
    public static final String PROFISSIONAL_TODOS = "Profissional.TodosProfissionais";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^(\\b[A-Z]\\w*\\s*)+$", message="{descorp.agendamentoweb.entities.Profissional.nome}")
    @Size(max = 60)
    @Column(name = "nome_profissional", nullable = false, length = 60)
    private String nome;
    //Apenas palavras com ou sem acentuação, não sendo permitido caracteres especiais
    @Pattern(regexp = "^([A-Za-záéíóúàâêôãõüçÁÉÍÓÚÀÂÊÔÃÕÜÇ ])+$", message="{descorp.agendamentoweb.entities.Profissional.profissao}")
    @Size(max = 60)
    @Column(name = "profissao", nullable = false, length = 60)
    private String profissao;
    //Apenas palavras com ou sem acentuação, não sendo permitido caracteres especiais
    @Pattern(regexp = "^([A-Za-záéíóúàâêôãõüçÁÉÍÓÚÀÂÊÔÃÕÜÇ ])+$", message="{descorp.agendamentoweb.entities.Profissional.especializacao}")
    @Size(max = 60)
    @Column(name = "especializacao", nullable = false, length = 60)
    private String especializacao;
    @NotNull
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicial", nullable = false)
    private Date horaInicial;
    @NotNull
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_final", nullable = false)
    private Date horaFinal;
    
    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
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

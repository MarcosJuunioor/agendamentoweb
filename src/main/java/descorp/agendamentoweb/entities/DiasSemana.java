
package descorp.agendamentoweb.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author TaynÃ¡
 * @author Evellinne;
 */
@Entity
@Table(name="dias_semana")
@NamedQueries(
        {
            @NamedQuery(
                    name = "DiasSemana.PorNome",
                    query = "SELECT d FROM DiasSemana d "
                            + "WHERE d.nome = :nome"
            )
        }
)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class DiasSemana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Apenas palavras que começam com letra maiúscula, e as demais minúsculas, podendo ser palavras acentuadas ou não, sendo a maior palavra com 7 letras e a menor com 5.
    @Pattern(regexp = "^([A-Za-zçá])+$", message="{descorp.agendamentoweb.entities.DiasSemana.nome}")
    @Size(max = 10)
    @Column(name = "nome_dia", nullable = false, length = 20)
    private String nome;
    
    @ManyToMany(mappedBy = "diasSemana", fetch = FetchType.LAZY)
    private List<Profissional> profissionais;
    
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

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public void setProfissionais(List<Profissional> profissionais) {
        this.profissionais = profissionais;
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
        if (!(object instanceof DiasSemana)) {
            return false;
        }
        DiasSemana other = (DiasSemana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.DiasSemana[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marco
 */
@Entity
@Table(name="sala") 
@NamedQueries(
        {
            @NamedQuery(
                    name = "Sala.PorNumSala",
                    query = "SELECT s FROM Sala s "
                            + "WHERE s.numSala = :numSala"
            )
        }
)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estabelecimento", referencedColumnName = "id")
    private Estabelecimento estabelecimento;
    @NotNull
    @Min(1)
    @Max(99999)
    @Column(name = "num_sala", nullable = false)
    private Integer numSala;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
        this.estabelecimento.setSala(this);
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
        if (!(object instanceof Sala)) {
            return false;
        }
        Sala other = (Sala) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.Sala[ id=" + id + " ]";
    }

    /**
     * @return the numSala
     */
    public Integer getNumSala() {
        return numSala;
    }

    /**
     * @param numSala the numSala to set
     */
    public void setNumSala(Integer numSala) {
        this.numSala = numSala;
    }
    
    
}

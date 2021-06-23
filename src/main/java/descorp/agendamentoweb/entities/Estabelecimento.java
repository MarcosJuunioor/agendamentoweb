/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author marco
 */
@Entity
@Table(name="estabelecimento") 
@DiscriminatorValue(value = "e")
@PrimaryKeyJoinColumn(name="id_usuario", referencedColumnName = "id")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Estabelecimento.PorCnpj",
                    query = "SELECT e FROM Estabelecimento e "
                            + "WHERE e.CNPJ = :CNPJ"
            )
        }
)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public class Estabelecimento extends Usuario implements Serializable {
    
    @OneToOne(mappedBy = "estabelecimento", 
            optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
    private Sala sala;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;
    
    @NotBlank
    @Column(name = "razao_social", nullable = false, length = 50)
    private String razaoSocial;
    @NotNull
    @Pattern(regexp = "[0-9]{2}.[0-9]{3}.[0-9]{3}/[0-9]{4}-[0-9]{2}", message="{descorp.agendamentoweb.entities.Estabelecimento.CNPJ}")
    @Column(name = "cnpj", nullable = false, length = 18)
    private String CNPJ;

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
  
        /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public Endereco criarEndereco() {
        this.setEndereco(new Endereco());
        return getEndereco();
    }


    /**
     * @return the razaoSocial
     */
    public String getRazaoSocial() {
        return razaoSocial;
    }

    /**
     * @param razaoSocial the razaoSocial to set
     */
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    /**
     * @return the CNPJ
     */
    public String getCNPJ() {
        return CNPJ;
    }

    /**
     * @param CNPJ the CNPJ to set
     */
    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    
}

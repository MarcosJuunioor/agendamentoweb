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
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author marco
 */
@Entity
@Table(name = "cliente")
@DiscriminatorValue(value = "c")
@PrimaryKeyJoinColumn(name = "id_usuario", referencedColumnName = "id")
@NamedQueries(
        {
            @NamedQuery(
                    name = "Cliente.PorCpf",
                    query = "SELECT c FROM Cliente c "
                    + "WHERE c.CPF = :CPF"
            ),
            @NamedQuery(
                    name = "Cliente.PorIdUsuario",
                    query = "SELECT c FROM Cliente c "
                    + "WHERE c.id = :idUsuario"
            )
        }
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Cliente extends Usuario implements Serializable {
    //Apenas palavras que começam com letra maiúscula, e as demais minúsculas
    @Pattern(regexp = "^(\\b[A-Z]\\w*\\s*)+$", message="{descorp.agendamentoweb.entities.Cliente.nome}")
    @Size(max = 60)
    @Column(name = "nome", nullable = false, length = 60)
    private String nome;
    @CPF
    @Column(name = "cpf", nullable = false, length = 15)
    private String CPF;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cliente_has_procedimento", joinColumns = {
        @JoinColumn(name = "cliente_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "procedimento_id")})
    private List<Procedimento> procedimentos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the CPF
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * @param CPF the CPF to set
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public List<Procedimento> getProcedimentos() {
        return procedimentos;
    }

    public void setProcedimentos(List<Procedimento> procedimentos) {
        this.procedimentos = procedimentos;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name="cliente") 
@DiscriminatorValue(value = "c")
@PrimaryKeyJoinColumn(name="id_usuario", referencedColumnName = "id")
public class Cliente extends Usuario implements Serializable {

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;
    @Column(name = "cpf", nullable = false, length = 15)
    private String CPF;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cliente_has_procedimento", joinColumns = {
        @JoinColumn(name = "cliente_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "procedimento_id")})
    private List<Procedimento> procedimentos;
    

    public String getNome(){
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

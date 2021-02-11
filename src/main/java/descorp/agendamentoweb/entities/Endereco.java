/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author marco
 */
@Entity
@Table(name="endereco") 
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "Endereco", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estabelecimento> estabelecimentos;
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;
    @Column(name = "rua", nullable = false, length = 45)
    private String rua;
    @Column(name = "numero", nullable = false)
    private Integer numero;
    @Column(name = "bairro", nullable = false, length = 20)
    private String bairro;
    @Column(name = "cidade", nullable = false, length = 30)
    private String cidade;
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Estabelecimento> getEstabelecimentos() {
        return this.estabelecimentos;
    }

    public void adicionarEstabelecimento(Estabelecimento estabelecimento) {
        if (this.estabelecimentos == null) {
            this.estabelecimentos = new ArrayList<>();
        }

        this.estabelecimentos.add(estabelecimento);
        estabelecimento.setEndereco(this);
    }

    public boolean removerEstabelecimento(Estabelecimento estabelecimento) {
        return this.estabelecimentos.remove(estabelecimento);
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
        if (!(object instanceof Endereco)) {
            return false;
        }
        Endereco other = (Endereco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "descorp.agendamento.web.entities.Endereco[ id=" + id + " ]";
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

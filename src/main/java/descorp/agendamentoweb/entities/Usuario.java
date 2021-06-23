package descorp.agendamentoweb.entities;

/**
 *
 * @author marco
 */
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/*
 * @Entity informa que a classe é uma entidade JPA.
 * Por padrão, o nome da tabela é o nome da classe.
 * A entidade deve possuir um construtor padrão vazio.
 */
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "DISC_USUARIO", 
        discriminatorType = DiscriminatorType.STRING, length = 1)
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
public abstract class Usuario implements Serializable {
    /*
     * @Id informa que o atributo representa a chave primária.
     * @GeneratedValue informa como gerar a chave primária.
     * GenerationType.IDENTITY é uma estratégia que pode ser utilizada no Derby.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    /*
     * Por padrão, se o nome do atributo é o nome do campo na tabela, nada
     * precisa ser feito em termos de mapeamento.
     */
    @NotNull
    @Email
    @Column(name = "email", nullable = false, length = 50)
    protected String email;
    @NotNull
    @Column(name = "telefone", nullable = false, length = 20)
    protected String telefone;
    @NotNull
    @Column(name = "senha", nullable = false, length = 20)
    protected String senha;
    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, orphanRemoval = true)
    protected List<Agendamento> agendamentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
    
}

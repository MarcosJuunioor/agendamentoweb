/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests.jpql;
import descorp.agendamentoweb.tests.GenericTest;
import descorp.agendamentoweb.entities.Endereco;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author tayná
 */
public class EnderecoJpqlTest extends GenericTest{
    //Consulta de um endereço pelo CEP
    @Test
    public void enderecoPorCep() {
        TypedQuery<Endereco> query = em.createQuery(
                "SELECT en FROM Endereco en WHERE en.cep LIKE :cep",
                Endereco.class);
        query.setParameter("cep", "24.250-490");
        Endereco endereco = query.getSingleResult();
        assertNotNull(endereco);
    }
    
}

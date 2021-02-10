/*jdbc:derby://localhost:1527/agendamento_web_db [agendamento_web on AGENDAMENTO_WEB]
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import descorp.agendamentoweb.entities.Cliente;

/**
 *
 * @author marco
 */
public class ClienteTeste{
     public static void main(String[] args) {
        Cliente cliente = new Cliente();
        preencherCliente(cliente);
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("agendamento_web");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(cliente);
            et.commit();
            System.out.println(cliente.getId());
            
        } catch (Exception ex) {
            ex.printStackTrace();
            if (et != null)
                et.rollback();
        } finally {
            if (em != null)
                em.close();       
            if (emf != null)
                emf.close();
        }
    }

    private static void preencherCliente(Cliente cliente) {
        cliente.setEmail("jose@gmail.com");
        cliente.setSenha("1234");
        cliente.setTelefone("81988771456");
        cliente.setNome("José");
        cliente.setCPF("12345678910");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author marco
 */
public class GenericModel {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected EntityTransaction et;
    
    public GenericModel(){
        emf = Persistence.createEntityManagerFactory("agendamento_web");
        em = emf.createEntityManager();
    }
    
    public void beginTransaction() {
        if(em == null){
            emf = Persistence.createEntityManagerFactory("agendamento_web");
            em = emf.createEntityManager();
        }
        et = em.getTransaction();
        et.begin();
    }

    public void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            emf.close();
            emf = null;
            em = null;
            et = null;
        }
    }
    
    public void checkEM(){
        if(em == null){
            emf = Persistence.createEntityManagerFactory("agendamento_web");
            em = emf.createEntityManager();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author marco
 */
public class GenericTest {
    protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction et;
    
    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory("agendamento_web");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();      
    }

    protected void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    protected void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}

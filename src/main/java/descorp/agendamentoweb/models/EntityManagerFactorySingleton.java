/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author marco
 */
public enum EntityManagerFactorySingleton {
    INSTANCE;
    public final EntityManagerFactory emf = Persistence.createEntityManagerFactory("agendamento_web");
}

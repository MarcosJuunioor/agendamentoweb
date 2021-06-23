/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.models;

import descorp.agendamentoweb.entities.Profissional;

/**
 *
 * @author marco
 */
public class ProfissionalModel extends GenericModel{
    public ProfissionalModel(){
        super();
    }

    public Profissional consultarProfissional(Long id) {
        return em.find(Profissional.class, id);
    }

}

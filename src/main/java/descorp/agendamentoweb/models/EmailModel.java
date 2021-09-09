/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * AND open the template in the editor.
 */
package descorp.agendamentoweb.models;

import java.io.Serializable;
import java.util.Date;
import java.sql.Time;

/**
 *
 * @author thiagoaraujo
 */
public class EmailModel implements Serializable {

    String email;
    String nome;
    Time hora;
    Date data;
    String nomeProfissional;
    String nomeProcedimento;

    public EmailModel(String email, String nome, Time hora, Date data, String nomeProfissional, String nomeProcedimento) {
        this.email = email;
        this.nome = nome;
        this.hora = hora;
        this.data = data;
        this.nomeProfissional = nomeProfissional;
        this.nomeProcedimento = nomeProcedimento;
    }

    public EmailModel() {
    }
   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public String getNomeProcedimento() {
        return nomeProcedimento;
    }

    public void setNomeProcsdimento(String nomeProcsdimento) {
        this.nomeProcedimento = nomeProcsdimento;
    }

}

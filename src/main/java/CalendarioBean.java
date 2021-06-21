/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author marco
 */
@Named(value = "calendarioBean")
@Dependent
public class CalendarioBean {
    private Date date;
    /**
     * Creates a new instance of CalendarioBean
     */
    public CalendarioBean() {
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import descorp.agendamentoweb.controllers.AgendamentoController;
import descorp.agendamentoweb.utilities.EmailSender;
import it.sauronsoftware.cron4j.Scheduler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author thiagoaraujo
 */
public class EmailServiceServlet extends HttpServlet {

    EmailSender mSender = new EmailSender();

    public void init() throws ServletException {
        enviarNotificacao();
    }

    public void enviarNotificacao() {

        AgendamentoController mController = new AgendamentoController();

        Scheduler jobNotificacoes = new Scheduler();

        Date diaAgendamento = new Date();
        Calendar c = Calendar.getInstance();

        c.setTime(diaAgendamento);
        c.add(Calendar.DATE, 1);

        List<String> mListaEmails = mController.getAgendamentosDoDia(c.getTime());

        jobNotificacoes.schedule("* * * * *", () -> {
            try {
                mSender.enviarEmail("Confirmação de Horário", "Email enviado automaticamente...", mListaEmails);
                System.out.println("Enviando notificação para a lista de e-mails: ");
                for (String s : mListaEmails) {
                    System.out.println(s);
                }
            } catch (Exception e) {
                System.out.println("Erro ao enviar notificação: " + e.getMessage());
            }

        });
        jobNotificacoes.start();
    }
}

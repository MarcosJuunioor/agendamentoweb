/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import descorp.agendamentoweb.models.EmailModel;
import descorp.agendamentoweb.utilities.CriacaoEmailTemp;
import descorp.agendamentoweb.utilities.EmailSender;
import it.sauronsoftware.cron4j.Scheduler;
import java.text.SimpleDateFormat;
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

    CriacaoEmailTemp geraLista = new CriacaoEmailTemp();

    public void init() throws ServletException {
        enviarNotificacao();
    }

    public void enviarNotificacao() {

        Scheduler jobNotificacoes = new Scheduler();

        SimpleDateFormat formatEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatRetorno = new SimpleDateFormat("dd/MM/yyyy");

        jobNotificacoes.schedule("0 6 * * *", () -> {
            try {
                Date diaAgendamento = new Date();
                Calendar c = Calendar.getInstance();

                c.setTime(diaAgendamento);
                c.add(Calendar.DATE, 1);

                List<EmailModel> mList = geraLista.selectAgendamentos(formatEntrada.format(c.getTime()));

                for (int i = 0; i < mList.size(); i++) {
                    System.out.println("Enviando notificação para o e-mail: " + mList.get(i).getEmail());
                    mSender.enviarEmailNotificacao(
                            "Confirmação de Horário - Agendamento Web", "Olá " + mList.get(i).getNome()
                            + " você tem um(a) " + mList.get(i).getNomeProcedimento()
                            + " marcado(a) para às " + mList.get(i).getHora() + " horas "
                            + " do dia " + formatRetorno.format(mList.get(i).getData())
                            + " com o profissional " + mList.get(i).getNomeProfissional() + ".", mList.get(i).getEmail());
                }
            } catch (Exception e) {
                System.out.println("Erro ao enviar notificação: " + e.getMessage());
            }

        });

        jobNotificacoes.start();

    }

}

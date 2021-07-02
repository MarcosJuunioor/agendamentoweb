/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import descorp.agendamentoweb.entities.DiasSemana;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.ProfissionalModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class AgendamentoServlet extends HttpServlet {

    private final ProfissionalModel profissionalModel;

    public AgendamentoServlet() {
        this.profissionalModel = new ProfissionalModel();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // path = agendamentos/calendario (GET)
    protected void getCalendario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProcedimento = Long.valueOf(request.getParameter("procedimento"));
        Long idProfissional = Long.valueOf(request.getParameter("profissional"));;
        request.getSession().setAttribute("idProfissional", idProfissional);
        request.getSession().setAttribute("idProcedimento", idProcedimento);
        //código para gerar calendário e retornar a página
        request.getRequestDispatcher("/calendario.xhtml").forward(request, response);
    }

    // path = agendamentos/horarios (GET)
    protected void getHorarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //código para gerar horários e retornar a página
    }

    // path = agendamentos/datas-indisponiveis (GET)
    protected void getDatasIndisponiveis(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Date> datasIndisponiveis = new ArrayList<Date>();
        String json = new ObjectMapper().writeValueAsString(datasIndisponiveis);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // path = agendamentos/dias-profissional(GET)
    protected void getDiasDaSemanaDoProfissional(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idProfissional = (Long) request.getSession().getAttribute("idProfissional");
        
        Profissional profissional = this.profissionalModel.consultarProfissional(idProfissional);
        ArrayList<String> diasDaSemanaProfissional = new ArrayList<String>() ;
        
        for(DiasSemana dia: profissional.getDiasSemana()){
            diasDaSemanaProfissional.add(dia.getNome());
        }
        String json = new ObjectMapper().writeValueAsString(diasDaSemanaProfissional);
        
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Extrai o nome do método a ser executado da URI de requisição
        String endPoint = request.getRequestURI().split("/")[3];

        if (endPoint.equals("calendario")) {
            getCalendario(request, response);
        } else if (endPoint.equals("horarios")) {
            getHorarios(request, response);
        } else if (endPoint.equals("datas-indisponiveis")) {
            getDatasIndisponiveis(request, response);
        } else if (endPoint.equals("dias-profissional")) {
            getDiasDaSemanaDoProfissional(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Extrai o nome do método a ser executado da URI de requisição
        String endPoint = request.getRequestURI().split("/")[3];
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

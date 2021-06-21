/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class AgendamentoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // path = agendamentos/calendario
    protected void getCalendario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //código para gerar calendário e retornar a página
        request.getRequestDispatcher("/calendario.xhtml").forward(request, response);
    }

    // path = agendamentos/horarios
    protected void getHorarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //código para gerar horários e retornar a página
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

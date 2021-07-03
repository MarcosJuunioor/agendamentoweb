/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import descorp.agendamentoweb.models.ProfissionalModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class ProfissionalServlet extends HttpServlet {
    
    private final ProfissionalModel profissionalModel;
    
    public ProfissionalServlet(){
        this.profissionalModel = new ProfissionalModel();
    }
    
    // path = /profissional (GET)
    private void getProfissional(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idUsuario = 2L;
        request.getSession().setAttribute("idUsuario", idUsuario);
        request.getRequestDispatcher("/profissionais.xhtml").forward(request, response);
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String endPoint;
        try{
           endPoint = request.getRequestURI().split("/")[3];
            switch (endPoint) {
                case "profissional":
                    
                    break;
            } 
        } catch (Exception ex){
            this.getProfissional(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}

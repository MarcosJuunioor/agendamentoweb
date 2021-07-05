/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.agendamentoweb.servlets;

import descorp.agendamentoweb.entities.Procedimento;
import descorp.agendamentoweb.entities.Profissional;
import descorp.agendamentoweb.models.ProcedimentoModel;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marco
 */
public class ProcedimentoServlet extends HttpServlet {

    

    private final ProcedimentoModel procedimentoModel;
    
    public ProcedimentoServlet(){
        this.procedimentoModel = new ProcedimentoModel();
    }
    // path = /procedimentos (GET)
    protected void getProcedimento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long idUsuario = 2L;
        request.getSession().setAttribute("idUsuario", idUsuario);
        request.getRequestDispatcher("/procedimentos.xhtml").forward(request, response);
    }
    
    // path = procedimentos/procedimento (GET)
    protected void MaracarProcedimento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long idUsuario = 2L;
        request.getSession().setAttribute("idUsuario", idUsuario);
        request.getRequestDispatcher("/marcar-procedimento.xhtml").forward(request, response);
    }
    //path = procedimentos/listar-procedimentos
    protected void getListaProcedimentos(HttpServletRequest request, HttpServletResponse response) throws IOException{
        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm:ss");  
        String json = "[";
        
        for(Procedimento proc: this.procedimentoModel.todosProcedimentos()){
            String procedimento = "{'id':"+proc.getId()+",'nome':'"+proc.getNome()+"','natureza':'"+proc.getNatureza()+"','duracao':'"+fmt.format(proc.getDuracao())+"','profissionais':[";
            for(Profissional profissional: proc.getProfissionais()){
                procedimento += "{'id':"+profissional.getId()+", 'nome':'"+profissional.getNome()+"'},";
            }
            procedimento += "]},";
            
            json += procedimento;
        }
        json += "]";
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String endPoint;
        try{
           endPoint = request.getRequestURI().split("/")[3];
            switch (endPoint) {
                case "procedimento":
                    this.MaracarProcedimento(request, response);
                    break;
                case "listar-procedimentos":
                    this.getListaProcedimentos(request, response);
                    break;
            } 
        } catch (Exception ex){
            this.getProcedimento(request, response);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * AND open the template in the editor.
 */
package descorp.agendamentoweb.utilities;

import descorp.agendamentoweb.models.EmailModel;
import descorp.agendamentoweb.models.GenericModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thiagoaraujo
 */
public class CriacaoEmailTemp extends GenericModel{

    private static final String dbURL = "jdbc:derby://localhost:1527/agendamento_web;user=agendamento_web;password=123";
    public List<EmailModel> mListEmail = new ArrayList<>();
    private static Connection conn = null;
    private static Statement stmt = null;

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException except) {

        }
    }

    public List<EmailModel> selectAgendamentos(String data) {
        createConnection();
        try {
            String query = "SELECT u.email, c.NOME, a.HORA, a.data, pf.NOME_PROFISSIONAL, pc.NOME_PROCEDIMENTO "
                    + "    FROM Usuario u, Agendamento a, Cliente c, Profissional pf, Procedimento pc "
                    + "          WHERE u.ID   = a.USUARIO_ID "
                    + "            AND u.ID   = c.ID_USUARIO "
                    + "            AND pc.ID  = a.PROCEDIMENTO_ID "
                    + "            AND pf.ID  = a.PROFISSIONAL_ID "
                    + "            AND a.data = '" + data + "'";
            stmt = conn.createStatement();

            try (
                    ResultSet results = stmt.executeQuery(query)) {
                while (results.next()) {
                    mListEmail.add(new EmailModel(results.getString(1), results.getString(2), results.getTime(3), results.getDate(4), results.getString(5), results.getString(6)));
                }
            }
            stmt.close();
        } catch (SQLException sqlExcept) {
            shutdown();
        }
        //
        shutdown();
        //
        return mListEmail;
    }

    private static void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException sqlExcept) {

        }

    }

}

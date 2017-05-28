package model;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.HashSet;

/**
 * The {@link model.DatabaseModel} class allows to insert data to and select data from game defined tables (PYTANIA, ODPOWIEDZI).
 */
public final class DatabaseModel {
    /**
     * A Connection object representing a connection to a database.
     */
    private Connection connection;

    /**
     * {@link model.DatabaseModel} class constructor.
     */
    DatabaseModel() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connect();
            checkConnection();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A getter for the database connection.
     * @return the actual database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * A method that checks if the connection is still valid. If it's not - it tries to reconnect. If it fails, the application quits.
     */
    public void checkConnection() {
        try {
            if (connection != null && connection.isValid(5))
                return;
            connect();
            if (connection == null) {
                String error = "Nie udało sie połączyć z bazą danych. Spróbuj połączyć się z Internetem i uruchomić aplikację ponownie.";
                new Alert(Alert.AlertType.ERROR, error).showAndWait();
                System.exit(-1);
            }
        }
        catch (SQLException e) {
            String error = "Nie udało sie połączyć z bazą danych. Sprawdź połączenie z Internetem i uruchom aplikację ponownie.";
            new Alert(Alert.AlertType.ERROR, error).showAndWait();
            System.exit(-1);
        }
    }

    /**
     * Selects a question from the database.
     * @param diff a difficulty of a question to be selected.
     * @param usedID a set of ids of questions that have been used and shouldn't get selected.
     * @return a selected question.
     */
    public String selectQuestion(String diff, HashSet<Integer> usedID) {
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT ID_Pytania, Pytanie FROM PYTANIA WHERE Poziom = ? ");
            if (usedID.size() > 0) {
                queryBuilder.append("AND ID_Pytania NOT IN(");
                for (int i = 0; i < usedID.size() - 1; ++i)
                    queryBuilder.append("?, ");
                queryBuilder.append("?) ");
            }
            queryBuilder.append("ORDER BY DBMS_RANDOM.VALUE");
            String query = queryBuilder.toString();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, diff);
            int i = 1;
            for (int x : usedID)
                stmt.setInt(++i, x);
            ResultSet rs = stmt.executeQuery();
            String question;
            if (rs.next())
                question = rs.getString("Pytanie");
            else
                throw new SQLException();
            rs.close();
            stmt.close();
            return question;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Selects the answers to the particular question.
     * @param qid An id of question to match answers.
     * @return an array of answers.
     */
    public String[] selectAnswers(int qid) {
        try {
            String query = "SELECT Odpowiedz FROM Odpowiedzi WHERE ID_Pytania = ? ORDER BY DBMS_RANDOM.VALUE";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, qid);
            ResultSet rs = stmt.executeQuery();
            String[] answers = new String[4];
            int i = 0;
            while (rs.next())
                answers[i++] = rs.getString("Odpowiedz");
            rs.close();
            stmt.close();
            return answers;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Selects the correct answer to the question.
     * @param qid an id of question to match correct answer.
     * @return a correct answer.
     */
    public String selectCorrectAnswer(int qid) {
        try {
            String query = "SELECT Odpowiedz FROM Odpowiedzi WHERE ID_Pytania = ? AND Czy_Prawidlowa = 'T'";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, qid);
            ResultSet rs = stmt.executeQuery();
            String correctAnswer;
            if (rs.next())
                correctAnswer = rs.getString("Odpowiedz");
            else
                throw new SQLException();
            rs.close();
            stmt.close();
            return correctAnswer;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserts a question with answers to a database.
     * @param question a question to be inserted (cannot be blank).
     * @param difficulty a difficulty of a question to be inserted.
     * @param answers an array of answers to be inserted (cannot be blank).
     * @return a Boolean indicating if the insertion was successful.
     */
    public Boolean insertQuestion(String question, String difficulty, String answers[]) {
        try {
            if (question.equals(""))
                return false;
            for (String s : answers)
                if (s.equals(""))
                    return false;
            connection.setAutoCommit(false);
            String query = "INSERT INTO PYTANIA VALUES (pytID.nextval, ?, ?)";
            insertRow(query, question, difficulty);
            query = "INSERT INTO ODPOWIEDZI VALUES (odpID.nextval, ?, ?, pytID.currval)";
            insertRow(query, answers[0], "T");
            for (int i = 1; i < 4; ++i)
                insertRow(query, answers[i], "N");
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Gets the question id.
     * @param question a question to match id.
     * @return an id of the question,
     */
    public int getQuestionID(String question) {
        try {
            String qQuery = "SELECT ID_Pytania FROM Pytania WHERE Pytanie LIKE ?";
            PreparedStatement qStmt = connection.prepareStatement(qQuery);
            qStmt.setString(1, question);
            ResultSet qRs = qStmt.executeQuery();
            int qid;
            if (qRs.next())
                qid = qRs.getInt("ID_Pytania");
            else
                throw new SQLException();
            qRs.close();
            qStmt.close();
            return qid;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Tries to connect to the database.
     * @throws SQLException when connection fails.
     */
    private void connect() throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "llepak", "llepak");
    }

    /**
     * Inserts a row to a table defined in a query with two String parameters. Used in {@link model.DatabaseModel#insertQuestion(String, String, String[])}.
     * @param query a query to be executed.
     * @param param1 first parameter of to be executed query.
     * @param param2 second parameter of to be executed query.
     * @throws SQLException when something with {@link PreparedStatement} goes wrong.
     */
    private void insertRow(String query, String param1, String param2)
            throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, param1);
        statement.setString(2, param2);
        statement.execute();
        statement.close();
    }

}



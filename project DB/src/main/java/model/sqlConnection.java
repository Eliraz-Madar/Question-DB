package model;

import java.sql.*;


public class sqlConnection {

    public static String url = "jdbc:mysql://localhost:3306/my_question";
    public static String userName = "root";
    public static String password = "Dvgl2206";
    public Connection con;

    public sqlConnection(){
        this.con = getConnection();
    }



    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch(Exception e) {
            System.out.println("Error getting connection in sqlConnection!\n" + e);}
        return null;
    }

    public void executeQuery(String query) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = con.createStatement();
            st.execute(query);
        }
        catch (Exception ex)
        {
            System.out.println("Error execute from DB in executeQuery!");
        }
    }

    public void executeUpdate(String query) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = con.createStatement();
            st.executeUpdate(query);
        }
        catch (Exception ex)
        {
            System.out.println("Error execute from DB in executeQuery!");
        }
    }

    public String executeQueryAndGetResult(String query) {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            String result = "";
            while(rs.next()) {
                result += rs.getString(1) + "\n";
            }
            return result;
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }

    public void saveQuestionToDB(Question question){
        if(question == null){
            return;
        }
        try {
            Statement st = con.createStatement();
            if(question instanceof AmericanQuestion){
                AmericanQuestion temp = (AmericanQuestion)question;
                st.executeQuery("INSERT INTO multiple_choice (questionID, answer) values" +
                    "( " + temp.getQuestionID() +"," + temp.getAnsList() + ");");
            }else{
                st.executeQuery("INSERT INTO open_question (questionID,answer) values" +
                    "( " + question.getQuestionID() +", " + question.getAnswer() + " );");
            }
        }catch(Exception e){
        System.out.println("Error saving question to DB");
        }
    }

    public void closeMySql(){
        try {
            this.con.close();
        } catch (SQLException e) {
            System.out.println("Error closing sql streem!");
        }
    }
}

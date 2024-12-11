package model;
import java.sql.SQLException;
import java.util.Vector;
import listeners.ExamEventListener;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.io.PrintWriter;


public class Database implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector <Question> allQ;
    public Vector <Test> allTests;
    private final Vector<ExamEventListener> listeners;
    private final String databaseName = "Name";

    private sqlConnection mySql = new sqlConnection();

    public Database() {
        allQ = new Vector <>();
        allTests = new  Vector <>();
        //this.databaseName = "N/A";
        listeners = new Vector<>();
    }

    public Database(String name) throws IOException, ClassNotFoundException {
        this.listeners = new Vector<>();
        this.allTests = new  Vector <>();
        this.allQ = new Vector <>();
        Connection con = mySql.con;
        try {
            PreparedStatement firstPS = con.prepareStatement("SELECT * FROM questions;");
            ResultSet firstRS = firstPS.executeQuery();
            PreparedStatement multiChoice = con.prepareStatement("SELECT * FROM multiple_choice;");
            ResultSet multiChoiceResult = multiChoice.executeQuery();
            PreparedStatement openChoice = con.prepareStatement("SELECT * FROM open_question;");
            ResultSet openQuestResult = openChoice.executeQuery();
            while(firstRS.next()){
                int lecturerID = firstRS.getInt(3);
                if(firstRS.getInt("typeQuestion") == 1){
                    Vector<Answer> mySet = new Vector<>();
                    if(multiChoiceResult.next()){
                        String answer = multiChoiceResult.getString("answer");
                        String[] answerList = answer.split("\n");
                        int numElements = answerList.length;
                        for (int i = 0 ; i < numElements ; i++){
                            String str = answerList[i].replace((i+1) + ". " ,"");
                            if(answerList[i].contains("true")){
                                String a = str.replace("- true","");
                                mySet.add(new Answer(a,true,lecturerID));
                            }
                            else{
                                String a = str.replace("- false","");
                                mySet.add(new Answer(a,false,lecturerID));
                            }
                        }
                    }
                    this.allQ.add(new AmericanQuestion(firstRS.getString(2),mySet,lecturerID));
                }
                else{
                    if(openQuestResult.next()){
                        String answer = openQuestResult.getString("answer");
                        this.allQ.add(new Question(firstRS.getString(2),answer.replace("- true", ""),lecturerID,0));
                    }
                }
            }
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    public boolean UpdateDatabase(Question tempQ) throws FileNotFoundException, IOException {
        if(HandelAQuestion(tempQ))
            return true;
        return false;
    }

    public boolean HandelAQuestion(Question tempQ){
        int lecturerID = tempQ.lecturer;
        Connection con = mySql.con;
        try {
            String str = "INSERT INTO questions (questionID, question,lecturerID,typeQuestion) VALUES ( '" +
                    tempQ.questionID +"', '" +tempQ.question +"', '"+ lecturerID + "', '" + tempQ.type +"')" +
                    "ON DUPLICATE KEY UPDATE question = ' " +tempQ.question + "' ;" ;
            PreparedStatement questionStat = con.prepareStatement(str);
            questionStat.executeUpdate();
            if(tempQ.type == 1){
                AmericanQuestion tempQuest = (AmericanQuestion)tempQ;
                PreparedStatement multipleChoiceStat = con.prepareStatement("INSERT INTO multiple_choice ( questionID, answer ) VALUES ( '" +
                        tempQ.questionID +"' , '" + tempQuest.ansList.toString() + "' )" +
                        "ON DUPLICATE KEY UPDATE answer = ' "+ tempQuest.ansList.toString() + "';");
                multipleChoiceStat.executeUpdate();
            }else{
                PreparedStatement openQuestionStat = con.prepareStatement("INSERT INTO open_question (questionID, answer) VALUES ('"+
                        tempQ.questionID +"' , '" + tempQ.answer + "' )" +
                        "ON DUPLICATE KEY UPDATE answer = '"+ tempQ.answer + "';");
                openQuestionStat.executeUpdate();
            }
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("problem to update whit question 1");
        }
        return true;
    }

    public void closeMySqlForDatabase(){
        mySql.closeMySql();
    }

    public boolean UpdateDatabase() throws FileNotFoundException, IOException {        
        for (Question tempQ : this.allQ){
            if(HandelAQuestion(tempQ))
                return true;
        }
        return true;
    }

    public Question getQuestionByID(int ID){
        for(Question quest : allQ){
            if(quest.questionID == ID)
                return quest;
        }
        return null;
    }

    public void deleteQuestByIndex(int index) {
        Question q = this.allQ.get(index);
        handleDeleteFromDB(q);
        this.allQ.remove(index);
    }

    private void handleDeleteFromDB(Question q){
        PreparedStatement statement = null;
        try {
            Connection con = mySql.con;
            int ID = q.questionID;
            if (q instanceof AmericanQuestion) {
                statement = con.prepareStatement("DELETE FROM multiple_choice WHERE questionID = '" + ID + "';");
            } else {
                statement = con.prepareStatement("DELETE FROM open_question WHERE questionID = '" + ID + "';");
            }
            statement.executeUpdate();
            PreparedStatement second = con.prepareStatement("DELETE FROM questions WHERE questionID = '" + ID + "';");
            second.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Deleting from DB!");
            System.out.println(e);
        }
    }

    public void handleDeleteAns(Question q){
        PreparedStatement statement = null;
        try {
            Connection con = mySql.con;
            if (q instanceof AmericanQuestion) {
                AmericanQuestion aQuestion = (AmericanQuestion)q;
                statement = con.prepareStatement("UPDATE multiple_choice SET answer = '" + aQuestion.getAnsList().toString() + "' WHERE questionID = '" + q.questionID + "';");
            } else {
                statement = con.prepareStatement("UPDATE open_question SET answer = null WHERE questionID = '" + q.questionID + "';");
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error Deleting from DB!");
            System.out.println(e);
        }
    }
    public void deleteAmericanAns(int id, int ansId) {
        AmericanQuestion q = (AmericanQuestion) allQ.get(id);
        handleDeleteFromDB(q);
        q.getAnsList().remove(ansId);
    }

    public String getName() {
        return databaseName;
    }

    public Test getTestByID(int id) {
        return allTests.get(id);
    }

    public Question getQuestByIndex(int index) {
        return this.allQ.get(index);
    }

    public boolean saveTestSol(Test test) throws FileNotFoundException {
        LocalDate date = LocalDate.now();
        PrintWriter pw = new PrintWriter("solution_"+date+".txt");
        pw.println(test);
        pw.close();
        return true;
    }

    public boolean saveTestNoSol(Test test) throws FileNotFoundException {
        LocalDate date = LocalDate.now();
        PrintWriter pw = new PrintWriter("exam_"+date+".txt");
        pw.println(test.toStringNoAns());
        pw.close();
        return true;
    }

    public boolean saveTest(Test test) throws IOException {
        this.allTests.add(test);
        return saveTestSol(test);
    }

/*	private void firePrintDatabaseToModelEvent() {
		for (ExamEventListener l : listeners) {
			l.printDatabaseToModelEvent(this);
		}
	}

	private void fireAddNewQuestToModelEvent() {
		for (ExamEventListener l : listeners) {
			l.addNewQuestToModelEvent(this);
		}
	}

	private void fireEditAnswerToModelEvent() {
		for (ExamEventListener l : listeners) {
			l.editAnswerToModelEvent(this);
		}
	}

	private void fireEditQuestToModelEvent() {
		for (ExamEventListener l : listeners) {
			l.editQuestToModelEvent(this);
		}
	}*/

    public void sortByAnsLength() {
        Question temp;
        for (int i = this.allQ.size(); i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (this.allQ.get(j).answerLength() > this.allQ.get(j + 1).answerLength()) {
                    temp = this.allQ.get(j);
                    this.allQ.set(j, this.allQ.get(j + 1));
                    this.allQ.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Database))
            return false;
        if (!(super.equals(other)))
            return false;
        Database ex = (Database) other;
        return ex.allQ == ex.allQ;
    }

    public String toStringNoAns() {
        StringBuffer str = new StringBuffer("The Database: \n\n");
        for (int i = 0; i < allQ.size(); i++) {
            str.append((i+1)+") "+ allQ.get(i).toStringNoAns() + "\n");

        }
        return str.toString();
    }

    public String showTests() {
        StringBuffer str = new StringBuffer("All the Tests: \n\n");
        for (int i = 0; i < allTests.size(); i++)
            str.append("Test Number : "+(i+1) +" ---- Name : "+ allTests.get(i) + " \n");
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("The Database: \n\n");
        for (int i = 0; i < allQ.size(); i++) {
            str.append((i + 1) + ") " + allQ.get(i) + "\n");
        }
        return str.toString();
    }

    public void registerListener(ExamEventListener listener) {
        listeners.add(listener);
    }

    public void saveAllTestToData() {
        Connection con = mySql.con;

        for (Test test : allTests) {
            try {
                String str = "INSERT INTO exam (dateCreated, title) VALUES ( '" +
                        test.getDate().toString() + "', '" + test.getName() + "');";
                PreparedStatement questionStat = con.prepareStatement(str);
                questionStat.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("problem to saveAllTestToData");
            }
        }
    }
}

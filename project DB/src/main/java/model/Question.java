package model;
import java.io.Serializable;


public class Question implements Comparable<Question>,Serializable,Cloneable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int questionID;
    protected static int id = 1;
    protected String question;
    public Answer answer;
    int lecturer;

    public int type;

    public Question(String question, String answer, int lecturer,int type) {
        this.question = question;
        this.questionID = id++;
        this.answer = new Answer(answer,lecturer);
        this.lecturer = lecturer;
        this.type = type;
    }

    public Question(int questionDataBaseID , String question , int lecturer,int type){
        this.questionID = questionDataBaseID;
        id = id + 1;
        this.question = question;
        this.lecturer = lecturer;
        this.type = type;
    }

    public final String getQuestion() {
        return question;
    }

    public void editAnswer(Answer newAns, int index) {
        this.answer=newAns;
    }

    public final boolean setQuestion(String question) {
        this.question = question;
        return true;
    }

    public final int getQuestionID() {
        return questionID;
    }

    public int answerLength() {
        return this.answer.answer.length();
    }

    public final void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public Answer getAnswer() {
        return answer;
    }

    public int getNumOfAnswers() {
        return 1;
    }

    public void setAnswer(String answer, int lecturerID) {
        Answer ans = new Answer(answer,lecturerID);
        this.answer = ans;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Question))
            return false;

        Question q = (Question) other;
        return (getQuestion().equals(q.getQuestion()));
    }

    @Override
    public int compareTo(Question q) {
        if (getQuestion().equals(q.getQuestion()))
            return 1;
        else
            return -1;
    }

    public String getQuestionString(){
        return question;
    }
    @Override
    public String toString() {
        return question + "\nAnswer: " + answer + "\n";
    }

    public String toStringNoAns() {
        return this.getQuestion() + "\n";
    }


}

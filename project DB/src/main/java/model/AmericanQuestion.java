package model;
import java.io.Serializable;
import java.util.Vector;

public class AmericanQuestion extends Question implements Serializable,Cloneable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<Answer> ansList = new Vector<Answer>();

    public AmericanQuestion(String question, Vector<Answer> mySet, int lecturerID) {
        super(question, null , lecturerID,1) ;
        this.ansList = mySet;
    }

    public Vector<Answer> getAnsList() {
        return ansList;
    }

    public void setAnsList(Vector<Answer> ansList) {
        this.ansList = ansList;
    }

    @Override
    public void editAnswer(Answer newAns, int index) {
        ansList.get(index).setAnswer(newAns);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof AmericanQuestion))
            return false;

        AmericanQuestion AQ = (AmericanQuestion) other;
        return (getQuestion().equals(AQ.getQuestion()));
    }

    public Answer getAnswerByIndex(int index) {
        return ansList.get(index);
    }

    @Override
    public int getNumOfAnswers() {
        return ansList.size();
    }

    @Override
    public int answerLength() {
        int counter = 0;
        for (int i = 0; i < ansList.size(); i++) {
            counter += ansList.get(i).answer.length();
        }
        return counter;
    }

    public String toStringNoAns() {
        StringBuffer str = new StringBuffer(super.getQuestion() +"\n");		//changed to super.getQuestion
        for (int i = 0; i < ansList.size(); i++) {
            str.append(" " + (i + 1) + ". " + ansList.get(i).toStringNoAns() + "\n");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer(super.getQuestion() +"\n");		//changed to super.getQuestion
        for (int i = 0; i < ansList.size(); i++) {
            str.append(" " + (i + 1) + ". " + ansList.get(i) + "\n");
        }
        return str.toString();
    }

}

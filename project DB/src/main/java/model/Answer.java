package model;
import java.io.Serializable;

public class Answer implements Comparable<Answer>,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String answer;
    public boolean trueORfalse;

    private final int lecturerID;

    // answer for open question
    public Answer(String answer, int id) {
        this.answer = answer;
        this.trueORfalse = true;
        this.lecturerID = id;
    }

    // answer for American question
    public Answer(String answer, boolean trueORFalse, int id) {
        this.answer = answer;
        this.trueORfalse = trueORFalse;
        this.lecturerID = id;
    }

    public String getAnswer() {
        return answer;
    }

    /*public boolean setAnswer(String answer) {
        this.answer = answer;
        return true;
    }*/

    public void setAnswer(Answer newAns) {
        this.answer = newAns.answer;
        this.trueORfalse = newAns.trueORfalse;
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Answer a))
            return false;

        if (!(super.equals(other)))
            return false;

        return this.answer.equals(a.answer);
    }

    @Override
    public int compareTo(Answer a) {
        return this.answer.compareTo(a.getAnswer());
    }

    @Override
    public String toString() {
        return answer + " - " + trueORfalse;
    }

    public String toStringNoAns() {
        return answer;
    }

    public int getLecturerID() {
        return lecturerID;
    }
}

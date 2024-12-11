package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

public class Test implements Serializable, Cloneable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector <Question> allQ;
    private String testName;

    private LocalDate date ;
    public Test() {
        allQ = new Vector<Question>();
        this.testName = "The Exam";
        this.date = LocalDate.now();
    }

    public Test (String testName) {
        allQ = new Vector<Question>();
        this.testName = testName;
        this.date = LocalDate.now();
    }

    public void setName(String name) {
        this.testName = name;
    }


    public LocalDate getDate(){
        return this.date;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Test clone() throws CloneNotSupportedException {
        Test temp = (Test)super.clone();
        temp.allQ = (Vector<Question>) this.getAllQst().clone();
        temp.testName = this.getName() + "_clone";
        return temp;
    }

    public void addNewQuest(Question q) {
        allQ.add(q);
    }

    public String getName() {
        return testName;
    }

    public Vector <Question> getAllQst(){
        return this.allQ;
    }

    public String toStringNoAns() {
        StringBuffer str = new StringBuffer(this.testName + "\n\n");
        for (int i = 0; i < allQ.size(); i++) {
            str.append((i+1)+") "+ allQ.get(i).toStringNoAns() + "\n");

        }
        return str.toString();
    }

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
    public String toString() {
        StringBuffer str = new StringBuffer(this.getName()+"\n\n");
        for (int i = 0; i < allQ.size(); i++) {
            str.append((i+1)+") "+ allQ.get(i) + "\n");
        }
        return str.toString();

    }
}

package listeners;
import model.Database;

public interface ExamEventListener {
    void printMenu();
    void printDatabaseToModelEvent(Database initExam);
    void addNewQuestToModelEvent(Database initExam);
    void editQuestToModelEvent(Database initExam);
    void editAnswerToModelEvent(Database initExam);
    void deleteAnswerToModelEvent(Database initExam);
    void createManualExamToModelEvent(Database initExam);
    void createAutoExamToModelEvent(Database initExam);
    void createExamCloneToModelEvent(Database initExam);
    void printTestsInMemToModelEvent(Database initExam);
    void exitAndSaveToModelEvent(Database initExam);
    void addNewQuestToExistTestToModelEvent(Database initExam);
}

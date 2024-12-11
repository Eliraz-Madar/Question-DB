package view;

import listeners.Exam_UI_EventListener;
import model.Database;

public interface AbstractExamView {
    void registerListener(Exam_UI_EventListener listener);
    //	void printMenu();
    void printDatabaseToUI(Database initExam);
    void addNewQuestToUI(Database initExam);
    void editQuestToUI(Database initExam);
    void editAnswerToUI(Database initExam);
    void deleteAnswerToUI(Database initExam);
    void createManualExamToUI(Database initExam);
    void createAutoExamToUI(Database initExam);
    void createExamCloneToUI(Database initExam);
    void printTestsInMemToUI(Database initExam);
    void exitAndSaveToUI(Database initExam);
    void addNewQuestToExistTestToUI(Database initExam);
}
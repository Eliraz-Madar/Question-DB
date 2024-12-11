package control;

import listeners.ExamEventListener;
import listeners.Exam_UI_EventListener;
import model.Database;
import model.Helper;
import view.AbstractExamView;

public class ExamController extends Helper implements ExamEventListener, Exam_UI_EventListener {
    private Database databaseModel;
    private AbstractExamView examView;

	public ExamController(Database model, AbstractExamView view) {
        databaseModel = model;
        examView = view;
        databaseModel.registerListener(this);
        examView.registerListener(this);
    }

    @Override
    public void printDatabaseToModelEvent(Database initExam) {
        examView.printDatabaseToUI(initExam);
    }

    @Override
    public void printDatabaseToUI(Database initExam) {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void printMenu() {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void addNewQuestToModelEvent(Database initExam) {
        examView.addNewQuestToUI(initExam);

    }

    @Override
    public void addNewQuestToUI(Database initExam) {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void editQuestToModelEvent(Database initExam) {
        examView.editQuestToUI(initExam);
    }

    @Override
    public void editQuestToUI(Database initExam) {
        examView.editAnswerToUI(initExam);

    }
//	****************************************************************************************

    @Override
    public void deleteAnswerToModelEvent(Database initExam) {
        examView.deleteAnswerToUI(initExam);
    }

    @Override
    public void deleteAnswerToUI(Database initExam) {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void createManualExamToModelEvent(Database initExam) {
        examView.createManualExamToUI(initExam);

    }

    @Override
    public void createManualExamToUI(Database initExam) {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void createAutoExamToModelEvent(Database initExam) {
        examView.createAutoExamToUI(initExam);

    }

    @Override
    public void createAutoExamToUI(Database initExam) {
        // TODO Auto-generated method stub

    }

    //	****************************************************************************************
    @Override
    public void createExamCloneToModelEvent(Database initExam) {
        examView.createExamCloneToUI(initExam);

    }

    @Override
    public void createExamCloneToUI(Database initExam) {
        // TODO Auto-generated method stub

    }

    //	****************************************************************************************
    @Override
    public void printTestsInMemToModelEvent(Database initExam) {
        examView.printTestsInMemToUI(initExam);

    }

    @Override
    public void printTestsInMemToUI(Database initExam) {
        // TODO Auto-generated method stub

    }

    //	****************************************************************************************
    @Override
    public void exitAndSaveToModelEvent(Database initExam) {
        examView.exitAndSaveToUI(initExam);
        onExit(initExam);
    }

    @Override
    public void exitAndSaveToUI(Database initExam) {
        // TODO Auto-generated method stub

    }

    public void onExit(Database initExam) {
        // save the exam to the database.
        //String questions = sql.executeQueryAndGetResult("select question from Department where Name = '" + + "'");
        //sql.executeQuery("update matches set Total_championship_wins = Total_championship_wins + 1 where Player_id = '"+ playerId+"'");
        //theView.showInfoDialog("Winner", "The competition winner is: " + player.getName());

    }
//	****************************************************************************************

    @Override
    public void addNewQuestToExistTestToModelEvent(Database initExam) {
        examView.addNewQuestToExistTestToUI(initExam);

    }

    @Override
    public void addNewQuestToExistTestToUI(Database initExam) {
        // TODO Auto-generated method stub

    }
//	****************************************************************************************

    @Override
    public void editAnswerToModelEvent(Database initExam) {
        examView.editAnswerToUI(initExam);

    }

    @Override
    public void editAnswerToUI(Database initExam) {
        // TODO Auto-generated method stub

    }

}

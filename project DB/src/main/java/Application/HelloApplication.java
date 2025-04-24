package Application;
import control.ExamController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Database;
import view.ViewMenu;

public class HelloApplication extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception {    	 
        Database theModel = new Database();// = new Database("initExam");      
        new ExamController(theModel, (new ViewMenu(primaryStage)));
        System.out.println(theModel);
    }

    public static void main(String[] args) {
	System.out.println("Now runing");
        launch(args);
    }
}

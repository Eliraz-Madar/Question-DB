package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import listeners.Exam_UI_EventListener;
import model.AmericanQuestion;
import model.Answer;
import model.Database;
import model.Question;
import model.Test;

public class ViewMenu implements AbstractExamView {
	private int ansCounter = 0;

	private Database initExam;
	private final Vector<listeners.Exam_UI_EventListener> allListeners = new Vector<>();
	// private final ComboBox<String> cmbDatabase = new ComboBox<>();

	public ViewMenu(Stage primaryStage) {

		primaryStage.setTitle("Exams Database");

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(5);

		VBox btnBox = new VBox(15);
		btnBox.setBackground(new Background(new BackgroundFill(Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
		btnBox.setAlignment(Pos.CENTER_LEFT);
		Button btPrintDatabase = new Button("Print all questions in Database");
		Button btAddNewQuest = new Button("Add New Quest");
		Button btEditQuest = new Button("Edit Quest");
		Button btEditAnswer = new Button("Edit Answer");
		Button btDeleteAnswer = new Button("Delete Answer");
		Button btCreateManualExam = new Button("Create Manual Exam");
		Button btCreateAutoExam = new Button("Create Auto Exam");
		Button btCreateExamClone = new Button("Create Exam Clone");
		Button btPrintTestsInMem = new Button("Print Tests In Menu");
		Button btExitAndSave = new Button("Exit And Save");
		Button btDeleteQuestFromDB = new Button("Delete Quest From DB");
		Button btAddNewQuestToExistTest = new Button("Add New Quest To Exist Test");
		btnBox.getChildren().addAll(btPrintDatabase, btAddNewQuest, btEditQuest, btDeleteQuestFromDB, btEditAnswer,
				btDeleteAnswer, btCreateManualExam, btCreateAutoExam, btCreateExamClone, btPrintTestsInMem,
				btAddNewQuestToExistTest, btExitAndSave);
		StackPane spRoot = new StackPane();
		spRoot.setPadding(new Insets(100));
		spRoot.setAlignment(Pos.TOP_LEFT);
		spRoot.setBackground(new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		spRoot.setMinWidth(550);
		spRoot.setMinHeight(600);

		gpRoot.add(btnBox, 0, 0, 1, 10);
		gpRoot.add(spRoot, 1, 0, 3, 10);

		GridPane gpPrintDatabase = new GridPane();
		GridPane gpAddNewQuest = new GridPane();
		GridPane gpEditQuest = new GridPane();
		GridPane gpDeleteQuestFromDB = new GridPane();
		GridPane gpEditAnswer = new GridPane();
		GridPane gpDeleteAnswer = new GridPane();
		GridPane gpCreateManualExam = new GridPane();
		GridPane gpCreateAutoExam = new GridPane();
		GridPane gpCreateExamClone = new GridPane();
		GridPane gpPrintTestsInMem = new GridPane();
		GridPane gpExitAndSave = new GridPane();
		GridPane gpAddNewQuestToExistTest = new GridPane();
		GridPane[] gpArr = { gpPrintDatabase, gpAddNewQuest, gpEditQuest, gpDeleteQuestFromDB, gpEditAnswer,
				gpDeleteAnswer, gpCreateManualExam, gpCreateAutoExam, gpCreateExamClone, gpPrintTestsInMem,
				gpExitAndSave, gpAddNewQuestToExistTest };

		spRoot.getChildren().addAll(gpArr);
		primaryStage.setScene(new Scene(gpRoot, 750, 630));
		primaryStage.show();

		try {
			initExam = new Database("initExam");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		// ******************************************PrintDatabase********************************************************
		btPrintDatabase.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.printDatabaseToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpPrintDatabase.setVisible(true);
			gpPrintDatabase.getChildren().clear();

			Text text = new Text();
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

			gpPrintDatabase.add(text, 0, 0);

			text.setText(initExam.toString());
			ScrollPane s = new ScrollPane();
			s.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
			s.setPannable(true);
			s.fitToHeightProperty();
			s.fitToWidthProperty();
			s.setContent(text);
			gpPrintDatabase.getChildren().add(s);
		});
		// ***************************************************AddNewQuest*******************************************
		btAddNewQuest.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.addNewQuestToUI(initExam);
			}

			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpAddNewQuest.setVisible(true);

			Button btSubmitAmQ = new Button("Submit");
			Button btSubmitOpenQ = new Button("Submit");

			Label lblAmericanQ = new Label("American Question");
			Label lblOpenQ = new Label("Open Question");
			Label lblAddNewQuestion = new Label("Please enter your new question:");
			Label lblEnterLecturerId = new Label("Please enter your lecturer ID:");
			Label lblNumOfAns = new Label();

			lblOpenQ.setFont(new Font("verdana", 15));
			lblAmericanQ.setFont(new Font("verdana", 15));
			lblAddNewQuestion.setFont(new Font("verdana", 15));
			lblNumOfAns.setFont(new Font("verdana", 10));

			TextField tfNewQuest = new TextField();
			TextField tfNewOpenAns = new TextField();
			Vector<TextField> tfNewAmericanANS = new Vector<>();
			TextField tfLecturerID = new TextField();

			RadioButton rbOpenQ = new RadioButton("Open Question");
			RadioButton rbAmericanQ = new RadioButton("American Question");

			rbOpenQ.setFont(new Font("verdana", 15));
			rbAmericanQ.setFont(new Font("verdana", 15));
			ToggleGroup tgOpenOrAm = new ToggleGroup();

			rbOpenQ.setToggleGroup(tgOpenOrAm);
			rbAmericanQ.setToggleGroup(tgOpenOrAm);

			ObservableList<Integer> ansNumOptionsObs = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			ComboBox<Integer> cmbNumOfOptions = new ComboBox<>(ansNumOptionsObs);

			ObservableList<Boolean> oblTrueOrF = FXCollections.observableArrayList(true, false);
			Vector<ComboBox<Boolean>> cmbTrueOrFalse = new Vector<>();

			for (int i = 0; i < 10; i++) {
				tfNewAmericanANS.add(new TextField());
				cmbTrueOrFalse.add(new ComboBox<>(oblTrueOrF));
			}

			StackPane spAddNewOpenQ = new StackPane();
			StackPane spAddNewAmQ = new StackPane();
			spAddNewOpenQ.setVisible(false);
			spAddNewAmQ.setVisible(false);

			GridPane gpAddnewAmQ = new GridPane();
			GridPane gpAddnewOpen = new GridPane();

			gpAddNewQuest.add(lblAddNewQuestion, 0, 0);
			gpAddNewQuest.add(tfNewQuest, 0, 1, 3, 1);
			gpAddNewQuest.add(rbOpenQ, 0, 2);
			gpAddNewQuest.add(rbAmericanQ, 0, 3);

			gpAddNewQuest.add(spAddNewAmQ, 0, 6);
			gpAddNewQuest.add(spAddNewOpenQ, 0, 6);
			gpAddNewQuest.add(lblEnterLecturerId, 0, 7);
			gpAddNewQuest.add(tfLecturerID, 0, 8, 3, 1);

			gpAddnewOpen.add(lblOpenQ, 0, 0);
			gpAddnewOpen.add(tfNewOpenAns, 0, 1);
			gpAddnewOpen.add(btSubmitOpenQ, 2, 1);

			spAddNewOpenQ.getChildren().add(gpAddnewOpen);

			spAddNewAmQ.getChildren().add(gpAddnewAmQ);

			gpAddnewAmQ.add(lblAmericanQ, 0, 0);
			gpAddnewAmQ.add(cmbNumOfOptions, 0, 1);
			gpAddnewAmQ.add(btSubmitAmQ, 3, 3);

			tgOpenOrAm.selectedToggleProperty().addListener((ob, o, n) -> {

				if (rbOpenQ.isSelected()) {
					spAddNewOpenQ.setVisible(true);
					spAddNewAmQ.setVisible(false);
					EventHandler<ActionEvent> rbOpenEvent = e -> {
						EventHandler<ActionEvent> btSubmitOpenQEvent = e14 -> {
							EventHandler<ActionEvent> tfNewQuestEvent = e13 -> tfNewQuest.setText(tfNewQuest.getText());
							EventHandler<ActionEvent> tfNewOpenAnsEvent = e12 -> tfNewOpenAns
									.setText(tfNewOpenAns.getText());

							tfLecturerID.setOnAction(r -> {
								tfLecturerID.setText(tfLecturerID.getText());
							});
							int lecturerID = Integer.parseInt(tfLecturerID.getText());

							tfNewQuest.setOnAction(tfNewQuestEvent);
							tfNewOpenAns.setOnAction(tfNewOpenAnsEvent);

							String openQ = tfNewQuest.getText();
							String openAns = tfNewOpenAns.getText();

							Question newOpenQ = new Question(openQ, openAns, lecturerID, 2);

							initExam.allQ.add(newOpenQ);
							try {
								initExam.UpdateDatabase(newOpenQ);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							tfLecturerID.clear();
							tfNewQuest.clear();
							tfNewOpenAns.clear();
						};
						btSubmitOpenQ.setOnAction(btSubmitOpenQEvent);
					};
					rbOpenQ.setOnAction(rbOpenEvent);
				} else if (rbAmericanQ.isSelected()) {
					spAddNewAmQ.setVisible(true);
					spAddNewOpenQ.setVisible(false);
					EventHandler<ActionEvent> rbAmerEvent = e -> {
						EventHandler<ActionEvent> cmbNumOfOptionsEvent = e16 -> {
							int ansCount;
							if (ansCounter != 0) {
								gpAddnewAmQ.getChildren().clear();
								gpAddnewAmQ.add(lblAmericanQ, 0, 0);
								gpAddnewAmQ.add(cmbNumOfOptions, 0, 1);
								gpAddnewAmQ.add(btSubmitAmQ, 3, 3);
							}
							ansCount = cmbNumOfOptions.getValue();
							for (int i = 0; i <= ansCount - 1; i++) {
								gpAddnewAmQ.add(tfNewAmericanANS.get(i), 0, i + 3);
								gpAddnewAmQ.add(cmbTrueOrFalse.get(i), 1, i + 3);
								ansCounter++;
							}

							EventHandler<ActionEvent> btSubmitAmQEvent = e15 -> {
								System.out.println("American Question Added Successfully");
								String AmericanQ = tfNewQuest.getText();
								Vector<Answer> ansArr = new Vector<Answer>();
								tfLecturerID.setOnAction(r -> {
									tfLecturerID.setText(tfLecturerID.getText());
								});
								int lecturerID = Integer.parseInt(tfLecturerID.getText());
								for (int i = 0; i < cmbNumOfOptions.getValue(); i++) {
									Answer tempAns = new Answer(tfNewAmericanANS.get(i).getText(),
											cmbTrueOrFalse.get(i).getValue(), lecturerID);
									ansArr.add(tempAns);
								}
								AmericanQuestion newAmericanQ = new AmericanQuestion(AmericanQ, ansArr, lecturerID);
								initExam.allQ.add(newAmericanQ);

								try {
									initExam.UpdateDatabase();
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								tfNewQuest.clear();
								for (int i = 0; i < tfNewAmericanANS.size(); i++) {
									tfNewAmericanANS.get(i).clear();
									cmbTrueOrFalse.get(i).cancelEdit();
									tfLecturerID.clear();
								}
							};
							btSubmitAmQ.setOnAction(btSubmitAmQEvent);
						};
						cmbNumOfOptions.setOnAction(cmbNumOfOptionsEvent);
					};
					rbAmericanQ.setOnAction(rbAmerEvent);
				}
			});
		});
		// ***************************************************EditQuest****************************************
		btEditQuest.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.editQuestToUI(initExam);
			}
			// ********need to add the action**********
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpEditQuest.setVisible(true);

			Label lbEditQuestion = new Label("Please select the question you would like to edit");
			lbEditQuestion.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

			TextField tfSelectedQuestion = new TextField();
			tfSelectedQuestion.setMaxWidth(450);

			Button btSubmit = new Button("Submit");

			ArrayList<String> arrQuestEdit = new ArrayList<>();
			for (int i = 0; i < initExam.allQ.size(); i++) {
				arrQuestEdit.add(initExam.allQ.get(i).getQuestion());
			}
			ObservableList<String> oblQuestList = FXCollections.observableArrayList(arrQuestEdit);
			ComboBox<String> cmbQuestions = new ComboBox<>(oblQuestList);
			cmbQuestions.setMaxWidth(450);

			EventHandler<ActionEvent> cmbQuestionsEvent = e -> {
				int index = cmbQuestions.getSelectionModel().getSelectedIndex();

				EventHandler<ActionEvent> btSubmitEvent = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						EventHandler<ActionEvent> tfSelectedQuestionEvent = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								tfSelectedQuestion.setText(tfSelectedQuestion.getText());
							}
						};
						tfSelectedQuestion.setOnAction(tfSelectedQuestionEvent);
						initExam.allQ.get(index).setQuestion(tfSelectedQuestion.getText());
						try {
							initExam.UpdateDatabase(initExam.allQ.get(index));
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						oblQuestList.set(index, tfSelectedQuestion.getText());
						cmbQuestions.getSelectionModel().clearSelection();
						tfSelectedQuestion.clear();
					}
				};
				btSubmit.setOnAction(btSubmitEvent);
			};
			cmbQuestions.setOnAction(cmbQuestionsEvent);

			gpEditQuest.setPrefSize(550, 610);
			gpEditQuest.add(lbEditQuestion, 0, 0);
			gpEditQuest.add(cmbQuestions, 0, 1);
			gpEditQuest.add(tfSelectedQuestion, 0, 3);
			gpEditQuest.add(btSubmit, 0, 4);
		});
		// *************************************************** Delete Question
		// *****************************************
		btDeleteQuestFromDB.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.printDatabaseToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpDeleteQuestFromDB.setVisible(true);
			Label lbQuestToChoose = new Label("Please Choose a Question to Delete From Database:");
			ArrayList<String> arrQuestDeleteList = new ArrayList<String>();
			Button btDeleteQuest = new Button("Delete");
			btDeleteQuest.setVisible(false);
			for (int i = 0; i < initExam.allQ.size(); i++) {
				arrQuestDeleteList.add(initExam.allQ.get(i).getQuestion());
			}
			ObservableList<String> oblQuestDeleteList = FXCollections.observableArrayList(arrQuestDeleteList);
			ComboBox<String> cmbQuestions = new ComboBox<>(oblQuestDeleteList);
			cmbQuestions.setMaxWidth(450);
			EventHandler<ActionEvent> cmbQuestionsEvent = e -> {
				btDeleteQuest.setVisible(true);
				cmbQuestions.setSelectionModel(cmbQuestions.getSelectionModel());
				int questIndex = cmbQuestions.getSelectionModel().getSelectedIndex();
				EventHandler<ActionEvent> btDeleteQuestEvent = DeleteQuestEvent -> {
					String tempQuest = "";
					try {
						tempQuest = initExam.allQ.get(questIndex).toString();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "No Questions to Delete");
					}
					initExam.deleteQuestByIndex(questIndex);
					JOptionPane.showMessageDialog(null,
							"This Question \n" + tempQuest + "\n Has Been Deleted Successfully");
					cmbQuestions.getSelectionModel().clearSelection();
					ArrayList<String> arrQuestDeleteList1 = new ArrayList<String>();
					for (int i = 0; i < initExam.allQ.size(); i++) {
						arrQuestDeleteList1.add(initExam.allQ.get(i).getQuestion());
					}
					oblQuestDeleteList.setAll(arrQuestDeleteList1);
					cmbQuestions.setItems(oblQuestDeleteList);
					/*
					 * try { initExam.UpdateDatabase(); } catch (FileNotFoundException e1) { // TODO
					 * Auto-generated catch block e1.printStackTrace(); } catch (IOException e1) {
					 * // TODO Auto-generated catch block e1.printStackTrace(); }
					 */
					btDeleteQuest.setVisible(false);
				};
				btDeleteQuest.setOnAction(btDeleteQuestEvent);
			};
			cmbQuestions.setOnAction(cmbQuestionsEvent);

			gpDeleteQuestFromDB.add(lbQuestToChoose, 0, 0);
			gpDeleteQuestFromDB.add(cmbQuestions, 0, 1);
			gpDeleteQuestFromDB.add(btDeleteQuest, 0, 2);
		});
		// ***************************************************EditAnswer*********************************************
		btEditAnswer.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.editAnswerToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpEditAnswer.setVisible(true);
			Label lbQuestionSelect = new Label("Please select the question you would like to edit");
			lbQuestionSelect.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Label lblLecturer = new Label("Please select the question you would like to edit");
			lbQuestionSelect.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

			Button btSubmit = new Button("Submit");

			TextField tfNewAnswer = new TextField();
			tfNewAnswer.disabledProperty();

			ObservableList<Boolean> oblTrueOrF = FXCollections.observableArrayList(true, false);
			ComboBox<Boolean> cmbBoolAnsTrueOrF = new ComboBox<Boolean>(oblTrueOrF);

			ObservableList<String> oblQuestList = FXCollections.observableArrayList();
			ComboBox<String> cmbQuestions = new ComboBox<String>();
			cmbQuestions.setMaxWidth(450);

			ObservableList<String> oblAnswerList = FXCollections.observableArrayList();
			ComboBox<String> cmbAnswerList = new ComboBox<String>();
			cmbAnswerList.setMaxWidth(450);

			ArrayList<String> arrQuestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allQ.size(); i++) {
				arrQuestList.add(initExam.allQ.get(i).getQuestion());
			}

			oblQuestList.setAll(arrQuestList);
			cmbQuestions.setItems(oblQuestList);

			EventHandler<ActionEvent> cmbQuestionsEvent = e -> {
				int indexQuest;
				indexQuest = cmbQuestions.getSelectionModel().getSelectedIndex();
				ArrayList<String> arrAnswerList = new ArrayList<String>();
				if (initExam.allQ.get(indexQuest) instanceof AmericanQuestion) {
					AmericanQuestion tempAmQ = (AmericanQuestion) initExam.getQuestByIndex(indexQuest);
					for (int i = 0; i < tempAmQ.getNumOfAnswers(); i++) {
						arrAnswerList.add(tempAmQ.getAnswerByIndex(i).getAnswer());
					}
					oblAnswerList.addAll(arrAnswerList);
				} else {
					arrAnswerList.add(initExam.getQuestByIndex(indexQuest).getAnswer().getAnswer());
					cmbBoolAnsTrueOrF.setVisible(false);
				}

				oblAnswerList.setAll(arrAnswerList);
				cmbAnswerList.setItems(oblAnswerList);
				EventHandler<ActionEvent> cmbAnswerListEvent = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						int indexAnswer;
						cmbAnswerList.setSelectionModel(cmbAnswerList.getSelectionModel());
						indexAnswer = cmbAnswerList.getSelectionModel().getSelectedIndex();
						if (initExam.allQ.get(indexQuest) instanceof AmericanQuestion) {
							EventHandler<ActionEvent> cmbBoolAnsTrueOrFEvent = new EventHandler<ActionEvent>() {
								public void handle(ActionEvent e) {
									Boolean tempBool;
									tempBool = cmbBoolAnsTrueOrF.getValue();
									EventHandler<ActionEvent> btSubmitEvent = new EventHandler<ActionEvent>() {
										public void handle(ActionEvent e) {
											EventHandler<ActionEvent> tfNewAnswerEvent = new EventHandler<ActionEvent>() {
												public void handle(ActionEvent e) {
													tfNewAnswer.setText(tfNewAnswer.getText());
												}
											};

											String tempAns;
											tempAns = tfNewAnswer.getText();
											tfNewAnswer.setOnAction(tfNewAnswerEvent);

											Answer newAns = new Answer(tempAns, tempBool,
													initExam.allQ.get(indexQuest).answer.getLecturerID());
											initExam.allQ.get(indexQuest).editAnswer(newAns, indexAnswer);

											try {
												initExam.UpdateDatabase(initExam.allQ.get(indexQuest));
											} catch (FileNotFoundException e1) {
												e1.printStackTrace();
											} catch (IOException e1) {
												e1.printStackTrace();
											}
											tfNewAnswer.clear();
											cmbAnswerList.getSelectionModel().clearSelection();
											cmbBoolAnsTrueOrF.getSelectionModel().clearSelection();
											oblAnswerList.set(indexAnswer, tfNewAnswer.getText());
										}
									};
									btSubmit.setOnAction(btSubmitEvent);
								}
							};
							cmbBoolAnsTrueOrF.setOnAction(cmbBoolAnsTrueOrFEvent);
						} else {
							EventHandler<ActionEvent> btSubmitEvent = Submit -> {
								EventHandler<ActionEvent> tfNewAnswerEvent = NewAnswerEvent -> {
									tfNewAnswer.setText(tfNewAnswer.getText());
									System.out.println("4");
								};

								String tempAns;

								tempAns = tfNewAnswer.getText();
								tfNewAnswer.setOnAction(tfNewAnswerEvent);
								boolean tempBool = true;
								Answer newAns = new Answer(tempAns, tempBool,
										initExam.allQ.get(indexQuest).answer.getLecturerID());
								initExam.allQ.get(indexQuest).editAnswer(newAns, indexAnswer);
								try {
									initExam.UpdateDatabase(initExam.allQ.get(indexQuest));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								tfNewAnswer.clear();
								cmbAnswerList.getSelectionModel().clearSelection();
								cmbBoolAnsTrueOrF.getSelectionModel().clearSelection();
								oblAnswerList.set(indexAnswer, tfNewAnswer.getText());
								System.out.println("5");
							};
							btSubmit.setOnAction(btSubmitEvent);
						}
					}
				};
				cmbAnswerList.setOnAction(cmbAnswerListEvent);
			};
			cmbQuestions.setOnAction(cmbQuestionsEvent);

			gpEditAnswer.setPrefSize(550, 610);
			gpEditAnswer.add(lbQuestionSelect, 0, 0);
			gpEditAnswer.add(cmbQuestions, 0, 1);
			gpEditAnswer.add(cmbAnswerList, 0, 2);
			gpEditAnswer.add(tfNewAnswer, 0, 3);
			gpEditAnswer.add(cmbBoolAnsTrueOrF, 1, 3);
			gpEditAnswer.add(btSubmit, 0, 6);
			gpEditAnswer.add(lblLecturer, 0, 4);

		});
		// ***************************************************DeleteAnswer***********************************************
		btDeleteAnswer.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.deleteAnswerToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpDeleteAnswer.setVisible(true);

			Label lbQuestionSelect = new Label("Please select the question with the answer you would like to delete");
			lbQuestionSelect.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

			Button btSubmit = new Button("Delete");

			ObservableList<String> oblQuestList = FXCollections.observableArrayList();
			ComboBox<String> cmbQuestions = new ComboBox<String>();
			cmbQuestions.setMaxWidth(450);

			ObservableList<String> oblAnswerList = FXCollections.observableArrayList();
			ComboBox<String> cmbAnswerList = new ComboBox<String>();
			cmbAnswerList.setMaxWidth(450);

			ArrayList<String> arrQuestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allQ.size(); i++) {
				arrQuestList.add(initExam.allQ.get(i).getQuestion());
			}

			oblQuestList.setAll(arrQuestList);
			cmbQuestions.setItems(oblQuestList);

			EventHandler<ActionEvent> cmbQuestionsEvent = onChoice -> {
				int indexQuest;
				indexQuest = cmbQuestions.getSelectionModel().getSelectedIndex();
				ArrayList<String> arrAnswerList = new ArrayList<String>();
				if (initExam.allQ.get(indexQuest) instanceof AmericanQuestion) {
					AmericanQuestion tempAmQ = (AmericanQuestion) initExam.getQuestByIndex(indexQuest);
					for (int i = 0; i < tempAmQ.getNumOfAnswers(); i++) {
						arrAnswerList.add(tempAmQ.getAnswerByIndex(i).getAnswer());
					}
					oblAnswerList.addAll(arrAnswerList);
				} else {
					arrAnswerList.add(initExam.getQuestByIndex(indexQuest).getAnswer().getAnswer());
				}

				oblAnswerList.setAll(arrAnswerList);
				cmbAnswerList.setItems(oblAnswerList);
				EventHandler<ActionEvent> cmbAnswerListEvent = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						int indexAnswer;
						cmbAnswerList.setSelectionModel(cmbAnswerList.getSelectionModel());
						indexAnswer = cmbAnswerList.getSelectionModel().getSelectedIndex();
						EventHandler<ActionEvent> btSubmitEvent = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								if (initExam.allQ.get(indexQuest).getClass() == AmericanQuestion.class) {
									initExam.deleteAmericanAns(indexQuest, indexAnswer);
								} else {									
									initExam.allQ.get(indexQuest).setAnswer(null, -1);
								}
								initExam.handleDeleteAns(initExam.allQ.get(indexQuest));
								try {
									initExam.UpdateDatabase(initExam.allQ.get(indexQuest));
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						};
						btSubmit.setOnAction(btSubmitEvent);

					}
				};
				cmbAnswerList.setOnAction(cmbAnswerListEvent);

			};
			cmbQuestions.setOnAction(cmbQuestionsEvent);

			gpDeleteAnswer.setPrefSize(550, 610);
			gpDeleteAnswer.add(lbQuestionSelect, 0, 0);
			gpDeleteAnswer.add(cmbQuestions, 0, 1);
			gpDeleteAnswer.add(cmbAnswerList, 0, 2);
			gpDeleteAnswer.add(btSubmit, 0, 4);

		});
		// ******CreateManualExam*********************************************************************************
		btCreateManualExam.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.createManualExamToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpCreateManualExam.setVisible(true);
			Label lbManualExamName = new Label("Enter name for you test");
			TextField tfManualExamName = new TextField();

			Test manualTest = new Test();

			Label lbQuestionSelect = new Label("Please select the next question type you want to add");
			lbQuestionSelect.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Label lbAns = new Label("Please enter your answer");
			lbAns.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			Label lbLecturerID = new Label("Please enter your ID");
			lbLecturerID.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			TextField tfLecturerID = new TextField(lbLecturerID.getText());
			Button BTadd = new Button("Add");
			Button showExam = new Button("showExam");
			tfLecturerID.setOnMouseClicked(r -> {
				tfLecturerID.clear();
			});
			TextField tfNewQuest = new TextField("Enter here your question");
			tfNewQuest.setOnMouseClicked(r -> {
				tfNewQuest.clear();
			});
			TextField tfNewOpenAns = new TextField("Enter your answer here ");
			tfNewOpenAns.setOnMouseClicked(e -> {
				tfNewOpenAns.clear();
			});
			Vector<TextField> tfNewAmericanANS = new Vector<TextField>();
			Text TAddQ = new Text();

			RadioButton rbOpenQ = new RadioButton("Open Question");
			RadioButton rbAmericanQ = new RadioButton("American Question");

			rbOpenQ.setFont(new Font("verdana", 15));
			rbAmericanQ.setFont(new Font("verdana", 15));
			ToggleGroup tgOpenOrAm = new ToggleGroup();

			ObservableList<Integer> ansNumOptionsObs = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
			ComboBox<Integer> cmbNumOfOptions = new ComboBox<Integer>(ansNumOptionsObs);

			ObservableList<Boolean> oblTrueOrF = FXCollections.observableArrayList(true, false);
			Vector<ComboBox<Boolean>> cmbTrueOrFalse = new Vector<ComboBox<Boolean>>();

			for (int i = 0; i < 10; i++) {
				tfNewAmericanANS.add(new TextField("Enter answer here"));
				int finalI = i;
				tfNewAmericanANS.get(i).setOnMouseClicked(MouseClicked -> {
					tfNewAmericanANS.get(finalI).clear();
				});
				cmbTrueOrFalse.add(new ComboBox<Boolean>(oblTrueOrF));
			}
			StackPane spAddNewOpenQ = new StackPane();
			StackPane spAddNewAmQ = new StackPane();
			spAddNewOpenQ.setVisible(false);
			spAddNewAmQ.setVisible(false);

			GridPane gpAddnewAmQ = new GridPane();
			GridPane gpAddnewOpen = new GridPane();
			gpCreateManualExam.add(lbManualExamName, 0, 0);
			gpCreateManualExam.add(tfManualExamName, 0, 1);
			gpCreateManualExam.add(lbQuestionSelect, 0, 2);
			gpCreateManualExam.add(tfNewQuest, 0, 3);
			gpCreateManualExam.add(tfLecturerID, 0, 6);
			gpCreateManualExam.add(rbOpenQ, 0, 7);
			gpCreateManualExam.add(rbAmericanQ, 0, 8);
			gpCreateManualExam.add(BTadd, 0, 9);
			gpCreateManualExam.add(TAddQ, 0, 10);
			gpCreateManualExam.add(showExam, 0, 11);

			gpCreateManualExam.add(spAddNewAmQ, 0, 12);
			gpCreateManualExam.add(spAddNewOpenQ, 0, 13);

			gpAddnewOpen.add(lbAns, 0, 1);
			gpAddnewOpen.add(tfNewOpenAns, 0, 2);

			spAddNewOpenQ.getChildren().add(gpAddnewOpen);

			spAddNewAmQ.getChildren().add(gpAddnewAmQ);

			gpAddnewAmQ.add(cmbNumOfOptions, 0, 1);

			EventHandler<ActionEvent> showExamEvent = e -> {
				EventHandler<ActionEvent> tfManualExamNameEvent = r -> tfManualExamName
						.setText(tfManualExamName.getText());
				tfManualExamName.setOnAction(tfManualExamNameEvent);

				manualTest.setName(tfManualExamName.getText());
				gpCreateManualExam.setVisible(false);

				try {
					if (!initExam.saveTest(manualTest)) {
						System.out.println("test was not saved!");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				gpPrintDatabase.setVisible(true);
				gpPrintDatabase.getChildren().clear();

				Text text = new Text();
				text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

				gpPrintDatabase.add(text, 0, 0);

				text.setText(manualTest.toString());
				ScrollPane s = new ScrollPane();
				s.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
				s.setPannable(true);
				s.setPrefSize(800, 400);
				s.setContent(text);
				gpPrintDatabase.getChildren().add(s);
				// JOptionPane.showMessageDialog(null, manualTest.toString());
				tfManualExamName.clear();
				tfNewQuest.clear();
				tfNewOpenAns.clear();
				TAddQ.setText("");
				spAddNewAmQ.setVisible(false);

			};
			showExam.setOnAction(showExamEvent);

			rbOpenQ.setToggleGroup(tgOpenOrAm);
			rbAmericanQ.setToggleGroup(tgOpenOrAm);

			tgOpenOrAm.selectedToggleProperty().addListener((ob, o, n) -> {
				if (rbOpenQ.isSelected()) {
					spAddNewOpenQ.setVisible(true);
					spAddNewAmQ.setVisible(false);
					EventHandler<ActionEvent> rbOpenEvent = k -> {
						EventHandler<ActionEvent> btaddOpenQEvent = m -> {
							EventHandler<ActionEvent> tfNewQuestEvent = c -> tfNewQuest.setText(tfNewQuest.getText());
							EventHandler<ActionEvent> tfNewOpenAnsEvent = d -> tfNewOpenAns
									.setText(tfNewOpenAns.getText());

							tfNewQuest.setOnAction(tfNewQuestEvent);
							tfLecturerID.setVisible(false);
							tfNewOpenAns.setOnAction(tfNewOpenAnsEvent);
							String openQ = tfNewQuest.getText();
							String openAns = tfNewOpenAns.getText();

							tfLecturerID.setOnAction(r -> {
								tfLecturerID.setText(tfLecturerID.getText());
							});
							int lecturerID = Integer.parseInt(tfLecturerID.getText());
							Question newOpenQ = new Question(openQ, openAns, lecturerID, 0);

							manualTest.allQ.add(newOpenQ);
							try {
								initExam.UpdateDatabase(newOpenQ);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							tfNewQuest.clear();
							tfNewOpenAns.clear();

							TAddQ.setText("The question:\n" + newOpenQ + "Has been added successfully");
							TAddQ.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

						};

						BTadd.setOnAction(btaddOpenQEvent);
					};

					rbOpenQ.setOnAction(rbOpenEvent);
				} else if (rbAmericanQ.isSelected()) {
					spAddNewAmQ.setVisible(true);
					spAddNewOpenQ.setVisible(false);
					EventHandler<ActionEvent> rbAmerEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							EventHandler<ActionEvent> cmbNumOfOptionsEvent = NumOfOptionsEvent -> {
								int ansCount;
								tfLecturerID.setVisible(false);
								if (ansCounter != 0) {
									gpAddnewAmQ.getChildren().clear();
									gpAddnewAmQ.add(lbAns, 0, 0);
									gpAddnewAmQ.add(cmbNumOfOptions, 0, 1);
								}
								ansCount = cmbNumOfOptions.getValue();
								for (int i = 0; i <= ansCount - 1; i++) {
									gpAddnewAmQ.add(tfNewAmericanANS.get(i), 0, i + 3);
									gpAddnewAmQ.add(cmbTrueOrFalse.get(i), 1, i + 3);
									ansCounter++;
								}

								EventHandler<ActionEvent> btSubmitAmQEvent = SubmitAmQEvent -> {
									System.out.println("American Question Added Successfully");
									String AmericanQ = tfNewQuest.getText();
									Vector<Answer> ansArr = new Vector<Answer>();

									tfLecturerID.setOnAction(r -> {
										tfLecturerID.setText(tfLecturerID.getText());
									});

									int lecturerID = Integer.parseInt(tfLecturerID.getText());

									for (int i = 0; i < cmbNumOfOptions.getValue(); i++) {
										Answer tempAns = new Answer(tfNewAmericanANS.get(i).getText(),
												cmbTrueOrFalse.get(i).getValue(), lecturerID);
										ansArr.add(tempAns);
									}
									tfLecturerID.setOnAction(r -> {
										tfLecturerID.setText(tfLecturerID.getText());
									});

									AmericanQuestion newAmericanQ = new AmericanQuestion(AmericanQ, ansArr, lecturerID);
									manualTest.allQ.add(newAmericanQ);
									try {
										initExam.UpdateDatabase(newAmericanQ);
									} catch (FileNotFoundException e1) {
										e1.printStackTrace();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									tfNewQuest.clear();
									for (int i = 0; i < tfNewAmericanANS.size(); i++) {
										tfNewAmericanANS.get(i).clear();
										cmbTrueOrFalse.get(i).cancelEdit();
									}
									TAddQ.setText("The question:\n" + newAmericanQ + "Has been added successfully");
									TAddQ.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
								};
								BTadd.setOnAction(btSubmitAmQEvent);
							};
							cmbNumOfOptions.setOnAction(cmbNumOfOptionsEvent);
						}
					};
					rbAmericanQ.setOnAction(rbAmerEvent);
				}
			});
		});
		// *************CreateAutoExam************************************************************************
		btCreateAutoExam.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.createAutoExamToUI(initExam);
			}
			// ********need to add the action**********
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			Label lbAutoExamName = new Label("Enter name for you test");
			TextField tfAutoExamName = new TextField();

			gpCreateAutoExam.setVisible(true);
			Label lbQuestionSelect = new Label("Please select how many\nquestion would like");
			lbQuestionSelect.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

			ObservableList<Integer> ObsQuestionAmount = FXCollections.observableArrayList();
			ComboBox<Integer> cmbNumOfQuestions = new ComboBox<Integer>(ObsQuestionAmount);

			Vector<Integer> ObsNumList = new Vector<Integer>();
			for (int i = 1; i < initExam.allQ.size(); i++) {
				ObsNumList.add(i);
			}
			ObsQuestionAmount.setAll(ObsNumList);
			cmbNumOfQuestions.setItems(ObsQuestionAmount);

			EventHandler<ActionEvent> cmbNumOfQuestionsEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int questionAmount;
					questionAmount = cmbNumOfQuestions.getSelectionModel().getSelectedIndex();

					EventHandler<ActionEvent> tfAutoExamNameEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							tfAutoExamName.setText(tfAutoExamName.getText());
						}
					};
					tfAutoExamName.setOnAction(tfAutoExamNameEvent);
					Test autoExam = new Test(tfAutoExamName.getText());
					while (autoExam.allQ.size() <= questionAmount) {
						autoExam.addNewQuest(
								initExam.allQ.get((int) Math.ceil(Math.random() * initExam.allQ.size()) - 1));
					}
					gpPrintDatabase.setVisible(true);
					gpPrintDatabase.getChildren().clear();

					Text text = new Text();
					text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

					gpPrintDatabase.add(text, 0, 0);

					text.setText(autoExam.toString());
					ScrollPane s = new ScrollPane();
					s.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
					s.setPannable(true);
					s.setPrefSize(800, 400);
					s.setContent(text);
					gpPrintDatabase.getChildren().add(s);
					gpCreateAutoExam.setVisible(false);
					// JOptionPane.showMessageDialog(null, autoExam +"\nYour test has been saved
					// successfully!");
					try {
						if (!initExam.saveTest(autoExam))
							System.out.print("test was not saved!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			};
			cmbNumOfQuestions.setOnAction(cmbNumOfQuestionsEvent);

			gpCreateAutoExam.setPrefSize(550, 610);
			gpCreateAutoExam.add(lbAutoExamName, 0, 1);
			gpCreateAutoExam.add(tfAutoExamName, 0, 2);
			gpCreateAutoExam.add(lbQuestionSelect, 0, 3);
			gpCreateAutoExam.add(cmbNumOfQuestions, 0, 4);

		});
		// ******CreateExamClone**************************************************************************
		btCreateExamClone.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.createExamCloneToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpCreateExamClone.setVisible(true);
			Button btClone = new Button("Clone");
			Label lbQuestionSelect = new Label("Please Select The Test you want to Clone");
			ObservableList<String> oblTestsList = FXCollections.observableArrayList();
			ComboBox<String> cmbTests = new ComboBox<String>();
			cmbTests.setMaxWidth(450);

			ArrayList<String> arrQuestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allTests.size(); i++) {
				arrQuestList.add(initExam.allTests.get(i).getName());
			}

			oblTestsList.setAll(arrQuestList);
			cmbTests.setItems(oblTestsList);

			EventHandler<ActionEvent> cmbTestsEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int index = cmbTests.getSelectionModel().getSelectedIndex();

					EventHandler<ActionEvent> btCloneEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							try {
								initExam.allTests.add(initExam.getTestByID(index).clone());
							} catch (CloneNotSupportedException e1) {
								System.out.println("Clone not support Exception");
							} catch (Exception e1) {
								System.out.println("General Error");
							}
							cmbTests.getSelectionModel().clearSelection();
							ArrayList<String> arrQuestList = new ArrayList<String>();
							for (int i = 0; i < initExam.allTests.size(); i++) {
								arrQuestList.add(initExam.allTests.get(i).getName());
							}

							oblTestsList.setAll(arrQuestList);
							cmbTests.setItems(oblTestsList);
							JOptionPane.showMessageDialog(null, "Your clone test has been created successfully!");
						}
					};
					btClone.setOnAction(btCloneEvent);
				}

			};
			cmbTests.setOnAction(cmbTestsEvent);

			gpCreateExamClone.setPrefSize(550, 610);
			gpCreateExamClone.add(lbQuestionSelect, 0, 0);
			gpCreateExamClone.add(cmbTests, 0, 1);
			gpCreateExamClone.add(btClone, 0, 2);
		});
		// ******PrintTestsInMem*************************************************************************
		btPrintTestsInMem.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.printTestsInMemToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpPrintTestsInMem.setVisible(true);
			Label lbPrintTest = new Label("Please choose a test from the list:");
			Button btShowTest = new Button("Show Test");
			ObservableList<String> oblTestsList = FXCollections.observableArrayList();
			ComboBox<String> cmbTests = new ComboBox<String>();
			cmbTests.setMaxWidth(450);

			ArrayList<String> arrTestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allTests.size(); i++) {
				arrTestList.add(initExam.allTests.get(i).getName());
			}

			oblTestsList.setAll(arrTestList);
			cmbTests.setItems(oblTestsList);

			EventHandler<ActionEvent> cmbTestsEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int index = cmbTests.getSelectionModel().getSelectedIndex();

					EventHandler<ActionEvent> btShowTestEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							// JOptionPane.showMessageDialog(null,initExam.getTestByID(index));
							gpPrintDatabase.setVisible(true);
							gpPrintTestsInMem.setVisible(false);
							gpPrintDatabase.getChildren().clear();

							Text text = new Text();
							text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
							gpPrintDatabase.add(text, 0, 0);
							text.setText(initExam.getTestByID(index).toString());
							ScrollPane s = new ScrollPane();
							s.setBackground(
									new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
							s.setPannable(true);
							s.setPrefSize(800, 400);
							s.setContent(text);
							gpPrintDatabase.getChildren().add(s);
							gpCreateAutoExam.setVisible(false);

							cmbTests.getSelectionModel().clearSelection();
							ArrayList<String> arrTestList = new ArrayList<String>();
							for (int i = 0; i < initExam.allTests.size(); i++) {
								arrTestList.add(initExam.allTests.get(i).getName());
							}
							oblTestsList.setAll(arrTestList);
							cmbTests.setItems(oblTestsList);
						}
					};
					btShowTest.setOnAction(btShowTestEvent);
				}
			};
			cmbTests.setOnAction(cmbTestsEvent);
			gpPrintTestsInMem.setPrefSize(550, 610);
			gpPrintTestsInMem.add(lbPrintTest, 0, 0);
			gpPrintTestsInMem.add(cmbTests, 0, 1);
			gpPrintTestsInMem.add(btShowTest, 0, 2);
		});
		// *********************************ExitAndSave***********************************************
		btExitAndSave.setOnAction(action -> {
			initExam.saveAllTestToData();
			for (Exam_UI_EventListener l : allListeners) {
				l.exitAndSaveToUI(initExam);
			}
			primaryStage.close();
		});
		// ******************************AddNewQuestToExistTest***************************************************************
		btAddNewQuestToExistTest.setOnAction(action -> {
			for (Exam_UI_EventListener l : allListeners) {
				l.addNewQuestToExistTestToUI(initExam);
			}
			for (GridPane g : gpArr) {
				g.setVisible(false);
			}
			gpAddNewQuestToExistTest.setVisible(true);
			Label lbTestToChoose = new Label("Choose a test to add question");
			Button btAddQuest = new Button("Add Quest");
			ObservableList<String> oblTestsList = FXCollections.observableArrayList();
			ComboBox<String> cmbTestsList = new ComboBox<String>();
			cmbTestsList.setMaxWidth(450);

			ArrayList<String> arrTestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allTests.size(); i++) {
				arrTestList.add(initExam.allTests.get(i).getName());
			}

			oblTestsList.setAll(arrTestList);
			cmbTestsList.setItems(oblTestsList);

			ObservableList<String> oblQuestsList = FXCollections.observableArrayList();
			ComboBox<String> cmbQuestsList = new ComboBox<String>();
			cmbTestsList.setMaxWidth(450);

			ArrayList<String> arrQuestList = new ArrayList<String>();
			for (int i = 0; i < initExam.allQ.size(); i++) {
				arrQuestList.add(initExam.allQ.get(i).getQuestion());
			}

			oblQuestsList.setAll(arrQuestList);
			cmbQuestsList.setItems(oblQuestsList);

			EventHandler<ActionEvent> btAddQuestEvent = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					int indexOfTest = 0;
					int indexOfQuest = 0;
					EventHandler<ActionEvent> cmbTestsEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							cmbTestsList.setSelectionModel(cmbTestsList.getSelectionModel());
						}
					};
					cmbTestsList.setOnAction(cmbTestsEvent);
					indexOfTest = cmbTestsList.getSelectionModel().getSelectedIndex();
					EventHandler<ActionEvent> cmbQuestsListEvent = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							cmbQuestsList.setSelectionModel(cmbQuestsList.getSelectionModel());
						}
					};
					cmbQuestsList.setOnAction(cmbQuestsListEvent);
					indexOfQuest = cmbQuestsList.getSelectionModel().getSelectedIndex();

					initExam.allTests.get(indexOfTest).allQ.add(initExam.allQ.get(indexOfQuest));
					JOptionPane.showMessageDialog(null,
							"Question added Successfully to " + initExam.allTests.get(indexOfTest).getName());
					cmbQuestsList.getSelectionModel().clearSelection();
					cmbTestsList.getSelectionModel().clearSelection();
				}
			};
			btAddQuest.setOnAction(btAddQuestEvent);

			gpAddNewQuestToExistTest.add(lbTestToChoose, 0, 0);
			gpAddNewQuestToExistTest.add(cmbTestsList, 0, 1);
			gpAddNewQuestToExistTest.add(cmbQuestsList, 0, 2);
			gpAddNewQuestToExistTest.add(btAddQuest, 0, 3);
		});
	}

	@Override
	public void registerListener(Exam_UI_EventListener listener) {
		allListeners.add(listener);

	}

	@Override
	public void printDatabaseToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void addNewQuestToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void editQuestToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void editAnswerToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void deleteAnswerToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void createManualExamToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void createAutoExamToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void createExamCloneToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void printTestsInMemToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void exitAndSaveToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

	@Override
	public void addNewQuestToExistTestToUI(Database initExam) {
		// TODO Auto-generated method stub
		// need to add the action

	}

}

package ui;

import backend.services.AnswerService;
import backend.services.QuestionService;
import com.googlecode.lanterna.gui2.Window;
import ui.windows.AddNewQuestionWindow;
import ui.windows.AllQuestionsWindow;
import ui.windows.MainWindow;
import ui.windows.QuestionWindow;

/*
Handles navigation
 */
public class UIController {

    private final Gui gui;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public UIController(Gui gui, QuestionService questionService, AnswerService answerService) {
        this.gui = gui;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    public void showMainMenu() {
        gui.show(new MainWindow(this));
    }

    public void showAllQuestionsPage() {
        gui.show(new AllQuestionsWindow(this, questionService));
    }

    public void showQuestionPage(int questionId) {
        gui.show(new QuestionWindow(this, questionService, questionId));
    }

    public void showAddNewQuestionPage() {
        gui.show(new AddNewQuestionWindow(this, questionService, answerService));
    }

    public void closeWindow(Window window) {
        window.close();
    }

    public void closeApp() {
        gui.close();
    }
}

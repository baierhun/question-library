package ui;

import backend.services.QuestionService;
import com.googlecode.lanterna.gui2.Window;
import ui.windows.AllQuestionsWindow;
import ui.windows.MainWindow;

/*
Handles navigation
 */
public class UIController {

    private final Gui gui;
    private final QuestionService questionService;

    public UIController(Gui gui, QuestionService questionService) {
        this.gui = gui;
        this.questionService = questionService;
    }

    public void showMainMenu() {
        gui.show(new MainWindow(this));
    }

    public void showAllQuestionsPage() {
        gui.show(new AllQuestionsWindow(this, questionService));
    }

    public void closeWindow(Window window) {
        window.close();
    }

    public void closeApp() {
        gui.close();
    }
}

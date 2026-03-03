package ui.windows;

import backend.services.QuestionService;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import models.Question;
import ui.UIController;

import java.util.List;

public class AllQuestionsWindow extends BasicWindow {

    private final UIController ui;
    private final QuestionService service;

    public AllQuestionsWindow(UIController ui, QuestionService service) {
        super("All Questions");
        this.ui = ui;
        this.service = service;
        setHints(List.of(Hint.CENTERED, Hint.EXPANDED, Hint.NO_POST_RENDERING));
        setComponent(build());
    }

    private record MenuItem(String name, Runnable func) {
    }

    private Component build() {
        Panel panel = new Panel();
        panel.setLayoutManager(
                new LinearLayout(Direction.VERTICAL)
        );

        Panel questionsPanel = new Panel().setLayoutManager(new GridLayout(2));
        panel.addComponent(questionsPanel);

        List<Question> questions = service.getAllQuestions();

        ActionListBox alb = new ActionListBox();
        ActionListBox deleteAlb = new ActionListBox();
        questionsPanel.addComponent(deleteAlb);
        questionsPanel.addComponent(alb);

        for (Question q : questions) {
            deleteAlb.addItem("X", () ->
            {
                MessageDialogButton res =
                        ui.showConfirmationDialog("Delete Record",
                                "Are you sure you want to delete the question\n" + q.text(),
                                MessageDialogButton.OK,
                                MessageDialogButton.Cancel);
                if (res == MessageDialogButton.OK) {
                    service.deleteQuestion(q.id());
                }
            });
            alb.addItem(q.text(), () -> ui.showQuestionPage(q.id()));
        }
        panel.addComponent(new Button("Back", () -> ui.closeWindow(this)));

        return panel;
    }
}

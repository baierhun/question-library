package ui.windows;

import backend.services.QuestionService;
import com.googlecode.lanterna.gui2.*;
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
        setHints(List.of(Hint.CENTERED));
        setComponent(build());
    }

    private record MenuItem(String name, Runnable func) {
    }

    private Component build() {
        Panel panel = new Panel();
        panel.setLayoutManager(
                new LinearLayout(Direction.VERTICAL)
        );

        List<Question> questions = service.getAllQuestions();

        ActionListBox alb = new ActionListBox();
        panel.addComponent(alb);

        for (Question q : questions) {
            alb.addItem(q.text(), () -> System.out.println(q.id()));
        }
        alb.addItem("Back", () -> ui.closeWindow(this));

        return panel;
    }
}

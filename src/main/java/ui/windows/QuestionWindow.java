package ui.windows;

import backend.services.QuestionService;
import com.googlecode.lanterna.gui2.*;
import models.Answer;
import models.Question;
import models.QuestionAnswer;
import ui.UIController;

import java.util.List;

public class QuestionWindow extends BasicWindow {

    private final UIController ui;
    private final QuestionService service;
    private final Question question;
    private final Iterable<Answer> answers;

    public QuestionWindow(UIController ui, QuestionService service, int questionId) {
        super("Question " + questionId);
        this.ui = ui;
        this.service = service;
        QuestionAnswer questionAnswer = service.getQuestion(questionId);
        this.question = questionAnswer.question();
        this.answers = questionAnswer.answers();
        setHints(List.of(Hint.CENTERED));
        setComponent(build());
    }

    private Component build() {
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout());
        Panel panel = new Panel();
        panel.setLayoutManager(
                new GridLayout(2)
        );

        for (Answer a : this.answers) {
            panel.addComponent(new Label(a.text()));
        }

        mainPanel.addComponent(new Label(this.question.text()));
        mainPanel.addComponent(panel);
        mainPanel.addComponent(new Button("Back", () -> ui.closeWindow(this)));

        return mainPanel;
    }
}

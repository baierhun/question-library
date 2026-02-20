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
    private Question question;
    private final List<Answer> answers;

    public QuestionWindow(UIController ui, QuestionService service, int questionId) {
        super("Question " + questionId);
        this.ui = ui;
        this.service = service;
        QuestionAnswer questionAnswer = service.getQuestion(questionId);
        this.question = questionAnswer.question();
        this.answers = questionAnswer.answers();
        setHints(List.of(Hint.CENTERED, Hint.EXPANDED, Hint.NO_POST_RENDERING));
        setComponent(build());
    }

    private Component build() {
        /*
        This component has two panels, the first is linear and vertical
        and the second is 4 columns
         */
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout());
        LayoutData centered = LinearLayout.createLayoutData(LinearLayout.Alignment.Center);

        Panel answersPanel = new Panel();
        answersPanel.setLayoutManager(
                new GridLayout(4)
        );

        // The answers all have text and a checkbox next to them,
        // They listen for changes and update the database when triggered (on change)
        for (Answer a : this.answers) {
            answersPanel.addComponent(new TextBox(a.text())
                    .setTextChangeListener(this.answerTextChangeListener(a))
                    .setLayoutData(centered));
            answersPanel.addComponent(new CheckBox()
                    .setChecked(a.isAnswer())
                    .addListener(this.answerChangeListener(a)));
        }

        TextBox questionText =
                new TextBox(this.question.text())
                        .setTextChangeListener(this.textChangeListener(this.question.id()));

        // add all the components to the main panel
        mainPanel.addComponent(questionText.setLayoutData(centered));
        mainPanel.addComponent(answersPanel.setLayoutData(centered));
        mainPanel.addComponent(new EmptySpace());
        mainPanel.addComponent(new EmptySpace());
        mainPanel.addComponent(new Button("Back", () -> ui.closeWindow(this)).setLayoutData(centered));

        return mainPanel;
    }

    private TextBox.TextChangeListener answerTextChangeListener(Answer a) {
        return (String newText, boolean changedByUserInteraction) -> {
            if (changedByUserInteraction) {
                Answer newAnswer = new Answer(a.id(), newText, a.isAnswer());
                service.updateAnswer(newAnswer);
            }
        };
    }

    private CheckBox.Listener answerChangeListener(Answer a) {
        return (boolean change) -> {
            service.updateAnswer(new Answer(a.id(), a.text(), change));
        };
    }

    private TextBox.TextChangeListener textChangeListener(int questionId) {
        return (String newText, boolean changedByUserInteraction) -> {
            if (changedByUserInteraction) {
                service.updateQuestion(new Question(questionId, newText));
            }
        };
    }
}

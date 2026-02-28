package ui.windows;

import backend.services.AnswerService;
import backend.services.QuestionService;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import models.Answer;
import models.Question;
import ui.UIController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class AnswerComponent {
    public Button closeButton = new Button("X");
    public ActionListBox alb = new ActionListBox();
    public Label answerLabel = new Label("Answer Option: ");
    public TextBox answerText = new TextBox();
    public Label isAnswerLabel = new Label("Is answer?");
    public CheckBox isAnswerCheckbox = new CheckBox();
    public Panel panel = new Panel().setLayoutManager(new GridLayout(5));
    public ArrayList<AnswerComponent> components;

    public AnswerComponent(ArrayList<AnswerComponent> componentsList) {
        this.components = componentsList;
        this.alb.addItem("x", () -> {
            this.panel.removeComponent(this.answerLabel);
            this.panel.removeComponent(this.answerText);
            this.panel.removeComponent(this.isAnswerLabel);
            this.panel.removeComponent(this.isAnswerCheckbox);
            this.panel.removeComponent(this.closeButton);
            this.panel.removeComponent(this.alb);
            this.components.remove(this);
        });

        this.panel.addComponent(this.alb)
                .addComponent(this.answerLabel)
                .addComponent(this.answerText)
                .addComponent(this.isAnswerLabel)
                .addComponent(this.isAnswerCheckbox);

        this.components.add(this);
    }
}

public class AddNewQuestionWindow extends BasicWindow {

    private final UIController ui;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public AddNewQuestionWindow(UIController ui, QuestionService questionService, AnswerService answerService) {
        super("Add New Question");
        this.ui = ui;
        this.questionService = questionService;
        this.answerService = answerService;
        setHints(List.of(Hint.CENTERED, Hint.EXPANDED, Hint.NO_POST_RENDERING));
        setComponent(build());
    }

    private Component build() {
        Panel mainPanel = new Panel()
                .setLayoutManager(new LinearLayout());
        TextBox questionInput = new TextBox().setPreferredSize(new TerminalSize(50, 1));
        Panel questionPanel = new Panel()
                .setLayoutManager(new GridLayout(2))
                .addComponent(new Label("Question: "))
                .addComponent(questionInput);

        Panel answerContainer = new Panel().setLayoutManager(new LinearLayout());
        ArrayList<AnswerComponent> answerInputs = new ArrayList<>();
        mainPanel.addComponent(questionPanel)
                .addComponent(new Button("+ Add Answer Option",
                        () -> answerContainer.addComponent(new AnswerComponent(answerInputs).panel)));

        mainPanel.addComponent(answerContainer);
        mainPanel.addComponent(new Panel().setLayoutManager(new GridLayout(2))
                .addComponent(new Button("Back", () -> ui.closeWindow(this)))
                .addComponent(new Button("Save", saveAction(() -> questionInput.getText(), answerInputs)))
        );

        return mainPanel;
    }

    private Runnable saveAction(Callable<String> getQuestionText, List<AnswerComponent> answerInputs) {
        return () ->
        {
            long questionID = 0;
            try {
                questionID = questionService.addQuestion(new Question(0, getQuestionText.call()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            List<Answer> a = answerInputs.stream().map(x -> new Answer(0, x.answerText.getText(),
                    x.isAnswerCheckbox.isChecked())).toList();
            answerService.addAnswers(a, questionID);
        };
    }
}

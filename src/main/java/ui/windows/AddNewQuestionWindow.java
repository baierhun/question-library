package ui.windows;

import backend.services.AnswerService;
import backend.services.QuestionService;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import ui.UIController;

import java.util.List;

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
        Panel questionPanel = new Panel()
                .setLayoutManager(new GridLayout(2))
                .addComponent(new Label("Question: "))
                .addComponent(new TextBox().setPreferredSize(new TerminalSize(50, 1)));

        Panel answerPanel = new Panel().setLayoutManager(new GridLayout(6));
        mainPanel.addComponent(questionPanel)
                .addComponent(new Button("+ Add Answer Option", () -> addAnswer(answerPanel)));

        mainPanel.addComponent(answerPanel);
        mainPanel.addComponent(new Panel().setLayoutManager(new GridLayout(2))
                .addComponent(new Button("Back", () -> ui.closeWindow(this)))
                .addComponent(new Button("Save", () -> System.out.println(mainPanel.getChildCount())))
        );

        return mainPanel;
    }

    private void addAnswer(Panel panel) {
        Button b = new Button("X");
        Label l = new Label("Answer Option: ");
        TextBox t = new TextBox();
        b.addListener((Button thisButton) -> {
            panel.removeComponent(l);
            panel.removeComponent(t);
            panel.removeComponent(thisButton);
        });

        panel.addComponent(b);
        panel.addComponent(l);
        panel.addComponent(t);
    }
}

package ui.windows;

import com.googlecode.lanterna.gui2.*;
import ui.UIController;

import java.util.List;

public class MainWindow extends BasicWindow {

    private final UIController ui;

    public MainWindow(UIController ui, String title) {
        super(title);
        this.ui = ui;
        setHints(List.of(Window.Hint.CENTERED, Hint.EXPANDED, Hint.NO_POST_RENDERING));
        setComponent(build());
    }

    public MainWindow(UIController ui) {
        this(ui, "Main Menu");
    }

    private record MenuItem(String name, Runnable func) {
    }

    private Component build() {
        Panel panel = new Panel();
        panel.setLayoutManager(
                new LinearLayout(Direction.VERTICAL)
        );

        MenuItem[] menu = {
                new MenuItem("Search Questions by Tag", this::NoOp),
                new MenuItem("Add New Question", this::NoOp),
                new MenuItem("View All Questions", ui::showAllQuestionsPage),
                new MenuItem("Exit", ui::closeApp)
        };

        for (MenuItem mi : menu) {
            panel.addComponent(new Button(mi.name, mi.func));
        }

        return panel;
    }

    private void NoOp() {
    }
}

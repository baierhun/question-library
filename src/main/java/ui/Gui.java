package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

/*
Manages Windows
 */
public class Gui {
    private final Screen screen;
    private MultiWindowTextGUI gui;

    public Gui() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
    }

    public void start() throws IOException {
        screen.startScreen();
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }

    public void show(Window window) {
        gui.addWindowAndWait(window);
    }

    public void close(Window window) throws IOException {
        window.close();
//        return gui.removeWindow(window);
    }

    public void close() {
        try {
            screen.close();
        } catch (IOException e) {
            System.err.println("Error closing screen");
            throw new RuntimeException(e);
        }
    }
}

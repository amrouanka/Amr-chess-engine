import userInterface.UserInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Amr Chess Engine");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(735, 755);
        f.setVisible(true);
    }
}

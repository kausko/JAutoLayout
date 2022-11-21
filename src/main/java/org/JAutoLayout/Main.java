package org.JAutoLayout;

import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.VFLUtils.Parser;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Cassowary Constraint Solver");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Close the application once the close button is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1500, 100));

        MainUI mainUI = new MainUI(frame);
        frame.add(mainUI.CenterComponent(), BorderLayout.CENTER);
        //frame.add(new JLabel("we have a very long text just to see how the layout is working. it has no meaning, so don't bother reading the text. it's absolutely meaningless."), BorderLayout.NORTH);
        frame.add(mainUI.WestComponent(), BorderLayout.WEST);

        frame.pack();
        frame.show();
        frame.setVisible(true);
        //

        frame.setLayout(new BorderLayout());
    }
}
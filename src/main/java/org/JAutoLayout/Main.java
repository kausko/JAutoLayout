package org.JAutoLayout;

import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.VFLUtils.Parser;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Cassowary Constraint Solver");
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Close the application once the close button is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1500, 100));

        MainUI mainUI = new MainUI(frame);
        frame.add(mainUI.NorthComponent(), BorderLayout.NORTH);
        frame.add(mainUI.CenterComponent(), BorderLayout.CENTER);
        frame.add(mainUI.WestComponent(), BorderLayout.WEST);

        frame.pack();
        frame.show();
        frame.setVisible(true);
        //


    }
}
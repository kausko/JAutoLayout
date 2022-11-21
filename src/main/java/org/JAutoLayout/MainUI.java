package org.JAutoLayout;

import org.JAutoLayout.AutoLayout.AutoLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUI {
    public static JPanel westPanel = new JPanel();
    public static JPanel centerPanel = new JPanel();
    public static JFrame mainFrame ;

    public static JTextArea visual_parser_string = new JTextArea(2, 30);
    public static List<String> constraints = new ArrayList<>();

    public MainUI(JFrame frame){
        mainFrame = frame;
    }
    public static JPanel CenterComponent()
    {
        centerPanel.setLayout(new AutoLayout(constraints));
        centerPanel.setBackground(Color.PINK);
        System.out.println("center panel details" + centerPanel.getWidth() +  centerPanel.getHeight());
        return centerPanel;
    }

    public static JPanel WestComponent()
    {

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        //JScrollPane scroll =new JScrollPane(westPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        visual_parser_string.setPreferredSize(new Dimension(200, 800));
        visual_parser_string.setEnabled(true);
        JButton addConstraint = new JButton("Add Constraint");
        addConstraint.addActionListener(e -> onAddConstraintButtonClick());
        westPanel.add(addConstraint);
        westPanel.add(visual_parser_string);


        return westPanel;
    }
    public static void onAddConstraintButtonClick()
    {

        constraints = Arrays.stream(visual_parser_string.getText().split("\\R")).toList();
        centerPanel.setLayout(new AutoLayout(constraints));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}

package org.JAutoLayout;

import org.JAutoLayout.AutoLayout.AutoLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    public static JPanel westPanel = new JPanel();
    public static JPanel centerPanel = new JPanel();
    public static JFrame mainFrame ;

    public MainUI(JFrame frame){
        mainFrame = frame;
    }
    public static JPanel CenterComponent()
    {
        //JPanel panel = new JPanel();
        centerPanel.setLayout(new AutoLayout());
        centerPanel.add(new JLabel("hello"));
        centerPanel.add(new JLabel("hello"));
        return centerPanel;
    }

    public static JPanel WestComponent()
    {

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        //JScrollPane scroll =new JScrollPane(westPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JButton addJButton = new JButton("Add A Button");
        addJButton.addActionListener(e -> onButtonClick(addJButton));
        JButton addJTextField = new JButton("Add A Text Field");
        addJTextField.addActionListener(e -> onButtonClick(addJTextField));
        westPanel.add(addJButton);
        westPanel.add(addJTextField);

        JTextField visual_parser_string = new JTextField();
        visual_parser_string.setPreferredSize(new Dimension(200, 200));
        westPanel.add(visual_parser_string);
        JButton addConstraint = new JButton("Add Constraint");
        addConstraint.addActionListener(e -> onAddConstraintButtonClick());
        westPanel.add(addConstraint);

        return westPanel;
    }
    public static void onAddConstraintButtonClick()
    {
        //mainFrame.remove(westPanel);
        for(int i = 0; i <=5; i++)
        {
            centerPanel.add(new Button("Button"));
        }
        mainFrame.add(westPanel, BorderLayout.WEST);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    public static void onButtonClick(JButton button){
        JPanel addContent = new JPanel();
        addContent.setLayout(new FlowLayout());
        centerPanel.setLayout(new AutoLayout());
        if(button.getText().contains("Button"))
        {
            addContent.add(new JLabel("button"));


        }
        if(button.getText().contains("Text Field"))
        {
            addContent.add(new JLabel("Text Field"));
        }
        addContent.add(new JTextField("give variable name"));
        westPanel.add(addContent);
        mainFrame.revalidate();
        mainFrame.repaint();

    }
}

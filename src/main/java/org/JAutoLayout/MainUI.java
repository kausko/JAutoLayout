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
    public static JScrollPane CenterComponent()
    {
        //JPanel panel = new JPanel();
        centerPanel.setLayout(new AutoLayout());
        JPanel panel1 = new JPanel();
        //panel1.setSize(new Dimension(100, 100));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setBackground(Color.pink);
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setBackground(Color.pink);
        centerPanel.add(panel1);
        centerPanel.add(panel2);
        JScrollPane scroll =new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        return scroll;
    }

    public static JPanel WestComponent()
    {

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        //JScrollPane scroll =new JScrollPane(westPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JTextField visual_parser_string = new JTextField();
        visual_parser_string.setPreferredSize(new Dimension(200, 800));
        westPanel.add(visual_parser_string);
        JButton addConstraint = new JButton("Add Constraint");
        addConstraint.addActionListener(e -> onAddConstraintButtonClick());
        westPanel.add(addConstraint);

        return westPanel;
    }
    public static void onAddConstraintButtonClick()
    {
        for(int i = 0; i <=5; i++)
        {
            JPanel panel1 = new JPanel();
            panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panel1.setBackground(Color.pink);
            centerPanel.add(panel1);
        }
        //mainFrame.add(westPanel, BorderLayout.WEST);
        //mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}

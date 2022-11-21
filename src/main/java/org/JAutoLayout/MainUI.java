package org.JAutoLayout;

import org.JAutoLayout.AutoLayout.AutoLayout;
import org.rekex.helper.anno.Str;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.SwingConstants.CENTER;

public class MainUI {
    public static JPanel westPanel = new JPanel();
    public static JPanel centerPanel = new JPanel();
    public static JFrame mainFrame ;
    private static JPanel constraintPanel = new JPanel();

    public static JTextArea visual_parser_string = new JTextArea(2, 25);
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
    private static JScrollPane ConstraintComponent()
    {
        String[] choices = { "Button","Text Area", "Text Field"};

        final JComboBox<String> cb = new JComboBox<String>(choices);
        cb.addActionListener(e -> OnComboBoxButtonClick(cb));
        constraintPanel.setLayout(new BoxLayout(constraintPanel, BoxLayout.Y_AXIS));
        constraintPanel.add(cb);

        JScrollPane scroll =new JScrollPane(constraintPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        return scroll;

    }

    private static void OnComboBoxButtonClick(JComboBox<String> cb)
    {
        JPanel addConstraint = new JPanel();
        addConstraint.setLayout(new FlowLayout());
        String cbText = cb.getSelectedItem().toString();
        JButton removeConstraint = new JButton("Remove Constraint");
        removeConstraint.addActionListener(e -> OnRemoveConstraintButtonClick(removeConstraint));

        addConstraint.add(new JLabel(cbText));
        addConstraint.add(new JTextField(20));
        addConstraint.add(removeConstraint);

        constraintPanel.add(addConstraint);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    private static void OnRemoveConstraintButtonClick(JButton button){

        var parentComponent = button.getParent();
        parentComponent.removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();

    }
    public static JPanel WestComponent()
    {
        //westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setLayout(new GridLayout(0,1, 8, 8));
        westPanel.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        westPanel.setBorder(BorderFactory.createMatteBorder(
                1, 5, 1, 5, Color.green));


        visual_parser_string.setEnabled(true);
        JButton addConstraint = new JButton("Add Constraint");
        addConstraint.addActionListener(e -> onAddConstraintButtonClick());

        westPanel.add(ConstraintComponent());
        westPanel.add(visual_parser_string);
        westPanel.add(addConstraint);



        return westPanel;
    }

    public static JPanel NorthComponent()
    {
        JPanel northPanel = new JPanel();
        JLabel heading = new JLabel("JAutoLayout", CENTER);
        heading.setFont(new Font("Serif", Font.PLAIN, 30));
        northPanel.add(heading);
        return northPanel;
    }
    public static void onAddConstraintButtonClick()
    {

        constraints = Arrays.stream(visual_parser_string.getText().split("\\R")).toList();
        centerPanel.setLayout(new AutoLayout(constraints));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}

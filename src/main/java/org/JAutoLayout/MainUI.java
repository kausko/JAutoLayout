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

    public static JTextField visual_parser_string = new JTextField();

    public MainUI(JFrame frame){
        mainFrame = frame;
    }
    public static JPanel CenterComponent()
    {
        centerPanel.setLayout(new AutoLayout());

        centerPanel.setBackground(Color.PINK);

        return centerPanel;
    }

    public static JPanel WestComponent()
    {

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        //JScrollPane scroll =new JScrollPane(westPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        visual_parser_string.setPreferredSize(new Dimension(200, 800));
        westPanel.add(visual_parser_string);
        JButton addConstraint = new JButton("Add Constraint");
        addConstraint.addActionListener(e -> onAddConstraintButtonClick());
        westPanel.add(addConstraint);

        return westPanel;
    }
    public static void onAddConstraintButtonClick()
    {
//        String tfString = visual_parser_string.getText();
//        List<String> matchList = new ArrayList<String>();
//        Pattern regex = Pattern.compile("\\((.*?)\\)");
//        Matcher regexMatcher = regex.matcher(tfString);
//
//        while (regexMatcher.find()) {//Finds Matching Pattern in String
//            matchList.add(regexMatcher.group(1));//Fetching Group from String
//        }
//        for(int i = 0; i<= matchList.size(); i++)
//        {
//           var something = matchList.get(i).split(":");
//           System.out.println("something variable values " + something[1] + something[2] + something[3] + something[4]);
//           JPanel panel = new JPanel();
//           panel.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(something[2].substring(1)),  Integer.parseInt(something[3].substring(1)), Integer.parseInt(something[1].substring(1)),  Integer.parseInt(something[3].substring(1))));
//           panel.setLocation(new Point(Integer.parseInt(something[0].substring(1)), Integer.parseInt(something[1].substring(1))));
//           centerPanel.add(panel);
//        }

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.blue);
        panel.setBounds(200, 200, 30, 30);
        mainFrame.getPreferredSize();
        centerPanel.add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

}

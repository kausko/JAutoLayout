package org.JAutoLayout;

import org.JAutoLayout.AutoLayout.AutoLayout;
import org.JAutoLayout.AutoLayout.DemoComponents;
import org.rekex.helper.anno.Str;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.SwingConstants.CENTER;

public class MainUI {
    public static JPanel centerPanel = new JPanel();
    public static JFrame mainFrame ;
    private static JPanel constraintPanel = new JPanel();
    public static List<String> constraints = new ArrayList<>();

    private static JComboBox comboBox;
    private static JButton addButton;
    private static JButton applyButton;
    private static JTextArea textArea;
    private static JPanel displayPanel;

    private static JPanel componentsList;
    private static DefaultTableModel componentsListModel;

    private static HashMap<String, String> viewNames = new HashMap<String, String>();

    public MainUI(JFrame frame) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        mainFrame = frame;

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        comboBox = new JComboBox<>(DemoComponents.components);
        System.out.println(DemoComponents.components.toString());
        addButton = new JButton("Add");
        applyButton = new JButton("Apply constraints");
        textArea = new JTextArea();
        displayPanel = new JPanel();
        componentsListModel = new DefaultTableModel();
        componentsListModel.addColumn("Components");
        componentsList = new JPanel();
        setupListeners();
    }

    private void setupListeners() {
        componentsList.setLayout(new BoxLayout(componentsList,  BoxLayout.Y_AXIS));
        addButton.addActionListener(btnEvent -> {
            OnAddButtonClick(comboBox);
        });
    }
    public static JPanel CenterComponent()
    {
        centerPanel.setLayout(new AutoLayout(constraints, viewNames));
        centerPanel.setBackground(Color.PINK);
        System.out.println("center panel details" + centerPanel.getWidth() +  centerPanel.getHeight());
        return centerPanel;
    }

    private static void OnAddButtonClick(JComboBox<String> cb)
    {
        JPanel addConstraint = new JPanel();
        addConstraint.setLayout(new FlowLayout());
        String cbText = cb.getSelectedItem().toString();
        JButton removeConstraint = new JButton("Remove Constraint");
        removeConstraint.addActionListener(e -> OnRemoveConstraintButtonClick(removeConstraint));

        addConstraint.add(new JLabel(cbText));
        addConstraint.add(new JTextField(15));
        addConstraint.add(removeConstraint);

        componentsList.add(addConstraint);
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
        var addComboPanel = new JPanel();
        addComboPanel.setBorder(BorderFactory.createTitledBorder("Select Component"));
        addComboPanel.add(comboBox);
        addComboPanel.add(addButton);

        var componentsScrollPane = new JScrollPane(componentsList);
        componentsScrollPane.setBorder(BorderFactory.createTitledBorder("Edit Component text"));

        var textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setBackground(Color.WHITE);
        textAreaScrollPane.setBorder(BorderFactory.createTitledBorder("Add Constraints"));

        var horizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, componentsScrollPane, textAreaScrollPane);
        horizontalSplitPane.setResizeWeight(0);

        var controlPanel = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.add(addComboPanel, gbc);
        gbc.weighty = 1;
        gbc.gridy = 1;
        controlPanel.add(horizontalSplitPane, gbc);
        gbc.weighty = 0;
        gbc.gridy = 2;
        controlPanel.add(applyButton, gbc);
        applyButton.addActionListener(e -> onAddConstraintButtonClick());



        return controlPanel;
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
        for(int i = 0; i < componentsList.getComponentCount(); i++)
        {
            JPanel ncomp = (JPanel) componentsList.getComponent(i);
            String compType = ((JLabel)ncomp.getComponent(0)).getText();
            String variableName = ((JTextField)ncomp.getComponent(1)).getText();
            viewNames.put(variableName, compType);
        }

        constraints = Arrays.stream(textArea.getText().split("\\R")).toList();
        centerPanel.setLayout(new AutoLayout(constraints, viewNames));
        mainFrame.revalidate();
        mainFrame.repaint();
    }



}

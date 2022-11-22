package org.JAutoLayout.AutoLayout;

import org.rekex.helper.anno.Str;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DemoComponents {

    public static String textField = "JTextField";
    public static String checkBox = "JCheckBox";
    public static String radioButton = "JRadioButton";
    public static String menuItem = "JMenuItem";
    public static String passwordField = "JPasswordField";
    public static String toggleButton = "JToggleButton";
    public static String textArea = "JTextArea";
    public static String label = "JLabel";
    public static String button = "JButton";


    public static Map<String, Component> componentMap = Map.of(
            textField, new JTextField(),
            checkBox, new JCheckBox(),
            radioButton, new JRadioButton(),
            menuItem, new JMenuItem(),
            passwordField, new JPasswordField(),
            toggleButton, new JToggleButton(),
            textArea, new JTextArea(),
            label, new JLabel(),
            button, new JButton()
    );

    public static String[] components = {textField,checkBox, radioButton, menuItem, passwordField, toggleButton, textArea, label, button };


//    public static JPopupMenu popupMenu = new JPopupMenu("");
//    public static JInternalFrame internalFrame = new JInternalFrame("");
//    public static JFormattedTextField formattedTextField = new JFormattedTextField("");
//    public static JToolBar toolBar = new JToolBar("");
//    public static JMenu menu = new JMenu("");
//    public static JComboBox<String> comboBox = new JComboBox<>();
//    public static JSlider slider = new JSlider();
//    public static JSpinner spinner = new JSpinner();
//    public static JProgressBar progressBar = new JProgressBar();
//    public static JList<String> list = new JList<>();
//    public static JTable table = new JTable();
//    public static JTree tree = new JTree();
//    public static JTabbedPane tabbedPane = new JTabbedPane();
//    public static JMenuBar menuBar = new JMenuBar();
//    public static JDesktopPane desktopPane = new JDesktopPane();
//    public static JColorChooser colorChooser = new JColorChooser();
//    public static JFileChooser fileChooser = new JFileChooser();
//    public static JEditorPane editorPane = new JEditorPane();
//    public static JSeparator separator = new JSeparator();
//    public static JTextPane textPane = new JTextPane();
//    public static JPanel panel = new JPanel();
//    public static JScrollPane scrollPane = new JScrollPane();
//    public static JSplitPane splitPane = new JSplitPane();

}

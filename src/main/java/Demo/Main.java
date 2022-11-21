package Demo;

import org.JAutoLayout.AutoLayout.DemoComponents;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Main {

    private final JComboBox<Field> comboBox;
    private final JButton addButton;
    private final JButton applyButton;
    private final JTextArea textArea;
    private final JPanel displayPanel;

    private final JTable componentsList;
    private final DefaultTableModel componentsListModel;

    public Main() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        comboBox = new JComboBox<>();
        addButton = new JButton("Add");
        applyButton = new JButton("Apply constraints");
        textArea = new JTextArea();
        displayPanel = new JPanel();
        componentsListModel = new DefaultTableModel();
        componentsListModel.addColumn("Components");
        componentsList = new JTable(componentsListModel);
        setupListeners();
    }

    private void setupListeners() {

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(((Field) value).getType().getSimpleName());
                return this;
            }
        });

        componentsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                var components =  displayPanel.getComponents();
                for (Component c : components) {
                    var co = (JComponent) c;
                    co.setBorder(null);
                }
                int row = componentsList.getSelectedRow();
                if (row == -1) return;
                var comp = (JComponent) displayPanel.getComponent(row);
                comp.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        });

        componentsListModel.addTableModelListener(e -> {

            try {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (e.getType() != TableModelEvent.UPDATE)
                    return;
                var comp = (JComponent) displayPanel.getComponent(row);

                comp
                        .getClass()
                        .getMethod("setText", String.class)
                        .invoke(comp, (String) componentsListModel.getValueAt(row, column));

                comp.setBorder(null);
                displayPanel.revalidate();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

        addButton.addActionListener(btnEvent -> {
            Field selectedItem = (Field) comboBox.getSelectedItem();
            try {
                assert selectedItem != null;
                Class<?> type = selectedItem.getType();
                Object comp = type
                        .getConstructor(String.class)
                        .newInstance(type.getSimpleName());

                DefaultTableModel model = (DefaultTableModel) componentsList.getModel();
                model.addRow(new Object[]{type.getSimpleName()});
                displayPanel.add((Component) comp);
                displayPanel.revalidate();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void run() {

        Arrays.stream(DemoComponents.class.getFields()).forEach(comboBox::addItem);

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


        var verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, displayPanel);
        verticalSplitPane.setResizeWeight(0.15);

        var frame = new JFrame("JAutoLayout Demo");

        frame.add(verticalSplitPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Main main = new Main();
        SwingUtilities.invokeLater(main::run);
    }
}

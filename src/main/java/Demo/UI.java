package Demo;

import org.JAutoLayout.JAutoLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.SwingConstants.CENTER;

public class UI {
    private final JComboBox<Field> comboBox;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton applyButton;
    private final JTextArea textArea;
    private final JPanel displayPanel;

    private final JTable componentsList;
    private final DefaultTableModel componentsListModel;

    private final JAutoLayout layout;

    public UI() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        comboBox = new JComboBox<>();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete selected component");
        applyButton = new JButton("Apply");
        textArea = new JTextArea();
        displayPanel = new JPanel();
        componentsListModel = new DefaultTableModel();
        componentsListModel.addColumn("Type");
        componentsListModel.addColumn("Name");
        componentsListModel.addColumn("Comp");
        componentsList = new JTable(componentsListModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        var cm = componentsList.getColumnModel().getColumn(2);
        cm.setWidth(0);
        cm.setMinWidth(0);
        cm.setMaxWidth(0);
        layout = new JAutoLayout();
        setup();
    }

    private void setup() {

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(((Field) value).getType().getSimpleName());
                return this;
            }
        });

        addButton.addActionListener(this::onAddButtonClick);
        deleteButton.addActionListener(this::onDeleteButtonClick);
        applyButton.addActionListener(e -> {
            try {
                onApplyButtonClick(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void onAddButtonClick(ActionEvent actionEvent) {
            Field selectedItem = (Field) comboBox.getSelectedItem();
            try {
                assert selectedItem != null;
                Class<?> type = selectedItem.getType();
                Object comp = type
                        .getConstructor()
                        .newInstance();

                DefaultTableModel model = (DefaultTableModel) componentsList.getModel();
                var compType = type.getSimpleName();
                var compName = compType + model.getRowCount();
                model.addRow(new Object[]{compType, compName, comp});
//                viewNames.put(compName, (Component) comp);
//                displayPanel.add((Component) comp);
//                displayPanel.revalidate();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
    }

    private void onDeleteButtonClick(ActionEvent actionEvent) {
        int row = componentsList.getSelectedRow();
        if (row == -1) return;
        componentsListModel.removeRow(row);
    }

    private void onApplyButtonClick(ActionEvent actionEvent) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var constraints = Arrays.stream(textArea.getText().split("\\R")).toList();
        Map<String, Component> viewNames = new HashMap<>();
        for (int i = 0; i < componentsListModel.getRowCount(); i++) {
            var compName = (String) componentsListModel.getValueAt(i, 1);
            var comp = (Component) componentsListModel.getValueAt(i, 2);

            // invoke setText on comp
            comp.getClass().getMethod("setText", String.class).invoke(comp, compName);
            viewNames.put(compName, comp);
        }
        layout.setConstraintsAndViews(constraints, viewNames);
        displayPanel.setLayout(layout);
        displayPanel.removeAll();
        for (var views: viewNames.entrySet()) {
            displayPanel.add(views.getValue());
        }
        displayPanel.revalidate();
    }

    public JPanel centerComponent() {
        displayPanel.setLayout(layout);
        displayPanel.setBackground(Color.PINK);
        return displayPanel;
    }

    public JPanel northComponent() {
        JPanel northPanel = new JPanel();
        JLabel heading = new JLabel("JAutoLayout", CENTER);
        heading.setFont(new Font("Serif", Font.PLAIN, 30));
        northPanel.add(heading);
        return northPanel;
    }

    public JPanel westComponent() {
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
        gbc.gridy++;
        controlPanel.add(deleteButton, gbc);
        gbc.weighty = 1;
        gbc.gridy++;
        controlPanel.add(horizontalSplitPane, gbc);
        gbc.weighty = 0;
        gbc.gridy++;
        controlPanel.add(applyButton, gbc);

        return controlPanel;
    }
}

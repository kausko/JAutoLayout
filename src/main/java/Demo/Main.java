package Demo;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("JAutoLayout Demo");
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UI ui = new UI();
        var verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ui.westComponent(), ui.centerComponent());
        verticalSplitPane.setResizeWeight(0.15);
        frame.add(verticalSplitPane, BorderLayout.CENTER);
        frame.add(ui.northComponent(), BorderLayout.NORTH);
        frame.setVisible(true);
    }
}

package org.JAutoLayout.AutoLayout;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


public class AutoLayout implements LayoutManager {
    private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

    public AutoLayout() {
        this(5);
    }

    public AutoLayout(int v) {
        vgap = v;
    }

    /* Required by LayoutManager. */
    public void addLayoutComponent(String name, Component comp) {
    }

    /* Required by LayoutManager. */
    public void removeLayoutComponent(Component comp) {
    }

    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d = null;

        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();

                if (i > 0) {
                    preferredWidth += d.width/2;
                    preferredHeight += vgap;
                } else {
                    preferredWidth = d.width;
                }
                preferredHeight += d.height;

                minWidth = Math.max(c.getMinimumSize().width,
                        minWidth);
                minHeight = preferredHeight;
            }
        }
    }


    /* Required by LayoutManager. */
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();

        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = preferredWidth
                + insets.left + insets.right;
        dim.height = preferredHeight
                + insets.top + insets.bottom;

        sizeUnknown = false;

        return dim;
    }

    /* Required by LayoutManager. */
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = minWidth
                + insets.left + insets.right;
        dim.height = minHeight
                + insets.top + insets.bottom;

        sizeUnknown = false;

        return dim;
    }

    /* Required by LayoutManager. */
    /*
     * This is called when the panel is first displayed,
     * and every time its size changes.
     * Note: You CAN'T assume preferredLayoutSize or
     * minimumLayoutSize will be called -- in the case
     * of applets, at least, they probably won't be.
     */
    public void layoutContainer(Container parent) {
        try {
            Map<String, Map<String, String>> jsonData = ReadJsonData();
            System.out.println("size of the constraint" + jsonData.entrySet().size());
            System.out.println(parent);

            for (var entry: jsonData.entrySet()
            ) {
                int x = (int)Double.parseDouble(entry.getValue().get("top")) + (parent.getX());
                int y = (int)Double.parseDouble(entry.getValue().get("left")) + (parent.getY());
                int width = (int)Double.parseDouble(entry.getValue().get("width")) ;
                int height = (int)Double.parseDouble(entry.getValue().get("height")) ;
                System.out.println("object printed");
                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                panel.setBackground(Color.blue);
                panel.setBounds(x,y,width ,height );
                parent.add(panel);
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Iterate over employee array

//        for(int i = 1; i<= 3; i++)
//        {
//            JPanel panel = new JPanel();
//            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//            panel.setBackground(Color.blue);
//            panel.setBounds(200 *i, 200*i, 30*i, 30*i);
//            parent.add(panel);
//        }

    }
    public Map ReadJsonData() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Map<String, String>> output =
                objectMapper.readValue(
                    new File("SampleParserData.json"), new TypeReference<Map<String, Map<String, String>>>() {});

        return output;
    }


    public String toString() {
        String str = "";
        return getClass().getName() + "[vgap=" + vgap + str + "]";
    }
}

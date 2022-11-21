package org.JAutoLayout.AutoLayout;

import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.Toolkit.Variable;
import org.JAutoLayout.VFLUtils.Parser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.rekex.helper.anno.Str;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.util.*;
import java.util.List;


public class AutoLayout implements LayoutManager {
    private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

    private List<String> userConstraints = null;

    public AutoLayout() {
        this(null);
    }

    public AutoLayout(List<String> constraint) {
        userConstraints = constraint;
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
                var parser = new Parser();
                var res = parser.parse(userConstraints);
                int x = 0, y = 0, width = 0, height =0;
                Solver solver = new Solver();
                var map = solver.solve(res, 300, 300);

                for (var entry: map.entrySet()) {

                    if(!entry.getKey().equals("container"))
                    {

                        HashMap<String, Variable> values = entry.getValue();
                        for(var constraint : values.entrySet()){
                            if(constraint.getKey().equals("top"))
                            {
                                y = (int)constraint.getValue().getValue();
                            }
                            if(constraint.getKey().equals("left"))
                            {
                                x = (int) constraint.getValue().getValue();
                            }
                            if(constraint.getKey().equals("width"))
                            {
                                width = (int) constraint.getValue().getValue();
                            }
                            if(constraint.getKey().equals("height"))
                            {
                                height = (int) constraint.getValue().getValue();
                            }

                            System.out.println("coordinate values "+ x + " " + y+ " " + width + " " + height);

                            JPanel panel = new JPanel();
                            panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
                            panel.setBackground(Color.blue);
                            panel.setBounds( x, y, width , height);
                            parent.add(panel);
                        }


                    }
                };


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

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

package org.JAutoLayout;

import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.VFLUtils.Parser;

import java.awt.*;

import java.util.*;
import java.util.List;


public class JAutoLayout implements LayoutManager {
    private int vgap;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

    private List<String> constraints;
    private Map<String, Component> viewNames;

    public JAutoLayout() {
        this(new ArrayList<>(), new HashMap<>());
    }

    public JAutoLayout(List<String> constraints, Map<String, Component> viewNames) {
        this.constraints = constraints;
        this.viewNames = viewNames;
    }

    public void setConstraintsAndViews(List<String> constraints, Map<String, Component> viewNames) {
        this.constraints = constraints;
        this.viewNames = viewNames;
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
            var solver = new Solver();
            var res = parser.parse(constraints);

            // TODO: add the parent's width and height to the solver
            var map = solver.solve(res, parent.getHeight(), parent.getWidth());

            // TODO: REMOVE: JSONified output of the map
//                System.out.println("{");
//                map.forEach((k, v) -> {
//                    System.out.println("\"" + k + "\": {");
//                    v.forEach((k1, v1) -> {
//                        System.out.println("\"" + k1 + "\": " + v1.getValue() + ",");
//                    });
//                    System.out.println("},");
//                });
//                System.out.println("}");

            map.remove("container");
            map.forEach((k, v) -> {
                var component = viewNames.get(k);
                // TODO: get the component by its name

//                    component.setBackground(new Color(77, 230, 220));
//                    System.out.println("key details" + k);
                component.setBounds(
                        (int) v.get("left").getValue(),
                        (int) v.get("top").getValue(),
                        (int) v.get("width").getValue(),
                        (int) v.get("height").getValue()
                );
//
//                    System.out.println(k + ": " + component.getBounds());
//                    parent.add(component);
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String toString() {
        String str = "";
        return getClass().getName() + "[vgap=" + vgap + str + "]";
    }
}

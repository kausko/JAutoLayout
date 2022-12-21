package org.JAutoLayout;

import no.birkett.kiwi.Variable;
import org.JAutoLayout.SolverUtils.Solver;
import org.JAutoLayout.VFL.Parser;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JAutoLayout implements LayoutManager {

    private int vgap;
    private int spacing = 8;
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;
    private boolean sizeUnknown = true;

    private java.util.List<String> constraints;
    private Map<String, Component> viewNames;

    public JAutoLayout() {
        this(8);
    }

    public JAutoLayout(int spacing) { this(new ArrayList<>(), new HashMap<>(), spacing); }

    public JAutoLayout(java.util.List<String> constraints, Map<String, Component> viewNames) {
        this(constraints, viewNames, 8);
    }

    public JAutoLayout(java.util.List<String> constraints, Map<String, Component> viewNames, int spacing) {
        setConstraintsAndViews(constraints, viewNames);
        this.spacing = spacing;
    }

    public void setConstraintsAndViews(List<String> constraints, Map<String, Component> viewNames) {
        this.constraints = constraints;
        this.viewNames = viewNames;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
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
            var parser = new Parser(spacing);
            var res = parser.parse(constraints);
            var solver = new Solver(parent.getWidth(), parent.getHeight());
            var map = solver.solve(res);

            map.remove("container");
            map.forEach((k, v) -> {
                var component = viewNames.get(k);
                component.setBounds(
                        (int) v.getOrDefault("left", new Variable(0)).getValue(),
                        (int) v.getOrDefault("top", new Variable(0)).getValue(),
                        (int) v.getOrDefault("width", new Variable(0)).getValue(),
                        (int) v.getOrDefault("height", new Variable(0)).getValue()
                );
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String toString() {
        String str = "";
        return getClass().getName() + "[vgap=" + vgap + str + "]";
    }
}
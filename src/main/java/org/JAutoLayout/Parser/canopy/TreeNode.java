/**
 * This file was generated from canopyVFL.peg
 * See https://canopy.jcoglan.com/ for documentation
 */

package org.JAutoLayout.Parser.canopy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeNode implements Iterable<TreeNode> {
    public String text;
    public int offset;
    public List<TreeNode> elements;

    Map<Label, TreeNode> labelled;

    public TreeNode() {
        this("", -1, new ArrayList<TreeNode>(0));
    }

    public TreeNode(String text, int offset, List<TreeNode> elements) {
        this.text = text;
        this.offset = offset;
        this.elements = elements;
        this.labelled = new EnumMap<Label, TreeNode>(Label.class);
    }

    public TreeNode get(Label key) {
        return labelled.get(key);
    }

    public Iterator<TreeNode> iterator() {
        return elements.iterator();
    }

    public String mapToJsonString(Map<Label, TreeNode> mp) {
        StringBuilder res = new StringBuilder("{");
        for (Map.Entry<Label, TreeNode> entry : mp.entrySet()) {
            res.append('"').append(entry.getKey()).append("\":").append(entry.getValue().toString()).append(", ");
        }
        if (res.length() > 2)
            res = new StringBuilder(res.substring(0, res.length() - 2));
        res.append("}");
        return res.toString();
    }

    @Override
    public String toString() {
        // JSON representation of the tree
        return "{" +
                "\"text\":" + "\"" + text + "\"," +
                "\"offset\":" + offset + "," +
                "\"elements\":" + elements + "," +
                "\"labelled\":" + mapToJsonString(labelled) +
        "}";
    }
}

class TreeNode1 extends TreeNode {
    TreeNode1(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.view, elements.get(2));
    }
}

class TreeNode2 extends TreeNode {
    TreeNode2(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.orientation, elements.get(0));
    }
}

class TreeNode3 extends TreeNode {
    TreeNode3(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.superview, elements.get(0));
        labelled.put(Label.connection, elements.get(1));
    }
}

class TreeNode4 extends TreeNode {
    TreeNode4(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.connection, elements.get(0));
        labelled.put(Label.view, elements.get(1));
    }
}

class TreeNode5 extends TreeNode {
    TreeNode5(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.connection, elements.get(0));
        labelled.put(Label.superview, elements.get(1));
    }
}

class TreeNode6 extends TreeNode {
    TreeNode6(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.viewName, elements.get(1));
    }
}

class TreeNode7 extends TreeNode {
    TreeNode7(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.predicateList, elements.get(1));
    }
}

class TreeNode8 extends TreeNode {
    TreeNode8(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.predicate, elements.get(1));
    }
}

class TreeNode9 extends TreeNode {
    TreeNode9(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.predicate, elements.get(1));
    }
}

class TreeNode10 extends TreeNode {
    TreeNode10(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.objectOfPredicate, elements.get(1));
    }
}

class TreeNode11 extends TreeNode {
    TreeNode11(String text, int offset, List<TreeNode> elements) {
        super(text, offset, elements);
        labelled.put(Label.priority, elements.get(1));
    }
}

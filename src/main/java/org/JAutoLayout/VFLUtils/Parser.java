package org.JAutoLayout.VFLUtils;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<String> constraints;
    private final String defaultGap;
    private final PegParser parser;

    public Parser() {
        this(8);
    }

    public Parser(Integer defaultGap) {
        this.defaultGap = defaultGap.toString();
        this.constraints = new ArrayList<>();
        this.parser = new PegParser();
    }

    public List<String> parse(List<String> vfl) throws Exception {
        for (String s : vfl) {
            var ast = parser.matchFull(s);
            var reducer = new ASTReducer(ast, defaultGap);
            constraints.addAll(reducer.getConstraints());
        }
        return constraints;
    }
}

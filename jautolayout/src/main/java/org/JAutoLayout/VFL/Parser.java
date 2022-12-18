package org.JAutoLayout.VFL;

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
            var parseTree = parser.matchFull(s);
            constraints.addAll(AST.synthesizeConstraints(parseTree, defaultGap));
        }
        return constraints;
    }
}

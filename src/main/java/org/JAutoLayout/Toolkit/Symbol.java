package org.JAutoLayout.Toolkit;


public class Symbol {

    enum Type {
        INVALID,
        EXTERNAL,
        SLACK,
        ERROR,
        DUMMY
    }

    private Type type;

    public Symbol() {
        this(Type.INVALID);
    }

    public Symbol(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}

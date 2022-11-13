package org.JAutoLayout.Parser.rekex;

import org.rekex.helper.anno.Ch;
import org.rekex.helper.anno.Str;
import org.rekex.helper.datatype.Opt;
import org.rekex.spec.Regex;

import java.util.List;

public class Grammar {

    public record VisualFormatString(
            Opt<Orientation> orientation,
            Opt<SuperViewConnection> superViewConnection,
            View view,
            List<ConnectionView> connectionViews,
            Opt<ConnectionSuperView> connectionSuperView
    ) {}

    public record ConnectionView(Connection c, View v) {}

    public record ConnectionSuperView(Connection c, SuperView s) {}

    public record SuperViewConnection(SuperView s, Connection c) {}

    public record Orientation(@Str({"H", "V"}) String orientation) {}

    public record SuperView(@Ch("|") String superView) {}

    public record View(
            @Ch("[") char lBracket,
            ViewName viewName,
            Opt<PredicateListWithParens> predicateListWithParens,
            @Ch("]") char rBracket
    ) {}

    public sealed interface Connection permits ConnectorWrappedPredicateList, Connector, EmptyString {}

    public record ConnectorWrappedPredicateList(
        Connector connector1,
        PredicateList predicateList,
        Connector connector2
    ) implements Connection {}

    public record Connector(@Ch("-") char c) implements Connection {}

    public record EmptyString(@Str("") String s) implements Connection {}

    public sealed interface PredicateList permits SimplePredicate, PredicateListWithParens {}

    public record SimplePredicate(Number n) implements PredicateList {}

    public record PredicateListWithParens (
        @Ch("(") char openParen,
        Predicate predicate,
        List<CommaPredicate> cp,
        @Ch(")") char closeParen
    )  implements PredicateList {}

    public record CommaPredicate(
        @Ch(",") char comma,
        Predicate predicate
    ) {}

    public record Predicate(
            Opt<Relation> rel,
            ObjectOfPredicate oop,
            Opt<StrPriority> atp
    ) {}

    public record Relation(@Str({"==", "<=", ">="}) String rel) {}

    public sealed interface ObjectOfPredicate permits Constant, ViewName {}

    public record StrPriority(@Ch("@") char at, Priority priority) {}

    public record Priority(@Regex("[0-9]+") String digits) {}

    public record Constant(Number n) implements ObjectOfPredicate {}

    public record ViewName(@Regex("[a-zA-Z_][a-zA-Z0-9_]*") String name) implements ObjectOfPredicate {}

    public sealed interface Number permits Integer, Double {}

    public record Integer(@Regex("[0-9]+") String integer) implements Number {}

    public record Double(@Regex("[0-9]+\\.[0-9]+") String dbl) implements Number {}
}

package org.JAutoLayout.VFLUtils;

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

    public record ConnectionView(Connection connection, View view) {}

    public record ConnectionSuperView(Connection connection, SuperView superView) {}

    public record SuperViewConnection(SuperView superView, Connection connection) {}

    public record Orientation(@Str({"H", "V"}) String orientation, @Ch(":") String colon) {}

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

    public record Connector(@Ch("-") char connector) implements Connection {}

    public record EmptyString(@Str("") String emptyString) implements Connection {}

    public sealed interface PredicateList permits Number, PredicateListWithParens {}

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
            Opt<Relation> relation,
            ObjectOfPredicate oop,
            Opt<StrPriority> atp
    ) {}

    public record Relation(@Str({"==", "<=", ">="}) String rel) {}

    public sealed interface ObjectOfPredicate permits Number, ViewName {}

    public record StrPriority(@Ch("@") char at, Priority priority) {}

    public record Priority(@Regex("[0-9]+") String digits) {}

    public record ViewName(@Regex("[a-zA-Z_][a-zA-Z0-9_]*") String name) implements ObjectOfPredicate {}

    public record Number(@Regex("([0-9]+(?:\\.[0-9]*)?)") String number) implements ObjectOfPredicate, PredicateList {}
}

package org.JAutoLayout.VFL;

import org.JAutoLayout.VFL.Grammar.*;

import java.util.ArrayList;
import java.util.List;

public class AST {

    private static VisualFormatString vfl;
    private static List<String> constraints;
    private static String orientation;
    private static String defaultGap;

    private AST () {}

    public static List<String> synthesizeConstraints(VisualFormatString vfl, String defaultGap) {
        AST.vfl = vfl;
        AST.defaultGap = defaultGap;
        AST.orientation = vfl.orientation().caseOf(Orientation::orientation, () -> "H");
        AST.constraints = new ArrayList<>();
        calcInitialConnectingConstraints();
        calcRemainingConnectingConstraints();
        calcViewConstraints();
        return constraints;
    }

    private static void calcInitialConnectingConstraints() {
        var osvc = vfl.superViewConnection();
        var view = vfl.view();
        var newConstraints = osvc.caseOf(
                // SuperView-Connector-View case
                svc -> {
                    var connection = svc.connection();
                    var expr1 = view.viewName().name() + getStartIdentifier();
                    var expr2 = "container" + getStartIdentifier();
                    return computeViewConnectorViewConstraints(expr1, connection, expr2);
                },
                ArrayList<String>::new
        );
        constraints.addAll(newConstraints);
    }

    private static void calcRemainingConnectingConstraints() {
        var connectionViews = vfl.connectionViews();
        var connectionSuperView = vfl.connectionSuperView();

        // View-Connector-SuperView case
        if (connectionViews.isEmpty()) {
            var newConstraints = connectionSuperView.caseOf(
                    csv -> {
                        var connection = csv.connection();
                        var expr1 = "container" + getEndIdentifier();
                        var expr2 = vfl.view().viewName().name() + getEndIdentifier();
                        return computeViewConnectorViewConstraints(expr1, connection, expr2);
                    },
                    ArrayList<String>::new
            );
            constraints.addAll(newConstraints);
            return;
        }

        // RequiredView-Connector-View[0] case
        var connection = connectionViews.get(0).connection();
        var expr1 = connectionViews.get(0).view().viewName().name() + getStartIdentifier();
        var expr2 = vfl.view().viewName().name() + getEndIdentifier();
        var newConstraints = computeViewConnectorViewConstraints(expr1, connection, expr2);

        // View[i]-Connector-View[i+1] case (will be skipped if there is only one view)
        for (int i = 1; i < connectionViews.size(); i++) {
            connection = connectionViews.get(i).connection();
            expr1 = connectionViews.get(i).view().viewName().name() + getStartIdentifier();
            expr2 = connectionViews.get(i - 1).view().viewName().name() + getEndIdentifier();
            newConstraints.addAll(computeViewConnectorViewConstraints(expr1, connection, expr2));
        }

        // View[n]-Connector-SuperView case
        newConstraints.addAll(
                connectionSuperView.caseOf(
                        csv -> {
                            var iconnection = csv.connection();
                            var exprn1 = "container" + getEndIdentifier();
                            var exprn2 = connectionViews.get(connectionViews.size() - 1).view().viewName().name() + getEndIdentifier();
                            return computeViewConnectorViewConstraints(exprn1, iconnection, exprn2);
                        },
                        ArrayList<String>::new
                )
        );
        constraints.addAll(newConstraints);
    }

    private static void calcViewConstraints() {
        var connectionViews = vfl.connectionViews();
        connectionViews.add(new ConnectionView(null, vfl.view()));
        for (var cvs: connectionViews) {
            var view = cvs.view();
            var result = view.predicateListWithParens().caseOf(
                    pl -> {
                        var predicates = pl.cp();
                        predicates.add(new CommaPredicate(',', pl.predicate()));

                        List<String> resultSet = new ArrayList<>();
                        for (var predicate: predicates) {
                            resultSet.add(
                                    view.viewName().name() + getLengthIdentifier()
                                            + predicate.predicate().relation().caseOf(
                                            Relation::rel,
                                            () -> " == "
                                    )
                                            + (
                                            (predicate.predicate().oop() instanceof Grammar.Number n)
                                                    ?
                                                    n.number()
                                                    :
                                                    ((ViewName) predicate.predicate().oop()).name() + getLengthIdentifier()
                                    )
                                            + predicate.predicate().atp().caseOf(
                                            atp -> " " + atp.at() + atp.priority().toString(),
                                            () -> ""
                                    )
                            );
                        }
                        return resultSet;
                    },
                    ArrayList<String>::new
            );
            constraints.addAll(result);
        }
    }



    private static String getStartIdentifier() {
        if (orientation.equals("H")) {
            return ".left";
        } else {
            return ".top";
        }
    }

    private static String getEndIdentifier() {
        if (orientation.equals("H")) {
            return ".right";
        } else {
            return ".bottom";
        }
    }

    private static String getLengthIdentifier() {
        if (orientation.equals("H")) {
            return ".width";
        } else {
            return ".height";
        }
    }

    private static List<String> computeViewConnectorViewConstraints(
            String expr1,
            Connection connection,
            String expr2
    ) {
        if (connection instanceof EmptyString)
            return new ArrayList<>(List.of(expr1 + " == " + expr2));

        expr2 += " + ";
        if (connection instanceof Connector)
            return new ArrayList<>(List.of(expr1 + " == " + expr2 + defaultGap));

        // connection instanceof ConnectorWrappedPredicateList cwp
        var predicateList = ((ConnectorWrappedPredicateList) connection).predicateList();
        if (predicateList instanceof Grammar.Number n)
            return new ArrayList<>(List.of(expr1 + " == " + expr2 + n));

        // predicateList instanceof PredicateListWithParens plwp

        var plwp = (PredicateListWithParens) predicateList;
        var predicates = plwp.cp();
        predicates.add(0, new CommaPredicate(',', plwp.predicate()));

        List<String> resultSet = new ArrayList<>();
        for (var predicate: predicates) {
            resultSet.add(
                    expr1 +
                            predicate.predicate().relation().caseOf(
                                    Relation::rel,
                                    () -> " == "
                            )
                            + expr2 +
                            (
                                    (predicate.predicate().oop() instanceof Grammar.Number n)
                                            ?
                                            n.number()
                                            :
                                            ((ViewName) predicate.predicate().oop()).name() + getLengthIdentifier()
                            )
                            +
                            predicate.predicate().atp().caseOf(
                                    atp -> " " + atp.at() + atp.priority().toString(),
                                    () -> ""
                            )
            );
        }
        return resultSet;
    }


}

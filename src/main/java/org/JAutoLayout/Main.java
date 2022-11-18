package org.JAutoLayout;

import com.eclipsesource.v8.V8;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

//    Parsing strategy 1: Run peg.js parser in Java using nashorn - SLOW!
//    public interface VFLParser extends Parser<String> { }
//
//    public static void main(String[] args) throws IOException {
//
//        String grammar = Files.readString(Path.of("src/main/java/org/JAutoLayout/Parser/VFL.peg"));
//        PEG peg = PEG.getInstance();
//        VFLParser parser = peg.generate(grammar, VFLParser.class);
//        var res = parser.parse("|-[find]-[findNext]-[findField(>=20)]-|");
//        Files.writeString(Path.of("pegjsOP.json"), res);
//    }

//    Parsing strategy 2: Run canopy parser natively in Java
//    public static void main(String[] args) throws ParseError, IOException {
//
//        var res = VFL.parse("|-[find]-[findNext]-[findField(>=20)]-|");
//        Files.writeString(Path.of("canOP.json"), res.toString());
//    }


    // Parsing strategy 3: Run Rekex parser natively in Java
    public static void main(String[] args) throws Exception {
//        PegParser<VisualFormatString> parser = new PegParserBuilder()
//                .rootType(VisualFormatString.class)
//                .packageName("org.JAutoLayout.Parser.rekex")
//                .outDirForJava(Path.of("src/main/java"))
//                .logGrammar(System.out::println)
//                .build();

//        PegParser<VisualFormatString> parser = new Parser();
        JFrame frame = new JFrame("Cassowary Constraint Solver");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Close the application once the close button is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1500, 100));

        MainUI mainUI = new MainUI(frame);
        frame.add(mainUI.CenterComponent(), BorderLayout.CENTER);
        //frame.add(new JLabel("we have a very long text just to see how the layout is working. it has no meaning, so don't bother reading the text. it's absolutely meaningless."), BorderLayout.NORTH);
        frame.add(mainUI.WestComponent(), BorderLayout.WEST);

        frame.pack();
        frame.show();
        frame.setVisible(true);
        //

        frame.setLayout(new BorderLayout());

//        PegParser<VisualFormatString> parser = PegParser.of(VisualFormatString.class);
//        var res = parser.matchFull("|-[find]-[findNext]-[findField(>=20)]-|");
//        System.out.println(res.view());
//        System.out.println(res.connectionViews());
//
//        Solver solver = new Solver();
//        Variable x = new Variable("x");
//        Variable y = new Variable("y");
//
//        // x = 20
//        solver.addConstraint(Symbolics.equals(x, 20));
//
//        // x + 2 == y + 10
//        solver.addConstraint(Symbolics.equals(Symbolics.add(x,2), Symbolics.add(y, 10)));
//
//        solver.updateVariables();
//        System.out.println("x " + x.getValue() + " y " + y.getValue());

//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//        engine.eval("load('nashorn:mozilla_compat.js')");
//        engine.eval(new FileReader("src/main/java/org/JAutoLayout/autoLayout.js"));
//        Object result = engine.eval("AutoLayout.VisualFormat.parse(\"|-[find]-[findNext]-[findField(>=20)]-|\")");
//        System.out.println(result);

//        V8 v8 = V8.createV8Runtime();
//        v8.executeScript(Files.readString(Path.of("src/main/java/org/JAutoLayout/autoLayout.js")));
//        String result = v8.executeStringScript("""
//                var constraints = AutoLayout.VisualFormat.parse([
//                    "|-[child1(child3)]-[child3]-|",
//                    "|-[child2(child4)]-[child4]-|",
//                    "[child5(child4)]-|",
//                    "V:|-[child1(child2)]-[child2]-|",
//                    "V:|-[child3(child4,child5)]-[child4]-[child5]-|"
//                ]);
//                var view = new AutoLayout.View({constraints: constraints});
//                view.setSize(400, 400);
//                JSON.stringify(view.subViews)
//                """);
//
//        System.out.println(result);
    }

    public void ReadJsonData(){
//        JSONParser parser = new JSONParser();
    }
}
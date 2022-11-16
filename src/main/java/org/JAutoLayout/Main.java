package org.JAutoLayout;

import org.JAutoLayout.AutoLayout.AutoLayout;
import org.JAutoLayout.Parser.rekex.Grammar.VisualFormatString;
import org.JAutoLayout.Parser.rekex.Parser;
import org.JAutoLayout.Toolkit.Solver;
import org.JAutoLayout.Toolkit.Variable;
import org.openjdk.nashorn.internal.parser.JSONParser;
import org.rekex.parser.PegParser;
import org.rekex.parser.PegParserBuilder;

import javax.swing.*;
import java.awt.*;
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
        frame.setMinimumSize(frame.getSize());

        // Close the application once the close button is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainUI mainUI = new MainUI(frame);
        frame.add(mainUI.CenterComponent(), BorderLayout.CENTER);
        frame.add(new JLabel("we have a very long text just to see how the layout is working. it has no meaning, so don't bother reading the text. it's absolutely meaningless."), BorderLayout.NORTH);
        frame.add(mainUI.WestComponent(), BorderLayout.WEST);
        frame.pack();
        frame.show();
        //

        frame.setLayout(new BorderLayout());

        PegParser<VisualFormatString> parser = PegParser.of(VisualFormatString.class);
        var res = parser.matchFull("|-[find]-[findNext]-[findField(>=20)]-|");

        System.out.println(res.view());
        System.out.println(res.connectionViews());
    }

    public void ReadJsonData(){
//        JSONParser parser = new JSONParser();
    }
}
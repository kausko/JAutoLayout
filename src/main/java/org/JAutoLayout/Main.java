package org.JAutoLayout;

import org.JAutoLayout.Parser.rekex.Grammar.VisualFormatString;
import org.JAutoLayout.Parser.rekex.Parser;
import org.rekex.parser.PegParser;
import org.rekex.parser.PegParserBuilder;

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


        PegParser<VisualFormatString> parser = PegParser.of(VisualFormatString.class);
        var res = parser.matchFull("|-[find]-[findNext]-[findField(>=20)]-|");
        System.out.println(res.view());
        System.out.println(res.connectionViews());
    }
}
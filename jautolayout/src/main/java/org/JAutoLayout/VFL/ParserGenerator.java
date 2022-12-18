package org.JAutoLayout.VFL;

import org.rekex.parser.PegParserBuilder;

import java.nio.file.Paths;

public class ParserGenerator {
    public static void main(String[] args) {
        new PegParserBuilder()
            .rootType(Grammar.VisualFormatString.class)
            .packageName("org.JAutoLayout.VFL")
            .className("PegParser")
            .outDirForJava(Paths.get("src/main/java"))
            .logGrammar(System.out::println)
            .build();
    }
}

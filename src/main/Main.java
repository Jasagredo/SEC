package main;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ast.Inst.Block;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import lexical.SELA;
import syntactical.parser;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
	 SELA sela = new SELA(input, new ComplexSymbolFactory());
	 parser asint = new parser(sela, new ComplexSymbolFactory());
	 Symbol s = asint.debug_parse();
	 System.out.println((Block) (s.value));
 }
}
   

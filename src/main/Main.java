package main;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ast.Inst.Block;
import excp.SemanticException;
import ident.MapaIdent;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import lexical.SELA;
import syntactical.parser;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 SELA sela = new SELA(input, new ComplexSymbolFactory());
	 parser asint = new parser(sela, new ComplexSymbolFactory());
	 MapaIdent mi = new MapaIdent();
	 try{
		 Symbol s = asint.parse();
		 mi.parsear(s);
		 System.out.println((Block) (s.value));
	 } catch (SemanticException se) {
		System.err.println(se.getMessage());
	 } 
   }
}
   

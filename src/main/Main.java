package main;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import ast.Inst.Block;
import excp.SemanticException;
import excp.TypeException;
import identifiers.SEIC;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import lexical.SELA;
import syntactical.parser;
import types.SETC;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
	 SELA sela = new SELA(input, new ComplexSymbolFactory());
	 parser asint = new parser(sela, new ComplexSymbolFactory());
	 SEIC mi = new SEIC();
	 SETC pt = new SETC();
	 try{
		 Symbol s = asint.parse();
		 System.out.println((Block) (s.value));
		 System.out.println("El código ha pasado con éxito los análisis léxicos y sintácticos.");
		 mi.parsear(s);
		 System.out.println("El código ha pasado con éxito la identificación de identificadores.");
		 pt.parsear(s);
		 System.out.println("El código ha pasado con éxito la comprobación de tipos.");
		 
	 } catch (SemanticException se) {
		System.err.println(se.getMessage());
	 } catch (TypeException te){
		 System.err.println("Error de tipado: " + te.getMessage());
	 }
   }
}
   

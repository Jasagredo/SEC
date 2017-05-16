package main;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java_cup.runtime.ComplexSymbolFactory;
import lexical.SELA;
import syntactical.SESA;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
	 SELA sela = new SELA(input, new ComplexSymbolFactory());
	 SESA asint = new SESA(sela, new ComplexSymbolFactory());
	 asint.debug_parse();
 }
}
   

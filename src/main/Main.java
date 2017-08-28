package main;

import java.io.*;

import errors.*;
import identifiers.SEIC;
import java_cup.runtime.*;
import lexical.SELA;
import syntactical.parser;
import traduc.SETR;
import types.SETC;

public class Main {
   public static void main(String[] args) throws Exception {
     Reader input = new InputStreamReader(new FileInputStream(args[0]));
     PrintWriter output = new PrintWriter("output.p", "UTF-8");
	 SELA sela = new SELA(input, new ComplexSymbolFactory()); 
	 parser asint = new parser(sela, new ComplexSymbolFactory()); 
	 SEIC mi = new SEIC();
	 SETC pt = new SETC();
	 SETR tr = new SETR(output);
	 try{
		 Symbol s = asint.parse();
		 if (!error) System.out.println("El código ha pasado con éxito los análisis léxicos y sintácticos.");
		 mi.parsear(s);
		 if (!error) System.out.println("El código ha pasado con éxito la identificación de identificadores.");
		 pt.parsear(s);
		 if (!error) System.out.println("El código ha pasado con éxito la comprobación de tipos.");
		 tr.traducir(s);
		 output.close();
		 if (!error) System.out.println("El código ha pasado con éxito la generación de código.");
		 
	 }
	 catch (ErrorIdentificadores se) {
		System.err.println(se.getMessage());
	 } catch (ErrorTipos te){
		 System.err.println("Se ha detectado un ERROR" + '\n' + "Tipo del error: TIPADO"
					+ '\n' + "A continuación se ofrece una descripción del error. El código no se puede compilar con este error, por favor soluciónelo y vuelva a intentarlo."
					+ '\n' + '\n' + te.getMessage());
	 }
   }
   public static boolean error = false;
}
   

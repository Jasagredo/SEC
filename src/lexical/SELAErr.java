package lexical;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

public class SELAErr {
   public void errorLexico(int fila, String lexema) {
     System.out.println("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
     System.exit(1);
   }  
   public void errorSintactico(ComplexSymbol unidadLexica) {
     System.out.print("ERROR "+unidadLexica.toString()+": Elemento inexperado "+unidadLexica.value);
     System.exit(1);
   }
}

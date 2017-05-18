package lexical;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class SELAErr {
   public void errorLexico(int fila, String lexema) {
     System.err.println("ERROR léxico fila "+fila+": Caracter inesperado: "+lexema); 
     System.exit(1);
   }  
   public void errorSintactico(ComplexSymbol unidadLexica) {
     System.err.print("ERROR sintáctico "+unidadLexica.toString()+": Elemento inesperado "+unidadLexica.value);
     System.exit(1);
   }
}

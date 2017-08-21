package lexical;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

public class SELAErr {
   public void errorLexico(int fila, String lexema) {
     System.err.println("ERROR léxico fila "+fila+": Elemento inesperado: "+lexema); 
   }  
   public void errorSintactico(ComplexSymbol unidadLexica) {
     System.err.print("ERROR sintáctico "+unidadLexica.toString()+": Elemento inesperado "+unidadLexica.value);
   }
}

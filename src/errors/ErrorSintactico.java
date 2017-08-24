package errors;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;
import syntactical.SESASym;

public class ErrorSintactico {

	public static void errorSintactico(Symbol ul){
		System.err.println("Se ha detectado un ERROR");
		System.err.println("Tipo del error: SINTACTICO");
		System.err.println("A continuación se ofrece una descripción del error. El código no se puede compilar con este error, por favor soluciónelo y vuelva a intentarlo.");
		System.err.println();
		System.err.println("Fila: " + ((ComplexSymbol)ul).xleft.getLine());
		System.err.println("Elemento inesperado: " + ul.value);
		System.exit(1);
	}
}

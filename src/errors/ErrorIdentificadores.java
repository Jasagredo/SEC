package errors;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

@SuppressWarnings("serial")
public class ErrorIdentificadores extends Exception {

	public ErrorIdentificadores(String string) {
		super("Se ha detectado un ERROR" + '\n' + "Tipo del error: SEMÁNTICO"
				+ '\n' + "A continuación se ofrece una descripción del error. El código no se puede compilar con este error, por favor soluciónelo y vuelva a intentarlo."
				+ '\n' + '\n' + string);
	}
	
}

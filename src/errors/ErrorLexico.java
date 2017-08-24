package errors;

public class ErrorLexico {
	public static void errorLexico(int fila, String lexema) {
		System.err.println("Se ha detectado un ERROR");
		System.err.println("Tipo del error: LEXICO");
		System.err.println("A continuación se ofrece una descripción del error. El código no se puede compilar con este error, por favor soluciónelo y vuelva a intentarlo.");
		System.err.println();
		System.err.println("Fila: " +fila);
		System.err.println("Caracter inesperado: " + lexema);
		System.exit(1);
	}
}

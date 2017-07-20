package excp;

import java.io.IOException;

import main.Main;

@SuppressWarnings("serial")
public class SyntacticException extends Exception{

	public static void gestiona(String st, int x, int y) throws IOException
	{
		System.err.println("Error: " + st + ". Línea: " + x + " Columna: " + y + ".");
		Main.error = true;
	}
}

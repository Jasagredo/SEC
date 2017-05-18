package ast.Inst;

import ast.Expr.Expr;

public class Asig implements Inst {
	Expr id;
	Expr e;
	public Asig(Expr i, Expr e){
		this.id = i;
		this.e = e;
	}

	public String toString(String acc) {
		return acc + "Asigación a la variable #" + id.toString() + "# el valor de #" + e.toString() + "#";
	}
	
	
}

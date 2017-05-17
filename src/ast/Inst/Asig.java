package ast.Inst;

import ast.Expr.Expr;

public class Asig implements Inst {
	Expr id;
	Expr e;
	public Asig(Expr i, Expr e){
		this.id = i;
		this.e = e;
	}

	public String toString() {
		return id.toString() + " " + e.toString();
	}
	
	
}

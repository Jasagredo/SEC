package ast.Inst;

import java.util.List;

import ast.Expr.Expr;

public class While implements Inst{
	Expr e;
	List<InstCond> cuerpo;
	public While(Expr e, List<InstCond> cuerpo) {
		super();
		this.e = e;
		this.cuerpo = cuerpo;
	}
}

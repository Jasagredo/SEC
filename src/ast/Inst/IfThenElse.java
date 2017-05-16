package ast.Inst;

import java.util.List;

import ast.Expr.Expr;

public class IfThenElse implements Inst{
	Expr e;
	List<InstCond> li;
	List<InstCond> le;
	public IfThenElse(Expr e, List<InstCond> li, List<InstCond> le) {
		super();
		this.e = e;
		this.li = li;
		this.le = le;
	}
	
}

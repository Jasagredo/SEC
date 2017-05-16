package ast.Inst;

import java.util.List;

import ast.Expr.Expr;

public class Ifthen implements Inst{
	Expr cond;
	List<InstCond> li;
	public Ifthen(Expr cond, List<InstCond> li) {
		this.cond = cond;
		this.li = li;
	}
	
}

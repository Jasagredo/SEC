package ast.Inst;

import java.util.Iterator;
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
	
	public String toString()
	{
		Iterator<InstCond> it = cuerpo.iterator();
		String s = "";
		while (it.hasNext()) s = s + it.next().toString() + "\n";
		return "while " + e.toString() + " " + s;
	}
}

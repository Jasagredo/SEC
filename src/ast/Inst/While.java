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
	
	public String toString(String acc)
	{
		Iterator<InstCond> it = cuerpo.iterator();
		String s = acc;
		while (it.hasNext()) s = s + it.next().toString(acc) + "\n";
		return acc + "while " + e.toString() + "\n" + s;
	}
}

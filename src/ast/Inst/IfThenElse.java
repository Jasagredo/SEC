package ast.Inst;

import java.util.Iterator;
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
	
	public String toString()
	{
		Iterator<InstCond> it1 = li.iterator();
		String s1 = "";
		while (it1.hasNext()) s1 = s1 + (it1.next()).toString() + "\n";
		Iterator<InstCond> it2 = le.iterator();
		String s2 = "";
		while (it2.hasNext()) s2 = s2 + it2.next().toString() + "\n";
		return "if " + e.toString() + "\n " + s1 + "else \n" + s2;
	}
	
}

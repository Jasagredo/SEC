package ast.Inst;

import java.util.Iterator;
import java.util.List;

import ast.Expr.Expr;

public class Ifthen implements Inst{
	Expr cond;
	List<InstCond> li;
	public Ifthen(Expr cond, List<InstCond> li) {
		this.cond = cond;
		this.li = li;
	}
	
	public String toString()
	{
		Iterator<InstCond> it1 = li.iterator();
		String s1 = "";
		while (it1.hasNext()) s1 = s1 + it1.next().toString() + "\n";
		return "if " + cond.toString() + " " + s1 + " else";
	}
	
}

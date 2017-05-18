package ast.Inst;

import java.util.Iterator;
import java.util.List;

import ast.Expr.Expr;

public class Ifthen implements Inst{
	Expr cond;
	List<Inst> li;
	public Ifthen(Expr cond, List<Inst> li) {
		this.cond = cond;
		this.li = li;
	}
	
	public String toString(String acc)
	{
		Iterator<Inst> it1 = li.iterator();
		String s1 = "";
		while (it1.hasNext()) s1 = s1 + it1.next().toString(acc + "\t") + "\n";
		return acc + "Si #" + cond.toString() + "#\n" + s1 + acc + "FinSi";
	}
	
}

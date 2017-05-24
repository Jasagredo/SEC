package ast.Expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DArray extends Expr{
	public List<Expr> lexp;
	public DArray(){
		lexp = new ArrayList<Expr>();
	}
	
	public String toString(){
		String aux = "{";
		Iterator<Expr> le = lexp.iterator();
		while (le.hasNext()){
			aux += le.next().toString();
			aux += ",";
		}
		aux += "}";
		return aux;
	}
}

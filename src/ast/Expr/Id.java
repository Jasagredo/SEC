package ast.Expr;

import ast.Dec.Dec;

public class Id extends Expr {
	public String id;
	public Dec d;
	public Id(String i){
		id = i;
		d = null;
	}
	
	public String toString()
	{
		return id;
	}

}

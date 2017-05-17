package ast.Expr;

public class Id extends Expr {
	String id;
	public Id(String i){
		id = i;
	}
	
	public String toString()
	{
		return id;
	}

}

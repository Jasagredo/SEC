package ast.Expr;

public abstract class Base extends Expr{
	int literal;
	
	public String toString()
	{
		return ((Integer) literal).toString();
	}
}

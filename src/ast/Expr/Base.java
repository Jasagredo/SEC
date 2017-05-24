package ast.Expr;

public abstract class Base extends Expr{
	public int literal;
	
	public String toString()
	{
		return ((Integer) literal).toString();
	}
}

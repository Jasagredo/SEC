package ast.Expr;

public class BoolConst extends Base  {
	public BoolConst(boolean b){
		int  k = b ? 1 : 0 ;
		literal = k;
	}
	
	public String toString()
	{
		return literal == 1 ? "true" : "false";
	}
}

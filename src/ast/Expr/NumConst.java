package ast.Expr;

public class NumConst extends Base{
	public NumConst(int i){
		literal = i;
	}
	
	public String toString()
	{
		return ((Integer) literal).toString();
	}
}

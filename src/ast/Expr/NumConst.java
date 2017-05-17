package ast.Expr;

public class NumConst extends Base{
	public NumConst(String i){
		literal = Integer.parseInt(i);
	}
	
	public String toString()
	{
		return ((Integer) literal).toString();
	}
}

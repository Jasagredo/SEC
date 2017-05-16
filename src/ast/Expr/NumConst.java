package ast.Expr;

public class NumConst extends Base{
	public NumConst(int i){
		literal = i;
	}
	
	public String mostrar()
	{
		return ((Integer) literal).toString();
	}
}

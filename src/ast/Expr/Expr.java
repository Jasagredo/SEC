package ast.Expr;

public class Expr {
	public Expr valueA;
	public Expr valueB;
	public Operadores op;
	
	public Expr(){
	}
	
	public Expr(Operadores op, Expr vA, Expr vB){
		this.op = op;
		this.valueA = vA;
		this.valueB = vB;
	}
	
	public String toString()
	{
		if (valueA != null)
		return valueA.toString() + " " + op.toString() + " " + valueB.toString();
		return op.toString() + " " + valueB.toString();
	}

}

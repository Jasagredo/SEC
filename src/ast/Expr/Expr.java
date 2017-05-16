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
	
	public String mostrar()
	{
		return valueA.mostrar() + " " + op.mostrar() + " " + valueB.mostrar();
	}

}

package ast.Inst;

public class InstCond {
	Inst i;
	public int ng;
	public InstCond(Inst i){
		this.ng = 0;
		this.i = i;
	}
	
	public String toString()
	{
		String str = "COND";
		for (int k = 0; k < ng; k++){
			str += "COND";
		}
		return str + i.toString();
	}
	
}

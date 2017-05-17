package ast.Inst;

public class InstCond {
	Inst i;
	public int ng;
	public InstCond(Inst i){
		this.ng = 0;
		this.i = i;
	}
	
	public String toString(String acc)
	{
		String str = acc + "COND ";
		return str + i.toString(acc);
	}
	
}

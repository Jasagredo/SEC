package ast.Inst;

import java.util.Iterator;
import java.util.List;

import ast.Dec.Dec;

public class Block implements Inst{
	public List<Dec> ld;
	public List<Inst> li;
	public Block(List<Dec> ld, List<Inst> li) {
		super();
		this.ld = ld;
		this.li = li;
	};
	
	public String toString(){
		return this.toString("");
	}
	
	public String toString(String acc)
	{
		Iterator<Dec> it1 = ld.iterator();
		String s1 = acc + "Bloque\n" + acc + "Comienzo de ListDec\n";
		while (it1.hasNext()) s1 = s1 + it1.next().toString(acc + "\t") + "\n";
		
		Iterator<Inst> it2 = li.iterator();
		String s2 = acc + "Comienzo de ListInstr\n";
		while (it2.hasNext()) s2 = s2 + it2.next().toString(acc + "\t") + "\n";
		return s1 + acc+ "Fin de ListDec\n" + acc + "--\n" + s2 + acc +"Fin de ListInstr\n" + acc + "finbloque";
	}
	
}

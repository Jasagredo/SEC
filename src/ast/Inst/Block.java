package ast.Inst;

import java.util.Iterator;
import java.util.List;

import ast.Dec.Dec;

public class Block implements Inst{
	List<Dec> ld;
	List<Inst> li;
	public Block(List<Dec> ld, List<Inst> li) {
		super();
		this.ld = ld;
		this.li = li;
	};
	
	public String mostrar()
	{
		Iterator<Dec> it1 = ld.iterator();
		String s1 = "";
		while (it1.hasNext()) s1 = s1 + it1.next().mostrar() + "\n";
		Iterator<Inst> it2 = li.iterator();
		String s2 = "";
		while (it2.hasNext()) s2 = s2 + it2.next().mostrar() + "\n";
		return "bloque\n" + s1 + "\n--\n" + s2 + "finbloque";
	}
	
}

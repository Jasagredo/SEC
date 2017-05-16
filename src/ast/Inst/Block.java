package ast.Inst;

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
	
}

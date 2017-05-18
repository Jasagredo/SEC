package ast.Dec;

import java.util.ArrayList;
import java.util.List;

public class TipoC {
	public Dec dec;
	public Tipo t;
	public List<Integer> d;
	public TipoC(Tipo t, String dim) {
		this.t = t;
		dec = null;
		d = new ArrayList<Integer>();
		d.add(Integer.parseInt(dim.substring(1, dim.length()-1)));
	}
	
	public String toString()
	{
		return "Tipo compuesto " + t.toString() + d ;
	}
}

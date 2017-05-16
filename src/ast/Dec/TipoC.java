package ast.Dec;

import java.util.ArrayList;
import java.util.List;

public class TipoC {
	Tipo t;
	public List<Integer> d;
	public TipoC(Tipo t, String dim) {
		this.t = t;
		d = new ArrayList<Integer>();
		d.add(Integer.parseInt(dim.substring(0, dim.length()-2)));
	}
	
	public String mostrar()
	{
		return "Tipo conpuesto " + t.toString() + "[" + d + "]";
	}
}

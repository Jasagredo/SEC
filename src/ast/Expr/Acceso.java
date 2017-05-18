package ast.Expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Acceso extends Expr {
	public Id id;
	public List<Expr> dim;
	public Acceso(Expr i2, String a){
		if (i2 instanceof Id){
			id = (Id) i2;
			dim = new ArrayList<Expr>();
			try{
				dim.add(new NumConst(a.substring(2, a.length()-1)));
			} catch (NumberFormatException e) {
				dim.add(new Id(a.substring(2, a.length()-1)));
			}
		} else if (i2 instanceof Acceso){
			id = ((Acceso) i2).id;
			dim = ((Acceso) i2).dim;
			try{
				dim.add(new NumConst(a.substring(2, a.length()-1)));
			} catch (NumberFormatException e) {
				dim.add(new Id(a.substring(2, a.length()-1)));
			}
		}
		
	}
	
	public String toString()
	{
		Iterator<Expr> it = dim.iterator();
		String s= id.toString();
		while (it.hasNext()) {
		 
		s = s + "[" + it.next().toString() + "]";
		 
		}
		return s;
	}
}

package ast.Dec;

import java.util.Iterator;
import java.util.List;

import ast.Expr.Base;
import ast.Expr.Expr;
import ast.Expr.Id;

public class Dec {
	public boolean cons = false;
	public Tipo t;
	public TipoC tc;
	public Id i;
	public Expr e;
	public List<Base> ad;
	
	public Dec(Tipo t, Id i, Expr e){
		this.t = t;
		this.i = i;
		this.e = e;
	}
	
	public Dec(TipoC t, Id i, List<Base> ad){
		this.tc = t;
		this.i = i;
		this.ad = ad;
	}
	
	public String mostrar()
	{
		String r = "Declaración";
		if (ad != null) 
		{
			r = r + tc.mostrar() + " ";
			Iterator<Base> it = ad.iterator();
			r = r + "{";
			while(it.hasNext()) r = r + it.next().mostrar() + ",";
			r = r + "}";
		}
		else 
		{
			r = r + " " + t.toString() + " " + i.mostrar() + " ";
			if (e != null) r = r + e.mostrar();
		}
		return r;
	}
	
}

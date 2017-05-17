package ast.Expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Acceso extends Expr {
	String Id;
	List<Integer> dim;
	public Acceso(String a){
		dim = new ArrayList<Integer>();
		String[] arr = a.split("_");
		Id = arr[0];
		for (int i = 1; i < arr.length-1; i++){
			dim.add(Integer.parseInt(arr[i].substring(1, arr[i].length()-2)));
		}
	}
	
	public String toString()
	{
		Iterator<Integer> it = dim.iterator();
		String s= Id;
		while (it.hasNext()) {
		 
		s = s + "[" + it.next().toString() + "]";
		 
		}
		return s;
	}
}

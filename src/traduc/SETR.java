package traduc;

import java.io.PrintWriter;
import java.util.*;

import ast.Dec.*;
import ast.Expr.*;
import ast.Inst.*;
import java_cup.runtime.Symbol;

public class SETR {

	private PrintWriter out;
	private List<String> programa;
	int desfase;
	
	public SETR(PrintWriter o) {
		out = o;
		programa = new ArrayList<String>();
		desfase = 0;
	}

	private void traducirBlock(Block a){
		Iterator<Dec> id = a.ld.iterator();
		while(id.hasNext()){
			traducirDec(id.next());
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			traducirInst(ii.next());
		}
	}
	
	private void traducirDec(Dec d) {
		if (d.t != null){ //Tipo simple
						
			boolean aux = d.e != null;
			if (d.t == Tipo.ENT) 
				if (aux)  // Si tiene una expresión, se traduce
					traducirExpr(d.e);
				else  	 //Si no, se asigna 0
					programa.add("ldc 0;"); 
			else 
				if (aux) 
					traducirExpr(d.e);
				else 
					programa.add("ldc false;");
		} else {
			boolean aux = d.tc.t == Tipo.ENT;
			boolean aux2 = d.ad != null;
			int lon = 1;
			Iterator<Integer> it = d.tc.d.iterator();
			while (it.hasNext()) {
				lon *= it.next();
			}
			if (aux)
				if (aux2){
					traducirDArray(d.ad, d.pos);
				}
				else
					for (int i = 0; i < lon; i++){
						programa.add("ldc 0;");
					}
			else
				if (aux2){
					traducirDArray(d.ad, d.pos);
				}
				else
					for (int i = 0; i < lon; i++){
						programa.add("ldc false;");
					}
		}
		
	}
	
	private void traducirDArray(DArray ad, int pos) {
		Iterator<Expr> ie = ad.lexp.iterator();
		int dir = 0;
		while (ie.hasNext()){
			Expr i = ie.next();
			if (i instanceof DArray){
				traducirDArray((DArray)i, pos+dir);
				dir++;
			} else if (i instanceof Base){
				programa.add("ldc "+((Base)i).toString()+";");
			}
			dir++;
		}
	}

	private void traducirExpr(Expr e){
			if (e instanceof Id){
				programa.add("ldc "+((Id)e).d.pos+";");
				programa.add("ind;");
			} else if (e instanceof Acceso){
				Acceso a = (Acceso) e;
				ListIterator<Expr> ie = a.dim.listIterator(a.dim.size());
				ListIterator<Integer> ii = a.id.d.tc.d.listIterator(a.id.d.tc.d.size()); 
				int mAcum = 1;
				List<Integer> lista = new ArrayList<Integer>();
				while (ii.hasPrevious()){
					int aux = ii.previous();
					lista.add(mAcum);
					mAcum *= aux;
				}
				ii = lista.listIterator();
				ListIterator<Integer> ic = a.id.d.tc.d.listIterator(a.id.d.tc.d.size());
				int cnt = 0;
				while(ie.hasPrevious()){
					traducirExpr(ie.previous());
					programa.add("chk 0 "+(ic.previous()-1)+";");
					programa.add("ldc "+ii.next()+";");
					programa.add("mul;");
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					programa.add("add;");
				}
				programa.add("ldc "+a.id.d.pos+";");
				programa.add("add;");
				programa.add("ind;");
				
			} else if (e instanceof BoolConst){
				if (((BoolConst) e).literal == 1) programa.add("ldc true;");
				else programa.add("ldc false;");
			} else if (e instanceof NumConst){
				programa.add("ldc "+((NumConst) e).literal +";");
			} else {
				switch (e.op){
				case CON:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("and;");
					break;
				case DIS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("or;");
					break;
				case DIV:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("div;");
					break;
				case IGUAL:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("equ;");
					break;
				case MAS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("add;");
					break;
				case MAYOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("gtr;");
					break;
				case MENOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("les;");
					break;
				case MENOS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("sub;");
					break;
				case NEG:
					traducirExpr(e.valueB);
					programa.add("not;");
					break;
				default:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					programa.add("mul;");
				}
			}

		}
	
	private void traducirInst(Inst i){
		if (i instanceof Block){
			traducirBlock((Block) i);
		} else if (i instanceof Asig) { // Instruccion de asignacion
			if (((Asig)i).id instanceof Id){ // En la que se asigna un identificador
				programa.add("ldc "+((Id)((Asig)i).id).d.pos+";"); // Obtenemos la dirección
				traducirExpr(((Asig)i).e);
				programa.add("sto;"); //Asignamos la expresión a la dirección
			} else { // No es un identificador, es un acceso a Array
				Acceso a = (Acceso)((Asig)i).id;
				ListIterator<Expr> ie = a.dim.listIterator(a.dim.size());
				ListIterator<Integer> ii = a.id.d.tc.d.listIterator(a.id.d.tc.d.size()); 
				int mAcum = 1;
				List<Integer> lista = new ArrayList<Integer>();
				while (ii.hasPrevious()){
					int aux = ii.previous();
					lista.add(mAcum);
					mAcum *= aux;
				}
				ii = lista.listIterator();
				ListIterator<Integer> ic = a.id.d.tc.d.listIterator(a.id.d.tc.d.size());
				int cnt = 0;
				while(ie.hasPrevious()){
					traducirExpr(ie.previous());
					programa.add("chk 0 "+(ic.previous()-1)+";");
					programa.add("ldc "+ii.next()+";");
					programa.add("mul;");
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					programa.add("add;");
				}
				programa.add("ldc "+a.id.d.pos+";");
				programa.add("add;");
				traducirExpr(((Asig)i).e);
				programa.add("sto;");
			}
			
		} else if (i instanceof IfThen) {
			traducirExpr(((IfThen)i).cond);
			int ind = programa.size(); // Donde meteremos la instruccion de salto al final del if
			Iterator<Inst> iterator = (((IfThen) i).li).iterator();
			desfase++;
			while(iterator.hasNext()) { //Traducimos las instrucciones de la cláusula IF 
				traducirInst(iterator.next());
			}
			desfase--;
			programa.add(ind, "fjp "+(programa.size()+1+desfase)+";"); // Añadimos el salto donde tocaba a el final +1 (la que estamos añadiendo) + nivel (por si en bucles externos hay más que añadir)
		} else if (i instanceof IfThenElse) {
			traducirExpr(((IfThenElse)i).e);
			int ind1 = programa.size(); // Donde va la instruccion de salto al else
			Iterator<Inst> iteratorIf = (((IfThenElse) i).li).iterator();
			desfase+=2;
			while(iteratorIf.hasNext()) { //Traducimos las instrucciones de la cláusula IF 
				traducirInst(iteratorIf.next());
			}
			desfase-=2;
			int ind2 = programa.size(); // donde va la instruccion de salto al final del else
			programa.add(ind1, "fjp "+(programa.size()+2+desfase)+";"); // Añadirmos la instrucción para saltar al else
			Iterator<Inst> iteratorElse = (((IfThenElse) i).le).iterator();
			desfase+=1;
			while(iteratorElse.hasNext()) { //Traducimos las instrucciones de la cláusula IF 
				traducirInst(iteratorElse.next());
			}
			desfase-=1;
			programa.add(ind2+1, "ujp "+(programa.size()+1+desfase)+";");
			
		} else if (i instanceof While){
			int ind1 = programa.size(); // Donde volver para volver a hacer el bucle
			traducirExpr(((While)i).e); 
			int ind2 = programa.size(); // Donde poner la instruccion de salto para salir
			ListIterator<Inst> iteratorW = ((While) i).cuerpo.listIterator();
			desfase++;
			while (iteratorW.hasNext()){
				traducirInst(iteratorW.next());
			}
			desfase--;
			programa.add(ind2,"fjp "+ (programa.size()+2+desfase) +";"); // Donde se hacía el salto, se salta al final
			programa.add("ujp "+ (ind1+desfase) +";");
		} 	
	}
	
	public void traducir(Symbol s){
		traducirBlock((Block) s.value);
		programa.add("stp;");
		int i=0;
		Iterator<String> it = programa.iterator();
		String t=" ";
		while(it.hasNext())
		{
			out.println(" {"+t+i+"}  "+it.next());
			i++;
			if (i>=10) t="";
		}
		out.flush();
	}


}

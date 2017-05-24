package traduc;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import ast.Dec.Dec;
import ast.Dec.Tipo;
import ast.Expr.Acceso;
import ast.Expr.Base;
import ast.Expr.BoolConst;
import ast.Expr.DArray;
import ast.Expr.Expr;
import ast.Expr.Id;
import ast.Expr.NumConst;
import ast.Inst.Asig;
import ast.Inst.Block;
import ast.Inst.IfThen;
import ast.Inst.IfThenElse;
import ast.Inst.Inst;
import ast.Inst.While;
import excp.TypeException;
import java_cup.runtime.Symbol;
import types.Tablas;

public class SETR {

	private List<String> programa = new ArrayList<String>();
	private PrintWriter out;
	private int ic = 0;
	private int vd = -1;
	
	public SETR(PrintWriter o) {
		out = o;
	}
	
	private void mostrar(String s){
		out.println(s);
		switch (s.substring(0, 3)){
		case "ldc":
		case "ldo":
		case "dpl":
			vd++;
			break;
		case "sto":
			vd -= 2;
			break;
		case "add":
		case "sub":
		case "mul":
		case "div":
		case "neg":
		case "and":
		case "or":
		case "equ":
		case "les":
		case "grt":
		case "sro":
		case "fjp":
			vd--;
			break;
		}
		ic++;
	}

	private void traducirBlock(Block a, boolean b){
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
		if (d.t != null){
			d.add = vd+1;
			
			boolean aux = d.e != null;
			if (d.t == Tipo.ENT) 
				if (aux) 
					traducirExpr(d.e);
				else 
					mostrar("ldc 0;"); 
			else 
				if (aux) 
					traducirExpr(d.e);
				else 
					mostrar("ldc false;");
			
			if (aux){
				mostrar("ldc 1;");
			} else mostrar("ldc 0;");
			
		} else {
			d.add = ic;
			boolean aux = d.tc.t == Tipo.ENT;
			boolean aux2 = d.ad != null;
			int lon = 1;
			Iterator<Integer> it = d.tc.d.iterator();
			while (it.hasNext()) {
				lon *= it.next();
			}
			if (aux)
				if (aux2){
					traducirDArray(d.ad);
				}
				else
					for (int i = 0; i < lon; i++){
						mostrar("ldc 0;");
						mostrar("ldc 0;");
					}
			else
				if (aux2){
					traducirDArray(d.ad);
				}
				else
					for (int i = 0; i < lon; i++){
						mostrar("ldc false;");
						mostrar("ldc 0;");
					}
		}
		
	}
	
	private void traducirDArray(DArray ad) {
		Iterator<Expr> ie = ad.lexp.iterator();
		while (ie.hasNext()){
			Expr i = ie.next();
			if (i instanceof DArray){
				traducirDArray((DArray)i);
			} else if (i instanceof Base){
				mostrar("ldc "+((Base)i).toString()+";");
				mostrar("ldc 1;");
			}
		}
	}

	private void traducirExpr(Expr e){
			if (e instanceof Id){
				mostrar("ldo "+((Id)e).d.add+";");
			} else if (e instanceof Acceso){
				
			} else if (e instanceof BoolConst){
				if (((BoolConst) e).literal == 1) mostrar("ldc true;");
				else mostrar("ldc false;");
			} else if (e instanceof NumConst){
				mostrar("ldc "+((NumConst) e).literal +";");
			} else {
				switch (e.op){
				case CON:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("and;");
					break;
				case DIS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("or;");
					break;
				case DIV:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("div;");
					break;
				case IGUAL:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("equ;");
					break;
				case MAS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("add;");
					break;
				case MAYOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("gtr;");
					break;
				case MENOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("les;");
				case MENOS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("sub;");
					break;
				case NEG:
					traducirExpr(e.valueB);
					mostrar("not;");
					break;
				default:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					mostrar("mul;");
				}
			}

		}
	
	private void traducirInst(Inst i){
		if (i instanceof Block){
			traducirBlock((Block) i, false);
		} else if (i instanceof Asig) {
			if (((Asig)i).id instanceof Id){
				mostrar("ldc "+((Id)((Asig)i).id).d.add+";");
				traducirExpr(((Asig)i).e);
				mostrar("sto;");
			} else {
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
					mostrar("chk 0 "+(ic.previous()-1)+";");
					mostrar("ldc "+ii.next()+";");
					mostrar("mul;");
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					mostrar("add;");
				}
				mostrar("ldc 2;");
				mostrar("mul;");
				mostrar("ldc "+a.id.d.add+";");
				mostrar("add;");
				mostrar("dpl;");
				traducirExpr(((Asig)i).e);
				mostrar("sto;");
				mostrar("ldc 1;");
				mostrar("add;");
				mostrar("ldc 1;");
				mostrar("sto;");
			}
			
		} else if (i instanceof IfThen) {
			traducirExpr(((IfThen)i).cond);
			String[] aux = traducirIf((IfThen) i);
			mostrar("fjp "+(ic+aux.length+1)+";");
			for (int m = 0; m < aux.length; m++){
				mostrar(aux[m]);
			}
		} else if (i instanceof IfThenElse) {
			traducirExpr(((IfThenElse)i).e);
			String[] aux = traducirIfElse((IfThenElse) i);
			for (int m = 0; m < aux.length; m++){
				mostrar(aux[m]);
			}
		} else if (i instanceof While){
			int aux2 = ic;
			traducirExpr(((While)i).e);
			String[] aux = traducirWhile((While) i);
			for (int m = 0; m < aux.length; m++){
				mostrar(aux[m]);
			}
			mostrar("ujp "+aux2+";");
		} 	
	}
	
	
	
	private String[] traducirWhile(While i) {
		List<String> ls = new ArrayList<String>();
		ListIterator<Inst> lii = i.cuerpo.listIterator();
		while (lii.hasNext()){
			ls.addAll(traducirAuxInst(lii.next()));
		}
		return ls.toArray(new String[0]);
	}

	private String[] traducirIfElse(IfThenElse i) {
		List<String> ls = new ArrayList<String>();
		ListIterator<Inst> lii = i.li.listIterator();
		while (lii.hasNext()){
			ls.addAll(traducirAuxInst(lii.next()));
		}
		List<String> ls2 = new ArrayList<String>();
		ListIterator<Inst> lie = i.le.listIterator();
		while (lie.hasNext()){
			ls2.addAll(traducirAuxInst(lie.next()));
		}
		ls.add("ujp "+(ic + ls.size() +1 +ls2.size() + 1)+";");
		ls.add(0, "fjp "+(ic + ls.size()+1)+";");
		ls.addAll(ls2);
		return ls.toArray(new String[0]);
	}

	private String[] traducirIf(IfThen i) {
		List<String> ls = new ArrayList<String>();
		ListIterator<Inst> lii = i.li.listIterator();
		while (lii.hasNext()){
			ls.addAll(traducirAuxInst(lii.next()));
		}
		return ls.toArray(new String[0]);
	}

	private List<String> traducirAuxInst(Inst i) {
		List<String> ls = new ArrayList<String>();
		if (i instanceof Block){
			ls.addAll(traducirAuxBlock((Block) i));
		} else if (i instanceof Asig) {
			if (((Asig)i).id instanceof Id){
				ls.add("ldc "+((Id)((Asig)i).id).d.add+";");
				ls.addAll(traducirAuxExpr(((Asig)i).e));
				ls.add("sto;");
			} else {
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
					ls.addAll(traducirAuxExpr(ie.previous()));
					ls.add("chk 0 "+(ic.previous()-1)+";");
					ls.add("ldc "+ii.next()+";");
					ls.add("mul;");
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					ls.add("add;");
				}
				ls.add("ldc 2;");
				ls.add("mul;");
				ls.add("ldc "+a.id.d.add+";");
				ls.add("add;");
				ls.add("dpl;");
				ls.addAll(traducirAuxExpr(((Asig)i).e));
				ls.add("sto;");
				ls.add("ldc 1;");
				ls.add("add;");
				ls.add("ldc 1;");
				ls.add("sto;");
			}
			
		} else if (i instanceof IfThen) {
			ls.addAll(traducirAuxExpr(((IfThen)i).cond));
			String[] aux = traducirIf((IfThen) i);
			ls.add("fjp "+(ic+aux.length)+";");
			for (int m = 0; m < aux.length; m++){
				ls.add(aux[m]);
			}
		} else if (i instanceof IfThenElse) {
			/*
			Iterator<Inst> ii = ((IfThenElse) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
			Iterator<Inst> ie = ((IfThenElse) i).le.iterator();
			while(ie.hasNext()){
				Inst j = ie.next();
				traducirInst(j);
			}*/
		} else if (i instanceof While){
			/*
			Iterator<Inst> ii = ((While) i).cuerpo.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}*/
		}
		return ls;
	}

	private List<String> traducirAuxExpr(Expr e) {
		List<String> ls = new ArrayList<String>();
		if (e instanceof Id){
			ls.add("ldo "+((Id)e).d.add+";");
		} else if (e instanceof Acceso){
			
		} else if (e instanceof BoolConst){
			if (((BoolConst) e).literal == 1) ls.add("ldc true;");
			else ls.add("ldc false;");
		} else if (e instanceof NumConst){
			ls.add("ldc "+((NumConst) e).literal +";");
		} else {
			switch (e.op){
			case CON:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("and;");
				break;
			case DIS:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("or;");
				break;
			case DIV:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("div;");
				break;
			case IGUAL:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("equ;");
				break;
			case MAS:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("add;");
				break;
			case MAYOR:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("gtr;");
				break;
			case MENOR:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("les;");
			case MENOS:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("sub;");
				break;
			case NEG:
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("not;");
				break;
			default:
				ls.addAll(traducirAuxExpr(e.valueA));
				ls.addAll(traducirAuxExpr(e.valueB));
				ls.add("mul;");
			}
		}
		return ls;
	}

	private List<String> traducirAuxBlock(Block a) {
		List<String> ls = new ArrayList<String>();
		Iterator<Dec> id = a.ld.iterator();
		while(id.hasNext()){
			ls.addAll(traducirAuxDec(id.next()));
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			ls.addAll(traducirAuxInst(ii.next()));
		}
		return ls;
	}

	private List<String> traducirAuxDec(Dec d) {
		List<String> ls = new ArrayList<String>();
		if (d.t != null){
			d.add = vd+1;
			
			boolean aux = d.e != null;
			if (d.t == Tipo.ENT) 
				if (aux) 
					ls.addAll(traducirAuxExpr(d.e));
				else 
					ls.add("ldc 0;"); 
			else 
				if (aux) 
					ls.addAll(traducirAuxExpr(d.e));
				else 
					ls.add("ldc false;");
			
			if (aux){
				ls.add("ldc 1;");
			} else ls.add("ldc 0;");
			
		} else {
			d.add = ic;
			boolean aux = d.tc.t == Tipo.ENT;
			boolean aux2 = d.ad != null;
			int lon = 1;
			Iterator<Integer> it = d.tc.d.iterator();
			while (it.hasNext()) {
				lon *= it.next();
			}
			if (aux)
				if (aux2){
					ls.addAll(traducirAuxDArray(d.ad));
				}
				else
					for (int i = 0; i < lon; i++){
						ls.add("ldc 0;");
						ls.add("ldc 0;");
					}
			else
				if (aux2){
					ls.addAll(traducirAuxDArray(d.ad));
				}
				else
					for (int i = 0; i < lon; i++){
						ls.add("ldc false;");
						ls.add("ldc 0;");
					}
		}
		return ls;
	}

	private List<String> traducirAuxDArray(DArray ad) {
		List<String> ls = new ArrayList<String>();
		Iterator<Expr> ie = ad.lexp.iterator();
		while (ie.hasNext()){
			Expr i = ie.next();
			if (i instanceof DArray){
				ls.addAll(traducirAuxDArray((DArray)i));
			} else if (i instanceof Base){
				ls.add("ldc "+((Base)i).toString()+";");
				ls.add("ldc 1;");
			}
		}
		return ls;
	}

	public void traducir(Symbol s){
		traducirBlock((Block) s.value, true);
		mostrar("stp;");
	}


}

package traduc;

import java.util.Iterator;
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
	
	public SETR(PrintWriter o)
	{
		out = o;
	}
	
	private String traducirBlock(Block a, String s){
		Iterator<Dec> id = a.ld.iterator();
		while(id.hasNext()){
			s += traducirDec(id.next(), s);
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			s += traducirInst(ii.next(), s);
		}
		
		return s;
	}
	
	private String traducirDec(Dec d, String s) {
		if (d.t != null){
			d.add = ic;
			
			boolean aux = d.e != null;
			if (d.t == Tipo.ENT) 
				if (aux) 
					s += traducirExpr(d.e, s);
				else 
					s += "ldc 0;\n"; 
			else 
				if (aux) 
					s += traducirExpr(d.e, s);
				else 
					s += "ldc false;\n";
			ic++;
			
			if (aux){
				s += "ldc 1;\n";
			} else s += "ldc 0;\n";
			
			ic++;
			
		} else {
			d.add = ic;
			boolean aux = d.tc.t == Tipo.ENT;
			boolean aux2 = d.e != null;
			int lon = 0;
			Iterator<Integer> it = d.tc.d.iterator();
			while (it.hasNext()) {
				lon *= it.next();
			}
			if (aux)
				if (aux2){
					s += traducirDArray(d.ad, s);
				}
				else
					for (int i = 0; i < lon; i++){
						s += "ldc 0;\n";
						ic++;
						s += "ldc 0;\n";
						ic++;
					}
			else
				if (aux2){
					s += traducirDArray(d.ad, s);
				}
				else
					for (int i = 0; i < lon; i++){
						s += "ldc false;\n";
						ic++;
						s += "ldc 0;\n";
						ic++;
					}
		}
		
		return s;
	}

	private String traducirExpr(Expr e, String s) {
		if (e instanceof Id){
			s += "ldo "+((Id)e).d.add+";\n";
		} else if (e instanceof Acceso){
			
		} else if (e instanceof BoolConst){
			if (((BoolConst) e).literal == 1) s += "ldc true;\n";
			else s += "ldc false;\n";
		} else if (e instanceof NumConst){
			s += "ldc "+((NumConst) e).literal +";\n";
		} else {
			switch (e.op){
			case CON:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "and;\n";
				break;
			case DIS:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "or;\n";
				break;
			case DIV:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "div;\n";
				break;
			case IGUAL:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "equ;\n";
				break;
			case MAS:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "add;\n";
			case MAYOR:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "gtr;\n";
				break;
			case MENOR:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "les;\n";
			case MENOS:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "sub;\n";
				break;
			case NEG:
				s += traducirExpr(e.valueB,s);
				s += "not;\n";
				break;
			default:
				s += traducirExpr(e.valueA,s);
				s += traducirExpr(e.valueB,s);
				s += "mul;\n";
			}
		}
		return s;

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
		if (d.t != null){
			d.add = ic;
			
			boolean aux = d.e != null;
			if (d.t == Tipo.ENT) 
				if (aux) 
					traducirExpr(d.e);
				else 
					out.println("ldc 0;"); 
			else 
				if (aux) 
					traducirExpr(d.e);
				else 
					out.println("ldc false;");
			ic++;
			
			if (aux){
				out.println("ldc 1;");
			} else out.println("ldc 0;");
			
			ic++;
			
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
						out.println("ldc 0;");
						ic++;
						out.println("ldc 0;");
						ic++;
					}
			else
				if (aux2){
					traducirDArray(d.ad);
				}
				else
					for (int i = 0; i < lon; i++){
						out.println("ldc false;");
						ic++;
						out.println("ldc 0;");
						ic++;
					}
		}
		
	}
	
	private String traducirDArray(DArray ad, String s) {
		Iterator<Expr> ie = ad.lexp.iterator();
		while (ie.hasNext()){
			Expr i = ie.next();
			if (i instanceof DArray){
				s += traducirDArray((DArray)i, s);
			} else if (i instanceof Base){
				s += "ldc "+((Base)i).toString()+";";
				ic++;
				s += "ldc 1;";
				ic++;
			}
		}
		return s;
	}

	private void traducirDArray(DArray ad) {
		Iterator<Expr> ie = ad.lexp.iterator();
		while (ie.hasNext()){
			Expr i = ie.next();
			if (i instanceof DArray){
				traducirDArray((DArray)i);
			} else if (i instanceof Base){
				out.println("ldc "+((Base)i).toString()+";");
				ic++;
				out.println("ldc 1;");
				ic++;
			}
		}
	}

	private void traducirExpr(Expr e){
			if (e instanceof Id){
				out.println("ldo "+((Id)e).d.add+";");
			} else if (e instanceof Acceso){
				
			} else if (e instanceof BoolConst){
				if (((BoolConst) e).literal == 1) out.println("ldc true;");
				else out.println("ldc false;");
			} else if (e instanceof NumConst){
				out.println("ldc "+((NumConst) e).literal +";");
			} else {
				switch (e.op){
				case CON:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("and;");
					break;
				case DIS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("or;");
					break;
				case DIV:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("div;");
					break;
				case IGUAL:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("equ;");
					break;
				case MAS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("add;");
					break;
				case MAYOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("gtr;");
					break;
				case MENOR:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("les;");
				case MENOS:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("sub;");
					break;
				case NEG:
					traducirExpr(e.valueB);
					out.println("not;");
					break;
				default:
					traducirExpr(e.valueA);
					traducirExpr(e.valueB);
					out.println("mul;");
				}
			}

		}
	
	private String traducirInst(Inst i, String s){
		if (i instanceof Block){
			s += traducirBlock((Block) i, s);
		} else if (i instanceof Asig) {
			
		} else if (i instanceof IfThen) {
			traducirExpr(((IfThen)i).cond);
			String s1 = "";
			Iterator<Inst> ii = ((IfThen) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
		} else if (i instanceof IfThenElse) {
			
			Iterator<Inst> ii = ((IfThenElse) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
			Iterator<Inst> ie = ((IfThenElse) i).le.iterator();
			while(ie.hasNext()){
				Inst j = ie.next();
				traducirInst(j);
			}
		} else if (i instanceof While){
			
			Iterator<Inst> ii = ((While) i).cuerpo.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
		} 	
		return s;
	}
	
	private void traducirInst(Inst i){
		if (i instanceof Block){
			traducirBlock((Block) i);
		} else if (i instanceof Asig) {
			traducirExpr(((Asig)i).e);
			if (((Asig)i).id instanceof Id){
				out.println("sro "+((Id)((Asig)i).id).d.add+";");
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
				int cnt = 0;
				while(ie.hasPrevious()){
					traducirExpr(ie.previous());
					out.println("ldc "+ii.next()+";");
					out.println("mul;");
					cnt++;
				}
				for (int p = 0; p < cnt-1; p++){
					out.println("add;");
				}
				
				
				
				
			}
		} else if (i instanceof IfThen) {
			traducirExpr(((IfThen)i).cond);
			String s = "";
			Iterator<Inst> ii = ((IfThen) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				s += traducirInst(j, s);
			}
		} else if (i instanceof IfThenElse) {
			
			Iterator<Inst> ii = ((IfThenElse) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
			Iterator<Inst> ie = ((IfThenElse) i).le.iterator();
			while(ie.hasNext()){
				Inst j = ie.next();
				traducirInst(j);
			}
		} else if (i instanceof While){
			
			Iterator<Inst> ii = ((While) i).cuerpo.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				traducirInst(j);
			}
		} 	
	}
	
	
	
	public void traducir(Symbol s){
		traducirBlock((Block) s.value);
		out.println("stp;");
	}


}

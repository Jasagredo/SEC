package traduc;

import java.io.PrintWriter;
import java.util.*;

import ast.Dec.*;
import ast.Expr.*;
import ast.Inst.*;
import java_cup.runtime.Symbol;

public class SETR {

	private List<String> programa = new ArrayList<String>();
	private PrintWriter out;
	private int ic = 0;
	
	public SETR(PrintWriter o)
	{
		out = o;
	}
	
	private void traducirBlock(Block a){
		Iterator<Dec> id = a.ld.iterator();
		while(id.hasNext()){
			traducirDec(id.next());
		}
		programa.add("ssp " + (ic+1) + ";\n");
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
					programa.add("ldc 0;\n"); 
			else 
				if (aux) 
					traducirExpr(d.e);
				else 
					programa.add("ldc false;\n");
			
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
					traducirDArray(d.ad);
				}
				else
					for (int i = 0; i < lon; i++){
						programa.add("ldc 0;\n");
						ic++;
						programa.add("ldc 0;\n");
						ic++;
					}
			else
				if (aux2){
					traducirDArray(d.ad);
				}
				else
					for (int i = 0; i < lon; i++){
						programa.add("ldc false;\n");
						ic++;
						programa.add("ldc 0;\n");
						ic++;
					}
		}
	}

	private void traducirExpr(Expr e) {
		if (e instanceof Id){
			programa.add("ldo "+((Id)e).d.add+";\n");
		} else if (e instanceof Acceso){
			
		} else if (e instanceof BoolConst){
			if (((BoolConst) e).literal == 1) programa.add("ldc true;\n");
			else programa.add("ldc false;\n");
		} else if (e instanceof NumConst){
			programa.add("ldc "+((NumConst) e).literal +";\n");
		} else {
			switch (e.op){
			case CON:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("and;\n");
				break;
			case DIS:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("or;\n");
				break;
			case DIV:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("div;\n");
				break;
			case IGUAL:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("equ;\n");
				break;
			case MAS:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("add;\n");
				break;
			case MAYOR:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("gtr;\n");
				break;
			case MENOR:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("les;\n");
			case MENOS:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("sub;\n");
				break;
			case NEG:
				traducirExpr(e.valueB);
				programa.add("not;\n");
				break;
			default:
				traducirExpr(e.valueA);
				traducirExpr(e.valueB);
				programa.add("mul;\n");
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
				programa.add("ldc "+((Base)i).toString()+";");
				ic++;
				programa.add("ldc 1;");
				ic++;
			}
		}
	}

	private void traducirInst(Inst i){
		if (i instanceof Block){
			traducirBlock((Block) i);
		} else if (i instanceof Asig) {
			programa.add("ldc " + ((Id) ((Asig) i).id).d.add + ";\n");
			traducirExpr(((Asig) i).e);
			
		} else if (i instanceof IfThen) {
			traducirExpr(((IfThen)i).cond);
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
	}
	
	public void traducir(Symbol s){

		traducirBlock((Block) s.value);
		Iterator<String> it = programa.iterator();
		String st = "";
		while (it.hasNext()) st += it.next();
		out.println(st + "stp;");
	}


}

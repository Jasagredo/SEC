package types;

import java.util.*;

import ast.Dec.*;
import ast.Expr.*;
import ast.Inst.*;
import excp.TypeException;
import java_cup.runtime.Symbol;

public class SETC {

	private Tablas t = new Tablas();

	private void parsearDim(DArray d, List<Integer> li) throws TypeException{
		List<Integer> li2 = new ArrayList<Integer>();
		li2.addAll(li);
		if (!li2.isEmpty()){
			int aux = li2.remove(0);
			List<Expr> ie = new ArrayList<Expr>();
			ie.addAll(d.lexp);
			if (ie.size() != aux) throw new TypeException("Problema de dimensiones en " + d.toString());
			Iterator<Expr> iex = ie.iterator();
			while(iex.hasNext()){
				Expr iex2 = iex.next();
				if (!li2.isEmpty() && iex2 instanceof DArray)
					parsearDim((DArray) iex2, li2);
				else if (iex2 instanceof DArray){
					throw new TypeException("Problema de dimensiones en " + d.toString());
				} else if (!li2.isEmpty()) throw new TypeException("Problema de dimensiones en " + d.toString());
			}
		} 
	}
	
	private void parsearBlock(Block a) throws TypeException {
		Iterator<Dec> id = a.ld.iterator();
		while (id.hasNext()){
			Dec i = id.next();
			if (i.t != null && i.e != null){
				if (parsearExpr(i.e) != i.t) throw new TypeException("Intento de declarar una variable asignandole un tipo que no corresponde en "+i.toString(""));
			} else {
				if (i.ad != null){
					parsearExpr(i.ad);
					parsearDim(i.ad, i.tc.d);
				}
			}
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			Inst i = ii.next();
			parsearInst(i);
		}
	}

	private Tipo parsearExpr(Expr e) throws TypeException{
		try{
			if (e instanceof Id){
				if (((Id) e).d.tc != null){
					throw new TypeException("Intento de utilización de una colección sin acceder a un elemento");
				}
				return ((Id) e).d.t;
			} else if (e instanceof DArray){
				Iterator<Expr> ie = ((DArray) e).lexp.iterator();
				Tipo t = parsearExpr(ie.next());
				while (ie.hasNext()){
					if (parsearExpr(ie.next()) != t) throw new TypeException("No concordancia de tipos dentro de la declaración del array");
				}
				return t;
			} else if (e instanceof Acceso){
				if (((Acceso) e).id.d.t != null){
					throw new TypeException("Intento de acceder a un índice de un elemento que no es una colección");
				}
				Iterator<Expr> it = ((Acceso) e).dim.iterator();
				while (it.hasNext()) t.Acc(parsearExpr(it.next()));
				if (((Acceso) e).dim.size() != ((Acceso) e).id.d.tc.d.size()) throw new TypeException("Número de índices de acceso no consistente con las dimensiones de la declaración");
				return ((Acceso) e).id.d.tc.t;
			} else if (e instanceof BoolConst){
				return Tipo.LOG;
			} else if (e instanceof NumConst){
				return Tipo.ENT;
			} else {
				switch (e.op){
				case CON:
					return t.Con(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case DIS:
					return t.Dis(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case DIV:
					return t.Div(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case IGUAL:
					return t.Igual(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MAS:
					return t.Mas(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MAYOR:
					return t.Mayor(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MENOR:
					return t.Menor(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MENOS:
					return t.Menos(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case NEG:
					return t.Neg(parsearExpr(e.valueB));
				default:
					return t.Por(parsearExpr(e.valueA), parsearExpr(e.valueB));
				}
			}
		} catch (TypeException te){
			throw new TypeException(te.getMessage() + " en la expresión " + e.toString());
		}

	}

	private void parsearInst(Inst i) throws TypeException{
		if (i instanceof Block){
			parsearBlock((Block) i);
		} else if (i instanceof Asig) {
			t.Asig(parsearExpr(((Asig) i).id), parsearExpr(((Asig) i).e));
		} else if (i instanceof IfThen) {
			t.Cond(parsearExpr(((IfThen) i).cond));
			Iterator<Inst> ii = ((IfThen) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				parsearInst(j);
			}
		} else if (i instanceof IfThenElse) {
			t.Cond(parsearExpr(((IfThenElse) i).e));
			Iterator<Inst> ii = ((IfThenElse) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				parsearInst(j);
			}
			Iterator<Inst> ie = ((IfThenElse) i).le.iterator();
			while(ie.hasNext()){
				Inst j = ie.next();
				parsearInst(j);
			}
		} else if (i instanceof While){
			t.Cond(parsearExpr(((While) i).e));
			Iterator<Inst> ii = ((While) i).cuerpo.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				parsearInst(j);
			}
		} 	
	}

	public void parsear(Symbol s) throws TypeException{
		parsearBlock((Block) s.value);
	}
}

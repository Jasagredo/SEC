package traduc;

import java.util.Iterator;
import java.util.*;
import ast.Dec.Tipo;
import ast.Expr.Acceso;
import ast.Expr.BoolConst;
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
	
	private void traducirBlock(Block a) throws TypeException {
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			Inst i = ii.next();
			traducirInst(i);
		}
	}
	
	private Tipo traducirExpr(Expr e) throws TypeException{
			try{
				if (e instanceof Id){
					if (((Id) e).d.tc != null){
						throw new TypeException("Intento de utilización de una colección sin acceder a un elemento");
					}
				return ((Id) e).d.t;
			} else if (e instanceof Acceso){
				if (((Acceso) e).id.d.t != null){
					throw new TypeException("Intento de acceder a un índice de un elemento que no es una colección");
				}
				Iterator<Expr> it = ((Acceso) e).dim.iterator();
				while (it.hasNext()) 
				if (((Acceso) e).dim.size() != ((Acceso) e).id.d.tc.d.size()) throw new TypeException("Número de índices de acceso no consistente con las dimensiones de la declaración");
				return ((Acceso) e).id.d.tc.t;
			} else if (e instanceof BoolConst){
				return Tipo.LOG;
			} else if (e instanceof NumConst){
				return Tipo.ENT;
			} else {
				switch (e.op){
				case CON:
					return programa.Con(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case DIS:
					return programa.Dis(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case DIV:
					return programa.Div(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case IGUAL:
					return programa.Igual(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MAS:
					return programa.Mas(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MAYOR:
					return programa.Mayor(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MENOR:
					return programa.Menor(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case MENOS:
					return programa.Menos(parsearExpr(e.valueA), parsearExpr(e.valueB));
				case NEG:
					return programa.Neg(parsearExpr(e.valueB));
				default:
					return programa.Por(parsearExpr(e.valueA), parsearExpr(e.valueB));
				}
			}
			} catch (TypeException te){
				throw new TypeException(te.getMessage() + " en la expresión " + e.toString());
			}

		}
	
	private void traducirInst(Inst i) throws TypeException{
			if (i instanceof Block){
			traducirBlock((Block) i);
		} else if (i instanceof Asig) {
			
		} else if (i instanceof IfThen) {
			
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
	
	public void traducir(Symbol s) throws TypeException{
		traducirBlock((Block) s.value);
	}


}

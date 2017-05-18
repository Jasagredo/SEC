package ident;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ast.Dec.*;
import ast.Expr.*;
import ast.Inst.*;
import java_cup.runtime.Symbol;

public class MapaIdent {
	Map<String, TIdent> mapa;
	private int nivel;
	
	public MapaIdent(){
		mapa = new HashMap<String, TIdent>();
	}
	
	private void entrarBloque(){
		this.nivel++;
	}
	
	private void salirBloque(){
		for (Map.Entry<String, TIdent> entry : mapa.entrySet()) {
			if (entry.getValue().actual == this.nivel) mapa.put(entry.getKey(), entry.getValue().sigDec);
		}
		this.nivel--;
	}
	
	private void anadirId(String id, Dec s){
		TIdent t;
		if (mapa.get(id) != null) {
			t = new TIdent(nivel, s, mapa.get(id));
		} else {
			t = new TIdent(nivel, s, null);
		}
		mapa.put(id, t);
	}
	
	private Dec buscarId(String id){
		return mapa.get(id).dec; 
	}
	
	private void parsearBlock(Block a){
		Iterator<Dec> it = a.ld.iterator();
		while(it.hasNext()){
			Dec d = it.next();
			anadirId(d.i.id, d);
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			Inst i = ii.next();
			parsearInst(i);
		}
	}
	
	private void parsearExpr(Expr e){
		if (e instanceof Id){
			((Id) e).d = buscarId(((Id) e).id);
		} else if (e instanceof Acceso){
			((Id) ((Acceso) e).id).d = buscarId(((Id) ((Acceso) e).id).id);
			Iterator<Expr> ie = ((Acceso) e).dim.iterator();
			while (ie.hasNext()){
				Expr i = ie.next();
				parsearExpr(i);
			}
		} else {
			if (e.valueA != null) parsearExpr(e.valueA);
			if (e.valueB != null) parsearExpr(e.valueB);
		}
	}
	
	private void parsearInst(Inst i) {
		if (i instanceof Block){
			entrarBloque();
			parsearBlock((Block) i);
			salirBloque();
		} else if (i instanceof Asig) {
			parsearExpr(((Asig) i).id);
			parsearExpr(((Asig) i).e);
		} else if (i instanceof IfThen) {
			parsearExpr(((IfThen) i).cond);
			Iterator<Inst> ii = ((IfThen) i).li.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				parsearInst(j);
			}
		} else if (i instanceof IfThenElse) {
			parsearExpr(((IfThenElse) i).e);
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
			parsearExpr(((While) i).e);
			Iterator<Inst> ii = ((While) i).cuerpo.iterator();
			while(ii.hasNext()){
				Inst j = ii.next();
				parsearInst(j);
			}
		} 	
	}

	public void parsear(Symbol s){
		parsearBlock((Block) s.value);
	}
}

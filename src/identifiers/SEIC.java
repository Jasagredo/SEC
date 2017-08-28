package identifiers;

import java.util.*;

import ast.Dec.*;
import ast.Expr.*;
import ast.Inst.*;
import errors.ErrorIdentificadores;
import java_cup.runtime.Symbol;

public class SEIC {
	Map<String, TIdent> mapa;
	private int nivel;
	private int dir;
	
	public SEIC(){
		mapa = new HashMap<String, TIdent>();
		nivel = 0;
		dir = 0;
	}
	
	private void entrarBloque(){
		this.nivel++;
	}
	
	private void salirBloque(){
		for (Map.Entry<String, TIdent> entry : mapa.entrySet()) {
			if (entry.getValue().actual == this.nivel) mapa.put(entry.getKey(), entry.getValue().sigDec); // Restauramos el que estaba a un niel menor (o se pone a null)
		}
		this.nivel--;
	}
	
	private void anadirId(String id, Dec s) throws Exception{ // Añadir un nuevo identificador
		TIdent t;
		if (mapa.get(id) != null) {
			if (mapa.get(id).actual == nivel) throw new Exception(); // Ya existe un identificador con el mismo id en el mismo nivel
			t = new TIdent(nivel, s, mapa.get(id)); // Remplazamos el anterior valor pero guardando un puntero para restaurarlo al bajar de nivel
		} else {
			t = new TIdent(nivel, s, null);
		}
		if (t.dec.tc != null)
		{
			t.dec.pos = dir;
			Iterator<Integer> it = t.dec.tc.d.iterator();
			int k = 1;
			while(it.hasNext()) k = k*it.next().intValue();
			dir = dir + k;
		}
		else 
		{
			t.dec.pos = dir;
			dir++;
		}
		mapa.put(id, t);
	}
	
	private Dec buscarId(String id){
			return mapa.get(id).dec; 
	}
	
	private void parsearBlock(Block a) throws ErrorIdentificadores {
		Iterator<Dec> it = a.ld.iterator();
		while(it.hasNext()){
			Dec d = it.next();
			try {
				anadirId(d.i.id, d);
			} catch (Exception e) {
				throw new ErrorIdentificadores("Identificador " + d.i.id + " ya declarado. "+ '\n' + "Tipo de la declaración inicial: " + mapa.get(d.i.id).dec.t.name() + '\n' + "Tipo de la nueva declaración: " + d.t.name());
			}
		}
		Iterator<Inst> ii = a.li.iterator();
		while(ii.hasNext()){
			Inst i = ii.next();
			parsearInst(i);
		}
	}
	
	private void parsearExpr(Expr e) throws ErrorIdentificadores{

			if (e instanceof Id){
				try {
					((Id) e).d = buscarId(((Id) e).id);
				} catch (NullPointerException e1){
					throw new ErrorIdentificadores("Identificador " + ((Id) e).id + " no declarado.");
				}
			} else if (e instanceof Acceso){
				try {
					((Id) ((Acceso) e).id).d = buscarId(((Id) ((Acceso) e).id).id);
					Iterator<Expr> ie = ((Acceso) e).dim.iterator();
					while (ie.hasNext()){
						Expr i = ie.next();
						parsearExpr(i);
					}
				} catch (NullPointerException e1){
					throw new ErrorIdentificadores("Identificador " + ((Id) ((Acceso) e).id).id + " no declarado.");
				}
			} else {
				if (e.valueA != null) parsearExpr(e.valueA);
				if (e.valueB != null) parsearExpr(e.valueB);
			}
		}
	
	private void parsearInst(Inst i) throws ErrorIdentificadores{
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

	public void parsear(Symbol s) throws ErrorIdentificadores{
		parsearBlock((Block) s.value);
	}
}

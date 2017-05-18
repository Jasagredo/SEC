package identifiers;

import ast.Dec.Dec;
import ast.Inst.Inst;
import java_cup.runtime.Symbol;

public class TIdent {
	int actual;
	Dec dec;
	TIdent sigDec;
	
	public TIdent(int actual, Dec s, TIdent sigDec) {
		this.actual = actual;
		this.dec = s;
		this.sigDec = sigDec;
	}
}

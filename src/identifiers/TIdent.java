package identifiers;

import ast.Dec.Dec;

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

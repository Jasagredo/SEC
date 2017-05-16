package lexical;

import java_cup.runtime.Symbol;
import syntactical.SESASym;

public class SELAOperations {
	private SELA sec;
	public SELAOperations(SELA sec) {
		this.sec = sec;   
	}
	public Symbol unidadNumeroEntero() {
		return sec.symbol(sec.lexema(),SESASym.NUMENT, sec.lexema()); 
	}
	public Symbol unidadIgual() {
		return sec.symbol("=",SESASym.IGUAL, "=");     
	}    
	public Symbol unidadMayor() {
		return sec.symbol(">",SESASym.MAYOR, ">");     
	}    
	public Symbol unidadMenor() {
		return sec.symbol("<",SESASym.MENOR, "<");     
	}
	public Symbol unidadConjuncion() {
		return sec.symbol("&",SESASym.CON, "&");     
	}     
	public Symbol unidadDisyuncion() {
		return sec.symbol("|",SESASym.DIS, "|");     
	}    
	public Symbol unidadNegacion() {
		return sec.symbol("Â¬",SESASym.NEG, "¬");     
	}  
	public Symbol unidadOperadorMas() {
		return sec.symbol("+",SESASym.MAS, "+"); 
	} 
	public Symbol unidadOperadorMenos() {
		return sec.symbol("-",SESASym.MENOS, "-"); 
	} 
	public Symbol unidadOperadorPor() {
		return sec.symbol("*",SESASym.POR, "*"); 
	} 
	public Symbol unidadOperadorDivision() {
		return sec.symbol("/",SESASym.DIV, "/"); 
	} 
	public Symbol unidadLogico() {
		return sec.symbol("Log",SESASym.LOG, "Log");     
	}   
	public Symbol unidadEntero() {
		return sec.symbol("Ent",SESASym.ENT, "Ent");     
	}
	public Symbol unidadTrue() {
		return sec.symbol("true",SESASym.TRUE, "true");     
	}   
	public Symbol unidadFalse() {
		return sec.symbol("false",SESASym.FALSE, "false");     
	}  
	public Symbol unidadBloque() {
		return sec.symbol("bloque",SESASym.BLOQUE, "bloque");     
	} 
	public Symbol unidadFinBloque() {
		return sec.symbol("finbloque",SESASym.FBLOQUE, "finbloque");     
	}
	public Symbol unidadIdV() {
		return sec.symbol(sec.lexema(),SESASym.IDV, sec.lexema());     
	} 
	public Symbol unidadParentesisApertura() {
		return sec.symbol("(",SESASym.PAP, "("); 
	} 
	public Symbol unidadParentesisCierre() {
		return sec.symbol(")",SESASym.PCI, ")"); 
	} 
	public Symbol unidadAsig() {
		return sec.symbol("<··",SESASym.ASIG, "<··"); 
	} 
	public Symbol unidadFinInstruccion() {
		return sec.symbol("!",SESASym.FININSTR, "!");     
	} 
	public Symbol unidadIf() {
		return sec.symbol("si",SESASym.IF, "si"); 
	} 
	public Symbol unidadAperturaCondicional() {
		return sec.symbol("¿",SESASym.APCON, "¿");     
	} 
	public Symbol unidadCierreCondicional() {
		return sec.symbol("?",SESASym.CICON, "?");     
	} 
	public Symbol unidadEntonces() {
		return sec.symbol("entonces",SESASym.ENTONCES, "entonces");     
	} 
	public Symbol unidadGuionControl() {
		return sec.symbol("~",SESASym.GCONTROL, "~");     
	} 
	public Symbol unidadSino() {
		return sec.symbol("sino", SESASym.SINO, "sino");     
	} 
	public Symbol unidadMientras() {
		return sec.symbol("mientras",SESASym.MIENTRAS, "mientras");     
	} 
	public Symbol unidadSimboloBucle() {
		return sec.symbol("...",SESASym.SBUCLE, "...");     
	} 
	public Symbol unidadAccesoArray() {
		return sec.symbol(sec.lexema(),SESASym.ACCESO, sec.lexema());     
	}     
	public Symbol unidadSep() {
		return sec.symbol("--",SESASym.SEP, "--");     
	}
	public Symbol unidadApAcc() {
		return sec.symbol("{",SESASym.APACCESO, "{");
	}
	public Symbol unidadCiAcc() {
		return sec.symbol("}",SESASym.CIACCESO, "}");
	}
	public Symbol unidadPyC() {
		return sec.symbol(";",SESASym.PYC, ";");
	}
	public Symbol unidadCons() {
		return sec.symbol("cons",SESASym.CONS, "cons");
	}
	public Symbol unidadDimension() {
		return sec.symbol(sec.lexema(),SESASym.DIM, sec.lexema());
	}
	public Symbol unidadEOF() {
		return sec.symbol("<EOF>",SESASym.EOF, "<EOF>");
	}
}

package lexical;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.Error;
import java.io.*;


public class SELA implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

  private SELAOperations ops;
  private SELAErr errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yychar+1;}
  public void fijaGestionErrores(SELAErr errores){
	this.errores = errores;
  }
  public SELA(Reader r, ComplexSymbolFactory sf){
	this(r);
        symbolFactory = sf;
    }
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;
    public Symbol symbol(String name, int code){
	return symbolFactory.newSymbol(name, code,new Location(yyline+1,yychar+1-yylength()),new Location(yyline+1,yychar+1));
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, new Location(yyline+1, yychar +1), new Location(yyline+1,yychar+yylength()), lexem);
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public SELA (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public SELA (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private SELA () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

  ops = new SELAOperations(this);
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NOT_ACCEPT,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NOT_ACCEPT,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"48:8,14:3,48:2,14,48:18,14,40,48:3,12,20,48,37,38,22,15,48,11,13,23,2:10,48" +
",10,19,17,18,42,48,46:4,26,46:6,24,46:14,1,48,3,48,45,48,32,34,4,47,30,31,2" +
"5,47,36,47:2,33,44,6,5,47,35,28,7,27,29,47:5,8,16,9,43,48:45,21,48:10,39,48" +
":7,41,48:65344,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,92,
"0,1,2,3,4,1:3,5,6,1,3,1:2,7,1:10,8,1,9,1:5,10:8,11,1,12,3,13,14,15,16,9,17," +
"18,1,19,20,21,22,23,24,25,26,27,28,10,29,30,31,32,33,34,35,36,37,38,39,40,4" +
"1,42,43,44,45,46,47,48,10,49,50,51,52,53,54,55")[0];

	private int yy_nxt[][] = unpackFromString(56,49,
"1,2,3,42,4,84:2,43,5,6,7,8,9,46,10,11,44,12,13,14,15,16,17,18,47,84,50,87,8" +
"4:2,88,89,84:2,90,84:2,19,20,42,21,22,23,24,91,49,52,84,42,-1:51,41,-1:48,3" +
",-1:48,84,-1,84,62,84:2,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,3,-1:8,2" +
"6,-1:50,9,-1:74,48,-1:11,84,-1,84:2,55,84,-1:16,63,84,63,84:10,-1:7,84,-1,6" +
"3,84,-1:3,27,-1:48,84,-1,84:4,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,41" +
",28,-1:47,84,-1,84:4,-1:16,63,84,63,84:9,25,-1:7,84,-1,63,84,-1:14,29,-1:48" +
",45,-1:40,51,-1:82,30,-1:15,53,-1:67,31,-1:50,32,-1:23,84,-1,84:3,33,-1:16," +
"63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84,34,84:2,-1:16,63,84,63,84:10," +
"-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:3,35,84:6,-1:7,84,-1,63," +
"84,-1:3,84,-1,84:4,-1:16,63,84,63,84:3,36,84:6,-1:7,84,-1,63,84,-1:3,84,-1," +
"84:4,-1:16,63,84,63,84:3,37,84:6,-1:7,84,-1,63,84,-1:3,84,-1,84:3,38,-1:16," +
"63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:3,39,-1:16,63,84,63,84:10,-1:" +
"7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:3,40,84:6,-1:7,84,-1,63,84," +
"-1:3,84,-1,84:2,54,84,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4" +
",-1:16,63,84,63,84:2,56,84:7,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,6" +
"3,85,84:9,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:6,70,84:3,-1:7" +
",84,-1,63,84,-1:3,84,-1,84:2,71,84,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1" +
":3,84,-1,84,72,84:2,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-" +
"1:16,63,84,63,84:3,73,84:6,-1:7,84,-1,63,84,-1:3,84,-1,84:3,57,-1:16,63,84," +
"63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:7,74,84:2,-1:7," +
"84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:8,75,84,-1:7,84,-1,63,84,-1:3" +
",84,-1,84:2,76,84,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:" +
"16,63,84,63,84:6,78,84:3,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84" +
":2,58,84:7,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,79,84:9,-1:7,84," +
"-1,63,84,-1:3,84,-1,80,84:3,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-" +
"1,84,81,84:2,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63" +
",84,63,84,82,84:8,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:3,59,8" +
"4:6,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:8,83,84,-1:7,84,-1,6" +
"3,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:5,60,84:4,-1:7,84,-1,63,84,-1:3,84,-" +
"1,84:4,-1:16,63,84,63,84:2,61,84:7,-1:7,84,-1,63,84,-1:3,84,-1,84,86,84:2,-" +
"1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-1,84:2,77,84,-1:16,63,84,63,8" +
"4:10,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84,64,84:8,-1:7,84,-1," +
"63,84,-1:3,84,-1,84:2,65,84,-1:16,63,84,63,84:10,-1:7,84,-1,63,84,-1:3,84,-" +
"1,84:4,-1:16,63,84,63,84:5,66,84:3,67,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:1" +
"6,63,84,63,84:6,68,84:3,-1:7,84,-1,63,84,-1:3,84,-1,84:4,-1:16,63,84,63,84:" +
"9,69,-1:7,84,-1,63,84,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	return ops.unidadEOF();
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{errores.errorLexico(fila(), lexema());}
					case -3:
						break;
					case 3:
						{return ops.unidadNumeroEntero();}
					case -4:
						break;
					case 4:
						{return ops.unidadIdV();}
					case -5:
						break;
					case 5:
						{return ops.unidadApAcc();}
					case -6:
						break;
					case 6:
						{return ops.unidadCiAcc();}
					case -7:
						break;
					case 7:
						{return ops.unidadPyC();}
					case -8:
						break;
					case 8:
						{return ops.unidadOperadorMenos();}
					case -9:
						break;
					case 9:
						{}
					case -10:
						break;
					case 10:
						{}
					case -11:
						break;
					case 11:
						{return ops.unidadOperadorMas();}
					case -12:
						break;
					case 12:
						{return ops.unidadIgual();}
					case -13:
						break;
					case 13:
						{return ops.unidadMayor();}
					case -14:
						break;
					case 14:
						{return ops.unidadMenor();}
					case -15:
						break;
					case 15:
						{return ops.unidadConjuncion();}
					case -16:
						break;
					case 16:
						{return ops.unidadNegacion();}
					case -17:
						break;
					case 17:
						{return ops.unidadOperadorPor();}
					case -18:
						break;
					case 18:
						{return ops.unidadOperadorDivision();}
					case -19:
						break;
					case 19:
						{return ops.unidadParentesisApertura();}
					case -20:
						break;
					case 20:
						{return ops.unidadParentesisCierre();}
					case -21:
						break;
					case 21:
						{return ops.unidadFinInstruccion();}
					case -22:
						break;
					case 22:
						{return ops.unidadAperturaCondicional();}
					case -23:
						break;
					case 23:
						{return ops.unidadCierreCondicional();}
					case -24:
						break;
					case 24:
						{return ops.unidadGuionControl();}
					case -25:
						break;
					case 25:
						{return ops.unidadIf();}
					case -26:
						break;
					case 26:
						{return ops.unidadSep();}
					case -27:
						break;
					case 27:
						{return ops.unidadAccesoArray();}
					case -28:
						break;
					case 28:
						{return ops.unidadDimension();}
					case -29:
						break;
					case 29:
						{return ops.unidadSimboloBucle();}
					case -30:
						break;
					case 30:
						{return ops.unidadAsig();}
					case -31:
						break;
					case 31:
						{return ops.unidadLogico();}
					case -32:
						break;
					case 32:
						{return ops.unidadEntero();}
					case -33:
						break;
					case 33:
						{return ops.unidadCons();}
					case -34:
						break;
					case 34:
						{return ops.unidadSino();}
					case -35:
						break;
					case 35:
						{return ops.unidadTrue();}
					case -36:
						break;
					case 36:
						{return ops.unidadFalse();}
					case -37:
						break;
					case 37:
						{return ops.unidadBloque();}
					case -38:
						break;
					case 38:
						{return ops.unidadEntonces();}
					case -39:
						break;
					case 39:
						{return ops.unidadMientras();}
					case -40:
						break;
					case 40:
						{return ops.unidadFinBloque();}
					case -41:
						break;
					case 42:
						{errores.errorLexico(fila(), lexema());}
					case -42:
						break;
					case 43:
						{return ops.unidadIdV();}
					case -43:
						break;
					case 44:
						{}
					case -44:
						break;
					case 46:
						{errores.errorLexico(fila(), lexema());}
					case -45:
						break;
					case 47:
						{return ops.unidadIdV();}
					case -46:
						break;
					case 49:
						{errores.errorLexico(fila(), lexema());}
					case -47:
						break;
					case 50:
						{return ops.unidadIdV();}
					case -48:
						break;
					case 52:
						{return ops.unidadIdV();}
					case -49:
						break;
					case 54:
						{return ops.unidadIdV();}
					case -50:
						break;
					case 55:
						{return ops.unidadIdV();}
					case -51:
						break;
					case 56:
						{return ops.unidadIdV();}
					case -52:
						break;
					case 57:
						{return ops.unidadIdV();}
					case -53:
						break;
					case 58:
						{return ops.unidadIdV();}
					case -54:
						break;
					case 59:
						{return ops.unidadIdV();}
					case -55:
						break;
					case 60:
						{return ops.unidadIdV();}
					case -56:
						break;
					case 61:
						{return ops.unidadIdV();}
					case -57:
						break;
					case 62:
						{return ops.unidadIdV();}
					case -58:
						break;
					case 63:
						{return ops.unidadIdV();}
					case -59:
						break;
					case 64:
						{return ops.unidadIdV();}
					case -60:
						break;
					case 65:
						{return ops.unidadIdV();}
					case -61:
						break;
					case 66:
						{return ops.unidadIdV();}
					case -62:
						break;
					case 67:
						{return ops.unidadIdV();}
					case -63:
						break;
					case 68:
						{return ops.unidadIdV();}
					case -64:
						break;
					case 69:
						{return ops.unidadIdV();}
					case -65:
						break;
					case 70:
						{return ops.unidadIdV();}
					case -66:
						break;
					case 71:
						{return ops.unidadIdV();}
					case -67:
						break;
					case 72:
						{return ops.unidadIdV();}
					case -68:
						break;
					case 73:
						{return ops.unidadIdV();}
					case -69:
						break;
					case 74:
						{return ops.unidadIdV();}
					case -70:
						break;
					case 75:
						{return ops.unidadIdV();}
					case -71:
						break;
					case 76:
						{return ops.unidadIdV();}
					case -72:
						break;
					case 77:
						{return ops.unidadIdV();}
					case -73:
						break;
					case 78:
						{return ops.unidadIdV();}
					case -74:
						break;
					case 79:
						{return ops.unidadIdV();}
					case -75:
						break;
					case 80:
						{return ops.unidadIdV();}
					case -76:
						break;
					case 81:
						{return ops.unidadIdV();}
					case -77:
						break;
					case 82:
						{return ops.unidadIdV();}
					case -78:
						break;
					case 83:
						{return ops.unidadIdV();}
					case -79:
						break;
					case 84:
						{return ops.unidadIdV();}
					case -80:
						break;
					case 85:
						{return ops.unidadIdV();}
					case -81:
						break;
					case 86:
						{return ops.unidadIdV();}
					case -82:
						break;
					case 87:
						{return ops.unidadIdV();}
					case -83:
						break;
					case 88:
						{return ops.unidadIdV();}
					case -84:
						break;
					case 89:
						{return ops.unidadIdV();}
					case -85:
						break;
					case 90:
						{return ops.unidadIdV();}
					case -86:
						break;
					case 91:
						{return ops.unidadIdV();}
					case -87:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}

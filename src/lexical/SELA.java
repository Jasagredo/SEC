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
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
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
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
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
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"47:8,14:3,47:2,14,47:18,14,40,47:3,12,18,47,37,38,22,21,47,11,13,23,2:10,47" +
",10,17,15,16,42,47,45:4,26,45:6,24,45:14,1,47,3,47,44,47,32,34,4,46,30,31,2" +
"5,46,36,46:2,33,43,6,5,46,35,28,7,27,29,46:5,8,14,9,47:46,20,47:10,39,47:7," +
"41,47:2,19,47:65341,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,103,
"0,1,2,3,4,1:3,5,6,1:3,7,1:8,8,1:3,9,1:4,10:4,1,10:6,11,1,12,13,14,15,16,17," +
"18,19,9,1,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,10,35,36,37,38,39,40" +
",41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,10,57,58,59,60,61,62,63,64" +
",65,66")[0];

	private int yy_nxt[][] = unpackFromString(67,48,
"1,2,3,43,4,92:2,44,5,6,7,8,9,46,10,11,12,13,14,49,43,15,16,17,47,92,50,96,9" +
"2:2,97,67,92:2,98,92:2,18,19,43,20,43,21,99,52,53,92,43,-1:50,42,-1:47,3,-1" +
":47,92,-1,92,68,92:2,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,3,-1:8,23,-" +
"1:49,9,-1:53,48,-1:30,92,-1,92:2,59,92,-1:16,69,92,69,92:10,-1:6,92,-1,69,9" +
"2,-1:3,26,-1:47,92,-1,92:4,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,42,27" +
",-1:46,92,-1,92:4,-1:16,69,92,69,92:9,22,-1:6,92,-1,69,92,-1:14,28,-1:47,45" +
",-1:39,51,-1:81,56,-1:28,24,-1:20,25,-1:12,54,-1:66,29,-1:49,30,-1:22,92,-1" +
",92:4,-1:16,69,92,69,92:9,31,-1:6,92,-1,69,92,-1:20,58,-1:30,92,-1,92:3,32," +
"-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:40,35,-1:10,92,-1,92,33,92:2,-1:16" +
",69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,34,92:" +
"6,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,36,92:6,-1:6,92,-1,6" +
"9,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,37,92:6,-1:6,92,-1,69,92,-1:3,92,-" +
"1,92:3,38,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:3,39,-1:16,69" +
",92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,40,92:6,-" +
"1:6,92,-1,69,92,-1:3,92,-1,92:3,41,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1" +
":3,92,-1,92:3,55,-1:16,69,92,69,92:5,72,92:3,73,-1:6,74,-1,69,92,-1:3,92,-1" +
",92:2,57,92,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69," +
"92,69,92:2,60,92:7,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,93,92:9," +
"-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:6,77,92:3,-1:6,92,-1,69," +
"92,-1:3,92,-1,92:2,78,92,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,9" +
"2:4,-1:16,69,92,69,92:9,102,-1:6,92,-1,69,92,-1:3,92,-1,92,79,92:2,-1:16,69" +
",92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,80,92:6,-" +
"1:6,92,-1,69,92,-1:3,92,-1,92:3,61,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1" +
":3,92,-1,92:4,-1:16,69,92,69,92:7,81,92:2,-1:6,92,-1,69,92,-1:3,92,-1,92:4," +
"-1:16,69,92,69,92:8,82,92,-1:6,92,-1,69,92,-1:3,92,-1,92:2,83,92,-1:16,69,9" +
"2,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:6,85,92:3,-1:" +
"6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:2,62,92:7,-1:6,92,-1,69,92," +
"-1:3,92,-1,92:4,-1:16,69,92,69,86,92:9,-1:6,92,-1,69,92,-1:3,92,-1,87,92:3," +
"-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92,88,92:2,-1:16,69,92,69," +
"92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92,89,92:8,-1:6,92,-1" +
",69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,63,92:6,-1:6,92,-1,69,92,-1:3,92" +
",-1,92:4,-1:16,69,92,69,92:8,90,92,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,6" +
"9,92,69,92:5,64,92:4,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:2,6" +
"5,92:7,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:5,66,92:4,-1:6,92" +
",-1,69,92,-1:3,92,-1,92,94,92:2,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3," +
"92,-1,92:2,84,92,-1:16,69,92,69,92:10,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:1" +
"6,69,92,69,92,91,92:8,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92,70" +
",92:8,-1:6,92,-1,69,92,-1:3,92,-1,92:2,71,92,-1:16,69,92,69,92:10,-1:6,92,-" +
"1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:6,75,92:3,-1:6,92,-1,69,92,-1:3,9" +
"2,-1,92:4,-1:16,69,92,69,92:9,76,-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69," +
"92,69,95,92:9,-1:6,92,-1,69,92,-1:3,92,-1,92:2,100,92,-1:16,69,92,69,92:10," +
"-1:6,92,-1,69,92,-1:3,92,-1,92:4,-1:16,69,92,69,92:3,101,92:6,-1:6,92,-1,69" +
",92,-1");

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
						{return ops.unidadIgual();}
					case -12:
						break;
					case 12:
						{return ops.unidadMayor();}
					case -13:
						break;
					case 13:
						{return ops.unidadMenor();}
					case -14:
						break;
					case 14:
						{return ops.unidadConjuncion();}
					case -15:
						break;
					case 15:
						{return ops.unidadOperadorMas();}
					case -16:
						break;
					case 16:
						{return ops.unidadOperadorPor();}
					case -17:
						break;
					case 17:
						{return ops.unidadOperadorDivision();}
					case -18:
						break;
					case 18:
						{return ops.unidadParentesisApertura();}
					case -19:
						break;
					case 19:
						{return ops.unidadParentesisCierre();}
					case -20:
						break;
					case 20:
						{return ops.unidadFinInstruccion();}
					case -21:
						break;
					case 21:
						{return ops.unidadCierreCondicional();}
					case -22:
						break;
					case 22:
						{return ops.unidadIf();}
					case -23:
						break;
					case 23:
						{return ops.unidadSep();}
					case -24:
						break;
					case 24:
						{return ops.unidadNegacion();}
					case -25:
						break;
					case 25:
						{return ops.unidadAperturaCondicional();}
					case -26:
						break;
					case 26:
						{return ops.unidadAccesoArray();}
					case -27:
						break;
					case 27:
						{return ops.unidadDimension();}
					case -28:
						break;
					case 28:
						{return ops.unidadSimboloBucle();}
					case -29:
						break;
					case 29:
						{return ops.unidadLogico();}
					case -30:
						break;
					case 30:
						{return ops.unidadEntero();}
					case -31:
						break;
					case 31:
						{return ops.unidadFSi();}
					case -32:
						break;
					case 32:
						{return ops.unidadCons();}
					case -33:
						break;
					case 33:
						{return ops.unidadSino();}
					case -34:
						break;
					case 34:
						{return ops.unidadTrue();}
					case -35:
						break;
					case 35:
						{return ops.unidadAsig();}
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
					case 41:
						{return ops.unidadFMientras();}
					case -42:
						break;
					case 43:
						{errores.errorLexico(fila(), lexema());}
					case -43:
						break;
					case 44:
						{return ops.unidadIdV();}
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
						{errores.errorLexico(fila(), lexema());}
					case -49:
						break;
					case 53:
						{return ops.unidadIdV();}
					case -50:
						break;
					case 55:
						{return ops.unidadIdV();}
					case -51:
						break;
					case 57:
						{return ops.unidadIdV();}
					case -52:
						break;
					case 59:
						{return ops.unidadIdV();}
					case -53:
						break;
					case 60:
						{return ops.unidadIdV();}
					case -54:
						break;
					case 61:
						{return ops.unidadIdV();}
					case -55:
						break;
					case 62:
						{return ops.unidadIdV();}
					case -56:
						break;
					case 63:
						{return ops.unidadIdV();}
					case -57:
						break;
					case 64:
						{return ops.unidadIdV();}
					case -58:
						break;
					case 65:
						{return ops.unidadIdV();}
					case -59:
						break;
					case 66:
						{return ops.unidadIdV();}
					case -60:
						break;
					case 67:
						{return ops.unidadIdV();}
					case -61:
						break;
					case 68:
						{return ops.unidadIdV();}
					case -62:
						break;
					case 69:
						{return ops.unidadIdV();}
					case -63:
						break;
					case 70:
						{return ops.unidadIdV();}
					case -64:
						break;
					case 71:
						{return ops.unidadIdV();}
					case -65:
						break;
					case 72:
						{return ops.unidadIdV();}
					case -66:
						break;
					case 73:
						{return ops.unidadIdV();}
					case -67:
						break;
					case 74:
						{return ops.unidadIdV();}
					case -68:
						break;
					case 75:
						{return ops.unidadIdV();}
					case -69:
						break;
					case 76:
						{return ops.unidadIdV();}
					case -70:
						break;
					case 77:
						{return ops.unidadIdV();}
					case -71:
						break;
					case 78:
						{return ops.unidadIdV();}
					case -72:
						break;
					case 79:
						{return ops.unidadIdV();}
					case -73:
						break;
					case 80:
						{return ops.unidadIdV();}
					case -74:
						break;
					case 81:
						{return ops.unidadIdV();}
					case -75:
						break;
					case 82:
						{return ops.unidadIdV();}
					case -76:
						break;
					case 83:
						{return ops.unidadIdV();}
					case -77:
						break;
					case 84:
						{return ops.unidadIdV();}
					case -78:
						break;
					case 85:
						{return ops.unidadIdV();}
					case -79:
						break;
					case 86:
						{return ops.unidadIdV();}
					case -80:
						break;
					case 87:
						{return ops.unidadIdV();}
					case -81:
						break;
					case 88:
						{return ops.unidadIdV();}
					case -82:
						break;
					case 89:
						{return ops.unidadIdV();}
					case -83:
						break;
					case 90:
						{return ops.unidadIdV();}
					case -84:
						break;
					case 91:
						{return ops.unidadIdV();}
					case -85:
						break;
					case 92:
						{return ops.unidadIdV();}
					case -86:
						break;
					case 93:
						{return ops.unidadIdV();}
					case -87:
						break;
					case 94:
						{return ops.unidadIdV();}
					case -88:
						break;
					case 95:
						{return ops.unidadIdV();}
					case -89:
						break;
					case 96:
						{return ops.unidadIdV();}
					case -90:
						break;
					case 97:
						{return ops.unidadIdV();}
					case -91:
						break;
					case 98:
						{return ops.unidadIdV();}
					case -92:
						break;
					case 99:
						{return ops.unidadIdV();}
					case -93:
						break;
					case 100:
						{return ops.unidadIdV();}
					case -94:
						break;
					case 101:
						{return ops.unidadIdV();}
					case -95:
						break;
					case 102:
						{return ops.unidadIdV();}
					case -96:
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

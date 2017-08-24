package types;

import ast.Dec.Tipo;
import errors.TypeException;

public class Tablas {
	
	public Tipo Acc(Tipo t) throws TypeException{
		if (t == Tipo.ENT) return Tipo.ENT;
		throw new TypeException("Acceso con un índice que no es de tipo entero");
	}
	
	public Tipo Mas(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new TypeException("Suma entre expresiones que no son ambas enteros");
	}
	
	public Tipo Cond(Tipo t) throws TypeException{
		if (t == Tipo.LOG) return Tipo.LOG;
		throw new TypeException("Instrucción Si evaluando una expresión no condicional");
	}
	
	public Tipo Menos(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new TypeException("Resta entre expresiones que no son ambas enteros");
	}
	
	public Tipo Por(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new TypeException("Multiplicación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Div(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new TypeException("División entre expresiones que no son ambas enteros");
	}
	
	public Tipo Con(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.LOG && t1 == t2){
			return Tipo.LOG;
		}
		throw new TypeException("Conjunción entre expresiones que no son ambas lógicos");
	}
	
	public Tipo Dis(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.LOG && t1 == t2){
			return Tipo.LOG;
		}
		throw new TypeException("Disyunción entre expresiones que no son ambas lógicos");
	}
	
	public Tipo Igual(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == t2){
			return Tipo.LOG;
		}
		throw new TypeException("Igualdad entre expresiones que no son del mismo tipo");
	}
	
	public Tipo Mayor(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.LOG;
		}
		throw new TypeException("Comparación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Menor(Tipo t1, Tipo t2) throws TypeException{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.LOG;
		}
		throw new TypeException("Comparación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Neg(Tipo t) throws TypeException{
		if (t == Tipo.LOG) return Tipo.LOG;
		throw new TypeException("Negación de una expresión que no es lógico");
	}
	
	public void Asig(Tipo t1, Tipo t2) throws TypeException{
		if (t1 != t2) throw new TypeException("Asignación de una expresión a un identificador de distinto tipo");
	}
}

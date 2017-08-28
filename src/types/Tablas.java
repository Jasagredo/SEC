package types;

import ast.Dec.Tipo;
import errors.ErrorTipos;

public class Tablas {
	
	public Tipo Acc(Tipo t) throws ErrorTipos{
		if (t == Tipo.ENT) return Tipo.ENT;
		throw new ErrorTipos("Acceso con un índice que no es de tipo entero");
	}
	
	public Tipo Mas(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new ErrorTipos("Suma entre expresiones que no son ambas enteros");
	}
	
	public Tipo Cond(Tipo t) throws ErrorTipos{
		if (t == Tipo.LOG) return Tipo.LOG;
		throw new ErrorTipos("Instrucción Si evaluando una expresión no condicional");
	}
	
	public Tipo Menos(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new ErrorTipos("Resta entre expresiones que no son ambas enteros");
	}
	
	public Tipo Por(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new ErrorTipos("Multiplicación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Div(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.ENT;
		}
		throw new ErrorTipos("División entre expresiones que no son ambas enteros");
	}
	
	public Tipo Con(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.LOG && t1 == t2){
			return Tipo.LOG;
		}
		throw new ErrorTipos("Conjunción entre expresiones que no son ambas lógicos");
	}
	
	public Tipo Dis(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.LOG && t1 == t2){
			return Tipo.LOG;
		}
		throw new ErrorTipos("Disyunción entre expresiones que no son ambas lógicos");
	}
	
	public Tipo Igual(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == t2){
			return Tipo.LOG;
		}
		throw new ErrorTipos("Igualdad entre expresiones que no son del mismo tipo");
	}
	
	public Tipo Mayor(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.LOG;
		}
		throw new ErrorTipos("Comparación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Menor(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 == Tipo.ENT && t1 == t2){
			return Tipo.LOG;
		}
		throw new ErrorTipos("Comparación entre expresiones que no son ambas enteros");
	}
	
	public Tipo Neg(Tipo t) throws ErrorTipos{
		if (t == Tipo.LOG) return Tipo.LOG;
		throw new ErrorTipos("Negación de una expresión que no es lógico");
	}
	
	public void Asig(Tipo t1, Tipo t2) throws ErrorTipos{
		if (t1 != t2) throw new ErrorTipos("Asignación de una expresión a un identificador de distinto tipo");
	}
}

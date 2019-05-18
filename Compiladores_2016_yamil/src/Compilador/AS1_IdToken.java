package Compilador;



public class AS1_IdToken extends Accion_Semantica {

public int ejecutar(Lexico lexico) {
//	lexico.setPosEntrada(lexico.getPosEntrada()+1); //no tiene que avanzar
	if(lexico.getBuffer_temporal().length()>20){
		lexico.setBuffer_temporal(lexico.getBuffer_temporal().substring(0,20));
		Compilador.warnings.add("WARNING, se acorto a 20 la longitud del IDENTIFICADOR. Linea: " + lexico.nrolinea);		
	}
	String aux = lexico.getBuffer_temporal().toUpperCase();
	int valor;
	/*switch (aux){
	case "ID":   valor = 260;break;
	case "THEN":	valor = 261;break; 
	case "IF":	valor = 262;break;
	case "ELSE":   valor = 263;	break;
	case "PRINT":   valor = 264;break;
	case "ENDIF":   valor = 265;	break;
	case "INTEGER":   valor = 266;	break;
	case "UINTEGER":   valor = 267;break;
	case "MATRIX":   valor = 268;		break;	
	case "FOR":   valor = 269;break;
	case "CONSTANTE_POSITIVA": valor = 270; break;	
	case "CONSTANTE_ENTERA":	valor = 271;break;
//	case "FIN":   valor = 279;
	default: valor = 260;break; //por defecto es ID
	}*/
	switch (aux){
	case "ID":   valor = 257;break;
	case "THEN":	valor = 258;break; 
	case "IF":	valor = 259;break;
	case "ELSE":   valor = 260;	break;
	case "PRINT":   valor = 261;break;
	case "ENDIF":   valor = 262;	break;
	case "INTEGER":   valor = 263;	break;
	case "UINTEGER":   valor = 264;break;
	case "MATRIX":   valor = 265;		break;	
	case "FOR":   valor = 266;break;
	case "CONSTANTE_POSITIVA": valor = 267; break;	
	case "CONSTANTE_ENTERA":	valor = 268;break;
	case "ALLOW": valor = 278;break;
	case "TO": valor = 279; break;
//	case "FIN":   valor = 277;
	default: valor = 257;break; //por defecto es ID
	}

	if(Compilador.T_simbolos.get(lexico.getBuffer_temporal()+ valor)!=null){	
		return Compilador.T_simbolos.get(lexico.getBuffer_temporal()+ valor).getToken();		
	}
	else{		
	Tabla_Simbolos t= new Tabla_Simbolos();
	t.setToken(Compilador.ID);
	t.setLexema(lexico.getBuffer_temporal());
	t.setInformacion("Identificador");
	t.setUso("");
	t.setTipo("");
	Compilador.T_simbolos.put(t.getLexema() + Compilador.ID, t);
	return Compilador.T_simbolos.get(t.getLexema() + Compilador.ID).getToken();
	}
		
}
	
		
}
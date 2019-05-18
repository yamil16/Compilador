package Compilador;

public class AS9_EntregarTokenModificadoYConsume extends Accion_Semantica{

	@Override
	public int ejecutar(Lexico lexico) {
		lexico.setBuffer_temporal(lexico.getBuffer_temporal()+lexico.getEntrada());
		lexico.setPosEntrada(lexico.getPosEntrada()+1);
		//buscar "+/n" y eliminarlos
		int indice = 0;
		String aux = "";
		while (indice < lexico.getBuffer_temporal().length()){
			if ((lexico.getBuffer_temporal().charAt(indice) == '+') && (((int)lexico.getBuffer_temporal().charAt(indice+1)) == 10)) //si es /n
				indice = indice + 2;	
			else {
				if (lexico.getBuffer_temporal().charAt(indice) != ' ')
					aux = aux + lexico.getBuffer_temporal().charAt(indice);
				else
					aux = aux + '_';
			indice++;
			}
		}
		aux = aux.substring(1, aux.length()-1);
//		System.out.println("print: " + aux);
		if(Compilador.T_simbolos.get(aux + Compilador.CADENA_MULTILINEA)!=null){
			return Compilador.T_simbolos.get(aux + Compilador.CADENA_MULTILINEA).getToken();
			
		}
		else{
		Tabla_Simbolos t= new Tabla_Simbolos();
		t.setToken(Compilador.CADENA_MULTILINEA);
		t.setLexema(aux);
		t.setInformacion("Cadena multilinea");
		Compilador.T_simbolos.put(aux + Compilador.CADENA_MULTILINEA, t);
		return Compilador.T_simbolos.get(aux + Compilador.CADENA_MULTILINEA).getToken();
		}
			
	}

}

package Compilador;

public class Error3_NoesAnotacion extends Accion_Semantica{
@Override
	public int ejecutar(Lexico lexico) {
		lexico.setBuffer_temporal(lexico.getBuffer_temporal()+lexico.getEntrada());
		//lexico.setEstado(14);
		//lexico.setPosEntrada(lexico.getPosEntrada()+1); no va
		lexico.setBuffer_temporal("");
		Compilador.errores.add("ERROR: Entrada Invalida en la linea "+lexico.getNrolinea());
	
		//System.out.println("WARNING, se considero como comentario y no una anotacion por falta de token 0 o 1 en la linea "+lexico.getNrolinea());
		return -1;
	}

}


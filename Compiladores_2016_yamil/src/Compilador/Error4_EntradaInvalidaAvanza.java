package Compilador;
public class Error4_EntradaInvalidaAvanza extends Accion_Semantica{


	@Override
	public int ejecutar(Lexico lexico) {
		lexico.setPosEntrada(lexico.getPosEntrada()+1);
		lexico.setBuffer_temporal("");
		Compilador.errores.add("ERROR: Entrada Invalida en la linea "+lexico.getNrolinea());
		
		//lexico.setEntrada(' '); 
		return -1;
	}

}
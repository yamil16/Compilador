package Compilador;

public class AS6_EntregarTokenAsciiBufferSinConsumir extends Accion_Semantica {

	
	public int ejecutar(Lexico lexico) {
				return lexico.getBuffer_temporal().codePointAt(0);
	}

}

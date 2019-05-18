
package Compilador;
public class AS7_VaciarBufferTemporal extends Accion_Semantica{

	public int ejecutar(Lexico lexico) {
		lexico.setBuffer_temporal("");
		return 0;
	}

}

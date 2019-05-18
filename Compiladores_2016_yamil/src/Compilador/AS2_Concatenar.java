package Compilador;

public class AS2_Concatenar extends Accion_Semantica {
	public int ejecutar(Lexico lexico) {
		// TODO Auto-generated method stub
		lexico.setBuffer_temporal(lexico.getBuffer_temporal()+lexico.getEntrada());
		lexico.setPosEntrada(lexico.getPosEntrada()+1);
		return 0;
	}
	
}

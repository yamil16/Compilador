package Compilador;
public class AS5_ConsumirEntrada extends Accion_Semantica {
	@Override
	public int ejecutar(Lexico lexico) {
		lexico.setPosEntrada(lexico.getPosEntrada()+1);
		return 0;
	}

}

package Compilador;
public class Error1_EntradaInvalida extends Accion_Semantica{


	@Override
	public int ejecutar(Lexico lexico) {
		//lexico.setEstado(0);
		//lexico.setPosEntrada(lexico.getPosEntrada()+1); //mejor que no avance, por si viene EOF por ejemplo
		lexico.setBuffer_temporal("");
		Compilador.errores.add("ERROR: Entrada Invalida en la linea "+lexico.getNrolinea());
	
		lexico.setEntrada(' '); 
		return -1;
	}

}

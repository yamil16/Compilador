package Compilador;
public class Error2_ColocaIgual extends Accion_Semantica{
	public int ejecutar(Lexico lexico) {
	
		Compilador.warnings.add("WARNING, se coloco un igual faltante en la linea "+lexico.getNrolinea());
	
		lexico.setEntrada('=');
		//lexico.setEstado(-1);
		if(lexico.getBuffer_temporal().charAt(0)==':')
			return Compilador.ASIGNACION;
		else
			return Compilador.DISTINTO;
	}

}

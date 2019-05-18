package Compilador;

public class AS8_EntregarTokenSinConsumir  extends Accion_Semantica {
	public int ejecutar(Lexico lexico) {
//		lexico.setPosEntrada(lexico.getPosEntrada()+1);
//		if (lexico.getEntrada()== '$') // $
//			return (int)lexico.getEntrada();
//		else					
		if(lexico.getBuffer_temporal()=="")
			return (int)lexico.getEntrada(); //supongo que esto devuelve el codigo ascii
		else //para el caso del --
		{	
			if ( lexico.getBuffer_temporal().codePointAt(0)==45) //menos
				return Compilador.MENOSMENOS;
			else
			if ( lexico.getBuffer_temporal().codePointAt(0)==58) //:
				return Compilador.ASIGNACION;
			else
			if ( lexico.getBuffer_temporal().codePointAt(0)==33) //!
				return Compilador.DISTINTO;
			else //
			if ( lexico.getBuffer_temporal().codePointAt(0)==60) //<=
				return Compilador.MENORIGUAL;
			else
			if ( lexico.getBuffer_temporal().codePointAt(0)==62) //>= 62
				return Compilador.MAYORIGUAL;
			else 
			if ( lexico.getBuffer_temporal().codePointAt(lexico.getBuffer_temporal().length()-1)==48) {// cero //ver si es lenght-1
				Lexico.nrolinea++;
				return Compilador.ANOTACION_CERO;
			}
			else 
			if ( lexico.getBuffer_temporal().codePointAt(lexico.getBuffer_temporal().length()-1)==49){ // uno
				Lexico.nrolinea++;
				return Compilador.ANOTACION_UNO;			
			}
			else{
				System.out.println("ERROR DETECTADO EN LA LINEA "+lexico.getNrolinea()+" SE ESPERABA MENOS, ASIGNACION, DISTINO, MENOR IGUAL, ANOTACIONES CERO Y UNO");
				return -1;
			}
		}
		
	}




}

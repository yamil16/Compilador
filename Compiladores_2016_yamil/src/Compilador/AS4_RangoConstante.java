package Compilador;
public class AS4_RangoConstante extends Accion_Semantica{
	// ver si puse bien los rangos
		public int ejecutar(Lexico lexico) {
			// TODO Auto-generated method stub
			int tamaño=lexico.getBuffer_temporal().length();
			if(lexico.getBuffer_temporal().charAt(1)=='i'){	// si es una contante entera		
				lexico.setBuffer_temporal(lexico.getBuffer_temporal().substring(2, tamaño));	//me quedo con el valor
				int numEntero = Integer.parseInt(lexico.getBuffer_temporal()); 
				
				if ((numEntero>32767) || (numEntero<-32768)){
					if(numEntero>0){ //me quedo con el mayor positivo
						numEntero=32767;
						Compilador.warnings.add("WARNING, CONSTANTE ENTERA supera el maximo rango de valores, se seteo al maximo valor de contante permitido. Linea: " + lexico.nrolinea);
											
					}
					else{ //me quedo con el menor negativo
						numEntero=-32768;
						Compilador.warnings.add("WARNING, CONSTANTE ENTERA supera el minimo rango de valores, se seteo al minimo valor de contante permitido. Linea: " + lexico.nrolinea);
										
					}
				}		
				
				String aux = "";
				aux = String.valueOf(numEntero);
				if (numEntero < 0){
					aux= ((Integer)numEntero).toString();
					aux = aux.substring(1, aux.length());
					aux = "_n" + aux;
				}
				lexico.setBuffer_temporal(aux);
				if(Compilador.T_simbolos.get(lexico.getBuffer_temporal() + Compilador.CONSTANTE_ENTERA)!=null){			
				
					return Compilador.T_simbolos.get(lexico.getBuffer_temporal() + Compilador.CONSTANTE_ENTERA).getToken();
				}
				else{				
					Tabla_Simbolos t= new Tabla_Simbolos();
					t.setToken(Compilador.CONSTANTE_ENTERA);
					t.setLexema(lexico.getBuffer_temporal());
					t.setInformacion("Constante entera");
					t.setTipo("Integer");
					Compilador.T_simbolos.put(t.getLexema() + t.getToken(), t);
				
					return Compilador.T_simbolos.get(lexico.getBuffer_temporal() + t.getToken()).getToken();
				}					
			}
			else //si es contante positiva
			{
				lexico.setBuffer_temporal(lexico.getBuffer_temporal().substring(3, tamaño));	//me quedo con el valor
				int numEntero = Integer.parseInt(lexico.getBuffer_temporal()); 
				if ((numEntero>65535) || (numEntero<0)){
					if(numEntero<0){ //me quedo con el menor negativo
						numEntero=0;
						Compilador.warnings.add("WARNING, CONSTANTE POSITIVA supera el minimo rango de valores, se seteo al minimo valor de contante permitido. Linea: " + lexico.nrolinea);
								
					}
					else{ //me quedo con el mayor positivo
						numEntero=65535;
						Compilador.warnings.add("WARNING, CONSTANTE POSITIVA supera el maximo rango de valores, se seteo al maximo valor de contante permitido. Linea: " + lexico.nrolinea);
											
					}
				}
				String aux = "";
				aux = String.valueOf(numEntero);
				lexico.setBuffer_temporal(aux);
				if(Compilador.T_simbolos.get(lexico.getBuffer_temporal() + Compilador.CONSTANTE_POSITIVA)!=null){
				
					return Compilador.T_simbolos.get(lexico.getBuffer_temporal() + Compilador.CONSTANTE_POSITIVA).getToken();
				}
				else{
						Tabla_Simbolos t= new Tabla_Simbolos();
						t.setToken(Compilador.CONSTANTE_POSITIVA);
						t.setLexema(lexico.getBuffer_temporal());
						t.setInformacion("Constante positiva");
						t.setTipo("Uinteger");
						Compilador.T_simbolos.put(lexico.getBuffer_temporal()+ Compilador.CONSTANTE_POSITIVA, t);
				
						return Compilador.T_simbolos.get(lexico.getBuffer_temporal()+ Compilador.CONSTANTE_POSITIVA).getToken();
					}			
			}
		}

	}

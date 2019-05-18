package Compilador;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;


public class GeneradorAssembler {
	private boolean[] registrosLibres;
	private boolean[] auxiliaresLibres;
	private ArrayList<Terceto> listaTercetos;
	private String label;
	
	public void ActualizarRegistro(Terceto t, int registro){
				t.setRegistro(registro);
		}
		
	public ArrayList<Terceto> getListaTercetos() {
		return listaTercetos;
	}

	public void setListaTercetos(ArrayList<Terceto> listaTercetos) {
		this.listaTercetos = listaTercetos;
	}

	public GeneradorAssembler(ArrayList<Terceto> listaTercetos) {
		this.registrosLibres = new boolean[4];//0=eax, 1=ebx, 2=ecx, 3=edx
		this.auxiliaresLibres = new boolean[4];
		this.listaTercetos=listaTercetos;
		for(int i=0;i<registrosLibres.length;i++){
			registrosLibres[i]=true;
			auxiliaresLibres[i] = true;
		}
	}
	
	public void generarAssembler() throws IOException{
		FileWriter f;
		f = new FileWriter("TextoAssembler.asm");
		BufferedWriter bw = new BufferedWriter(f);
		PrintWriter arc = new PrintWriter(bw);
		
		arc.append(".386" + "\r\n");
		arc.append(".model flat, stdcall"+ "\r\n");
		arc.append("option casemap :none"+ "\r\n");		
		arc.append("include \\masm32\\include\\windows.inc"+ "\r\n");		
		arc.append("include \\masm32\\include\\kernel32.inc"+ "\r\n");		
		arc.append("include \\masm32\\include\\user32.inc"+ "\r\n");		
		arc.append("includelib \\masm32\\lib\\kernel32.lib"+ "\r\n");		
		arc.append("includelib \\masm32\\lib\\user32.lib"+ "\r\n");	
		
		arc.append("\r\n" +".data"+ "\r\n");
		arc.append("@auxAx dw 0"+ "\r\n"); //la uso en multiplicacion y division cuando los registros estan ocupados				
		arc.append("@auxDx dw 0"+ "\r\n"); //la uso en multiplicacion y division cuando los registros estan ocupados

		Enumeration<String> e=Compilador.T_simbolos.keys();
		while (e.hasMoreElements()) {
			String clave = e.nextElement();
			if (Compilador.T_simbolos.get(clave.toString()).getToken() == 257){			
				if(Compilador.T_simbolos.get(clave).getUso().equals("prog@"))
					arc.append(Compilador.T_simbolos.get(clave).getUso()+ Compilador.T_simbolos.get(clave).getLexema()+" dw 0"+ "\r\n");
				else
					if (Compilador.T_simbolos.get(clave).getUso().equals("var@"))
						arc.append(Compilador.T_simbolos.get(clave).getUso() + Compilador.T_simbolos.get(clave).getLexema()+" dw 0"+ "\r\n");
					else
						if (Compilador.T_simbolos.get(clave).getUso().equals("mat@")){
							String lexema=Compilador.T_simbolos.get(clave).getLexema();
							if (Compilador.T_simbolos.get(clave).getElementoMatrizIJ(0,0) != 0){
								int [][] mat= Compilador.T_simbolos.get(clave).getMatriz();
								int filas=Compilador.T_simbolos.get(clave).getFilas();
								int col=Compilador.T_simbolos.get(clave).getColumnas();
								String aux="";						
								for(int i=0;i<filas;i++)
									for(int j=0;j<col;j++)
										if((i==filas-1)&&(j==col-1))
											aux+=mat[i][j];
										else
											aux+=mat[i][j]+",";
							arc.append(Compilador.T_simbolos.get(clave).getUso() + Compilador.T_simbolos.get(clave).getLexema()+" dw " + aux+ "\r\n");
							}
							else
								arc.append(Compilador.T_simbolos.get(clave).getUso() + Compilador.T_simbolos.get(clave).getLexema()+" dw 0"+ "\r\n");
						}
			}
			else{
				  if ((Compilador.T_simbolos.get(clave.toString()).getToken() == 276) ||((Compilador.T_simbolos.get(clave.toString()).getToken() > 266) && (Compilador.T_simbolos.get(clave.toString()).getToken() < 269))){
					  switch (Compilador.T_simbolos.get(clave).getToken()){
					  case 267:{ //CONSTANTE_Positiva
						  String valor =Compilador.T_simbolos.get(clave).getLexema();
						  arc.append("_ui"+valor+" dw "+Compilador.T_simbolos.get(clave.toString()).getLexema()+ "\r\n");
					  break;}
					  case 268:{ //CONSTANTE_ENTERA
						  String valor =Compilador.T_simbolos.get(clave).getLexema();
					  if((valor.codePointAt(0)==95) && (valor.codePointAt(1) == 110)){
							  String valor2=valor.substring(2, valor.length());
							  arc.append("_in"+valor2+" dw -"+ valor2+ "\r\n");
						  }
						  else{
							  arc.append("_i"+valor+" dw "+Compilador.T_simbolos.get(clave.toString()).getLexema()+ "\r\n");
						  }
					 break;}
					  case 276:{ //CADENA_MULTILINEA
						  String cadena =Compilador.T_simbolos.get(clave).getLexema();
						  String etiqueta=cadena.replaceAll("_", " ");
						  arc.append(cadena+" db '"+etiqueta+"', 0"+ "\r\n");
					  break;}
					  }
				  }	
			}
		}
		arc.append("$division_por_cero db 'division por Cero' , 0"+ "\r\n");
		arc.append("$overflow_suma db 'overflow de suma' , 0"+ "\r\n");
		arc.append("$programa_compila db 'Programa Compila' , 0"+ "\r\n");
		arc.append("$overflow_matriz db 'Se accedio a un elemento fuera del rango de la matriz' , 0"+ "\r\n");
		arc.append("$error_conversion db 'El valor del elemento no se puede convertir' , 0"+ "\r\n");
	
	//Codigo
	arc.append("\r\n" +".code"+ "\r\n");
			arc.append("DIVCERO:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $division_por_cero, addr $division_por_cero, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			arc.append("OVERFLOWSUMA:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $overflow_suma, addr $overflow_suma, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			
			arc.append("MATRIZ_FUERA_RANGO:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $overflow_matriz, addr $overflow_matriz, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			
			arc.append("ERRORENCONVERSION:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $error_conversion, addr $error_conversion, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			
			
		
			
		/*
			arc.append("OVERFLOWRESTA:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $overflow_resta, addr $overflow_resta, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
		*/	
			
	//Comienzo del programa
	arc.append("\r\n" +"START:"+ "\r\n");
	for (Terceto t:listaTercetos){
		if(t!=null){
		
			System.out.println("Estoy Obteniendo un Registro Libre quiero saber los estados para el terceto "+t.getLexema() );
			for(int i=0;i<registrosLibres.length;i++){
				System.out.println(" quiero saber los estados en la pos "+i+" , "+registrosLibres[i]);
			}
			System.out.println();
			System.out.println();
			
			generarCodigoEjecutable(t,arc);
		}	
	}
	arc.append("Invoke MessageBox, NULL, addr $programa_compila, addr $programa_compila, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+"\r\n");
			arc.append("END START"+ "\r\n");
			arc.close();
			bw.close();
	}
	
	public Integer obtenerRegistroLibre(){	
		if(registrosLibres[1])
			return 1;
		else
			if(registrosLibres[2])
				return 2;
			else
				if(registrosLibres[3])
					return 3;
				else
					if(registrosLibres[0])
						return 0;
					else
			return null;
	} 
	
	public String getNombreRegistro(int nro) {
		String aux;
		switch (nro){
		case 0: {aux = "A";		break;}
		case 1:{aux = "B";		break;}
		case 2:{aux = "C";		break;}
		case 3:{aux = "D";		break;}
		default :{ aux = null; break;}	
		}
		return aux;
	}
	
	
	
	public void GenerarAssemblerAsignacionVar(Terceto t, PrintWriter arc ) throws IOException{
		   if(!t.getOperando2().isTerceto()){ //es un token
		    String operando2 = t.getOperando2().getLexema(); 
		    String uso1 = Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getUso();
		    String uso2 = Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getUso();
		    Integer registro = obtenerRegistroLibre();
		    String nombreReg = getNombreRegistro(registro); 
		    if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getToken() == 257)
		    // arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+uso2 +operando2 + "\r\n");
		     arc.append("MOV " + nombreReg + "X, "+uso2 +operando2 + "\r\n");
		    else{
		     if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
		      if(operando2.codePointAt(0)==95){
		         String valor2=operando2.substring(2, operando2.length());
		       //  arc.append("MOV "+uso1+t.getOperando1().getLexema()+", " + "_in"+valor2+ "\r\n");
		         arc.append("MOV " + nombreReg + "X, " + "_in"+valor2+ "\r\n");
		        }
		        else
		       //  arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+"_i" +operando2+ "\r\n");
		         arc.append("MOV " + nombreReg + "X, "+"_i" +operando2+ "\r\n");
		     }else
		      // arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+"_ui" +operando2+ "\r\n");
		      arc.append("MOV " + nombreReg + "X, " + "_ui" + operando2 + "\r\n");
		    }
		    arc.append("MOV " + uso1+t.getOperando1().getLexema() + ", " + nombreReg +"X"+ "\r\n");
		   }
		   else {
		    //El 2° operando es un terceto
		    String lexemaTercetoOperando2=t.getOperando2().getLexemaGeneradorNumero();// .getLexemaGenerador();
		    int nrotercetoOperando2 = (Integer.parseInt(lexemaTercetoOperando2.substring(1, lexemaTercetoOperando2.length() - 1)));
		    int registroOperando2=listaTercetos.get(nrotercetoOperando2).getRegistro();
		    String nombreRegOperando2 = getNombreRegistro(registroOperando2);   
		    arc.append("MOV "+Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getUso()+t.getOperando1().getLexema()+", "+nombreRegOperando2+"X"+ "\r\n");
		    System.out.println("reg" + registroOperando2);
		    registrosLibres[registroOperando2] = true; 
		   }
		  }
	
	
	
	
	
	/*
	public void GenerarAssemblerAsignacionVar(Terceto t, PrintWriter arc ) throws IOException{
			if(!t.getOperando2().isTerceto()){ //es un token
				String operando2 = t.getOperando2().getLexema();	
				String uso1 = Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getUso();
				String uso2 = Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getUso();
				Integer registro = obtenerRegistroLibre();
				String nombreReg = getNombreRegistro(registro);	
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getToken() == 257)
				//	arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+uso2 +operando2 + "\r\n");
					arc.append("MOV " + nombreReg + "X, "+uso2 +operando2 + "\r\n");
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							  String valor2=operando2.substring(2, operando2.length());
							//  arc.append("MOV "+uso1+t.getOperando1().getLexema()+", " + "_in"+valor2+ "\r\n");
							  arc.append("MOV " + nombreReg + "X, " + "_in"+valor2+ "\r\n");
						  }
						  else
							//  arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+"_i" +operando2+ "\r\n");
							  arc.append("MOV " + nombreReg + "X, "+"_i" +operando2+ "\r\n");
					}else
						// arc.append("MOV "+uso1+t.getOperando1().getLexema()+", "+"_ui" +operando2+ "\r\n");
						arc.append("MOV " + nombreReg + "X, " + "_ui" + operando2 + "\r\n");
				}
				arc.append("MOV " + uso1+t.getOperando1().getLexema() + ", " + nombreReg +"X"+ "\r\n");
			}
			else {
				//El 2° operando es un terceto
				String lexemaTercetoOperando2=t.getOperando2().getLexemaGeneradorNumero();// .getLexemaGenerador();
				int nrotercetoOperando2 = (Integer.parseInt(lexemaTercetoOperando2.substring(1, lexemaTercetoOperando2.length() - 1)));
				int registroOperando2=listaTercetos.get(nrotercetoOperando2).getRegistro();
				String nombreRegOperando2 = getNombreRegistro(registroOperando2);			
				arc.append("MOV "+Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getUso()+t.getOperando1().getLexema()+", "+nombreRegOperando2+"X"+ "\r\n");
				System.out.println("reg" + registroOperando2);
				registrosLibres[registroOperando2] = true;	
			}
		}
	*/
	public void GenerarAssemblerAsignacion(Terceto t, PrintWriter arc ) throws IOException{
			if(!t.getOperando1().isTerceto())				
				GenerarAssemblerAsignacionVar(t, arc);	
//			else
//				GenerarAssemblerAsignacionMat(t,arc);	
	}

	public void GenerarAssemblerOperacion1erPrecedencia(Terceto t, PrintWriter arc,String Operador,boolean EsResta) throws IOException {
		if(EsResta){
			if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
				//situacion 4b (-,Variable,Registro)
							
					int nroRegistroLibre=obtenerRegistroLibre();
					String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
					registrosLibres[nroRegistroLibre] = false;
					t.setRegistro(nroRegistroLibre);
				
					Token t1= (Token) t.getOperando1();		
					String operando1=t1.getLexema();
								
					if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257){
					
							String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
							arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");	
					}		
					else{
						
						if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
							if(operando1.codePointAt(0)==95){
								  String valor1=operando1.substring(2, operando1.length());
								  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
							  }
							 else
								  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
						}else
							 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
					}			
					String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
					int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
					int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
					String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
					
					arc.append("SUB "+nombreRegistroLibre+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
					registrosLibres[nroRegistroTercetoOperando2] = true;			
					
				}	
		}
		else{
			if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
				String lexemaTerceto=t.getOperando2().getLexemaGeneradorNumero();
				int nroterceto = (Integer.parseInt(lexemaTerceto.substring(1, lexemaTerceto.length() - 1)));
				int registroOperando2=listaTercetos.get(nroterceto).getRegistro();
				t.setRegistro(registroOperando2);
				String nombreReg = getNombreRegistro(registroOperando2);
				String operando1=t.getOperando1().getLexema();
			//nuevo
				Token t1= (Token) t.getOperando1();
				if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)					
					arc.append("ADD "+nombreReg+"X, "+Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso()+operando1 + "\r\n");
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando1.codePointAt(0)==95){
							String valor1=operando1.substring(2, operando1.length());
						  arc.append("ADD "+nombreReg+"X, _in"+valor1 + "\r\n");
					  }
					  else
						  arc.append("ADD "+nombreReg+"X, _i"+operando1 + "\r\n");
					}
					else
						 arc.append("ADD "+nombreReg+"X, "+"_ui" +operando1+ "\r\n");
				}
			
			}
				 
			
		}
		//Casos normales
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//son 2 variables y o cte 
			//situacion 1 (OP,Variable,Variable)
		
			Token t1= (Token) t.getOperando1();
			Token t2= (Token) t.getOperando2();
			
			int nroRegistroLibre=obtenerRegistroLibre();
			String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
			registrosLibres[nroRegistroLibre] = false;
			t.setRegistro(nroRegistroLibre);
		
			String operando1=t.getOperando1().getLexema();
			
			String operando2=t.getOperando2().getLexema();
		
			
		if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257){						
			String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
			arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");	
		}		
		else{
			
			if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
				if(operando1.codePointAt(0)==95){
					  String valor1=operando1.substring(2, operando1.length());
					  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
				  }
				 else
					  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
			}else
				 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
		}
		
		if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){
		
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			arc.append(Operador+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");
		}			
		else{
		
			if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
		
				if(operando2.codePointAt(0)==95){
		
					  String valor2=operando2.substring(2, operando2.length());
					  arc.append(Operador+nombreRegistroLibre+"X, _in"+valor2 + "\r\n");
				  }
				  else
					  arc.append(Operador+nombreRegistroLibre+"X, _i"+operando2 + "\r\n");
			}
			else
				arc.append(Operador+nombreRegistroLibre+"X, "+"_ui" +operando2+ "\r\n");
		}
			
				
		}
		else
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (OP,Registro,Variable)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			
			
			Token t2= (Token) t.getOperando2();
			String operando2=t2.getLexema();
			
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){	
				String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
				arc.append(Operador+nombreRegistroTerceto+"X, "+uso2+operando2 + "\r\n");
			}
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						String valor2=operando2.substring(2, operando2.length());
						arc.append(Operador+nombreRegistroTerceto+"X, _in"+valor2 + "\r\n");
				  }
				  else
					  arc.append(Operador+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
				}
				else
					arc.append(Operador+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
			}		
			t.setRegistro(nroRegistroTerceto);
		}
		else
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (OP,Registro,Registro)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTercetoOperando1=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTercetoOperando1=getNombreRegistro(nroRegistroTercetoOperando1);
						
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			
			t.setRegistro(nroRegistroTercetoOperando1);
			arc.append(Operador+nombreRegistroTercetoOperando1+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
			registrosLibres[nroRegistroTercetoOperando2] = true;		
		}
		if(!EsResta){
			if(t.getTipo()=="Integer")
				arc.append("JO OVERFLOWSUMA" + "\r\n");	 
			else
				arc.append("JC OVERFLOWSUMA" + "\r\n");
		}		
	}
	
	
	public void GenerarAssemblerOperacionEspecial2daPrecedencia(Terceto t,PrintWriter arc, String Operador, boolean Esdivision ) throws IOException{

		if(Esdivision){
			//hago el chequeo de la division por cero
			if(t.getOperando2().isTerceto()){ //es un terceto
				
				String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
				int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
				int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
				String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);	
				arc.append("CMP "+nombreRegistroTercetoOperando2+"X, 0" + "\r\n");
				arc.append("JE DIVCERO" + "\r\n");
			}
			else{ //es un token
				Token t2= (Token) t.getOperando2();
				int nroRegistroLibre=obtenerRegistroLibre();
				String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);				
				String operando2=t.getOperando2().getLexema();
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){ //es un id			
					String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
					arc.append("MOV "+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");				
				}
				else{
					//verico si el tipo de cte			
					if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							  String valor1=operando2.substring(2, operando2.length());
							  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
						  }
						 else
							  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando2+ "\r\n");
					}else
						 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando2+ "\r\n");
					
				}
				arc.append("CMP "+nombreRegistroLibre+"X, 0" + "\r\n");
				arc.append("JE DIVCERO" + "\r\n");
			}			
		
			
			
		}
	
		
		
		
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){			 
			//situacion 1 (OP,Variable,Variable)
			
			Token t1= (Token) t.getOperando1();
			Token t2= (Token) t.getOperando2();
			
			int nroRegistroLibre=obtenerRegistroLibre();
			
			String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
			registrosLibres[nroRegistroLibre] = false;
			t.setRegistro(nroRegistroLibre);
		
			String operando1=t1.getLexema();
			String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
			String operando2=t2.getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			
			if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
				arc.append("MOV "+"AX, "+uso1+operando1 + "\r\n");						
			else{			
			if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
				if(operando1.codePointAt(0)==95){
					  String valor1=operando1.substring(2, operando1.length());
					  arc.append("MOV "+"AX, " + "_in"+valor1+ "\r\n");
				  }
				 else
					  arc.append("MOV "+"AX, "+"_i" +operando1+ "\r\n");
			}
			else
				 arc.append("MOV "+"AX, "+"_ui" +operando1+ "\r\n");
			}
			
			if(Esdivision)			
				arc.append("CWD "+"\r\n"); //Extiendo el signo en DX:AX
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257)						
				arc.append("MOV "+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
					  String valor2=operando2.substring(2, operando2.length());
					  arc.append("MOV "+nombreRegistroLibre+"X, _in"+valor2 + "\r\n");
					}
					else
					  arc.append("MOV "+nombreRegistroLibre+"X, _i"+operando2 + "\r\n");
				}
				else
					arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando2+ "\r\n");
			}
			//hago la operacion
			arc.append(Operador+nombreRegistroLibre+"X "+"\r\n");
			if (Operador == "IMUL "){
				arc.append("AND " + "DX" + ", 32768" + "\r\n");
				arc.append("OR " + "AX" + ", DX" + "\r\n");
			}
			arc.append("MOV "+nombreRegistroLibre+"X, AX "+ "\r\n");
		
		}
		else
			
			
			
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (OP,Registro,Variable)
	
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			
			
			Token t2= (Token) t.getOperando2();
			String operando2=t2.getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			Integer registroDiv=obtenerRegistroLibre();
		
			String nombreRegDispobible= getNombreRegistro(registroDiv);
			t.setRegistro(registroDiv);
			registrosLibres[registroDiv] = false;
			
			if(nombreRegistroTerceto.equals("D"))
				arc.append("MOV "+"AX, "+"@auxDx"+ "\r\n");
			else
			if(nombreRegistroTerceto.equals("A"))
				arc.append("MOV "+"AX, "+"@auxAx"+ "\r\n");
			else
				arc.append("MOV "+"AX, "+nombreRegistroTerceto+"X"+ "\r\n");
			
			
			
			if(Esdivision)			
				arc.append("CWD "+"\r\n"); //Extiendo el signo en DX:AX
			
			
			if((!(nombreRegDispobible.equals("A")))&&(!(nombreRegDispobible.equals("D")))){
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){				
					
					arc.append("MOV "+nombreRegDispobible+"X, "+uso2+operando2 + "\r\n");				
					arc.append(Operador+nombreRegDispobible+"X "+"\r\n");				
					if (Operador == "IMUL "){
						arc.append("AND " + "DX" + ", 32768" + "\r\n");
						arc.append("OR " + "AX" + ", DX" + "\r\n");
					}
				}
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							String valor2=operando2.substring(2, operando2.length());												
							arc.append("MOV "+nombreRegDispobible+"X, _in"+valor2 + "\r\n");				
							arc.append(Operador+nombreRegDispobible+"X "+"\r\n");				
							if (Operador == "IMUL "){
								arc.append("AND " + "DX" + ", 32768" + "\r\n");
								arc.append("OR " + "AX" + ", DX" + "\r\n");
							}
					  }
					  else{
						  //arc.append(Operador+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
						  	arc.append("MOV "+nombreRegDispobible+"X, _i"+operando2 + "\r\n");				
						  	arc.append(Operador+nombreRegDispobible+"X "+"\r\n");
						  	if (Operador == "IMUL "){
								arc.append("AND " + "DX" + ", 32768" + "\r\n");
								arc.append("OR " + "AX" + ", DX" + "\r\n");
							}
					  }  
					}
					else{
						//arc.append(Operador+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
						arc.append("MOV "+nombreRegDispobible+"X, _ui"+operando2 + "\r\n");				
					  	arc.append(Operador+nombreRegDispobible+"X "+"\r\n");
					  	if (Operador == "IMUL "){
							arc.append("AND " + "DX" + ", 32768" + "\r\n");
							arc.append("OR " + "AX" + ", DX" + "\r\n");
						}
					}	
				}
			}else{ //ES EL REGISTRODISPONIBLE A
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){				
													
					arc.append(Operador+" "+uso2+operando2 +"\r\n");				
					if (Operador == "IMUL "){
						arc.append("AND " + "DX" + ", 32768" + "\r\n");
						arc.append("OR " + "AX" + ", DX" + "\r\n");
					}
				}
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							String valor2=operando2.substring(2, operando2.length());		
											
							arc.append(Operador+" _in"+valor2+"\r\n");				
							if (Operador == "IMUL "){
								arc.append("AND " + "DX" + ", 32768" + "\r\n");
								arc.append("OR " + "AX" + ", DX" + "\r\n");
							}
					  }
					  else{
						  //arc.append(Operador+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
						  					
						  	arc.append(Operador+" _i"+operando2+"\r\n");
						  	if (Operador == "IMUL "){
								arc.append("AND " + "DX" + ", 32768" + "\r\n");
								arc.append("OR " + "AX" + ", DX" + "\r\n");
							}
					  }  
					}
					else{
						//arc.append(Operador+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
										
					  	arc.append(Operador+" _ui"+operando2 +"\r\n");
					  	if (Operador == "IMUL "){
							arc.append("AND " + "DX" + ", 32768" + "\r\n");
							arc.append("OR " + "AX" + ", DX" + "\r\n");
						}
					}
				}	
			}
				
	
			//guardo el resultado en el registro del terceto
		//	arc.append("MOV "+nombreRegDispobible+"X, AX "+ "\r\n");
			
			
			//ver si esto va
		
				arc.append("MOV "+nombreRegDispobible+"X, " +" AX "+ "\r\n");
			
			
		
			registrosLibres[nroRegistroTerceto] = true;
			
		}
		else
			
			
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (OP,Registro,Registro)
		
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTercetoOperando1=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTercetoOperando1=getNombreRegistro(nroRegistroTercetoOperando1);
						
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			
			
			if(nombreRegistroTercetoOperando1.equals("D"))
				arc.append("MOV "+"AX, "+"@auxDx"+ "\r\n");
			else
			if(nombreRegistroTercetoOperando1.equals("A"))
				arc.append("MOV "+"AX, "+"@auxAx"+ "\r\n");
			else
				arc.append("MOV "+"AX, "+nombreRegistroTercetoOperando1+"X"+ "\r\n");
			
			
			
			//arc.append("MOV "+"AX, "+nombreRegistroTercetoOperando1+"X"+ "\r\n");
			
			if(Esdivision)			
				arc.append("CWD "+"\r\n"); //Extiendo el signo en DX:AX
			
			 //Este es un caso especial capaz que es de gusto
			if(nombreRegistroTercetoOperando2.equals("A")) //OPERO POR AUX
				arc.append(Operador+"@auxAx" + "\r\n");
			else	
			if(nombreRegistroTercetoOperando2.equals("D")) //OPERO POR AUX
				arc.append(Operador+"@auxDx" + "\r\n");
			else //OPERO normal
				arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			if (Operador == "IMUL "){
				arc.append("AND " + "DX" + ", 32768" + "\r\n");
				arc.append("OR " + "AX" + ", DX" + "\r\n");
			}
			//guardo el resultado en el registro del terceto
			//arc.append("MOV "+nombreRegistroTercetoOperando1+"X, " +" AX "+ "\r\n");
			
			if(nombreRegistroTercetoOperando1.equals("D"))
				arc.append("MOV "+"@auxDx, "+" AX "+ "\r\n");
			else
			if(nombreRegistroTercetoOperando1.equals("A"))
				arc.append("MOV "+"@auxAx, "+" AX "+ "\r\n");
			else
				arc.append("MOV "+nombreRegistroTercetoOperando1+"X, " +" AX "+ "\r\n");
			

			
			t.setRegistro(nroRegistroTercetoOperando1);
			
			registrosLibres[nroRegistroTercetoOperando2] = true;		
		}
		
		if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
		//situacion 4 (OP,Variable,Registro) es la misma para la multiplicion y division porque necito asegurarme que el primer operaando este en AX
		
			int nroRegistroLibre=obtenerRegistroLibre();
			String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
			registrosLibres[nroRegistroLibre] = false;
			t.setRegistro(nroRegistroLibre);
		
			Token t1= (Token) t.getOperando1();		
			String operando1=t1.getLexema();
			String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
						
			if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
					arc.append("MOV "+"AX, "+uso1+operando1 + "\r\n");						
			else{
				
				if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando1.codePointAt(0)==95){
						  String valor1=operando1.substring(2, operando1.length());
						  arc.append("MOV "+"AX, " + "_in"+valor1+ "\r\n");
					  }
					 else
						  arc.append("MOV "+"AX, "+"_i" +operando1+ "\r\n");
				}else
					 arc.append("MOV "+"AX, "+"_ui" +operando1+ "\r\n");
			}			
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			

			if(Esdivision)			
				arc.append("CWD "+"\r\n"); //Extiendo el signo en DX:AX
			//arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			 //Este es un caso especial capaz que es de gusto
			if(nombreRegistroTercetoOperando2.equals("A")) //DIVIDO POR AUX
				arc.append(Operador+"@auxAx" + "\r\n");
			else	
			if(nombreRegistroTercetoOperando2.equals("D")) //DIVIDO POR AUX
				arc.append(Operador+"@auxDx" + "\r\n");
			else //divido normal
				arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			if (Operador == "IMUL "){
				arc.append("AND " + "DX" + ", 32768" + "\r\n");
				arc.append("OR " + "AX" + ", DX" + "\r\n");
			}
			
			//arc.append("MOV "+nombreRegistroLibre+"X, " +"AX"+ "\r\n");
			/*if(nombreRegistroLibre.equals("D"))
				arc.append("MOV "+"@auxDx, "+" AX "+ "\r\n");
			else
			if(nombreRegistroLibre.equals("A"))
				arc.append("MOV "+"@auxAx, "+" AX "+ "\r\n");
			else*/
				arc.append("MOV "+nombreRegistroLibre+"X, " +" AX "+ "\r\n");
			
			registrosLibres[nroRegistroTercetoOperando2] = true;			
			
		}	
		
					
				
		
	}
	public void GenerarAssemblerOperacion2daPrecedencia(Terceto t, PrintWriter arc,boolean Esdivision) throws IOException {
		/*
		if((t.getOperando1().isTerceto())){
			//situacion 2 (OP,Registro,Variable)
	
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			if ((nombreRegistroTerceto == "A") || (nombreRegistroTerceto == "DX"))
				System.out.println("");
		}
		
		if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 4 (OP,Variable,Registro) es la misma para la multiplicion y division porque necito asegurarme que el primer operaando este en AX
			
				int nroRegistroLibre=obtenerRegistroLibre();
				String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
				
		}		
		
		
		*/
		
		
		
		
		
		
		boolean DisponibleAX=false;
		boolean DisponibleDX=false;
		if (registrosLibres[0]) { //esta disponible Ax
			t.setRegistro(0);
			//registrosLibres[0] = false;
			DisponibleAX=true;
			if (registrosLibres[3]) { //esta disponible DX
				//registrosLibres[3] = false;
				DisponibleDX=true;
			}
			else{ //esta disponible AX y NO esta disponible DX				
				  arc.append("MOV @auxDx, DX"+ "\r\n");
				 // arc.append("MOV DX, 0"+ "\r\n");
			}
		}	
		else{ //NO esta disponible AX
			if (registrosLibres[3]) { //esta disponible DX
				//registrosLibres[3] = false;
				
				/*
				if((t.getOperando1().isTerceto())){				
			
					String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
					int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
					int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
					String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
					if ((nombreRegistroTerceto == "A")){
						int nroRegistroLibre=obtenerRegistroLibre();
						String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
						arc.append("MOV "+nombreRegistroLibre+"X, AX"+ "\r\n");
						listaTercetos.get(nroTercetoOperando1).setRegistro(nroRegistroLibre);
						
						
					}
						
				}
				*/
				
				
				
				 DisponibleDX=true;				
				 arc.append("MOV @auxAx, AX"+ "\r\n");
				 //arc.append("MOV DX, 0"+ "\r\n");
			}
			else{ //NO esta disponible AX y NO esta disponible DX
				arc.append("MOV @auxAx, AX"+ "\r\n"); 
				arc.append("MOV @auxDx, DX"+ "\r\n");				 
			}
		}
		
		if(Esdivision==false){
			if(t.getTipo()=="Integer"){
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"IMUL ",Esdivision);
			}	
			else{
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"MUL ",Esdivision);
			}
		}
		else{
			if(t.getTipo()=="Integer")
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"IDIV ",Esdivision);
			else
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"DIV ",Esdivision);
		}
		
		
		//Reestablesco los valores de los registros con las variables auxiliares
				if((DisponibleAX)&&(!DisponibleDX)){
					arc.append("MOV DX, @auxDx"+ "\r\n");
				
				}	
				else
				if(!(DisponibleAX)&&(DisponibleDX)){
					arc.append("MOV AX, @auxAx"+ "\r\n");
				
				}	
				else
				if(!(DisponibleAX)&&!(DisponibleDX)){
					arc.append("MOV AX, @auxAx"+ "\r\n");
					arc.append("MOV DX, @auxDx"+ "\r\n");
				
				}
				/*
				
				if((DisponibleAX)){
					registrosLibres[0] = true;
					
				}
				if((DisponibleDX)){
					registrosLibres[3] = true;
				}
				*/
	}

	
	public void GenerarAssemblerBMenor(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JB Label"+nombre2 +"\r\n");
		else
			arc.append("JL Label"+nombre2 +"\r\n");
	}
	public void GenerarAssemblerBMenorIgual(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JBE Label"+nombre2 +"\r\n");
		else
			arc.append("JLE Label"+nombre2 +"\r\n");
	}
	public void GenerarAssemblerBIgual(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JE Label"+nombre2 +"\r\n");
		else
			arc.append("JE Label"+nombre2 +"\r\n");
	}
	public void GenerarAssemblerBDistinto(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JNE Label"+nombre2 +"\r\n");
		else
			arc.append("JNE Label"+nombre2 +"\r\n");
	}
	public void GenerarAssemblerBMayor(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JA Label"+nombre2 +"\r\n");
		else
			arc.append("JG Label"+nombre2 +"\r\n");
	}
	public void GenerarAssemblerBMayorIgual(Terceto t, PrintWriter arc ) throws IOException{
		Terceto t1=(Terceto) t.getOperando1();
		String tipo=t1.getTipo();	
		String nombre=t.getOperando2().getLexema();			
//		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, nombre.length()-1);
		if (tipo == "Uinteger")
			arc.append("JAE Label"+nombre2 +"\r\n");
		else
			arc.append("JGE Label"+nombre2 +"\r\n");
	}
	
	public void GenerarAssemblerBI(Terceto t, PrintWriter arc) throws IOException {
		String nombre=t.getOperando1().getLexema();			
		int aux2=nombre.lastIndexOf(')');
		String nombre2=nombre.substring(1, aux2);	
		arc.append("JMP Label"+nombre2+"\r\n");
		//bw.write("JMP Label"+t.getOperando1().getLexemaGenerador());		
	}
	
	public void GenerarAssemblerComparaciones(Terceto t, PrintWriter arc) throws IOException {
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 1 (OP,Variable,Variable)
			
			Token t1= (Token) t.getOperando1();
			Token t2= (Token) t.getOperando2();
			
			int nroRegistroLibre=obtenerRegistroLibre();
			String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
			registrosLibres[nroRegistroLibre] = false;
			t.setRegistro(nroRegistroLibre);
		
			String operando1=t.getOperando1().getLexema();
			String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
			String operando2=t.getOperando2().getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
					arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");						
			else{
				
				if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando1.codePointAt(0)==95){
						  String valor1=operando1.substring(2, operando1.length());
						  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
					  }
					 else
						  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
				}else
					 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
			}
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257)						
				arc.append("CMP "+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						  String valor2=operando2.substring(2, operando2.length());
						  arc.append("CMP "+nombreRegistroLibre+"X, _in"+valor2 + "\r\n");
					  }
					  else
						  arc.append("CMP "+nombreRegistroLibre+"X, _i"+operando2 + "\r\n");
				}
				else
					arc.append("CMP "+nombreRegistroLibre+"X, "+"_ui" +operando2+ "\r\n");
			}
			registrosLibres[nroRegistroLibre] = true;
		}
		else
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (OP,Registro,Variable)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			
			
			Token t2= (Token) t.getOperando2();
			String operando2=t2.getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){					
				arc.append("CMP "+nombreRegistroTerceto+"X, "+uso2+operando2 + "\r\n");
			}
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						String valor2=operando2.substring(2, operando2.length());
						arc.append("CMP "+nombreRegistroTerceto+"X, _in"+valor2 + "\r\n");
				  }
				  else
					  arc.append("CMP "+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
				}
				else
					arc.append("CMP "+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
			}		
			t.setRegistro(nroRegistroTerceto);
			registrosLibres[nroRegistroTerceto] = true;
		}
		else
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (OP,Registro,Registro)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTercetoOperando1=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTercetoOperando1=getNombreRegistro(nroRegistroTercetoOperando1);
						
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			
			t.setRegistro(nroRegistroTercetoOperando1);
			arc.append("CMP "+nombreRegistroTercetoOperando1+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
			registrosLibres[nroRegistroTercetoOperando2] = true;		
		}
		else
			if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
				//situacion 4b (op,Variable,Registro)
							
					int nroRegistroLibre=obtenerRegistroLibre();
					String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
					registrosLibres[nroRegistroLibre] = false;
					t.setRegistro(nroRegistroLibre);
				
					Token t1= (Token) t.getOperando1();		
					String operando1=t1.getLexema();
					String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
								
					if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
							arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");						
					else{
						
						if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
							if(operando1.codePointAt(0)==95){
								  String valor1=operando1.substring(2, operando1.length());
								  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
							  }
							 else
								  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
						}else
							 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
					}			
					String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
					int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
					int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
					String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
					
					arc.append("CMP "+nombreRegistroLibre+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
					registrosLibres[nroRegistroTercetoOperando2] = true;			
					registrosLibres[nroRegistroLibre] = true;
				}	
			
	
	}
	
	public void GenerarAssemblerConversiones(Terceto t, PrintWriter arc){
		  if (t.getOperando1().isTerceto()){
		  String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
		  int nroTercetoOperando1= (Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1))); 
		  int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();  
		  String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
		  t.setRegistro(nroRegistroTerceto);
		  System.out.println("reg " +nroRegistroTerceto);
		  arc.append("AND "+nombreRegistroTerceto+"X, "+"32768"+ "\r\n");
		  arc.append("JNZ ERRORENCONVERSION"+ "\r\n");
		  }else{

		   int nroRegistroLibre=obtenerRegistroLibre();
		   String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
		   registrosLibres[nroRegistroLibre] = false;
		   t.setRegistro(nroRegistroLibre);
		   System.out.println("reg " +nroRegistroLibre);
		   
		   Token t1 = (Token) t.getOperando1();
		   String operando1=t.getOperando1().getLexema();
		   String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();

		   if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)      
		     arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");      
		   else{
		    
		    if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
		     if(operando1.codePointAt(0)==95){
		        String valor1=operando1.substring(2, operando1.length());
		        arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
		       }
		      else
		        arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
		    }else
		      arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
		   }
		   
		   arc.append("AND "+nombreRegistroLibre+"X, "+"32768" + "\r\n");
		   arc.append("JNZ ERRORENCONVERSION"+ "\r\n");
		  }
		   
		 }
	
	
	
	/*
	public void GenerarAssemblerConversiones(Terceto t, PrintWriter arc){
		if (t.getOperando1().isTerceto()){
		String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
		int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
		int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
		String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
		t.setRegistro(nroRegistroTerceto);
		arc.append("AND "+nombreRegistroTerceto+"X, "+"32768"+ "\r\n");
		arc.append("JNZ ERRORENCONVERSION"+ "\r\n");
		}else{

			int nroRegistroLibre=obtenerRegistroLibre();
			String nombreRegistroLibre=getNombreRegistro(nroRegistroLibre);
			registrosLibres[nroRegistroLibre] = false;
			t.setRegistro(nroRegistroLibre);
		
			Token t1 = (Token) t.getOperando1();
			String operando1=t.getOperando1().getLexema();
			String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();

			if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
					arc.append("MOV "+nombreRegistroLibre+"X, "+uso1+operando1 + "\r\n");						
			else{
				
				if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando1.codePointAt(0)==95){
						  String valor1=operando1.substring(2, operando1.length());
						  arc.append("MOV "+nombreRegistroLibre+"X, " + "_in"+valor1+ "\r\n");
					  }
					 else
						  arc.append("MOV "+nombreRegistroLibre+"X, "+"_i" +operando1+ "\r\n");
				}else
					 arc.append("MOV "+nombreRegistroLibre+"X, "+"_ui" +operando1+ "\r\n");
			}
			
			arc.append("AND "+nombreRegistroLibre+"X, "+"32768" + "\r\n");
			arc.append("JNZ ERRORENCONVERSION"+ "\r\n");
		}
			
	}
	
	
	
	*/
	private void generarCodigoEjecutable(Terceto t, PrintWriter arc) throws IOException {
		System.out.println("terceto " + t.getLexema());
		switch(t.getOperador()){
			case "+":{
				//GenerarAssemblerSuma(t,arc);
				GenerarAssemblerOperacion1erPrecedencia(t,arc,"ADD ",false);
			break;}
			case "-":{
				GenerarAssemblerOperacion1erPrecedencia(t,arc,"SUB ",true);
			break;}
			case "/":{
				GenerarAssemblerOperacion2daPrecedencia(t,arc,true);
			break;}
			case "*":{
				GenerarAssemblerOperacion2daPrecedencia(t,arc,false);
			break;}
			case ":=":{
				GenerarAssemblerAsignacion(t,arc);
			break;}
			case "PRINT":{
			arc.append("Invoke MessageBox, NULL, addr "+t.getOperando1().getLexema()+", addr "+t.getOperando1().getLexema()+", MB_OK" + "\r\n");
			break;}
			case "BI":{
				GenerarAssemblerBI(t, arc);		
				break;}
			case "B<":{ //para el for
				GenerarAssemblerBMenor(t, arc);
				break;}
			case "B<=":{ //para el for
				GenerarAssemblerBMenorIgual(t, arc);
				break;}
			case "B=":{ //para el for
				GenerarAssemblerBIgual(t, arc);
				break;}
			case "B!=":{ //para el for
				GenerarAssemblerBDistinto(t, arc);
				break;}
			case "B>":{ //para el for
				GenerarAssemblerBMayor(t, arc);
				break;}
			case "B>=":{ //para el for
				GenerarAssemblerBMayorIgual(t, arc);
				break;}
			case "inttouint":{
				GenerarAssemblerConversiones(t, arc);
				break;}
			case "uinttoint":{
				GenerarAssemblerConversiones(t, arc);
				break;}
			default: {
	//			System.out.println("el oprador no detectado es "+t.getOperador());
				break;}
			}
			if(t.getOperador().contains("Label")){
				String nombre=t.getLexema();			
				int aux2=nombre.lastIndexOf(')');
				String nombre2=nombre.substring(1, aux2);
				arc.append("Label"+nombre2+":" + "\r\n");
			}
			else		
				if ((t.getOperador().equals("<")) || (t.getOperador().equals(">")) || (t.getOperador().equals(">=")) || (t.getOperador().equals("<=")) || (t.getOperador().equals("=")) || (t.getOperador().equals("!=")))
				{
					GenerarAssemblerComparaciones(t, arc);
				}
		
		}
}
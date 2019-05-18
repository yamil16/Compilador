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
			
			arc.append("OVERFLOWRESTA:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $overflow_resta, addr $overflow_resta, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			arc.append("OVERFLOWMULTIPLICACION:"+ "\r\n");
			arc.append("Invoke MessageBox, NULL, addr $overflow_multiplicacion, addr $overflow_multiplicacion, MB_OK"+ "\r\n");
			arc.append("Invoke ExitProcess, 0"+ "\r\n");
			
			
	//Comienzo del programa
	arc.append("\r\n" +"START:"+ "\r\n");
	for (Terceto t:listaTercetos){
		if(t!=null)
			generarCodigoEjecutable(t,arc);
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
				registrosLibres[registroOperando2] = true;	
			}
		}
	
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
			arc.append(Operador+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");
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
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){					
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
		if(EsResta){
			if(t.getTipo()=="Integer")
				arc.append("JO OVERFLOWRESTA" + "\r\n");	 
			else
				arc.append("JC OVERFLOWRESTA" + "\r\n");
		}
		else{
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
			arc.append(Operador+nombreRegistroLibre+"X, "+"\r\n");
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
			
			
			arc.append("MOV "+"AX, "+nombreRegistroTerceto+ "\r\n");
			
			if(Esdivision)			
				arc.append("CWD "+"\r\n"); //Extiendo el signo en DX:AX
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){				
			
				arc.append("MOV "+nombreRegDispobible+"X, "+uso2+operando2 + "\r\n");				
				arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");				
			
			}
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						String valor2=operando2.substring(2, operando2.length());												
						arc.append("MOV "+nombreRegDispobible+"X, _in"+valor2 + "\r\n");				
						arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");				
					
				  }
				  else{
					  //arc.append(Operador+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
					  	arc.append("MOV "+nombreRegDispobible+"X, _i"+operando2 + "\r\n");				
					  	arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");
					  
				  }  
				}
				else{
					//arc.append(Operador+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
					arc.append("MOV "+nombreRegDispobible+"X, _ui"+operando2 + "\r\n");				
				  	arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");
				}	
			}
			//guardo el resultado en el registro del terceto
			arc.append("MOV "+nombreRegistroTerceto+", AX "+ "\r\n");
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
			
			
			arc.append("MOV "+"AX, "+nombreRegistroTercetoOperando1+ "\r\n");
			
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
			arc.append("MOV "+nombreRegistroLibre+"X, " +"AX"+ "\r\n");
			
			registrosLibres[nroRegistroTercetoOperando2] = true;			
			
		}	
		
					
				
		
	}
	public void GenerarAssemblerOperacion2daPrecedencia(Terceto t, PrintWriter arc,boolean Esdivision) throws IOException {
		boolean DisponibleAX=false;
		boolean DisponibleDX=false;
		if (registrosLibres[0]) { //esta disponible Ax
			t.setRegistro(0);
			registrosLibres[0] = false;
			DisponibleAX=true;
			if (registrosLibres[3]) { //esta disponible DX
				registrosLibres[3] = false;
				DisponibleDX=true;
			}
			else{ //esta disponible AX y NO esta disponible DX
				  arc.append("MOV @auxDx, DX"+ "\r\n");
				  arc.append("MOV DX, 0"+ "\r\n");
			}
		}	
		else{ //NO esta disponible AX
			if (registrosLibres[3]) { //esta disponible DX
				registrosLibres[3] = false;
				DisponibleDX=true;
				 arc.append("MOV @auxAx, AX"+ "\r\n");
				 arc.append("MOV DX, 0"+ "\r\n");
			}
			else{ //NO esta disponible AX y NO esta disponible DX
				arc.append("MOV @auxAx, AX"+ "\r\n"); 
				arc.append("MOV @auxDx, DX"+ "\r\n");				 
			}
		}
		
		if(Esdivision==false){
			if(t.getTipo()=="Integer"){
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"IMUL ",Esdivision);
				arc.append("J0 OVERFLOWMULTIPLICACION" + "\r\n"); //SALTO SI EL FLAG OF=1 Y HUBO DESBORDAMIENTO
			}	
			else{
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"MUL ",Esdivision);
				arc.append("JC OVERFLOWMULTIPLICACION" + "\r\n"); //SALTO SI EL FLAG CF=1 y HUBO OVERFLOW/DESBORDAMIENTO Y CF ES PARA ARITMETICA SIN SIGNO
			}
		}
		else{
			if(t.getTipo()=="Integer")
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"IDIV ",Esdivision);
			else
				GenerarAssemblerOperacionEspecial2daPrecedencia(t,arc,"DIV ",Esdivision);
		}
		
		
		//Reestablesco los valores de los registros con las variables auxiliares
				if((DisponibleAX)&&(!DisponibleDX))
					arc.append("MOV DX, @auxDx"+ "\r\n");			
				else
				if(!(DisponibleAX)&&(DisponibleDX))
					arc.append("MOV AX, @auxAx"+ "\r\n");
				else
				if(!(DisponibleAX)&&!(DisponibleDX)){
					arc.append("MOV AX, @auxAx"+ "\r\n");
					arc.append("MOV DX, @auxDx"+ "\r\n");
				}
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
	
	public void GenerarAssemblerComparacionesConmutativas(Terceto t, PrintWriter arc) throws IOException {
		if(!t.getOperando1().isTerceto()){ //es un token
			if(!t.getOperando2().isTerceto()){ //es un token
				//son los 2 token
				Token t1= (Token) t.getOperando1();
				Token t2= (Token) t.getOperando2();
				Integer registro=obtenerRegistroLibre();
				if(registro==null)
					System.out.println("No hay registro disponible");
				else{
//					System.out.println("registro obtenido: " + registro + " para suma");
					
				registrosLibres[registro]=false;
				t.setRegistro(registro);
				
				String operando1=t.getOperando1().getLexema();
				String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
				String operando2=t.getOperando2().getLexema();
				String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
				String nombreReg = getNombreRegistro(registro);
				
				if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
						arc.append("MOV "+nombreReg+"X, "+uso1+operando1 + "\r\n");						
				else{
					
					if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando1.codePointAt(0)==95){
							  String valor1=operando1.substring(2, operando1.length());
							  arc.append("MOV "+nombreReg+"X, " + "_in"+valor1+ "\r\n");
						  }
						 else
							  arc.append("MOV "+nombreReg+"X, "+"_i" +operando1+ "\r\n");
					}else
						 arc.append("MOV "+nombreReg+"X, "+"_ui" +operando1+ "\r\n");
				}
				
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257)						
					arc.append("CMP "+nombreReg+"X, "+uso2+operando2 + "\r\n");
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							  String valor2=operando2.substring(2, operando2.length());
							  arc.append("CMP "+nombreReg+"X, _in"+valor2 + "\r\n");
						  }
						  else
							  arc.append("CMP "+nombreReg+"X, _i"+operando2 + "\r\n");
					}
					else
						 arc.append("CMP "+nombreReg+"X, "+"_ui" +operando2+ "\r\n");
				}			
			}

		}
		else{ //primero operando Token y segundo Terceto
			
			String lexemaTerceto=t.getOperando2().getLexemaGeneradorNumero();
			int nroterceto = (Integer.parseInt(lexemaTerceto.substring(1, lexemaTerceto.length() - 1)));
			int registroOperando2=listaTercetos.get(nroterceto).getRegistro();
			t.setRegistro(registroOperando2);
			String nombreReg = getNombreRegistro(registroOperando2);
			String operando1=t.getOperando1().getLexema();
		//nuevo
			Token t1= (Token) t.getOperando1();
			if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)					
				arc.append("CMP "+nombreReg+"X, "+Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso()+operando1 + "\r\n");
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando1.codePointAt(0)==95){
						String valor1=operando1.substring(2, operando1.length());
					  arc.append("CMP "+nombreReg+"X, _in"+valor1 + "\r\n");
				  }
				  else
					  arc.append("CMP "+nombreReg+"X, _i"+operando1 + "\r\n");
				}
				else
					 arc.append("CMP "+nombreReg+"X, "+"_ui" +operando1+ "\r\n");
			}
				
			 
			}
		}
		else{ //primer Operando es un Terceto
			if(!t.getOperando2().isTerceto()){ //es un token
				
				String lexemaTerceto=t.getOperando1().getLexemaGeneradorNumero();
				int nroterceto = (Integer.parseInt(lexemaTerceto.substring(1, lexemaTerceto.length() - 1)));
				int registroOperando1=listaTercetos.get(nroterceto).getRegistro();
				t.setRegistro(registroOperando1);
				String nombreReg = getNombreRegistro(registroOperando1);
				String operando2=t.getOperando2().getLexema();
				//nuevo	
				Token t2= (Token) t.getOperando2();
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){					
					arc.append("CMP "+nombreReg+"X, "+Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso()+operando2 + "\r\n");
				}
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							String valor2=operando2.substring(2, operando2.length());
						  arc.append("CMP "+nombreReg+"X, _in"+valor2 + "\r\n");
					  }
					  else
						  arc.append("CMP "+nombreReg+"X, _i"+operando2 + "\r\n");
					}
					else
						 arc.append("CMP "+nombreReg+"X, "+"_ui" +operando2+ "\r\n");
				}
			}
			else{ //los dos son Tercetos
				String lexemaTercetoOperando1=t.getOperando1().getLexemaGeneradorNumero();
				int nrotercetoOperando1 = (Integer.parseInt(lexemaTercetoOperando1.substring(1, lexemaTercetoOperando1.length() - 1)));
				int registroOperando1=listaTercetos.get(nrotercetoOperando1).getRegistro();
				t.setRegistro(registroOperando1);
				String nombreRegOperando1 = getNombreRegistro(registroOperando1);
				
				String lexemaTercetoOperando2=t.getOperando2().getLexemaGeneradorNumero();
				int nrotercetoOperando2 = (Integer.parseInt(lexemaTercetoOperando2.substring(1, lexemaTercetoOperando2.length() - 1)));
				int registroOperando2=listaTercetos.get(nrotercetoOperando2).getRegistro();
				String nombreRegOperando2 = getNombreRegistro(registroOperando2);
				
				arc.append("CMP "+nombreRegOperando1+"X, "+nombreRegOperando2+"X" + "\r\n");
				registrosLibres[registroOperando2] = true;
			}
			
		}
	}
	public void GenerarAssemblerComparacionesEnOrden(Terceto t, PrintWriter arc) throws IOException {
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//son 2 variables y o cte 
			//situacion 1 (-,Variable,Variable)
			
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
			
				
		}
		else
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (-,Registro,Variable)
			
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
		}
		else
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (-,Registro,Registro)
			
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
		//situacion 4b (-,Variable,Registro)
					
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
			
		}	
		else
		System.out.println("no entra por la ninguna de las restas");
		
//		arc.append("JS, label"); 
	}
	
	private void generarCodigoEjecutable(Terceto t, PrintWriter arc) throws IOException {
		System.out.println("terceto " + t.getLexema());
		switch(t.getOperador()){
			case "+":{
				//GenerarAssemblerSuma(t,arc);
				GenerarAssemblerOperacion1erPrecedencia(t,arc,"ADD ",false);
			break;}
			case "-":{
				//GenerarAssemblerOperacion1erPrecedencia(t,arc,"SUB ",true);
				GenerarAssemblerResta(t,arc);
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
			default: {
				System.out.println("el oprador no detectado es "+t.getOperador());
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
					GenerarAssemblerComparacionesEnOrden(t, arc);
				}
			else
				if ((t.getOperador().equals("=")) || (t.getOperador().equals("!=")))
					GenerarAssemblerComparacionesConmutativas(t, arc);
		
		}	
	public void GenerarAssemblerSuma(Terceto t,PrintWriter arc) throws IOException{
		if(!t.getOperando1().isTerceto()){ //es un token
			if(!t.getOperando2().isTerceto()){ //es un token
				//son los 2 token
				Token t1= (Token) t.getOperando1();
				Token t2= (Token) t.getOperando2();
				Integer registro=obtenerRegistroLibre();
				if(registro==null)
					System.out.println("No hay registro disponible");
				else{
//					System.out.println("registro obtenido: " + registro + " para suma");
					
				registrosLibres[registro]=false;
				t.setRegistro(registro);
				
				String operando1=t.getOperando1().getLexema();
				String uso1 = Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getUso();
				String operando2=t.getOperando2().getLexema();
				String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
				String nombreReg = getNombreRegistro(registro);
				
				if (Compilador.T_simbolos.get(t1.getClaveTablaSimboloToken()).getToken() == 257)						
						arc.append("MOV "+nombreReg+"X, "+uso1+operando1 + "\r\n");						
				else{
					
					if (Compilador.T_simbolos.get(((Token) t.getOperando1()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando1.codePointAt(0)==95){
							  String valor1=operando1.substring(2, operando1.length());
							  arc.append("MOV "+nombreReg+"X, " + "_in"+valor1+ "\r\n");
						  }
						 else
							  arc.append("MOV "+nombreReg+"X, "+"_i" +operando1+ "\r\n");
					}else
						 arc.append("MOV "+nombreReg+"X, "+"_ui" +operando1+ "\r\n");
				}
				
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257)						
					arc.append("ADD "+nombreReg+"X, "+uso2+operando2 + "\r\n");
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							  String valor2=operando2.substring(2, operando2.length());
							  arc.append("ADD "+nombreReg+"X, _in"+valor2 + "\r\n");
						  }
						  else
							  arc.append("ADD "+nombreReg+"X, _i"+operando2 + "\r\n");
					}
					else
						 arc.append("ADD "+nombreReg+"X, "+"_ui" +operando2+ "\r\n");
				}			
			}

		}
		else{ //primero operando Token y segundo Terceto
			
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
		else{ //primer Operando es un Terceto
			if(!t.getOperando2().isTerceto()){ //es un token
				
				String lexemaTerceto=t.getOperando1().getLexemaGeneradorNumero();
				int nroterceto = (Integer.parseInt(lexemaTerceto.substring(1, lexemaTerceto.length() - 1)));
				int registroOperando1=listaTercetos.get(nroterceto).getRegistro();
				t.setRegistro(registroOperando1);
				String nombreReg = getNombreRegistro(registroOperando1);
				String operando2=t.getOperando2().getLexema();
				//nuevo	
				Token t2= (Token) t.getOperando2();
				if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){					
					arc.append("ADD "+nombreReg+"X, "+Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso()+operando2 + "\r\n");
				}
				else{
					if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
						if(operando2.codePointAt(0)==95){
							String valor2=operando2.substring(2, operando2.length());
						  arc.append("ADD "+nombreReg+"X, _in"+valor2 + "\r\n");
					  }
					  else
						  arc.append("ADD "+nombreReg+"X, _i"+operando2 + "\r\n");
					}
					else
						 arc.append("ADD "+nombreReg+"X, "+"_ui" +operando2+ "\r\n");
				}
			}
			else{ //los dos son Tercetos
				String lexemaTercetoOperando1=t.getOperando1().getLexemaGeneradorNumero();
				int nrotercetoOperando1 = (Integer.parseInt(lexemaTercetoOperando1.substring(1, lexemaTercetoOperando1.length() - 1)));
				int registroOperando1=listaTercetos.get(nrotercetoOperando1).getRegistro();
				t.setRegistro(registroOperando1);
				String nombreRegOperando1 = getNombreRegistro(registroOperando1);
				
				String lexemaTercetoOperando2=t.getOperando2().getLexemaGeneradorNumero();
				int nrotercetoOperando2 = (Integer.parseInt(lexemaTercetoOperando2.substring(1, lexemaTercetoOperando2.length() - 1)));
				int registroOperando2=listaTercetos.get(nrotercetoOperando2).getRegistro();
				String nombreRegOperando2 = getNombreRegistro(registroOperando2);
				
				arc.append("ADD "+nombreRegOperando1+"X, "+nombreRegOperando2+"X" + "\r\n");
				registrosLibres[registroOperando2] = true;
			}
			
		}
		if(t.getTipo()=="Integer")
			arc.append("JO OVERFLOWSUMA" + "\r\n");	 
		else
			arc.append("JC OVERFLOWSUMA" + "\r\n");	 
	}

	
	public void GenerarAssemblerResta(Terceto t,PrintWriter arc ) throws IOException{
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//son 2 variables y o cte 
			//situacion 1 (-,Variable,Variable)
			
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
			arc.append("SUB "+nombreRegistroLibre+"X, "+uso2+operando2 + "\r\n");
		else{
			if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
				if(operando2.codePointAt(0)==95){
					  String valor2=operando2.substring(2, operando2.length());
					  arc.append("SUB "+nombreRegistroLibre+"X, _in"+valor2 + "\r\n");
				  }
				  else
					  arc.append("SUB "+nombreRegistroLibre+"X, _i"+operando2 + "\r\n");
			}
			else
				 arc.append("SUB "+nombreRegistroLibre+"X, "+"_ui" +operando2+ "\r\n");
		}
			
				
		}
		else
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (-,Registro,Variable)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			
			
			Token t2= (Token) t.getOperando2();
			String operando2=t2.getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){					
				arc.append("SUB "+nombreRegistroTerceto+"X, "+uso2+operando2 + "\r\n");
			}
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						String valor2=operando2.substring(2, operando2.length());
					  arc.append("SUB "+nombreRegistroTerceto+"X, _in"+valor2 + "\r\n");
				  }
				  else
					  arc.append("SUB "+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
				}
				else
					 arc.append("SUB "+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
			}		
			t.setRegistro(nroRegistroTerceto);
		}
		else
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (-,Registro,Registro)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTercetoOperando1=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTercetoOperando1=getNombreRegistro(nroRegistroTercetoOperando1);
						
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			
			t.setRegistro(nroRegistroTercetoOperando1);
			arc.append("SUB "+nombreRegistroTercetoOperando1+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
			registrosLibres[nroRegistroTercetoOperando2] = true;		
		}
		else
		if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
		//situacion 4b (-,Variable,Registro)
					
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
			
			arc.append("SUB "+nombreRegistroLibre+"X, "+nombreRegistroTercetoOperando2+"X" + "\r\n");
			registrosLibres[nroRegistroTercetoOperando2] = true;			
			
		}	
		else
		System.out.println("no entra por la ninguna de las restas");
	}
	
	
		public void GenerarAssemblerDivisionEspecial(Terceto t,PrintWriter arc, String Operador ) throws IOException{
	
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
		
		
		
		
		
		
		//verifico la situacion y opero		
		
		
		if(!(t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){			 
			//situacion 1 (/,Variable,Variable)
			
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
			//Extiendo el signo en DX:AX
			arc.append("CWD "+"\r\n");
			
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
			arc.append(Operador+nombreRegistroLibre+"X, "+"\r\n");
		}
		else
			
			
			
		if((t.getOperando1().isTerceto())&& !(t.getOperando2().isTerceto())){
			//situacion 2 (/,Registro,Variable)
			
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTerceto=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTerceto=getNombreRegistro(nroRegistroTerceto);
			
			
			Token t2= (Token) t.getOperando2();
			String operando2=t2.getLexema();
			String uso2 = Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getUso();
			
			Integer registroDiv=obtenerRegistroLibre();
			String nombreRegDispobible= getNombreRegistro(registroDiv);
			
			
			arc.append("MOV "+"AX, "+nombreRegistroTerceto+ "\r\n");
			//Extiendo el signo en DX:AX
			arc.append("CWD "+"\r\n");
			if (Compilador.T_simbolos.get(t2.getClaveTablaSimboloToken()).getToken() == 257){				
			
				arc.append("MOV "+nombreRegDispobible+"X, "+uso2+operando2 + "\r\n");				
				arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");				
			
			}
			else{
				if (Compilador.T_simbolos.get(((Token) t.getOperando2()).getClaveTablaSimboloToken()).getTipo() == "Integer"){
					if(operando2.codePointAt(0)==95){
						String valor2=operando2.substring(2, operando2.length());												
						arc.append("MOV "+nombreRegDispobible+"X, _in"+valor2 + "\r\n");				
						arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");				
					
				  }
				  else{
					  //arc.append(Operador+nombreRegistroTerceto+"X, _i"+operando2 + "\r\n");
					  	arc.append("MOV "+nombreRegDispobible+"X, _i"+operando2 + "\r\n");				
					  	arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");
					  
				  }  
				}
				else{
					//arc.append(Operador+nombreRegistroTerceto+"X, "+"_ui" +operando2+ "\r\n");
					arc.append("MOV "+nombreRegDispobible+"X, _ui"+operando2 + "\r\n");				
				  	arc.append(Operador+nombreRegDispobible+"X, "+"\r\n");
				}	
			}
			//guardo el resultado en el registro del terceto
			arc.append("MOV "+nombreRegistroTerceto+", AX "+ "\r\n");
			t.setRegistro(nroRegistroTerceto);
		}
		else
			
			
			
		if((t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
			//situacion 3 (-,Registro,Registro)
			/*
			 //Este es un caso especial de que justo sea A el registro del terceto y al haber movido el valor se pierda
			if(nombreRegDispobible.equals("A")) {
			
			}			 
			 
			 */
			String NroTercetoLexemaOperando1=t.getOperando1().getLexemaGeneradorNumero();
			int nroTercetoOperando1=	(Integer.parseInt(NroTercetoLexemaOperando1.substring(1,NroTercetoLexemaOperando1.length()-1)));	
			int nroRegistroTercetoOperando1=listaTercetos.get(nroTercetoOperando1).getRegistro();		
			String nombreRegistroTercetoOperando1=getNombreRegistro(nroRegistroTercetoOperando1);
						
			String NroTercetoLexemaOperando2=t.getOperando2().getLexemaGeneradorNumero();
			int nroTercetoOperando2=	(Integer.parseInt(NroTercetoLexemaOperando2.substring(1,NroTercetoLexemaOperando2.length()-1)));	
			int nroRegistroTercetoOperando2=listaTercetos.get(nroTercetoOperando2).getRegistro();		
			String nombreRegistroTercetoOperando2=getNombreRegistro(nroRegistroTercetoOperando2);
			
			
			arc.append("MOV "+"AX, "+nombreRegistroTercetoOperando1+ "\r\n");
			//Extiendo el signo en DX:AX
			arc.append("CWD "+"\r\n");
			
			 //Este es un caso especial capaz que es de gusto
			if(nombreRegistroTercetoOperando2.equals("A")) //DIVIDO POR AUX
				arc.append(Operador+"@auxAx" + "\r\n");
			else	
			if(nombreRegistroTercetoOperando2.equals("D")) //DIVIDO POR AUX
				arc.append(Operador+"@auxDx" + "\r\n");
			else //divido normal
				arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			
			
			t.setRegistro(nroRegistroTercetoOperando1);
			
			registrosLibres[nroRegistroTercetoOperando2] = true;		
		}
		else
			
			
			
			
		if(!(t.getOperando1().isTerceto())&& (t.getOperando2().isTerceto())){
		//situacion 4b (-,Variable,Registro)
					
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
			
			//Extiendo el signo en DX:AX
			arc.append("CWD "+"\r\n");
			//arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			 //Este es un caso especial capaz que es de gusto
			if(nombreRegistroTercetoOperando2.equals("A")) //DIVIDO POR AUX
				arc.append(Operador+"@auxAx" + "\r\n");
			else	
			if(nombreRegistroTercetoOperando2.equals("D")) //DIVIDO POR AUX
				arc.append(Operador+"@auxDx" + "\r\n");
			else //divido normal
				arc.append(Operador+nombreRegistroTercetoOperando2+"X" + "\r\n");
			arc.append("MOV "+nombreRegistroLibre+"X, " +"AX"+ "\r\n");
			
			registrosLibres[nroRegistroTercetoOperando2] = true;			
			
		}	
	}
	
	
		public void GenerarAssemblerDivision(Terceto t,PrintWriter arc ) throws IOException{
		//el dividendo debe estar en DX,AX por lo tanto hay que verificar que no esten usados y en casos de estar usados usar una varialbe auxilar rpara guardar el resultado
		//Si dx esta usado lo voy a hacer cero para que no influya en el resultado
		//En AX queda el cociente que es lo que nos interesa 
		//En dx queda el resto
	
		boolean DisponibleAX=false;
		boolean DisponibleDX=false;
		if (registrosLibres[0]) { //esta disponible Ax
			t.setRegistro(0);
			registrosLibres[0] = false;
			DisponibleAX=true;
			if (registrosLibres[3]) { //esta disponible DX
				registrosLibres[3] = false;
				DisponibleDX=true;
			}
			else{ //esta disponible AX y NO esta disponible DX
				  arc.append("MOV @auxDx, DX"+ "\r\n");
				  arc.append("MOV DX, 0"+ "\r\n");
			}
		}	
		else{ //NO esta disponible AX
			if (registrosLibres[3]) { //esta disponible DX
				registrosLibres[3] = false;
				DisponibleDX=true;
				 arc.append("MOV @auxAx, AX"+ "\r\n");
				 arc.append("MOV DX, 0"+ "\r\n");
			}
			else{ //NO esta disponible AX y NO esta disponible DX
				arc.append("MOV @auxAx, AX"+ "\r\n"); 
				arc.append("MOV @auxDx, DX"+ "\r\n");				 
			}
		}
		//guarde en variables auxiliares en caso de ser necesarios los valores de AX y DX
		
		//Opero normal		
		if(t.getTipo()=="Integer")
			GenerarAssemblerDivisionEspecial(t,arc,"IDIV ");
		else
			GenerarAssemblerDivisionEspecial(t,arc,"DIV ");
		
		
		
		
		
		//Reestablesco los valores de los registros con las variables auxiliares
		if((DisponibleAX)&&(!DisponibleDX))
			arc.append("MOV DX, @auxDx"+ "\r\n");			
		else
		if(!(DisponibleAX)&&(DisponibleDX))
			arc.append("MOV AX, @auxAx"+ "\r\n");
		else
		if(!(DisponibleAX)&&!(DisponibleDX)){
			arc.append("MOV AX, @auxAx"+ "\r\n");
			arc.append("MOV DX, @auxDx"+ "\r\n");
		}
		
	}
	
}	
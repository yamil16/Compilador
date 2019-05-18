package Compilador;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class Main {

	public static ArrayList<String> listaTokens;
	public static ArrayList<String> listaErroresSintacticos;
	public static ArrayList<String> listaErroresSintacticosSinSyntaxError;
	public static ArrayList<String> listaSentenciasValidas;
	public static ArrayList<String> listaErroresSemanticos;
	private static int count;
	public static ArrayList<Terceto> listaTerceto;

		
			public static void main (String [] args) {				
				//if(args.length>0){					
					//String entrada=args[0];	
				count = 0;
				listaTerceto= new ArrayList<Terceto>();
						Compilador c = new Compilador();
						listaTokens = new ArrayList<String>();
						listaErroresSintacticos = new ArrayList<String>();
						listaSentenciasValidas = new ArrayList<String>();
						listaErroresSintacticosSinSyntaxError= new ArrayList<String>();
						listaErroresSemanticos= new ArrayList<String>();
						try{	
							File name = new File("salida_lexico.txt");
							@SuppressWarnings("resource")
							FileWriter w = new FileWriter(name);
							BufferedWriter bw = new BufferedWriter(w);
							PrintWriter arc = new PrintWriter(bw);
						//	arc.append("tokens: "+"\r\n");
							//Parser parser = new Parser(args[0]);
//							Parser parser = new Parser("D:\\masm32\\bin\\matriz14.txt");
//							Parser parser = new Parser("C:\\Users\\juan\\desktop\\ifFor.txt");
//							Parser parser = new Parser("C:\\Users\\juan\\workspace\\2015\\Compiladores\\suma.txt");
							Parser parser = new Parser("C:\\Users\\Yamil\\workspace\\Compiladores_2016_v4\\src\\tp1.txt");
							parser.run();			
							String auxi = "";
							if (!Compilador.errores.isEmpty()){
								count++;
								System.out.println("Se detectaron errores LEXICOS");
								arc.append( "Errores lexicos detectados: " +"\r\n");
								while (Compilador.errores.size() > 0){
									auxi = Compilador.errores.remove(0) ;
									if (auxi != "syntax error")
										arc.append(auxi + "\r\n");
								}
							}
							if (!Compilador.warnings.isEmpty()){
								System.out.println("Se detectaron Warning");
								arc.append( "Warnings lexicos detectados: " + "\r\n");
								while (Compilador.warnings.size() > 0){
									auxi = Compilador.warnings.remove(0) ;
									if (auxi != "syntax error")
										arc.append(auxi + "\r\n");
								}
							}	
							while (listaErroresSintacticos.size() > 0){
								auxi = listaErroresSintacticos.remove(0) ;
								if (auxi != "syntax error")
									listaErroresSintacticosSinSyntaxError.add(auxi);
							}						
							if ((listaErroresSintacticosSinSyntaxError.size() > 0)) {							
								count ++;
								System.out.println("Se detectaron errores SINTACTICOS");
									arc.append( "Errores sintacticos detectados: " + "\r\n");
									while (listaErroresSintacticosSinSyntaxError.size() > 0){
										auxi = listaErroresSintacticosSinSyntaxError.remove(0) ;
										arc.append(auxi + "\r\n");										
									}	
							}
							if (!listaErroresSemanticos.isEmpty()){
								count++;
								System.out.println("Se detectaron errores SEMANTICOS");
								
								//System.out.println("Se detectaron errores semanticos");
								arc.append( "\r\n" + "errores semanticos detectados en el codigo intermedio: " + "\r\n");
								while (listaErroresSemanticos.size() > 0)
									arc.append(listaErroresSemanticos.remove(0) + "\r\n") ;
									
								
							}
//							arc.append("\r\n \r\n" +  "Tokens del programa fuente: " + "\r\n");
//							while (listaTokens.size() > 1)
//								arc.append("Token: " + listaTokens.remove(0) + "\r\n");
							if (!listaSentenciasValidas.isEmpty())
								arc.append("\r\n \r\n" + "sentencias validas reconocidas: " + "\r\n");
							while (listaSentenciasValidas.size() > 0)
								arc.append("sentencia: " + listaSentenciasValidas.remove(0) + "\r\n");
							Enumeration<String>  e = Compilador.T_simbolos.keys();
							Object clave;
							arc.append( "\r\n" +"\r\n" + "Tabla de simboloes resultante: " + "\r\n");
							while( e.hasMoreElements() ){
								  clave = e.nextElement();
	//							  System.out.println(clave.toString());
								  String aux;
								  switch (Compilador.T_simbolos.get(clave.toString()).getToken()){
									case 257: aux = "ID"; break;
									case 258: aux = "THEN"; break;
									case 259: aux = "IF";break; 
									case 260: aux = "ELSE";break; 
									case 261: aux = "PRINT";break; 
									case 262: aux = "ENDIF";break; 
									case 263: aux = "INTEGER";break; 
									case 264: aux = "UINTEGER";break; 
									case 265: aux = "MATRIX";break; 
									case 266: aux = "FOR";	break; 
									case 267: aux = "CONSTANTE_POSITIVA";break; 
									case 268: aux = "CONSTANTE_ENTERA";break; 
									case 269: aux = "MENOSMENOS";break; 
									case 270: aux = "ASIGNACION";break; 
									case 271: aux = "DISTINTO";break; 
									case 272: aux = "MENORIGUAL";break; 
									case 273: aux = "MAYORIGUAL";break; 
									case 274: aux = "ANOTACION_CERO";break; 
									case 275: aux = "ANOTACION_UNO";break; 
									case 276: aux = "CADENA_MULTILINEA";break; 
									case 277: aux = "FIN";break; 
									case 278: aux = "ALLOW";break; 
									case 279: aux = "TO";break; 							
	
									default: aux = "FIN"; break; 
								  }								
								  if ((Compilador.T_simbolos.get(clave.toString()).getToken() == 276) ||((Compilador.T_simbolos.get(clave.toString()).getToken() > 266) && (Compilador.T_simbolos.get(clave.toString()).getToken() < 269)))
									  arc.append(( "Clave: " + clave + ",	 Token: " +  aux + ",	Lexema: " +  Compilador.T_simbolos.get(clave.toString()).getLexema()) + "\r\n");
								  else
									  if ((Compilador.T_simbolos.get(clave.toString()).getToken() == 257) && (clave.toString().contains("@"))){
										  arc.append(( "Clave: " + clave + ",	 Token: " +  aux + ",	Lexema: " +  Compilador.T_simbolos.get(clave.toString()).getLexema() + ",	uso: " + Compilador.T_simbolos.get(clave.toString()).getUso()) + "\r\n");
									  }
//								  if (clave.toString().contains("mat@"))
//									  arc.append("filas " + Compilador.T_simbolos.get(clave.toString()).getFilas() + "columnas " + Compilador.T_simbolos.get(clave.toString()).getColumnas());
							}
							arc.append( "\r\n" + "\r\n" +"Tercetos detectados: " + "\r\n");
							
							try{
								int i = 0;
								Terceto auxTerceto;
								//arc.append("size: " + listaTerceto.size()+ "\r\n");
							while (listaTerceto.size() > i){
								auxTerceto = listaTerceto.get(i);
								i++;
								if (auxTerceto.getOperador().contains("Label")){
									int aux = auxTerceto.getLexema().length();
									arc.append("Label" + auxTerceto.getLexema().substring(1, aux-1) + "\r\n");
								}
								else
									arc.append(auxTerceto.getLexema() + " tipo: " +auxTerceto.getTipo()  + "\r\n");
									
								
								
						
							}
							if (count == 0){
								try {
									GeneradorAssembler generadorAss = new GeneradorAssembler(listaTerceto);
									generadorAss.generarAssembler();
								} catch (IOException eg) {
									// TODO Auto-generated catch block
									eg.printStackTrace();
								}
							}
							}catch( NullPointerException ef){
							System.out.println("Null el while");
							count++;
							}
							
							arc.close();
							bw.close();
							w.close();				
							if (count > 0)
								System.out.print("Se detectaron errores, por lo tanto el programa no compila. Verificarlos en el archivo de salida: salida_lexico.txt");
							else					
								
								System.out.print("No se detectaron errores. Se genero el archivo de salida: salida_lexico.txt");
			
						}catch(IOException | NullPointerException e){
							System.out.println( "el archivo no se pudo abrir");
						}
				//}
				//else
					//System.out.println( "No se paso correctamente el argumento del archivo");
				
			}
			
			
			
			public static void addTerceto(Terceto obj){
				listaTerceto.add(obj);
				
			}
			public void eliminarTerceto(Terceto t){
				listaTerceto.remove(t);
			}
			public static List<Terceto> getTerceto(){
				return listaTerceto;
			}
			public static void ActualizarTerceto(Terceto t){
				for(Terceto t2: listaTerceto){
					if(t2.getNumTerceto()==t.getNumTerceto()){
						
						t2=t;
						
					}	
				}
			}
	}

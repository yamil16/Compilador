//C:\\Users\\juan\\Desktop\\gramatica_yamil\\entrada_sintactica1.txt

%{
	import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Enumeration;
	import java.io.IOException;
	import java.io.FileNotFoundException;
%}

%token ID 
%token THEN
%token IF 
%token ELSE
%token PRINT
%token ENDIF
%token INTEGER
%token UINTEGER
%token MATRIX
%token FOR
%token CONSTANTE_POSITIVA
%token CONSTANTE_ENTERA
%token MENOSMENOS
%token ASIGNACION
%token DISTINTO
%token MENORIGUAL
%token MAYORIGUAL
%token ANOTACION_CERO
%token ANOTACION_UNO
%token CADENA_MULTILINEA
%token FIN
%token ALLOW //conversion
%token TO //conversion
//%nonassoc cuerpo_then
//%nonassoc cuerpo_else


%% //COMIENZO CON LA ESPECIFICACION DE REGLAS
incio: programa
programa: 	ID s_declarativa '{' s_ejecutable '}' {if (!((Generador)$1.obj).isTerceto()){
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getToken());
													t1.setUso("prog@");
													t1.setTipo("nombre_programa");
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						//							Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());
													((Token)$1.obj).setUso("prog@");
													if((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getUso() == ""))
														System.out.println("nombre programa sin definir");
													}
													else
														System.out.println("nombre de programa es terceto, error");
													}
			| ID s_declarativa 			{if (!((Generador)$1.obj).isTerceto()){
										Tabla_Simbolos t1=new Tabla_Simbolos();
										t1.setInformacion(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getInformacion());
										t1.setLexema(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getLexema());
										t1.setToken(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getToken());
										t1.setUso("prog@");
										t1.setTipo("nombre_programa");
										Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						//				Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());
										((Token)$1.obj).setUso("prog@");
										if((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getUso() == ""))
											System.out.println("nombre programa sin definir");
										}
										else
											System.out.println("nombre de programa es terceto, error");
										}
			| ID '{' s_ejecutable '}' 			{if (!((Generador)$1.obj).isTerceto()){
												Tabla_Simbolos t1=new Tabla_Simbolos();
												t1.setInformacion(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getInformacion());
												t1.setLexema(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getLexema());
												t1.setToken(Compilador.get_Tsimbolos(( (Token)$1.obj).getClaveTablaSimboloToken()).getToken());
												t1.setUso("prog@");
												t1.setTipo("nombre_programa");
												Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						//						Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());
												((Token)$1.obj).setUso("prog@");
												if((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getUso() == ""))
													System.out.println("nombre programa sin definir");
												}
												else
													System.out.println("nombre de programa es terceto, error");
												}
//ERRORES
			|error s_declarativa '{' s_ejecutable '}'  {yyerror("Error Sintactico, se espera un id que haga referecia al nombre del programa. Linea: 1 " );}	//FALTA DE NOMBRE DEL PROGRAMA
			|ID s_declarativa  s_ejecutable '}'   {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA
			|ID s_declarativa  '{' s_ejecutable   {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA
			|ID s_declarativa s_ejecutable  {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA
			|ID '{' s_ejecutable  {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA
			|ID  s_ejecutable '}'  {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA			
			|ID s_ejecutable  {yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}	//FALTA DE NOMBRE DEL PROGRAMA			
;

s_declarativa: s_declarativa declarar_variable 
			| s_declarativa declarar_arreglo 
			|s_declarativa conversiones
			| declarar_variable 
			| declarar_arreglo
			|conversiones //lo pongo aca para eliminar el error desp. preguntar			
;

declarar_variable: 	tipo l_variables ';'{
											for (Token variable: listaTipoVariable){											
												if((Compilador.get_Tsimbolos("var@" +variable.getClaveTablaSimboloToken())!=null)&&(Compilador.get_Tsimbolos("var@" +variable.getClaveTablaSimboloToken()).getTipo()!="")){													
													yyerrorSemantico("Se redeclaro la variable: " + Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getLexema() + " en la linea: "+lexico.getNrolinea());													
													String aux = variable.getClaveTablaSimboloToken();
										//			Compilador.T_simbolos.remove(aux);
												}else{
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getToken());
													t1.setUso("var@");
													t1.setTipo(tipoVariable);
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
							//						Compilador.T_simbolos.remove(variable.getClaveTablaSimboloToken());
													variable.setUso("var@");
												}																							
											};
											listaTipoVariable.removeAll(listaTipoVariable);
										}
//ERRORES				
				| tipo ';' {yyerror("Error Sintactico, faltan declarar las variable.En la Linea: "+lexico.getNrolinea());}		//FALTA las variables
				| tipo l_variables {yyerror("Error Sintactico, se espera un punto y coma. Linea: "+(lexico.getNrolinea()-1));}		//FALTA EL ;
				| l_variables ';' {yyerror("Error Sintactico, falta el tipo de la variable.En la Linea: "+lexico.getNrolinea());}		//FALTA EL tipo
;

l_variables: l_variables ','  ID {listaTipoVariable.add((Token)$3.obj);}
			| ID {listaTipoVariable.add((Token)$1.obj);}
//ERRORES
			| l_variables error  {yyerror("Error Sintactico, se espera una coma. Linea: "+lexico.getNrolinea());}		//FALTA LA COMA
;

declarar_arreglo: tipo MATRIX matriz inicializar_arreglo ';' ANOTACION_CERO { if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(false);
					//											System.out.println("le cambie el tipo a la mat " +((Token)$3.obj).getClaveTablaSimboloToken() +" en la declaracion, ahora es: " + Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).getTipo());
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																//		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz (deben ser enteras), en la linea: " + lexico.getNrolinea());																			
																		}
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																}
																else System.out.println("no deberia");
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
															
				| tipo MATRIX matriz inicializar_arreglo ';' ANOTACION_UNO{if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(true);
																numFila--;
																numCol--;
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setFilas(numFila);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setColumnas(numCol);
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																//		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz, en la linea" + lexico.getNrolinea());
																				
																		}
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																};
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
				| tipo MATRIX matriz inicializar_arreglo ';'{ if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(false);
																
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																//		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz, en la linea" + lexico.getNrolinea());
																				
																		}
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																};
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
				| tipo MATRIX matriz ';' ANOTACION_CERO {if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(false);
										//						System.out.println("clave: " + ((Token)$3.obj).getClaveTablaSimboloToken());
																}}
														else
															System.out.println("declaracion de matriz es terceto, error");
														}
				| tipo MATRIX matriz ';' ANOTACION_UNO{ if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																numFila--;
																numCol--;
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setFilas(numFila);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setColumnas(numCol);
																
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(true);
																}}
														else
															System.out.println("declaracion de matriz es terceto, error");
														}
				| tipo MATRIX matriz ';'{ if (!((Generador)$3.obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).setComienza11(false);
																}}
										else
											System.out.println("declaracion de matriz es terceto, error");
										}
//ERRORES
		//		|MATRIX matriz inicializar_arreglo anotacion {yyerror("Error Sintactico, falta declarar el tipo de la matriz.En la Linea: "+lexico.getNrolinea());}
		//		|MATRIX matriz anotacion  {yyerror("Error Sintactico, falta declarar el tipo de la matriz.En la Linea: "+lexico.getNrolinea());}
		//15/9		
		//		| MATRIX matriz anotacion {yyerror("Error Sintactico en la declaracion de los arreglos, falta declarar el tipo de la matriz.En la Linea: "+lexico.getNrolinea());}
				| MATRIX matriz ';' {yyerror("Error Sintactico en la declaracion de los arreglos, falta declarar el tipo de la matriz.En la Linea: "+lexico.getNrolinea());}			
;

matriz : ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA ']' {numFila = Integer.parseInt(((Token)$3.obj).getLexema());
																numCol = Integer.parseInt(((Token)$6.obj).getLexema());
												if((Compilador.get_Tsimbolos("mat@" +((Token)$1.obj).getClaveTablaSimboloToken())!=null)&&(Compilador.get_Tsimbolos("mat@" +((Token)$1.obj).getClaveTablaSimboloToken()).getTipo()!="sin definir")){													
													yyerrorSemantico("Se redeclaro la matriz: " + Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getLexema() + " en la linea: "+lexico.getNrolinea());													
													String aux = Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getLexema() + Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getToken();
													Compilador.T_simbolos.remove(aux);
												}else{
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos((((Token)$1.obj).getClaveTablaSimboloToken())).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getToken());
													t1.setUso("mat@");
													t1.setTipo(tipoVariable);
													t1.setFilas(numFila);
													t1.setColumnas(numCol);
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
													((Token)$1.obj).setUso("mat@");
												}
												$$.obj = $1.obj;
}
//ERRORES
		| ID '[' CONSTANTE_POSITIVA ']' '[' CONSTANTE_POSITIVA ']' {yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
		| ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_POSITIVA ']' {yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
		| ID '[' CONSTANTE_POSITIVA ']' '[' CONSTANTE_ENTERA ']' {yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
		| ID '[' CONSTANTE_ENTERA ']'error {yyerror("Error Sintactico, se espera un corchete que abre en la Linea: "+lexico.getNrolinea());}		//FALTA CORCHETE QUE ABRE
		| ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA error {yyerror("Error Sintactico, se espera un corchete que cierra en la Linea: "+lexico.getNrolinea());}		//FALTA CORCHETE QUE cierra
		| '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA ']' {yyerror("Error Sintactico, se espera un nombre para la matriz en la Linea: "+lexico.getNrolinea());}		//FALTA id de la matriz
		
		
;
matriz_ejecutable: ID '[' expresion ']' '[' expresion ']'{ 
													((Token)$1.obj).setUso("mat@");
													if (($3.getTipo() == "Integer") && ($6.getTipo() == "Integer")){
													if(((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())!=null))){
														String aux = "mat@" +((Token)$1.obj).getLexema();
														Token token = new Token("", aux, Compilador.ID);
//									Terceto t0 = new Terceto("baseMatriz",token,null, Main.getTerceto().size());
														Tabla_Simbolos tabla2=new Tabla_Simbolos();
														tabla2.setLexema("2");
														tabla2.setToken(Compilador.CONSTANTE_ENTERA);
														tabla2.setUso("");
														Compilador.T_simbolos.put(tabla2.getUso() +tabla2.getLexema() + tabla2.getToken(), tabla2);
														Terceto t4;
														if (Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getComienza11()){
															Terceto t2 = new Terceto("-", (Generador)$3.obj, new Token("","1",Compilador.CONSTANTE_ENTERA) , Main.getTerceto().size());
															t2.setTipo("Integer");
															Main.addTerceto(t2);
															Terceto t12 = new Terceto ("chequeoFila" , t2, token, Main.getTerceto().size());
															t12.setTipo("Integer");
															Main.addTerceto(t12);
															Terceto t1 = new Terceto("-", (Generador)$6.obj, new Token("","1",Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t1.setTipo("Integer");
															Main.addTerceto(t1);
															Terceto t11 = new Terceto ("chequeoCol" , t1, token, Main.getTerceto().size());
															t11.setTipo("Integer");
															Main.addTerceto(t11);
															Terceto t3 = new Terceto("*", t12, new Token("",Integer.toString(Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getColumnas()),Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t3.setTipo("Integer");
															Main.addTerceto(t3);
															t4 = new Terceto("+", t3 , t11, Main.getTerceto().size());
															t4.setTipo("Integer");
														
														}
														else{
															Terceto t12 = new Terceto ("chequeoFila" , (Generador)$3.obj, token, Main.getTerceto().size());
															t12.setTipo("Integer");
															Main.addTerceto(t12);
															Terceto t11 = new Terceto ("chequeoCol" , (Generador)$6.obj, token, Main.getTerceto().size());
															t11.setTipo("Integer");
															Main.addTerceto(t11);
															Terceto t3 = new Terceto("*", t12, new Token("",Integer.toString(Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getColumnas()),Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t3.setTipo("Integer");
															Main.addTerceto(t3);
															t4 = new Terceto("+", t3 , t11, Main.getTerceto().size());
															t4.setTipo("Integer");
														}
														Main.addTerceto(t4);
														Terceto t5 = new Terceto("*", t4, new Token("","2",Compilador.CONSTANTE_ENTERA) , Main.getTerceto().size());//para cantidad de bytes
														t5.setTipo("Integer");
														Main.addTerceto(t5); 
													//	Terceto t6 = new Terceto("+", t0, t5, Main.getTerceto().size());
													//	t6.setTipo("Integer");
													//	Main.addTerceto(t6);
//control de fuera de matriz comparando la direccion base y la base + fin con la base+ anterior	
														Terceto t7 = new Terceto("operacion", t5, token, Main.getTerceto().size());
														Main.addTerceto(t7);
														$$.obj = ((Generador)t7);
														$$.setTipo(Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getTipo());
			//											System.out.println("tipo de mat ejec " + $$.getTipo());
														}
														else
															yyerrorSemantico("se quiere utilizar una matriz no declarada. En la linea: " + lexico.getNrolinea());
														}
														else
															yyerrorSemantico("Tipo de acceso incorrecto, las posiciones de la matriz deben ser integer. En la linea: " + lexico.getNrolinea());
														}
				
				| ID '[' expresion ']'error ']' {yyerror("Error Sintactico, se espera un corchete que abre en la Linea: "+lexico.getNrolinea());}		//FALTA CORCHETE QUE ABRE
				| ID '[' expresion ']' '[' expresion error ']' {yyerror("Error Sintactico, se espera un corchete que cierra en la Linea: "+lexico.getNrolinea());}		//FALTA CORCHETE QUE cierra
				| '[' expresion ']' '[' expresion ']' {yyerror("Error Sintactico, se espera un nombre para la matriz en la Linea: "+lexico.getNrolinea());}		//FALTA id de la matriz
;

//INICIALIZACION_ARREGLO
inicializar_arreglo: '{' valores_fila  '}'
//ERRORES
					| valores_fila  '}' {yyerror("Error Sintactico, se espera un corchete que abre : "+lexico.getNrolinea());}		//falta corchete
					| '{' valores_fila error '}' {yyerror("Error Sintactico, se espera un corchete que cierra : "+lexico.getNrolinea());}		//falta corchete
					| error '}' {yyerror("Error Sintactico, se espera un corchete que abre : "+lexico.getNrolinea());}		//falta corchete
;
valores_fila: valores_fila ';' valores_columna
			| valores_columna
//ERRORES
		| valores_fila valores_columna {yyerror("Error Sintactico, se espera ';' en la fila de la matriz. Linea: "+lexico.getNrolinea());}		//FALTA LA COMA
;
valores_columna: valores_columna ',' constante {if (!((Generador)$3.obj).isTerceto()){
												listaInicMatriz.add(Integer.parseInt(((Token)$3.obj).getLexema()));
												listaTiposInicMatriz.add($3.getTipo());
											}else
												System.out.println("inicializacion de matriz es terceto, error");
											}
												
				| constante	{if (!((Generador)$1.obj).isTerceto()){
												listaInicMatriz.add(Integer.parseInt(((Token)$1.obj).getLexema()));
												listaTiposInicMatriz.add($1.getTipo());
												}else
												System.out.println("inicializacion de matriz es terceto, error");
											}
;


//CONVERSIONES
conversiones: ALLOW UINTEGER TO INTEGER';' {conversionesUintegeraInteger = true;}
			| ALLOW error ';' {yyerror("Error Sintactico, no es una conversion valida. En la linea: "+lexico.getNrolinea());}
			| tipo TO error ';'{yyerror("Error Sintactico, no es una anotacion valida. En la linea: "+lexico.getNrolinea());}
;

constante: CONSTANTE_ENTERA{$$.obj = $1.obj;
							$$.setTipo("Integer");
							}
		| CONSTANTE_POSITIVA{$$.obj = $1.obj;
							$$.setTipo("Uinteger");
							}
//		 | error {yyerror("Error Sintactico, no es una constante valida. En la linea: "+lexico.getNrolinea());}
;
tipo: 	INTEGER{tipoVariable="Integer";}
		|UINTEGER{tipoVariable="Uinteger";}	
//		 | error {yyerror("Error Sintactico, no es un tipo valido. En la linea: "+lexico.getNrolinea());}
;


/*declaracion de SENTENCIAS EJECUTABLES -----------------------------------------------*/
s_compleja: s_ejecutable s_simple 
;

s_ejecutable: s_ejecutable s_simple 
			| s_simple 
			//conflicto shft reduce | error {yyerror("Error Sintactico, no es una secuencia compleja valida. En la linea: "+lexico.getNrolinea());}
;

s_simple: b_if {yyvalida("sentencia IF . En la linea: "+lexico.getNrolinea());}
		| b_for {yyvalida("sentencia FOR . En la linea: "+lexico.getNrolinea());}
		| b_print {yyvalida("sentencia PRINT . En la linea: "+lexico.getNrolinea());}
		| b_asignacion {yyvalida("sentencia de ASIGNACION . En la linea: "+lexico.getNrolinea());}
		
		//conflicto shft reduce| error {yyerror("Error Sintactico, no es una secuencia simple valida. En la linea: "+lexico.getNrolinea());}
;

// IF
//OCTUBRE INICIO
b_if: IF condicion_parentesis cuerpo_if {
		TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label" + Main.getTerceto().size(), Main.getTerceto().size());
		Main.addTerceto(t2); 
}
	
	//ERRORES ver error de s_simple y ver el tema de la ambiguedad
	| IF condicion_parentesis error {yyerror("Error Sintactico, falta secuencia simple o llave que abre. En la linea: "+lexico.getNrolinea());}
	| IF condicion_parentesis s_simple error {yyerror("Error Sintactico, falta un else o un endif. En la linea: "+lexico.getNrolinea());}
	| IF condicion_parentesis '{' error {yyerror("Error Sintactico, falta una secuencia compleja. En la linea: "+lexico.getNrolinea());}
	| IF condicion_parentesis '{' s_compleja error {yyerror("Error Sintactico, falta una llave que cierra. En la linea: "+lexico.getNrolinea());}
	| IF condicion_parentesis '{' s_compleja '}' error {yyerror("Error Sintactico, falta un else o un endif. En la linea: "+lexico.getNrolinea());}
	| IF condicion_parentesis '{' s_compleja '}' ';' {yyerror("Error Sintactico, falta un endif. En la linea: "+lexico.getNrolinea());}
	//conflicto shft reduce|  error {yyerror("Error Sintactico. En la linea: "+lexico.getNrolinea());}
;
	
condicion_parentesis: '(' condicion ')'{if (((Generador)$2.obj).isTerceto()){
										String aux = ((Terceto)$2.obj).getOperador();
										System.out.println(aux);
										String salida = "";
										switch(aux){
											case "<": {salida = ">=";break;}
											case "<=": {salida = ">";break;}
											case "=": {salida = "!=";break;}
											case "!=": {salida = "=";break;}
											case ">": {salida = "<=";break;}
											case ">=": {salida = "<";break;}
											default: {salida = "F"; break;}
										}
										Terceto t  = new Terceto ("B"+salida, (Generador)$2.obj, null,Main.getTerceto().size());
										Main.addTerceto(t); 
										pilaTerceto.push(t);
										}
										else
										 System.out.println("condicion if no es terceto, ERROR");
										}
;

cuerpo_if: cuerpo_then ELSE cuerpo_else ENDIF ';'{}
			| cuerpo_then2 ENDIF ';'{}
;

cuerpo_then: s_simple 	{Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size()+1;
						topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						Terceto t  = new Terceto ("BI",null, null,Main.getTerceto().size());
						Main.addTerceto(t); 
						pilaTerceto.push(t);
						TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label" + Main.getTerceto().size(), Main.getTerceto().size());
						Main.addTerceto(t2); 
						}
			| '{' s_compleja '}' {
								Terceto	 topePila=pilaTerceto.pop();
								int cantTercetos=Main.getTerceto().size()+1;
								topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
								Main.ActualizarTerceto(topePila);
								Terceto t  = new Terceto ("BI",null, null,Main.getTerceto().size());
								Main.addTerceto(t); 
								pilaTerceto.push(t);
								TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label" + Main.getTerceto().size(), Main.getTerceto().size());
								Main.addTerceto(t2);
								}
;

cuerpo_then2: s_simple 	{
						Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size();
						topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						}
			| '{' s_compleja '}' {
								Terceto topePila=pilaTerceto.pop();
								int cantTercetos=Main.getTerceto().size();
								topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
								Main.ActualizarTerceto(topePila);
								}
;
cuerpo_else: s_simple 	{
						Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size();
						topePila.setOperando1(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						}
			| '{' s_compleja '}'{
								Terceto	 topePila=pilaTerceto.pop();
								int cantTercetos=Main.getTerceto().size();
								topePila.setOperando1(new TercetoDireccionSalto("direccionSalto",cantTercetos));
								Main.ActualizarTerceto(topePila);
								}
;


condicion: expresion comparacion expresion {
									//$$.obj = new Terceto (getOperacion(), (Generador)$1.obj,(Generador) $3.obj,lexico.getTerceto().size());				
									//lexico.addTerceto((Terceto)$$.obj);
										if ($1.getTipo() != "sin definir"){
													if ($1.getTipo() == $3.getTipo()){
														Terceto t = new Terceto (getOperacion(), (Generador)$1.obj,(Generador) $3.obj,Main.getTerceto().size());				
														t.setTipo($1.getTipo());
														$$.obj = t;
														Main.addTerceto((Terceto)$$.obj);}
													else{
														if ($1.getTipo() == "Uinteger"){
															Terceto aux = new Terceto("inttouint", (Generador)$3.obj, null, Main.getTerceto().size());
															Main.addTerceto(aux);
															Terceto t =new Terceto (getOperacion(), (Generador)$1.obj,(Generador) $3.obj,Main.getTerceto().size());				
															t.setTipo("Uinteger");
	/****ACA***/											$$.obj = t;
															Main.addTerceto((Terceto)$$.obj);}
														else{
															if (conversionesUintegeraInteger){
																Terceto aux = new Terceto("uinttoint", (Generador)$3.obj, null, Main.getTerceto().size());
																Main.addTerceto(aux);
																Terceto t = new Terceto (getOperacion(), (Generador)$1.obj,(Generador) $3.obj,Main.getTerceto().size());				
																t.setTipo("Integer");
		/****ACA***/											$$.obj = t;
																Main.addTerceto((Terceto)$$.obj);}	
															else
																yyerrorSemantico("Incompatibilidad de tipos en la comparacion de la linea: " + lexico.getNrolinea());
															}
															}
															}
													else 
															yyerrorSemantico("Incompatibilidad porque a " +  $1.obj  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());													
									}
//ERRORES
		|error {yyerror(("Error Sintactico, se espera una expresion.En la Linea: "+lexico.getNrolinea()));}	
		//shft reduce |expresion error expresion{yyerror(("Error Sintactico, se espera una comparacion.En la Linea: "+lexico.getNrolinea()));}	
		|expresion comparacion error {yyerror(("Error Sintactico, se espera una expresion.En la Linea: "+lexico.getNrolinea()));}	
;

// FOR
b_for: inicio_for '(' b_asignacion_for condicion_for ';' actualizacion_variable ')' cuerpo_for{
																								Terceto topePila=pilaTerceto.pop();	
																								if (topePila.getOperando1()!=null)
																									System.out.println("esta bien");
																								int cantTercetos=Main.getTerceto().size()+1;																								
																								topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
																								Main.ActualizarTerceto(topePila);																								
																								//desapilo el numero de terceto inicio_asig
																								TercetoDireccionSalto topePilaInicio=(TercetoDireccionSalto) pilaTercetoSalto.pop();																																																
																								if (topePilaInicio.getOperando1() == null)
																									System.out.println("esta bien");
																								Terceto t  = new Terceto ("BI",new TercetoDireccionSalto("direccionSalto",topePilaInicio.getDireccionSalto()), null,Main.getTerceto().size());																																																
																								Main.addTerceto(t); 
																								cantTercetos=Main.getTerceto().size();
																								TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label"+cantTercetos,Main.getTerceto().size());
																								Main.addTerceto(t2); 
																								//pilaTerceto.push(t);																								
																							}

//ERRORES FALTAN HAY QUE VER EL TEMA DE LA AMBIGUEDAD DEL IF
	| FOR error ';' actualizacion_variable ')' s_simple {yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
	| FOR error ';' actualizacion_variable ')' '{' s_compleja '}' {yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
	| FOR error ')' s_simple {yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
	| FOR error ')' '{' s_compleja '}' {yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
;
inicio_for: FOR
condicion_for: condicion{ if (((Generador)$1.obj).isTerceto()){
										String aux = ((Terceto)$1.obj).getOperador();
										System.out.println(aux);
										String salida = "";
										switch(aux){
											case "<": {salida = ">=";break;}
											case "<=": {salida = ">";break;}
											case "=": {salida = "!=";break;}
											case "!=": {salida = "=";break;}
											case ">": {salida = "<=";break;}
											case ">=": {salida = "<";break;}
											default: {salida = "F"; break;}
										}
										Terceto t  = new Terceto ("B"+salida, (Generador)$1.obj, null,Main.getTerceto().size());
										Main.addTerceto(t); 
										pilaTerceto.push(t);
										System.out.println("apilo terceto");
						}else
							System.out.println("condicion for no es terceto, ERROR");
						}
;
cuerpo_for: s_simple
			| '{' s_compleja '}'	
;

actualizacion_variable: factor MENOSMENOS{
							if ($1.getTipo() == "Uinteger"){
								yyerrorSemantico("el tipo de la variable de control del for no corresponde con la inicializada. En la linea: " + lexico.getNrolinea());
							}
							else{
								if (!((Generador)$1.obj).isTerceto()){
								System.out.println("aux for= " + aux_for + "size " + aux_for.length());
								System.out.println("lexema= " + Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getUso() + Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getLexema() + "size" + ((Token)$1.obj).getLexema().length());
								System.out.println("tipo: " + Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getTipo());
								String aux_for2 = Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getUso()+((Token)$1.obj).getLexema();
								if (aux_for2 == aux_for2){
								System.out.println("si entraba");
									Tabla_Simbolos tabla2=new Tabla_Simbolos();
									tabla2.setLexema("1");
									tabla2.setToken(Compilador.CONSTANTE_ENTERA);
									Compilador.T_simbolos.put(tabla2.getLexema() + tabla2.getToken(), tabla2);
									
									Terceto t=new Terceto ("-", (Generador)$1.obj, new Token("", "1", Compilador.CONSTANTE_ENTERA),Main.getTerceto().size());
									t.setTipo("Integer");
									Main.addTerceto(t);
								
									Terceto tn = new Terceto(":=", (Generador)$1.obj, t, Main.getTerceto().size());
									tn.setTipo("Integer");
									$$.obj = tn;
									$$.setTipo("Integer");
									Main.addTerceto(tn);
								}
								else{
									System.out.println("MAL. aux for= " + aux_for + "size " + aux_for.length());
									System.out.println("MAL. aux for2= " + aux_for2 + "size " + aux_for.length());
									yyerrorSemantico("la variable de control del for no corresponde con la inicializada. En la linea: " + lexico.getNrolinea());
									}
								}else
									System.out.println("actualizacion de variable de for no es token, ERROR ");
								}
							}
					//shft reduce| factor error {yyerror("Error Sintactico se espera el operador menos menos. En la linea: "+lexico.getNrolinea());}
					|  error {yyerror("Error Sintactico. Al actualizar variable. En la linea: "+lexico.getNrolinea());}
;

//PRINT 
b_print: PRINT '(' CADENA_MULTILINEA ')' ';'{
											String lexema = ((Token)$3.obj).getLexema();
											int indice = 0;
											String aux = "";
											while (indice < lexema.length()){
												if (lexema.charAt(indice) != ' ')
													aux = aux + lexema.charAt(indice);
												else
													aux = aux + '_';
												indice++;
											}
											aux = aux.substring(1, aux.length()-1);
											Token t = new Token("", aux, CADENA_MULTILINEA);
											Terceto t1 = new Terceto ("PRINT", (Generador)t, null,Main.getTerceto().size());
											Main.listaTerceto.add(t1);
											$$.obj = t1;}
		| PRINT error ';' {yyerror("Error Sintactico. Al hacer un print, falta parentesis que abre. En la linea: "+lexico.getNrolinea());}
		| PRINT '(' error ')'';'{yyerror("Error Sintactico. Al hacer un print, falta la cadena multilinea. En la linea: "+lexico.getNrolinea());}
		| PRINT '(' CADENA_MULTILINEA error ';'{yyerror("Error Sintactico. Al hacer un print, falta parentesis que cierra. En la linea: "+lexico.getNrolinea());}	
		| PRINT '(' CADENA_MULTILINEA ')' error ';'{yyerror("Error Sintactico. Al hacer un print, falta el punto y coma. En la linea: "+lexico.getNrolinea());}			
;

b_asignacion_for: inicio_asig_for ASIGNACION expresion ';'	{
						if ($1.getTipo() != "sin definir"){
							if ($1.getTipo() == $3.getTipo()){
								Terceto t=new Terceto(":=", (Generador)$1.obj, (Generador)$3.obj, Main.getTerceto().size());
								t.setTipo($1.getTipo());
								$$.obj =t;	
								Main.addTerceto((Terceto)$$.obj);}
							else{
								if ($1.getTipo() == "Uinteger"){
									Terceto aux = new Terceto("inttouint", (Generador)$3.obj, null, Main.getTerceto().size());
									Main.addTerceto(aux);
									Terceto t=new Terceto(":=", (Generador)$1.obj, aux, Main.getTerceto().size());
									t.setTipo("Uinteger");
									$$.obj = t;
									Main.addTerceto((Terceto)$$.obj);}
								else{
									if (conversionesUintegeraInteger){
										Terceto aux = new Terceto("uinttoint", (Generador)$3.obj, null, Main.getTerceto().size());
										Main.addTerceto(aux);
										Terceto t=new Terceto(":=", (Generador)$1.obj, aux, Main.getTerceto().size());
										t.setTipo("Uinteger");
										$$.obj = t;	
										Main.addTerceto((Terceto)$$.obj);
									}	
									else
										yyerrorSemantico("Incompatibilidad de tipos en la asignacion de la linea: " + lexico.getNrolinea());
								}
							}
						}
						else 
							yyerrorSemantico("Incompatibilidad porque a " +  $1  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());

						int cantTercetos=Main.getTerceto().size();
						TercetoDireccionSalto t3=new TercetoDireccionSalto("direccionInicioSalto",cantTercetos);
						pilaTercetoSalto.push(t3);
						System.out.println("apilo terceto salto");
						cantTercetos=Main.getTerceto().size();
						TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label"+cantTercetos, Main.getTerceto().size());
						Main.addTerceto(t2);
	 					}
;
//ASIGNACION
b_asignacion: inicio_asig ASIGNACION expresion ';'	{
												if ($1.getTipo() != ""){
													if ($1.getTipo() == $3.getTipo()){
													Terceto t1 = new Terceto(":=", (Generador)$1.obj, (Generador)$3.obj, Main.getTerceto().size());
													t1.setTipo($1.getTipo());
														$$.obj = t1;
														Main.addTerceto((Terceto)$$.obj);}
													else{
														if ($1.getTipo() == "Uinteger"){
															Terceto aux = new Terceto("inttouint", (Generador)$3.obj, null, Main.getTerceto().size());
															Main.addTerceto(aux);
															Terceto t1 = new Terceto(":=", (Generador)$1.obj, aux, Main.getTerceto().size());
															t1.setTipo($1.getTipo());
	/****ACA***/											$$.obj = t1;
															((Terceto)$$.obj).setTipo("Uinteger");
															Main.addTerceto((Terceto)$$.obj);}
														else{
															if (conversionesUintegeraInteger){
							//									System.out.println("$1 tiene tipo: " + $1.getTipo());
																Terceto aux = new Terceto("uinttoint", (Generador)$3.obj, null, Main.getTerceto().size());
																Main.addTerceto(aux);
																Terceto t1 = new Terceto(":=", (Generador)$1.obj, aux, Main.getTerceto().size());
																t1.setTipo($1.getTipo());
		/****ACA***/											$$.obj = t1;
																((Terceto)$$.obj).setTipo("Integer");
																Main.addTerceto((Terceto)$$.obj);}	
															else
																yyerrorSemantico("Incompatibilidad de tipos en la asignacion de la linea: " + lexico.getNrolinea());
															}
															}
															}
												else 
													yyerrorSemantico("Incompatibilidad porque a " +  $1.obj  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());													
}
			//errores
				| inicio_asig ASIGNACION error ';' {yyerror("Error Sintactico al realizar una asignacion falta expresion. En la linea: "+lexico.getNrolinea());}
;

inicio_asig_for: ID	{if (ExisteTipo((Token)$1.obj, "var@")){
						((Token)$1.obj).setUso("var@");
						if ((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getTipo()=="Integer")){
							$$.obj = $1.obj;
							aux_for = "var@" +((Token)$1.obj).getLexema();							
							$$.setTipo((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())).getTipo());
						}
						else
							yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea" + lexico.getNrolinea());
					}
				else{
					$$.obj = $1.obj;
					$$.setTipo("sin definir");
					yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea" + lexico.getNrolinea());
					}}
		|matriz_ejecutable {if (ExisteTipo((Token)$1.obj, "mat@")){
								if ((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken()).getTipo()=="Integer")){
			/*????*/				$$.obj = $1.obj;
									aux_for = "mat@"+((Token)$1.obj).getLexema();
									$$.setTipo((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())).getTipo());
								}
								else
									yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea: " + lexico.getNrolinea());
							}else{
								$$.obj = $1.obj;
								$$.setTipo("sin definir");
								yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea" + lexico.getNrolinea());
								}
							}
;

inicio_asig: ID 	{
					if (ExisteTipo((Token)$1.obj, "var@")){
					((Token)$1.obj).setUso("var@");
						$$.obj = $1.obj;
						$$.setTipo((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())).getTipo());
						}
					else{
						$$.obj = $1.obj;
						$$.setTipo("");
						}
					}
			| matriz_ejecutable{
								$$.obj = $1.obj;
								$$.setTipo($1.getTipo());
			//					System.out.println("tipo mat inicio asig " + $1.getTipo());
							}							
;
expresion: expresion '+' termino {if ($1.getTipo() == $3.getTipo()){
									Terceto t1 =new Terceto ("+", (Generador)$1.obj,(Generador) $3.obj,Main.getTerceto().size());
									t1.setTipo($1.getTipo());
									$$.obj = t1;
									$$.setTipo($1.getTipo());
									Main.addTerceto((Terceto)$$.obj);}
								else{
									
									if ($1.getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)$3.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("+", (Generador)$1.obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)$$.obj);}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)$1.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("+", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) $3.obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)$$.obj);}}}
		 | expresion '-' termino {if ($1.getTipo() == $3.getTipo()){
									Terceto t1 = new Terceto ("-", (Generador)$1.obj,(Generador) $3.obj,Main.getTerceto().size());
									t1.setTipo($1.getTipo());
									$$.obj = t1;
									Main.addTerceto((Terceto)$$.obj);
									$$.setTipo($1.getTipo());}
								else{
									
									if ($1.getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)$3.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("-", (Generador)$1.obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										Main.addTerceto((Terceto)$$.obj);
										$$.setTipo("Uinteger");}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)$1.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("-", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) $3.obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										Main.addTerceto((Terceto)$$.obj);
										$$.setTipo("Uinteger");}}}
		 | termino {$$.obj = $1.obj;
					$$.setTipo($1.getTipo());}
;
termino: termino '*' miembro{if (($1.getTipo() == "Integer") && ($3.getTipo() == "Integer")){
									Terceto t1 = new Terceto ("*",(Generador) $1.obj,(Generador) $3.obj,Main.getTerceto().size());
									t1.setTipo("Integer");
									$$.obj = t1;
									Main.addTerceto((Terceto)t1);
									$$.setTipo("Integer");
							}
							else{
								if ($1.getTipo() == $3.getTipo()){
									Terceto t1 = new Terceto ("*",(Generador) $1.obj,(Generador) $3.obj,Main.getTerceto().size());
									t1.setTipo("Uinteger");
									$$.obj = t1;
									Main.addTerceto((Terceto)t1);
									$$.setTipo("Uinteger");
								}
								else{
									
									if ($1.getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)$3.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("*", (Generador)$1.obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
									}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)$1.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("*", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) $3.obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
										}
									}
								}
							}

	   | termino '/' miembro {
							if (($1.getTipo() == "Integer") && ($3.getTipo() == "Integer")){
								Terceto t1 = new Terceto ("/",(Generador) $1.obj,(Generador) $3.obj,Main.getTerceto().size());
								t1.setTipo ("Integer");
								$$.obj = t1;
								Main.addTerceto((Terceto)t1);
								$$.setTipo("Integer");
							}
							else{
								if ($1.getTipo() == $3.getTipo()){
									Terceto t1 = new Terceto ("/",(Generador) $1.obj,(Generador) $3.obj,Main.getTerceto().size());
									t1.setTipo("Uinteger");
									$$.obj = t1;
									Main.addTerceto((Terceto)t1);
									$$.setTipo("Uinteger");}
								else{
									if ($1.getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)$3.obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("/", (Generador)$1.obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)$1.obj, null, Main.getTerceto().size()));
										Terceto t1 =new Terceto ("/", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) $3.obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										$$.obj = t1;
										$$.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
										}
									}
								}
							}									
	   | miembro {$$.obj = $1.obj;
				$$.setTipo($1.getTipo());
				}
;
miembro: factor MENOSMENOS{
							if ($1.getTipo() == "Uinteger"){
								Enumeration<String>  e = Compilador.T_simbolos.keys();
								Object clave;
								boolean necesito_alta = true;
								while( e.hasMoreElements() ){
								  clave = e.nextElement();
									if (clave == "1267")
										necesito_alta = false;
								}
								if (necesito_alta){
									Tabla_Simbolos t1=new Tabla_Simbolos();
									t1.setLexema("1");
									t1.setToken(Compilador.CONSTANTE_POSITIVA);
									t1.setTipo("Uinteger");
									Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
									}
								Terceto t=new Terceto ("-", (Generador)$1.obj, new Token("", "1", Compilador.CONSTANTE_POSITIVA),Main.getTerceto().size());
								t.setTipo("Uinteger");
								Main.addTerceto(t);
//								Terceto t1 = new Terceto(":=", (Generador)$1.obj, t, Main.getTerceto().size());
//								t1.setTipo("Uinteger");
//								Main.addTerceto((Terceto)t1);
//								$$.obj = t1;
								$$.obj = t;
								$$.setTipo("Uinteger");
							}
							else{
								Enumeration<String>  e = Compilador.T_simbolos.keys();
								Object clave;
								boolean necesito_alta = true;
								while( e.hasMoreElements() ){
								  clave = e.nextElement();
									if (clave == "1268")
										necesito_alta = false;
								}
								if (necesito_alta){
									Tabla_Simbolos t1=new Tabla_Simbolos();
									t1.setLexema("1");
									t1.setToken(Compilador.CONSTANTE_ENTERA);
									t1.setTipo("Integer");
									Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
									}
								Terceto t=new Terceto ("-", (Generador)$1.obj, new Token("", "1", Compilador.CONSTANTE_ENTERA),Main.getTerceto().size());
								t.setTipo("Integer");
								Main.addTerceto(t);
//								Terceto t1 = new Terceto(":=", (Generador)$1.obj, t, Main.getTerceto().size());
//								t1.setTipo("Integer");
//								$$.obj = t1;
								$$.obj = t;
								$$.setTipo("Integer");
//								Main.addTerceto((Terceto)t1);
							}
						}
		| factor	{$$.obj = $1.obj;
					$$.setTipo($1.getTipo());
					}
;

factor: ID	{if (ExisteTipo((Token)$1.obj, "var@")){
				((Token)$1.obj).setUso("var@");
				$$.setTipo((Compilador.get_Tsimbolos(((Token)$1.obj).getClaveTablaSimboloToken())).getTipo());
				$$.obj = $1.obj;
			}else
				$$.setTipo("");
			}
		| CONSTANTE_POSITIVA {	$$.obj = $1.obj;
								$$.setTipo("Uinteger");}
		| CONSTANTE_ENTERA {$$.obj = $1.obj;
							$$.setTipo("Integer");
							}
		|matriz_ejecutable {$$.obj = $1.obj;
								$$.setTipo($1.getTipo());
					}
;


comparacion: '<' {comparar = "<";}
			| MENORIGUAL{comparar="<=";}
			| '>'{comparar=">";}
			| MAYORIGUAL{comparar=">=";}
			| '='{comparar="=";}
			| DISTINTO{comparar="!=";}
			//| error {yyerror("Error Sintactico tipo de comparacion no conocida. En la linea: "+lexico.getNrolinea());}
;


%%

//CODIGO

Lexico lexico;
boolean error;
List<Token> listaTipoVariable;
String tipoVariable;
boolean conversionesUintegeraInteger;
int numFila ;
int numCol;
List<Integer>listaInicMatriz;
int [][] elementosMatriz;
List<String> listaTiposInicMatriz;
private Stack<Terceto> pilaTerceto;
private Stack<TercetoDireccionSalto> pilaTercetoSalto;
String label;
String comparar;
String aux_for;

private void yyerror(String mensaje){
	error = true;
	Main.listaErroresSintacticos.add(mensaje);
}
private void yyvalida(String mensaje){
	Main.listaSentenciasValidas.add(mensaje);
}
public boolean ExisteTipo(Token t, String aux){
	String var = (aux +t.getClaveTablaSimboloToken());
	if((Compilador.get_Tsimbolos(var)!=null)&&((Compilador.get_Tsimbolos(var).getTipo()=="Uinteger") ||(Compilador.get_Tsimbolos(var).getTipo()=="Integer"))){
		return true;}
	else{
	//	System.out.println("no fue definido, poruqe matchea con: " + var);
		yyerrorSemantico("El token: "+t.getLexema()+", no tiene tipo asignado, y se lo usa en la linea: " + lexico.getNrolinea());
		return false;}
		}
		
private int yylex() throws IOException{
	Token aux = lexico.yylex();
	yylval = new ParserVal(aux);
	return ((Token)aux).getToken();
}
private void yyerrorSemantico(String mensaje){
	Main.listaErroresSemanticos.add(mensaje);
}
public Parser(String archivo) throws FileNotFoundException, IOException
{
		label = new String();
		label = "";
		aux_for = new String();
		aux_for = "";
		pilaTerceto= new Stack<>();
		pilaTercetoSalto = new Stack<>();
		listaTipoVariable = new ArrayList<Token>();
		
		comparar = "";
		listaInicMatriz = new ArrayList<Integer>();
		listaTiposInicMatriz = new ArrayList<String>();
		lexico = new Lexico();
		lexico.prueba = lexico.abrirArchivo(archivo);
		
		conversionesUintegeraInteger = false;
		tipoVariable="";
}
public String getOperacion(){
	return comparar;
}
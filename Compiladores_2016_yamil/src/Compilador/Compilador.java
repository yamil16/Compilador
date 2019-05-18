
package Compilador;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Compilador {
	public static int ID = 257;
	public static int THEN =258;
	public static int IF =259;
	public static int ELSE =260;
	public static int PRINT=261;
	public static int ENDIF=262;
	public static int INTEGER=263;
	public static int UINTEGER=264;
	public static int MATRIX=265;
	public static int FOR=266;	
	public static int CONSTANTE_POSITIVA=267;
	public static int CONSTANTE_ENTERA=268;
	public static int MENOSMENOS=269;
	public static int ASIGNACION=270;
	public static int DISTINTO=271;
	public static int MENORIGUAL=272;
	public static int MAYORIGUAL=273;
	public static int ANOTACION_CERO=274;
	public static int ANOTACION_UNO=275;
	public static int CADENA_MULTILINEA=276;
	public static int FIN = 0;
	public static int ALLOW=278;
	public static int TO=279;
	/*
	public static int ID = 260;
	public static int THEN =261;
	public static int IF =262;
	public static int ELSE =263;
	public static int PRINT=264;
	public static int ENDIF=265;
	public static int INTEGER=266;
	public static int UINTEGER=267;
	public static int MATRIX=268;
	public static int FOR=269;	
	public static int CONSTANTE_POSITIVA=270;
	public static int CONSTANTE_ENTERA=271;
	public static int MENOSMENOS=272;
	public static int ASIGNACION=273;
	public static int DISTINTO=274;
	public static int MENORIGUAL=275;
	public static int MAYORIGUAL=276;
	public static int ANOTACION_CERO=277;
	public static int ANOTACION_UNO=278;
	public static int CADENA_MULTILINEA=279;
	public static int FIN = 280;
	*/
	
	public static Hashtable<String,Tabla_Simbolos> T_simbolos;
	public static List<String> errores = new ArrayList();
	public static List<String> warnings = new ArrayList();
	
	public static Tabla_Simbolos get_Tsimbolos(String s) {
		return T_simbolos.get(s);
	}



	


	public Compilador() {
		super();
		
		T_simbolos= new Hashtable<String,Tabla_Simbolos>();
		
		Tabla_Simbolos t1=new Tabla_Simbolos();
		t1.setInformacion("palabra reservada if");/*v*/
		t1.setLexema("if");
		t1.setToken(IF);
		T_simbolos.put(t1.getLexema() + t1.getToken(), t1);
		
		Tabla_Simbolos t2=new Tabla_Simbolos();
		t2.setInformacion("palabra reservada else");/*v*/
		t2.setLexema("else");
		t2.setToken(ELSE);
		T_simbolos.put(t2.getLexema() + t2.getToken(), t2);
		
		Tabla_Simbolos t3=new Tabla_Simbolos();
		t3.setInformacion("palabra reservada ENDIF");/*v*/
		t3.setLexema("endif");
		t3.setToken(ENDIF);
		T_simbolos.put(t3.getLexema() + t3.getToken(), t3);
	
		
		Tabla_Simbolos t4=new Tabla_Simbolos();
		t4.setInformacion("palabra reservada integer");/*v*/
		t4.setLexema("integer");
		t4.setToken(INTEGER);
		T_simbolos.put(t4.getLexema() + t4.getToken(), t4);
		
		Tabla_Simbolos t5=new Tabla_Simbolos();
		t5.setInformacion("palabra reservada matrix");/*v*/
		t5.setLexema("matrix");
		t5.setToken(MATRIX);
		T_simbolos.put(t5.getLexema() + t5.getToken(), t5);
		
		Tabla_Simbolos t6=new Tabla_Simbolos();
		t6.setInformacion("palabra reservada for");/*v*/
		t6.setLexema("for");
		t6.setToken(FOR);
		T_simbolos.put(t6.getLexema() + t6.getToken(), t6);
		
		
		Tabla_Simbolos t7=new Tabla_Simbolos();
		t7.setInformacion("palabra reservada uinteger");/*v*/
		t7.setLexema("uinteger");
		t7.setToken(UINTEGER);
		T_simbolos.put(t7.getLexema() + t7.getToken(), t7);
		
		Tabla_Simbolos t8=new Tabla_Simbolos();
		t8.setInformacion("palabra reservada print");/*v*/
		t8.setLexema("print");
		t8.setToken(PRINT);
		T_simbolos.put(t8.getLexema() + t8.getToken(), t8);
		
		Tabla_Simbolos t9=new Tabla_Simbolos();
		t9.setInformacion("palabra reservada THEN");/*v*/
		t9.setLexema("then");
		t9.setToken(THEN);
		T_simbolos.put(t9.getLexema() + t9.getToken(), t9);
		
		Tabla_Simbolos t10=new Tabla_Simbolos();
		t10.setInformacion("palabra reservada ALLOW");/*v*/
		t10.setLexema("allow");
		t10.setToken(ALLOW);
		T_simbolos.put(t10.getLexema() + t10.getToken(), t10);
		
		Tabla_Simbolos t11=new Tabla_Simbolos();
		t11.setInformacion("palabra reservada TO");/*v*/
		t11.setLexema("to");
		t11.setToken(TO);
		T_simbolos.put(t11.getLexema() + t11.getToken(), t11);
		
		/*//creo que no va
		t.setInformacion("Anotaciones &&@0");
		t.setLexema("&&@0");
		t.setToken(28);
		T_simbolos.put("&&@028", t);
		
		t.setInformacion("Anotaciones &&@1");
		t.setLexema("&&@1");
		t.setToken(29);
		T_simbolos.put("&&@129", t);
		*/
		/*
		t.setInformacion("IDentificador"); //VER SI VA
		t.setLexema("");
		t.setToken(ID);
		T_simbolos.put("IDID", t);
		
		
		//ver creo que hay que poner contante solo y en el tipo IDentificamos cada una
		t.setInformacion("Constante entera");
		t.setLexema("_i");
		t.setToken(CONTANTE_ENTERA);
		T_simbolos.put("cteCONTANTE_ENTERA", t);
		
		t.setInformacion("Constante entera positiva");
		t.setLexema("_ui");
		t.setToken(CONTANTE_POSITIVA);
		T_simbolos.put("cte", t);*/
		
	}
	
	
	
	/*public static void main(String[] args) throws IOException {
		Compilador c = new Compilador();
		Lexico l= new Lexico();
		//String archivo =("C:\\Users\\juan\\workspace\\2015\\Compiladores\\entrada.txt");
	//	l.prueba = l.abrirArchivo(archivo);
		for(int i=0;i<13;i++){
			
			int token=l.yylex();
			System.out.println("imprimir token "+token);
		}
		if (errores.isEmpty()){
			if(warnings.isEmpty())
				System.out.println("el programa compila");
			else
				System.out.println("hay warnings, se realizaron cambios para que el programa compile");
		}
		else
			System.out.println( "hay errores, por lo tanto el programa falló");
		
		Enumeration e = T_simbolos.keys();
		Object clave;
		while( e.hasMoreElements() ){
			  clave = e.nextElement();
			  System.out.println( "Clave : " + clave );
			}
	}*/
	
	
}

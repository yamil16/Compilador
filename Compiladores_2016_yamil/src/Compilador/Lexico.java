package Compilador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Lexico {

public String abrirArchivo(String nombre) throws FileNotFoundException, IOException {
	try{
		List<String> str = Files.readAllLines(Paths.get(nombre), Charset.defaultCharset());
		String cadena="";
		while (str.size()> 0){
			cadena+=str.remove(0);
			cadena+= ((char) 10);
		}
		return cadena;
	}catch(IOException e){ 
		return null;
	}
}

	
	protected  String prueba;
	protected  int [][] matrizEstado;		
	protected  Accion_Semantica [][]accionSemantica;		
	
	public static  int nrolinea;
	private  int estado;
	private  String buffer_temporal;
	private  char entrada;
	private  int posEntrada;	


	public Lexico(){//Hashtable<String,Tabla_Simbolo> T_simbolos) {
		super();
		matrizEstado= new int [18][21];
		accionSemantica= new Accion_Semantica[18][21];
		estado=0;
		posEntrada=0;
		cargarMatrizEstado();
		cargarMatrizAccionSemantica();			
		 nrolinea=1;
		
	}


	public void cargarMatrizEstado(){
		//final=17
		//error= 18
		matrizEstado[0][0]=1;
		matrizEstado[0][1]=1;
		matrizEstado[0][2]=1;
		matrizEstado[0][3]=1;
		matrizEstado[0][4]=-1; //error
		matrizEstado[0][5]=-1;
		matrizEstado[0][6]=-1;
		matrizEstado[0][7]=2;
		matrizEstado[0][8]=7;
		matrizEstado[0][9]=8;
		matrizEstado[0][10]=9;
		matrizEstado[0][11]=17;
		matrizEstado[0][12]=15;
		matrizEstado[0][13]=17;
		matrizEstado[0][14]=10; 
		matrizEstado[0][15]=-1;
		matrizEstado[0][16]=0;
		matrizEstado[0][17]=17; 
		matrizEstado[0][18]=0;
		matrizEstado[0][19]=17; //eof = fin
		matrizEstado[0][20]=-1; //otro = error
		
		matrizEstado[1][0]=1;
		matrizEstado[1][1]=1;
		matrizEstado[1][2]=1;
		matrizEstado[1][3]=1;
		matrizEstado[1][4]=1;
		matrizEstado[1][5]=1;
		matrizEstado[1][6]=1;
		matrizEstado[1][7]=1;
		matrizEstado[1][8]=17;
		matrizEstado[1][9]=17;
		matrizEstado[1][10]=17;
		matrizEstado[1][11]=17;
		matrizEstado[1][12]=17;
		matrizEstado[1][13]=17;
		matrizEstado[1][14]=17;
		matrizEstado[1][15]=17;
		matrizEstado[1][16]=17;
		matrizEstado[1][17]=17;
		matrizEstado[1][18]=17;
		matrizEstado[1][19]=17;
		matrizEstado[1][20]=17;
		
		matrizEstado[2][0]=3;
		matrizEstado[2][1]=4;
		matrizEstado[2][2]=-1;
		matrizEstado[2][3]=-1;
		matrizEstado[2][4]=-1;
		matrizEstado[2][5]=-1;
		matrizEstado[2][6]=-1;
		matrizEstado[2][7]=-1;
		matrizEstado[2][8]=-1;
		matrizEstado[2][9]=-1;
		matrizEstado[2][10]=-1;
		matrizEstado[2][11]=-1;
		matrizEstado[2][12]=-1;
		matrizEstado[2][13]=-1;
		matrizEstado[2][14]=-1;
		matrizEstado[2][15]=-1;
		matrizEstado[2][16]=-1;
		matrizEstado[2][17]=-1;
		matrizEstado[2][18]=-1;
		matrizEstado[2][19]=-1;
		matrizEstado[2][20]=-1;
		
		matrizEstado[3][0]=-1;
		matrizEstado[3][1]=-1;
		matrizEstado[3][2]=-1;
		matrizEstado[3][3]=-1;
		matrizEstado[3][4]=5; 
		matrizEstado[3][5]=5;
		matrizEstado[3][6]=5;
		matrizEstado[3][7]=-1; 
		matrizEstado[3][8]=6;
		matrizEstado[3][9]=-1;
		matrizEstado[3][10]=-1;
		matrizEstado[3][11]=-1;
		matrizEstado[3][12]=-1;
		matrizEstado[3][13]=-1;
		matrizEstado[3][14]=-1;
		matrizEstado[3][15]=-1;
		matrizEstado[3][16]=-1;
		matrizEstado[3][17]=-1;
		matrizEstado[3][18]=-1;
		matrizEstado[3][19]=-1;
		matrizEstado[3][20]=-1;
		
		
		matrizEstado[4][0]=3;
		matrizEstado[4][1]=-1;
		matrizEstado[4][2]=-1;
		matrizEstado[4][3]=-1;
		matrizEstado[4][4]=-1;
		matrizEstado[4][5]=-1;
		matrizEstado[4][6]=-1;
		matrizEstado[4][7]=-1;
		matrizEstado[4][8]=-1;
		matrizEstado[4][9]=-1;
		matrizEstado[4][10]=-1;
		matrizEstado[4][11]=-1;
		matrizEstado[4][12]=-1;
		matrizEstado[4][13]=-1;
		matrizEstado[4][14]=-1;
		matrizEstado[4][15]=-1;
		matrizEstado[4][16]=-1;
		matrizEstado[4][17]=-1;
		matrizEstado[4][18]=-1;
		matrizEstado[4][19]=-1;
		matrizEstado[4][20]=-1;
		
		
		matrizEstado[5][0]=17;
		matrizEstado[5][1]=17;
		matrizEstado[5][2]=17;
		matrizEstado[5][3]=17;
		matrizEstado[5][4]=5; 
		matrizEstado[5][5]=5;
		matrizEstado[5][6]=5;
		matrizEstado[5][7]=17;
		matrizEstado[5][8]=17;
		matrizEstado[5][9]=17;
		matrizEstado[5][10]=17;
		matrizEstado[5][11]=17;
		matrizEstado[5][12]=17;
		matrizEstado[5][13]=17;
		matrizEstado[5][14]=17;
		matrizEstado[5][15]=17;
		matrizEstado[5][16]=17;
		matrizEstado[5][17]=17;
		matrizEstado[5][18]=17;
		matrizEstado[5][19]=17;
		matrizEstado[5][20]=17;
		
		matrizEstado[6][0]=-1;
		matrizEstado[6][1]=-1;
		matrizEstado[6][2]=-1;
		matrizEstado[6][3]=-1;
		matrizEstado[6][4]=5; 
		matrizEstado[6][5]=5;
		matrizEstado[6][6]=5;
		matrizEstado[6][7]=-1;
		matrizEstado[6][8]=-1;
		matrizEstado[6][9]=-1;
		matrizEstado[6][10]=-1;
		matrizEstado[6][11]=-1;
		matrizEstado[6][12]=-1;
		matrizEstado[6][13]=-1;
		matrizEstado[6][14]=-1;
		matrizEstado[6][15]=-1;
		matrizEstado[6][16]=-1;
		matrizEstado[6][17]=-1;
		matrizEstado[6][18]=-1;
		matrizEstado[6][19]=-1;
		matrizEstado[6][20]=-1;
	
		matrizEstado[7][0]=17;
		matrizEstado[7][1]=17;
		matrizEstado[7][2]=17;
		matrizEstado[7][3]=17;
		matrizEstado[7][4]=17;
		matrizEstado[7][5]=17;
		matrizEstado[7][6]=17;
		matrizEstado[7][7]=17;
		matrizEstado[7][8]=17;
		matrizEstado[7][9]=17;
		matrizEstado[7][10]=17;
		matrizEstado[7][11]=17;
		matrizEstado[7][12]=17;
		matrizEstado[7][13]=17;
		matrizEstado[7][14]=17;
		matrizEstado[7][15]=17;
		matrizEstado[7][16]=17;
		matrizEstado[7][17]=17;
		matrizEstado[7][18]=17;
		matrizEstado[7][19]=17;
		matrizEstado[7][20]=17;
		
		
		matrizEstado[8][0]=-1;
		matrizEstado[8][1]=-1;
		matrizEstado[8][2]=-1;
		matrizEstado[8][3]=-1;
		matrizEstado[8][4]=-1;
		matrizEstado[8][5]=-1;
		matrizEstado[8][6]=-1;
		matrizEstado[8][7]=-1;
		matrizEstado[8][8]=-1;
		matrizEstado[8][9]=-1;
		matrizEstado[8][10]=-1;
		matrizEstado[8][11]=17;
		matrizEstado[8][12]=-1;
		matrizEstado[8][13]=-1;
		matrizEstado[8][14]=-1;
		matrizEstado[8][15]=-1;
		matrizEstado[8][16]=-1;
		matrizEstado[8][17]=-1;
		matrizEstado[8][18]=-1;
		matrizEstado[8][19]=-1;
		matrizEstado[8][20]=-1;
		
		
		matrizEstado[9][0]=17;
		matrizEstado[9][1]=17;
		matrizEstado[9][2]=17;
		matrizEstado[9][3]=17;
		matrizEstado[9][4]=17;
		matrizEstado[9][5]=17;
		matrizEstado[9][6]=17;
		matrizEstado[9][7]=17;
		matrizEstado[9][8]=17;
		matrizEstado[9][9]=17;
		matrizEstado[9][10]=17;
		matrizEstado[9][11]=17;
		matrizEstado[9][12]=17;
		matrizEstado[9][13]=17;
		matrizEstado[9][14]=17;
		matrizEstado[9][15]=17;
		matrizEstado[9][16]=17;
		matrizEstado[9][17]=17;
		matrizEstado[9][18]=17;
		matrizEstado[9][19]=17;
		matrizEstado[9][20]=17;
	
		matrizEstado[10][0]=-1;
		matrizEstado[10][1]=-1;
		matrizEstado[10][2]=-1;
		matrizEstado[10][3]=-1;
		matrizEstado[10][4]=-1;
		matrizEstado[10][5]=-1;
		matrizEstado[10][6]=-1;
		matrizEstado[10][7]=-1;
		matrizEstado[10][8]=-1;
		matrizEstado[10][9]=-1;
		matrizEstado[10][10]=-1;
		matrizEstado[10][11]=-1;
		matrizEstado[10][12]=-1;
		matrizEstado[10][13]=-1;
		matrizEstado[10][14]=11;
		matrizEstado[10][15]=-1;
		matrizEstado[10][16]=-1; 
		matrizEstado[10][17]=-1;
		matrizEstado[10][18]=-1;
		matrizEstado[10][19]=-1;
		matrizEstado[10][20]=-1;

	
		matrizEstado[11][0]=14;
		matrizEstado[11][1]=14;
		matrizEstado[11][2]=14;
		matrizEstado[11][3]=14;
		matrizEstado[11][4]=14;
		matrizEstado[11][5]=14;
		matrizEstado[11][6]=14;
		matrizEstado[11][7]=14;
		matrizEstado[11][8]=14;
		matrizEstado[11][9]=14;
		matrizEstado[11][10]=14;
		matrizEstado[11][11]=14;
		matrizEstado[11][12]=14;
		matrizEstado[11][13]=14;
		matrizEstado[11][14]=14;
		matrizEstado[11][15]=12;
		matrizEstado[11][16]=0;
		matrizEstado[11][17]=14;
		matrizEstado[11][18]=14;
		matrizEstado[11][19]=0; //documentar consideramos que puede haber comentarios vacios
		matrizEstado[11][20]=14;

		
	
		matrizEstado[12][0]=14;
		matrizEstado[12][1]=14;
		matrizEstado[12][2]=14;
		matrizEstado[12][3]=14;
		matrizEstado[12][4]=13;
		matrizEstado[12][5]=13;
		matrizEstado[12][6]=14;
		matrizEstado[12][7]=14;
		matrizEstado[12][8]=14;  
		matrizEstado[12][9]=14;
		matrizEstado[12][10]=14;
		matrizEstado[12][11]=14;
		matrizEstado[12][12]=14;
		matrizEstado[12][13]=14;
		matrizEstado[12][14]=14;
		matrizEstado[12][15]=14;
		matrizEstado[12][16]=0;
		matrizEstado[12][17]=14;
		matrizEstado[12][18]=14;
		matrizEstado[12][19]=0;
		matrizEstado[12][20]=14;

		
		matrizEstado[13][0]=13;
		matrizEstado[13][1]=13;
		matrizEstado[13][2]=13;
		matrizEstado[13][3]=13;
		matrizEstado[13][4]=13;
		matrizEstado[13][5]=13;
		matrizEstado[13][6]=13;
		matrizEstado[13][7]=13;
		matrizEstado[13][8]=13; 
		matrizEstado[13][9]=13;
		matrizEstado[13][10]=13;
		matrizEstado[13][11]=13; 
		matrizEstado[13][12]=13;
		matrizEstado[13][13]=13;
		matrizEstado[13][14]=13;
		matrizEstado[13][15]=13;
		matrizEstado[13][16]=17;
		matrizEstado[13][17]=13;
		matrizEstado[13][18]=13;
		matrizEstado[13][19]=17;
		matrizEstado[13][20]=13;

	
		matrizEstado[14][0]=14;
		matrizEstado[14][1]=14;
		matrizEstado[14][2]=14;
		matrizEstado[14][3]=14;
		matrizEstado[14][4]=14;
		matrizEstado[14][5]=14;
		matrizEstado[14][6]=14;
		matrizEstado[14][7]=14;
		matrizEstado[14][8]=14;
		matrizEstado[14][9]=14;
		matrizEstado[14][10]=14;
		matrizEstado[14][11]=14; 
		matrizEstado[14][12]=14; 
		matrizEstado[14][13]=14;
		matrizEstado[14][14]=14;
		matrizEstado[14][15]=14;
		matrizEstado[14][16]=0;
		matrizEstado[14][17]=14;
		matrizEstado[14][18]=14;
		matrizEstado[14][19]=0;
		matrizEstado[14][20]=14;

		
		
		matrizEstado[15][0]=15;
		matrizEstado[15][1]=15;
		matrizEstado[15][2]=15;
		matrizEstado[15][3]=15;
		matrizEstado[15][4]=15;
		matrizEstado[15][5]=15;
		matrizEstado[15][6]=15;
		matrizEstado[15][7]=15;
		matrizEstado[15][8]=15;
		matrizEstado[15][9]=15;
		matrizEstado[15][10]=15; 
		matrizEstado[15][11]=15;
		matrizEstado[15][12]=17;
		matrizEstado[15][13]=16;
		matrizEstado[15][14]=15;
		matrizEstado[15][15]=15;
		matrizEstado[15][16]=-1;
		matrizEstado[15][17]=15;
		matrizEstado[15][18]=15;
		matrizEstado[15][19]=-1; 
		matrizEstado[15][20]=15;

		
		matrizEstado[16][0]=15;
		matrizEstado[16][1]=15;
		matrizEstado[16][2]=15;
		matrizEstado[16][3]=15;
		matrizEstado[16][4]=15;
		matrizEstado[16][5]=15;
		matrizEstado[16][6]=15;
		matrizEstado[16][7]=15;
		matrizEstado[16][8]=15;
		matrizEstado[16][9]=15;
		matrizEstado[16][10]=15;
		matrizEstado[16][11]=15;
		matrizEstado[16][12]=17;
		matrizEstado[16][13]=16;
		matrizEstado[16][14]=15;
		matrizEstado[16][15]=15;
		matrizEstado[16][16]=15;
		matrizEstado[16][17]=15; 
		matrizEstado[16][18]=15;
		matrizEstado[16][19]=-1; //idem al 15
		matrizEstado[16][20]=15;

		
		matrizEstado[17][0]=17;
		matrizEstado[17][1]=17;
		matrizEstado[17][2]=17;
		matrizEstado[17][3]=17;
		matrizEstado[17][4]=17; 
		matrizEstado[17][5]=17;
		matrizEstado[17][6]=17;
		matrizEstado[17][7]=17;
		matrizEstado[17][8]=17;
		matrizEstado[17][9]=17;
		matrizEstado[17][10]=17;
		matrizEstado[17][11]=17;
		matrizEstado[17][12]=17;
		matrizEstado[17][13]=17;
		matrizEstado[17][14]=17;
		matrizEstado[17][15]=17;
		matrizEstado[17][16]=17;
		matrizEstado[17][17]=17;
		matrizEstado[17][18]=17;
		matrizEstado[17][19]=17;	
		matrizEstado[17][20]=17;

/*
		matrizEstado[-1][0]=0;
		matrizEstado[-1][1]=0;
		matrizEstado[-1][2]=0;
		matrizEstado[-1][3]=0;
		matrizEstado[-1][4]=0; 
		matrizEstado[-1][5]=0;
		matrizEstado[-1][6]=0;
		matrizEstado[-1][7]=0;
		matrizEstado[-1][8]=0;
		matrizEstado[-1][9]=0;
		matrizEstado[-1][10]=0;
		matrizEstado[-1][11]=0;
		matrizEstado[-1][12]=0;
		matrizEstado[-1][13]=0;
		matrizEstado[-1][14]=0;
		matrizEstado[-1][15]=0;
		matrizEstado[-1][16]=0;
		matrizEstado[-1][17]=0;
		matrizEstado[-1][18]=0;
		matrizEstado[-1][19]=0;	
		matrizEstado[-1][20]=0;*/
	}

	public void cargarMatrizAccionSemantica(){
		accionSemantica[0][0]=new AS2_Concatenar();
		accionSemantica[0][1]=new AS2_Concatenar();
		accionSemantica[0][2]=new AS2_Concatenar();
		accionSemantica[0][3]=new AS2_Concatenar(); 
		accionSemantica[0][4]=new Error4_EntradaInvalidaAvanza();
		accionSemantica[0][5]=new Error4_EntradaInvalidaAvanza();
		accionSemantica[0][6]=new Error4_EntradaInvalidaAvanza();
		accionSemantica[0][7]=new AS2_Concatenar(); 
		accionSemantica[0][8]=new AS2_Concatenar(); 
		accionSemantica[0][9]=new AS2_Concatenar(); 
		accionSemantica[0][10]=new AS2_Concatenar(); 
		accionSemantica[0][11]=new AS3_EntregarTokenYConsumirEntrada();
		accionSemantica[0][12]=new AS2_Concatenar(); 
		accionSemantica[0][13]=new AS3_EntregarTokenYConsumirEntrada();
		accionSemantica[0][14]=new AS2_Concatenar(); 
		accionSemantica[0][15]=new Error4_EntradaInvalidaAvanza();
		accionSemantica[0][16]=new AS5_ConsumirEntrada(); //= null??
		accionSemantica[0][17]=new AS3_EntregarTokenYConsumirEntrada(); 
		accionSemantica[0][18]=new AS5_ConsumirEntrada();
		accionSemantica[0][19]=new AS3_EntregarTokenYConsumirEntrada(); //y no pedir mas token
		accionSemantica[0][20]=new Error4_EntradaInvalidaAvanza();
		
		accionSemantica[1][0]=new AS2_Concatenar();
		accionSemantica[1][1]=new AS2_Concatenar();
		accionSemantica[1][2]=new AS2_Concatenar();
		accionSemantica[1][3]=new AS2_Concatenar();
		accionSemantica[1][4]=new AS2_Concatenar();
		accionSemantica[1][5]=new AS2_Concatenar();
		accionSemantica[1][6]=new AS2_Concatenar();
		accionSemantica[1][7]=new AS2_Concatenar();
		accionSemantica[1][8]=new AS1_IdToken();
		accionSemantica[1][9]=new AS1_IdToken();
		accionSemantica[1][10]=new AS1_IdToken();
		accionSemantica[1][11]=new AS1_IdToken();
		accionSemantica[1][12]=new AS1_IdToken();
		accionSemantica[1][13]=new AS1_IdToken();
		accionSemantica[1][14]=new AS1_IdToken();
		accionSemantica[1][15]=new AS1_IdToken();
		accionSemantica[1][16]=new AS1_IdToken();
		accionSemantica[1][17]=new AS1_IdToken(); 
		accionSemantica[1][18]=new AS1_IdToken();
		accionSemantica[1][19]=new AS1_IdToken();
		accionSemantica[1][20]=new AS1_IdToken();
		
		accionSemantica[2][0]=new AS2_Concatenar();
		accionSemantica[2][1]=new AS2_Concatenar();
		accionSemantica[2][2]=new Error1_EntradaInvalida();
		accionSemantica[2][3]=new Error1_EntradaInvalida();
		accionSemantica[2][4]=new Error1_EntradaInvalida();
		accionSemantica[2][5]=new Error1_EntradaInvalida();
		accionSemantica[2][6]=new Error1_EntradaInvalida();
		accionSemantica[2][7]=new Error1_EntradaInvalida();
		accionSemantica[2][8]=new Error1_EntradaInvalida();
		accionSemantica[2][9]=new Error1_EntradaInvalida();
		accionSemantica[2][10]=new Error1_EntradaInvalida();
		accionSemantica[2][11]=new Error1_EntradaInvalida();
		accionSemantica[2][12]=new Error1_EntradaInvalida();
		accionSemantica[2][13]=new Error1_EntradaInvalida();
		accionSemantica[2][14]=new Error1_EntradaInvalida(); 
		accionSemantica[2][15]=new Error1_EntradaInvalida();
		accionSemantica[2][16]=new Error1_EntradaInvalida();
		accionSemantica[2][17]=new Error1_EntradaInvalida(); 
		accionSemantica[2][18]=new Error1_EntradaInvalida();
		accionSemantica[2][19]=new Error1_EntradaInvalida();
		accionSemantica[2][20]=new Error1_EntradaInvalida();
		
		//hay que ver si hacer otro error porque sino se pierde mucho
		accionSemantica[3][0]=new Error1_EntradaInvalida();
		accionSemantica[3][1]=new Error1_EntradaInvalida();
		accionSemantica[3][2]=new Error1_EntradaInvalida();
		accionSemantica[3][3]=new Error1_EntradaInvalida();
		accionSemantica[3][4]=new AS2_Concatenar();
		accionSemantica[3][5]=new AS2_Concatenar();
		accionSemantica[3][6]=new AS2_Concatenar();
		accionSemantica[3][7]=new Error1_EntradaInvalida();
		accionSemantica[3][8]=new AS2_Concatenar();
		accionSemantica[3][9]=new Error1_EntradaInvalida();
		accionSemantica[3][10]=new Error1_EntradaInvalida();
		accionSemantica[3][11]=new Error1_EntradaInvalida();
		accionSemantica[3][12]=new Error1_EntradaInvalida();
		accionSemantica[3][13]=new Error1_EntradaInvalida();
		accionSemantica[3][14]=new Error1_EntradaInvalida(); 
		accionSemantica[3][15]=new Error1_EntradaInvalida();
		accionSemantica[3][16]=new Error1_EntradaInvalida();
		accionSemantica[3][17]=new Error1_EntradaInvalida(); 
		accionSemantica[3][18]=new Error1_EntradaInvalida();
		accionSemantica[3][19]=new Error1_EntradaInvalida();
		accionSemantica[3][20]=new Error1_EntradaInvalida();
		
		//hay que ver si hacer otro error porque sino se pierde mucho idem a 3
		accionSemantica[4][0]=new AS2_Concatenar();
		accionSemantica[4][1]=new Error1_EntradaInvalida();
		accionSemantica[4][2]=new Error1_EntradaInvalida();
		accionSemantica[4][3]=new Error1_EntradaInvalida();
		accionSemantica[4][4]=new Error1_EntradaInvalida();
		accionSemantica[4][5]=new Error1_EntradaInvalida();
		accionSemantica[4][6]=new Error1_EntradaInvalida();
		accionSemantica[4][7]=new Error1_EntradaInvalida();
		accionSemantica[4][8]=new Error1_EntradaInvalida();
		accionSemantica[4][9]=new Error1_EntradaInvalida();
		accionSemantica[4][10]=new Error1_EntradaInvalida();
		accionSemantica[4][11]=new Error1_EntradaInvalida();
		accionSemantica[4][12]=new Error1_EntradaInvalida();
		accionSemantica[4][13]=new Error1_EntradaInvalida();
		accionSemantica[4][14]=new Error1_EntradaInvalida(); 
		accionSemantica[4][15]=new Error1_EntradaInvalida();
		accionSemantica[4][16]=new Error1_EntradaInvalida();
		accionSemantica[4][17]=new Error1_EntradaInvalida(); 
		accionSemantica[4][18]=new Error1_EntradaInvalida();
		accionSemantica[4][19]=new Error1_EntradaInvalida();
		accionSemantica[4][20]=new Error1_EntradaInvalida();
	
		accionSemantica[5][0]=new AS4_RangoConstante();
		accionSemantica[5][1]=new AS4_RangoConstante();
		accionSemantica[5][2]=new AS4_RangoConstante();
		accionSemantica[5][3]=new AS4_RangoConstante();
		accionSemantica[5][4]=new AS2_Concatenar();
		accionSemantica[5][5]=new AS2_Concatenar();
		accionSemantica[5][6]=new AS2_Concatenar();
		accionSemantica[5][7]=new AS4_RangoConstante();
		accionSemantica[5][8]=new AS4_RangoConstante();
		accionSemantica[5][9]=new AS4_RangoConstante();
		accionSemantica[5][10]=new AS4_RangoConstante();
		accionSemantica[5][11]=new AS4_RangoConstante();
		accionSemantica[5][12]=new AS4_RangoConstante();
		accionSemantica[5][13]=new AS4_RangoConstante();
		accionSemantica[5][14]=new AS4_RangoConstante();
		accionSemantica[5][15]=new AS4_RangoConstante();
		accionSemantica[5][16]=new AS4_RangoConstante();
		accionSemantica[5][17]=new AS4_RangoConstante(); 
		accionSemantica[5][18]=new AS4_RangoConstante();
		accionSemantica[5][19]=new AS4_RangoConstante();
		accionSemantica[5][20]=new AS4_RangoConstante();
		
		//hay que ver si hacer otro error porque sino se pierde mucho idem a 3 y 4
		accionSemantica[6][0]=new Error1_EntradaInvalida();
		accionSemantica[6][1]=new Error1_EntradaInvalida();
		accionSemantica[6][2]=new Error1_EntradaInvalida();
		accionSemantica[6][3]=new Error1_EntradaInvalida();
		accionSemantica[6][4]=new AS2_Concatenar();
		accionSemantica[6][5]=new AS2_Concatenar();
		accionSemantica[6][6]=new AS2_Concatenar();
		accionSemantica[6][7]=new Error1_EntradaInvalida();
		accionSemantica[6][8]=new Error1_EntradaInvalida();
		accionSemantica[6][9]=new Error1_EntradaInvalida();
		accionSemantica[6][10]=new Error1_EntradaInvalida();
		accionSemantica[6][11]=new Error1_EntradaInvalida();
		accionSemantica[6][12]=new Error1_EntradaInvalida();
		accionSemantica[6][13]=new Error1_EntradaInvalida();
		accionSemantica[6][14]=new Error1_EntradaInvalida(); 
		accionSemantica[6][15]=new Error1_EntradaInvalida();
		accionSemantica[6][16]=new Error1_EntradaInvalida();
		accionSemantica[6][17]=new Error1_EntradaInvalida(); 
		accionSemantica[6][18]=new Error1_EntradaInvalida();
		accionSemantica[6][19]=new Error1_EntradaInvalida();
		accionSemantica[6][20]=new Error1_EntradaInvalida();
		
		accionSemantica[7][0]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][1]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][2]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][3]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][4]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][5]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][6]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][7]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][8]=new AS3_EntregarTokenYConsumirEntrada(); 
		accionSemantica[7][9]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][10]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][11]=new  AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][12]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][13]=new  AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][14]=new AS6_EntregarTokenAsciiBufferSinConsumir(); 
		accionSemantica[7][15]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][16]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][17]=new  AS6_EntregarTokenAsciiBufferSinConsumir(); 
		accionSemantica[7][18]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][19]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[7][20]=new AS6_EntregarTokenAsciiBufferSinConsumir();
	
		accionSemantica[8][0]=new Error2_ColocaIgual();
		accionSemantica[8][1]=new Error2_ColocaIgual();
		accionSemantica[8][2]=new Error2_ColocaIgual();
		accionSemantica[8][3]=new Error2_ColocaIgual();
		accionSemantica[8][4]=new Error2_ColocaIgual();
		accionSemantica[8][5]=new Error2_ColocaIgual();
		accionSemantica[8][6]=new Error2_ColocaIgual();
		accionSemantica[8][7]=new Error2_ColocaIgual();
		accionSemantica[8][8]=new Error2_ColocaIgual();
		accionSemantica[8][9]=new Error2_ColocaIgual();
		accionSemantica[8][10]=new Error2_ColocaIgual();
		accionSemantica[8][11]=new AS3_EntregarTokenYConsumirEntrada(); 
		accionSemantica[8][12]=new Error2_ColocaIgual();
		accionSemantica[8][13]=new Error2_ColocaIgual();
		accionSemantica[8][14]=new Error2_ColocaIgual(); 
		accionSemantica[8][15]=new Error2_ColocaIgual();
		accionSemantica[8][16]=new Error2_ColocaIgual();
		accionSemantica[8][17]=new Error2_ColocaIgual(); 
		accionSemantica[8][18]=new Error2_ColocaIgual();
		accionSemantica[8][19]=new Error2_ColocaIgual();
		accionSemantica[8][20]=new Error2_ColocaIgual();
		
		accionSemantica[9][0]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][1]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][2]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][3]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][4]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][5]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][6]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][7]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][8]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][9]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][10]= new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][11]=new AS3_EntregarTokenYConsumirEntrada();
		accionSemantica[9][12]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][13]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][14]=new AS6_EntregarTokenAsciiBufferSinConsumir(); 
		accionSemantica[9][15]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][16]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][17]=new AS6_EntregarTokenAsciiBufferSinConsumir(); 
		accionSemantica[9][18]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][19]=new AS6_EntregarTokenAsciiBufferSinConsumir();
		accionSemantica[9][20]=new AS6_EntregarTokenAsciiBufferSinConsumir();
			
		accionSemantica[10][0]=new Error1_EntradaInvalida();
		accionSemantica[10][1]=new Error1_EntradaInvalida();
		accionSemantica[10][2]=new Error1_EntradaInvalida();
		accionSemantica[10][3]=new Error1_EntradaInvalida();
		accionSemantica[10][4]=new Error1_EntradaInvalida();
		accionSemantica[10][5]=new Error1_EntradaInvalida();
		accionSemantica[10][6]=new Error1_EntradaInvalida();
		accionSemantica[10][7]=new Error1_EntradaInvalida();
		accionSemantica[10][8]=new Error1_EntradaInvalida();
		accionSemantica[10][9]=new Error1_EntradaInvalida();
		accionSemantica[10][10]=new Error1_EntradaInvalida();
		accionSemantica[10][11]=new Error1_EntradaInvalida();
		accionSemantica[10][12]=new Error1_EntradaInvalida();
		accionSemantica[10][13]=new Error1_EntradaInvalida();
		accionSemantica[10][14]=new AS2_Concatenar(); 
		accionSemantica[10][15]=new Error1_EntradaInvalida();
		accionSemantica[10][16]=new Error1_EntradaInvalida();
		accionSemantica[10][17]=new Error1_EntradaInvalida(); 
		accionSemantica[10][18]=new Error1_EntradaInvalida();
		accionSemantica[10][19]=new Error1_EntradaInvalida();
		accionSemantica[10][20]=new Error1_EntradaInvalida();
		
		accionSemantica[11][0]=new AS5_ConsumirEntrada();
		accionSemantica[11][1]=new AS5_ConsumirEntrada();
		accionSemantica[11][2]=new AS5_ConsumirEntrada();
		accionSemantica[11][3]=new AS5_ConsumirEntrada();
		accionSemantica[11][4]=new AS5_ConsumirEntrada();
		accionSemantica[11][5]=new AS5_ConsumirEntrada();
		accionSemantica[11][6]=new AS5_ConsumirEntrada();
		accionSemantica[11][7]=new AS5_ConsumirEntrada();
		accionSemantica[11][8]=new AS5_ConsumirEntrada();
		accionSemantica[11][9]=new AS5_ConsumirEntrada();
		accionSemantica[11][10]=new AS5_ConsumirEntrada();
		accionSemantica[11][11]=new AS5_ConsumirEntrada();
		accionSemantica[11][12]=new AS5_ConsumirEntrada();
		accionSemantica[11][13]=new AS5_ConsumirEntrada();
		accionSemantica[11][14]=new AS5_ConsumirEntrada(); 
		accionSemantica[11][15]=new AS2_Concatenar();
		accionSemantica[11][16]=new AS7_VaciarBufferTemporal(); 
		accionSemantica[11][17]=new AS5_ConsumirEntrada(); 
		accionSemantica[11][18]=new AS5_ConsumirEntrada();
		accionSemantica[11][19]=new AS7_VaciarBufferTemporal();
		accionSemantica[11][20]=new AS5_ConsumirEntrada();
		
		accionSemantica[12][0]=new AS5_ConsumirEntrada();
		accionSemantica[12][1]=new AS5_ConsumirEntrada();
		accionSemantica[12][2]=new AS5_ConsumirEntrada();
		accionSemantica[12][3]=new AS5_ConsumirEntrada();
		accionSemantica[12][4]=new AS2_Concatenar(); 
		accionSemantica[12][5]=new AS2_Concatenar();
		accionSemantica[12][6]=new AS5_ConsumirEntrada();
		accionSemantica[12][7]=new AS5_ConsumirEntrada();
		accionSemantica[12][8]=new AS5_ConsumirEntrada();
		accionSemantica[12][9]=new AS5_ConsumirEntrada();
		accionSemantica[12][10]=new AS5_ConsumirEntrada();
		accionSemantica[12][11]=new AS5_ConsumirEntrada();
		accionSemantica[12][12]=new AS5_ConsumirEntrada();
		accionSemantica[12][13]=new AS5_ConsumirEntrada();
		accionSemantica[12][14]=new AS5_ConsumirEntrada(); 
		accionSemantica[12][15]=new AS5_ConsumirEntrada();
		accionSemantica[12][16]=new AS7_VaciarBufferTemporal();
		accionSemantica[12][17]=new AS5_ConsumirEntrada(); 
		accionSemantica[12][18]=new AS5_ConsumirEntrada();
		accionSemantica[12][19]=new AS7_VaciarBufferTemporal();
		accionSemantica[12][20]=new AS5_ConsumirEntrada();
		
		accionSemantica[13][0]=new AS5_ConsumirEntrada();
		accionSemantica[13][1]=new AS5_ConsumirEntrada();
		accionSemantica[13][2]=new AS5_ConsumirEntrada();
		accionSemantica[13][3]=new AS5_ConsumirEntrada();
		accionSemantica[13][4]=new AS5_ConsumirEntrada();
		accionSemantica[13][5]=new AS5_ConsumirEntrada();
		accionSemantica[13][6]=new AS5_ConsumirEntrada();
		accionSemantica[13][7]=new AS5_ConsumirEntrada();
		accionSemantica[13][8]=new AS5_ConsumirEntrada();
		accionSemantica[13][9]=new AS5_ConsumirEntrada();
		accionSemantica[13][10]=new AS5_ConsumirEntrada();
		accionSemantica[13][11]=new AS5_ConsumirEntrada();
		accionSemantica[13][12]=new AS5_ConsumirEntrada();
		accionSemantica[13][13]=new AS5_ConsumirEntrada();
		accionSemantica[13][14]=new AS5_ConsumirEntrada(); 
		accionSemantica[13][15]=new AS5_ConsumirEntrada();
		accionSemantica[13][16]=new AS3_EntregarTokenYConsumirEntrada();
		accionSemantica[13][17]=new AS5_ConsumirEntrada(); 
		accionSemantica[13][18]=new AS5_ConsumirEntrada();
		accionSemantica[13][19]=new AS8_EntregarTokenSinConsumir();
		accionSemantica[13][20]=new AS5_ConsumirEntrada();
		
		accionSemantica[14][0]=new AS5_ConsumirEntrada();
		accionSemantica[14][1]=new AS5_ConsumirEntrada();
		accionSemantica[14][2]=new AS5_ConsumirEntrada();
		accionSemantica[14][3]=new AS5_ConsumirEntrada();
		accionSemantica[14][4]=new AS5_ConsumirEntrada();
		accionSemantica[14][5]=new AS5_ConsumirEntrada();
		accionSemantica[14][6]=new AS5_ConsumirEntrada();
		accionSemantica[14][7]=new AS5_ConsumirEntrada();
		accionSemantica[14][8]=new AS5_ConsumirEntrada();
		accionSemantica[14][9]=new AS5_ConsumirEntrada();
		accionSemantica[14][10]=new AS5_ConsumirEntrada();
		accionSemantica[14][11]=new AS5_ConsumirEntrada();
		accionSemantica[14][12]=new AS5_ConsumirEntrada();
		accionSemantica[14][13]=new AS5_ConsumirEntrada();
		accionSemantica[14][14]=new AS5_ConsumirEntrada(); 
		accionSemantica[14][15]=new AS5_ConsumirEntrada();
		accionSemantica[14][16]=new AS7_VaciarBufferTemporal();
		accionSemantica[14][17]=new AS5_ConsumirEntrada(); 
		accionSemantica[14][18]=new AS5_ConsumirEntrada();
		accionSemantica[14][19]=new AS7_VaciarBufferTemporal();
		accionSemantica[14][20]=new AS5_ConsumirEntrada();
	
		accionSemantica[15][0]=new AS2_Concatenar();
		accionSemantica[15][1]=new AS2_Concatenar();
		accionSemantica[15][2]=new AS2_Concatenar();
		accionSemantica[15][3]=new AS2_Concatenar();
		accionSemantica[15][4]=new AS2_Concatenar();
		accionSemantica[15][5]=new AS2_Concatenar();
		accionSemantica[15][6]=new AS2_Concatenar();
		accionSemantica[15][7]=new AS2_Concatenar();
		accionSemantica[15][8]=new AS2_Concatenar();
		accionSemantica[15][9]=new AS2_Concatenar();
		accionSemantica[15][10]=new AS2_Concatenar();
		accionSemantica[15][11]=new AS2_Concatenar();
		accionSemantica[15][12]=new AS9_EntregarTokenModificadoYConsume();
		accionSemantica[15][13]=new AS2_Concatenar();
		accionSemantica[15][14]=new AS2_Concatenar(); 
		accionSemantica[15][15]=new AS2_Concatenar();
		accionSemantica[15][16]=new Error4_EntradaInvalidaAvanza(); //podria ser error4_agregaMas();
		accionSemantica[15][17]=new AS2_Concatenar();
		accionSemantica[15][18]=new AS2_Concatenar();
		accionSemantica[15][19]=new Error1_EntradaInvalida();
		accionSemantica[15][20]=new AS2_Concatenar();
		
		accionSemantica[16][0]=new AS2_Concatenar();
		accionSemantica[16][1]=new AS2_Concatenar();
		accionSemantica[16][2]=new AS2_Concatenar();
		accionSemantica[16][3]=new AS2_Concatenar();
		accionSemantica[16][4]=new AS2_Concatenar();
		accionSemantica[16][5]=new AS2_Concatenar();
		accionSemantica[16][6]=new AS2_Concatenar();
		accionSemantica[16][7]=new AS2_Concatenar();
		accionSemantica[16][8]=new AS2_Concatenar();
		accionSemantica[16][9]=new AS2_Concatenar();
		accionSemantica[16][10]=new AS2_Concatenar();
		accionSemantica[16][11]=new AS2_Concatenar();
		accionSemantica[16][12]=new AS9_EntregarTokenModificadoYConsume();
		accionSemantica[16][13]=new AS2_Concatenar();
		accionSemantica[16][14]=new AS2_Concatenar();
		accionSemantica[16][15]=new AS2_Concatenar();
		accionSemantica[16][16]=new AS2_Concatenar();
		accionSemantica[16][17]=new AS2_Concatenar(); 
		accionSemantica[16][18]=new AS2_Concatenar();
		accionSemantica[16][19]=new Error1_EntradaInvalida();
		accionSemantica[16][20]=new AS2_Concatenar();
		
		accionSemantica[17][0]=null;
		accionSemantica[17][1]=null;
		accionSemantica[17][2]=null;
		accionSemantica[17][3]=null;
		accionSemantica[17][4]=null;
		accionSemantica[17][5]=null;
		accionSemantica[17][6]=null;
		accionSemantica[17][7]=null;
		accionSemantica[17][8]=null;
		accionSemantica[17][9]=null;
		accionSemantica[17][10]=null;
		accionSemantica[17][11]=null;
		accionSemantica[17][12]=null;
		accionSemantica[17][13]=null;
		accionSemantica[17][14]=null; 
		accionSemantica[17][15]=null;
		accionSemantica[17][16]=null;
		accionSemantica[17][17]=null;		//new AS4_RangoConstante(); 
		accionSemantica[17][18]=null;
		accionSemantica[17][19]=null;
		accionSemantica[17][20]=null;
	}
	
	 int ObtenerColumnaMatriz(char entrada){
		int ascii=(int)entrada;
		int a = (int)'a';
		int z = (int)'z';	
		if(ascii==105) //letra i
			return 0;
		else
		if(ascii==117) //letra u
			return 1;
		else			
		if(ascii>=97 && ascii<=122) //letra minuscula
			return 2;
		else
		if(ascii>=65 && ascii<=90) //letra mayuscula
			return 3;
		else
		if(ascii==48) //digito 0
			return 4;
		else
		if(ascii==49) //digito 1
			return 5;
		else
		if(ascii>=48 && ascii<=57) //digito
			return 6;
		else	
			
		if(ascii==10) //nueva linea
			return 16;
		else			
		if(ascii==9) //tabulador horizontal
			return 18;
		else
		if(ascii==32) //espacio
			return 18;	
		else
			switch (entrada){
			case '_':   return 7;
			case '-':	return 8; 
			
			case ':':	return 9;
			case '!':   return 9;
			
			case '>':   return 10;
			case '<':   return 10;
			
			case '=':   return 11;
			
			case '"':   return 12;
			case '+':   return 13;			
			case '&':   return 14;
			case '@':   return 15;
			
			case '*':	return 17;
			case '/':	return 17;			
			case '(':   return 17;
			case ')':   return 17;
			case '{':   return 17;
			case '}':   return 17;
			case ',':   return 17;
			case ';':   return 17;
			case '[':   return 17;
			case ']':   return 17;	
			
			case '$':   return 19;	
			
			default: return 20; //ver el default que numero le ponemos y falta el EOF
			}		
	}
	
	 Token yylex() throws IOException {
		 Token token= new Token("", "", 0);
		 

		estado = 0;

		buffer_temporal = "";
		while(estado < 17){				
			
			if(posEntrada< prueba.length())				
				entrada=prueba.charAt(posEntrada);
			else
				entrada=(char)36;//$ 
			int col=ObtenerColumnaMatriz(entrada);
			if(accionSemantica[estado][col]!=null)
				token.setToken(accionSemantica[estado][col].ejecutar(this));;
			if((int)entrada==10) //seria el /n en ascii
				nrolinea++;
			if ((estado != 0) && (estado != 16) &&(((int)entrada)==10))
				nrolinea--;

		col=ObtenerColumnaMatriz(entrada); 		
		estado=matrizEstado[estado][col];
		if (estado == -1)
			estado = 0;
		}
		Integer auxSalida = token.getToken();
		if (token.getToken() < 257)
			setBuffer_temporal((char)token.getToken() + "");
		if (token.getToken() == Compilador.FIN)
			Main.listaTokens.add(auxSalida.toString() + " - " + "$");
		else{
			if (token.getToken() == Compilador.MENOSMENOS)
				Main.listaTokens.add(auxSalida.toString() + " - " + "--");
			if (token.getToken() == Compilador.ASIGNACION)
				Main.listaTokens.add(auxSalida.toString() + " - " + ":=");
			if (token.getToken() == Compilador.DISTINTO)
				Main.listaTokens.add(auxSalida.toString() + " - " + "!=");
			if (token.getToken() ==  Compilador.MENORIGUAL)
				Main.listaTokens.add(auxSalida.toString() + " - " + "<=");
			if (token.getToken() == Compilador.MAYORIGUAL)
				Main.listaTokens.add(auxSalida.toString() + " - " + ">=");
			if ((token.getToken() < 269) || (token.getToken() > 273))
				Main.listaTokens.add(auxSalida.toString() + " - " + getBuffer_temporal());
		}
		token.setLexema(getBuffer_temporal());
		token.setUso("");
	return token;
}
	
	
	public int getNrolinea() {
		return nrolinea;
	}

	public void setNrolinea(int nrolinea) {
		this.nrolinea = nrolinea;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getBuffer_temporal() {
		return buffer_temporal;
	}

	public void setBuffer_temporal(String buffer_temporal) {
		this.buffer_temporal = buffer_temporal;
	}

	public char getEntrada() {
		return entrada;
	}

	public void setEntrada(char entrada) {
		this.entrada = entrada;
	}

	public int getPosEntrada() {
		return posEntrada;
	}

	public void setPosEntrada(int posEntrada) {
		this.posEntrada = posEntrada;
	}
	
	
	
	
}




package Compilador;
public class Tabla_Simbolos {
	
	private int token;
	private String informacion;
	private String lexema;
	private String tipo;
	private String uso;
	public String [][] elementosMatriz;
	private int filas;
	private int columnas;
	private boolean comienza11; 
	private int direccion_base;
	//private String tipo; seria la informacion
	
	public void setMatriz(String[][] matriz){
		this.elementosMatriz = matriz;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public String getInformacion() {
		return informacion;
	}
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	public String getLexema() {
		return lexema;
	}
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public Tabla_Simbolos(){
		this.token = 0;
		this.informacion = null;
		this.lexema = "";
		this.elementosMatriz = null;
		this.comienza11 = false;
		this.uso = "";
		this.tipo = "";
	}
	public void Tabla_Sinbolos(int token, String informacion, String lexema){
		this.token = token;
		this.informacion = informacion;
		this.lexema = lexema;
		this.elementosMatriz = null;
		this.comienza11 = false;
		this.uso = "";
		this.tipo = "";

	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUso() {
		return this.uso;
	}
	public void setUso(String uso) {
		this.uso = uso;
	}
	public boolean getComienza11() {
		return this.comienza11;
	}
	public void setComienza11(boolean comienza11) {
		this.comienza11 = comienza11;
	}
	
	public String getElementoMatrizIJ(int i, int j){
		if (elementosMatriz != null){
		//	if (comienza11)
			//	return this.elementosMatriz[i-1][j-1];
			//else
				return this.elementosMatriz[i][j];
		}
		else
			return "0";
	}
	public int getFilas() {
		return this.filas;
	}
	public void setFilas(int filas) {
		this.filas = filas;
	}
	public int getColumnas() {
		return this.columnas;
	}
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}
	public int getDireccion_base() {
		return this.direccion_base;
	}
	public void setDireccion_base(int direccion_base) {
		this.direccion_base = direccion_base;
	}
	public String[][] getMatriz() {
		// TODO Auto-generated method stub
		return this.elementosMatriz;
	}
	
}

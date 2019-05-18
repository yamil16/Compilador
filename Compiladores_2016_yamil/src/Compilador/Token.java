package Compilador;

public class Token extends Generador {
	private int token;
	private String lexema;
	private String uso;
	


	public Token(String uso, String Lexema, int token) {
		// TODO Auto-generated constructor stub
		this.token=token;
		this.lexema=Lexema;
		this.uso = uso;
	}
	

	

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}
	public void setLexema(String lexema ) {
		this.lexema = lexema;
	}
	
	public String getClaveTablaSimboloToken(){
		return uso+lexema+token;
	}


	@Override
	public boolean isTerceto() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getLexemaGeneradorNumero() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getUso(){
		return this.uso;
	}
	public void setUso(String string) {
		this.uso = string;
		// TODO Auto-generated method stub
		
	}




	@Override
	public String getLexema() {
		// TODO Auto-generated method stub
		return this.lexema;
	}





}

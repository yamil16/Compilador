package Compilador;




public class Terceto extends Generador{
private String operador;
private Generador operando1;
private Generador operando2;
private int numTerceto;
private int registro;
private String tipo;

public Terceto (String o, Generador g1, Generador g2,int numTerceto){
	this.setOperador(o);
	this.setOperando1(g1);
	this.setOperando2(g2);
	this.numTerceto=numTerceto;
	setRegistro(-1);
}

public Generador getOperando1() {
	return operando1;
}

public void setOperando1(Generador operando1) {
	this.operando1 = operando1;
}

public Generador getOperando2() {
	return operando2;
}

public void setOperando2(Generador operando2) {
	this.operando2 = operando2;
}

public int getNumTerceto() {
	return numTerceto;
}

public void setNumTerceto(int numTerceto) {
	this.numTerceto = numTerceto;
}

public String getOperador() {
	return operador;
}

public void setOperador(String operador) {
	this.operador = operador;
}

@Override
public String getLexema() {
	if((operando1==null)&&(operando2==null)){
		System.out.println("es sallto");
		return ("("+ "" +")"); 
	}
	else{
	if(operando2==null){
		if(operando1.isTerceto()==false)
			return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexema()+", "+"-)");
		else
			return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexemaGeneradorNumero()+", "+"-)");
			
	}	
	else
		if(operando1.isTerceto()==false){
			if(operando2.isTerceto()==false)
				return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexema()+", "+operando2.getLexema()+ ")");
			else
				return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexema()+", "+operando2.getLexemaGeneradorNumero()+ ")");
		}else{
			if(operando2.isTerceto()==false)
				return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexemaGeneradorNumero()+", "+operando2.getLexema()+ ")");
			else
				return (getNumTerceto()+": ("+getOperador()+", "+operando1.getLexemaGeneradorNumero()+", "+operando2.getLexemaGeneradorNumero()+ ")");
	}
	}
}

public String getLexemaGeneradorNumero() {
	return "["+getNumTerceto()+"]";
}

@Override
public boolean isTerceto() {
	// TODO Auto-generated method stub
	return true;
}

public int getRegistro() {
	return registro;
}

public void setRegistro(int registro) {
	this.registro = registro;
}

public String getTipo() {
	return tipo;
}

public void setTipo(String tipo) {
	this.tipo = tipo;
	//System.out.println("le cambie el tipo, ahora es: " + this.tipo);
}



}

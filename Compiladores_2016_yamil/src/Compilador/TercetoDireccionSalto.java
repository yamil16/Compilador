package Compilador;

public class TercetoDireccionSalto extends Terceto {

	private int direccionSalto;
	public TercetoDireccionSalto(String o,int direccionSalto) {
		super(o, null, null, -1);
		// TODO Auto-generated constructor stub
		this.direccionSalto=direccionSalto;
	}
	public String getLexema() {
		return ("("+ getDireccionSalto() +")"); 
	}
	public int getDireccionSalto() {
		return direccionSalto;
	}
	public void setDireccionSalto(int direccionSalto) {
		this.direccionSalto = direccionSalto;
	}
	@Override
	public boolean isTerceto() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public TercetoDireccionSalto(String string, int direccion_base, int size) {
		// TODO Auto-generated constructor stub
		super(string, null, null,size);
		this.direccionSalto=direccion_base;
	}
	

}

package Compilador;

public class ParserVal
{

public int ival;
public double dval;
public String sval;
public String Tipo;
public Object obj;

public String getTipo(){
	return Tipo;
}

public void setTipo (String tipo){
	Tipo=tipo;}

public ParserVal()
{
}
public ParserVal(int val)
{
  ival=val;
}
public ParserVal(double val)
{
  dval=val;
}
public ParserVal(String val)
{
  sval=val;
}
public ParserVal(Object val)
{
  obj=val;
}
}
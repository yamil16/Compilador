package Compilador;
//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 4 "gramaticaReentrega.y"
	import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Enumeration;
	import java.io.IOException;
	import java.io.FileNotFoundException;
//#line 24 "Parser.java"




public class Parserprevio
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
//method: debug
//###############################################################
void debug(String msg)
{
if (yydebug)
  System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
//methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
   int oldsize = statestk.length;
   int newsize = oldsize * 2;
   int[] newstack = new int[newsize];
   System.arraycopy(statestk,0,newstack,0,oldsize);
   statestk = newstack;
   statestk[stateptr]=state;
}
}
final int state_pop()
{
return statestk[stateptr--];
}
final void state_drop(int cnt)
{
stateptr -= cnt; 
}
final int state_peek(int relative)
{
return statestk[stateptr-relative];
}
//###############################################################
//method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
stateptr = -1;
val_init();
return true;
}
//###############################################################
//method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
for (i=0;i<count;i++)
  System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
//methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
valstk=new ParserVal[YYSTACKSIZE];
yyval=new ParserVal();
yylval=new ParserVal();
valptr=-1;
}
void val_push(ParserVal val)
{
if (valptr>=YYSTACKSIZE)
  return;
valstk[++valptr]=val;
}
ParserVal val_pop()
{
if (valptr<0)
  return new ParserVal();
return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
ptr=valptr-cnt;
if (ptr<0)
  return;
valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
ptr=valptr-relative;
if (ptr<0)
  return new ParserVal();
return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
ParserVal dup = new ParserVal();
dup.ival = val.ival;
dup.dval = val.dval;
dup.sval = val.sval;
dup.obj = val.obj;
return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short THEN=258;
public final static short IF=259;
public final static short ELSE=260;
public final static short PRINT=261;
public final static short ENDIF=262;
public final static short INTEGER=263;
public final static short UINTEGER=264;
public final static short MATRIX=265;
public final static short FOR=266;
public final static short CONSTANTE_POSITIVA=267;
public final static short CONSTANTE_ENTERA=268;
public final static short MENOSMENOS=269;
public final static short ASIGNACION=270;
public final static short DISTINTO=271;
public final static short MENORIGUAL=272;
public final static short MAYORIGUAL=273;
public final static short ANOTACION_CERO=274;
public final static short ANOTACION_UNO=275;
public final static short CADENA_MULTILINEA=276;
public final static short FIN=277;
public final static short ALLOW=278;
public final static short TO=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
  0,    1,    1,    1,    1,    1,    1,    1,    1,    1,
  1,    2,    2,    2,    2,    2,    2,    4,    4,    4,
  4,    8,    8,    8,    5,    5,    5,    5,    5,    5,
  5,    9,    9,    9,    9,    9,    9,    9,   11,   11,
 11,   11,   10,   10,   10,   10,   13,   13,   13,   14,
 14,    6,    6,    6,   15,   15,    7,    7,   16,    3,
  3,   17,   17,   17,   17,   18,   18,   18,   18,   18,
 18,   18,   22,   23,   23,   25,   25,   27,   27,   26,
 26,   24,   24,   24,   19,   19,   19,   19,   19,   29,
 31,   33,   33,   32,   32,   20,   20,   20,   20,   20,
 30,   21,   21,   35,   35,   36,   36,   12,   12,   12,
 37,   37,   37,   38,   38,   34,   34,   34,   34,   28,
 28,   28,   28,   28,   28,
};
final static short yylen[] = {                            2,
  1,    5,    2,    4,    5,    4,    4,    3,    3,    3,
  2,    2,    2,    2,    1,    1,    1,    3,    2,    2,
  2,    3,    1,    2,    6,    6,    5,    5,    5,    4,
  3,    7,    7,    7,    7,    5,    7,    6,    7,    6,
  8,    6,    3,    2,    4,    2,    3,    1,    2,    3,
  1,    5,    3,    4,    1,    1,    1,    1,    2,    2,
  1,    1,    1,    1,    1,    3,    3,    4,    4,    5,
  6,    6,    3,    5,    3,    1,    3,    1,    3,    1,
  3,    3,    1,    3,    8,    6,    8,    4,    6,    1,
  1,    1,    3,    2,    1,    5,    3,    5,    5,    6,
  4,    4,    4,    1,    1,    1,    1,    3,    3,    1,
  3,    3,    1,    2,    1,    1,    1,    1,    1,    1,
  1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
  0,    0,    0,    1,   23,   57,   58,    0,    0,    0,
 15,   16,   17,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,  107,   61,   62,   63,   64,   65,    0,
  0,    0,    0,    0,    0,    0,    0,   12,   13,   14,
  0,    0,   19,    0,   24,   21,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,  117,  118,  119,    0,
  0,    0,  113,    0,    0,   10,   60,    0,    0,    0,
  0,   31,   53,    0,    0,    0,    0,   18,   22,    0,
 83,    0,    0,   67,    0,    0,   66,    0,    0,   97,
  0,    0,    0,    0,    4,    0,    0,    0,  114,    0,
  0,    0,    6,    0,  105,    0,    0,    0,    0,    0,
  0,    0,    0,    5,    0,   56,   55,    0,    0,    0,
  0,    0,   51,   54,    0,  125,  121,  123,  120,  122,
124,    0,   73,   69,    0,    0,   68,    0,    0,    0,
  0,    0,   95,    0,    0,    0,   88,    0,    0,    0,
111,  112,    2,   91,    0,    0,  103,  102,    0,    0,
  0,   52,   46,    0,   28,   29,    0,   44,    0,    0,
  0,    0,    0,   84,    0,    0,   70,    0,    0,   80,
  0,   75,   98,   99,    0,   96,    0,   94,    0,    0,
  0,    0,    0,   36,    0,    0,    0,   43,   25,   26,
  0,   50,   40,    0,   71,   72,    0,    0,  100,    0,
 86,   89,   42,    0,  101,    0,    0,    0,    0,   38,
 45,    0,   39,   81,   74,    0,    0,   33,   35,   34,
 37,   32,   41,   87,    0,   92,   85,    0,   93,
};
final static short yydgoto[] = {                          3,
  4,   10,  135,   11,   12,   13,   14,   15,   34,  120,
 24,   82,  121,  122,  123,  136,   25,   26,   27,   28,
 29,   50,   87,   83,   88,  181,   89,  132,   30,  106,
155,  144,  237,   61,  107,   31,   62,   63,
};
final static short yysindex[] = {                       -97,
-114,   78,    0,    0,    0,    0,    0,  -41, -124,  -82,
  0,    0,    0,  -57,  -15,    8,    7,  -33, -194,  -46,
 83,   98,  163,    0,    0,    0,    0,    0,    0,   69,
-196,   35, -152,  103,  117, -142,  -46,    0,    0,    0,
-41,  -83,    0,   -7,    0,    0,  -77,   83,  -64,  138,
129, -205,   -2,    8,  200,    8,    0,    0,    0,   15,
-91,   68,    0,  -46,  201,    0,    0,  -32,   61, -102,
106,    0,    0,  -72,  211,  -16,  150,    0,    0,   37,
  0,  -26,  183,    0,  157,  -23,    0,  -22,   -9,    0,
214,  -38,  111,  243,    0,  168,   83,   83,    0,   83,
 83,  233,    0,    8,    0,  -64,   -1,  204,   10,  180,
190,  209,  236,    0,  179,    0,    0, -100,  -90,  250,
-11,  267,    0,    0,  -49,    0,    0,    0,    0,    0,
  0,  114,    0,    0,  -46,  -85,    0,  254,  264,  268,
271,  -50,    0,  297,   77,  -46,    0,   83,   68,   68,
  0,    0,    0,    0,  288,   83,    0,    0,  258,  -42,
 84,    0,    0,   45,    0,    0,  -88,    0, -100,  267,
-100,  260,   83,    0,   18,    0,    0,  -39,  -46,    0,
 92,    0,    0,    0,  306,    0,  257,    0,  244,   46,
111,   38,  -73,    0,  -70,  279,  248,    0,    0,    0,
267,    0,    0,  -17,    0,    0,  249,  316,    0,  -46,
  0,    0,    0,  342,    0,  291,  292,  293,  -28,    0,
  0,  294,    0,    0,    0,  263,  269,    0,    0,    0,
  0,    0,    0,    0,  -46,    0,    0,  265,    0,
};
final static short yyrindex[] = {                         0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,  -44,    0,    0,  349,    0,
  0,  391,  393,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
  0,    0,    0,  128,  396,  -37,    0,    0,    0,    0,
-29,   25,    0,    0,  401,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0, -115,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,  408,    0,  130,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,   11,    0,
  0,   47,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,   34,   60,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,   21,    0,    0,   52,
  0,    0,    0,    0,   13,  146,    0, -105,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  0,  407,  126,  113,  119,  132,    0,  397,  369,    0,
385,  400,  299,  -31,  255,  -79,   23,    0,    0,    0,
  0,    0,    0,  309,    0,    0,    0,    0,    0,    0,
  0,  228,    0,  -55,    0,    0,  133,  193,
};
final static int YYTABLESIZE=576;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         23,
 20,   43,  142,  116,  116,  116,   52,  116,  186,  116,
 30,  115,  115,  115,   23,  115,   97,  115,   98,  206,
 27,  116,  116,  116,  116,   97,   21,   98,   47,  115,
115,  115,  115,  129,  131,  130,   47,  145,   94,  178,
 37,  173,  119,   46,   21,   67,   49,  169,  195,   33,
 91,   78,   97,   82,   98,  116,   93,   97,   21,   98,
 97,   53,   98,  115,  232,  110,  189,  110,  158,  110,
 92,   82,   86,   69,  108,  223,  108,   67,  108,   97,
 97,   98,   98,  110,  110,  110,  110,   67,   97,  170,
 98,   20,  108,  108,  108,  108,  215,   67,   48,  207,
109,   30,  109,  169,  109,   48,  118,   96,   68,  100,
 49,   27,   47,  168,  101,   71,  147,  110,  109,  109,
109,  109,   38,   20,   67,   70,  108,   23,   39,  125,
226,   35,  170,   30,   38,  145,   74,  201,  213,   36,
 39,   40,    5,   27,   76,   55,   78,   65,    6,    7,
  8,   21,  109,   40,   77,  238,   79,  176,    1,    2,
180,   72,   75,    9,  110,  111,  116,  117,   21,  198,
177,   48,   77,   21,    5,   73,   49,   99,   47,   79,
  6,    7,    8,  165,  166,  199,  200,   90,   21,  102,
113,   81,   56,  216,  217,    9,  218,  219,  112,    5,
 20,   21,   57,   58,   21,  185,  172,   41,  124,  211,
 54,   23,   17,  194,   18,   32,  205,  141,  116,   19,
 64,   42,   51,  133,  104,  106,  115,  231,   21,  149,
150,  116,  137,  116,  116,  116,   60,  138,  222,  115,
 45,  115,  115,  115,  126,  127,  128,   21,   45,  236,
116,  117,  139,   21,  140,  116,  117,   20,  148,   20,
 85,   20,  157,   20,   20,   20,   20,   30,  156,   30,
 59,   30,  159,   30,   30,   30,   30,   27,   20,   27,
110,   27,  160,   27,   27,   27,   27,   66,   30,  108,
 21,   21,  151,  152,  162,  110,  110,  110,   27,  161,
197,   21,   48,  163,  108,  108,  108,   49,  167,   47,
171,  116,  117,   48,   48,  109,  108,   56,   49,   49,
 47,   47,  182,   21,   95,  103,  183,   57,   58,  184,
109,  109,  109,   21,   16,  114,   17,  187,   18,   56,
  6,    7,    8,   19,   21,  188,  191,   21,  193,   57,
 58,  196,  203,  208,   16,    9,   17,  153,   18,   21,
  6,    7,    8,   19,  209,  146,  143,   56,  212,  174,
 56,  220,  221,  224,  225,    9,  179,   57,   58,  210,
 57,   58,  227,  228,  229,  230,  233,  234,   90,  239,
  3,  235,   11,   84,   54,    9,   17,  106,   18,  104,
  8,   59,   60,   19,   60,   59,   60,    7,   22,   76,
 44,   60,  134,   54,  154,   17,  164,   18,  214,   54,
 60,   17,   19,   18,    0,  202,    0,    0,   19,    0,
  0,    0,   59,   59,    0,    0,    0,    0,    0,    0,
  0,    0,    0,    0,    0,    0,    0,   80,    0,    0,
  0,    0,  105,   59,    0,    0,   54,   54,   17,   17,
 18,   18,    0,    0,    0,   19,   19,   54,  109,   17,
  0,   18,    0,    0,    0,    0,   19,   59,    0,    0,
  0,   59,   59,    0,   59,   59,    0,    0,    0,   54,
 59,   17,    0,   18,    0,    0,    0,    0,   19,   54,
  0,   17,    0,   18,    0,    0,    0,    0,   19,    0,
 54,    0,   17,   54,   18,   17,   59,   18,    0,   19,
  0,    0,   19,    0,    0,   54,    0,   17,    0,   18,
  0,  175,   59,    0,   19,    0,    0,    0,    0,    0,
 59,    0,    0,    0,    0,    0,    0,  190,    0,    0,
  0,    0,    0,    0,    0,  192,    0,   59,    0,    0,
  0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  0,    0,  204,    0,    0,   59,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
  0,   59,   41,   41,   42,   43,   40,   45,   59,   47,
  0,   41,   42,   43,   59,   45,   43,   47,   45,   59,
  0,   59,   60,   61,   62,   43,   91,   45,   44,   59,
 60,   61,   62,   60,   61,   62,   44,   93,   41,  125,
123,   91,   59,   59,   91,   23,   40,   59,   91,   91,
256,   59,   43,   41,   45,   93,   59,   43,   91,   45,
 43,  256,   45,   93,   93,   41,  146,   43,   59,   45,
276,   59,   50,  270,   41,   93,   43,   55,   45,   43,
 43,   45,   45,   59,   60,   61,   62,   65,   43,  121,
 45,   91,   59,   60,   61,   62,   59,   75,   91,  179,
 41,   91,   43,   59,   45,   59,  123,   93,   40,   42,
 59,   91,   59,  125,   47,  268,   94,   93,   59,   60,
 61,   62,   10,  123,  102,   91,   93,    2,   10,   93,
210,  256,  164,  123,   22,  191,  279,  169,   93,  264,
 22,   10,  257,  123,  260,   20,  262,   22,  263,  264,
265,   91,   93,   22,  260,  235,  262,  135,  256,  257,
138,   59,   37,  278,  267,  268,  267,  268,   91,  125,
256,  125,  256,   91,  257,   59,  125,  269,  125,  257,
263,  264,  265,  274,  275,  274,  275,   59,   91,   64,
263,  256,  257,  267,  268,  278,  267,  268,   93,  257,
123,   91,  267,  268,   91,  256,  256,  265,   59,  187,
257,  256,  259,  256,  261,  257,  256,  256,  256,  266,
123,  279,  256,   41,  257,  270,  256,  256,   91,   97,
 98,  269,  256,  271,  272,  273,   91,  260,  256,  256,
256,  271,  272,  273,  271,  272,  273,   91,  256,  227,
267,  268,  262,   91,   41,  267,  268,  257,   91,  259,
123,  261,   59,  263,  264,  265,  266,  257,  270,  259,
125,  261,   93,  263,  264,  265,  266,  257,  278,  259,
256,  261,   93,  263,  264,  265,  266,  125,  278,  256,
 91,   91,  100,  101,   59,  271,  272,  273,  278,   91,
256,   91,  256,  125,  271,  272,  273,  256,   59,  256,
 44,  267,  268,  267,  268,  256,  256,  257,  267,  268,
267,  268,   59,   91,  125,  125,   59,  267,  268,   59,
271,  272,  273,   91,  257,  125,  259,   41,  261,  257,
263,  264,  265,  266,   91,  269,   59,   91,   91,  267,
268,  268,   93,  262,  257,  278,  259,  125,  261,   91,
263,  264,  265,  266,   59,  123,  256,  257,  125,  256,
257,   93,  125,  125,   59,  278,  123,  267,  268,  123,
267,  268,   41,   93,   93,   93,   93,  125,   40,  125,
  0,  123,    0,  256,  257,    0,  259,  270,  261,  270,
  0,  256,  257,  266,  259,   21,  261,    0,    2,   41,
 14,  266,  256,  257,  106,  259,  118,  261,  191,  257,
 21,  259,  266,  261,   -1,  171,   -1,   -1,  266,   -1,
 -1,   -1,   48,   49,   -1,   -1,   -1,   -1,   -1,   -1,
 -1,   -1,   -1,   -1,   -1,   -1,   -1,   48,   -1,   -1,
 -1,   -1,   68,   69,   -1,   -1,  257,  257,  259,  259,
261,  261,   -1,   -1,   -1,  266,  266,  257,   69,  259,
 -1,  261,   -1,   -1,   -1,   -1,  266,   93,   -1,   -1,
 -1,   97,   98,   -1,  100,  101,   -1,   -1,   -1,  257,
106,  259,   -1,  261,   -1,   -1,   -1,   -1,  266,  257,
 -1,  259,   -1,  261,   -1,   -1,   -1,   -1,  266,   -1,
257,   -1,  259,  257,  261,  259,  132,  261,   -1,  266,
 -1,   -1,  266,   -1,   -1,  257,   -1,  259,   -1,  261,
 -1,  132,  148,   -1,  266,   -1,   -1,   -1,   -1,   -1,
156,   -1,   -1,   -1,   -1,   -1,   -1,  148,   -1,   -1,
 -1,   -1,   -1,   -1,   -1,  156,   -1,  173,   -1,   -1,
 -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
 -1,   -1,  173,   -1,   -1,  191,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=279;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"ID","THEN","IF","ELSE","PRINT","ENDIF",
"INTEGER","UINTEGER","MATRIX","FOR","CONSTANTE_POSITIVA","CONSTANTE_ENTERA",
"MENOSMENOS","ASIGNACION","DISTINTO","MENORIGUAL","MAYORIGUAL","ANOTACION_CERO",
"ANOTACION_UNO","CADENA_MULTILINEA","FIN","ALLOW","TO",
};
final static String yyrule[] = {
"$accept : incio",
"incio : programa",
"programa : ID s_declarativa '{' s_ejecutable '}'",
"programa : ID s_declarativa",
"programa : ID '{' s_ejecutable '}'",
"programa : error s_declarativa '{' s_ejecutable '}'",
"programa : ID s_declarativa s_ejecutable '}'",
"programa : ID s_declarativa '{' s_ejecutable",
"programa : ID s_declarativa s_ejecutable",
"programa : ID '{' s_ejecutable",
"programa : ID s_ejecutable '}'",
"programa : ID s_ejecutable",
"s_declarativa : s_declarativa declarar_variable",
"s_declarativa : s_declarativa declarar_arreglo",
"s_declarativa : s_declarativa conversiones",
"s_declarativa : declarar_variable",
"s_declarativa : declarar_arreglo",
"s_declarativa : conversiones",
"declarar_variable : tipo l_variables ';'",
"declarar_variable : tipo ';'",
"declarar_variable : tipo l_variables",
"declarar_variable : l_variables ';'",
"l_variables : l_variables ',' ID",
"l_variables : ID",
"l_variables : l_variables error",
"declarar_arreglo : tipo MATRIX matriz inicializar_arreglo ';' ANOTACION_CERO",
"declarar_arreglo : tipo MATRIX matriz inicializar_arreglo ';' ANOTACION_UNO",
"declarar_arreglo : tipo MATRIX matriz inicializar_arreglo ';'",
"declarar_arreglo : tipo MATRIX matriz ';' ANOTACION_CERO",
"declarar_arreglo : tipo MATRIX matriz ';' ANOTACION_UNO",
"declarar_arreglo : tipo MATRIX matriz ';'",
"declarar_arreglo : MATRIX matriz ';'",
"matriz : ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA ']'",
"matriz : ID '[' CONSTANTE_POSITIVA ']' '[' CONSTANTE_POSITIVA ']'",
"matriz : ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_POSITIVA ']'",
"matriz : ID '[' CONSTANTE_POSITIVA ']' '[' CONSTANTE_ENTERA ']'",
"matriz : ID '[' CONSTANTE_ENTERA ']' error",
"matriz : ID '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA error",
"matriz : '[' CONSTANTE_ENTERA ']' '[' CONSTANTE_ENTERA ']'",
"matriz_ejecutable : ID '[' expresion ']' '[' expresion ']'",
"matriz_ejecutable : ID '[' expresion ']' error ']'",
"matriz_ejecutable : ID '[' expresion ']' '[' expresion error ']'",
"matriz_ejecutable : '[' expresion ']' '[' expresion ']'",
"inicializar_arreglo : '{' valores_fila '}'",
"inicializar_arreglo : valores_fila '}'",
"inicializar_arreglo : '{' valores_fila error '}'",
"inicializar_arreglo : error '}'",
"valores_fila : valores_fila ';' valores_columna",
"valores_fila : valores_columna",
"valores_fila : valores_fila valores_columna",
"valores_columna : valores_columna ',' constante",
"valores_columna : constante",
"conversiones : ALLOW UINTEGER TO INTEGER ';'",
"conversiones : ALLOW error ';'",
"conversiones : tipo TO error ';'",
"constante : CONSTANTE_ENTERA",
"constante : CONSTANTE_POSITIVA",
"tipo : INTEGER",
"tipo : UINTEGER",
"s_compleja : s_ejecutable s_simple",
"s_ejecutable : s_ejecutable s_simple",
"s_ejecutable : s_simple",
"s_simple : b_if",
"s_simple : b_for",
"s_simple : b_print",
"s_simple : b_asignacion",
"b_if : IF condicion_parentesis cuerpo_if",
"b_if : IF condicion_parentesis error",
"b_if : IF condicion_parentesis s_simple error",
"b_if : IF condicion_parentesis '{' error",
"b_if : IF condicion_parentesis '{' s_compleja error",
"b_if : IF condicion_parentesis '{' s_compleja '}' error",
"b_if : IF condicion_parentesis '{' s_compleja '}' ';'",
"condicion_parentesis : '(' condicion ')'",
"cuerpo_if : cuerpo_then ELSE cuerpo_else ENDIF ';'",
"cuerpo_if : cuerpo_then2 ENDIF ';'",
"cuerpo_then : s_simple",
"cuerpo_then : '{' s_compleja '}'",
"cuerpo_then2 : s_simple",
"cuerpo_then2 : '{' s_compleja '}'",
"cuerpo_else : s_simple",
"cuerpo_else : '{' s_compleja '}'",
"condicion : expresion comparacion expresion",
"condicion : error",
"condicion : expresion comparacion error",
"b_for : inicio_for '(' b_asignacion_for condicion_for ';' actualizacion_variable ')' cuerpo_for",
"b_for : FOR error ';' actualizacion_variable ')' s_simple",
"b_for : FOR error ';' actualizacion_variable ')' '{' s_compleja '}'",
"b_for : FOR error ')' s_simple",
"b_for : FOR error ')' '{' s_compleja '}'",
"inicio_for : FOR",
"condicion_for : condicion",
"cuerpo_for : s_simple",
"cuerpo_for : '{' s_compleja '}'",
"actualizacion_variable : factor MENOSMENOS",
"actualizacion_variable : error",
"b_print : PRINT '(' CADENA_MULTILINEA ')' ';'",
"b_print : PRINT error ';'",
"b_print : PRINT '(' error ')' ';'",
"b_print : PRINT '(' CADENA_MULTILINEA error ';'",
"b_print : PRINT '(' CADENA_MULTILINEA ')' error ';'",
"b_asignacion_for : inicio_asig_for ASIGNACION expresion ';'",
"b_asignacion : inicio_asig ASIGNACION expresion ';'",
"b_asignacion : inicio_asig ASIGNACION error ';'",
"inicio_asig_for : ID",
"inicio_asig_for : matriz_ejecutable",
"inicio_asig : ID",
"inicio_asig : matriz_ejecutable",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' miembro",
"termino : termino '/' miembro",
"termino : miembro",
"miembro : factor MENOSMENOS",
"miembro : factor",
"factor : ID",
"factor : CONSTANTE_POSITIVA",
"factor : CONSTANTE_ENTERA",
"factor : matriz_ejecutable",
"comparacion : '<'",
"comparacion : MENORIGUAL",
"comparacion : '>'",
"comparacion : MAYORIGUAL",
"comparacion : '='",
"comparacion : DISTINTO",
};

//#line 993 "gramaticaReentrega.y"

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
//#line 586 "Parser.java"
//###############################################################
//method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
if (ch < 0) ch=0;
if (ch <= YYMAXTOKEN) //check index bounds
   s = yyname[ch];    //now get it
if (s==null)
  s = "illegal-symbol";
debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
//method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws IOException
{
boolean doaction;
init_stacks();
yynerrs = 0;
yyerrflag = 0;
yychar = -1;          //impossible char forces a read
yystate=0;            //initial state
state_push(yystate);  //save it
val_push(yylval);     //save empty value
while (true) //until parsing is done, either correctly, or w/error
  {
  doaction=true;
  if (yydebug) debug("loop"); 
  //#### NEXT ACTION (from reduction table)
  for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
    {
    if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
    if (yychar < 0)      //we want a char?
      {
      yychar = yylex();  //get next token
      if (yydebug) debug(" next yychar:"+yychar);
      //#### ERROR CHECK ####
      if (yychar < 0)    //it it didn't work/error
        {
        yychar = 0;      //change it to default string (no -1!)
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      }//yychar<0
    yyn = yysindex[yystate];  //get amount to shift by (shift index)
    if ((yyn != 0) && (yyn += yychar) >= 0 &&
        yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {
      if (yydebug)
        debug("state "+yystate+", shifting to state "+yytable[yyn]);
      //#### NEXT STATE ####
      yystate = yytable[yyn];//we are in a new state
      state_push(yystate);   //save it
      val_push(yylval);      //push our lval as the input for next rule
      yychar = -1;           //since we have 'eaten' a token, say we need another
      if (yyerrflag > 0)     //have we recovered an error?
         --yyerrflag;        //give ourselves credit
      doaction=false;        //but don't process yet
      break;   //quit the yyn=0 loop
      }

  yyn = yyrindex[yystate];  //reduce
  if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {   //we reduced!
    if (yydebug) debug("reduce");
    yyn = yytable[yyn];
    doaction=true; //get ready to execute
    break;         //drop down to actions
    }
  else //ERROR RECOVERY
    {
    if (yyerrflag==0)
      {
      yyerror("syntax error");
      yynerrs++;
      }
    if (yyerrflag < 3) //low error count?
      {
      yyerrflag = 3;
      while (true)   //do until break
        {
        if (stateptr<0)   //check for under & overflow here
          {
          yyerror("stack underflow. aborting...");  //note lower case 's'
          return 1;
          }
        yyn = yysindex[state_peek(0)];
        if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                  yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
          {
          if (yydebug)
            debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
          yystate = yytable[yyn];
          state_push(yystate);
          val_push(yylval);
          doaction=false;
          break;
          }
        else
          {
          if (yydebug)
            debug("error recovery discarding state "+state_peek(0)+" ");
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("Stack underflow. aborting...");  //capital 'S'
            return 1;
            }
          state_pop();
          val_pop();
          }
        }
      }
    else            //discard this token
      {
      if (yychar == 0)
        return 1; //yyabort
      if (yydebug)
        {
        yys = null;
        if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
        if (yys == null) yys = "illegal-symbol";
        debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
        }
      yychar = -1;  //read another
      }
    }//end error recovery
  }//yyn=0 loop
  if (!doaction)   //any reason not to proceed?
    continue;      //skip action
  yym = yylen[yyn];          //get count of terminals on rhs
  if (yydebug)
    debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
  if (yym>0)                 //if count of rhs not 'nil'
    yyval = val_peek(yym-1); //get current semantic value
  yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
  switch(yyn)
    {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 41 "gramaticaReentrega.y"
{if (!((Generador)val_peek(4).obj).isTerceto()){
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos(( (Token)val_peek(4).obj).getClaveTablaSimboloToken()).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(( (Token)val_peek(4).obj).getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(( (Token)val_peek(4).obj).getClaveTablaSimboloToken()).getToken());
													t1.setUso("prog@");
													t1.setTipo("nombre_programa");
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						/*							Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());*/
													((Token)val_peek(4).obj).setUso("prog@");
													if((Compilador.get_Tsimbolos(((Token)val_peek(4).obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)val_peek(4).obj).getClaveTablaSimboloToken()).getUso() == ""))
														System.out.println("nombre programa sin definir");
													}
													else
														System.out.println("nombre de programa es terceto, error");
													}
break;
case 3:
//#line 57 "gramaticaReentrega.y"
{if (!((Generador)val_peek(1).obj).isTerceto()){
										Tabla_Simbolos t1=new Tabla_Simbolos();
										t1.setInformacion(Compilador.get_Tsimbolos(( (Token)val_peek(1).obj).getClaveTablaSimboloToken()).getInformacion());
										t1.setLexema(Compilador.get_Tsimbolos(( (Token)val_peek(1).obj).getClaveTablaSimboloToken()).getLexema());
										t1.setToken(Compilador.get_Tsimbolos(( (Token)val_peek(1).obj).getClaveTablaSimboloToken()).getToken());
										t1.setUso("prog@");
										t1.setTipo("nombre_programa");
										Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						/*				Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());*/
										((Token)val_peek(1).obj).setUso("prog@");
										if((Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getUso() == ""))
											System.out.println("nombre programa sin definir");
										}
										else
											System.out.println("nombre de programa es terceto, error");
										}
break;
case 4:
//#line 73 "gramaticaReentrega.y"
{if (!((Generador)val_peek(3).obj).isTerceto()){
												Tabla_Simbolos t1=new Tabla_Simbolos();
												t1.setInformacion(Compilador.get_Tsimbolos(( (Token)val_peek(3).obj).getClaveTablaSimboloToken()).getInformacion());
												t1.setLexema(Compilador.get_Tsimbolos(( (Token)val_peek(3).obj).getClaveTablaSimboloToken()).getLexema());
												t1.setToken(Compilador.get_Tsimbolos(( (Token)val_peek(3).obj).getClaveTablaSimboloToken()).getToken());
												t1.setUso("prog@");
												t1.setTipo("nombre_programa");
												Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
						/*						Compilador.T_simbolos.remove(( (Token)$1.obj).getClaveTablaSimboloToken());*/
												((Token)val_peek(3).obj).setUso("prog@");
												if((Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken())!=null) && (Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).getUso() == ""))
													System.out.println("nombre programa sin definir");
												}
												else
													System.out.println("nombre de programa es terceto, error");
												}
break;
case 5:
//#line 90 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un id que haga referecia al nombre del programa. Linea: 1 " );}
break;
case 6:
//#line 91 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 7:
//#line 92 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 8:
//#line 93 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 9:
//#line 94 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 10:
//#line 95 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 11:
//#line 96 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan las llaves del programa. Linea:  " +lexico.getNrolinea());}
break;
case 18:
//#line 107 "gramaticaReentrega.y"
{
											for (Token variable: listaTipoVariable){											
												if((Compilador.get_Tsimbolos("var@" +variable.getClaveTablaSimboloToken())!=null)&&(Compilador.get_Tsimbolos("var@" +variable.getClaveTablaSimboloToken()).getTipo()!="")){													
													yyerrorSemantico("Se redeclaro la variable: " + Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getLexema() + " en la linea: "+lexico.getNrolinea());													
													String aux = variable.getClaveTablaSimboloToken();
										/*			Compilador.T_simbolos.remove(aux);*/
												}else{
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(variable.getClaveTablaSimboloToken()).getToken());
													t1.setUso("var@");
													t1.setTipo(tipoVariable);
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
							/*						Compilador.T_simbolos.remove(variable.getClaveTablaSimboloToken());*/
													variable.setUso("var@");
												}																							
											};
											listaTipoVariable.removeAll(listaTipoVariable);
										}
break;
case 19:
//#line 128 "gramaticaReentrega.y"
{yyerror("Error Sintactico, faltan declarar las variable.En la Linea: "+lexico.getNrolinea());}
break;
case 20:
//#line 129 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un punto y coma. Linea: "+(lexico.getNrolinea()-1));}
break;
case 21:
//#line 130 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta el tipo de la variable.En la Linea: "+lexico.getNrolinea());}
break;
case 22:
//#line 133 "gramaticaReentrega.y"
{listaTipoVariable.add((Token)val_peek(0).obj);}
break;
case 23:
//#line 134 "gramaticaReentrega.y"
{listaTipoVariable.add((Token)val_peek(0).obj);}
break;
case 24:
//#line 136 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera una coma. Linea: "+lexico.getNrolinea());}
break;
case 25:
//#line 139 "gramaticaReentrega.y"
{ if (!((Generador)val_peek(3).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setComienza11(false);
					/*											System.out.println("le cambie el tipo a la mat " +((Token)$3.obj).getClaveTablaSimboloToken() +" en la declaracion, ahora es: " + Compilador.get_Tsimbolos(((Token)$3.obj).getClaveTablaSimboloToken()).getTipo());*/
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																/*		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);*/
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz (deben ser enteras), en la linea: " + lexico.getNrolinea());																			
																		}
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																}
																 
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
break;
case 26:
//#line 162 "gramaticaReentrega.y"
{if (!((Generador)val_peek(3).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setComienza11(true);
																numFila--;
																numCol--;
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setFilas(numFila);
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setColumnas(numCol);
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																/*		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);*/
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz, en la linea" + lexico.getNrolinea());
																				
																		}
																Compilador.get_Tsimbolos(((Token)val_peek(3).obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																};
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
break;
case 27:
//#line 187 "gramaticaReentrega.y"
{ if (!((Generador)val_peek(2).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setComienza11(false);
																
																elementosMatriz = new int [numFila][numCol];
																for (int i = 0; i < numFila; i++)
																	for (int j = 0; j < numCol; j++){
																		elementosMatriz[i][j] = listaInicMatriz.get(i*(numCol)+j);
																/*		System.out.println("mat " + Compilador.get_Tsimbolos("mat@" +((Token)$3.obj).getClaveTablaSimboloToken()).getLexema() + " [" + i + "]" + " [" + j + "] = " + elementosMatriz[i][j]);*/
																		if (listaTiposInicMatriz.get(i*(numCol)+j) != Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).getTipo())
																			yyerrorSemantico("incompatibilidad de tipos en inicializacion de matriz, en la linea" + lexico.getNrolinea());
																				
																		}
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setMatriz(elementosMatriz);
																listaInicMatriz.removeAll(listaInicMatriz);
																listaTiposInicMatriz.removeAll(listaTiposInicMatriz);
																};
															}
															else
																System.out.println("declaracion de matriz es terceto, error");
															}
break;
case 28:
//#line 209 "gramaticaReentrega.y"
{if (!((Generador)val_peek(2).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setComienza11(false);
										/*						System.out.println("clave: " + ((Token)$3.obj).getClaveTablaSimboloToken());*/
																}}
														else
															System.out.println("declaracion de matriz es terceto, error");
														}
break;
case 29:
//#line 218 "gramaticaReentrega.y"
{ if (!((Generador)val_peek(2).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken())!=null)){
																numFila--;
																numCol--;
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setFilas(numFila);
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setColumnas(numCol);
																
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(2).obj).getClaveTablaSimboloToken()).setComienza11(true);
																}}
														else
															System.out.println("declaracion de matriz es terceto, error");
														}
break;
case 30:
//#line 231 "gramaticaReentrega.y"
{ if (!((Generador)val_peek(1).obj).isTerceto()){
															if((Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken())!=null)){
																Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).setTipo(tipoVariable);
																Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).setComienza11(false);
																}}
										else
											System.out.println("declaracion de matriz es terceto, error");
										}
break;
case 31:
//#line 244 "gramaticaReentrega.y"
{yyerror("Error Sintactico en la declaracion de los arreglos, falta declarar el tipo de la matriz.En la Linea: "+lexico.getNrolinea());}
break;
case 32:
//#line 247 "gramaticaReentrega.y"
{numFila = Integer.parseInt(((Token)val_peek(4).obj).getLexema());
																numCol = Integer.parseInt(((Token)val_peek(1).obj).getLexema());
												if((Compilador.get_Tsimbolos("mat@" +((Token)val_peek(6).obj).getClaveTablaSimboloToken())!=null)&&(Compilador.get_Tsimbolos("mat@" +((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getTipo()!="sin definir")){													
													yyerrorSemantico("Se redeclaro la matriz: " + Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getLexema() + " en la linea: "+lexico.getNrolinea());													
													String aux = Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getLexema() + Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getToken();
													Compilador.T_simbolos.remove(aux);
												}else{
													Tabla_Simbolos t1=new Tabla_Simbolos();
													t1.setInformacion(Compilador.get_Tsimbolos((((Token)val_peek(6).obj).getClaveTablaSimboloToken())).getInformacion());
													t1.setLexema(Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getLexema());
													t1.setToken(Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getToken());
													t1.setUso("mat@");
													t1.setTipo(tipoVariable);
													t1.setFilas(numFila);
													t1.setColumnas(numCol);
													Compilador.T_simbolos.put(t1.getUso() + t1.getLexema() + t1.getToken(), t1);
													((Token)val_peek(6).obj).setUso("mat@");
												}
												yyval.obj = val_peek(6).obj;
}
break;
case 33:
//#line 268 "gramaticaReentrega.y"
{yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
break;
case 34:
//#line 269 "gramaticaReentrega.y"
{yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
break;
case 35:
//#line 270 "gramaticaReentrega.y"
{yyerror ("Error Sintactico, deben ser constantes enteras. En la linea: " + lexico.getNrolinea());}
break;
case 36:
//#line 271 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que abre en la Linea: "+lexico.getNrolinea());}
break;
case 37:
//#line 272 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que cierra en la Linea: "+lexico.getNrolinea());}
break;
case 38:
//#line 273 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un nombre para la matriz en la Linea: "+lexico.getNrolinea());}
break;
case 39:
//#line 277 "gramaticaReentrega.y"
{ 
													((Token)val_peek(6).obj).setUso("mat@");
													if ((val_peek(4).getTipo() == "Integer") && (val_peek(1).getTipo() == "Integer")){
													if(((Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken())!=null))){
														String aux = "mat@" +((Token)val_peek(6).obj).getLexema();
														Token token = new Token("", aux, Compilador.ID);
/*									Terceto t0 = new Terceto("baseMatriz",token,null, Main.getTerceto().size());*/
														Tabla_Simbolos tabla2=new Tabla_Simbolos();
														tabla2.setLexema("2");
														tabla2.setToken(Compilador.CONSTANTE_ENTERA);
														tabla2.setUso("");
														Compilador.T_simbolos.put(tabla2.getUso() +tabla2.getLexema() + tabla2.getToken(), tabla2);
														Terceto t4;
														if (Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getComienza11()){
															Terceto t2 = new Terceto("-", (Generador)val_peek(4).obj, new Token("","1",Compilador.CONSTANTE_ENTERA) , Main.getTerceto().size());
															t2.setTipo("Integer");
															Main.addTerceto(t2);
															Terceto t12 = new Terceto ("chequeoFila" , t2, token, Main.getTerceto().size());
															t12.setTipo("Integer");
															Main.addTerceto(t12);
															Terceto t1 = new Terceto("-", (Generador)val_peek(1).obj, new Token("","1",Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t1.setTipo("Integer");
															Main.addTerceto(t1);
															Terceto t11 = new Terceto ("chequeoCol" , t1, token, Main.getTerceto().size());
															t11.setTipo("Integer");
															Main.addTerceto(t11);
															Terceto t3 = new Terceto("*", t12, new Token("",Integer.toString(Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getColumnas()),Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t3.setTipo("Integer");
															Main.addTerceto(t3);
															t4 = new Terceto("+", t3 , t11, Main.getTerceto().size());
															t4.setTipo("Integer");
														
														}
														else{
															Terceto t12 = new Terceto ("chequeoFila" , (Generador)val_peek(4).obj, token, Main.getTerceto().size());
															t12.setTipo("Integer");
															Main.addTerceto(t12);
															Terceto t11 = new Terceto ("chequeoCol" , (Generador)val_peek(1).obj, token, Main.getTerceto().size());
															t11.setTipo("Integer");
															Main.addTerceto(t11);
															Terceto t3 = new Terceto("*", t12, new Token("",Integer.toString(Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getColumnas()),Compilador.CONSTANTE_ENTERA), Main.getTerceto().size());
															t3.setTipo("Integer");
															Main.addTerceto(t3);
															t4 = new Terceto("+", t3 , t11, Main.getTerceto().size());
															t4.setTipo("Integer");
														}
														Main.addTerceto(t4);
														Terceto t5 = new Terceto("*", t4, new Token("","2",Compilador.CONSTANTE_ENTERA) , Main.getTerceto().size());/*para cantidad de bytes*/
														t5.setTipo("Integer");
														Main.addTerceto(t5); 
													/*	Terceto t6 = new Terceto("+", t0, t5, Main.getTerceto().size());*/
													/*	t6.setTipo("Integer");*/
													/*	Main.addTerceto(t6);*/
/*control de fuera de matriz comparando la direccion base y la base + fin con la base+ anterior	*/
														Terceto t7 = new Terceto("operacion", t5, token, Main.getTerceto().size());
														Main.addTerceto(t7);
														yyval.obj = ((Generador)t7);
														yyval.setTipo(Compilador.get_Tsimbolos(((Token)val_peek(6).obj).getClaveTablaSimboloToken()).getTipo());
			/*											System.out.println("tipo de mat ejec " + $$.getTipo());*/
														}
														else
															yyerrorSemantico("se quiere utilizar una matriz no declarada. En la linea: " + lexico.getNrolinea());
														}
														else
															yyerrorSemantico("Tipo de acceso incorrecto, las posiciones de la matriz deben ser integer. En la linea: " + lexico.getNrolinea());
														}
break;
case 40:
//#line 344 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que abre en la Linea: "+lexico.getNrolinea());}
break;
case 41:
//#line 345 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que cierra en la Linea: "+lexico.getNrolinea());}
break;
case 42:
//#line 346 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un nombre para la matriz en la Linea: "+lexico.getNrolinea());}
break;
case 44:
//#line 352 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que abre : "+lexico.getNrolinea());}
break;
case 45:
//#line 353 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que cierra : "+lexico.getNrolinea());}
break;
case 46:
//#line 354 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera un corchete que abre : "+lexico.getNrolinea());}
break;
case 49:
//#line 359 "gramaticaReentrega.y"
{yyerror("Error Sintactico, se espera ';' en la fila de la matriz. Linea: "+lexico.getNrolinea());}
break;
case 50:
//#line 361 "gramaticaReentrega.y"
{if (!((Generador)val_peek(0).obj).isTerceto()){
												listaInicMatriz.add(Integer.parseInt(((Token)val_peek(0).obj).getLexema()));
												listaTiposInicMatriz.add(val_peek(0).getTipo());
											}else
												System.out.println("inicializacion de matriz es terceto, error");
											}
break;
case 51:
//#line 368 "gramaticaReentrega.y"
{if (!((Generador)val_peek(0).obj).isTerceto()){
												listaInicMatriz.add(Integer.parseInt(((Token)val_peek(0).obj).getLexema()));
												listaTiposInicMatriz.add(val_peek(0).getTipo());
												}else
												System.out.println("inicializacion de matriz es terceto, error");
											}
break;
case 52:
//#line 378 "gramaticaReentrega.y"
{conversionesUintegeraInteger = true;}
break;
case 53:
//#line 379 "gramaticaReentrega.y"
{yyerror("Error Sintactico, no es una conversion valida. En la linea: "+lexico.getNrolinea());}
break;
case 54:
//#line 380 "gramaticaReentrega.y"
{yyerror("Error Sintactico, no es una anotacion valida. En la linea: "+lexico.getNrolinea());}
break;
case 55:
//#line 383 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
							yyval.setTipo("Integer");
							}
break;
case 56:
//#line 386 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
							yyval.setTipo("Uinteger");
							}
break;
case 57:
//#line 391 "gramaticaReentrega.y"
{tipoVariable="Integer";}
break;
case 58:
//#line 392 "gramaticaReentrega.y"
{tipoVariable="Uinteger";}
break;
case 62:
//#line 406 "gramaticaReentrega.y"
{yyvalida("sentencia IF . En la linea: "+lexico.getNrolinea());}
break;
case 63:
//#line 407 "gramaticaReentrega.y"
{yyvalida("sentencia FOR . En la linea: "+lexico.getNrolinea());}
break;
case 64:
//#line 408 "gramaticaReentrega.y"
{yyvalida("sentencia PRINT . En la linea: "+lexico.getNrolinea());}
break;
case 65:
//#line 409 "gramaticaReentrega.y"
{yyvalida("sentencia de ASIGNACION . En la linea: "+lexico.getNrolinea());}
break;
case 66:
//#line 416 "gramaticaReentrega.y"
{
		TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label" + Main.getTerceto().size(), Main.getTerceto().size());
		Main.addTerceto(t2); 
}
break;
case 67:
//#line 422 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta secuencia simple o llave que abre. En la linea: "+lexico.getNrolinea());}
break;
case 68:
//#line 423 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta un else o un endif. En la linea: "+lexico.getNrolinea());}
break;
case 69:
//#line 424 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta una secuencia compleja. En la linea: "+lexico.getNrolinea());}
break;
case 70:
//#line 425 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta una llave que cierra. En la linea: "+lexico.getNrolinea());}
break;
case 71:
//#line 426 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta un else o un endif. En la linea: "+lexico.getNrolinea());}
break;
case 72:
//#line 427 "gramaticaReentrega.y"
{yyerror("Error Sintactico, falta un endif. En la linea: "+lexico.getNrolinea());}
break;
case 73:
//#line 431 "gramaticaReentrega.y"
{if (((Generador)val_peek(1).obj).isTerceto()){
										String aux = ((Terceto)val_peek(1).obj).getOperador();
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
										Terceto t  = new Terceto ("B"+salida, (Generador)val_peek(1).obj, null,Main.getTerceto().size());
										Main.addTerceto(t); 
										pilaTerceto.push(t);
										}
										else
										 System.out.println("condicion if no es terceto, ERROR");
										}
break;
case 74:
//#line 452 "gramaticaReentrega.y"
{}
break;
case 75:
//#line 453 "gramaticaReentrega.y"
{}
break;
case 76:
//#line 456 "gramaticaReentrega.y"
{Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size()+1;
						topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						Terceto t  = new Terceto ("BI",null, null,Main.getTerceto().size());
						Main.addTerceto(t); 
						pilaTerceto.push(t);
						TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label" + Main.getTerceto().size(), Main.getTerceto().size());
						Main.addTerceto(t2); 
						}
break;
case 77:
//#line 466 "gramaticaReentrega.y"
{
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
break;
case 78:
//#line 479 "gramaticaReentrega.y"
{
						Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size();
						topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						}
break;
case 79:
//#line 485 "gramaticaReentrega.y"
{
								Terceto topePila=pilaTerceto.pop();
								int cantTercetos=Main.getTerceto().size();
								topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
								Main.ActualizarTerceto(topePila);
								}
break;
case 80:
//#line 492 "gramaticaReentrega.y"
{
						Terceto topePila=pilaTerceto.pop();
						int cantTercetos=Main.getTerceto().size();
						topePila.setOperando1(new TercetoDireccionSalto("direccionSalto",cantTercetos));
						Main.ActualizarTerceto(topePila);
						}
break;
case 81:
//#line 498 "gramaticaReentrega.y"
{
								Terceto	 topePila=pilaTerceto.pop();
								int cantTercetos=Main.getTerceto().size();
								topePila.setOperando1(new TercetoDireccionSalto("direccionSalto",cantTercetos));
								Main.ActualizarTerceto(topePila);
								}
break;
case 82:
//#line 507 "gramaticaReentrega.y"
{
									/*$$.obj = new Terceto (getOperacion(), (Generador)$1.obj,(Generador) $3.obj,lexico.getTerceto().size());				*/
									/*lexico.addTerceto((Terceto)$$.obj);*/
										if (val_peek(2).getTipo() != "sin definir"){
													if (val_peek(2).getTipo() == val_peek(0).getTipo()){
														Terceto t = new Terceto (getOperacion(), (Generador)val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());				
														t.setTipo(val_peek(2).getTipo());
														yyval.obj = t;
														Main.addTerceto((Terceto)yyval.obj);}
													else{
														if (val_peek(2).getTipo() == "Uinteger"){
															Terceto aux = new Terceto("inttouint", (Generador)val_peek(0).obj, null, Main.getTerceto().size());
															Main.addTerceto(aux);
															Terceto t =new Terceto (getOperacion(), (Generador)val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());				
															t.setTipo("Uinteger");
	/****ACA***/											yyval.obj = t;
															Main.addTerceto((Terceto)yyval.obj);}
														else{
															if (conversionesUintegeraInteger){
																Terceto aux = new Terceto("uinttoint", (Generador)val_peek(0).obj, null, Main.getTerceto().size());
																Main.addTerceto(aux);
																Terceto t = new Terceto (getOperacion(), (Generador)val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());				
																t.setTipo("Integer");
		/****ACA***/											yyval.obj = t;
																Main.addTerceto((Terceto)yyval.obj);}	
															else
																yyerrorSemantico("Incompatibilidad de tipos en la comparacion de la linea: " + lexico.getNrolinea());
															}
															}
															}
													else 
															yyerrorSemantico("Incompatibilidad porque a " +  val_peek(2).obj  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());													
									}
break;
case 83:
//#line 541 "gramaticaReentrega.y"
{yyerror(("Error Sintactico, se espera una expresion.En la Linea: "+lexico.getNrolinea()));}
break;
case 84:
//#line 543 "gramaticaReentrega.y"
{yyerror(("Error Sintactico, se espera una expresion.En la Linea: "+lexico.getNrolinea()));}
break;
case 85:
//#line 547 "gramaticaReentrega.y"
{
																								Terceto topePila=pilaTerceto.pop();	
																								
																								int cantTercetos=Main.getTerceto().size()+1;																								
																								topePila.setOperando2(new TercetoDireccionSalto("direccionSalto",cantTercetos));
																								Main.ActualizarTerceto(topePila);																								
																								/*desapilo el numero de terceto inicio_asig*/
																								TercetoDireccionSalto topePilaInicio=(TercetoDireccionSalto) pilaTercetoSalto.pop();																																																
																								
																								Terceto t  = new Terceto ("BI",new TercetoDireccionSalto("direccionSalto",topePilaInicio.getDireccionSalto()), null,Main.getTerceto().size());																																																
																								Main.addTerceto(t); 
																								cantTercetos=Main.getTerceto().size();
																								TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label"+cantTercetos,Main.getTerceto().size());
																								Main.addTerceto(t2); 
																								/*pilaTerceto.push(t);																								*/
																							}
break;
case 86:
//#line 565 "gramaticaReentrega.y"
{yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
break;
case 87:
//#line 566 "gramaticaReentrega.y"
{yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
break;
case 88:
//#line 567 "gramaticaReentrega.y"
{yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
break;
case 89:
//#line 568 "gramaticaReentrega.y"
{yyerror("Error Sintactico en sentencia FOR. En la linea: "+lexico.getNrolinea());}
break;
case 91:
//#line 571 "gramaticaReentrega.y"
{ if (((Generador)val_peek(0).obj).isTerceto()){
										String aux = ((Terceto)val_peek(0).obj).getOperador();

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
										Terceto t  = new Terceto ("B"+salida, (Generador)val_peek(0).obj, null,Main.getTerceto().size());
										Main.addTerceto(t); 
										pilaTerceto.push(t);
										
						}else
							System.out.println("condicion for no es terceto, ERROR");
						}
break;
case 94:
//#line 596 "gramaticaReentrega.y"
{
							if (val_peek(1).getTipo() == "Uinteger"){
								yyerrorSemantico("el tipo de la variable de control del for no corresponde con la inicializada. En la linea: " + lexico.getNrolinea());
							}
							else{
								if (!((Generador)val_peek(1).obj).isTerceto()){
								System.out.println("aux for= " + aux_for + "size " + aux_for.length());
								System.out.println("lexema= " + Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getUso() + Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getLexema() + "size" + ((Token)val_peek(1).obj).getLexema().length());
								System.out.println("tipo: " + Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getTipo());
								String aux_for2 = Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getUso() + Compilador.get_Tsimbolos(((Token)val_peek(1).obj).getClaveTablaSimboloToken()).getLexema();
								if (aux_for2 == aux_for2){
								System.out.println("si entraba");
									Tabla_Simbolos tabla2=new Tabla_Simbolos();
									tabla2.setLexema("1");
									tabla2.setToken(Compilador.CONSTANTE_ENTERA);
									Compilador.T_simbolos.put(tabla2.getLexema() + tabla2.getToken(), tabla2);
									
									Terceto t=new Terceto ("-", (Generador)val_peek(1).obj, new Token("", "1", Compilador.CONSTANTE_ENTERA),Main.getTerceto().size());
									t.setTipo("Integer");
									Main.addTerceto(t);
								
									Terceto tn = new Terceto(":=", (Generador)val_peek(1).obj, t, Main.getTerceto().size());
									tn.setTipo("Integer");
									yyval.obj = tn;
									yyval.setTipo("Integer");
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
break;
case 95:
//#line 633 "gramaticaReentrega.y"
{yyerror("Error Sintactico. Al actualizar variable. En la linea: "+lexico.getNrolinea());}
break;
case 96:
//#line 637 "gramaticaReentrega.y"
{
											String lexema = ((Token)val_peek(2).obj).getLexema();
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
											yyval.obj = t1;}
break;
case 97:
//#line 653 "gramaticaReentrega.y"
{yyerror("Error Sintactico. Al hacer un print, falta parentesis que abre. En la linea: "+lexico.getNrolinea());}
break;
case 98:
//#line 654 "gramaticaReentrega.y"
{yyerror("Error Sintactico. Al hacer un print, falta la cadena multilinea. En la linea: "+lexico.getNrolinea());}
break;
case 99:
//#line 655 "gramaticaReentrega.y"
{yyerror("Error Sintactico. Al hacer un print, falta parentesis que cierra. En la linea: "+lexico.getNrolinea());}
break;
case 100:
//#line 656 "gramaticaReentrega.y"
{yyerror("Error Sintactico. Al hacer un print, falta el punto y coma. En la linea: "+lexico.getNrolinea());}
break;
case 101:
//#line 659 "gramaticaReentrega.y"
{
						if (val_peek(3).getTipo() != "sin definir"){
							if (val_peek(3).getTipo() == val_peek(1).getTipo()){
								Terceto t=new Terceto(":=", (Generador)val_peek(3).obj, (Generador)val_peek(1).obj, Main.getTerceto().size());
								t.setTipo(val_peek(3).getTipo());
								yyval.obj =t;	
								Main.addTerceto((Terceto)yyval.obj);}
							else{
								if (val_peek(3).getTipo() == "Uinteger"){
									Terceto aux = new Terceto("inttouint", (Generador)val_peek(1).obj, null, Main.getTerceto().size());
									Main.addTerceto(aux);
									Terceto t=new Terceto(":=", (Generador)val_peek(3).obj, aux, Main.getTerceto().size());
									t.setTipo("Uinteger");
									yyval.obj = t;
									Main.addTerceto((Terceto)yyval.obj);}
								else{
									if (conversionesUintegeraInteger){
										Terceto aux = new Terceto("uinttoint", (Generador)val_peek(1).obj, null, Main.getTerceto().size());
										Main.addTerceto(aux);
										Terceto t=new Terceto(":=", (Generador)val_peek(3).obj, aux, Main.getTerceto().size());
										t.setTipo("Uinteger");
										yyval.obj = t;	
										Main.addTerceto((Terceto)yyval.obj);
									}	
									else
										yyerrorSemantico("Incompatibilidad de tipos en la asignacion de la linea: " + lexico.getNrolinea());
								}
							}
						}
						else 
							yyerrorSemantico("Incompatibilidad porque a " +  val_peek(3)  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());

						int cantTercetos=Main.getTerceto().size();
						TercetoDireccionSalto t3=new TercetoDireccionSalto("direccionInicioSalto",cantTercetos);
						pilaTercetoSalto.push(t3);
						cantTercetos=Main.getTerceto().size();
						TercetoDireccionSalto t2  = new TercetoDireccionSalto ("Label"+cantTercetos, Main.getTerceto().size());
						Main.addTerceto(t2);
	 					}
break;
case 102:
//#line 700 "gramaticaReentrega.y"
{
												if (val_peek(3).getTipo() != ""){
													if (val_peek(3).getTipo() == val_peek(1).getTipo()){
													Terceto t1 = new Terceto(":=", (Generador)val_peek(3).obj, (Generador)val_peek(1).obj, Main.getTerceto().size());
													t1.setTipo(val_peek(3).getTipo());
														yyval.obj = t1;
														Main.addTerceto((Terceto)yyval.obj);}
													else{
														if (val_peek(3).getTipo() == "Uinteger"){
															Terceto aux = new Terceto("inttouint", (Generador)val_peek(1).obj, null, Main.getTerceto().size());
															Main.addTerceto(aux);
															Terceto t1 = new Terceto(":=", (Generador)val_peek(3).obj, aux, Main.getTerceto().size());
															t1.setTipo(val_peek(3).getTipo());
	/****ACA***/											yyval.obj = t1;
															((Terceto)yyval.obj).setTipo("Uinteger");
															Main.addTerceto((Terceto)yyval.obj);}
														else{
															if (conversionesUintegeraInteger){
							/*									System.out.println("$1 tiene tipo: " + $1.getTipo());*/
																Terceto aux = new Terceto("uinttoint", (Generador)val_peek(1).obj, null, Main.getTerceto().size());
																Main.addTerceto(aux);
																Terceto t1 = new Terceto(":=", (Generador)val_peek(3).obj, aux, Main.getTerceto().size());
																t1.setTipo(val_peek(3).getTipo());
		/****ACA***/											yyval.obj = t1;
																((Terceto)yyval.obj).setTipo("Integer");
																Main.addTerceto((Terceto)yyval.obj);}	
															else
																yyerrorSemantico("Incompatibilidad de tipos en la asignacion de la linea: " + lexico.getNrolinea());
															}
															}
															}
												else 
													yyerrorSemantico("Incompatibilidad porque a " +  val_peek(3).obj  + " le falta tipo en la asignacion de la linea: " + lexico.getNrolinea());													
}
break;
case 103:
//#line 735 "gramaticaReentrega.y"
{yyerror("Error Sintactico al realizar una asignacion falta expresion. En la linea: "+lexico.getNrolinea());}
break;
case 104:
//#line 738 "gramaticaReentrega.y"
{if (ExisteTipo((Token)val_peek(0).obj, "var@")){
						((Token)val_peek(0).obj).setUso("var@");
						if ((Compilador.get_Tsimbolos(((Token)val_peek(0).obj).getClaveTablaSimboloToken()).getTipo()=="Integer")){
							yyval.obj = val_peek(0).obj;
							aux_for = "var@" +((Token)val_peek(0).obj).getLexema();							
							yyval.setTipo((Compilador.get_Tsimbolos(((Token)val_peek(0).obj).getClaveTablaSimboloToken())).getTipo());
						}
						else
							yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea" + lexico.getNrolinea());
					}
				else{
					yyval.obj = val_peek(0).obj;
					yyval.setTipo("sin definir");
					yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea" + lexico.getNrolinea());
					}}
break;
case 105:
//#line 753 "gramaticaReentrega.y"
{
								if (val_peek(0).getTipo()=="Integer"){
			/*????*/				yyval.obj = val_peek(0).obj;
									Token t = (Token)((Terceto)val_peek(0).obj).getOperando2();
									aux_for = t.getLexema();
									yyval.setTipo((val_peek(0)).getTipo());
								}
								else
									yyerrorSemantico("Incompatibilidad de tipos en inicializacion de for. En la linea: " + lexico.getNrolinea());
							
							}
break;
case 106:
//#line 766 "gramaticaReentrega.y"
{
					if (ExisteTipo((Token)val_peek(0).obj, "var@")){
					((Token)val_peek(0).obj).setUso("var@");
						yyval.obj = val_peek(0).obj;
						yyval.setTipo((Compilador.get_Tsimbolos(((Token)val_peek(0).obj).getClaveTablaSimboloToken())).getTipo());
						}
					else{
						yyval.obj = val_peek(0).obj;
						yyval.setTipo("");
						}
					}
break;
case 107:
//#line 777 "gramaticaReentrega.y"
{
								yyval.obj = val_peek(0).obj;
								yyval.setTipo(val_peek(0).getTipo());
			/*					System.out.println("tipo mat inicio asig " + $1.getTipo());*/
							}
break;
case 108:
//#line 783 "gramaticaReentrega.y"
{if (val_peek(2).getTipo() == val_peek(0).getTipo()){
									Terceto t1 =new Terceto ("+", (Generador)val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
									t1.setTipo(val_peek(2).getTipo());
									yyval.obj = t1;
									yyval.setTipo(val_peek(2).getTipo());
									Main.addTerceto((Terceto)yyval.obj);}
								else{
									
									if (val_peek(2).getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)val_peek(0).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("+", (Generador)val_peek(2).obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)yyval.obj);}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)val_peek(2).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("+", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) val_peek(0).obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)yyval.obj);}}}
break;
case 109:
//#line 805 "gramaticaReentrega.y"
{if (val_peek(2).getTipo() == val_peek(0).getTipo()){
									Terceto t1 = new Terceto ("-", (Generador)val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
									t1.setTipo(val_peek(2).getTipo());
									yyval.obj = t1;
									Main.addTerceto((Terceto)yyval.obj);
									yyval.setTipo(val_peek(2).getTipo());}
								else{
									
									if (val_peek(2).getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)val_peek(0).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("-", (Generador)val_peek(2).obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										Main.addTerceto((Terceto)yyval.obj);
										yyval.setTipo("Uinteger");}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)val_peek(2).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("-", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) val_peek(0).obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										Main.addTerceto((Terceto)yyval.obj);
										yyval.setTipo("Uinteger");}}}
break;
case 110:
//#line 827 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
					yyval.setTipo(val_peek(0).getTipo());}
break;
case 111:
//#line 830 "gramaticaReentrega.y"
{if ((val_peek(2).getTipo() == "Integer") && (val_peek(0).getTipo() == "Integer")){
									Terceto t1 = new Terceto ("*",(Generador) val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
									t1.setTipo("Integer");
									yyval.obj = t1;
									Main.addTerceto((Terceto)t1);
									yyval.setTipo("Integer");
							}
							else{
								if (val_peek(2).getTipo() == val_peek(0).getTipo()){
									Terceto t1 = new Terceto ("*",(Generador) val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
									t1.setTipo("Uinteger");
									yyval.obj = t1;
									Main.addTerceto((Terceto)t1);
									yyval.setTipo("Uinteger");
								}
								else{
									
									if (val_peek(2).getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)val_peek(0).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("*", (Generador)val_peek(2).obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
									}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)val_peek(2).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("*", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) val_peek(0).obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
										}
									}
								}
							}
break;
case 112:
//#line 867 "gramaticaReentrega.y"
{
							if ((val_peek(2).getTipo() == "Integer") && (val_peek(0).getTipo() == "Integer")){
								Terceto t1 = new Terceto ("/",(Generador) val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
								t1.setTipo ("Integer");
								yyval.obj = t1;
								Main.addTerceto((Terceto)t1);
								yyval.setTipo("Integer");
							}
							else{
								if (val_peek(2).getTipo() == val_peek(0).getTipo()){
									Terceto t1 = new Terceto ("/",(Generador) val_peek(2).obj,(Generador) val_peek(0).obj,Main.getTerceto().size());
									t1.setTipo("Uinteger");
									yyval.obj = t1;
									Main.addTerceto((Terceto)t1);
									yyval.setTipo("Uinteger");}
								else{
									if (val_peek(2).getTipo() == "Uinteger"){
										Main.addTerceto(new Terceto ("inttouint", (Generador)val_peek(0).obj, null, Main.getTerceto().size()));
										Terceto t1 = new Terceto ("/", (Generador)val_peek(2).obj,Main.getTerceto().get(Main.getTerceto().size()-1),Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);}
									else{
										Main.addTerceto (new Terceto ("inttouint", (Generador)val_peek(2).obj, null, Main.getTerceto().size()));
										Terceto t1 =new Terceto ("/", Main.getTerceto().get(Main.getTerceto().size()-1),(Generador) val_peek(0).obj,Main.getTerceto().size());
										t1.setTipo("Uinteger");
										yyval.obj = t1;
										yyval.setTipo("Uinteger");
										Main.addTerceto((Terceto)t1);
										}
									}
								}
							}
break;
case 113:
//#line 901 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
				yyval.setTipo(val_peek(0).getTipo());
				}
break;
case 114:
//#line 905 "gramaticaReentrega.y"
{
							if (val_peek(1).getTipo() == "Uinteger"){
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
								Terceto t=new Terceto ("-", (Generador)val_peek(1).obj, new Token("", "1", Compilador.CONSTANTE_POSITIVA),Main.getTerceto().size());
								t.setTipo("Uinteger");
								Main.addTerceto(t);
/*								Terceto t1 = new Terceto(":=", (Generador)$1.obj, t, Main.getTerceto().size());*/
/*								t1.setTipo("Uinteger");*/
/*								Main.addTerceto((Terceto)t1);*/
/*								$$.obj = t1;*/
								yyval.obj = t;
								yyval.setTipo("Uinteger");
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
								Terceto t=new Terceto ("-", (Generador)val_peek(1).obj, new Token("", "1", Compilador.CONSTANTE_ENTERA),Main.getTerceto().size());
								t.setTipo("Integer");
								Main.addTerceto(t);
/*								Terceto t1 = new Terceto(":=", (Generador)$1.obj, t, Main.getTerceto().size());*/
/*								t1.setTipo("Integer");*/
/*								$$.obj = t1;*/
								yyval.obj = t;
								yyval.setTipo("Integer");
/*								Main.addTerceto((Terceto)t1);*/
							}
						}
break;
case 115:
//#line 959 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
					yyval.setTipo(val_peek(0).getTipo());
					}
break;
case 116:
//#line 964 "gramaticaReentrega.y"
{if (ExisteTipo((Token)val_peek(0).obj, "var@")){
				((Token)val_peek(0).obj).setUso("var@");
				yyval.setTipo((Compilador.get_Tsimbolos(((Token)val_peek(0).obj).getClaveTablaSimboloToken())).getTipo());
				yyval.obj = val_peek(0).obj;
			}else
				yyval.setTipo("");
			}
break;
case 117:
//#line 971 "gramaticaReentrega.y"
{	yyval.obj = val_peek(0).obj;
								yyval.setTipo("Uinteger");}
break;
case 118:
//#line 973 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
							yyval.setTipo("Integer");
							}
break;
case 119:
//#line 976 "gramaticaReentrega.y"
{yyval.obj = val_peek(0).obj;
								yyval.setTipo(val_peek(0).getTipo());
					}
break;
case 120:
//#line 982 "gramaticaReentrega.y"
{comparar = "<";}
break;
case 121:
//#line 983 "gramaticaReentrega.y"
{comparar="<=";}
break;
case 122:
//#line 984 "gramaticaReentrega.y"
{comparar=">";}
break;
case 123:
//#line 985 "gramaticaReentrega.y"
{comparar=">=";}
break;
case 124:
//#line 986 "gramaticaReentrega.y"
{comparar="=";}
break;
case 125:
//#line 987 "gramaticaReentrega.y"
{comparar="!=";}
break;
//#line 1890 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
  }//switch
  //#### Now let's reduce... ####
  if (yydebug) debug("reduce");
  state_drop(yym);             //we just reduced yylen states
  yystate = state_peek(0);     //get new state
  val_drop(yym);               //corresponding value drop
  yym = yylhs[yyn];            //select next TERMINAL(on lhs)
  if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
    {
    if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
    yystate = YYFINAL;         //explicitly say we're done
    state_push(YYFINAL);       //and save it
    val_push(yyval);           //also save the semantic value of parsing
    if (yychar < 0)            //we want another character?
      {
      yychar = yylex();        //get next character
      if (yychar<0) yychar=0;  //clean, if necessary
      if (yydebug)
        yylexdebug(yystate,yychar);
      }
    if (yychar == 0)          //Good exit (if lex returns 0 ;-)
       break;                 //quit the loop--all DONE
    }//if yystate
  else                        //else not done yet
    {                         //get next state and push, for next yydefred[]
    yyn = yygindex[yym];      //find out where to go
    if ((yyn != 0) && (yyn += yystate) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
      yystate = yytable[yyn]; //get new state
    else
      yystate = yydgoto[yym]; //else go to new defred
    if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
    state_push(yystate);     //going again, so push state & val...
    val_push(yyval);         //for next action
    }
  }//main loop
return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
* A default run method, used for operating this parser
* object in the background.  It is intended for extending Thread
* or implementing Runnable.  Turn off with -Jnorun .
 * @throws IOException 
*/
public void run() throws IOException
{
yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
* Default constructor.  Turn off with -Jnoconstruct .

*/
public Parser()
{
//nothing to do
}


/**
* Create a parser, setting the debug to true or false.
* @param debugMe true for debugging, false for no debug.
*/
public Parser(boolean debugMe)
{
yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################

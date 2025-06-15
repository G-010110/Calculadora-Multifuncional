package Calculo;
import java.util.Scanner;
import org.mozilla.javascript.*;;

public class IntLogica {
    private static Scanner teclado = new Scanner(System.in);
    private Context contexto = Context.enter();

    public String integralDConstantein(String funcion) {
        String[] numero={"0","1","2","3","4","5","6","7","8","9"};
        for(String n:numero) {
            if(funcion.contains(n)) {
                funcion=funcion+"x";
                break;
            }
            //System.out.println(palabra.contains(n));
        }
        return funcion;
    }

    public String integralDConstanteDef(String funcion, int a, int b) {
        String[] numero={"0","1","2","3","4","5","6","7","8","9"};
        Object resultado;
        for(String n:numero) {
            if(funcion.contains(n)) {
                funcion=funcion+"x";
                break;
            }
            //System.out.println(palabra.contains(n));
        }
        String evalA=funcion.replace("x","*("+String.valueOf(a)+")");
        String evalB=funcion.replace("x","*("+String.valueOf(b)+")");
        String script ="var fo= "+evalB+"-"+evalA+"; fo;";
            try {
                Scriptable entorno = contexto.initStandardObjects();
                resultado=contexto.evaluateString(entorno, script, "JavaScript",1,null);
            } finally {
                contexto.exit();
            }
        return String.valueOf(resultado);
    }

    public String integralCXin(String funcion) {
        /*Bug sin resolver, cuando ingresamos un mismo numero ejm. 55x^5
         * el resultado es que no solo al exponente se le suma 1 
         */
        String resultado="";
        if(funcion.contains("x^")) {
            int exponente=1+Integer.parseInt(funcion.substring(1+funcion.indexOf("^")));
            String exponenteElevado=funcion.replace(funcion.substring(1+funcion.indexOf("^")),String.valueOf(exponente));
            resultado=exponenteElevado.concat("/"+String.valueOf(exponente));
        return resultado;
        } else if(funcion.contains("x")) {
            resultado=funcion+"^2/2";
        }
        return resultado;
    }
    
    public String integralCXdef(String funcion, int a, int b) {
        String resultado="";
        Object resultado2;
        if(funcion.contains("x^")) {
            int exponente=1+Integer.parseInt(funcion.substring(1+funcion.indexOf("^")));
            String exponenteElevado=funcion.replace(funcion.substring(1+funcion.indexOf("^")),String.valueOf(exponente));
            resultado=exponenteElevado.concat("/"+String.valueOf(exponente));
            
            //String valToExponer = resultado.substring(0, resultado.indexOf("x"));
            String valExpon=resultado.substring(1+funcion.indexOf("^"), resultado.indexOf("/"));
            String evalA=resultado.replace(resultado.substring(funcion.indexOf("x"), resultado.indexOf("/")), "*Math.pow("+String.valueOf(a)+","+valExpon+")");
            String evalB=resultado.replace(resultado.substring(funcion.indexOf("x"), resultado.indexOf("/")), "*Math.pow("+String.valueOf(b)+","+valExpon+")");
            
            String script ="var fo= "+evalB+"-"+evalA+"; fo;";
            try {
                Scriptable entorno = contexto.initStandardObjects();
                resultado2=contexto.evaluateString(entorno, script, "JavaScript",1,null);
            } finally {
                contexto.exit();
            }
            return String.valueOf(resultado2);
        } else if(funcion.contains("x")) {
            resultado=funcion+"^2/2";
            
            String valExpon=resultado.substring(1+funcion.indexOf("^"), resultado.indexOf("/"));
            String evalA=resultado.replace(resultado.substring(funcion.indexOf("x"), resultado.indexOf("/")), "*Math.pow("+String.valueOf(a)+","+valExpon+")");
            String evalB=resultado.replace(resultado.substring(funcion.indexOf("x"), resultado.indexOf("/")), "*Math.pow("+String.valueOf(b)+","+valExpon+")");
            
            String script ="var fo= "+evalB+"-"+evalA+"; fo;";
            try {
                Scriptable entorno = contexto.initStandardObjects();
                resultado2=contexto.evaluateString(entorno, script, "JavaScript",1,null);
            } finally {
                contexto.exit();
            }
            return script;
        }
        return "";
        //
    }

    public String metodoBusqueda(int tipo, String funcion, int a, int b) {
        //tipo 0: indefinida, tipo 1 definida
    	String r="";
        if(funcion.contains("x")) 
            if(tipo==0)
                r=integralCXin(funcion);
            else
                r=integralCXdef(funcion,a,b);
        else 
        	if(tipo==0)
        		r=integralDConstantein(funcion);
        	else
        		r=integralDConstanteDef(funcion,a,b);
        return r;
    }

    public static void main(String args[]) {
    	IntLogica integral = new IntLogica();
    	System.out.print("f(x)= ");
    	String f=teclado.next();
    	System.out.println(integral.integralCXdef(f,0,2));
    	//System.out.println("Resultado: "+integral.integralDConstanteDef(f,0,2)+" + C");
    	//System.out.println(integral.integralCX(inf));
    }
    /* */
}
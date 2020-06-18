package cl.tesoreria; 

import java.io.Serializable;

public class ItemRenta implements Serializable 
{     
	private static final long serialVersionUID = 4149048104777122302L;
	protected String   codigo;         // Código del Item
    protected String   signo;         // Código del Item
    protected String   contenido;          // Valor del Item
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getCodigo() {
        return this.codigo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public String getContenido() {
        return this.contenido;
    }


    public void setSigno(String signo) {
        this.signo = signo;
    }
    public String getSigno() {
        return this.signo;
    }
} 

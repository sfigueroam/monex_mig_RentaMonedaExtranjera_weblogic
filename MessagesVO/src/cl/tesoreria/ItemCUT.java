package cl.tesoreria; 

import java.io.Serializable;

public class ItemCUT implements Serializable
{     
	private static final long serialVersionUID = 5383189729201906995L;
	protected int      codigo;         // Código del Item
    protected String   tipoDato;         // Código del Item
    protected String   valor;          // Valor del Item
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getValor() {
        return valor;
    } 
    public String getTipoDato() {
        return tipoDato;
    } 
} 

package cl.tesoreria.finanzas; 
public class RentaMEGenericException extends Exception {
	
	private static final long serialVersionUID = 6738959194797626273L;
	private int typeError;
    
    /*
        1=Error interno TGR
        2=Estructura XML es invalida    
    */

	public RentaMEGenericException(int type) {
		// TODO Auto-generated constructor stub
		this.typeError=type;
	}

	public RentaMEGenericException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RentaMEGenericException(String arg0) {
        super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RentaMEGenericException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public int getTypeError() {
		return typeError;
	}

	public void setTypeError(int typeError) {
		this.typeError = typeError;
	}

}

package cl.tesoreria.finanzas; 

public class RentaMException extends Exception 
{ 
    
	private static final long serialVersionUID = -6798105827234786064L;
	private String className;
    private String methodName;
    private String glosaErr;
    private String valueErr;
    
    public RentaMException() {
		// TODO Auto-generated constructor stub
	}
    
    public RentaMException(String className,String methodName,String valueErr,String glosaErr){
       this.className=className;
        this.methodName=methodName;
        this.glosaErr=glosaErr;
        this.valueErr=valueErr; 
        

        //this.glosaErr=g0.getMessage();
    }
    

    
    public RentaMException(Throwable arg0, String className,String methodName,String glosaErr){
        super(arg0);
        this.className=className;
        this.methodName=methodName;
        this.glosaErr=glosaErr;
        this.valueErr=arg0.getMessage();        
    }
    
    

	public RentaMException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RentaMException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RentaMException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
    
    
    public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
    
    public String getGlosaErr() {
		return glosaErr;
	}

	public void setGlosaErr(String glosa) {
		this.glosaErr = glosa;
	}

    public String getValueErr() {
		return valueErr;
	}

	public void setValueErr(String value) {
		this.valueErr = value;
	}
	

} 

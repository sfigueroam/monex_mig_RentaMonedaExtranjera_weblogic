package cl.tesoreria; 

import java.io.Serializable;

public class AdfResult implements Serializable
{     
	private static final long serialVersionUID = -1876922993062695692L;
	protected String module;
	protected String severity;
	protected String number;
	protected String objectName;
	protected String objectValue;
	protected String description;
	protected String objectDesc;
	protected String formIndex;
    
    public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObjectDesc() {
		return objectDesc;
	}

	public void setObjectDesc(String objectDesc) {
		this.objectDesc = objectDesc;
	}

	public String getFormIndex() {
		return formIndex;
	}

	public void setFormIndex(String formIndex) {
		this.formIndex = formIndex;
	}
    
} 

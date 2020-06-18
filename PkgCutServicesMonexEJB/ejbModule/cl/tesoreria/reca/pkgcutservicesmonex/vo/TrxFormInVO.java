package cl.tesoreria.reca.pkgcutservicesmonex.vo; 
import java.io.Serializable; 
import java.math.BigDecimal; 
import java.util.Date; 

public class TrxFormInVO implements Serializable { 
  /** 
  * 
  */ 
  private static final long serialVersionUID = 1L; 

  private String inUser; 
  private BigDecimal inRutIra; 
  private String inRutIraDv; 
  private BigDecimal inFormTipo; 
  private String inFormVer; 
  private String inItems; 
  private String inIdOrigen; 
  private Date inFechaOrigen; 
  private Date inFechaCaja; 
  private BigDecimal inLoteCanal; 
  private BigDecimal inLoteTipo; 
  private BigDecimal inCutMovEstado; 
  private String inCommitT; 
  private String inLabel; 
 

  public TrxFormInVO(){ 
  } 


  public void setInUser(String newInUser){ 
    this.inUser = newInUser; 
  } 

  public String getInUser(){ 
    return this.inUser; 
  } 


  public void setInRutIra(BigDecimal newInRutIra){ 
    this.inRutIra = newInRutIra; 
  } 

  public BigDecimal getInRutIra(){ 
    return this.inRutIra; 
  } 


  public void setInRutIraDv(String newInRutIraDv){ 
    this.inRutIraDv = newInRutIraDv; 
  } 

  public String getInRutIraDv(){ 
    return this.inRutIraDv; 
  } 


  public void setInFormTipo(BigDecimal newInFormTipo){ 
    this.inFormTipo = newInFormTipo; 
  } 

  public BigDecimal getInFormTipo(){ 
    return this.inFormTipo; 
  } 


  public void setInFormVer(String newInFormVer){ 
    this.inFormVer = newInFormVer; 
  } 

  public String getInFormVer(){ 
    return this.inFormVer; 
  } 


  public void setInItems(String newInItems){ 
    this.inItems = newInItems; 
  } 

  public String getInItems(){ 
    return this.inItems; 
  } 


  public void setInIdOrigen(String newInIdOrigen){ 
    this.inIdOrigen = newInIdOrigen; 
  } 

  public String getInIdOrigen(){ 
    return this.inIdOrigen; 
  } 


  public void setInFechaOrigen(Date newInFechaOrigen){ 
    this.inFechaOrigen = newInFechaOrigen; 
  } 

  public Date getInFechaOrigen(){ 
    return this.inFechaOrigen; 
  } 


  public void setInFechaCaja(Date newInFechaCaja){ 
    this.inFechaCaja = newInFechaCaja; 
  } 

  public Date getInFechaCaja(){ 
    return this.inFechaCaja; 
  } 


  public void setInLoteCanal(BigDecimal newInLoteCanal){ 
    this.inLoteCanal = newInLoteCanal; 
  } 

  public BigDecimal getInLoteCanal(){ 
    return this.inLoteCanal; 
  } 


  public void setInLoteTipo(BigDecimal newInLoteTipo){ 
    this.inLoteTipo = newInLoteTipo; 
  } 

  public BigDecimal getInLoteTipo(){ 
    return this.inLoteTipo; 
  } 


  public void setInCutMovEstado(BigDecimal newInCutMovEstado){ 
    this.inCutMovEstado = newInCutMovEstado; 
  } 

  public BigDecimal getInCutMovEstado(){ 
    return this.inCutMovEstado; 
  } 


  public void setInCommitT(String newInCommitT){ 
    this.inCommitT = newInCommitT; 
  } 

  public String getInCommitT(){ 
    return this.inCommitT; 
  } 


  public void setInLabel(String newInLabel){ 
    this.inLabel = newInLabel; 
  } 

  public String getInLabel(){ 
    return this.inLabel; 
  } 


} 

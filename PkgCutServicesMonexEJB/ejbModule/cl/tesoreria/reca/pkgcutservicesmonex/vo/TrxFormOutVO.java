package cl.tesoreria.reca.pkgcutservicesmonex.vo; 
import java.io.Serializable; 
import java.math.BigDecimal; 
import java.util.Collection; 

public class TrxFormOutVO implements Serializable { 
  /** 
  * 
  */ 
  private static final long serialVersionUID = 1L; 

  private BigDecimal outErrlvl; 
  private String outMensajes; 
  private BigDecimal outTrnTranId; 
  private Collection<TrxFormOutRecaMsgVO> outRecaMsg; 
 

  public TrxFormOutVO(){ 
  } 


  public void setOutErrlvl(BigDecimal newOutErrlvl){ 
    this.outErrlvl = newOutErrlvl; 
  } 

  public BigDecimal getOutErrlvl(){ 
    return this.outErrlvl; 
  } 


  public void setOutMensajes(String newOutMensajes){ 
    this.outMensajes = newOutMensajes; 
  } 

  public String getOutMensajes(){ 
    return this.outMensajes; 
  } 


  public void setOutTrnTranId(BigDecimal newOutTrnTranId){ 
    this.outTrnTranId = newOutTrnTranId; 
  } 

  public BigDecimal getOutTrnTranId(){ 
    return this.outTrnTranId; 
  } 


  public void setOutRecaMsg(Collection<TrxFormOutRecaMsgVO> newOutRecaMsg){ 
    this.outRecaMsg = newOutRecaMsg; 
  } 

  public Collection<TrxFormOutRecaMsgVO> getOutRecaMsg(){ 
    return this.outRecaMsg; 
  } 


} 

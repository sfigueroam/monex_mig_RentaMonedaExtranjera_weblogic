package cl.tesoreria.reca.pkgcutservicesmonex.dao; 
import java.sql.CallableStatement; 
import java.sql.SQLException;
import java.sql.Connection; 
import java.io.Serializable; 
import cl.tesoreria.utiles.dao.OracleDAOV2; 
import cl.tesoreria.reca.pkgcutservicesmonex.vo.*; 
import cl.tesoreria.utiles.format.FormatosFechasNew; 
import java.sql.Types; 
import cl.tesoreria.utiles.sql.TypesExt; 
import java.util.Collection;
import java.util.ArrayList;
import java.sql.ResultSet;

public class TrxFormDAO extends OracleDAOV2 { 

  public String callProcedure(){ 
    return "{ call RECA.PKG_CUT_SERVICES_MONEX.TRX_FORM ( ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ?  , ? )  }"; 
  } 

  public Serializable fillStatement(CallableStatement cstmt,Connection conn) throws SQLException{ 

    TrxFormInVO voIn = (TrxFormInVO) entrada; 
    TrxFormOutVO voOut =  new TrxFormOutVO(); 
    cstmt.setString(1,voIn.getInUser()); 
    cstmt.setBigDecimal(2,voIn.getInRutIra()); 
    cstmt.setString(3,voIn.getInRutIraDv()); 
    cstmt.setBigDecimal(4,voIn.getInFormTipo()); 
    cstmt.setString(5,voIn.getInFormVer()); 
    cstmt.setString(6,voIn.getInItems()); 
    cstmt.setString(7,voIn.getInIdOrigen()); 
    cstmt.setTimestamp(8,FormatosFechasNew.transformarDateToTimestamp(voIn.getInFechaOrigen())); 
    cstmt.setTimestamp(9,FormatosFechasNew.transformarDateToTimestamp(voIn.getInFechaCaja())); 
    cstmt.setBigDecimal(10,voIn.getInLoteCanal()); 
    cstmt.setBigDecimal(11,voIn.getInLoteTipo()); 
    cstmt.setBigDecimal(12,voIn.getInCutMovEstado()); 
    cstmt.setString(13,voIn.getInCommitT()); 
    cstmt.setString(14,voIn.getInLabel()); 
    cstmt.registerOutParameter(15,Types.NUMERIC); 
    cstmt.registerOutParameter(16,Types.VARCHAR); 
    cstmt.registerOutParameter(17,Types.NUMERIC); 
    cstmt.registerOutParameter(18,TypesExt.CURSOR); 
    cstmt.execute(); 
    voOut.setOutErrlvl(cstmt.getBigDecimal(15)); 
    voOut.setOutMensajes(cstmt.getString(16)); 
    voOut.setOutTrnTranId(cstmt.getBigDecimal(17)); 
    voOut.setOutRecaMsg(this.pojoFillCollectionResultSet1(cstmt.getObject(18))); 
    return voOut; 
  } 


  protected Collection<TrxFormOutRecaMsgVO> pojoFillCollectionResultSet1(Object obj) throws SQLException
  {

    ResultSet rs = null;
    Collection<TrxFormOutRecaMsgVO> lista = new ArrayList<TrxFormOutRecaMsgVO>();
    try {
      rs = (ResultSet) obj;
      TrxFormOutRecaMsgVO vo;
      while (rs.next()) {
        vo = new TrxFormOutRecaMsgVO();
        vo.setTipo(rs.getBigDecimal("TIPO"));
        vo.setCodigo(rs.getBigDecimal("CODIGO"));
        vo.setSeveridad(rs.getBigDecimal("SEVERIDAD"));
        vo.setGlosa(rs.getString("GLOSA"));
        vo.setErrcode(rs.getBigDecimal("ERRCODE"));
        vo.setErrorTgr(rs.getBigDecimal("ERROR_TGR"));
        vo.setErrmsg(rs.getString("ERRMSG"));
        vo.setObjname(rs.getString("OBJNAME"));
        vo.setObjvalue(rs.getString("OBJVALUE"));
        vo.setObjdescrip(rs.getString("OBJDESCRIP"));
        lista.add(vo);
      }
    } finally {
      rs.close();
    }
    return lista;

  }


} 

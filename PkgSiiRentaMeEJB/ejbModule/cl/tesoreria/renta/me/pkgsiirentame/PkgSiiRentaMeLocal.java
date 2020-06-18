package cl.tesoreria.renta.me.pkgsiirentame; 

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Local;

@Local
public interface PkgSiiRentaMeLocal {

    public ActRectificadaCtaByLlaveResult actRectificadaCtaByLlave(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin, Date fechavenc)
        throws PkgSiiRentaMeException;
    public ActRectificadaCtaByLlave2Result actRectificadaCtaByLlave2(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin)
        throws PkgSiiRentaMeException;
    public MyfunctionResult myfunction(BigDecimal param1)
        throws PkgSiiRentaMeException;
    public MyprocedureResult myprocedure(BigDecimal param1)
        throws PkgSiiRentaMeException;
    public SpBuscarcodigoobbymovResult spBuscarcodigoobbymov(BigDecimal idmov)
        throws PkgSiiRentaMeException;
    public SpBuscarcontitembyidmovResult spBuscarcontitembyidmov(BigDecimal idmov)
        throws PkgSiiRentaMeException;
    public SpBuscaridcuentacutbyformResult spBuscaridcuentacutbyform(BigDecimal rut, String dv, BigDecimal formtipo, BigDecimal folio, Date fechvto)
        throws PkgSiiRentaMeException;
    public SpBuscaridmonedabynemoResult spBuscaridmonedabynemo(String nemos)
        throws PkgSiiRentaMeException;
    public SpBuscaridtipomovbynemoResult spBuscaridtipomovbynemo(String descripnemo)
        throws PkgSiiRentaMeException;
    public SpBuscarmovrecmasmebyformResult spBuscarmovrecmasmebyform(BigDecimal idcta)
        throws PkgSiiRentaMeException;
    public SpExisteCuentabyfolioandrutResult spExisteCuentabyfolioandrut(BigDecimal rut, String dv, BigDecimal tipoform, BigDecimal folioform, Date fechvto)
        throws PkgSiiRentaMeException;
    public SpExisteMovByLlaveResult spExisteMovByLlave(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin, Date fechavenc)
        throws PkgSiiRentaMeException;
    public SpExisteMovByLlave2Result spExisteMovByLlave2(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin)
        throws PkgSiiRentaMeException;
    public SpExisteRegistradoFormmovtoResult spExisteRegistradoFormmovto(BigDecimal numbertrx)
        throws PkgSiiRentaMeException;
    public SpFindMsgmovByLlaveResult spFindMsgmovByLlave(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin, Date fechavenc)
        throws PkgSiiRentaMeException;
    public SpFindMsgmovByLlave2Result spFindMsgmovByLlave2(BigDecimal rutin, String dvin, BigDecimal formtipoin, BigDecimal folioin)
        throws PkgSiiRentaMeException;
    public SpGenerarfolio01meResult spGenerarfolio01me(BigDecimal formulario, BigDecimal fech315, BigDecimal fech9927)
        throws PkgSiiRentaMeException;
    public SpInsertErrorMsgMeResult spInsertErrorMsgMe(String messageid, BigDecimal errorindex, BigDecimal numbererror, BigDecimal severity, String description, BigDecimal formindex, BigDecimal codigoindex, String contenido, String modulename, String objectname, String objectdesc, BigDecimal periodocontable)
        throws PkgSiiRentaMeException;
    public SpInsertMsgRentaMeResult spInsertMsgRentaMe(String messageid, String code, Date fechain, BigDecimal identif, BigDecimal rutcontr, BigDecimal formulario, BigDecimal foliodecl, BigDecimal periodocont, String status, String xml, BigDecimal fecha9927)
        throws PkgSiiRentaMeException;
    public SpRegistraCuentaCutResult spRegistraCuentaCut(BigDecimal rut, String dv, BigDecimal formtipo, BigDecimal folio, BigDecimal instit, Date periodo, Date fechaCrea, Date fechaAct, Date fechaVto, BigDecimal moneda, BigDecimal saldo, String recti)
        throws PkgSiiRentaMeException;
    public SpRegistraItemCutResult spRegistraItemCut(BigDecimal movid, BigDecimal codigo, String datoTipo, BigDecimal estado, String valor)
        throws PkgSiiRentaMeException;
    public SpRegistraMovimientoCutResult spRegistraMovimientoCut(BigDecimal cuenta, BigDecimal numtrx, BigDecimal movtipo, Date fecha, Date fechaVto, BigDecimal formtipo, String formver, BigDecimal folio, BigDecimal moneda, BigDecimal monto, BigDecimal montopesos, BigDecimal valorcambio, BigDecimal estado, BigDecimal rutira, String dvira, BigDecimal fol01, BigDecimal cod9927, BigDecimal montoret, BigDecimal montoimp, BigDecimal montopag, Date periodocont)
        throws PkgSiiRentaMeException;
    public BuscarCutItemsResult buscarCutItems (BigDecimal idMov)
        throws PkgSiiRentaMeException;
    public BigDecimal validaNumber (BigDecimal numberXML,
                                    BigDecimal rut,
                                    BigDecimal folio,
                                    BigDecimal formulario)
        throws PkgSiiRentaMeException;
    public BigDecimal validaLabel (String code)
        throws PkgSiiRentaMeException;
    
    public InsertItemsByCollResult insertItemsByColl(ItemCut[] itmCol) throws PkgSiiRentaMeException;
    public RegistrarFomulariosResult registrarFomularios(Formulario[] formucoll) throws PkgSiiRentaMeException;
    

}

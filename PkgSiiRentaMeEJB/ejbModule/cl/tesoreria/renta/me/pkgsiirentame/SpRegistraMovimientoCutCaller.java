/*
 * File: SpRegistraMovimientoCutCaller.java  2008/10/09 10:09:45
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:3yBum-Q9zVQRlrtb7iG75f>>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implements a caller of procedure "PKG_SII_RENTA_ME.SP_REGISTRA_MOVIMIENTO_CUT".
 */
public class SpRegistraMovimientoCutCaller extends ProcedureCaller
{
    /**
     * Executes procedure "PKG_SII_RENTA_ME.SP_REGISTRA_MOVIMIENTO_CUT".
     *
     * CUENTA             NUMBER             Input
     * NUMTRX             NUMBER             Input
     * MOVTIPO            NUMBER             Input
     * FECHA              DATE               Input
     * FECHA_VTO          DATE               Input
     * FORMTIPO           NUMBER             Input
     * FORMVER            CHAR(2000)         Input
     * FOLIO              NUMBER             Input
     * MONEDA             NUMBER             Input
     * MONTO              NUMBER             Input
     * MONTOPESOS         NUMBER             Input
     * VALORCAMBIO        NUMBER             Input
     * ESTADO             NUMBER             Input
     * RUTIRA             NUMBER             Input
     * DVIRA              VARCHAR2(4000)     Input
     * FOL01              NUMBER             Input
     * COD9927            NUMBER             Input
     * MONTORET           NUMBER             Input
     * MONTOIMP           NUMBER             Input
     * MONTOPAG           NUMBER             Input
     * PERIODOCONT        DATE               Input
     * RESPOUT            NUMBER             Output
     */
    public static SpRegistraMovimientoCutResult execute(Connection conn, BigDecimal cuenta, BigDecimal numtrx, BigDecimal movtipo, Date fecha, Date fechaVto, BigDecimal formtipo, String formver, BigDecimal folio, BigDecimal moneda, BigDecimal monto, BigDecimal montopesos, BigDecimal valorcambio, BigDecimal estado, BigDecimal rutira, String dvira, BigDecimal fol01, BigDecimal cod9927, BigDecimal montoret, BigDecimal montoimp, BigDecimal montopag, Date periodocont)
        throws java.sql.SQLException
    {
        SpRegistraMovimientoCutResult result = new SpRegistraMovimientoCutResult();
        ArrayList resultSets = new ArrayList();
        CallableStatement call = conn.prepareCall("{call SII.PKG_SII_RENTA_ME.SP_REGISTRA_MOVIMIENTO_CUT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        try
        {
            call.setBigDecimal(1, cuenta);
            call.setBigDecimal(2, numtrx);
            call.setBigDecimal(3, movtipo);
            call.setTimestamp(4, toTimestamp(fecha));
            call.setTimestamp(5, toTimestamp(fechaVto));
            call.setBigDecimal(6, formtipo);
            call.setString(7, formver);
            call.setBigDecimal(8, folio);
            call.setBigDecimal(9, moneda);
            call.setBigDecimal(10, monto);
            call.setBigDecimal(11, montopesos);
            call.setBigDecimal(12, valorcambio);
            call.setBigDecimal(13, estado);
            call.setBigDecimal(14, rutira);
            call.setString(15, dvira);
            call.setBigDecimal(16, fol01);
            call.setBigDecimal(17, cod9927);
            call.setBigDecimal(18, montoret);
            call.setBigDecimal(19, montoimp);
            call.setBigDecimal(20, montopag);
            call.setTimestamp(21, toTimestamp(periodocont));
            call.registerOutParameter(22, Types.NUMERIC);
            int updateCount = 0;
            boolean haveRset = call.execute();
            while (haveRset || updateCount != -1)
            {
                if (!haveRset)
                    updateCount = call.getUpdateCount();
                else
                    resultSets.add(toProcedureTable(call.getResultSet()));
                haveRset = call.getMoreResults();
            }
            result.setRespout(call.getBigDecimal(22));
        }
        finally
        {
            call.close();
        }
        if (resultSets.size() > 0)
        {
            ProcedureTable[] tables = new ProcedureTable[resultSets.size()];
            result.setProcedureTables((ProcedureTable[]) resultSets.toArray(tables));
        }
        return result;
    }
}
/*
 * File: SpBuscarcontitembyidmovCaller.java  2008/10/09 10:09:45
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:OuweYn2Om8Qz7sjb7wU7ib>>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Implements a caller of procedure "PKG_SII_RENTA_ME.SP_BUSCARCONTITEMBYIDMOV".
 */
public class SpBuscarcontitembyidmovCaller extends ProcedureCaller
{
    /**
     * Executes procedure "PKG_SII_RENTA_ME.SP_BUSCARCONTITEMBYIDMOV".
     *
     * IDMOV              NUMBER             Input
     * CONT82             VARCHAR2(4000)     Output
     */
    public static SpBuscarcontitembyidmovResult execute(Connection conn, BigDecimal idmov)
        throws java.sql.SQLException
    {
        SpBuscarcontitembyidmovResult result = new SpBuscarcontitembyidmovResult();
        ArrayList resultSets = new ArrayList();
        CallableStatement call = conn.prepareCall("{call SII.PKG_SII_RENTA_ME.SP_BUSCARCONTITEMBYIDMOV(?,?)}");
        try
        {
            call.setBigDecimal(1, idmov);
            call.registerOutParameter(2, Types.VARCHAR);
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
            result.setCont82(call.getString(2));
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

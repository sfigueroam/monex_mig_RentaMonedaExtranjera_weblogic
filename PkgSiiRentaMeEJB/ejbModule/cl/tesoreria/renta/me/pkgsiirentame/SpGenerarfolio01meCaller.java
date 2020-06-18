/*
 * File: SpGenerarfolio01meCaller.java  2008/10/09 10:09:45
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:tQBTi-H3ZAScqwMEQMOqWv>>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Implements a caller of procedure "PKG_SII_RENTA_ME.SP_GENERARFOLIO01ME".
 */
public class SpGenerarfolio01meCaller extends ProcedureCaller
{
    /**
     * Executes procedure "PKG_SII_RENTA_ME.SP_GENERARFOLIO01ME".
     *
     * FORMULARIO         NUMBER             Input
     * FECH315            NUMBER             Input
     * FECH9927           NUMBER             Input
     * FOL01OUT           NUMBER             Output
     */
    public static SpGenerarfolio01meResult execute(Connection conn, BigDecimal formulario, BigDecimal fech315, BigDecimal fech9927)
        throws java.sql.SQLException
    {
        SpGenerarfolio01meResult result = new SpGenerarfolio01meResult();
        ArrayList resultSets = new ArrayList();
        CallableStatement call = conn.prepareCall("{call SII.PKG_SII_RENTA_ME.SP_GENERARFOLIO01ME(?,?,?,?)}");
        try
        {
            call.setBigDecimal(1, formulario);
            call.setBigDecimal(2, fech315);
            call.setBigDecimal(3, fech9927);
            call.registerOutParameter(4, Types.NUMERIC);
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
            result.setFol01out(call.getBigDecimal(4));
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

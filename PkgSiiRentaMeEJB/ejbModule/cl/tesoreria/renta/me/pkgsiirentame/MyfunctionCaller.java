/*
 * File: MyfunctionCaller.java  2008/10/09 10:09:44
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:C5ykU4FyFBha4hcJ9xWn0s>>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Implements a caller of procedure "PKG_SII_RENTA_ME.MYFUNCTION".
 */
public class MyfunctionCaller extends ProcedureCaller
{
    /**
     * Executes procedure "PKG_SII_RENTA_ME.MYFUNCTION".
     *
     * RETURN_VALUE       NUMBER             Return
     * PARAM1             NUMBER             Input
     */
    public static MyfunctionResult execute(Connection conn, BigDecimal param1)
        throws java.sql.SQLException
    {
        MyfunctionResult result = new MyfunctionResult();
        ArrayList resultSets = new ArrayList();
        CallableStatement call = conn.prepareCall("{?=call SII.PKG_SII_RENTA_ME.MYFUNCTION(?)}");
        try
        {
            call.registerOutParameter(1, Types.NUMERIC);
            call.setBigDecimal(2, param1);
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
            result.setReturnValue(call.getBigDecimal(1));
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

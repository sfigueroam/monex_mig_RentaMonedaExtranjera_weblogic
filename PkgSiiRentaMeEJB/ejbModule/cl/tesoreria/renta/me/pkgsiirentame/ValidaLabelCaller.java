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
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import javax.sql.RowSet;

/**
 * Implements a caller of procedure "PKG_SII_RENTA_ME.buscar_cut_items".
 */
public class ValidaLabelCaller extends ProcedureCaller
{
    /**
     * Executes procedure "PKG_SII_RENTA_ME.validaLabel 
     * 
     * code_in IN number
     * retorno_out OUT Number
     * 
     */
    public static BigDecimal execute(Connection conn, 
                                     String code) throws java.sql.SQLException
    {
        BigDecimal retorno = null;
        CallableStatement call = conn.prepareCall("{call SII.PKG_SII_RENTA_ME.validaLabel(?,?)}");
        try
        {
            call.setString(1, code);
            call.registerOutParameter(2, Types.NUMERIC);
            call.execute();
            retorno = (BigDecimal) call.getObject(2);
        }
        finally
        {
            call.close();
        }
        return retorno;
    }
}

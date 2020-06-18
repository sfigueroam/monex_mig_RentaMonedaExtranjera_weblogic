/*
 * File: ProcessResult.java  2008/05/09 10:54:31
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.228.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:vSv1A4y4WAMCuXFCD5DfjK>>.
 */

package cl.tesoreria.adf.adfprocedure;

import java.math.BigDecimal;
import javax.sql.RowSet;

/**
 * Output parameters of procedure "DISPATCH$PROCESS".
 */
public class ProcessResult implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private String myFaultString;
    private String myItemsOut;
    private String myContexttgfout;
    private String myMessagestgf;
    private BigDecimal myResultado;
    private RowSet[] myRowSets;

    /**
     * Constructor.
     */
    public ProcessResult()
    {
    }

    /**
     * Returns the value of the "FaultString" field.
     */
    public String getFaultString()
    {
        return myFaultString;
    }

    /**
     * Changes the value of the "FaultString" field.
     */
    public void setFaultString(String value)
    {
        myFaultString = value;
    }

    /**
     * Returns the value of the "ItemsOut" field.
     */
    public String getItemsOut()
    {
        return myItemsOut;
    }

    /**
     * Changes the value of the "ItemsOut" field.
     */
    public void setItemsOut(String value)
    {
        myItemsOut = value;
    }

    /**
     * Returns the value of the "Contexttgfout" field.
     */
    public String getContexttgfout()
    {
        return myContexttgfout;
    }

    /**
     * Changes the value of the "Contexttgfout" field.
     */
    public void setContexttgfout(String value)
    {
        myContexttgfout = value;
    }

    /**
     * Returns the value of the "Messagestgf" field.
     */
    public String getMessagestgf()
    {
        return myMessagestgf;
    }

    /**
     * Changes the value of the "Messagestgf" field.
     */
    public void setMessagestgf(String value)
    {
        myMessagestgf = value;
    }

    /**
     * Returns the value of the "Resultado" field.
     */
    public BigDecimal getResultado()
    {
        return myResultado;
    }

    /**
     * Changes the value of the "Resultado" field.
     */
    public void setResultado(BigDecimal value)
    {
        myResultado = value;
    }

    /**
     * Changes the value of the "RowSets" field.
     */
    void setRowSets(RowSet[] value)
    {
        myRowSets = value;
    }

    /**
     * Returns the value of the "RowSetCount" field.
     */
    public int getRowSetCount()
    {
        return (myRowSets != null) ? myRowSets.length : 0;
    }

    /**
     * Returns the "RowSet" at the specified index.
     */
    public RowSet getRowSet(int index)
    {
        if (index < 0 || index >= getRowSetCount())
            throw new ArrayIndexOutOfBoundsException(index);
        return myRowSets[index];
    }
}
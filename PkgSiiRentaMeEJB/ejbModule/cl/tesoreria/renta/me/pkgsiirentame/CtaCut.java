/*
 * File: CtaCut.java  2008/10/09 10:09:46
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:EoyF1F2xTRE1M5lYZbDqq->>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Implements the "CTA_CUT" user-defined object.
 */
public class CtaCut implements Serializable
{
    private static final long serialVersionUID = 1L;

    // Fields of the user-defined object
    private BigDecimal  myRut;        // NUMBER
    private String      myDv;         // CHAR(1)
    private BigDecimal  myFormtipo;   // NUMBER
    private BigDecimal  myFolio;      // NUMBER
    private BigDecimal  myInstit;     // NUMBER
    private Date        myPeriodo;    // DATE
    private Date        myFechaCrea;  // DATE
    private Date        myFechaAct;   // DATE
    private Date        myFechaVto;   // DATE
    private BigDecimal  myMoneda;     // NUMBER
    private BigDecimal  mySaldo;      // NUMBER
    private String      myRecti;      // VARCHAR2(2)

    /**
     * Constructor.
     */
    public CtaCut()
    {
    }

    /**
     * Gets the RUT value of this CtaCut.
     */
    public BigDecimal getRut()
    {
        return myRut;
    }

    /**
     * Sets the RUT value of this CtaCut.
     */
    public void setRut(BigDecimal value)
    {
        myRut = value;
    }

    /**
     * Gets the DV value of this CtaCut.
     */
    public String getDv()
    {
        return myDv;
    }

    /**
     * Sets the DV value of this CtaCut.
     */
    public void setDv(String value)
    {
        myDv = value;
    }

    /**
     * Gets the FORMTIPO value of this CtaCut.
     */
    public BigDecimal getFormtipo()
    {
        return myFormtipo;
    }

    /**
     * Sets the FORMTIPO value of this CtaCut.
     */
    public void setFormtipo(BigDecimal value)
    {
        myFormtipo = value;
    }

    /**
     * Gets the FOLIO value of this CtaCut.
     */
    public BigDecimal getFolio()
    {
        return myFolio;
    }

    /**
     * Sets the FOLIO value of this CtaCut.
     */
    public void setFolio(BigDecimal value)
    {
        myFolio = value;
    }

    /**
     * Gets the INSTIT value of this CtaCut.
     */
    public BigDecimal getInstit()
    {
        return myInstit;
    }

    /**
     * Sets the INSTIT value of this CtaCut.
     */
    public void setInstit(BigDecimal value)
    {
        myInstit = value;
    }

    /**
     * Gets the PERIODO value of this CtaCut.
     */
    public Date getPeriodo()
    {
        return myPeriodo;
    }

    /**
     * Sets the PERIODO value of this CtaCut.
     */
    public void setPeriodo(Date value)
    {
        myPeriodo = value;
    }

    /**
     * Gets the FECHA_CREA value of this CtaCut.
     */
    public Date getFechaCrea()
    {
        return myFechaCrea;
    }

    /**
     * Sets the FECHA_CREA value of this CtaCut.
     */
    public void setFechaCrea(Date value)
    {
        myFechaCrea = value;
    }

    /**
     * Gets the FECHA_ACT value of this CtaCut.
     */
    public Date getFechaAct()
    {
        return myFechaAct;
    }

    /**
     * Sets the FECHA_ACT value of this CtaCut.
     */
    public void setFechaAct(Date value)
    {
        myFechaAct = value;
    }

    /**
     * Gets the FECHA_VTO value of this CtaCut.
     */
    public Date getFechaVto()
    {
        return myFechaVto;
    }

    /**
     * Sets the FECHA_VTO value of this CtaCut.
     */
    public void setFechaVto(Date value)
    {
        myFechaVto = value;
    }

    /**
     * Gets the MONEDA value of this CtaCut.
     */
    public BigDecimal getMoneda()
    {
        return myMoneda;
    }

    /**
     * Sets the MONEDA value of this CtaCut.
     */
    public void setMoneda(BigDecimal value)
    {
        myMoneda = value;
    }

    /**
     * Gets the SALDO value of this CtaCut.
     */
    public BigDecimal getSaldo()
    {
        return mySaldo;
    }

    /**
     * Sets the SALDO value of this CtaCut.
     */
    public void setSaldo(BigDecimal value)
    {
        mySaldo = value;
    }

    /**
     * Gets the RECTI value of this CtaCut.
     */
    public String getRecti()
    {
        return myRecti;
    }

    /**
     * Sets the RECTI value of this CtaCut.
     */
    public void setRecti(String value)
    {
        myRecti = value;
    }
}

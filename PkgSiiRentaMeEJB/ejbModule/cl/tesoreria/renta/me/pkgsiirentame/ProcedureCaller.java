/*
 * File: ProcedureCaller.java  2008/10/09 10:09:46
 * User: Italo Maragliano Gonzalez (Tesoreria General de la Republica)
 *
 * This file was generated by "OBCOM SQL Wizard" version 5.1.239.
 * Copyright (c) OBCOM INGENIERIA S.A. (Chile) All rights reserved.
 * OBCOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * DO NOT EDIT THIS FILE <<Signature:DLyAFS4SK3ULvg6ni0vVjt>>.
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.RowSet;

import org.apache.log4j.Logger;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

import oracle.jdbc.OracleConnection;
//import weblogic.jdbc.rowset.RowSetFactory;
//import weblogic.jdbc.rowset.WLCachedRowSet;

/**
 * Implements methods common to all procedure callers.
 */
public abstract class ProcedureCaller
{
    // The same as oracle.jdbc.OracleTypes.CURSOR
    protected static final int ORACLE_CURSOR = -10;
    private static Logger logger = Logger.getLogger("cl.tesoreria.finpub.RentaMonedaExtranjera.ProcedureCaller");

    //------------------------------------------------------------
    //-- ResultSet methods ---------------------------------------
    //------------------------------------------------------------
    
    /**
     * Builds a RowSet from the supplied ResultSet.
     */
    protected static RowSet toRowSet(ResultSet resultSet)
        throws java.sql.SQLException
    {
    	//RowSetFactory factory = RowSetFactory.newInstance();
    	//WLCachedRowSet rowSet = factory.newCachedRowSet();
    	CachedRowSet rowSet = new CachedRowSetImpl();
        rowSet.populate(resultSet);
        return rowSet;

    }

    /**
     * Builds a ProcedureTable from the supplied ResultSet.
     */
    protected static ProcedureTable toProcedureTable(ResultSet resultSet)
        throws java.sql.SQLException
    {
        // Obtain ResultSet meta data and number of Columns
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Create array with column names of the ResultSet
        String[] cols = new String[columnCount];
        for (int c = 1; c <= columnCount; c++)
            cols[c-1] = metaData.getColumnName(c);

        // Create a list with the rows of the ResultSet
        ArrayList rows = new ArrayList();
        while (resultSet.next())
        {
            Object[] values = new Object[columnCount];
            for (int c = 1; c <= columnCount; c++)
                values[c-1] = toService(resultSet.getObject(c));
            rows.add(values);
        }

        // Create and return new ProcedureTable
        ProcedureTable table = new ProcedureTable();
        table.setCols(cols);
        table.setRows((Object[][]) rows.toArray(new Object[rows.size()][]));
        return table;
    }

    /**
     * Converts an object to a Web Service compatible object.
     */
    private static Object toService(Object value)
        throws java.sql.SQLException
    {
        if (value instanceof java.sql.Clob)
        {
            java.sql.Clob clob = (java.sql.Clob) value;
            return clob.getSubString(1L, (int) clob.length());
        }
        if (value instanceof java.sql.Blob)
        {
            java.sql.Blob blob = (java.sql.Blob) value;
            return blob.getBytes(1L, (int) blob.length());
        }
        if (value instanceof java.util.Date)
        {
            java.util.Date date = (java.util.Date) value;
            return new java.util.Date(date.getTime());
        }
        return value;
    }

    //------------------------------------------------------------
    //-- DATE/TIME conversion methods ----------------------------
    //------------------------------------------------------------

    /**
     * Converts the supplied Date to a SQL Timestamp instance.
     */
    protected static java.sql.Timestamp toTimestamp(Date date)
    {
        if (date == null || date instanceof java.sql.Timestamp)
            return (java.sql.Timestamp) date;
        return new java.sql.Timestamp(date.getTime());
    }

    //------------------------------------------------------------
    //-- CLOB conversion methods ---------------------------------
    //------------------------------------------------------------

    /**
     * Returns the string data of the supplied SQL Clob.
     */
    protected static String toString(Clob clob)
        throws java.sql.SQLException
    {
        if (clob != null)
        {
            try
            {
                java.io.Reader reader =
                    clob.getCharacterStream();
                StringBuffer writer =
                    new StringBuffer(8192);
                int len; char[] buf = new char[8192];
                while ((len = reader.read(buf)) != -1) 
                    writer.append(buf, 0, len);
                return writer.toString();
            }
            catch (java.io.IOException ex)
            {
            	ex.printStackTrace();
            	logger.error("Error en el metodo ProcedureCaller.toString() : " + ex);
            	throw new java.sql.SQLException(ex.toString());
            }
        }
        return null;
    }

    /**
     * Converts the supplied Java String to an SQL Clob.
     */
    protected static Clob toClob(String str, Connection conn)
        throws java.sql.SQLException
    {
        if (str != null)
        {
            try
            {
                Clob clob = oracle.sql.CLOB.createTemporary(
                    conn, false, oracle.sql.CLOB.DURATION_SESSION);
                java.io.Writer writer = clob.setCharacterStream(1);
                try
                {
                    writer.write(str);
                }
                finally
                {
                    writer.close();
                }
                return clob;
            }
            catch (java.io.IOException ex)
            {
            	ex.printStackTrace();
            	logger.error("Error en el metodo ProcedureCaller.toClob() : " + ex);
            	throw new java.sql.SQLException(ex.toString());
            }
        }
        return null;
    }

    //------------------------------------------------------------
    //-- Utility methods -----------------------------------------
    //------------------------------------------------------------

    /**
     * Returns the supplied string truncated to a maximum length.
     */
    private static String truncate(String value, int maxlen)
    {
        if (value != null && value.length() > maxlen)
            value = value.substring(0, maxlen);
        return value;
    }

    //------------------------------------------------------------
    //-- ITEMS_CUT conversion methods ----------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Array to an array of ItemCut.
     */
    protected static ItemCut[] toItemsCut(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Array interface
        if (!(datum instanceof java.sql.Array))
            throw new IllegalArgumentException("datum is not an Array");
        java.sql.Array array = (java.sql.Array) datum;

        // Create array of items from Array objects 
        Object[] objects = (Object[]) array.getArray();
        ItemCut[] items = new ItemCut[objects.length];

        // Convert Struct objects to ItemCut items
        for (int i = 0; i < objects.length; i++)
            items[i] = toItemCut(objects[i]);

        // Return items
        return items;
    }

    /**
     * Converts an array of ItemCut to an SQL Array.
     */
    protected static Object dbItemsCut(ItemCut[] items, Connection conn)
        throws java.sql.SQLException
    {
        // If items is null then done
        if (items == null) return null;

        // Convert ItemCut items to SQL Structs
        Object[] objects = new Object[items.length];
        for (int i = 0; i < items.length; i++)
            objects[i] = dbItemCut(items[i], conn);

        // Create an SQL Array from array of objects
        oracle.sql.ArrayDescriptor descriptor =
            oracle.sql.ArrayDescriptor.createDescriptor("SII.ITEMS_CUT", conn);
        return new oracle.sql.ARRAY(descriptor, conn, objects);
    }

    //------------------------------------------------------------
    //-- FORMULARIOS conversion methods --------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Array to an array of Formulario.
     */
    protected static Formulario[] toFormularios(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Array interface
        if (!(datum instanceof java.sql.Array))
            throw new IllegalArgumentException("datum is not an Array");
        java.sql.Array array = (java.sql.Array) datum;

        // Create array of items from Array objects 
        Object[] objects = (Object[]) array.getArray();
        Formulario[] items = new Formulario[objects.length];

        // Convert Struct objects to Formulario items
        for (int i = 0; i < objects.length; i++)
            items[i] = toFormulario(objects[i]);

        // Return items
        return items;
    }

    /**
     * Converts an array of Formulario to an SQL Array.
     */
    protected static Object dbFormularios(Formulario[] items, Connection conn)
        throws java.sql.SQLException
    {
        // If items is null then done
        if (items == null) return null;

        // Convert Formulario items to SQL Structs
        Object[] objects = new Object[items.length];
        for (int i = 0; i < items.length; i++)
            objects[i] = dbFormulario(items[i], conn);

        // Create an SQL Array from array of objects
        oracle.sql.ArrayDescriptor descriptor =
            oracle.sql.ArrayDescriptor.createDescriptor("FORMULARIOS", conn);
        return new oracle.sql.ARRAY(descriptor, conn, objects);
    }

    //------------------------------------------------------------
    //-- ITEM_CUT conversion methods -----------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Struct to an instance of ItemCut.
     */
    protected static ItemCut toItemCut(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Struct interface
        if (!(datum instanceof java.sql.Struct))
            throw new IllegalArgumentException("datum is not a Struct");
        java.sql.Struct struct = (java.sql.Struct) datum;

        // Obtain Struct attributes and check size
        Object[] attributes = struct.getAttributes();
        if (attributes == null || attributes.length != 5)
            throw new IllegalArgumentException("invalid Struct attributes");

        // Create ItemCut from Struct attributes
        ItemCut result = new ItemCut();
        result.setIdMov((BigDecimal) attributes[0]);
        result.setCodg((BigDecimal) attributes[1]);
        result.setDatoType((String) attributes[2]);
        result.setStatus((BigDecimal) attributes[3]);
        result.setValor((String) attributes[4]);
        return result;
    }

    /**
     * Converts an instance of ItemCut to an SQL Struct.
     */
    protected static Object dbItemCut(ItemCut item, Connection conn)
        throws java.sql.SQLException
    {
        // If item is null then done
        if (item == null) return null;

        // Create array of Struct attributes
        Object[] attributes = new Object[5];
        attributes[0] = item.getIdMov();
        attributes[1] = item.getCodg();
        attributes[2] = truncate(item.getDatoType(), 1);
        attributes[3] = item.getStatus();
        attributes[4] = truncate(item.getValor(), 4000);

        // Create an SQL Struct from array of attributes
        
      //  try {
	//		OracleConnection oracleConnection = (OracleConnection) 
	//			    conn.getClass().getMethod("getUnderlyingConnection").invoke(conn);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
//				| SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		};
        
        oracle.sql.StructDescriptor descriptor =
            oracle.sql.StructDescriptor.createDescriptor("SII.ITEM_CUT", conn);
        return new oracle.sql.STRUCT(descriptor, conn, attributes);
    }

    //------------------------------------------------------------
    //-- FORMULARIO conversion methods ---------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Struct to an instance of Formulario.
     */
    protected static Formulario toFormulario(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Struct interface
        if (!(datum instanceof java.sql.Struct))
            throw new IllegalArgumentException("datum is not a Struct");
        java.sql.Struct struct = (java.sql.Struct) datum;

        // Obtain Struct attributes and check size
        Object[] attributes = struct.getAttributes();
        if (attributes == null || attributes.length != 4)
            throw new IllegalArgumentException("invalid Struct attributes");

        // Create Formulario from Struct attributes
        Formulario result = new Formulario();
        result.setIndcta((BigDecimal) attributes[0]);
        result.setCtaobj(toCtaCut(attributes[1]));
        result.setMovobj(toMovCut(attributes[2]));
        result.setItemscoll(toItemsCut(attributes[3]));
        return result;
    }

    /**
     * Converts an instance of Formulario to an SQL Struct.
     */
    protected static Object dbFormulario(Formulario item, Connection conn)
        throws java.sql.SQLException
    {
        // If item is null then done
        if (item == null) return null;

        // Create array of Struct attributes
        Object[] attributes = new Object[4];
        attributes[0] = item.getIndcta();
        attributes[1] = dbCtaCut(item.getCtaobj(), conn);
        attributes[2] = dbMovCut(item.getMovobj(), conn);
        attributes[3] = dbItemsCut(item.getItemscoll(), conn);

        // Create an SQL Struct from array of attributes
        oracle.sql.StructDescriptor descriptor =
            oracle.sql.StructDescriptor.createDescriptor("FORMULARIO", conn);
        return new oracle.sql.STRUCT(descriptor, conn, attributes);
    }

    //------------------------------------------------------------
    //-- CTA_CUT conversion methods ------------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Struct to an instance of CtaCut.
     */
    protected static CtaCut toCtaCut(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Struct interface
        if (!(datum instanceof java.sql.Struct))
            throw new IllegalArgumentException("datum is not a Struct");
        java.sql.Struct struct = (java.sql.Struct) datum;

        // Obtain Struct attributes and check size
        Object[] attributes = struct.getAttributes();
        if (attributes == null || attributes.length != 12)
            throw new IllegalArgumentException("invalid Struct attributes");

        // Create CtaCut from Struct attributes
        CtaCut result = new CtaCut();
        result.setRut((BigDecimal) attributes[0]);
        result.setDv((String) attributes[1]);
        result.setFormtipo((BigDecimal) attributes[2]);
        result.setFolio((BigDecimal) attributes[3]);
        result.setInstit((BigDecimal) attributes[4]);
        result.setPeriodo((Date) attributes[5]);
        result.setFechaCrea((Date) attributes[6]);
        result.setFechaAct((Date) attributes[7]);
        result.setFechaVto((Date) attributes[8]);
        result.setMoneda((BigDecimal) attributes[9]);
        result.setSaldo((BigDecimal) attributes[10]);
        result.setRecti((String) attributes[11]);
        return result;
    }

    /**
     * Converts an instance of CtaCut to an SQL Struct.
     */
    protected static Object dbCtaCut(CtaCut item, Connection conn)
        throws java.sql.SQLException
    {
        // If item is null then done
        if (item == null) return null;

        // Create array of Struct attributes
        Object[] attributes = new Object[12];
        attributes[0] = item.getRut();
        attributes[1] = truncate(item.getDv(), 1);
        attributes[2] = item.getFormtipo();
        attributes[3] = item.getFolio();
        attributes[4] = item.getInstit();
        attributes[5] = toTimestamp(item.getPeriodo());
        attributes[6] = toTimestamp(item.getFechaCrea());
        attributes[7] = toTimestamp(item.getFechaAct());
        attributes[8] = toTimestamp(item.getFechaVto());
        attributes[9] = item.getMoneda();
        attributes[10] = item.getSaldo();
        attributes[11] = truncate(item.getRecti(), 2);

        // Create an SQL Struct from array of attributes
        oracle.sql.StructDescriptor descriptor =
            oracle.sql.StructDescriptor.createDescriptor("CTA_CUT", conn);
        return new oracle.sql.STRUCT(descriptor, conn, attributes);
    }

    //------------------------------------------------------------
    //-- MOV_CUT conversion methods ------------------------------
    //------------------------------------------------------------

    /**
     * Converts an SQL Struct to an instance of MovCut.
     */
    protected static MovCut toMovCut(Object datum)
        throws java.sql.SQLException
    {
        // If datum is null then done
        if (datum == null) return null;

        // Check datum and obtain Struct interface
        if (!(datum instanceof java.sql.Struct))
            throw new IllegalArgumentException("datum is not a Struct");
        java.sql.Struct struct = (java.sql.Struct) datum;

        // Obtain Struct attributes and check size
        Object[] attributes = struct.getAttributes();
        if (attributes == null || attributes.length != 21)
            throw new IllegalArgumentException("invalid Struct attributes");

        // Create MovCut from Struct attributes
        MovCut result = new MovCut();
        result.setCuenta((BigDecimal) attributes[0]);
        result.setNumtrx((BigDecimal) attributes[1]);
        result.setMovtipo((BigDecimal) attributes[2]);
        result.setFecha((Date) attributes[3]);
        result.setFechaVto((Date) attributes[4]);
        result.setFormtipo((BigDecimal) attributes[5]);
        result.setFormver((String) attributes[6]);
        result.setFolio((BigDecimal) attributes[7]);
        result.setMoneda((BigDecimal) attributes[8]);
        result.setMonto((BigDecimal) attributes[9]);
        result.setMontopesos((BigDecimal) attributes[10]);
        result.setValorcambio((BigDecimal) attributes[11]);
        result.setEstado((BigDecimal) attributes[12]);
        result.setRutira((BigDecimal) attributes[13]);
        result.setDvira((String) attributes[14]);
        result.setFol01((BigDecimal) attributes[15]);
        result.setCod9927((BigDecimal) attributes[16]);
        result.setMontoret((BigDecimal) attributes[17]);
        result.setMontoimp((BigDecimal) attributes[18]);
        result.setMontopag((BigDecimal) attributes[19]);
        result.setPeriodocont((Date) attributes[20]);
        return result;
    }

    /**
     * Converts an instance of MovCut to an SQL Struct.
     */
    protected static Object dbMovCut(MovCut item, Connection conn)
        throws java.sql.SQLException
    {
        // If item is null then done
        if (item == null) return null;

        // Create array of Struct attributes
        Object[] attributes = new Object[21];
        attributes[0] = item.getCuenta();
        attributes[1] = item.getNumtrx();
        attributes[2] = item.getMovtipo();
        attributes[3] = toTimestamp(item.getFecha());
        attributes[4] = toTimestamp(item.getFechaVto());
        attributes[5] = item.getFormtipo();
        attributes[6] = truncate(item.getFormver(), 1);
        attributes[7] = item.getFolio();
        attributes[8] = item.getMoneda();
        attributes[9] = item.getMonto();
        attributes[10] = item.getMontopesos();
        attributes[11] = item.getValorcambio();
        attributes[12] = item.getEstado();
        attributes[13] = item.getRutira();
        attributes[14] = truncate(item.getDvira(), 2);
        attributes[15] = item.getFol01();
        attributes[16] = item.getCod9927();
        attributes[17] = item.getMontoret();
        attributes[18] = item.getMontoimp();
        attributes[19] = item.getMontopag();
        attributes[20] = toTimestamp(item.getPeriodocont());

        // Create an SQL Struct from array of attributes
        oracle.sql.StructDescriptor descriptor =
            oracle.sql.StructDescriptor.createDescriptor("MOV_CUT", conn);
        return new oracle.sql.STRUCT(descriptor, conn, attributes);
    }
}

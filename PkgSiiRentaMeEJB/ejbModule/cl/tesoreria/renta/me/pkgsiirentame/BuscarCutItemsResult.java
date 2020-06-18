/*
 * DHN: 20151222
 */

package cl.tesoreria.renta.me.pkgsiirentame;

import javax.sql.RowSet;

/**
 * Output parameters of procedure "PKG_SII_RENTA_ME.buscar_cut_items".
 */
public class BuscarCutItemsResult implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private RowSet[] myRowSets;

    /**
     * Constructor.
     */
    public BuscarCutItemsResult()
    {
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

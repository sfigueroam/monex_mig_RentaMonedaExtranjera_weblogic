/*
 * An XML document type.
 * Localname: Message
 * Namespace: 
 * Java type: noNamespace.MessageDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace.impl;
/**
 * A document containing one Message(@) element.
 *
 * This is a complex type.
 */
public class MessageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument
{
    
    public MessageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MESSAGE$0 = 
        new javax.xml.namespace.QName("", "Message");
    
    
    /**
     * Gets the "Message" element
     */
    public noNamespace.MessageDocument.Message getMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.MessageDocument.Message target = null;
            target = (noNamespace.MessageDocument.Message)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Message" element
     */
    public void setMessage(noNamespace.MessageDocument.Message message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.MessageDocument.Message target = null;
            target = (noNamespace.MessageDocument.Message)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                target = (noNamespace.MessageDocument.Message)get_store().add_element_user(MESSAGE$0);
            }
            target.set(message);
        }
    }
    
    /**
     * Appends and returns a new empty "Message" element
     */
    public noNamespace.MessageDocument.Message addNewMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            noNamespace.MessageDocument.Message target = null;
            target = (noNamespace.MessageDocument.Message)get_store().add_element_user(MESSAGE$0);
            return target;
        }
    }
    /**
     * An XML Message(@).
     *
     * This is a complex type.
     */
    public static class MessageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message
    {
        
        public MessageImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName MESSAGEID$0 = 
            new javax.xml.namespace.QName("", "MessageId");
        private static final javax.xml.namespace.QName FORMULARIO$2 = 
            new javax.xml.namespace.QName("", "Formulario");
        
        
        /**
         * Gets the "MessageId" element
         */
        public noNamespace.MessageDocument.Message.MessageId getMessageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.MessageId target = null;
                target = (noNamespace.MessageDocument.Message.MessageId)get_store().find_element_user(MESSAGEID$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "MessageId" element
         */
        public void setMessageId(noNamespace.MessageDocument.Message.MessageId messageId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.MessageId target = null;
                target = (noNamespace.MessageDocument.Message.MessageId)get_store().find_element_user(MESSAGEID$0, 0);
                if (target == null)
                {
                    target = (noNamespace.MessageDocument.Message.MessageId)get_store().add_element_user(MESSAGEID$0);
                }
                target.set(messageId);
            }
        }
        
        /**
         * Appends and returns a new empty "MessageId" element
         */
        public noNamespace.MessageDocument.Message.MessageId addNewMessageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.MessageId target = null;
                target = (noNamespace.MessageDocument.Message.MessageId)get_store().add_element_user(MESSAGEID$0);
                return target;
            }
        }
        
        /**
         * Gets a List of "Formulario" elements
         */
        public java.util.List<noNamespace.MessageDocument.Message.Formulario> getFormularioList()
        {
            final class FormularioList extends java.util.AbstractList<noNamespace.MessageDocument.Message.Formulario>
            {
                public noNamespace.MessageDocument.Message.Formulario get(int i)
                    { return MessageImpl.this.getFormularioArray(i); }
                
                public noNamespace.MessageDocument.Message.Formulario set(int i, noNamespace.MessageDocument.Message.Formulario o)
                {
                    noNamespace.MessageDocument.Message.Formulario old = MessageImpl.this.getFormularioArray(i);
                    MessageImpl.this.setFormularioArray(i, o);
                    return old;
                }
                
                public void add(int i, noNamespace.MessageDocument.Message.Formulario o)
                    { MessageImpl.this.insertNewFormulario(i).set(o); }
                
                public noNamespace.MessageDocument.Message.Formulario remove(int i)
                {
                    noNamespace.MessageDocument.Message.Formulario old = MessageImpl.this.getFormularioArray(i);
                    MessageImpl.this.removeFormulario(i);
                    return old;
                }
                
                public int size()
                    { return MessageImpl.this.sizeOfFormularioArray(); }
                
            }
            
            synchronized (monitor())
            {
                check_orphaned();
                return new FormularioList();
            }
        }
        
        /**
         * Gets array of all "Formulario" elements
         */
        public noNamespace.MessageDocument.Message.Formulario[] getFormularioArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(FORMULARIO$2, targetList);
                noNamespace.MessageDocument.Message.Formulario[] result = new noNamespace.MessageDocument.Message.Formulario[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "Formulario" element
         */
        public noNamespace.MessageDocument.Message.Formulario getFormularioArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.Formulario target = null;
                target = (noNamespace.MessageDocument.Message.Formulario)get_store().find_element_user(FORMULARIO$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Returns number of "Formulario" element
         */
        public int sizeOfFormularioArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FORMULARIO$2);
            }
        }
        
        /**
         * Sets array of all "Formulario" element
         */
        public void setFormularioArray(noNamespace.MessageDocument.Message.Formulario[] formularioArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(formularioArray, FORMULARIO$2);
            }
        }
        
        /**
         * Sets ith "Formulario" element
         */
        public void setFormularioArray(int i, noNamespace.MessageDocument.Message.Formulario formulario)
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.Formulario target = null;
                target = (noNamespace.MessageDocument.Message.Formulario)get_store().find_element_user(FORMULARIO$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(formulario);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Formulario" element
         */
        public noNamespace.MessageDocument.Message.Formulario insertNewFormulario(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.Formulario target = null;
                target = (noNamespace.MessageDocument.Message.Formulario)get_store().insert_element_user(FORMULARIO$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Formulario" element
         */
        public noNamespace.MessageDocument.Message.Formulario addNewFormulario()
        {
            synchronized (monitor())
            {
                check_orphaned();
                noNamespace.MessageDocument.Message.Formulario target = null;
                target = (noNamespace.MessageDocument.Message.Formulario)get_store().add_element_user(FORMULARIO$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "Formulario" element
         */
        public void removeFormulario(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FORMULARIO$2, i);
            }
        }
        /**
         * An XML MessageId(@).
         *
         * This is a complex type.
         */
        public static class MessageIdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message.MessageId
        {
            
            public MessageIdImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CODE$0 = 
                new javax.xml.namespace.QName("", "Code");
            private static final javax.xml.namespace.QName MSGDESC$2 = 
                new javax.xml.namespace.QName("", "MsgDesc");
            private static final javax.xml.namespace.QName VERSION$4 = 
                new javax.xml.namespace.QName("", "Version");
            private static final javax.xml.namespace.QName FECHAVERSION$6 = 
                new javax.xml.namespace.QName("", "FechaVersion");
            private static final javax.xml.namespace.QName FROMADDRESS$8 = 
                new javax.xml.namespace.QName("", "FromAddress");
            private static final javax.xml.namespace.QName TOADDRESS$10 = 
                new javax.xml.namespace.QName("", "ToAddress");
            private static final javax.xml.namespace.QName REFADDRESS$12 = 
                new javax.xml.namespace.QName("", "RefAddress");
            private static final javax.xml.namespace.QName DATETIME$14 = 
                new javax.xml.namespace.QName("", "DateTime");
            private static final javax.xml.namespace.QName VALIDADO$16 = 
                new javax.xml.namespace.QName("", "Validado");
            private static final javax.xml.namespace.QName NUMBER$18 = 
                new javax.xml.namespace.QName("", "Number");
            private static final javax.xml.namespace.QName PERIODOCONTABLE$20 = 
                new javax.xml.namespace.QName("", "PeriodoContable");
            
            
            /**
             * Gets the "Code" element
             */
            public java.lang.String getCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODE$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Code" element
             */
            public org.apache.xmlbeans.XmlString xgetCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODE$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Code" element
             */
            public void setCode(java.lang.String code)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODE$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODE$0);
                    }
                    target.setStringValue(code);
                }
            }
            
            /**
             * Sets (as xml) the "Code" element
             */
            public void xsetCode(org.apache.xmlbeans.XmlString code)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CODE$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CODE$0);
                    }
                    target.set(code);
                }
            }
            
            /**
             * Gets the "MsgDesc" element
             */
            public java.lang.String getMsgDesc()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSGDESC$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "MsgDesc" element
             */
            public org.apache.xmlbeans.XmlString xgetMsgDesc()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MSGDESC$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "MsgDesc" element
             */
            public void setMsgDesc(java.lang.String msgDesc)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSGDESC$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MSGDESC$2);
                    }
                    target.setStringValue(msgDesc);
                }
            }
            
            /**
             * Sets (as xml) the "MsgDesc" element
             */
            public void xsetMsgDesc(org.apache.xmlbeans.XmlString msgDesc)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MSGDESC$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MSGDESC$2);
                    }
                    target.set(msgDesc);
                }
            }
            
            /**
             * Gets the "Version" element
             */
            public long getVersion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSION$4, 0);
                    if (target == null)
                    {
                      return 0L;
                    }
                    return target.getLongValue();
                }
            }
            
            /**
             * Gets (as xml) the "Version" element
             */
            public org.apache.xmlbeans.XmlLong xgetVersion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlLong target = null;
                    target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(VERSION$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Version" element
             */
            public void setVersion(long version)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VERSION$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VERSION$4);
                    }
                    target.setLongValue(version);
                }
            }
            
            /**
             * Sets (as xml) the "Version" element
             */
            public void xsetVersion(org.apache.xmlbeans.XmlLong version)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlLong target = null;
                    target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(VERSION$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(VERSION$4);
                    }
                    target.set(version);
                }
            }
            
            /**
             * Gets the "FechaVersion" element
             */
            public java.lang.String getFechaVersion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FECHAVERSION$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "FechaVersion" element
             */
            public org.apache.xmlbeans.XmlString xgetFechaVersion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FECHAVERSION$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "FechaVersion" element
             */
            public void setFechaVersion(java.lang.String fechaVersion)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FECHAVERSION$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FECHAVERSION$6);
                    }
                    target.setStringValue(fechaVersion);
                }
            }
            
            /**
             * Sets (as xml) the "FechaVersion" element
             */
            public void xsetFechaVersion(org.apache.xmlbeans.XmlString fechaVersion)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FECHAVERSION$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FECHAVERSION$6);
                    }
                    target.set(fechaVersion);
                }
            }
            
            /**
             * Gets the "FromAddress" element
             */
            public java.lang.String getFromAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FROMADDRESS$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "FromAddress" element
             */
            public org.apache.xmlbeans.XmlString xgetFromAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FROMADDRESS$8, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "FromAddress" element
             */
            public void setFromAddress(java.lang.String fromAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FROMADDRESS$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FROMADDRESS$8);
                    }
                    target.setStringValue(fromAddress);
                }
            }
            
            /**
             * Sets (as xml) the "FromAddress" element
             */
            public void xsetFromAddress(org.apache.xmlbeans.XmlString fromAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FROMADDRESS$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FROMADDRESS$8);
                    }
                    target.set(fromAddress);
                }
            }
            
            /**
             * Gets the "ToAddress" element
             */
            public java.lang.String getToAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOADDRESS$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ToAddress" element
             */
            public org.apache.xmlbeans.XmlString xgetToAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TOADDRESS$10, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ToAddress" element
             */
            public void setToAddress(java.lang.String toAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TOADDRESS$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TOADDRESS$10);
                    }
                    target.setStringValue(toAddress);
                }
            }
            
            /**
             * Sets (as xml) the "ToAddress" element
             */
            public void xsetToAddress(org.apache.xmlbeans.XmlString toAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TOADDRESS$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TOADDRESS$10);
                    }
                    target.set(toAddress);
                }
            }
            
            /**
             * Gets the "RefAddress" element
             */
            public java.math.BigDecimal getRefAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFADDRESS$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "RefAddress" element
             */
            public org.apache.xmlbeans.XmlDecimal xgetRefAddress()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(REFADDRESS$12, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "RefAddress" element
             */
            public void setRefAddress(java.math.BigDecimal refAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REFADDRESS$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REFADDRESS$12);
                    }
                    target.setBigDecimalValue(refAddress);
                }
            }
            
            /**
             * Sets (as xml) the "RefAddress" element
             */
            public void xsetRefAddress(org.apache.xmlbeans.XmlDecimal refAddress)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(REFADDRESS$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(REFADDRESS$12);
                    }
                    target.set(refAddress);
                }
            }
            
            /**
             * Gets the "DateTime" element
             */
            public java.lang.String getDateTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATETIME$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "DateTime" element
             */
            public org.apache.xmlbeans.XmlString xgetDateTime()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATETIME$14, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "DateTime" element
             */
            public void setDateTime(java.lang.String dateTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATETIME$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATETIME$14);
                    }
                    target.setStringValue(dateTime);
                }
            }
            
            /**
             * Sets (as xml) the "DateTime" element
             */
            public void xsetDateTime(org.apache.xmlbeans.XmlString dateTime)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATETIME$14, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATETIME$14);
                    }
                    target.set(dateTime);
                }
            }
            
            /**
             * Gets the "Validado" element
             */
            public java.lang.String getValidado()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDADO$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "Validado" element
             */
            public org.apache.xmlbeans.XmlString xgetValidado()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDADO$16, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Validado" element
             */
            public void setValidado(java.lang.String validado)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALIDADO$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALIDADO$16);
                    }
                    target.setStringValue(validado);
                }
            }
            
            /**
             * Sets (as xml) the "Validado" element
             */
            public void xsetValidado(org.apache.xmlbeans.XmlString validado)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlString target = null;
                    target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VALIDADO$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VALIDADO$16);
                    }
                    target.set(validado);
                }
            }
            
            /**
             * Gets the "Number" element
             */
            public java.math.BigDecimal getNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMBER$18, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "Number" element
             */
            public org.apache.xmlbeans.XmlDecimal xgetNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(NUMBER$18, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "Number" element
             */
            public void setNumber(java.math.BigDecimal number)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMBER$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMBER$18);
                    }
                    target.setBigDecimalValue(number);
                }
            }
            
            /**
             * Sets (as xml) the "Number" element
             */
            public void xsetNumber(org.apache.xmlbeans.XmlDecimal number)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(NUMBER$18, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(NUMBER$18);
                    }
                    target.set(number);
                }
            }
            
            /**
             * Gets the "PeriodoContable" element
             */
            public java.math.BigDecimal getPeriodoContable()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERIODOCONTABLE$20, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "PeriodoContable" element
             */
            public org.apache.xmlbeans.XmlDecimal xgetPeriodoContable()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(PERIODOCONTABLE$20, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "PeriodoContable" element
             */
            public void setPeriodoContable(java.math.BigDecimal periodoContable)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERIODOCONTABLE$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERIODOCONTABLE$20);
                    }
                    target.setBigDecimalValue(periodoContable);
                }
            }
            
            /**
             * Sets (as xml) the "PeriodoContable" element
             */
            public void xsetPeriodoContable(org.apache.xmlbeans.XmlDecimal periodoContable)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(PERIODOCONTABLE$20, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(PERIODOCONTABLE$20);
                    }
                    target.set(periodoContable);
                }
            }
        }
        /**
         * An XML Formulario(@).
         *
         * This is a complex type.
         */
        public static class FormularioImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message.Formulario
        {
            
            public FormularioImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName IDENTIFICACION$0 = 
                new javax.xml.namespace.QName("", "Identificacion");
            private static final javax.xml.namespace.QName LISTACODIGOS$2 = 
                new javax.xml.namespace.QName("", "ListaCodigos");
            private static final javax.xml.namespace.QName NRO$4 = 
                new javax.xml.namespace.QName("", "nro");
            
            
            /**
             * Gets the "Identificacion" element
             */
            public noNamespace.MessageDocument.Message.Formulario.Identificacion getIdentificacion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.Identificacion target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.Identificacion)get_store().find_element_user(IDENTIFICACION$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "Identificacion" element
             */
            public void setIdentificacion(noNamespace.MessageDocument.Message.Formulario.Identificacion identificacion)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.Identificacion target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.Identificacion)get_store().find_element_user(IDENTIFICACION$0, 0);
                    if (target == null)
                    {
                      target = (noNamespace.MessageDocument.Message.Formulario.Identificacion)get_store().add_element_user(IDENTIFICACION$0);
                    }
                    target.set(identificacion);
                }
            }
            
            /**
             * Appends and returns a new empty "Identificacion" element
             */
            public noNamespace.MessageDocument.Message.Formulario.Identificacion addNewIdentificacion()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.Identificacion target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.Identificacion)get_store().add_element_user(IDENTIFICACION$0);
                    return target;
                }
            }
            
            /**
             * Gets the "ListaCodigos" element
             */
            public noNamespace.MessageDocument.Message.Formulario.ListaCodigos getListaCodigos()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.ListaCodigos target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos)get_store().find_element_user(LISTACODIGOS$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ListaCodigos" element
             */
            public void setListaCodigos(noNamespace.MessageDocument.Message.Formulario.ListaCodigos listaCodigos)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.ListaCodigos target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos)get_store().find_element_user(LISTACODIGOS$2, 0);
                    if (target == null)
                    {
                      target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos)get_store().add_element_user(LISTACODIGOS$2);
                    }
                    target.set(listaCodigos);
                }
            }
            
            /**
             * Appends and returns a new empty "ListaCodigos" element
             */
            public noNamespace.MessageDocument.Message.Formulario.ListaCodigos addNewListaCodigos()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    noNamespace.MessageDocument.Message.Formulario.ListaCodigos target = null;
                    target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos)get_store().add_element_user(LISTACODIGOS$2);
                    return target;
                }
            }
            
            /**
             * Gets the "nro" attribute
             */
            public java.math.BigDecimal getNro()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NRO$4);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "nro" attribute
             */
            public org.apache.xmlbeans.XmlDecimal xgetNro()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_attribute_user(NRO$4);
                    return target;
                }
            }
            
            /**
             * Sets the "nro" attribute
             */
            public void setNro(java.math.BigDecimal nro)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NRO$4);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NRO$4);
                    }
                    target.setBigDecimalValue(nro);
                }
            }
            
            /**
             * Sets (as xml) the "nro" attribute
             */
            public void xsetNro(org.apache.xmlbeans.XmlDecimal nro)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlDecimal target = null;
                    target = (org.apache.xmlbeans.XmlDecimal)get_store().find_attribute_user(NRO$4);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().add_attribute_user(NRO$4);
                    }
                    target.set(nro);
                }
            }
            /**
             * An XML Identificacion(@).
             *
             * This is a complex type.
             */
            public static class IdentificacionImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message.Formulario.Identificacion
            {
                
                public IdentificacionImpl(org.apache.xmlbeans.SchemaType sType)
                {
                    super(sType);
                }
                
                private static final javax.xml.namespace.QName RUTIRA$0 = 
                    new javax.xml.namespace.QName("", "RutIra");
                private static final javax.xml.namespace.QName DVIRA$2 = 
                    new javax.xml.namespace.QName("", "DvIra");
                private static final javax.xml.namespace.QName FOLIOF01$4 = 
                    new javax.xml.namespace.QName("", "FolioF01");
                private static final javax.xml.namespace.QName FORMULARIO$6 = 
                    new javax.xml.namespace.QName("", "Formulario");
                private static final javax.xml.namespace.QName PERIODO$8 = 
                    new javax.xml.namespace.QName("", "Periodo");
                private static final javax.xml.namespace.QName FOLIODECL$10 = 
                    new javax.xml.namespace.QName("", "FolioDecl");
                private static final javax.xml.namespace.QName SIGNO$12 = 
                    new javax.xml.namespace.QName("", "Signo");
                private static final javax.xml.namespace.QName RUTCONTR$14 = 
                    new javax.xml.namespace.QName("", "RutContr");
                private static final javax.xml.namespace.QName DVCONTR$16 = 
                    new javax.xml.namespace.QName("", "DvContr");
                private static final javax.xml.namespace.QName MARCAFISC$18 = 
                    new javax.xml.namespace.QName("", "MarcaFisc");
                private static final javax.xml.namespace.QName GLOSAFISC$20 = 
                    new javax.xml.namespace.QName("", "GlosaFisc");
                private static final javax.xml.namespace.QName MONTORET$22 = 
                    new javax.xml.namespace.QName("", "MontoRet");
                private static final javax.xml.namespace.QName SIGNOMONTOIMP$24 = 
                    new javax.xml.namespace.QName("", "SignoMontoImp");
                private static final javax.xml.namespace.QName MONTOIMP$26 = 
                    new javax.xml.namespace.QName("", "MontoImp");
                private static final javax.xml.namespace.QName SIGNOMONTOPAG$28 = 
                    new javax.xml.namespace.QName("", "SignoMontoPag");
                private static final javax.xml.namespace.QName MONTOPAGO$30 = 
                    new javax.xml.namespace.QName("", "MontoPago");
                
                
                /**
                 * Gets the "RutIra" element
                 */
                public java.math.BigDecimal getRutIra()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUTIRA$0, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "RutIra" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetRutIra()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(RUTIRA$0, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "RutIra" element
                 */
                public void setRutIra(java.math.BigDecimal rutIra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUTIRA$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RUTIRA$0);
                      }
                      target.setBigDecimalValue(rutIra);
                    }
                }
                
                /**
                 * Sets (as xml) the "RutIra" element
                 */
                public void xsetRutIra(org.apache.xmlbeans.XmlDecimal rutIra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(RUTIRA$0, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(RUTIRA$0);
                      }
                      target.set(rutIra);
                    }
                }
                
                /**
                 * Gets the "DvIra" element
                 */
                public java.lang.String getDvIra()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DVIRA$2, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DvIra" element
                 */
                public org.apache.xmlbeans.XmlString xgetDvIra()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DVIRA$2, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "DvIra" element
                 */
                public void setDvIra(java.lang.String dvIra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DVIRA$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DVIRA$2);
                      }
                      target.setStringValue(dvIra);
                    }
                }
                
                /**
                 * Sets (as xml) the "DvIra" element
                 */
                public void xsetDvIra(org.apache.xmlbeans.XmlString dvIra)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DVIRA$2, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DVIRA$2);
                      }
                      target.set(dvIra);
                    }
                }
                
                /**
                 * Gets the "FolioF01" element
                 */
                public java.math.BigDecimal getFolioF01()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOLIOF01$4, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "FolioF01" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetFolioF01()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FOLIOF01$4, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "FolioF01" element
                 */
                public void setFolioF01(java.math.BigDecimal folioF01)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOLIOF01$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOLIOF01$4);
                      }
                      target.setBigDecimalValue(folioF01);
                    }
                }
                
                /**
                 * Sets (as xml) the "FolioF01" element
                 */
                public void xsetFolioF01(org.apache.xmlbeans.XmlDecimal folioF01)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FOLIOF01$4, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(FOLIOF01$4);
                      }
                      target.set(folioF01);
                    }
                }
                
                /**
                 * Gets the "Formulario" element
                 */
                public java.math.BigDecimal getFormulario()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMULARIO$6, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Formulario" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetFormulario()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FORMULARIO$6, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Formulario" element
                 */
                public void setFormulario(java.math.BigDecimal formulario)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMULARIO$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FORMULARIO$6);
                      }
                      target.setBigDecimalValue(formulario);
                    }
                }
                
                /**
                 * Sets (as xml) the "Formulario" element
                 */
                public void xsetFormulario(org.apache.xmlbeans.XmlDecimal formulario)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FORMULARIO$6, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(FORMULARIO$6);
                      }
                      target.set(formulario);
                    }
                }
                
                /**
                 * Gets the "Periodo" element
                 */
                public java.math.BigDecimal getPeriodo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERIODO$8, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Periodo" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetPeriodo()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(PERIODO$8, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Periodo" element
                 */
                public void setPeriodo(java.math.BigDecimal periodo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERIODO$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERIODO$8);
                      }
                      target.setBigDecimalValue(periodo);
                    }
                }
                
                /**
                 * Sets (as xml) the "Periodo" element
                 */
                public void xsetPeriodo(org.apache.xmlbeans.XmlDecimal periodo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(PERIODO$8, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(PERIODO$8);
                      }
                      target.set(periodo);
                    }
                }
                
                /**
                 * Gets the "FolioDecl" element
                 */
                public java.math.BigDecimal getFolioDecl()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOLIODECL$10, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "FolioDecl" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetFolioDecl()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FOLIODECL$10, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "FolioDecl" element
                 */
                public void setFolioDecl(java.math.BigDecimal folioDecl)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOLIODECL$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOLIODECL$10);
                      }
                      target.setBigDecimalValue(folioDecl);
                    }
                }
                
                /**
                 * Sets (as xml) the "FolioDecl" element
                 */
                public void xsetFolioDecl(org.apache.xmlbeans.XmlDecimal folioDecl)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(FOLIODECL$10, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(FOLIODECL$10);
                      }
                      target.set(folioDecl);
                    }
                }
                
                /**
                 * Gets the "Signo" element
                 */
                public java.lang.String getSigno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNO$12, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "Signo" element
                 */
                public org.apache.xmlbeans.XmlString xgetSigno()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNO$12, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "Signo" element
                 */
                public void setSigno(java.lang.String signo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNO$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIGNO$12);
                      }
                      target.setStringValue(signo);
                    }
                }
                
                /**
                 * Sets (as xml) the "Signo" element
                 */
                public void xsetSigno(org.apache.xmlbeans.XmlString signo)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNO$12, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIGNO$12);
                      }
                      target.set(signo);
                    }
                }
                
                /**
                 * Gets the "RutContr" element
                 */
                public java.math.BigDecimal getRutContr()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUTCONTR$14, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "RutContr" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetRutContr()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(RUTCONTR$14, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "RutContr" element
                 */
                public void setRutContr(java.math.BigDecimal rutContr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RUTCONTR$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RUTCONTR$14);
                      }
                      target.setBigDecimalValue(rutContr);
                    }
                }
                
                /**
                 * Sets (as xml) the "RutContr" element
                 */
                public void xsetRutContr(org.apache.xmlbeans.XmlDecimal rutContr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(RUTCONTR$14, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(RUTCONTR$14);
                      }
                      target.set(rutContr);
                    }
                }
                
                /**
                 * Gets the "DvContr" element
                 */
                public java.lang.String getDvContr()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DVCONTR$16, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "DvContr" element
                 */
                public org.apache.xmlbeans.XmlString xgetDvContr()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DVCONTR$16, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "DvContr" element
                 */
                public void setDvContr(java.lang.String dvContr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DVCONTR$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DVCONTR$16);
                      }
                      target.setStringValue(dvContr);
                    }
                }
                
                /**
                 * Sets (as xml) the "DvContr" element
                 */
                public void xsetDvContr(org.apache.xmlbeans.XmlString dvContr)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DVCONTR$16, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DVCONTR$16);
                      }
                      target.set(dvContr);
                    }
                }
                
                /**
                 * Gets the "MarcaFisc" element
                 */
                public java.math.BigDecimal getMarcaFisc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARCAFISC$18, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MarcaFisc" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetMarcaFisc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MARCAFISC$18, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MarcaFisc" element
                 */
                public void setMarcaFisc(java.math.BigDecimal marcaFisc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MARCAFISC$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MARCAFISC$18);
                      }
                      target.setBigDecimalValue(marcaFisc);
                    }
                }
                
                /**
                 * Sets (as xml) the "MarcaFisc" element
                 */
                public void xsetMarcaFisc(org.apache.xmlbeans.XmlDecimal marcaFisc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MARCAFISC$18, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(MARCAFISC$18);
                      }
                      target.set(marcaFisc);
                    }
                }
                
                /**
                 * Gets the "GlosaFisc" element
                 */
                public java.lang.String getGlosaFisc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GLOSAFISC$20, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "GlosaFisc" element
                 */
                public org.apache.xmlbeans.XmlString xgetGlosaFisc()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GLOSAFISC$20, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "GlosaFisc" element
                 */
                public void setGlosaFisc(java.lang.String glosaFisc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GLOSAFISC$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GLOSAFISC$20);
                      }
                      target.setStringValue(glosaFisc);
                    }
                }
                
                /**
                 * Sets (as xml) the "GlosaFisc" element
                 */
                public void xsetGlosaFisc(org.apache.xmlbeans.XmlString glosaFisc)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GLOSAFISC$20, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GLOSAFISC$20);
                      }
                      target.set(glosaFisc);
                    }
                }
                
                /**
                 * Gets the "MontoRet" element
                 */
                public java.math.BigDecimal getMontoRet()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTORET$22, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MontoRet" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetMontoRet()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTORET$22, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MontoRet" element
                 */
                public void setMontoRet(java.math.BigDecimal montoRet)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTORET$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MONTORET$22);
                      }
                      target.setBigDecimalValue(montoRet);
                    }
                }
                
                /**
                 * Sets (as xml) the "MontoRet" element
                 */
                public void xsetMontoRet(org.apache.xmlbeans.XmlDecimal montoRet)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTORET$22, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(MONTORET$22);
                      }
                      target.set(montoRet);
                    }
                }
                
                /**
                 * Gets the "SignoMontoImp" element
                 */
                public java.lang.String getSignoMontoImp()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNOMONTOIMP$24, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SignoMontoImp" element
                 */
                public org.apache.xmlbeans.XmlString xgetSignoMontoImp()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNOMONTOIMP$24, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SignoMontoImp" element
                 */
                public void setSignoMontoImp(java.lang.String signoMontoImp)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNOMONTOIMP$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIGNOMONTOIMP$24);
                      }
                      target.setStringValue(signoMontoImp);
                    }
                }
                
                /**
                 * Sets (as xml) the "SignoMontoImp" element
                 */
                public void xsetSignoMontoImp(org.apache.xmlbeans.XmlString signoMontoImp)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNOMONTOIMP$24, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIGNOMONTOIMP$24);
                      }
                      target.set(signoMontoImp);
                    }
                }
                
                /**
                 * Gets the "MontoImp" element
                 */
                public java.math.BigDecimal getMontoImp()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTOIMP$26, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MontoImp" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetMontoImp()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTOIMP$26, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MontoImp" element
                 */
                public void setMontoImp(java.math.BigDecimal montoImp)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTOIMP$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MONTOIMP$26);
                      }
                      target.setBigDecimalValue(montoImp);
                    }
                }
                
                /**
                 * Sets (as xml) the "MontoImp" element
                 */
                public void xsetMontoImp(org.apache.xmlbeans.XmlDecimal montoImp)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTOIMP$26, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(MONTOIMP$26);
                      }
                      target.set(montoImp);
                    }
                }
                
                /**
                 * Gets the "SignoMontoPag" element
                 */
                public java.lang.String getSignoMontoPag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNOMONTOPAG$28, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getStringValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "SignoMontoPag" element
                 */
                public org.apache.xmlbeans.XmlString xgetSignoMontoPag()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNOMONTOPAG$28, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "SignoMontoPag" element
                 */
                public void setSignoMontoPag(java.lang.String signoMontoPag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNOMONTOPAG$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIGNOMONTOPAG$28);
                      }
                      target.setStringValue(signoMontoPag);
                    }
                }
                
                /**
                 * Sets (as xml) the "SignoMontoPag" element
                 */
                public void xsetSignoMontoPag(org.apache.xmlbeans.XmlString signoMontoPag)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlString target = null;
                      target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNOMONTOPAG$28, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIGNOMONTOPAG$28);
                      }
                      target.set(signoMontoPag);
                    }
                }
                
                /**
                 * Gets the "MontoPago" element
                 */
                public java.math.BigDecimal getMontoPago()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTOPAGO$30, 0);
                      if (target == null)
                      {
                        return null;
                      }
                      return target.getBigDecimalValue();
                    }
                }
                
                /**
                 * Gets (as xml) the "MontoPago" element
                 */
                public org.apache.xmlbeans.XmlDecimal xgetMontoPago()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTOPAGO$30, 0);
                      return target;
                    }
                }
                
                /**
                 * Sets the "MontoPago" element
                 */
                public void setMontoPago(java.math.BigDecimal montoPago)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.SimpleValue target = null;
                      target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MONTOPAGO$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MONTOPAGO$30);
                      }
                      target.setBigDecimalValue(montoPago);
                    }
                }
                
                /**
                 * Sets (as xml) the "MontoPago" element
                 */
                public void xsetMontoPago(org.apache.xmlbeans.XmlDecimal montoPago)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      org.apache.xmlbeans.XmlDecimal target = null;
                      target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(MONTOPAGO$30, 0);
                      if (target == null)
                      {
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(MONTOPAGO$30);
                      }
                      target.set(montoPago);
                    }
                }
            }
            /**
             * An XML ListaCodigos(@).
             *
             * This is a complex type.
             */
            public static class ListaCodigosImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message.Formulario.ListaCodigos
            {
                
                public ListaCodigosImpl(org.apache.xmlbeans.SchemaType sType)
                {
                    super(sType);
                }
                
                private static final javax.xml.namespace.QName CODIGOS$0 = 
                    new javax.xml.namespace.QName("", "Codigos");
                
                
                /**
                 * Gets a List of "Codigos" elements
                 */
                public java.util.List<noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos> getCodigosList()
                {
                    final class CodigosList extends java.util.AbstractList<noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos>
                    {
                      public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos get(int i)
                          { return ListaCodigosImpl.this.getCodigosArray(i); }
                      
                      public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos set(int i, noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos o)
                      {
                        noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos old = ListaCodigosImpl.this.getCodigosArray(i);
                        ListaCodigosImpl.this.setCodigosArray(i, o);
                        return old;
                      }
                      
                      public void add(int i, noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos o)
                          { ListaCodigosImpl.this.insertNewCodigos(i).set(o); }
                      
                      public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos remove(int i)
                      {
                        noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos old = ListaCodigosImpl.this.getCodigosArray(i);
                        ListaCodigosImpl.this.removeCodigos(i);
                        return old;
                      }
                      
                      public int size()
                          { return ListaCodigosImpl.this.sizeOfCodigosArray(); }
                      
                    }
                    
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return new CodigosList();
                    }
                }
                
                /**
                 * Gets array of all "Codigos" elements
                 */
                public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[] getCodigosArray()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      java.util.List targetList = new java.util.ArrayList();
                      get_store().find_all_element_users(CODIGOS$0, targetList);
                      noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[] result = new noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[targetList.size()];
                      targetList.toArray(result);
                      return result;
                    }
                }
                
                /**
                 * Gets ith "Codigos" element
                 */
                public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos getCodigosArray(int i)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos target = null;
                      target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos)get_store().find_element_user(CODIGOS$0, i);
                      if (target == null)
                      {
                        throw new IndexOutOfBoundsException();
                      }
                      return target;
                    }
                }
                
                /**
                 * Returns number of "Codigos" element
                 */
                public int sizeOfCodigosArray()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      return get_store().count_elements(CODIGOS$0);
                    }
                }
                
                /**
                 * Sets array of all "Codigos" element
                 */
                public void setCodigosArray(noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[] codigosArray)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      arraySetterHelper(codigosArray, CODIGOS$0);
                    }
                }
                
                /**
                 * Sets ith "Codigos" element
                 */
                public void setCodigosArray(int i, noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos codigos)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos target = null;
                      target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos)get_store().find_element_user(CODIGOS$0, i);
                      if (target == null)
                      {
                        throw new IndexOutOfBoundsException();
                      }
                      target.set(codigos);
                    }
                }
                
                /**
                 * Inserts and returns a new empty value (as xml) as the ith "Codigos" element
                 */
                public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos insertNewCodigos(int i)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos target = null;
                      target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos)get_store().insert_element_user(CODIGOS$0, i);
                      return target;
                    }
                }
                
                /**
                 * Appends and returns a new empty value (as xml) as the last "Codigos" element
                 */
                public noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos addNewCodigos()
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos target = null;
                      target = (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos)get_store().add_element_user(CODIGOS$0);
                      return target;
                    }
                }
                
                /**
                 * Removes the ith "Codigos" element
                 */
                public void removeCodigos(int i)
                {
                    synchronized (monitor())
                    {
                      check_orphaned();
                      get_store().remove_element(CODIGOS$0, i);
                    }
                }
                /**
                 * An XML Codigos(@).
                 *
                 * This is a complex type.
                 */
                public static class CodigosImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos
                {
                    
                    public CodigosImpl(org.apache.xmlbeans.SchemaType sType)
                    {
                      super(sType);
                    }
                    
                    private static final javax.xml.namespace.QName CODIGO$0 = 
                      new javax.xml.namespace.QName("", "Codigo");
                    private static final javax.xml.namespace.QName SIGNO$2 = 
                      new javax.xml.namespace.QName("", "Signo");
                    private static final javax.xml.namespace.QName CONTENIDO$4 = 
                      new javax.xml.namespace.QName("", "Contenido");
                    private static final javax.xml.namespace.QName NRO$6 = 
                      new javax.xml.namespace.QName("", "nro");
                    
                    
                    /**
                     * Gets the "Codigo" element
                     */
                    public java.math.BigDecimal getCodigo()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODIGO$0, 0);
                        if (target == null)
                        {
                          return null;
                        }
                        return target.getBigDecimalValue();
                      }
                    }
                    
                    /**
                     * Gets (as xml) the "Codigo" element
                     */
                    public org.apache.xmlbeans.XmlDecimal xgetCodigo()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlDecimal target = null;
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(CODIGO$0, 0);
                        return target;
                      }
                    }
                    
                    /**
                     * Sets the "Codigo" element
                     */
                    public void setCodigo(java.math.BigDecimal codigo)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CODIGO$0, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CODIGO$0);
                        }
                        target.setBigDecimalValue(codigo);
                      }
                    }
                    
                    /**
                     * Sets (as xml) the "Codigo" element
                     */
                    public void xsetCodigo(org.apache.xmlbeans.XmlDecimal codigo)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlDecimal target = null;
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(CODIGO$0, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(CODIGO$0);
                        }
                        target.set(codigo);
                      }
                    }
                    
                    /**
                     * Gets the "Signo" element
                     */
                    public java.lang.String getSigno()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNO$2, 0);
                        if (target == null)
                        {
                          return null;
                        }
                        return target.getStringValue();
                      }
                    }
                    
                    /**
                     * Gets (as xml) the "Signo" element
                     */
                    public org.apache.xmlbeans.XmlString xgetSigno()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlString target = null;
                        target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNO$2, 0);
                        return target;
                      }
                    }
                    
                    /**
                     * Sets the "Signo" element
                     */
                    public void setSigno(java.lang.String signo)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SIGNO$2, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SIGNO$2);
                        }
                        target.setStringValue(signo);
                      }
                    }
                    
                    /**
                     * Sets (as xml) the "Signo" element
                     */
                    public void xsetSigno(org.apache.xmlbeans.XmlString signo)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlString target = null;
                        target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SIGNO$2, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SIGNO$2);
                        }
                        target.set(signo);
                      }
                    }
                    
                    /**
                     * Gets the "Contenido" element
                     */
                    public java.lang.String getContenido()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENIDO$4, 0);
                        if (target == null)
                        {
                          return null;
                        }
                        return target.getStringValue();
                      }
                    }
                    
                    /**
                     * Gets (as xml) the "Contenido" element
                     */
                    public org.apache.xmlbeans.XmlString xgetContenido()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlString target = null;
                        target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENIDO$4, 0);
                        return target;
                      }
                    }
                    
                    /**
                     * Sets the "Contenido" element
                     */
                    public void setContenido(java.lang.String contenido)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENIDO$4, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTENIDO$4);
                        }
                        target.setStringValue(contenido);
                      }
                    }
                    
                    /**
                     * Sets (as xml) the "Contenido" element
                     */
                    public void xsetContenido(org.apache.xmlbeans.XmlString contenido)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlString target = null;
                        target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENIDO$4, 0);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTENIDO$4);
                        }
                        target.set(contenido);
                      }
                    }
                    
                    /**
                     * Gets the "nro" attribute
                     */
                    public java.math.BigDecimal getNro()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NRO$6);
                        if (target == null)
                        {
                          return null;
                        }
                        return target.getBigDecimalValue();
                      }
                    }
                    
                    /**
                     * Gets (as xml) the "nro" attribute
                     */
                    public org.apache.xmlbeans.XmlDecimal xgetNro()
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlDecimal target = null;
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().find_attribute_user(NRO$6);
                        return target;
                      }
                    }
                    
                    /**
                     * Sets the "nro" attribute
                     */
                    public void setNro(java.math.BigDecimal nro)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.SimpleValue target = null;
                        target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(NRO$6);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(NRO$6);
                        }
                        target.setBigDecimalValue(nro);
                      }
                    }
                    
                    /**
                     * Sets (as xml) the "nro" attribute
                     */
                    public void xsetNro(org.apache.xmlbeans.XmlDecimal nro)
                    {
                      synchronized (monitor())
                      {
                        check_orphaned();
                        org.apache.xmlbeans.XmlDecimal target = null;
                        target = (org.apache.xmlbeans.XmlDecimal)get_store().find_attribute_user(NRO$6);
                        if (target == null)
                        {
                          target = (org.apache.xmlbeans.XmlDecimal)get_store().add_attribute_user(NRO$6);
                        }
                        target.set(nro);
                      }
                    }
                }
            }
        }
    }
}

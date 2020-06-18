/*
 * An XML document type.
 * Localname: Message
 * Namespace: 
 * Java type: noNamespace.MessageDocument
 *
 * Automatically generated - do not modify.
 */
package noNamespace;


/**
 * A document containing one Message(@) element.
 *
 * This is a complex type.
 */
public interface MessageDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MessageDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("messagec54edoctype");
    
    /**
     * Gets the "Message" element
     */
    noNamespace.MessageDocument.Message getMessage();
    
    /**
     * Sets the "Message" element
     */
    void setMessage(noNamespace.MessageDocument.Message message);
    
    /**
     * Appends and returns a new empty "Message" element
     */
    noNamespace.MessageDocument.Message addNewMessage();
    
    /**
     * An XML Message(@).
     *
     * This is a complex type.
     */
    public interface Message extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Message.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("message09f1elemtype");
        
        /**
         * Gets the "MessageId" element
         */
        noNamespace.MessageDocument.Message.MessageId getMessageId();
        
        /**
         * Sets the "MessageId" element
         */
        void setMessageId(noNamespace.MessageDocument.Message.MessageId messageId);
        
        /**
         * Appends and returns a new empty "MessageId" element
         */
        noNamespace.MessageDocument.Message.MessageId addNewMessageId();
        
        /**
         * Gets a List of "Formulario" elements
         */
        java.util.List<noNamespace.MessageDocument.Message.Formulario> getFormularioList();
        
        /**
         * Gets array of all "Formulario" elements
         * @deprecated
         */
        noNamespace.MessageDocument.Message.Formulario[] getFormularioArray();
        
        /**
         * Gets ith "Formulario" element
         */
        noNamespace.MessageDocument.Message.Formulario getFormularioArray(int i);
        
        /**
         * Returns number of "Formulario" element
         */
        int sizeOfFormularioArray();
        
        /**
         * Sets array of all "Formulario" element
         */
        void setFormularioArray(noNamespace.MessageDocument.Message.Formulario[] formularioArray);
        
        /**
         * Sets ith "Formulario" element
         */
        void setFormularioArray(int i, noNamespace.MessageDocument.Message.Formulario formulario);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "Formulario" element
         */
        noNamespace.MessageDocument.Message.Formulario insertNewFormulario(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "Formulario" element
         */
        noNamespace.MessageDocument.Message.Formulario addNewFormulario();
        
        /**
         * Removes the ith "Formulario" element
         */
        void removeFormulario(int i);
        
        /**
         * An XML MessageId(@).
         *
         * This is a complex type.
         */
        public interface MessageId extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(MessageId.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("messageid00cfelemtype");
            
            /**
             * Gets the "Code" element
             */
            java.lang.String getCode();
            
            /**
             * Gets (as xml) the "Code" element
             */
            org.apache.xmlbeans.XmlString xgetCode();
            
            /**
             * Sets the "Code" element
             */
            void setCode(java.lang.String code);
            
            /**
             * Sets (as xml) the "Code" element
             */
            void xsetCode(org.apache.xmlbeans.XmlString code);
            
            /**
             * Gets the "MsgDesc" element
             */
            java.lang.String getMsgDesc();
            
            /**
             * Gets (as xml) the "MsgDesc" element
             */
            org.apache.xmlbeans.XmlString xgetMsgDesc();
            
            /**
             * Sets the "MsgDesc" element
             */
            void setMsgDesc(java.lang.String msgDesc);
            
            /**
             * Sets (as xml) the "MsgDesc" element
             */
            void xsetMsgDesc(org.apache.xmlbeans.XmlString msgDesc);
            
            /**
             * Gets the "Version" element
             */
            long getVersion();
            
            /**
             * Gets (as xml) the "Version" element
             */
            org.apache.xmlbeans.XmlLong xgetVersion();
            
            /**
             * Sets the "Version" element
             */
            void setVersion(long version);
            
            /**
             * Sets (as xml) the "Version" element
             */
            void xsetVersion(org.apache.xmlbeans.XmlLong version);
            
            /**
             * Gets the "FechaVersion" element
             */
            java.lang.String getFechaVersion();
            
            /**
             * Gets (as xml) the "FechaVersion" element
             */
            org.apache.xmlbeans.XmlString xgetFechaVersion();
            
            /**
             * Sets the "FechaVersion" element
             */
            void setFechaVersion(java.lang.String fechaVersion);
            
            /**
             * Sets (as xml) the "FechaVersion" element
             */
            void xsetFechaVersion(org.apache.xmlbeans.XmlString fechaVersion);
            
            /**
             * Gets the "FromAddress" element
             */
            java.lang.String getFromAddress();
            
            /**
             * Gets (as xml) the "FromAddress" element
             */
            org.apache.xmlbeans.XmlString xgetFromAddress();
            
            /**
             * Sets the "FromAddress" element
             */
            void setFromAddress(java.lang.String fromAddress);
            
            /**
             * Sets (as xml) the "FromAddress" element
             */
            void xsetFromAddress(org.apache.xmlbeans.XmlString fromAddress);
            
            /**
             * Gets the "ToAddress" element
             */
            java.lang.String getToAddress();
            
            /**
             * Gets (as xml) the "ToAddress" element
             */
            org.apache.xmlbeans.XmlString xgetToAddress();
            
            /**
             * Sets the "ToAddress" element
             */
            void setToAddress(java.lang.String toAddress);
            
            /**
             * Sets (as xml) the "ToAddress" element
             */
            void xsetToAddress(org.apache.xmlbeans.XmlString toAddress);
            
            /**
             * Gets the "RefAddress" element
             */
            java.math.BigDecimal getRefAddress();
            
            /**
             * Gets (as xml) the "RefAddress" element
             */
            org.apache.xmlbeans.XmlDecimal xgetRefAddress();
            
            /**
             * Sets the "RefAddress" element
             */
            void setRefAddress(java.math.BigDecimal refAddress);
            
            /**
             * Sets (as xml) the "RefAddress" element
             */
            void xsetRefAddress(org.apache.xmlbeans.XmlDecimal refAddress);
            
            /**
             * Gets the "DateTime" element
             */
            java.lang.String getDateTime();
            
            /**
             * Gets (as xml) the "DateTime" element
             */
            org.apache.xmlbeans.XmlString xgetDateTime();
            
            /**
             * Sets the "DateTime" element
             */
            void setDateTime(java.lang.String dateTime);
            
            /**
             * Sets (as xml) the "DateTime" element
             */
            void xsetDateTime(org.apache.xmlbeans.XmlString dateTime);
            
            /**
             * Gets the "Validado" element
             */
            java.lang.String getValidado();
            
            /**
             * Gets (as xml) the "Validado" element
             */
            org.apache.xmlbeans.XmlString xgetValidado();
            
            /**
             * Sets the "Validado" element
             */
            void setValidado(java.lang.String validado);
            
            /**
             * Sets (as xml) the "Validado" element
             */
            void xsetValidado(org.apache.xmlbeans.XmlString validado);
            
            /**
             * Gets the "Number" element
             */
            java.math.BigDecimal getNumber();
            
            /**
             * Gets (as xml) the "Number" element
             */
            org.apache.xmlbeans.XmlDecimal xgetNumber();
            
            /**
             * Sets the "Number" element
             */
            void setNumber(java.math.BigDecimal number);
            
            /**
             * Sets (as xml) the "Number" element
             */
            void xsetNumber(org.apache.xmlbeans.XmlDecimal number);
            
            /**
             * Gets the "PeriodoContable" element
             */
            java.math.BigDecimal getPeriodoContable();
            
            /**
             * Gets (as xml) the "PeriodoContable" element
             */
            org.apache.xmlbeans.XmlDecimal xgetPeriodoContable();
            
            /**
             * Sets the "PeriodoContable" element
             */
            void setPeriodoContable(java.math.BigDecimal periodoContable);
            
            /**
             * Sets (as xml) the "PeriodoContable" element
             */
            void xsetPeriodoContable(org.apache.xmlbeans.XmlDecimal periodoContable);
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.MessageDocument.Message.MessageId newInstance() {
                  return (noNamespace.MessageDocument.Message.MessageId) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.MessageDocument.Message.MessageId newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.MessageDocument.Message.MessageId) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * An XML Formulario(@).
         *
         * This is a complex type.
         */
        public interface Formulario extends org.apache.xmlbeans.XmlObject
        {
            public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Formulario.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("formulario118felemtype");
            
            /**
             * Gets the "Identificacion" element
             */
            noNamespace.MessageDocument.Message.Formulario.Identificacion getIdentificacion();
            
            /**
             * Sets the "Identificacion" element
             */
            void setIdentificacion(noNamespace.MessageDocument.Message.Formulario.Identificacion identificacion);
            
            /**
             * Appends and returns a new empty "Identificacion" element
             */
            noNamespace.MessageDocument.Message.Formulario.Identificacion addNewIdentificacion();
            
            /**
             * Gets the "ListaCodigos" element
             */
            noNamespace.MessageDocument.Message.Formulario.ListaCodigos getListaCodigos();
            
            /**
             * Sets the "ListaCodigos" element
             */
            void setListaCodigos(noNamespace.MessageDocument.Message.Formulario.ListaCodigos listaCodigos);
            
            /**
             * Appends and returns a new empty "ListaCodigos" element
             */
            noNamespace.MessageDocument.Message.Formulario.ListaCodigos addNewListaCodigos();
            
            /**
             * Gets the "nro" attribute
             */
            java.math.BigDecimal getNro();
            
            /**
             * Gets (as xml) the "nro" attribute
             */
            org.apache.xmlbeans.XmlDecimal xgetNro();
            
            /**
             * Sets the "nro" attribute
             */
            void setNro(java.math.BigDecimal nro);
            
            /**
             * Sets (as xml) the "nro" attribute
             */
            void xsetNro(org.apache.xmlbeans.XmlDecimal nro);
            
            /**
             * An XML Identificacion(@).
             *
             * This is a complex type.
             */
            public interface Identificacion extends org.apache.xmlbeans.XmlObject
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Identificacion.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("identificacion4624elemtype");
                
                /**
                 * Gets the "RutIra" element
                 */
                java.math.BigDecimal getRutIra();
                
                /**
                 * Gets (as xml) the "RutIra" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetRutIra();
                
                /**
                 * Sets the "RutIra" element
                 */
                void setRutIra(java.math.BigDecimal rutIra);
                
                /**
                 * Sets (as xml) the "RutIra" element
                 */
                void xsetRutIra(org.apache.xmlbeans.XmlDecimal rutIra);
                
                /**
                 * Gets the "DvIra" element
                 */
                java.lang.String getDvIra();
                
                /**
                 * Gets (as xml) the "DvIra" element
                 */
                org.apache.xmlbeans.XmlString xgetDvIra();
                
                /**
                 * Sets the "DvIra" element
                 */
                void setDvIra(java.lang.String dvIra);
                
                /**
                 * Sets (as xml) the "DvIra" element
                 */
                void xsetDvIra(org.apache.xmlbeans.XmlString dvIra);
                
                /**
                 * Gets the "FolioF01" element
                 */
                java.math.BigDecimal getFolioF01();
                
                /**
                 * Gets (as xml) the "FolioF01" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetFolioF01();
                
                /**
                 * Sets the "FolioF01" element
                 */
                void setFolioF01(java.math.BigDecimal folioF01);
                
                /**
                 * Sets (as xml) the "FolioF01" element
                 */
                void xsetFolioF01(org.apache.xmlbeans.XmlDecimal folioF01);
                
                /**
                 * Gets the "Formulario" element
                 */
                java.math.BigDecimal getFormulario();
                
                /**
                 * Gets (as xml) the "Formulario" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetFormulario();
                
                /**
                 * Sets the "Formulario" element
                 */
                void setFormulario(java.math.BigDecimal formulario);
                
                /**
                 * Sets (as xml) the "Formulario" element
                 */
                void xsetFormulario(org.apache.xmlbeans.XmlDecimal formulario);
                
                /**
                 * Gets the "Periodo" element
                 */
                java.math.BigDecimal getPeriodo();
                
                /**
                 * Gets (as xml) the "Periodo" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetPeriodo();
                
                /**
                 * Sets the "Periodo" element
                 */
                void setPeriodo(java.math.BigDecimal periodo);
                
                /**
                 * Sets (as xml) the "Periodo" element
                 */
                void xsetPeriodo(org.apache.xmlbeans.XmlDecimal periodo);
                
                /**
                 * Gets the "FolioDecl" element
                 */
                java.math.BigDecimal getFolioDecl();
                
                /**
                 * Gets (as xml) the "FolioDecl" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetFolioDecl();
                
                /**
                 * Sets the "FolioDecl" element
                 */
                void setFolioDecl(java.math.BigDecimal folioDecl);
                
                /**
                 * Sets (as xml) the "FolioDecl" element
                 */
                void xsetFolioDecl(org.apache.xmlbeans.XmlDecimal folioDecl);
                
                /**
                 * Gets the "Signo" element
                 */
                java.lang.String getSigno();
                
                /**
                 * Gets (as xml) the "Signo" element
                 */
                org.apache.xmlbeans.XmlString xgetSigno();
                
                /**
                 * Sets the "Signo" element
                 */
                void setSigno(java.lang.String signo);
                
                /**
                 * Sets (as xml) the "Signo" element
                 */
                void xsetSigno(org.apache.xmlbeans.XmlString signo);
                
                /**
                 * Gets the "RutContr" element
                 */
                java.math.BigDecimal getRutContr();
                
                /**
                 * Gets (as xml) the "RutContr" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetRutContr();
                
                /**
                 * Sets the "RutContr" element
                 */
                void setRutContr(java.math.BigDecimal rutContr);
                
                /**
                 * Sets (as xml) the "RutContr" element
                 */
                void xsetRutContr(org.apache.xmlbeans.XmlDecimal rutContr);
                
                /**
                 * Gets the "DvContr" element
                 */
                java.lang.String getDvContr();
                
                /**
                 * Gets (as xml) the "DvContr" element
                 */
                org.apache.xmlbeans.XmlString xgetDvContr();
                
                /**
                 * Sets the "DvContr" element
                 */
                void setDvContr(java.lang.String dvContr);
                
                /**
                 * Sets (as xml) the "DvContr" element
                 */
                void xsetDvContr(org.apache.xmlbeans.XmlString dvContr);
                
                /**
                 * Gets the "MarcaFisc" element
                 */
                java.math.BigDecimal getMarcaFisc();
                
                /**
                 * Gets (as xml) the "MarcaFisc" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetMarcaFisc();
                
                /**
                 * Sets the "MarcaFisc" element
                 */
                void setMarcaFisc(java.math.BigDecimal marcaFisc);
                
                /**
                 * Sets (as xml) the "MarcaFisc" element
                 */
                void xsetMarcaFisc(org.apache.xmlbeans.XmlDecimal marcaFisc);
                
                /**
                 * Gets the "GlosaFisc" element
                 */
                java.lang.String getGlosaFisc();
                
                /**
                 * Gets (as xml) the "GlosaFisc" element
                 */
                org.apache.xmlbeans.XmlString xgetGlosaFisc();
                
                /**
                 * Sets the "GlosaFisc" element
                 */
                void setGlosaFisc(java.lang.String glosaFisc);
                
                /**
                 * Sets (as xml) the "GlosaFisc" element
                 */
                void xsetGlosaFisc(org.apache.xmlbeans.XmlString glosaFisc);
                
                /**
                 * Gets the "MontoRet" element
                 */
                java.math.BigDecimal getMontoRet();
                
                /**
                 * Gets (as xml) the "MontoRet" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetMontoRet();
                
                /**
                 * Sets the "MontoRet" element
                 */
                void setMontoRet(java.math.BigDecimal montoRet);
                
                /**
                 * Sets (as xml) the "MontoRet" element
                 */
                void xsetMontoRet(org.apache.xmlbeans.XmlDecimal montoRet);
                
                /**
                 * Gets the "SignoMontoImp" element
                 */
                java.lang.String getSignoMontoImp();
                
                /**
                 * Gets (as xml) the "SignoMontoImp" element
                 */
                org.apache.xmlbeans.XmlString xgetSignoMontoImp();
                
                /**
                 * Sets the "SignoMontoImp" element
                 */
                void setSignoMontoImp(java.lang.String signoMontoImp);
                
                /**
                 * Sets (as xml) the "SignoMontoImp" element
                 */
                void xsetSignoMontoImp(org.apache.xmlbeans.XmlString signoMontoImp);
                
                /**
                 * Gets the "MontoImp" element
                 */
                java.math.BigDecimal getMontoImp();
                
                /**
                 * Gets (as xml) the "MontoImp" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetMontoImp();
                
                /**
                 * Sets the "MontoImp" element
                 */
                void setMontoImp(java.math.BigDecimal montoImp);
                
                /**
                 * Sets (as xml) the "MontoImp" element
                 */
                void xsetMontoImp(org.apache.xmlbeans.XmlDecimal montoImp);
                
                /**
                 * Gets the "SignoMontoPag" element
                 */
                java.lang.String getSignoMontoPag();
                
                /**
                 * Gets (as xml) the "SignoMontoPag" element
                 */
                org.apache.xmlbeans.XmlString xgetSignoMontoPag();
                
                /**
                 * Sets the "SignoMontoPag" element
                 */
                void setSignoMontoPag(java.lang.String signoMontoPag);
                
                /**
                 * Sets (as xml) the "SignoMontoPag" element
                 */
                void xsetSignoMontoPag(org.apache.xmlbeans.XmlString signoMontoPag);
                
                /**
                 * Gets the "MontoPago" element
                 */
                java.math.BigDecimal getMontoPago();
                
                /**
                 * Gets (as xml) the "MontoPago" element
                 */
                org.apache.xmlbeans.XmlDecimal xgetMontoPago();
                
                /**
                 * Sets the "MontoPago" element
                 */
                void setMontoPago(java.math.BigDecimal montoPago);
                
                /**
                 * Sets (as xml) the "MontoPago" element
                 */
                void xsetMontoPago(org.apache.xmlbeans.XmlDecimal montoPago);
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.MessageDocument.Message.Formulario.Identificacion newInstance() {
                      return (noNamespace.MessageDocument.Message.Formulario.Identificacion) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.MessageDocument.Message.Formulario.Identificacion newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.MessageDocument.Message.Formulario.Identificacion) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * An XML ListaCodigos(@).
             *
             * This is a complex type.
             */
            public interface ListaCodigos extends org.apache.xmlbeans.XmlObject
            {
                public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                    org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ListaCodigos.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("listacodigosc34celemtype");
                
                /**
                 * Gets a List of "Codigos" elements
                 */
                java.util.List<noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos> getCodigosList();
                
                /**
                 * Gets array of all "Codigos" elements
                 * @deprecated
                 */
                noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[] getCodigosArray();
                
                /**
                 * Gets ith "Codigos" element
                 */
                noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos getCodigosArray(int i);
                
                /**
                 * Returns number of "Codigos" element
                 */
                int sizeOfCodigosArray();
                
                /**
                 * Sets array of all "Codigos" element
                 */
                void setCodigosArray(noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos[] codigosArray);
                
                /**
                 * Sets ith "Codigos" element
                 */
                void setCodigosArray(int i, noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos codigos);
                
                /**
                 * Inserts and returns a new empty value (as xml) as the ith "Codigos" element
                 */
                noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos insertNewCodigos(int i);
                
                /**
                 * Appends and returns a new empty value (as xml) as the last "Codigos" element
                 */
                noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos addNewCodigos();
                
                /**
                 * Removes the ith "Codigos" element
                 */
                void removeCodigos(int i);
                
                /**
                 * An XML Codigos(@).
                 *
                 * This is a complex type.
                 */
                public interface Codigos extends org.apache.xmlbeans.XmlObject
                {
                    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
                      org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Codigos.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.RentaSchemaTypeSystem").resolveHandle("codigosf4a2elemtype");
                    
                    /**
                     * Gets the "Codigo" element
                     */
                    java.math.BigDecimal getCodigo();
                    
                    /**
                     * Gets (as xml) the "Codigo" element
                     */
                    org.apache.xmlbeans.XmlDecimal xgetCodigo();
                    
                    /**
                     * Sets the "Codigo" element
                     */
                    void setCodigo(java.math.BigDecimal codigo);
                    
                    /**
                     * Sets (as xml) the "Codigo" element
                     */
                    void xsetCodigo(org.apache.xmlbeans.XmlDecimal codigo);
                    
                    /**
                     * Gets the "Signo" element
                     */
                    java.lang.String getSigno();
                    
                    /**
                     * Gets (as xml) the "Signo" element
                     */
                    org.apache.xmlbeans.XmlString xgetSigno();
                    
                    /**
                     * Sets the "Signo" element
                     */
                    void setSigno(java.lang.String signo);
                    
                    /**
                     * Sets (as xml) the "Signo" element
                     */
                    void xsetSigno(org.apache.xmlbeans.XmlString signo);
                    
                    /**
                     * Gets the "Contenido" element
                     */
                    java.lang.String getContenido();
                    
                    /**
                     * Gets (as xml) the "Contenido" element
                     */
                    org.apache.xmlbeans.XmlString xgetContenido();
                    
                    /**
                     * Sets the "Contenido" element
                     */
                    void setContenido(java.lang.String contenido);
                    
                    /**
                     * Sets (as xml) the "Contenido" element
                     */
                    void xsetContenido(org.apache.xmlbeans.XmlString contenido);
                    
                    /**
                     * Gets the "nro" attribute
                     */
                    java.math.BigDecimal getNro();
                    
                    /**
                     * Gets (as xml) the "nro" attribute
                     */
                    org.apache.xmlbeans.XmlDecimal xgetNro();
                    
                    /**
                     * Sets the "nro" attribute
                     */
                    void setNro(java.math.BigDecimal nro);
                    
                    /**
                     * Sets (as xml) the "nro" attribute
                     */
                    void xsetNro(org.apache.xmlbeans.XmlDecimal nro);
                    
                    /**
                     * A factory class with static methods for creating instances
                     * of this type.
                     */
                    
                    public static final class Factory
                    {
                      public static noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos newInstance() {
                        return (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                      
                      public static noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos newInstance(org.apache.xmlbeans.XmlOptions options) {
                        return (noNamespace.MessageDocument.Message.Formulario.ListaCodigos.Codigos) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                      
                      private Factory() { } // No instance of this class allowed
                    }
                }
                
                /**
                 * A factory class with static methods for creating instances
                 * of this type.
                 */
                
                public static final class Factory
                {
                    public static noNamespace.MessageDocument.Message.Formulario.ListaCodigos newInstance() {
                      return (noNamespace.MessageDocument.Message.Formulario.ListaCodigos) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                    
                    public static noNamespace.MessageDocument.Message.Formulario.ListaCodigos newInstance(org.apache.xmlbeans.XmlOptions options) {
                      return (noNamespace.MessageDocument.Message.Formulario.ListaCodigos) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                    
                    private Factory() { } // No instance of this class allowed
                }
            }
            
            /**
             * A factory class with static methods for creating instances
             * of this type.
             */
            
            public static final class Factory
            {
                public static noNamespace.MessageDocument.Message.Formulario newInstance() {
                  return (noNamespace.MessageDocument.Message.Formulario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
                
                public static noNamespace.MessageDocument.Message.Formulario newInstance(org.apache.xmlbeans.XmlOptions options) {
                  return (noNamespace.MessageDocument.Message.Formulario) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
                
                private Factory() { } // No instance of this class allowed
            }
        }
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static noNamespace.MessageDocument.Message newInstance() {
              return (noNamespace.MessageDocument.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static noNamespace.MessageDocument.Message newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (noNamespace.MessageDocument.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static noNamespace.MessageDocument newInstance() {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static noNamespace.MessageDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static noNamespace.MessageDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
        	
        	//org.apache.xmlbeans.XmlObject
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static noNamespace.MessageDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static noNamespace.MessageDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static noNamespace.MessageDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static noNamespace.MessageDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static noNamespace.MessageDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static noNamespace.MessageDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static noNamespace.MessageDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static noNamespace.MessageDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static noNamespace.MessageDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static noNamespace.MessageDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static noNamespace.MessageDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static noNamespace.MessageDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static noNamespace.MessageDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.MessageDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static noNamespace.MessageDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (noNamespace.MessageDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

package cl.tesoreria.finanzas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class CargarProperties {
	private static Properties rentaProps =null;;
	private static InputStream in = null;
	private static Logger logger = Logger.getLogger("cl.tesoreria.finanzas.CargarProperties"); 
	
	static{
		try {

			in = CargarProperties.class.getClassLoader().getResourceAsStream("RentaMonedaExtranjera.properties");
			rentaProps=new Properties();
			rentaProps.load(in);
            in.close();
            logger.info("------>>>>> Carga de propiedades Exitosa : ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("Error en el metodo CargarProperties()1 : " + e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Error en el metodo CargarProperties()2 : " + e);			            
		}
				
	}

	private CargarProperties() {}
	
	public static String getValueProp(String arg){	
        //System.out.println(rentaProps.get(arg));
		return (String)rentaProps.get(arg);	
        	
	}
    
    public static Collection getValuePropIntoColl(String key){
		String valor=(String)getValueProp(key);
		String newKey=valor.substring(1, valor.length()-1);
		List lista=(List)Arrays.asList(newKey.split(","));		
		return (Collection)lista;
	}
	
    public static Hashtable getHashTableOfProperValue(String key){
		Hashtable ht=null;
		if(key!=null && key.trim().length()>0){
			ht= convertStringToHashTable(getValueProp(key));
		}		
		return ht;
	}
	
	
	protected static Hashtable convertStringToHashTable(String arrayS){
		Hashtable ht=new Hashtable();
		int index0=0,index1=0;
		index0=arrayS.indexOf("[");
		index1=arrayS.indexOf("]");
		String newString =arrayS.substring(index0+1,index1);
		StringTokenizer stokens=new StringTokenizer(newString,",");
		while(stokens.hasMoreElements()){
			String items=(String) stokens.nextElement();
			StringTokenizer item=new StringTokenizer(items,"=");
			String key=(String) item.nextElement();
			String value=(String) item.nextElement();
			ht.put(key.trim(),value.trim());
		}
		return ht;	
	}	
}
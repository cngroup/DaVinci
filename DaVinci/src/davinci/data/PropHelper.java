/*
 * DaVinci V1.0 is a light weighted visualization framework and toolkit. 
 * The design of DaVinci based on the information visualization reference model.
 * Some of the packages are ported from prefuse (http://prefuse.org/). All these packages 
 * keep the original file headers and copyright information. Please read and follow it 
 * if you want to use them.
 * 
 * The original motivation of creating this project is to design a light weighted, 
 * simple and easy to use information visualization framework that facilitates the 
 * Ph.D study of the author. For any other purposes please notify the author through 
 * email.
 * 
 * DaVinci V1.0 is under the MIT opensource license.
 * 
 * Author : Nan Cao
 * Email: nan.cao@gmail.com
 * Homepage: http://www.cse.ust.hk/~nancao/
 * Project Homepage: http://www.cse.ust.hk/~nancao/architecture.html
 * V1 Release Date : 1st June 2010
 */
package davinci.data;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import davinci.data.elem.IEdge;
import davinci.data.elem.IElement;


public final class PropHelper {
	
	public PropHelper() {
	}
	
	/**
	 * Check if the class1 is the subclass of class2
	 * 
	 * @param class1
	 * @param class2
	 * @return
	 */
	public static boolean isSubClassOf(Class class1, Class class2) {
		if(class1 == null) {
			return false;
		}
		
		if(class2.equals(class1)) {
			return true;
		} else {
			return isSubClassOf(class1.getSuperclass(), class2);
		}
	}
	
	public static String getID(Object data) {
		if(data instanceof String) {
			return (String)data;
		} else if(data instanceof IElement) {
			return ((IElement)data).getID();
		} else {
			Object id = getProperty("id", data);
			return id == null ? null : id.toString();
		}
	}
	
	public static String getLabel(Object data) {
		if(data instanceof IElement) {
			return ((IElement)data).getLabel();
		} else {
			Object label = getProperty("label", data);
			return label == null ? "" : (String)label;
		}
	}
	
	public static Object getProperty(String key, Object data) {
		if(data instanceof Map) {
			return ((Map)data).get(key);
		} else if(data instanceof Hashtable) {
			return ((Hashtable)data).get(key);
		} else if(data instanceof IElement) {
			return ((IElement)data).get(key);
		}
		return null;
	}
	
	public static void setProperty(Object data, String key, Object value) {
		if(data instanceof Map) {
			((Map)data).put(key, value);
		} else if(data instanceof Hashtable) {
			((Hashtable)data).put(key, value);
		}  else if(data instanceof IElement) {
			((IElement)data).put(key, value);
		}
	}
	
	public static void removeProperty(Object data, String key) {
		if(data instanceof Map) {
			((Map)data).remove(key);
		} else if(data instanceof Hashtable) {
			((Hashtable)data).remove(key);
		} else if(data instanceof IElement) {
			((IElement)data).remove(key);
		}
	}
	
	public static Object[] getKeyCollection(Object data) {
		
		if(data instanceof Map) {
			return ((Map)data).keySet().toArray();
		} else if(data instanceof Hashtable) {
			return ((Hashtable)data).keySet().toArray();
		}
		
		return null;
	}
	
	public static Object[] getValueCollection(Object data) {
		
		if(data instanceof Map) {
			return ((Map)data).values().toArray();
		} else if(data instanceof Hashtable) {
			return ((Hashtable)data).values().toArray();
		}
		return null;
	}
	
	public static void clear(Object data) {
		if(data instanceof Map) {
			((Map)data).clear();
		} else if(data instanceof Hashtable) {
			((Hashtable)data).clear();
		}
		if(data instanceof IEdge) {
			((IEdge)data).setFirstNode(null);
			((IEdge)data).setSecondNode(null);
		}
	}

	public static double getDouble(String key, Object data) {
		Object value = getProperty(key, data);
		
		if(null == value) {
			return 0;
		} else if(value instanceof Number) {
			return ((Number)value).doubleValue();
		} else if(value instanceof String) {
			return Double.parseDouble((String)value);
		} else {
			return 0;
		}
	}

	public static float getFloat(String key, Object data) {
		Object value = getProperty(key, data);
		
		if(null == value) {
			return 0;
		} else if(value instanceof Number) {
			return ((Number)value).floatValue();
		} else if(value instanceof String) {
			return Float.parseFloat((String)value);
		} else {
			return 0;
		}
	}

	public static String getString(String key, Object data) {
		Object value = getProperty(key, data);
		
		if(null == value) {
			return null;
		} else if(value instanceof String) {
			return (String)value;
		} else {
			return value.toString();
		}
	}

	public static long getLong(String key, Object data) {
		Object value = getProperty(key, data);
		if(null == value) {
			return 0;
		} else if(value instanceof Number) {
			return ((Number)value).longValue();
		} else if(value instanceof String) {
			return Long.parseLong((String)value);
		} else {
			return 0;
		}
	}

	public static int getInteger(String key, Object data) {
		Object value = getProperty(key, data);
		
		if(null == value) {
			return 0;
		} else if(value instanceof Number) {
			return ((Number)value).intValue();
		} else if(value instanceof String) {
			return Integer.parseInt((String)value);
		} else {
			return 0;
		}
	}

	public static Date getDate(String key, Object data) {
		Object value = getProperty(key, data);
		
		if(null == value) {
			return null;
		} else if(value instanceof Date) {
			return (Date)value;
		} else {
			return null;
		}
	}
	
	public static Object getObject(String key, Object data) {
		Object value = getProperty(key, data);
		if(null == value) {
			return null;
		} else if(value instanceof Date) {
			return (Date)value;
		} else {
			return null;
		}
	}
}

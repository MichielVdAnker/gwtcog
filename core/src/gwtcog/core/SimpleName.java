package gwtcog.core;

public class SimpleName {

	/**
	 * @return the simple name of the underlying class. Will be the empty string for anonymous classes.
	 */
	public static final String get(Object object) {

		String typeName = object.getClass().getName();
		int lastDot = typeName.lastIndexOf('.');
		if (lastDot >= 0) {
			int lastDollar = typeName.lastIndexOf('$', lastDot);
			if (lastDollar > lastDot) {
				lastDot = lastDollar;
			}
			return typeName.substring(lastDot + 1);
		}
		return typeName;
		
	}
	
	public static final String getFromClass(Class object) {

		String typeName = object.getName();
		int lastDot = typeName.lastIndexOf('.');
		if (lastDot >= 0) {
			int lastDollar = typeName.lastIndexOf('$', lastDot);
			if (lastDollar > lastDot) {
				lastDot = lastDollar;
			}
			return typeName.substring(lastDot + 1);
		}
		return typeName;
		
	}

}

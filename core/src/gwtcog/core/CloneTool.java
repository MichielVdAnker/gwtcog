package gwtcog.core;

public class CloneTool {

	public static final byte[] clone(byte[] ints) {
		byte[] copy = new byte[ints.length];
		for(int i=0;i<ints.length;i++) {
			copy[i] = ints[i];
		}
		return copy;
	}
	
	public static final int[] clone(int[] ints) {
		int[] copy = new int[ints.length];
		for(int i=0;i<ints.length;i++) {
			copy[i] = ints[i];
		}
		return copy;
	}

	public static double[] clone(double[] doubles) {
		double[] copy = new double[doubles.length];
		for(int i=0;i<doubles.length;i++) {
			copy[i] = doubles[i];
		}
		return copy;
	}

	public static double[][] clone(double[][] doubles) {
		double[][] copy = new double[doubles.length][];
		for(int i=0;i<doubles.length;i++) {
			copy[i] = clone(doubles[i]);
		}
		return copy;
	}
	
	public static Object[] clone(Object[] objects) {
		Object[] copy = new Object[objects.length];
		for(int i=0;i<objects.length;i++) {
			copy[i] = objects[i];
		}
		return copy;
	}

	public static Object[][] clone(Object[][] objects) {
		Object[][] copy = new Object[objects.length][];
		for(int i=0;i<objects.length;i++) {
			copy[i] = clone(objects[i]);
		}
		return copy;
	}
	
}

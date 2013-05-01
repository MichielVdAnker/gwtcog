package gwtcog.core;

public class CloneTool {

	public static final int[] clone(int[] ints) {
		int[] newInts = new int[ints.length];
		for(int i=0;i<ints.length;i++) {
			newInts[i] = ints[i];
		}
		return newInts;
	}

	public static double[] clone(double[] doubles) {
		double[] newDoubles = new double[doubles.length];
		for(int i=0;i<doubles.length;i++) {
			newDoubles[i] = doubles[i];
		}
		return newDoubles;
	}

	public static double[][] clone(double[][] doubles) {
		double[][] newDoubles = new double[doubles.length][];
		for(int i=0;i<doubles.length;i++) {
			newDoubles[i] = clone(doubles[i]);
		}
		return newDoubles;
	}
	
}

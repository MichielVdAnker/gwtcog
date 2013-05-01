package gwtcog.core.neural.rbf.training;
///*
// * Encog(tm) Core v3.2 - Java Version
// * http://www.heatonresearch.com/encog/
// * https://github.com/encog/encog-java-core
// 
// * Copyright 2008-2013 Heaton Research, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *   
// * For more information on Heaton Research copyrights, licenses 
// * and trademarks visit:
// * http://www.heatonresearch.com/copyright
// */
//package gwtcog.core.neural.rbf.training;
//
//import gwtcog.core.mathutil.rbf.RadialBasisFunction;
//import gwtcog.core.ml.TrainingImplementationType;
//import gwtcog.core.ml.data.MLDataSet;
//import gwtcog.core.ml.train.BasicTraining;
//import gwtcog.core.neural.networks.training.TrainingError;
//import gwtcog.core.neural.networks.training.propagation.TrainingContinuation;
//import gwtcog.core.neural.rbf.RBFNetwork;
//import gwtcog.core.util.ObjectPair;
//import gwtcog.core.util.simple.TrainingSetUtil;
//
///**
// * Train a RBF neural network using a SVD.
// * 
// * Contributed to Encog By M.Fletcher and M.Dean University of Cambridge, Dept.
// * of Physics, UK
// * 
// */
//public class SVDTraining extends BasicTraining {
//
//	/**
//	 * The network that is to be trained.
//	 */
//	private final RBFNetwork network;
//
//	/**
//	 * Construct the training object.
//	 * 
//	 * @param network
//	 *            The network to train. Must have a single output neuron.
//	 * @param training
//	 *            The training data to use. Must be indexable.
//	 */
//	public SVDTraining(final RBFNetwork network, final MLDataSet training) {
//		super(TrainingImplementationType.OnePass);
//		if (network.getOutputCount() != 1) {
//			throw new TrainingError(
//					"SVD requires an output layer with a single neuron.");
//		}
//
//		setTraining(training);
//		this.network = network;
//	}
//
//	@Override
//	public boolean canContinue() {
//		return false;
//	}
//
//	public void flatToMatrix(final double[] flat, final int start,
//			final double[][] matrix) {
//		final int rows = matrix.length;
//		final int cols = matrix[0].length;
//
//		int index = start;
//
//		for (int r = 0; r < rows; r++) {
//			for (int c = 0; c < cols; c++) {
//				matrix[r][c] = flat[index++];
//			}
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public RBFNetwork getMethod() {
//		return this.network;
//	}
//
//	/**
//	 * Perform one iteration.
//	 */
//	@Override
//	public void iteration() {
//		final int length = this.network.getRBF().length;
//
//		final RadialBasisFunction[] funcs = new RadialBasisFunction[length];
//
//		// Iteration over neurons and determine the necessaries
//		for (int i = 0; i < length; i++) {
//			final RadialBasisFunction basisFunc = this.network.getRBF()[i];
//
//			funcs[i] = basisFunc;
//
//			// This is the value that is changed using other training methods.
//			// weights[i] =
//			// network.Structure.Synapses[0].WeightMatrix.Data[i][j];
//		}
//
//		final ObjectPair<double[][], double[][]> data = TrainingSetUtil
//				.trainingToArray(getTraining());
//
//		final double[][] matrix = new double[length][this.network
//				.getOutputCount()];
//
//		flatToMatrix(this.network.getFlat().getWeights(), 0, matrix);
//		setError(SVD.svdfit(data.getA(), data.getB(), matrix, funcs));
//		matrixToFlat(matrix, this.network.getFlat().getWeights(), 0);
//	}
//
//	/**
//	 * Convert the matrix to flat.
//	 * @param matrix The matrix.
//	 * @param flat Flat array.
//	 * @param start WHere to start.
//	 */
//	public void matrixToFlat(final double[][] matrix, final double[] flat,
//			final int start) {
//		final int rows = matrix.length;
//		final int cols = matrix[0].length;
//
//		int index = start;
//
//		for (int r = 0; r < rows; r++) {
//			for (int c = 0; c < cols; c++) {
//				flat[index++] = matrix[r][c];
//			}
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public TrainingContinuation pause() {
//		return null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public void resume(final TrainingContinuation state) {
//
//	}
//
//}

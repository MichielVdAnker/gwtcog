/*
 * Encog(tm) Core v3.2 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core
 
 * Copyright 2008-2013 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package gwtcog.core.ml.factory.method;

import gwtcog.core.EncogError;
import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.factory.parse.ArchitectureLayer;
import gwtcog.core.ml.factory.parse.ArchitectureParse;
import gwtcog.core.neural.NeuralNetworkError;
import gwtcog.core.neural.pnn.BasicPNN;
import gwtcog.core.neural.pnn.PNNKernelType;
import gwtcog.core.neural.pnn.PNNOutputMode;
import gwtcog.core.util.ParamsHolder;

import java.util.List;

/**
 * A factory to create PNN networks.
 */
public class PNNFactory {
	
	/**
	 * The max layer count.
	 */
	public static final int MAX_LAYERS = 3;
	
	/**
	 * Create a PNN network.
	 * @param architecture THe architecture string to use.
	 * @param input The input count. 
	 * @param output The output count.
	 * @return The RBF network.
	 */
	public MLMethod create(final String architecture, final int input,
			final int output) {

		final List<String> layers = ArchitectureParse.parseLayers(architecture);
		if (layers.size() != MAX_LAYERS) {
			throw new EncogError(
					"PNN Networks must have exactly three elements, " 
					+ "separated by ->.");
		}

		final ArchitectureLayer inputLayer = ArchitectureParse.parseLayer(
				layers.get(0), input);
		final ArchitectureLayer pnnLayer = ArchitectureParse.parseLayer(
				layers.get(1), -1);
		final ArchitectureLayer outputLayer = ArchitectureParse.parseLayer(
				layers.get(2), output);

		final int inputCount = inputLayer.getCount();
		final int outputCount = outputLayer.getCount();

		PNNKernelType kernel;
		PNNOutputMode outmodel;

		if (pnnLayer.getName().equalsIgnoreCase("c")) {
			outmodel = PNNOutputMode.Classification;
		} else if (pnnLayer.getName().equalsIgnoreCase("r")) {
			outmodel = PNNOutputMode.Regression;
		} else if (pnnLayer.getName().equalsIgnoreCase("u")) {
			outmodel = PNNOutputMode.Unsupervised;
		} else {
			throw new NeuralNetworkError("Unknown model: " 
					+ pnnLayer.getName());
		}

		final ParamsHolder holder = new ParamsHolder(pnnLayer.getParams());

		final String kernelStr = holder.getString("KERNEL", false, "gaussian");
		
		if (kernelStr.equalsIgnoreCase("gaussian")) {
			kernel = PNNKernelType.Gaussian;
		} else if (kernelStr.equalsIgnoreCase("reciprocal")) {
			kernel = PNNKernelType.Reciprocal;
		} else {
			throw new NeuralNetworkError("Unknown kernel: " + kernelStr);
		}
			
		final BasicPNN result = new BasicPNN(kernel, outmodel,
				inputCount, outputCount);

		return result;
	}
}

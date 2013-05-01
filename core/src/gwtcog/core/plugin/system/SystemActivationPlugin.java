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
package gwtcog.core.plugin.system;

import gwtcog.core.EncogError;
import gwtcog.core.engine.network.activation.ActivationBiPolar;
import gwtcog.core.engine.network.activation.ActivationCompetitive;
import gwtcog.core.engine.network.activation.ActivationFunction;
import gwtcog.core.engine.network.activation.ActivationGaussian;
import gwtcog.core.engine.network.activation.ActivationLOG;
import gwtcog.core.engine.network.activation.ActivationLinear;
import gwtcog.core.engine.network.activation.ActivationRamp;
import gwtcog.core.engine.network.activation.ActivationSIN;
import gwtcog.core.engine.network.activation.ActivationSigmoid;
import gwtcog.core.engine.network.activation.ActivationSoftMax;
import gwtcog.core.engine.network.activation.ActivationSteepenedSigmoid;
import gwtcog.core.engine.network.activation.ActivationStep;
import gwtcog.core.engine.network.activation.ActivationTANH;
import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.factory.MLActivationFactory;
import gwtcog.core.ml.train.MLTrain;
import gwtcog.core.plugin.EncogPluginBase;
import gwtcog.core.plugin.EncogPluginService1;
import gwtcog.core.util.csv.CSVFormat;
import gwtcog.core.util.csv.NumberList;

public class SystemActivationPlugin implements EncogPluginService1 {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getPluginDescription() {
		return "This plugin provides the built in machine " +
				"learning methods for Encog.";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String getPluginName() {
		return "HRI-System-Methods";
	}

	/**
	 * @return This is a type-1 plugin.
	 */
	@Override
	public final int getPluginType() {
		return 1;
	}
	
	private ActivationFunction allocateAF(String name) {
		if (name.equalsIgnoreCase(MLActivationFactory.AF_BIPOLAR)) {
			return new ActivationBiPolar();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_COMPETITIVE)) {
			return new ActivationCompetitive();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_GAUSSIAN)) {
			return new ActivationGaussian();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_LINEAR)) {
			return new ActivationLinear();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_LOG)) {
			return new ActivationLOG();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_RAMP)) {
			return new ActivationRamp();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_SIGMOID)) {
			return new ActivationSigmoid();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_SIN)) {
			return new ActivationSIN();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_SOFTMAX)) {
			return new ActivationSoftMax();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_STEP)) {
			return new ActivationStep();
		}

		if (name.equalsIgnoreCase(MLActivationFactory.AF_TANH)) {
			return new ActivationTANH();
		}
		
		if( name.equalsIgnoreCase(MLActivationFactory.AF_SSIGMOID)) {
			return new ActivationSteepenedSigmoid();
		}

		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivationFunction createActivationFunction(String fn) {
		String name;
		double[] params;

		int index = fn.indexOf('[');
		if (index != -1) {
			name = fn.substring(0, index).toLowerCase();
			int index2 = fn.indexOf(']');
			if (index2 == -1) {
				throw new EncogError(
						"Unbounded [ while parsing activation function.");
			}
			String a = fn.substring(index + 1, index2);
			params = NumberList.fromList(CSVFormat.EG_FORMAT, a);

		} else {
			name = fn.toLowerCase();
			params = new double[0];
		}

		ActivationFunction af = allocateAF(name);
		
		if( af==null ) {
			return null;
		}

		if (af.getParamNames().length != params.length) {
			throw new EncogError(name + " expected "
					+ af.getParamNames().length + ", but " + params.length
					+ " were provided.");
		}

		for (int i = 0; i < af.getParamNames().length; i++) {
			af.setParam(i, params[i]);
		}

		return af;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MLMethod createMethod(String methodType, String architecture,
			int input, int output) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MLTrain createTraining(MLMethod method, MLDataSet training,
			String type, String args) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPluginServiceType() {
		return EncogPluginBase.TYPE_SERVICE;
	}
}

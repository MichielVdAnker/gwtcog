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
package gwtcog.core.ml.factory.train;

import gwtcog.core.EncogError;
import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.train.MLTrain;
import gwtcog.core.neural.networks.training.pnn.TrainBasicPNN;
import gwtcog.core.neural.pnn.BasicPNN;

/**
 * A factory used to create PNN trainers. 
 *
 */
public class PNNTrainFactory {
	
	/**
	 * Create a PNN trainer.
	 * 
	 * @param method
	 *            The method to use.
	 * @param training
	 *            The training data to use.
	 * @param args
	 *            The arguments to use.
	 * @return The newly created trainer.
	 */
	public MLTrain create(final MLMethod method, 
			final MLDataSet training,
			final String args) {

		if (!(method instanceof BasicPNN)) {
			throw new EncogError(
					"PNN training cannot be used on a method of type: "
							+ method.getClass().getName());
		}

		return new TrainBasicPNN((BasicPNN) method, training);
	}
}

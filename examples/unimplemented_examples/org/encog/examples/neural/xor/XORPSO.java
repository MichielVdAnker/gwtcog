/*
 * Encog(tm) Java Examples v3.2
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-examples
 *
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
package gwtcog.examples.client.org.encog.examples.neural.xor;

import gwtcog.core.Encog;
import gwtcog.core.mathutil.randomize.NguyenWidrowRandomizer;
import gwtcog.core.mathutil.randomize.Randomizer;
import gwtcog.core.ml.CalculateScore;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.ml.train.MLTrain;
import gwtcog.core.neural.networks.BasicNetwork;
import gwtcog.core.neural.networks.training.TrainingSetScore;
import gwtcog.core.util.simple.EncogUtility;

/**
 * XOR-PSO: This example solves the classic XOR operator neural
 * network problem.  However, it uses PSO training.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class XORPSO {
	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
			{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	public static void main(final String args[]) {

		MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
		BasicNetwork network = EncogUtility.simpleFeedForward(2, 2, 0, 1, false);
		CalculateScore score = new TrainingSetScore(trainingSet);
		Randomizer randomizer = new NguyenWidrowRandomizer();
		
		final MLTrain train = new NeuralPSO(network,randomizer,score,20);
		
		EncogUtility.trainToError(train, 0.01);

		network = (BasicNetwork)train.getMethod();

		// test the neural network
		System.out.println("Neural Network Results:");
		EncogUtility.evaluate(network, trainingSet);
		
		Encog.getInstance().shutdown();
	}
}

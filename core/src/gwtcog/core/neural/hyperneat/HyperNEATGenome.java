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
package gwtcog.core.neural.hyperneat;

import gwtcog.core.engine.network.activation.ActivationBipolarSteepenedSigmoid;
import gwtcog.core.engine.network.activation.ActivationClippedLinear;
import gwtcog.core.engine.network.activation.ActivationFunction;
import gwtcog.core.engine.network.activation.ActivationGaussian;
import gwtcog.core.engine.network.activation.ActivationSIN;
import gwtcog.core.neural.neat.NEATPopulation;
import gwtcog.core.neural.neat.training.NEATGenome;
import gwtcog.core.neural.neat.training.NEATLinkGene;
import gwtcog.core.neural.neat.training.NEATNeuronGene;
import gwtcog.core.util.obj.ChooseObject;

import java.util.List;
import java.util.Random;

public class HyperNEATGenome extends NEATGenome {

	public static void buildCPPNActivationFunctions(
			final ChooseObject<ActivationFunction> activationFunctions) {
		activationFunctions.add(0.25, new ActivationClippedLinear());
		activationFunctions.add(0.25, new ActivationBipolarSteepenedSigmoid());
		activationFunctions.add(0.25, new ActivationGaussian());
		activationFunctions.add(0.25, new ActivationSIN());
		activationFunctions.finalizeStructure();
	}

	public HyperNEATGenome() {

	}

	public HyperNEATGenome(final HyperNEATGenome other) {
		super(other);
	}

	public HyperNEATGenome(final List<NEATNeuronGene> neurons,
			final List<NEATLinkGene> links, final int inputCount,
			final int outputCount) {
		super(neurons, links, inputCount, outputCount);
	}

	public HyperNEATGenome(final Random rnd, final NEATPopulation pop,
			final int inputCount, final int outputCount,
			final double connectionDensity) {
		super(rnd, pop, inputCount, outputCount, connectionDensity);

	}
}

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

import gwtcog.core.engine.network.activation.ActivationFunction;
import gwtcog.core.engine.network.activation.ActivationSteepenedSigmoid;
import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.data.MLData;
import gwtcog.core.ml.data.basic.BasicMLData;
import gwtcog.core.ml.ea.codec.GeneticCODEC;
import gwtcog.core.ml.ea.genome.Genome;
import gwtcog.core.ml.genetic.GeneticError;
import gwtcog.core.neural.hyperneat.substrate.Substrate;
import gwtcog.core.neural.hyperneat.substrate.SubstrateLink;
import gwtcog.core.neural.hyperneat.substrate.SubstrateNode;
import gwtcog.core.neural.neat.NEATCODEC;
import gwtcog.core.neural.neat.NEATLink;
import gwtcog.core.neural.neat.NEATNetwork;
import gwtcog.core.neural.neat.NEATPopulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HyperNEATCODEC implements GeneticCODEC {

	private double minWeight = 0.2;
	private double maxWeight = 5.0;

	@Override
	public MLMethod decode(final Genome genome) {
		final NEATPopulation pop = (NEATPopulation) genome.getPopulation();
		final Substrate substrate = pop.getSubstrate();
		return decode(pop, substrate, genome);
	}

	public MLMethod decode(final NEATPopulation pop, final Substrate substrate,
			final Genome genome) {
		// obtain the CPPN
		final NEATCODEC neatCodec = new NEATCODEC();
		final NEATNetwork cppn = (NEATNetwork) neatCodec.decode(genome);

		final List<NEATLink> linkList = new ArrayList<NEATLink>();

		final ActivationFunction[] afs = new ActivationFunction[substrate
				.getNodeCount()];

		final ActivationFunction af = new ActivationSteepenedSigmoid();
		// all activation functions are the same
		for (int i = 0; i < afs.length; i++) {
			afs[i] = af;
		}

		final double c = this.maxWeight / (1.0 - this.minWeight);
		final MLData input = new BasicMLData(cppn.getInputCount());

		// First create all of the non-bias links.
		for (final SubstrateLink link : substrate.getLinks()) {
			final SubstrateNode source = link.getSource();
			final SubstrateNode target = link.getTarget();

			int index = 0;
			for (final double d : source.getLocation()) {
				input.setData(index++, d);
			}
			for (final double d : target.getLocation()) {
				input.setData(index++, d);
			}
			final MLData output = cppn.compute(input);

			double weight = output.getData(0);
			if (Math.abs(weight) > this.minWeight) {
				weight = (Math.abs(weight) - this.minWeight) * c
						* Math.signum(weight);
				linkList.add(new NEATLink(source.getId(), target.getId(),
						weight));
			}
		}

		// now create biased links
		input.clear();
		final int d = substrate.getDimensions();
		final List<SubstrateNode> biasedNodes = substrate.getBiasedNodes();
		for (final SubstrateNode target : biasedNodes) {
			for (int i = 0; i < d; i++) {
				input.setData(d + i, target.getLocation()[i]);
			}

			final MLData output = cppn.compute(input);

			double biasWeight = output.getData(1);
			if (Math.abs(biasWeight) > this.minWeight) {
				biasWeight = (Math.abs(biasWeight) - this.minWeight) * c
						* Math.signum(biasWeight);
				linkList.add(new NEATLink(0, target.getId(), biasWeight));
			}
		}

		// check for invalid neural network
		if (linkList.size() == 0) {
			return null;
		}

		Collections.sort(linkList);

		final NEATNetwork network = new NEATNetwork(substrate.getInputCount(),
				substrate.getOutputCount(), linkList, afs);

		network.setActivationCycles(substrate.getActivationCycles());
		return network;

	}

	@Override
	public Genome encode(final MLMethod phenotype) {
		throw new GeneticError(
				"Encoding of a HyperNEAT network is not supported.");
	}

	/**
	 * @return the maxWeight
	 */
	public double getMaxWeight() {
		return this.maxWeight;
	}

	/**
	 * @return the minWeight
	 */
	public double getMinWeight() {
		return this.minWeight;
	}

	/**
	 * @param maxWeight
	 *            the maxWeight to set
	 */
	public void setMaxWeight(final double maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @param minWeight
	 *            the minWeight to set
	 */
	public void setMinWeight(final double minWeight) {
		this.minWeight = minWeight;
	}

}

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
package gwtcog.core.neural.neat.training.opp.links;

import gwtcog.core.ml.ea.train.EvolutionaryAlgorithm;
import gwtcog.core.neural.neat.training.NEATLinkGene;

import java.util.Random;

/**
 * This interface defines various ways that a NEAT network can have its link
 * weights mutated.
 * 
 * -----------------------------------------------------------------------------
 * http://www.cs.ucf.edu/~kstanley/ Encog's NEAT implementation was drawn from
 * the following three Journal Articles. For more complete BibTeX sources, see
 * NEATNetwork.java.
 * 
 * Evolving Neural Networks Through Augmenting Topologies
 * 
 * Generating Large-Scale Neural Networks Through Discovering Geometric
 * Regularities
 * 
 * Automatic feature selection in neuroevolution
 */
public interface MutateLinkWeight {

	/**
	 * @return The training class that this mutator is being used with.
	 */
	EvolutionaryAlgorithm getTrainer();

	/**
	 * Setup the link mutator.
	 * 
	 * @param theTrainer
	 *            The training class that this mutator is used with.
	 */
	void init(EvolutionaryAlgorithm theTrainer);

	/**
	 * Perform the weight mutation on the specified link.
	 * 
	 * @param rnd
	 *            A random number generator.
	 * @param linkGene
	 *            The link to mutate.
	 * @param weightRange
	 *            The weight range, weights are between -weightRange and
	 *            +weightRange.
	 */
	void mutateWeight(Random rnd, NEATLinkGene linkGene, double weightRange);

}

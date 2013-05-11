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
package gwtcog.core.ml.genetic;

import gwtcog.core.ml.CalculateScore;
import gwtcog.core.ml.MLEncodable;
import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.MethodFactory;
import gwtcog.core.ml.TrainingImplementationType;
import gwtcog.core.ml.ea.genome.Genome;
import gwtcog.core.ml.ea.population.BasicPopulation;
import gwtcog.core.ml.ea.population.Population;
import gwtcog.core.ml.ea.sort.GenomeComparator;
import gwtcog.core.ml.ea.sort.MaximizeScoreComp;
import gwtcog.core.ml.ea.sort.MinimizeScoreComp;
import gwtcog.core.ml.ea.species.Species;
import gwtcog.core.ml.ea.train.basic.TrainEA;
import gwtcog.core.ml.genetic.crossover.Splice;
import gwtcog.core.ml.genetic.mutate.MutatePerturb;
import gwtcog.core.ml.train.BasicTraining;
import gwtcog.core.neural.networks.training.propagation.TrainingContinuation;
import gwtcog.core.util.logging.EncogLogging;

/**
 * Implements a genetic algorithm that allows an MLMethod that is encodable (MLEncodable)
 * to be trained.  It works well with both BasicNetwork and FreeformNetwork class, as well
 * as any MLEncodable class.
 * 
 * There are essentially two ways you can make use of this class.
 * 
 * Either way, you will need a score object. The score object tells the genetic
 * algorithm how well suited a neural network is.
 * 
 * If you would like to use genetic algorithms with a training set you should
 * make use TrainingSetScore class. This score object uses a training set to
 * score your neural network.
 * 
 * If you would like to be more abstract, and not use a training set, you can
 * create your own implementation of the CalculateScore method. This class can
 * then score the networks any way that you like.
 */
public class MLMethodGeneticAlgorithm extends BasicTraining {

	/**
	 * Very simple class that implements a genetic algorithm.
	 * 
	 * @author jheaton
	 */
	public class MLMethodGeneticAlgorithmHelper extends TrainEA {
		/**
		 * The serial id.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Construct the helper.
		 * @param thePopulation The population.
		 * @param theScoreFunction The score function.
		 */
		public MLMethodGeneticAlgorithmHelper(Population thePopulation,
				CalculateScore theScoreFunction) {
			super(thePopulation, theScoreFunction);
		}
	}

	/**
	 * Simple helper class that implements the required methods to implement a
	 * genetic algorithm.
	 */
	private MLMethodGeneticAlgorithmHelper genetic;

	/**
	 * Construct a method genetic algorithm.
	 * @param phenotypeFactory The phenotype factory.
	 * @param calculateScore The score calculation object.
	 * @param populationSize The population size.
	 */
	public MLMethodGeneticAlgorithm(final MethodFactory phenotypeFactory,
			final CalculateScore calculateScore,
			final int populationSize) {
		super(TrainingImplementationType.Iterative);
		
		final Population population = new BasicPopulation(populationSize, null);
		population.setGenomeFactory(new MLMethodGenomeFactory(phenotypeFactory,population));
		this.genetic = new MLMethodGeneticAlgorithmHelper(population, calculateScore);
		this.genetic.setCODEC(new MLEncodableCODEC());
		
		GenomeComparator comp = null;
		if( calculateScore.shouldMinimize() ) {
			comp = new MinimizeScoreComp();
		} else {
			comp = new MaximizeScoreComp();
		}
		this.genetic.setBestComparator(comp);
		this.genetic.setSelectionComparator(comp);

		Species defaultSpecies = population.createSpecies();

		for (int i = 0; i < population.getPopulationSize(); i++) {
			final MLEncodable chromosomeNetwork = (MLEncodable)phenotypeFactory.factor();
			final MLMethodGenome genome = new MLMethodGenome(chromosomeNetwork);
			getGenetic().calculateScore(genome);
			defaultSpecies.add(genome);
		}
		defaultSpecies.setLeader(defaultSpecies.getMembers().get(0));
		
		int s = Math.max(defaultSpecies.getMembers().get(0).size()/5,1);
		getGenetic().setPopulation(population);
		
		this.genetic.addOperation(0.9,new Splice(s));
		this.genetic.addOperation(0.1,new MutatePerturb(1.0));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canContinue() {
		return false;
	}

	/**
	 * @return The genetic algorithm implementation.
	 */
	public MLMethodGeneticAlgorithmHelper getGenetic() {
		return this.genetic;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MLMethod getMethod() {
		Genome best = genetic.getBestGenome();		
		return this.genetic.getCODEC().decode(best);
	}

	/**
	 * Perform one training iteration.
	 */
	@Override
	public void iteration() {

		EncogLogging.log(EncogLogging.LEVEL_INFO,
				"Performing Genetic iteration.");
		preIteration();
		setError(getGenetic().getError());
		getGenetic().iteration();
		setError(getGenetic().getError());
		postIteration();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TrainingContinuation pause() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resume(final TrainingContinuation state) {

	}

	/**
	 * Set the genetic helper class.
	 * 
	 * @param genetic
	 *            The genetic helper class.
	 */
	public void setGenetic(final MLMethodGeneticAlgorithmHelper genetic) {
		this.genetic = genetic;
	}

	//remnant from implments MultiThrseadable
//	@Override
//	public int getThreadCount() {
//		return this.genetic.getThreadCount();
//	}
//
//	@Override
//	public void setThreadCount(int numThreads) {
//		this.genetic.setThreadCount(numThreads);
//	}
}

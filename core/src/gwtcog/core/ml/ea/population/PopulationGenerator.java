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
package gwtcog.core.ml.ea.population;

import java.util.Random;

import gwtcog.core.ml.ea.genome.Genome;

/**
 * Generate a random population.
 */
public interface PopulationGenerator {
	/**
	 * Generate a random genome.
	 * @param rnd A random number generator.
	 * @return A random genome.
	 */
	Genome generate(Random rnd);

	/**
	 * Generate a random population.
	 * 
	 * @param rnd
	 *            Random number generator.
	 * @param pop
	 *            The population to generate into.
	 */
	void generate(Random rnd, Population pop);
}

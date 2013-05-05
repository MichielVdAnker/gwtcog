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
package gwtcog.examples.client.org.encog.examples.neural.predict.market;

import gwtcog.core.ConsoleStatusReportable;
import gwtcog.core.engine.network.activation.ActivationTANH;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.neural.pattern.FeedForwardPattern;
import gwtcog.core.neural.prune.PruneIncremental;
import gwtcog.core.util.simple.EncogUtility;

import java.io.File;

public class MarketPrune {

	public static void incremental(File dataDir) {
		File file = new File(dataDir, Config.TRAINING_FILE);

		if (!file.exists()) {
			System.out.println("Can't read file: " + file.getAbsolutePath());
			return;
		}

		MLDataSet training = EncogUtility.loadEGB2Memory(file);

		FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(training.getInputSize());
		pattern.setOutputNeurons(training.getIdealSize());
		pattern.setActivationFunction(new ActivationTANH());

		PruneIncremental prune = new PruneIncremental(training, pattern, 100, 1, 10,
				new ConsoleStatusReportable());

		prune.addHiddenLayer(5, 50);
		prune.addHiddenLayer(0, 50);

		prune.process();

		File networkFile = new File(dataDir, Config.NETWORK_FILE);
		EncogDirectoryPersistence.saveObject(networkFile, prune.getBestNetwork());

	}
}
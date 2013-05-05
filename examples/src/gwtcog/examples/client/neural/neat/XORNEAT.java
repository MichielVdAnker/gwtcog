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
 * 
 * #modified for gwtcog
 */
package gwtcog.examples.client.neural.neat;

import gwtcog.core.ml.CalculateScore;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.ml.ea.train.EvolutionaryAlgorithm;
import gwtcog.core.neural.neat.NEATNetwork;
import gwtcog.core.neural.neat.NEATPopulation;
import gwtcog.core.neural.neat.NEATUtil;
import gwtcog.core.neural.networks.training.TrainingSetScore;
import gwtcog.core.util.simple.EncogUtility;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

//original imports
//import org.encog.Encog;
//import org.encog.ml.CalculateScore;
//import org.encog.ml.data.MLDataSet;
//import org.encog.ml.data.basic.BasicMLDataSet;
//import org.encog.ml.ea.train.EvolutionaryAlgorithm;
//import org.encog.neural.neat.NEATNetwork;
//import org.encog.neural.neat.NEATPopulation;
//import org.encog.neural.neat.NEATUtil;
//import org.encog.neural.networks.training.TrainingSetScore;
//import org.encog.util.simple.EncogUtility;

/**
 * XOR-NEAT: This example solves the classic XOR operator neural
 * network problem.  However, it uses a NEAT evolving network.
 * 
 * @author $Author$
 * @version $Revision$
 */
public class XORNEAT implements RunnableDemo {
	
	private VerticalPanel panel;
	
	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
			{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	@Override
	public void run(Panel parent) {

		//setup output
		panel = new VerticalPanel();
		parent.add(panel);
		
		//setup close button
		panel.add(new RemoveDemoButton(parent,panel));
		
		//show we've started
		panel.add(new Label("XORNEAT Demo Started"));
		
		MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
		
		NEATPopulation pop = new NEATPopulation(2,1,1000);
		pop.setInitialConnectionDensity(1.0);// not required, but speeds training
		pop.reset();

		CalculateScore score = new TrainingSetScore(trainingSet);
		
		EvolutionaryAlgorithm train = NEATUtil.constructNEATTrainer(pop,score);

		//train the neural network
		train(train,0.01,pop,trainingSet);
	}
	
	private void train(final EvolutionaryAlgorithm train, final double error, final NEATPopulation pop, final MLDataSet trainingSet) {

		if(train.getError() > error) {
			Scheduler.get().scheduleDeferred(new Command() {
				public void execute () {
					train.iteration();
					panel.add(new Label("Epoch #" + train.getIteration() + " Error:" + train.getError()+ ", Species:" + pop.getSpecies().size()));
					train(train,error,pop,trainingSet);
				}
			});
		}
		else {
			finishTraining(train,error,pop,trainingSet);
		}

	}

	
	private void finishTraining(final EvolutionaryAlgorithm train, final double error, final NEATPopulation pop, final MLDataSet trainingSet) {

		NEATNetwork network = (NEATNetwork)train.getCODEC().decode(train.getBestGenome());

		// test the neural network
		panel.add(new Label("Neural Network Results:"));
		ArrayList<String> responses = EncogUtility.evaluate(network, trainingSet);
		
		for(String s : responses) {
			panel.add(new Label(s));
		}

		//?Encog.getInstance().shutdown();
		
	}
}

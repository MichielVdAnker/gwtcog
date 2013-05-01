package gwtcog.examples.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwtcog.core.Encog;
import gwtcog.core.ml.CalculateScore;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.ml.ea.train.EvolutionaryAlgorithm;
import gwtcog.core.neural.neat.NEATNetwork;
import gwtcog.core.neural.neat.NEATPopulation;
import gwtcog.core.neural.neat.NEATUtil;
import gwtcog.core.neural.networks.training.TrainingSetScore;
import gwtcog.core.util.simple.EncogUtility;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Demo implements EntryPoint {

	private VerticalPanel panel;

	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
		{ 0.0, 1.0 }, { 1.0, 1.0 } };

	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		//setup output
		panel = new VerticalPanel();
		RootPanel.get().add(panel);

		//show we've started
		panel.add(new Label("Started!"));

		MLDataSet trainingSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);
		
		NEATPopulation pop = new NEATPopulation(2,1,1000);
		pop.setInitialConnectionDensity(1.0);// not required, but speeds training
		pop.reset();

		CalculateScore score = new TrainingSetScore(trainingSet);
		
		EvolutionaryAlgorithm train = NEATUtil.constructNEATTrainer(pop,score);

		// train the neural network
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
		//		System.out.println("Neural Network Results:");
		ArrayList<String> responses = EncogUtility.evaluate(network, trainingSet);
		//		EncogUtility.evaluate(network, trainingSet);

		for(String s : responses) {
			panel.add(new Label(s));
		}

		Encog.getInstance().shutdown();
		
	}
}

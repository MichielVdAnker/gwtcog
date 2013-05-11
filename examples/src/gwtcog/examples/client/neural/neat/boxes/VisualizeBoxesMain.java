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
package gwtcog.examples.client.neural.neat.boxes;

import gwtcog.core.ml.ea.train.EvolutionaryAlgorithm;
import gwtcog.core.neural.hyperneat.substrate.Substrate;
import gwtcog.core.neural.hyperneat.substrate.SubstrateFactory;
import gwtcog.core.neural.neat.NEATPopulation;
import gwtcog.core.neural.neat.NEATUtil;
import gwtcog.core.neural.neat.training.species.OriginalNEATSpeciation;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This program demonstrates HyperNEAT.
 * 
 * The objective is to distinguish a large object from a small object in a two-
 * dimensional visual field. Because the same principle determines the
 * difference between small and large objects regardless of their location in
 * the retina, this task is well suited to testing the ability of HyperNEAT to
 * discover and exploit regularities.
 * 
 * This program will display two rectangles, one large, and one small. The
 * program seeks to place the red position indicator in the middle of the larger
 * rectangle. The program trains and attempts to gain the maximum score of 110.
 * Once training is complete, you can run multiple test cases and see the
 * program attempt to find the center.
 * 
 * One unique feature of HyperNEAT is that the resolution can be adjusted after
 * training has occured. This allows you to efficiently train on a small data
 * set and run with a much larger.
 * 
 */
public class VisualizeBoxesMain implements RunnableDemo {

	private DockLayoutPanel panel;			

	/**
	 * The serial id.
	 */
	//	private static final long serialVersionUID = 1L;
	private Button btnTraining;
	private Button btnExample;
	private boolean trainingUnderway;
	private Label labelIterations;
	private Label labelError;
	private Label labelSpecies;
	private boolean requestStop = false;
	private NEATPopulation pop;
	private EvolutionaryAlgorithm train;

	public void resetTraining() {
		Substrate substrate = SubstrateFactory.factorSandwichSubstrate(11, 11);
		BoxesScore score = new BoxesScore(11);
		pop = new NEATPopulation(substrate, 50);
		pop.setActivationCycles(4);
		pop.reset();
		train = NEATUtil.constructNEATTrainer(pop, score);
		OriginalNEATSpeciation speciation = new OriginalNEATSpeciation();
		speciation.setCompatibilityThreshold(1);
		train.setSpeciation(speciation = new OriginalNEATSpeciation());
		// train.setThreadCount(1);
	}

	@Override
	public void run(Panel parent) {

		//setup output
		panel = new DockLayoutPanel(Unit.EM);
		panel.setSize("400px", "600px");
		parent.add(panel);

		//show we've started
		//panel.add(new Label("NEATBoxes Demo Started"));

		HorizontalPanel buttonPanel = new HorizontalPanel();
		btnTraining = new Button("Start Training");
		btnTraining.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleTraining();
			}
			
		});

		btnExample = new Button("Run Example");
		btnExample.setEnabled(false);
		btnExample.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				panel.add(new DisplayBoxes(pop));
			}
			
		});

		//setup close button
		buttonPanel.add(new RemoveDemoButton(parent,panel));

		buttonPanel.add(btnTraining);
		buttonPanel.add(btnExample);
		panel.addNorth(buttonPanel,3);
		VerticalPanel mainPanel = new VerticalPanel();
		panel.addSouth(mainPanel,6);
		labelError = new Label("Current Score: N/A");
		labelIterations = new Label("Iterations: 0");
		labelSpecies = new Label("Species: 0");
		mainPanel.add(new Label("Target Score: 110"));
		mainPanel.add(labelError);
		mainPanel.add(labelIterations);
		mainPanel.add(labelSpecies);

		resetTraining();

	}

	private void beginTraining() {

		if (pop == null) {
			btnTraining.setEnabled(false);
			resetTraining();
		}

		// update the GUI
		btnTraining.setText("Stop Training");
		btnExample.setEnabled(false);
		trainingUnderway = true;

		requestStop = false;

		//Start the main loop
		Timer t = new Timer() {
			@Override
			public void run() {
				train();
			}
		};
		t.schedule(100);
	}

	private void train() {

		if(!requestStop && train.getError() < 110) {
			
			Scheduler.get().scheduleDeferred(new Command() {
				public void execute () {

					train.iteration();
					labelError.setText("Current Score: "+String.valueOf(train.getError()));//Format.formatDouble(train.getError(), 2));
					labelIterations.setText("Iterations: "+String.valueOf(train.getIteration()));
					//				Format.formatInteger(this.train
					//				.getIteration()));
					labelSpecies.setText("Species: "+String.valueOf(pop.getSpecies().size()));
					//				Format.formatInteger(this.pop
					//				.getSpecies().size()));
					finishTraining();
				}
			});

		}
		else {
			finishTraining();
		}

	}

	private void finishTraining() {
		
		train.finishTraining();

		btnTraining.setText("Start Training");
		btnExample.setEnabled(true);
		trainingUnderway = false;
	}

	public void handleTraining() {
		if (trainingUnderway) {
			requestStop = true;
		} else {
			beginTraining();
		}
	}

	//	@Override
	//	public void actionPerformed(ActionEvent ev) {
	//		if (ev.getSource() == this.btnTraining) {
	//			handleTraining();
	//		}
	//		if (ev.getSource() == this.btnExample) {
	//			DisplayBoxes display = new DisplayBoxes(pop);
	//			display.setVisible(true);
	//		}
	//	}

}

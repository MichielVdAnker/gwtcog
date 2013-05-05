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
package gwtcog.examples.client.neural.som;

import gwtcog.core.ml.data.MLData;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLData;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.neural.som.SOM;
import gwtcog.core.neural.som.training.basic.BasicTrainSOM;
import gwtcog.core.neural.som.training.basic.neighborhood.NeighborhoodSingle;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Implement a simple SOM using Encog.  It learns to recognize two patterns.
 * @author jeff
 *
 */
public class SimpleSOM implements RunnableDemo {

	private VerticalPanel panel;

	public static double SOM_INPUT[][] = { 
		{ -1.0, -1.0, 1.0, 1.0 }, 
		{ 1.0, 1.0, -1.0, -1.0 } };

	@Override
	public void run(Panel parent) {

		//setup output
		panel = new VerticalPanel();
		parent.add(panel);

		//setup close button
		panel.add(new RemoveDemoButton(parent,panel));

		//show we've started
		panel.add(new Label("SimpleSOM Demo Started"));

		Scheduler.get().scheduleDeferred(new Command() {
			public void execute () {
				
				// create the training set
				MLDataSet training = new BasicMLDataSet(SOM_INPUT,null);

				// Create the neural network.
				SOM network = new SOM(4,2);
				network.reset();

				BasicTrainSOM train = new BasicTrainSOM(
						network,
						0.7,
						training,
						new NeighborhoodSingle());

				int iteration = 0;

				for(iteration = 0;iteration<=10;iteration++) {
					train.iteration();
					panel.add(new Label("Iteration: " + iteration + ", Error:" + train.getError()));
				}

				MLData data1 = new BasicMLData(SOM_INPUT[0]);
				MLData data2 = new BasicMLData(SOM_INPUT[1]);
				panel.add(new Label("Pattern 1 winner: " + network.classify(data1)));
				panel.add(new Label("Pattern 2 winner: " + network.classify(data2)));
				
			}
		});
		
		//?Encog.getInstance().shutdown();
	}
}

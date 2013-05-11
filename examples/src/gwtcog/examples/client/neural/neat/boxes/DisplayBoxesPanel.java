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

import gwtcog.core.mathutil.IntPair;
import gwtcog.core.neural.hyperneat.HyperNEATCODEC;
import gwtcog.core.neural.hyperneat.substrate.Substrate;
import gwtcog.core.neural.hyperneat.substrate.SubstrateFactory;
import gwtcog.core.neural.neat.NEATNetwork;
import gwtcog.core.neural.neat.NEATPopulation;
import gwtcog.core.neural.neat.training.NEATGenome;

import java.util.Random;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.SimplePanel;

public class DisplayBoxesPanel extends SimplePanel {

	private Canvas canvas;

	/**
	 * The serial.
	 */
	//	private static final long serialVersionUID = 1L;
	private BoxTrialCase testCase = new BoxTrialCase(new Random());
	private NEATPopulation pop;
	private int resolution = BoxTrialCase.BASE_RESOLUTION;

	public DisplayBoxesPanel(NEATPopulation thePopulation) {
		testCase.initTestCase(0);
		this.pop = thePopulation;
		canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceHeight(400);
		canvas.setCoordinateSpaceWidth(400);
		canvas.setSize("400px", "400px");
		add(canvas);
	}

	//	@Override
	public void paint() {//(Graphics g) {

		Context2d g = canvas.getContext2d();

		NEATGenome genome = (NEATGenome) this.pop.getBestGenome();
		Substrate substrate = SubstrateFactory.factorSandwichSubstrate(resolution, resolution);
		HyperNEATCODEC codec = new HyperNEATCODEC();
		NEATNetwork phenotype = (NEATNetwork) codec.decode(this.pop, substrate, genome);		

		TrialEvaluation trial = new TrialEvaluation(phenotype, this.testCase);
		IntPair actualPos = trial.query(resolution);

		// clear what was there before
		//g.setColor(Color.white);
		g.setFillStyle("white");
		g.fillRect(0, 0, 400, 400);

		//
		int boxWidth = 400/resolution;
		int boxHeight = 400/resolution;
		double delta = 2.0 / resolution;
		int index = 0;

		for(int row = 0; row < resolution; row++ ) {
			double y = -1 + (row*delta);
			int boxY = row * boxHeight;
			for(int col = 0; col< resolution; col++ ) {
				double x = -1 + (col*delta);
				int boxX = col*boxWidth;

				if( this.testCase.getPixel(x, y)>0 ) {
					//g.setColor(Color.blue);
					g.setFillStyle("blue");
					g.fillRect(boxX, boxY, boxWidth, boxHeight);
				} else {
					double d = trial.getOutput().getData(index);

					int c = trial.normalize(d,255);
					String hex = Integer.toHexString(c);
					if(hex.length() == 1) {
						hex = "0"+hex;
					}
					String color = "#ff"+hex+"ff";
					g.setFillStyle(color);
					g.fillRect(boxX, boxY, boxWidth, boxHeight);
					
					g.setStrokeStyle("black");

					g.strokeRect(boxX, boxY, boxWidth, boxHeight);
					g.strokeRect(boxX+1, boxY+1, boxWidth-2, boxHeight-2);
				}
				index++;
			}
		}

		g.setFillStyle("red");
		g.fillRect(actualPos.getX()*boxWidth, actualPos.getY()*boxHeight, boxWidth, boxHeight);
	}

	public void createNewCase(int theResolution) {
		Random r = new Random();
		this.resolution = theResolution;
		this.testCase.initTestCase(r.nextInt(3));
		//this.repaint();
		paint();
	}	
}

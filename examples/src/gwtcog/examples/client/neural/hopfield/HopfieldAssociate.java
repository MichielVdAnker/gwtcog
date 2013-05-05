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
package gwtcog.examples.client.neural.hopfield;

import gwtcog.core.ml.data.specific.BiPolarNeuralData;
import gwtcog.core.neural.thermal.HopfieldNetwork;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Simple class to recognize some patterns with a Hopfield Neural Network.
 * This is very loosely based on a an example by Karsten Kutza, 
 * written in C on 1996-01-30.
 * http://www.neural-networks-at-your-fingertips.com/hopfield.html
 * 
 * I translated it to Java and adapted it to use Encog for neural
 * network processing.  I mainly kept the patterns from the 
 * original example.
 *
 */
public class HopfieldAssociate implements RunnableDemo {

	private VerticalPanel panel;
	
	final static int HEIGHT = 10;
	final static int WIDTH = 10;
	
	/**
	 * The neural network will learn these patterns.
	 */
	public static final String[][] PATTERN  = { { 
		"OXOXOXOXOX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO"  },

      { "OOXXOOXXOO",
        "OOXXOOXXOO",
        "XXOOXXOOXX",
        "XXOOXXOOXX",
        "OOXXOOXXOO",
        "OOXXOOXXOO",
        "XXOOXXOOXX",
        "XXOOXXOOXX",
        "OOXXOOXXOO",
        "OOXXOOXXOO"  },

      { "OOOOOXXXXX",
        "OOOOOXXXXX",
        "OOOOOXXXXX",
        "OOOOOXXXXX",
        "OOOOOXXXXX",
        "XXXXXOOOOO",
        "XXXXXOOOOO",
        "XXXXXOOOOO",
        "XXXXXOOOOO",
        "XXXXXOOOOO"  },

      { "OXXOXXOXXO",
        "XOXXOXXOXX",
        "XXOXXOXXOX",
        "OXXOXXOXXO",
        "XOXXOXXOXX",
        "XXOXXOXXOX",
        "OXXOXXOXXO",
        "XOXXOXXOXX",
        "XXOXXOXXOX",
        "OXXOXXOXXO"  },

      { "OOOOOOOOOO",
        "OXXXXXXXXO",
        "OXOOOOOOXO",
        "OXOXXXXOXO",
        "OXOXOOXOXO",
        "OXOXOOXOXO",
        "OXOXXXXOXO",
        "OXOOOOOOXO",
        "OXXXXXXXXO",
        "OOOOOOOOOO"  } };

	/**
	 * The neural network will be testedXOn these patterns, tOXsee
	 * whichXOf the last set they are the closest to.
	 */
	public static final String[][] PATTERN2 = { { 
		"XXXXXXXXXX ",
        "XXXXXXXXXX",
        "XXXXXXXXXX",
        "XXXXXXXXXX",
        "XXXXXXXXXX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO",
        "OXOXOXOXOX",
        "XOXOXOXOXO"  },

      { "OOOXOXXXXO",
        "XOXXOOOXOO",
        "XXOXOXOOXO",
        "XOOOXXXOXX",
        "OOXXOXXOOO",
        "XOXOOOXXXO",
        "OXOOXXOXXO",
        "XXXOXOOOXX",
        "OOXOOOXXOX",
        "XOXXOXXOOO"  },

      { "OOOOOXXXXX",
        "OXXXOXOOOX",
        "OXXXOXOOOX",
        "OXXXOXOOOX",
        "OOOOOXXXXX",
        "XXXXXOOOOO",
        "XOOOXOXXXO",
        "XOOOXOXXXO",
        "XOOOXOXXXO",
        "XXXXXOOOOO"  },

      { "OXXOOOOXXO",
        "OOXXOOOOXX",
        "OOOXXOOOOX",
        "OOOOXXOOOO",
        "XOOOOXXOOO",
        "XXOOOOXXOO",
        "OXXOOOOXXO",
        "OOXXOOOOXX",
        "OOOXXOOOOX",
        "OOOOXXOOOO"  },

      { "OOOOOOOOOO",
        "OXXXXXXXXO",
        "OXXXXXXXXO",
        "OXXXXXXXXO",
        "OXXXOOXXXO",
        "OXXXOOXXXO",
        "OXXXXXXXXO",
        "OXXXXXXXXO",
        "OXXXXXXXXO",
        "OOOOOOOOOO"  } };
	
	@Override
	public void run(Panel parent) {
		
		//setup output
		panel = new VerticalPanel();
		parent.add(panel);
		
		//setup close button
		panel.add(new RemoveDemoButton(parent,panel));
		
		//show we've started
		panel.add(new Label("HopfieldAssociate Demo Started"));
		
		/*HopfieldPattern pattern = new HopfieldPattern();
		pattern.setInputNeurons(WIDTH*HEIGHT);
		BasicNetwork hopfield = pattern.generate();
		HopfieldLogic hopfieldLogic = (HopfieldLogic)hopfield.getLogic();*/
		
		//update UI by deferring the calculations
		Scheduler.get().scheduleDeferred(new Command() {
			public void execute () {
				HopfieldNetwork hopfieldLogic = new HopfieldNetwork(WIDTH*HEIGHT);

				for(int i=0;i<PATTERN.length;i++) {
					hopfieldLogic.addPattern(convertPattern(PATTERN,i));
				}
				
				evaluate(hopfieldLogic,PATTERN);
				evaluate(hopfieldLogic,PATTERN2);
			}
		});
		
		//?Encog.getInstance().shutdown();
	}

	public BiPolarNeuralData convertPattern(String[][] data, int index) {
		int resultIndex = 0;
		BiPolarNeuralData result = new BiPolarNeuralData(WIDTH*HEIGHT);
		for(int row=0;row<HEIGHT;row++)	{
			for(int col=0;col<WIDTH;col++) {
				char ch = data[index][row].charAt(col);
				result.setData(resultIndex++, ch=='O');
			}
		}
		return result;
	}
	
	public void display(BiPolarNeuralData pattern1,BiPolarNeuralData pattern2) {
		int index1 = 0;
		int index2 = 0;
		
		for(int row = 0;row<HEIGHT;row++) {
			StringBuilder line = new StringBuilder();
			
			for(int col = 0;col<WIDTH;col++) {
				if(pattern1.getBoolean(index1++)) {
					line.append('O');
				}
				else {
					line.append('X');
				}
			}
			
			line.append("   ->   ");
			
			for(int col = 0;col<WIDTH;col++) {
				if(pattern2.getBoolean(index2++)) {
					line.append('O');
				}
				else {
					line.append('X');
				}
			}
			
			panel.add(new Label(line.toString()));
		}
	}
	
	public void evaluate(HopfieldNetwork hopfieldLogic, String[][] pattern)	{
		for(int i=0;i<pattern.length;i++) {
			BiPolarNeuralData pattern1 = convertPattern(pattern,i);
			hopfieldLogic.setCurrentState(pattern1);
			int cycles = hopfieldLogic.runUntilStable(100);
			BiPolarNeuralData pattern2 = (BiPolarNeuralData)hopfieldLogic.getCurrentState();
			panel.add(new Label("Cycles until stable(max 100): " + cycles + ", result="));
			display( pattern1, pattern2);
			panel.add(new Label("----------------------"));
		}
	}
	
}

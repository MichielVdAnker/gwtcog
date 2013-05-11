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
package gwtcog.examples.client.neural.xor;

import gwtcog.core.ml.MLMethod;
import gwtcog.core.ml.MLRegression;
import gwtcog.core.ml.MLResettable;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.ml.factory.MLMethodFactory;
import gwtcog.core.ml.factory.MLTrainFactory;
import gwtcog.core.ml.train.MLTrain;
import gwtcog.core.ml.train.strategy.RequiredImprovementStrategy;
import gwtcog.core.neural.networks.training.propagation.manhattan.ManhattanPropagation;
import gwtcog.core.util.simple.EncogUtility;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This example shows how to use the Encog machine learning factory to 
 * generate a number of machine learning methods and training techniques 
 * to learn the XOR operator.
 */
public class XORFactory implements RunnableDemo {

	private VerticalPanel panel;

	/**
	 * The input necessary for XOR.
	 */
	public static double XOR_INPUT[][] = { { 0.0, 0.0 }, { 1.0, 0.0 },
		{ 0.0, 1.0 }, { 1.0, 1.0 } };

	/**
	 * The ideal data necessary for XOR.
	 */
	public static double XOR_IDEAL[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };

	public static final String METHOD_FEEDFORWARD_A = "?:B->SIGMOID->4:B->SIGMOID->?";
	public static final String METHOD_BIASLESS_A = "?->SIGMOID->4->SIGMOID->?";
	public static final String METHOD_SVMC_A = "?->C->?";
	public static final String METHOD_SVMR_A = "?->R->?";
	public static final String METHOD_RBF_A = "?->gaussian(c=4)->?";
	public static final String METHOD_PNNC_A = "?->C(kernel=gaussian)->?";
	public static final String METHOD_PNNR_A = "?->R(kernel=gaussian)->?";

	/**
	 * Demonstrate a feedforward network with RPROP.
	 */
	public void xorRPROP() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_RPROP,
				"",1);		
	}

	/**
	 * Demonstrate a feedforward biasless network with RPROP.
	 */
	public void xorBiasless() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_BIASLESS_A,
				MLTrainFactory.TYPE_RPROP,
				"",1);		
	}

	/**
	 * Demonstrate a feedforward network with backpropagation.
	 */
	public void xorBackProp() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_BACKPROP,
				"",1);		
	}


	/**
	 * Demonstrate a feedforward network with backpropagation.
	 */
	public void xorQProp() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_QPROP,
				"",1);		
	}

	/**
	 * Demonstrate a SVM-classify.
	 */
	public void xorSVMClassify() {
		process( 
				MLMethodFactory.TYPE_SVM,
				XORFactory.METHOD_SVMC_A,
				MLTrainFactory.TYPE_SVM,
				"",1);		
	}

	/**
	 * Demonstrate a SVM-regression.
	 */
	public void xorSVMRegression() {
		process( 
				MLMethodFactory.TYPE_SVM,
				XORFactory.METHOD_SVMR_A,
				MLTrainFactory.TYPE_SVM,
				"",1);		
	}

	/**
	 * Demonstrate a SVM-regression search.
	 */
	public void xorSVMSearchRegression() {
		process( 
				MLMethodFactory.TYPE_SVM,
				XORFactory.METHOD_SVMR_A,
				MLTrainFactory.TYPE_SVM_SEARCH,
				"",1);		
	}

	/**
	 * Demonstrate a XOR annealing.
	 */
	public void xorAnneal() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_ANNEAL,
				"",1);		
	}

	/**
	 * Demonstrate a XOR genetic.
	 */
	public void xorGenetic() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_GENETIC,
				"",1);		
	}

	/**
	 * Demonstrate a XOR LMA.
	 */
	public void xorLMA() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_LMA,
				"",1);		
	}

	/**
	 * Demonstrate a XOR LMA.
	 */
	public void xorNM() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_NELDER_MEAD,
				"",1);		
	}

	/**
	 * Demonstrate a XOR LMA.
	 */
	public void xorManhattan() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_MANHATTAN,
				"lr=0.0001",1);		
	}

	/**
	 * Demonstrate a XOR SCG.
	 */
	public void xorSCG() {
		process( 
				MLMethodFactory.TYPE_FEEDFORWARD,
				XORFactory.METHOD_FEEDFORWARD_A,
				MLTrainFactory.TYPE_SCG,
				"",1);		
	}

	/**
	 * Demonstrate a XOR RBF.
	 */
	public void xorRBF() {
		process( 
				MLMethodFactory.TYPE_RBFNETWORK,
				XORFactory.METHOD_RBF_A,
				MLTrainFactory.TYPE_RPROP,
				"",1);		
	}

	/**
	 * Demonstrate a XOR RBF.
	 */
	public void xorSVD() {
		process( 
				MLMethodFactory.TYPE_RBFNETWORK,
				XORFactory.METHOD_RBF_A,
				MLTrainFactory.TYPE_SVD,
				"",1);		
	}

	/**
	 * Demonstrate a XOR RBF.
	 */
	public void xorPNNC() {
		process( 
				MLMethodFactory.TYPE_PNN,
				XORFactory.METHOD_PNNC_A,
				MLTrainFactory.TYPE_PNN,
				"",2);		
	}

	/**
	 * Demonstrate a XOR RBF.
	 */
	public void xorPNNR() {
		process( 
				MLMethodFactory.TYPE_PNN,
				XORFactory.METHOD_PNNR_A,
				MLTrainFactory.TYPE_PNN,
				"",1);		
	}

	public void process(String methodName, String methodArchitecture,String trainerName, String trainerArgs,int outputNeurons) {

		// first, create the machine learning method
		MLMethodFactory methodFactory = new MLMethodFactory();		
		MLMethod method = methodFactory.create(methodName, methodArchitecture, 2, outputNeurons);

		// second, create the data set		
		MLDataSet dataSet = new BasicMLDataSet(XOR_INPUT, XOR_IDEAL);

		// third, create the trainer
		MLTrainFactory trainFactory = new MLTrainFactory();	
		MLTrain train = trainFactory.create(method,dataSet,trainerName,trainerArgs);				
		// reset if improve is less than 1% over 5 cycles
		if( method instanceof MLResettable && !(train instanceof ManhattanPropagation) ) {
			train.addStrategy(new RequiredImprovementStrategy(500));
		}

		// fourth, train and evaluate.
		EncogUtility.trainToError(train, 0.01);
		method = train.getMethod();
		ArrayList<String> responses = EncogUtility.evaluate((MLRegression)method, dataSet);

		// report responses to screen
		for(String response : responses) {
			panel.add(new Label(response));
		}
		
		// finally, write out what we did

		panel.add(new Label("Machine Learning Type: " + methodName));
		panel.add(new Label("Machine Learning Architecture: " + methodArchitecture));

		panel.add(new Label("Training Method: " + trainerName));
		panel.add(new Label("Training Args: " + trainerArgs));
		
		panel.add(new Label("--------------finished----------------"));
		panel.add(new Label(""));

	}

	//	/**
	//	 * Display usage information.
	//	 */
	//	public void usage() {
	//		panel.add(new Label("Usage:\nXORFactory [mode]\n\nWhere mode is one of:\n"));
	//		
	//		panel.add(new Label("backprop - Feedforward biased with backpropagation"));
	//		panel.add(new Label("rprop - Feedforward biased with resilient propagation"));
	//		panel.add(new Label("biasless - Feedforward biasless with resilient"));
	//		panel.add(new Label("svm-c - Support vector machine classification"));
	//		panel.add(new Label("svm-r - Support vector machine regression"));
	//		panel.add(new Label("svm-search-r - Support vector machine search regression"));
	//		panel.add(new Label("anneal - Simulated annealing"));
	//		panel.add(new Label("genetic - Genetic"));
	//		panel.add(new Label("lma - Levenberg Marquadt"));
	//		panel.add(new Label("manhattan - Manhattan Update"));
	//		panel.add(new Label("nm - Nelder Mead"));
	//		panel.add(new Label("scg - Scaled Conjugate Gradient"));
	//		panel.add(new Label("rbf - Radial Basis Function with RPROP"));
	//		panel.add(new Label("svd - Radial Basis Function with SVD"));
	//		panel.add(new Label("pnn-c - Probabalistic Neural Network Classification"));
	//		panel.add(new Label("pnn-r - Probabalistic Neural Network Regression"));
	//		panel.add(new Label("qprop - Quick Propagation"));
	//		
	//	}

	public void execute(String mode) {
		
		System.out.println("Going to start mode: "+mode);
		
		if( mode.equalsIgnoreCase("backprop") ) {
			xorBackProp();
		} else if( mode.equalsIgnoreCase("rprop") ) {
			xorRPROP();
		} else if( mode.equalsIgnoreCase("biasless") ) {
			xorBiasless();
		} else if( mode.equalsIgnoreCase("svm-c") ) {
			xorSVMClassify();
		} else if( mode.equalsIgnoreCase("svm-r") ) {
			xorSVMRegression();
		}  else if( mode.equalsIgnoreCase("svm-search-r") ) {
			xorSVMSearchRegression();
		} else if( mode.equalsIgnoreCase("anneal") ) {
			xorAnneal();
		} else if( mode.equalsIgnoreCase("genetic") ) {
			xorGenetic();
		} else if( mode.equalsIgnoreCase("lma") ) {
			xorLMA();
		} else if( mode.equalsIgnoreCase("nm") ) {
			xorNM();
		} else if( mode.equalsIgnoreCase("manhattan") ) {
			xorManhattan();
		} else if( mode.equalsIgnoreCase("scg") ) {
			xorSCG();
		} else if( mode.equalsIgnoreCase("rbf") ) {
			xorRBF();
		} else if( mode.equalsIgnoreCase("svd") ) {
			xorSVD();
		} else if( mode.equalsIgnoreCase("pnn-c") ) {
			xorPNNC();
		} else if( mode.equalsIgnoreCase("pnn-r") ) {
			xorPNNR();
		} else if( mode.equalsIgnoreCase("qprop") ) {
			xorQProp();
		} 
		//		else {
		//			usage();
		//		}
	}

	@Override
	public void run(Panel parent) {

		//setup output
		panel = new VerticalPanel();
		parent.add(panel);

		//setup close button
		panel.add(new RemoveDemoButton(parent,panel));

		//show we've started
		panel.add(new Label("XORFactory Demo Started"));

		String[] modes = { 
				"backprop", 
				"rprop", 
				"biasless", 
				"svm-c", 
				"svm-r", 
				"svm-search-r", 
				"anneal", 
//				"genetic", //incompatible at this time 
				"lma", 
				"nm", 
				"manhattan", 
				"scg", 
				"rbf", 
//				"svd", //incompatible at this time 
				"pnn-c", 
				"pnn-r", 
				"qprop" 
				};

		executeAll(0, modes);

	}

	private void executeAll(final int index, final String[] modes) {
		Scheduler.get().scheduleDeferred(new Command() {
			public void execute () {

				if(index < modes.length) {
					XORFactory.this.execute(modes[index]);
					executeAll(index+1,modes);
				}

			}
		});
	}

	//	public static void main(final String args[]) {
	//
	//		XORFactory program = new XORFactory();
	//		if( args.length>0 ) {
	//			program.run(args[0]);
	//		} else {
	//			program.usage();
	//		}
	//		
	//		Encog.getInstance().shutdown();
	//	}
}

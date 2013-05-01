package gwtcog.core.ml.factory.train;
///*
// * Encog(tm) Core v3.2 - Java Version
// * http://www.heatonresearch.com/encog/
// * https://github.com/encog/encog-java-core
// 
// * Copyright 2008-2013 Heaton Research, Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *   
// * For more information on Heaton Research copyrights, licenses 
// * and trademarks visit:
// * http://www.heatonresearch.com/copyright
// */
//package gwtcog.core.ml.factory.train;
//
//import java.util.Map;
//
//import gwtcog.core.ml.CalculateScore;
//import gwtcog.core.ml.MLEncodable;
//import gwtcog.core.ml.MLMethod;
//import gwtcog.core.ml.MLResettable;
//import gwtcog.core.ml.MethodFactory;
//import gwtcog.core.ml.data.MLDataSet;
//import gwtcog.core.ml.factory.MLTrainFactory;
//import gwtcog.core.ml.factory.parse.ArchitectureParse;
//import gwtcog.core.ml.genetic.MLMethodGeneticAlgorithm;
//import gwtcog.core.ml.train.MLTrain;
//import gwtcog.core.neural.networks.training.TrainingError;
//import gwtcog.core.neural.networks.training.TrainingSetScore;
//import gwtcog.core.util.ParamsHolder;
////import gwtcog.core.util.obj.ObjectCloner;
//
///**
// * A factory to create genetic algorithm trainers.
// */
//public class GeneticFactory {
//	/**
//	 * Create an annealing trainer.
//	 * 
//	 * @param method
//	 *            The method to use.
//	 * @param training
//	 *            The training data to use.
//	 * @param argsStr
//	 *            The arguments to use.
//	 * @return The newly created trainer.
//	 */
//	public MLTrain create(final MLMethod method,
//			final MLDataSet training, final String argsStr) {
//
//		if (!(method instanceof MLEncodable)) {
//			throw new TrainingError(
//					"Invalid method type, requires an encodable MLMethod");
//		}
//
//		final CalculateScore score = new TrainingSetScore(training);
//
//		final Map<String, String> args = ArchitectureParse.parseParams(argsStr);
//		final ParamsHolder holder = new ParamsHolder(args);
//		final int populationSize = holder.getInt(
//				MLTrainFactory.PROPERTY_POPULATION_SIZE, false, 5000);
//		
//		MLTrain train = new MLMethodGeneticAlgorithm(new MethodFactory(){
//			@Override
//			public MLMethod factor() {
//				final MLMethod result = (MLMethod) ObjectCloner.deepCopy(method);
//				((MLResettable)result).reset();
//				return result;
//			}}, score, populationSize);
//
//		return train;
//	}
//}

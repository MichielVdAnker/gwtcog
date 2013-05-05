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
package gwtcog.core.ensemble.stacking;

import gwtcog.core.ensemble.Ensemble;
import gwtcog.core.ensemble.EnsembleAggregator;
import gwtcog.core.ensemble.EnsembleML;
import gwtcog.core.ensemble.EnsembleMLMethodFactory;
import gwtcog.core.ensemble.EnsembleTrainFactory;
import gwtcog.core.ensemble.EnsembleTypes;
import gwtcog.core.ensemble.EnsembleTypes.ProblemType;
import gwtcog.core.ensemble.data.factories.WrappingNonResamplingDataSetFactory;

import java.util.ArrayList;

public class Stacking extends Ensemble {

	private int splits;

	public Stacking(int splits, int dataSetSize, EnsembleMLMethodFactory mlFactory, EnsembleTrainFactory trainFactory, EnsembleAggregator aggregator)
	{
		int dataSplits = aggregator.needsTraining() ? splits + 1 : splits;
		this.dataSetFactory = new WrappingNonResamplingDataSetFactory(dataSplits);
		this.splits = splits;
		this.mlFactory = mlFactory;
		this.trainFactory = trainFactory;
		this.members = new ArrayList<EnsembleML>();
		this.aggregator = aggregator;
		initMembers();
	}

	@Override
	public void initMembers()
	{
		this.initMembersBySplits(this.splits);
	}

	@Override
	public ProblemType getProblemType() {
		return EnsembleTypes.ProblemType.CLASSIFICATION;
	}

	@Override
	public EnsembleML getMember(int memberNumber) {
		return members.get(memberNumber);
	}

	public void trainStep() {
		for (EnsembleML current : members)
		{
			current.trainStep();
		}
	}


}

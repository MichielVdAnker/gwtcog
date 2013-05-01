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
package gwtcog.core.ensemble.data.factories;

import gwtcog.core.ensemble.data.EnsembleDataSet;

public class WrappingNonResamplingDataSetFactory extends EnsembleDataSetFactory {

	//NOTE: dataSetSize here is used as the number of datasets, rather than the number of data instances


	private int currentPosition = 0;

	public WrappingNonResamplingDataSetFactory(int dataSetSize) {
		super(dataSetSize);
	}

	@Override
	public EnsembleDataSet getNewDataSet() {
		EnsembleDataSet ds = new EnsembleDataSet(dataSource.getInputSize(), dataSource.getIdealSize());
		for (int i = currentPosition; i < currentPosition + dataSource.size() / dataSetSize; i++)
		{
			ds.add(dataSource.get(i % this.dataSource.size()));
		}
		return ds;
	}
}

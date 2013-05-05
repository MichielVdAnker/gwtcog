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
package gwtcog.examples.client.clustering.kmeans;

import gwtcog.core.ml.MLCluster;
import gwtcog.core.ml.data.MLDataPair;
import gwtcog.core.ml.data.MLDataSet;
import gwtcog.core.ml.data.basic.BasicMLData;
import gwtcog.core.ml.data.basic.BasicMLDataPair;
import gwtcog.core.ml.data.basic.BasicMLDataSet;
import gwtcog.core.ml.kmeans.KMeansClustering;
import gwtcog.examples.client.RemoveDemoButton;
import gwtcog.examples.client.RunnableDemo;

import java.util.Arrays;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This example performs a simple KMeans cluster.  The numbers are clustered
 * into two groups.
 */
public class SimpleKMeans implements RunnableDemo {

	private VerticalPanel panel;

	/**
	 * The data to be clustered.
	 */
	public static final double[][] DATA = { { 28, 15, 22 }, { 16, 15, 32 },
		{ 32, 20, 44 }, { 1, 2, 3 }, { 3, 2, 1 } };

	@Override
	public void run(Panel parent) {

		//setup output
		panel = new VerticalPanel();
		parent.add(panel);

		//setup close button
		panel.add(new RemoveDemoButton(parent,panel));

		//show we've started
		panel.add(new Label("SimpleKMeans Demo Started"));

		final BasicMLDataSet set = new BasicMLDataSet();

		for (final double[] element : SimpleKMeans.DATA) {
			set.add(new BasicMLData(element));
		}

		final KMeansClustering kmeans = new KMeansClustering(2, set);

		kmeans.iteration(100);
		//panel.add(new Label("Final WCSS: " + kmeans.getWCSS());

		// Display the cluster
		int i = 1;
		for (final MLCluster cluster : kmeans.getClusters()) {
			panel.add(new Label("*** Cluster " + (i++) + " ***"));
			final MLDataSet ds = cluster.createDataSet();
			final MLDataPair pair = BasicMLDataPair.createPair(
					ds.getInputSize(), ds.getIdealSize());
			for (int j = 0; j < ds.getRecordCount(); j++) {
				ds.getRecord(j, pair);
				panel.add(new Label(Arrays.toString(pair.getInputArray())));
			}
		}
	}
}

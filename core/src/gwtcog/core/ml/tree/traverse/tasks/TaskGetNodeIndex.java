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
package gwtcog.core.ml.tree.traverse.tasks;

import gwtcog.core.ml.tree.TreeNode;
import gwtcog.core.ml.tree.traverse.DepthFirstTraversal;
import gwtcog.core.ml.tree.traverse.TreeTraversalTask;

public class TaskGetNodeIndex implements TreeTraversalTask {
	private int nodeCount;
	private int targetIndex;
	private TreeNode result;
	
	public TaskGetNodeIndex(int theIndex) {
		this.targetIndex = theIndex;
		this.nodeCount = 0;
	}

	@Override
	public boolean task(TreeNode node) {
		if( this.nodeCount>=targetIndex) {
			if( result==null ) {
				result = node;
			}
			return false;
		}
		
		this.nodeCount++;
		return true;
	}
	
	public TreeNode getResult() {
		return result;
	}

	public static TreeNode process(int index, TreeNode node) {
		TaskGetNodeIndex task = new TaskGetNodeIndex(index);
		DepthFirstTraversal trav = new DepthFirstTraversal();
		trav.traverse(node, task);
		return task.getResult();
	}

}

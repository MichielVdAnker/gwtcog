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

import gwtcog.core.neural.neat.NEATPopulation;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Display the boxes on the screen using Swing.
 *
 */
public class DisplayBoxes extends DockLayoutPanel {
	
	public static final String[] RESOLUTIONS = { "11", "22", "33", "44", "55" };
	private ListBox resolution;
	private DisplayBoxesPanel display;
	private Button newCase;
	
	public DisplayBoxes(NEATPopulation thePopulation) {
		super(Unit.EM);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		resolution = new ListBox();
		for(String s : RESOLUTIONS) {
			resolution.addItem(s);
		}
		buttonPanel.add(resolution);
		
		newCase=new Button("New Case");
		newCase.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				display.createNewCase(Integer.parseInt(resolution.getItemText(resolution.getSelectedIndex())));
			}
		});
		buttonPanel.add(newCase);
		
		addNorth(buttonPanel, 3);
		display = new DisplayBoxesPanel(thePopulation);
		add(display);
	}

//	@Override
//	public void actionPerformed(ActionEvent evt) {
//		if( evt.getSource()==this.newCase ) {
//			this.display.createNewCase(Integer.parseInt(this.resolution.getSelectedItem().toString()));
//		}
//	}
	
	
}

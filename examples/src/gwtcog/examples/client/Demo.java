package gwtcog.examples.client;

import gwtcog.examples.client.clustering.kmeans.SimpleKMeans;
import gwtcog.examples.client.neural.boltzmann.BoltzTSP;
import gwtcog.examples.client.neural.hopfield.HopfieldAssociate;
import gwtcog.examples.client.neural.neat.XORNEAT;
import gwtcog.examples.client.neural.predict.sunspot.PredictSunspotSVM;
import gwtcog.examples.client.neural.som.SimpleSOM;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Demo implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		HorizontalPanel baseLayout = new HorizontalPanel();
		VerticalPanel optionsPanel = new VerticalPanel();
		final HorizontalPanel demoPanel = new HorizontalPanel();

		baseLayout.add(optionsPanel);
		baseLayout.add(demoPanel);
		RootPanel.get().add(baseLayout);

		//setup demo options
		optionsPanel.add(new Label("Pick a gwtcog demo"));

		//XORNEAT
		Button demoButton = new Button("XORNEAT", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new XORNEAT().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);

		//HopfieldAssociate
		demoButton = new Button("HopfieldAssociate", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new HopfieldAssociate().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);

		//SimpleKMeans
		demoButton = new Button("SimpleKMeans", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new SimpleKMeans().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);

		//SimpleSOM
		demoButton = new Button("SimpleSOM", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new SimpleSOM().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);
		
		//BoltzTSP
		demoButton = new Button("BoltzTSP", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new BoltzTSP().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);
		
		//PredictSunspotSVM
		demoButton = new Button("PredictSunspotSVM", new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Something failed");
					}

					public void onSuccess() {
						new PredictSunspotSVM().run(demoPanel);
					}
				});
			}
		});
		optionsPanel.add(demoButton);

	}

}

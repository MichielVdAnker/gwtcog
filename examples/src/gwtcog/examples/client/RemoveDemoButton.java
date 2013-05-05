package gwtcog.examples.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;

public class RemoveDemoButton extends Button {

	public RemoveDemoButton(final Panel parent, final Panel demo) {
		
		setText("Remove this demo");
		
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parent.remove(demo);
			}
			
		});
		
	}
	
}

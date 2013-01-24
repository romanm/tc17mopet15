package org.tc17.jaxb.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.tasclin1.mopet.service.MopetService;


public class SWTHelloWorld {
	 @Autowired
	    private MopetService mopetService;
public static void main (String [] args) {
	SWTHelloWorld swtHelloWorld = new SWTHelloWorld();
	swtHelloWorld.init();
}
private void init() {
	Display display = new Display ();
	Shell shell = new Shell(display);
 
	Text helloWorldTest = new Text(shell, SWT.NONE);
	helloWorldTest.setText("Hello World SWT"+mopetService);
	helloWorldTest.pack();
 
	shell.pack();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
	
}
}
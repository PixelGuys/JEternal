out.println("Example Program");

out.println("Loading..");
var UIModule = misc.loadModule("system", "ui"); /* or misc.loadModule("system/ui"); */
var EventModule = misc.loadModule("system", "event");
var win = UIModule.createWindow();
var button = UIModule.createComponent("Button");
button.setText("Hi!");
button.setBounds(100, 200, 100, 100);
win.setLocation(100, 100);
win.setGraphicsLightweight(true);
win.setTitle("Example Program");
win.add(button);
win.setVisible(true);

while (true) {
	var event = EventModule.pullEvent();
	update();
	render(win.getSoftwareGraphics());
	event.accept();
}

function render(g) {
	//g.fillRect(0, 0, win.getWidth(), win.getHeight());
	
	// Lightweight button
	//g.fillRect(20, 20, 20, 80);
	g.drawString("Hello!", 64, 40);
}

function update() {
	
}
var file = argv[1];
var UIModule = misc.loadModule("system", "ui"); /* or misc.loadModule("system/ui"); */
var EventModule = misc.loadModule("system", "event");
var win = UIModule.createWindow();
var buttonPrev = UIModule.createComponent("Button");
var imageView = UIModule.createComponent("ImageView");
buttonPrev.setText("<<");
buttonPrev.setBounds(0, 0, 32, 32);
win.setLocation(100, 100);
win.setLayout(null)
win.setTitle("Picture Viewver");
win.add(buttonPrev);
win.setVisible(true);

while (true) {
	var event = EventModule.pullEvent();
	update();
	//render(win.getSoftwareGraphics());
	event.accept();
}

function render(g) {
	//g.drawString("Hello!", 64, 40);
}

function update() {
	
}
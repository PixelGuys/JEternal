var dir = argv[0];
var ui = misc.loadModule("system", "ui"); /* or misc.loadModule("system/ui"); */
var EventModule = misc.loadModule("system", "event");
var fs = misc.loadModule("system", "filesystem")
var win = ui.createWindow();
var root = ui.createComponent("Pane");
var i = 0;


win.setLocationRelativeTo(null);
win.setTitle("Explorer");
win.add(ui.createComponent("Scroller", root))
win.setVisible(true);

while (true) {
	var event = EventModule.pullEvent();
	update(win);
	//render(win.getSoftwareGraphics());
	event.accept();
}

function createIcon(file) {
	var pane = ui.createComponent("Pane");
	var imageView = ui.createComponent("ImageView")
	var label = ui.createComponent("Label")
	var image = null
	if (file.isDirectory())
		image = ui.readImage("System/Resources/Images/FileDefaultIcon.png")
	else {
		if (file.getExtension() == "png") {
			image = ui.readImage(file.getPath())
		}
		else {
			image = ui.readImage("System/Resources/Images/FileDefaultIcon.png")
		}
	}
	imageView.setImage(image);
	//label.setText(file.getPath())
	pane.add(imageView);
	pane.add(label);
}

function render(g) {
	//g.drawString("Hello!", 64, 40);
}

function update(win) {
	
}
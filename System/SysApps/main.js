var file = argv[0];
var UIModule = misc.loadModule("system", "ui"); /* or misc.loadModule("system/ui"); */
var EventModule = misc.loadModule("system", "event");
var win = UIModule.createWindow();
var buttonPrev = UIModule.createComponent("Button");
var imageView = UIModule.createComponent("ImageView");
var image = UIModule.readImage(file);
imageView.setImage(image);
buttonPrev.setText("<<");
buttonPrev.setBounds(0, 0, 42, 32);
imageView.setBounds(0, 32, 200, 200);
win.setLocation(100, 100);
win.setLayout(null);
win.setTitle("Pictures");
win.add(buttonPrev);
win.add(imageView);
win.setVisible(true);

while (true) {
	var event = EventModule.pullEvent();
	update(win);
	//render(win.getSoftwareGraphics());
	event.accept();
}

function render(g) {
	//g.drawString("Hello!", 64, 40);
}

function update(win) {
	imageView.setBounds(0, 32, win.getWidth(), win.getHeight() - 32)
}
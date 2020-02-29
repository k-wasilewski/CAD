# 'CAD': (pure Java)

A desktop app with basic functionalities of AutoCAD.

## Index of commands:


'l' - draw a line from point A to point B;

'cl' - clear the canvas;

m -move object(s);

dcol:X - set the drawing color to X color;

bcol:X - set the background color to X color;

colors:

	bla - black,

	w - white,

	g - green,

	y - yellow,

	r - red,

	blu - blue;

ortoX - turn the ortho mode on/off in the horizontal direction;

ortoY - turn the ortho mode on/off in the vertical direction;

c - draw a circle with a center at point A and a radius of |B-A|;

ENTER - process the present or, if nothing is typed, last known input command;

dist - measure the distance in pxs;

rec - draw a rectangle with opposite corners of points A and B respectively;

mouse keys:

	left - draw / measure the distance / select,

	press mousewheel - move the canvas while holding the button;

	move  mousewheel - zoom in / out;

	right (over an image) - change display order;

	right - snap mode ON/OFF;

	right - snap-to-grid mode ON/OFF;

	right - grid mode ON/OFF (only for scale = 1.0);

select->DEL - delete object;

select->(draw an AB path) - move the object by the AB distance;

CTRL+S - save file;

CTRL+O - open file

regen - regenerating the canvas for correct behaviour;

pl - draw a polyline from point A through consequent points;

co - copy object(s) from point A to point B;

menu:

	Import - import an image

	Export - export as image



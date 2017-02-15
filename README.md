# HTML text highlighter

## The Task
The idea is that any HTML tag has a predefined color and the program will use the color associated with the tag to highlight the tag and the text within it. Whenever you find a new tag the program needs to change to the new corresponding tag color, switching back to the previous color after the closing tag.

We already have a program that applies the new color visually whenever it finds a specific escape sequence in the text (\color[RED]) by changing the current background color to the specified color. The escape sequence can contain color names or color codes (i.e. both \color[RED] or \color[FF0000] will be accepted).

Your task is to create a component that will take as input a valid HTML string and it will generate a new string with escape codes for the colors applied. To illustrate your component, you also have to create a console app that reads an HTML text file and creates an output file containing the string returned by the component.

## Sample

### Sample input
```
<html>
<head>
<title>HTML highlight test page</title>
</head>
<body>
This is text in the body.
<br>
<h1>This is a heading</h1>
<p>This is a paragraph.</p>
There is more text in the body after the paragraph.
</body>
</html>
```

### Sample Output
```
\color[RED]<html>
\color[YELLOW]<head>
\color[GREEN]<title>HTML highlight test page</title>
\color[YELLOW]</head>
\color[TURQUOISE]<body>
This is text in the body.
\color[PINK]<br>
\color[DARKGREEN]<h1>This is a heading</h1>
\color[DARKGRAY]<p>This is a paragraph.</p>
\color[TURQUOISE]There is more text in the body after the paragraph.
</body>
\color[RED]</html>
```

## Highlights

The program is written in Java. It can read a file or a valid HTML string from the command line. The program parses the HTML line by line. If the next tag is closing tag for the previous tag, then a color code is not added else, a color code is added.

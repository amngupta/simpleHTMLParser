all: HighlightHTML.class test1 test2

HighlightHTML.class:
	javac -d ./ ./src/HighlightHTML.java

test1:
	java HighlightHTML "test.html" > "output.html"

test2:
	java HighlightHTML "test2.html" > "output2.html"

clean:
	rm -f ./HighlightHTML.class ./output2.html ./output.html
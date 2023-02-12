compile: src/main/*.java
	javac src/main/*.java

run: src/main/*.class
	java -classpath src/ main/Controller

clean:
	rm src/main/*.class

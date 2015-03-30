cls
javac com/mysimplecount/*.java
@echo Fichiers .java compiles
jar cvmf META-INF/MANIFEST.MF simpleCount.jar com/mysimplecount/*.class
@echo simpleCount.jar genere !
java -jar simpleCount.jar
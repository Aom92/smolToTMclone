@ECHO OFF

call dir *.java /s /b > sources.txt
call javac @sources.txt -d bin

@ECHO SI NO HAY NADA ANTES DE ESTE MENSAJE, ENTONCES SE HA COMPILADO CORRECTAMENTE 
cmd /k

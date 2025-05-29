@echo off
REM 1. Creează folderul de output
if not exist out\classes mkdir out\classes

REM 2. Construiește CLASSPATH (punct şi virgulă)
set CP=
for %%f in (lib\*.jar) do (
    set CP=!CP!;lib\%%~nxf
)

REM 3. Compilează toate .java
javac -cp "%CP%" -d out\classes src\com\example\countryapi\**\*.java

echo Compilare finalizată!

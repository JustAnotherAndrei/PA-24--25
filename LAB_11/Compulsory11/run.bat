@echo off
REM 1. Construiește CLASSPATH
set CP=out\classes
for %%f in (lib\*.jar) do (
    set CP=!CP!;lib\%%~nxf
)

REM 2. Rulează aplicația
java -cp "%CP%" com.example.countryapi.CountryApiApplication

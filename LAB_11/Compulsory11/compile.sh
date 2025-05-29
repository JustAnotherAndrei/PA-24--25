#!/usr/bin/env bash
# 1. Creează folderul de output
mkdir -p out/classes

# 2. Construiește CLASSPATH din lib/*.jar
CP=$(printf "lib/%s:" $(ls lib))

# 3. Compilează toate fișierele .java
javac -cp "${CP}" -d out/classes $(find src -name "*.java")

echo "Compilație finalizată cu succes → out/classes/"

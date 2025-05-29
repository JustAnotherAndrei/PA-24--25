#!/usr/bin/env bash
# 1. Setează CLASSPATH: clasele + toate JAR-urile din lib/
CP="out/classes:$(printf "lib/%s:" $(ls lib))"

# 2. Rulează aplicația Spring Boot
java -cp "${CP}" com.example.countryapi.CountryApiApplication

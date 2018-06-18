#!/bin/bash

while [ "$MAVEN_HOME" == "" ]
do
   echo "Please, provide a valid maven base directory:"
   read -r MAVEN_HOME
   
   if [ -f "$MAVEN_HOME/bin/mvn" ] ; then
    break;
   else
    echo "Not a valid maven installation directory"
    MAVEN_HOME=
   fi
done

while [ "$JAVA_HOME" == "" ]
do
   echo "Please, provide JDK or JRE base directory:"
   read -r JAVA_HOME
   
   if [ -f "$JAVA_HOME/bin/java" ] ; then
    break;
   else
    echo "Not a valid JDK/JRE installation directory"
    JAVA_HOME=
   fi
done

export PATH=$PATH:$MAVEN_HOME/bin:$JAVA_HOME/bin
:checkM2Home
@if "%MAVEN_HOME%"=="" (
	goto :setM2Home
) else (
	goto :checkJavaHome
)

:setM2Home
set /p MAVEN_HOME="Please, provide a valid maven base directory:"

@if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
	@echo Not a valid maven installation directory
	set MAVEN_HOME=
	goto :checkM2Home
)

:checkJavaHome
@if "%JAVA_HOME%"=="" (
	goto :setJavaHome
) else (
	goto :setPath
)

:setJavaHome
set /p JAVA_HOME="Please, provide JDK or JRE base directory:"

@if not exist "%JAVA_HOME%\bin\java.exe" (
	@echo Not a valid JDK/JRE installation directory
	set JAVA_HOME=
	goto :checkJavaHome
)

:setPath
SET "PATH=%PATH%;%MAVEN_HOME%\bin;%JAVA_HOME%\bin"
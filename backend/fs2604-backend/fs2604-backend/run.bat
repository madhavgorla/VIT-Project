@echo off
echo Starting FS-2604 Backend & API Hub (Ganesh's Module)...
set JAVA_HOME=C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10
set PATH=%JAVA_HOME%\bin;%PATH%
"C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd" spring-boot:run
pause

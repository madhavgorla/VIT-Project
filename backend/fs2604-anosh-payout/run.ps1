Write-Host "Starting Anosh's Module: Database, Payout and Audit on Port 8082..." -ForegroundColor Green
$env:JAVA_HOME = "C:\Users\Ganesh Yendluri\.jdks\ms-21.0.10"
$env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
& "C:\Users\Ganesh Yendluri\.m2\wrapper\dists\apache-maven-3.9.16-bin\5grr65jo27hi51sujmtcldfovl\apache-maven-3.9.16\bin\mvn.cmd" spring-boot:run

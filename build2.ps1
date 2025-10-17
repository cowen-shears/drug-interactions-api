# Get the 8.3 short path for Java installation to avoid space issues
$javaPath = (Get-WmiObject -Class Win32_Directory -Filter "Name='C:\\Program Files\\Java\\jdk-21'").FileSystemName
if (-not $javaPath) {
    Write-Error "Could not get short path for Java installation"
    exit 1
}
$env:JAVA_HOME = $javaPath

Write-Host "Using JAVA_HOME: $env:JAVA_HOME"
Write-Host "Java version:"
& java -version

# Ensure .mvn/wrapper directory exists
$wrapperDir = Join-Path $PWD ".mvn\wrapper"
if (-not (Test-Path $wrapperDir)) {
    New-Item -ItemType Directory -Force -Path $wrapperDir | Out-Null
}

# Download Maven wrapper jar if it doesn't exist
$wrapperJar = Join-Path $wrapperDir "maven-wrapper.jar"
if (-not (Test-Path $wrapperJar)) {
    Write-Host "Downloading Maven wrapper..."
    $wrapperUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
    Invoke-WebRequest -Uri $wrapperUrl -OutFile $wrapperJar
}

# Create maven-wrapper.properties if it doesn't exist
$wrapperProperties = Join-Path $wrapperDir "maven-wrapper.properties"
if (-not (Test-Path $wrapperProperties)) {
    @"
distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.5/apache-maven-3.9.5-bin.zip
wrapperUrl=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
"@ | Out-File -FilePath $wrapperProperties -Encoding UTF8
}

# Set up environment
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
$env:MAVEN_OPTS = "-Dmaven.multiModuleProjectDirectory=$PWD"

# Run Maven build directly with full paths to avoid shell issues
$javaExe = Join-Path $env:JAVA_HOME "bin\java.exe"
$mvnwJar = Resolve-Path $wrapperJar
Write-Host "Running Maven build..."
& $javaExe $env:MAVEN_OPTS "-jar" $mvnwJar "clean" "install"

# Enable verbose output
$ErrorActionPreference = 'Continue'
$VerbosePreference = 'Continue'

Write-Host "=== Environment Setup ==="
# Set JAVA_HOME
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
Write-Host "JAVA_HOME set to: $env:JAVA_HOME"

# Verify Java is accessible
Write-Host "`n=== Java Version Check ==="
& java -version

# Verify Maven wrapper exists
Write-Host "`n=== Maven Wrapper Check ==="
if (Test-Path "mvnw.cmd") {
    Write-Host "mvnw.cmd found"
} else {
    Write-Host "mvnw.cmd not found!"
    exit 1
}

# Run Maven build
Write-Host "`n=== Starting Maven Build ==="
Write-Host "Current directory: $PWD"
Write-Host "Running: mvnw.cmd clean install`n"

# Use Start-Process to capture output
$pinfo = New-Object System.Diagnostics.ProcessStartInfo
$pinfo.FileName = "cmd.exe"
$pinfo.Arguments = "/c mvnw.cmd -e -X clean install"
$pinfo.UseShellExecute = $false
$pinfo.RedirectStandardOutput = $true
$pinfo.RedirectStandardError = $true
$pinfo.CreateNoWindow = $true

$p = New-Object System.Diagnostics.Process
$p.StartInfo = $pinfo
$p.Start() | Out-Null

Write-Host "=== Build Output ==="
$stdout = $p.StandardOutput.ReadToEnd()
$stderr = $p.StandardError.ReadToEnd()
$p.WaitForExit()

Write-Host $stdout
Write-Host $stderr

Write-Host "`n=== Build Complete ==="
Write-Host "Exit code: " $p.ExitCode

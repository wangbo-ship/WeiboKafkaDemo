# Install local JARs from lib/ into Maven local repository (~/.m2).
# Run once after cloning, or when lib/ jars change:
#   powershell -ExecutionPolicy Bypass -File scripts/install-local-jars.ps1

$ErrorActionPreference = "Stop"
$projectRoot = Split-Path -Parent $PSScriptRoot
$lib = Join-Path $projectRoot "lib"
$m2 = Join-Path $env:USERPROFILE ".m2\repository"

if (-not (Test-Path $lib)) {
    throw "lib folder not found: $lib"
}

$artifacts = @(
    @{ GroupId = "org.geodt"; ArtifactId = "sos2.0"; Version = "2.0"; SourceFile = "newsos-2.0.jar" },
    @{ GroupId = "org.geodt"; ArtifactId = "sml"; Version = "2.0"; SourceFile = "sml-2.0.jar" },
    @{ GroupId = "org.geodt"; ArtifactId = "sos"; Version = "1.0"; SourceFile = "sos-1.0.jar" },
    @{ GroupId = "org.geodt"; ArtifactId = "soap"; Version = "1.0"; SourceFile = "soap-2.0.jar" },
    @{ GroupId = "com.example"; ArtifactId = "geodt-xmlbeans-filters"; Version = "2.0"; SourceFile = "filters-2.0.jar" },
    @{ GroupId = "net.hydromatic"; ArtifactId = "linq4j"; Version = "0.3"; SourceFile = "linq4j-0.3.jar" }
)

function Install-LocalJar {
    param($artifact)

    $groupPath = ($artifact.GroupId -replace '\.', '\')
    $dir = Join-Path $m2 "$groupPath\$($artifact.ArtifactId)\$($artifact.Version)"
    $source = Join-Path $lib $artifact.SourceFile
    $jarName = "$($artifact.ArtifactId)-$($artifact.Version).jar"
    $targetJar = Join-Path $dir $jarName
    $targetPom = Join-Path $dir "$($artifact.ArtifactId)-$($artifact.Version).pom"

    if (-not (Test-Path $source)) {
        throw "Missing jar: $source"
    }

    New-Item -ItemType Directory -Force -Path $dir | Out-Null
    Copy-Item $source $targetJar -Force
    Get-ChildItem $dir -Filter "*.lastUpdated" -ErrorAction SilentlyContinue | Remove-Item -Force

    $pom = @"
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>$($artifact.GroupId)</groupId>
    <artifactId>$($artifact.ArtifactId)</artifactId>
    <version>$($artifact.Version)</version>
    <packaging>jar</packaging>
</project>
"@
    Set-Content -Path $targetPom -Value $pom -Encoding UTF8
    Write-Host "Installed $($artifact.GroupId):$($artifact.ArtifactId):$($artifact.Version) -> $targetJar"
}

foreach ($artifact in $artifacts) {
    Install-LocalJar $artifact
}

Write-Host ""
Write-Host "Done. In IDEA: Maven panel -> Reload All Maven Projects, then rebuild."

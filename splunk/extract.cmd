@REM echo off

echo This tool requires 7zip in the path to work (https://www.7-zip.org)

7z x -tgzip prohelion_telemetry.spl  prohelion_telemetry.tar
7z x -ttar prohelion_telemetry.tar  prohelion_telemetry/

del prohelion_telemetry.tar

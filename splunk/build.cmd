@REM echo off

echo This tool requires 7zip in the path to work (https://www.7-zip.org)

7z a -ttar prohelion_telemetry.tar  prohelion_telemetry/
7z a -tgzip prohelion_telemetry.spl  prohelion_telemetry.tar
del prohelion_telemetry.tar

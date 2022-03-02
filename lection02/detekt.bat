@ECHO OFF
TITLE Check style with Detekt
SET ROOT_FOLDER=build\bin
SET VERSION=1.13.1
SET DETEKT_BIN=%ROOT_FOLDER%\detekt-%VERSION%.jar
if not exist %DETEKT_BIN% (
  ECHO Please wait, first download...
  if not exist %ROOT_FOLDER% MKDIR %ROOT_FOLDER%
  curl.exe -sL "https://github.com/detekt/detekt/releases/download/v%VERSION%/detekt-cli-%VERSION%-all.jar" -o "%DETEKT_BIN%"
)

java -jar %DETEKT_BIN% --config .github/workflows/assets/detekt.yml %*
echo Done!
PAUSE

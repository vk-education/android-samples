@ECHO OFF
TITLE Check style with KtLint
SET ROOT_FOLDER=build\bin
SET VERSION=0.38.1
SET KTLINT_BIN=%ROOT_FOLDER%\ktlint-%VERSION%.jar
if not exist %KTLINT_BIN% (
  ECHO Please wait, first download...
  if not exist %ROOT_FOLDER% MKDIR %ROOT_FOLDER%
  curl.exe -sL "https://github.com/pinterest/ktlint/releases/download/%VERSION%/ktlint" -o "%KTLINT_BIN%"
)

java -jar %KTLINT_BIN% --android %*
echo Done!
PAUSE

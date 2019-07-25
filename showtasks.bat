call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo There were problems with compilation, try to call runcrud.bat and check errors.
goto fail

:openbrowser
start chrome http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL$" == "0" goto end
start firefox http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot open browser.
goto fail

:fail
echo There were errors in process.

:end
echo Work is finished.
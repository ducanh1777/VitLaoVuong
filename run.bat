@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk1.8.0_321"

echo ======================================================
echo           VIT LAO VUONG - KHOI DONG HE THONG
echo ======================================================

if not exist "%JAVA_HOME%" (
    echo [LOI] Khong tim thay Java tai: %JAVA_HOME%
    echo Vui long mo file 'run.bat' va sua lai duong dan JAVA_HOME neu can.
    pause
    exit /b
)

REM Lay duong dan hien tai va xoa dau \ o cuoi neu co
set "PROJECT_DIR=%~dp0"
if "%PROJECT_DIR:~-1%"=="\" set "PROJECT_DIR=%PROJECT_DIR:~0,-1%"

REM Tao thu muc du lieu rieng tren o D de tranh loi day o C
if not exist "%PROJECT_DIR%\data" mkdir "%PROJECT_DIR%\data"

echo [OK] Tim thay Java tai: %JAVA_HOME%
echo [OK] Thu muc du an: %PROJECT_DIR%
echo [INFO] O C: cua ban da het dung luong (0 byte free). 
echo [INFO] He thong se su dung thu muc: %PROJECT_DIR%\data de luu tru Maven.
echo.
echo Dang khoi tao moi truong va tai Maven (lan dau se hoi lau tu 2-5 phut)...
echo Vui long cho doi...
echo.

"%JAVA_HOME%\bin\java.exe" -Duser.home="%PROJECT_DIR%\data" -classpath "%PROJECT_DIR%\.mvn\wrapper\maven-wrapper.jar" -Dmaven.multiModuleProjectDirectory="%PROJECT_DIR%" org.apache.maven.wrapper.MavenWrapperMain jetty:run

pause

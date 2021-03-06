@echo off
setlocal

set JAVA=c:/j2sdk1.4.2_15/bin/java
set JAVA_FLAGS= -ms5m -mx32m %JAVA_FLAGS%

set PREFIX=c:/jacl141
set XP_TCLJAVA_INSTALL_DIR=%PREFIX%/lib/tcljava1.4.1

set CLASSPATH=%CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/tcljava.jar
set CLASSPATH=%CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/jacl.jar
set CLASSPATH=%CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/itcl.jar
set CLASSPATH=%CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/tjc.jar

rem Debug CLASSPATH used when atteching a debugger to Jacl.
set JACL_DEBUG_CLASSPATH=%CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/tcljavasrc.jar
set JACL_DEBUG_CLASSPATH=%JACL_DEBUG_CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/jaclsrc.jar
set JACL_DEBUG_CLASSPATH=%JACL_DEBUG_CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/itclsrc.jar
set JACL_DEBUG_CLASSPATH=%JACL_DEBUG_CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/tjcsrc.jar
set JACL_DEBUG_CLASSPATH=%JACL_DEBUG_CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/janino.jar
set JACL_DEBUG_CLASSPATH=%JACL_DEBUG_CLASSPATH%;%XP_TCLJAVA_INSTALL_DIR%/janinosrc.jar

rem If JACL_DEBUG is set to 1, start the java executable with
rem  debug flags so that a Java debugger can attach to the Jacl process.
rem One can't typically run Jacl as a child in a debugger because
rem interactive Jacl requires a console.

IF "%JACL_DEBUG%"=="" goto skipdebug
set JAVA_FLAGS=%JAVA_FLAGS% -Xdebug -Xrunjdwp:transport=dt_socket,address=8757,server=y,suspend=y
set CLASSPATH=%JACL_DEBUG_CLASSPATH%
rem echo JACL_DEBUG_CLASSPATH is %CLASSPATH%

set ATTACH=-attach 8757
IF "%ATTACH%"=="" goto skipdebug
echo Attach to shell via %ATTACH%
:skipdebug


rem If JACL_MAIN is set then use it as the name of the Java
rem class to execute. If it is not set, then use tcl.lang.Shell.
rem This provides an easy way to launch an alternative shell
rem without having to duplicate all the CLASSPATH and JAVA logic.

IF "%JACL_MAIN%"=="" goto jacldefaultmain
goto jaclcustommain
:jacldefaultmain
set JACL_MAIN=tcl.lang.Shell
:jaclcustommain

%JAVA% %JAVA_FLAGS% %JACL_MAIN% %1 %2 %3 %4 %5 %6 %7 %8 %9

endlocal


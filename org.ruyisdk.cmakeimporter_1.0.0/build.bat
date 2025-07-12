@echo off
echo 正在编译和打包 CMake Project Importer 插件...

REM 创建临时目录
if not exist "temp" mkdir temp
if not exist "temp\src" mkdir temp\src
if not exist "temp\META-INF" mkdir temp\META-INF

REM 复制源文件
xcopy /E /I /Y "src" "temp\src"
xcopy /E /I /Y "META-INF" "temp\META-INF"

REM 复制其他文件
copy "plugin.xml" "temp\"
copy "build.properties" "temp\"

REM 创建JAR文件
cd temp
jar -cfm ..\org.ruyisdk.cmakeimporter_1.0.0.jar META-INF\MANIFEST.MF *

REM 清理临时目录
cd ..
rmdir /S /Q temp

echo 打包完成！生成文件：org.ruyisdk.cmakeimporter_1.0.0.jar
echo 你可以将此JAR文件放到Eclipse的dropins目录中使用。
pause 
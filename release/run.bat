@echo off
java --module-path ".\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml -jar .\cookbook-1.0-SNAPSHOT-shaded.jar
pause

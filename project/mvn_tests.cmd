@echo off

call mvn_config.cmd

call mvn.cmd test

pause

# Bundlr(tm)
Specifieer de directories / files die gebundeld moeten worden door het script in bundlr.cfg

Bundling(tm) kan op volgende manieren: 

# EXE
hier uitleg over hoe ge da runt met de exe

# BASH
hier uitleg over hoe ge da runt met bash

# PYTHON
## Requirements
This script requires at least 1 parameter:
   1: Your name, to be used in the zip's name (e.g.: BolaTom).
   2: (Optional) The .txt file with the names of the files or folders you wish to zip. Defaults to 'files.txt'.

### txt file
* The first line of this file must contain the path to the directory containing the files to be zipped
* The file must also contain the paths to all files and directories that need to be present

## Execution
run the python script: python bundlr.py YourName

### Note
The program always creates a zip file, even if certain files might be missing.
 
# JAVA
Double click the jar file to start the program. 
## Requirements
The program requires a 'files.txt', in the same directory as the jar file.
  * The first line of this file must contain the path to the directory containing the files to be zipped
  * The file must also contain the paths to all files and directories that need to be present
## Execution
After running the jar, a message will appear detailing which files and or directories are missing (if any).
### Note
The program always creates a zip file, even if certain files might be missing.

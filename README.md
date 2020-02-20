# Bundlr™

## Bundlr.py
This script contains the logic that is needed to Bundl™.

## runToBundleFiles.py

### Use as lector
This is the script you want to edit as lector.
- You first define a variable 'folder' which is the relative or absolute path to the root folder you want to Bundl™ the files from
* bundlr.add(rootFolder, filePath)
```python
bundlr.add(folder, "folder1/file.test")
```
Adds a file to the list of files to include in the final zip-file

```python
bundlr.add(folder, "folder1/folder1.1")
```
Adds the whole folder to the list of files to include in the final zip-file

* bundlr.addAllEndingIn(rootFolder, endingOfFile):
```python
bundlr.addAllEndingIn(folder, ".js")
```
Adds all '.js' files to the final zip-file

### Use as student
As a student you are not allowed to edit this file, doing so might change the files which are included in the final zip-file and therefor might affect your grade.

You want to run this file like this e.g.:
```python
python runToBundleFiles.py r0702794_JohnDoe
```

After execution the script will tell you which files you are missing. Make sure to check this output!

SUCCESS: A zip-file has been created, ready fro you to upload to xToledo.
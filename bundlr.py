import sys
import os
import re
import shutil
from distutils.dir_util import copy_tree
from optparse import OptionParser

filesAndFolders = []
absentFiles = []
    
# add folder or file
def add(path):
    filesAndFolders.append(path)
def addAllEndingIn(folder, ends, depth = 0):
    for x in os.listdir(folder):
        t = os.path.join(folder, x)
        if os.path.isfile(t) and x.endswith(ends):
            filesAndFolders.append(re.sub(r"^[^/]*/", "", t))
        elif os.path.isdir(t):
            addAllEndingIn(t, ends)
            
# make the actual zip
def createZip(folder="./", outputfile="YourNameAndOrRNumber_zip_to_upload"):
    print("\nCreating a zip file '"+outputfile+"'")
    
    # copy all files to temp folder
    if not os.path.isdir('temp'): os.mkdir("temp")
    for x in filesAndFolders:
        t = os.path.join(folder, x)
        p = 'temp/'+x
        if(os.path.exists(t)):
            if os.path.isdir(t):
                copy_tree(t, p)
            elif os.path.isfile(t):
                p2 = re.sub(r"/[^/]*$", "", p)
                if not os.path.exists(p2):
                    os.makedirs(p2)
                if not os.path.exists(p): shutil.copy(t, p)
        else:
            absentFiles.append(t)

    # showing the user what files he is missing
    if len(absentFiles) > 0:
        print( "\nYou are missing these files and/or folders:" )
        for x in absentFiles:
            print("  -    "+ x)
    # zipping the temp folder
    print("\nAdding all files to zip file '"+outputfile+"'...")
    shutil.make_archive(outputfile, 'zip', "temp/")
    # remove the temp folder
    print('\nCleaning up temporary files...')
    if os.path.exists('temp/'): shutil.rmtree("temp/")
    # done
    print("\nSUCCESS: Your zip should be created!\n")
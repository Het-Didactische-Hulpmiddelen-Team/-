import bundlr
import glob
import sys

# PARAM
if len(sys.argv) > 1:
    name = sys.argv[1]
else:
    exitstr = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    exitstr += "\nPlease provide your name and R-number as first parameter! e.g.: python runToBundleFiles.py r0702794_JohnDoe"
    exitstr += "\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    sys.exit(exitstr)

outputfile = name + "_zip_to_upload"

folder = "examen_vragen/"

bundlr.add("folder3/arne.tech")
bundlr.add("fonder2000")
bundlr.add("folder1/folder1.1")
bundlr.add("klonk/absent.file")
bundlr.add("koek.java")
# bundlr.addAllEndingIn(folder, ".tech")
bundlr.addAllEndingIn(folder, ".js")
# bundlr.addAllEndingIn(folder, ".java")

bundlr.createZip(folder, outputfile)
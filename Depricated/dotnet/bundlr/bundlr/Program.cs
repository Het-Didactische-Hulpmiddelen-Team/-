using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace bundlr
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] lines = File.ReadAllLines(".gitignore");

            string outputDir = ".tmp/";
            List<String> errors = new List<string>();
            CreateFolderIfNeeded(outputDir);
            string first = lines.First();
            string foldername = first.Trim().Replace(".", "").Replace("/", "");
            string outputZip = foldername + ".zip";

            if (first.Last() != '/')
            {
                first += "/";
            }

            lines = lines.Skip(1).ToArray();
            for(int i = 0;i< lines.Length;i++)
            {

                if (lines.First().Length != 0)
                {
                    string line = first + lines[i];
                    FileAttributes attr;
                    try
                    {
                        attr = File.GetAttributes(@line);
                        String tempLine = line.Replace("../", "");
                        if (attr.HasFlag(FileAttributes.Directory))
                        {
                            CreateFolderIfNeeded(outputDir + tempLine);
                            foreach (string file in DirSearch(line))
                            {
                                String tempFile = file.Replace("../", "");
                                CreateFolderIfNeeded(outputDir + tempFile);
                                File.Copy(file, outputDir + tempFile, true);
                            }
                        }
                        else
                        {
                            CreateFolderIfNeeded(outputDir + tempLine);
                            File.Copy(line, outputDir + tempLine, true);
                        }
                    }
                    catch (Exception)
                    {
                        errors.Add(line.Trim().Replace(first, ""));
                    }
                }

            }

            if (File.Exists(outputZip))
            {
                File.Delete(outputZip);
            }
            System.IO.Compression.ZipFile.CreateFromDirectory(outputDir + foldername, outputZip);
            delete(outputDir);
            if (errors.Count > 0)
            {
                Console.WriteLine("Deze files/folders waren niet gevonden...");
                foreach (String error in errors)
                {
                    Console.WriteLine(error);
                }
                Console.ReadKey();
            }
        }

        private static List<String> DirSearch(string sDir)
        {
            List<String> files = new List<String>();
            try
            {
                foreach (string f in Directory.GetFiles(sDir))
                {
                    files.Add(f);
                }
                foreach (string d in Directory.GetDirectories(sDir))
                {
                    files.AddRange(DirSearch(d));
                }
            }
            catch (Exception excpt)
            {
                Console.WriteLine(excpt.Message);
            }

            return files;
        }
        public static void CreateFolderIfNeeded(string filename)
        {
            string folder = Path.GetDirectoryName(filename);
            Directory.CreateDirectory(folder);

        }

        public static void delete(string folder)
        {
            DirectoryInfo di = new DirectoryInfo(folder);

            foreach (FileInfo file in di.GetFiles())
            {
                file.Delete();
            }
            foreach (DirectoryInfo dir in di.GetDirectories())
            {
                dir.Delete(true);
            }
            Directory.Delete(folder);
        }

    }
}

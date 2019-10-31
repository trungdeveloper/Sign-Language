using Microsoft.AspNetCore.Http;
using System;
using System.IO;

namespace OMS.API.Core.Common.Helpers
{
    public class FileHelper
    {
        public static string SaveFile(string folder, string fileName, IFormFile file)
        {
            var filePath = MapPath("/Files/" + folder);

            Directory.CreateDirectory(filePath);

            var returnPath = string.Empty;
            
            if (file.Length > 0)
            {
                var extension = Path.GetExtension(file.FileName);
                using (var fileStream = new FileStream(Path.Combine(filePath, string.Format("{0}{1}", fileName, extension)), FileMode.Create))
                {
                    file.CopyTo(fileStream);
                }

                returnPath = string.Format("/Files/{0}/{1}", folder, string.Format("{0}{1}", fileName, extension));
            }

            return returnPath;
        }

        public static string GetFile(string folder, string fileName)
        {
            var filePath = MapPath("/Files/" + folder + "/" + fileName);
            return filePath;
        }

        public static void RemoveFile(string folder, string fileName)
        {
            var filePath = MapPath("/Files/" + folder + "/" + fileName);
            File.Delete(filePath);

        }

        public static string MapPath(string path, string basePath = null)
        {
            if (string.IsNullOrEmpty(basePath))
            {
                basePath = "wwwroot";
            }

            path = path.Replace("~/", "").TrimStart('/').Replace('/', '\\');
            return Path.Combine(basePath, path);
        }
    }
}
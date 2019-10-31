using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using OMS.API.Core.Common.Helpers;
using System;
using System.IO;
using System.Threading.Tasks;

namespace OMS.API.Controllers
{
    [Route("api/media")]
    [EnableCors("CorsPolicy")]
    public class MediaController : Controller
    {
        [HttpPost("upload")]
        public async Task<IActionResult> UploadFile(string folder, IFormFile file)
        {
            string fileName = Guid.NewGuid().ToString();

            var task = Task.Run(() => FileHelper.SaveFile(folder, fileName, file));

            await Task.WhenAll(task);

            var extension = Path.GetExtension(file.FileName);

            return Ok(Url.Action("GetFile", "Media", new { folder = folder, fileName = string.Format("{0}{1}", fileName, extension) }, Request.Scheme));
        }

        [HttpGet("{folder}/{fileName}")]
        public async Task<IActionResult> GetFile(string folder, string fileName)
        {
            var task = Task.Run(() => FileHelper.GetFile(folder, fileName));

            await Task.WhenAll(task);

            var fileStream = System.IO.File.OpenRead(task.Result);

            string contentType = GetMimeType(fileName);

            return base.File(fileStream, contentType);
        }
        
        private string GetMimeType(string fileName)
        {
            string mimeType = "application/unknown";
            string ext = Path.GetExtension(fileName).ToLower();
            Microsoft.Win32.RegistryKey regKey = Microsoft.Win32.Registry.ClassesRoot.OpenSubKey(ext);
            if (regKey != null && regKey.GetValue("Content Type") != null)
                mimeType = regKey.GetValue("Content Type").ToString();
            return mimeType;
        }
    }
}
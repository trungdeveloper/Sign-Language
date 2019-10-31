using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Business.Services;
using System.Threading.Tasks;

namespace OMS.API.Controllers
{
    [Route("api/dictionary")]
    [EnableCors("CorsPolicy")]
    public class DictionaryController : Controller
    {
        private readonly IDictionaryService _dictionaryService;

        public DictionaryController(IDictionaryService dictionaryService)
        {
            _dictionaryService = dictionaryService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll(PostRequestListViewModel postRequestListViewModel)
        {
            var posts = await _dictionaryService.ListDictionaryPostsAsync(postRequestListViewModel);
            return Ok(posts);
        }
    }
}

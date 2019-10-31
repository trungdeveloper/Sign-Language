using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using OMS.API.Core.Business.Filters;
using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Business.Services;

namespace OMS.API.Controllers
{
    [Route("api/posts")]
    [EnableCors("CorsPolicy")]
    [ValidateModel]
    public class PostController : Controller
    {
        private readonly IPostService _postService;

        public PostController(IPostService postService)
        {
            _postService = postService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll(PostRequestListViewModel postRequestListViewModel)
        {
            var posts = await _postService.ListPostAsync(postRequestListViewModel);
            return Ok(posts);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetPostById(Guid id)
        {
            var post = await _postService.GetPostByIdAsync(id);

            if (post != null)
            {
                return Ok(post);
            }
            return NotFound("This post doesn't existence!");
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] PostManageModel postManageModel)
        {
            var responseModel = await _postService.CreatePostAsync(postManageModel);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return Ok(responseModel.Data);
            }
            return BadRequest(responseModel.Message);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(Guid id, [FromBody] PostManageModel postManageModel)
        {
            var responseModel = await _postService.UpdatePostAsync(id, postManageModel);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return Ok(responseModel.Data);
            }
            return BadRequest(responseModel.Message);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(Guid id)
        {
            var responseModel = await _postService.DeletePostAsync(id);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return Ok(responseModel.Data);
            }
            return BadRequest(responseModel.Message);
        }
    }
}
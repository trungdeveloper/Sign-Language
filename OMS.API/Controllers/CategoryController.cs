using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using OMS.API.Core.Business.Models.Categories;
using OMS.API.Core.Business.Services;

namespace OMS.API.Controllers
{
    [Route("api/categories")]
    [EnableCors("CorsPolicy")]
    public class CategoryController : Controller
    {
        private readonly ICategoryService _categoryService;

        public CategoryController(ICategoryService categoryService)
        {
            _categoryService = categoryService;
        }

        [HttpGet]
        public async Task<IActionResult> Get(CategoryRequestListViewModel categoryRequestListViewModel)
        {
            var categories = await _categoryService.ListCategoryAsync(categoryRequestListViewModel);
            return Ok(categories);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetCategoryById(Guid id)
        {
            var category = await _categoryService.GetCategoryByIdAsync(id);

            if (category != null)
            {
                return Ok(category);
            }
            return NotFound("This category doesn't existence!");
        }
    }
}
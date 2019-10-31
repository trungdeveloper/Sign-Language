using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using OMS.API.Core.Business.Filters;
using OMS.API.Core.Business.Models.SubCategories;
using OMS.API.Core.Business.Services;

namespace OMS.API.Controllers
{
    [Route("api/subcategories")]
    [EnableCors("CorsPolicy")]
    [ValidateModel]
    public class SubCategoryController : Controller
    {
        private readonly ISubCategoryService _subCategoryService;

        public SubCategoryController(ISubCategoryService subCategoryService)
        {
            _subCategoryService = subCategoryService;
        }

        [HttpGet]
        public async Task<IActionResult> Get(SubCategoryRequestListViewModel subCategoryRequestListViewModel)
        {
            var subCategories = await _subCategoryService.ListSubCategoryAsync(subCategoryRequestListViewModel);
            return Ok(subCategories);
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetSubCategoryById(Guid id)
        {
            var category = await _subCategoryService.GetSubCategoryByIdAsync(id);

            if (category != null)
            {
                return Ok(category);
            }
            return NotFound("This subcategory doesn't existence!");
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] SubCategoryManageModel subCategoryManageModel)
        {
            var responseModel = await _subCategoryService.CreateSubCategoryAsync(subCategoryManageModel);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var subCategory = (CustomSubCategoryViewModel)responseModel.Data;
                return Ok(subCategory);
            }
            return BadRequest(responseModel.Message);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update(Guid id, [FromBody] SubCategoryManageModel subCategoryManageModel)
        {
            var responseModel = await _subCategoryService.UpdateSubCategoryAsync(id, subCategoryManageModel);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                return Ok(responseModel.Data);
            }
            return BadRequest(responseModel.Message);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(Guid id)
        {
            var responseModel = await _subCategoryService.DeleteSubCategoryAsync(id);
            if (responseModel.StatusCode == System.Net.HttpStatusCode.OK)
            {
                var subCategory = (CustomSubCategoryViewModel)responseModel.Data;
                return Ok(subCategory);
            }
            return BadRequest(responseModel.Message);
        }
    }
}
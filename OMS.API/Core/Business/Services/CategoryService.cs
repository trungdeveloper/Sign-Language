using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Business.Models.Base;
using OMS.Api.Core.Common.Constants;
using OMS.Api.Core.Common.Reflections;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.Api.Core.Entities;
using OMS.API.Core.Business.Models.Categories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OMS.API.Core.Business.Services
{
    public interface ICategoryService
    {
        Task<PagedList<CustomCategoryViewModel>> ListCategoryAsync(CategoryRequestListViewModel categoryRequestListViewModel);
        Task<CategoryViewModel> GetCategoryByIdAsync(Guid id);
    }

    public class CategoryService : ICategoryService
    {
        private readonly IRepository<Category> _categoryRepository;

        public CategoryService(IRepository<Category> categoryRepository)
        {
            _categoryRepository = categoryRepository;
        }

        public async Task<PagedList<CustomCategoryViewModel>> ListCategoryAsync(CategoryRequestListViewModel categoryRequestListViewModel)
        {
            var list = await GetAll()
                .Where(x => (string.IsNullOrEmpty(categoryRequestListViewModel.Query)
                    || (x.Name.Contains(categoryRequestListViewModel.Query))))
                        .Select(x => new CustomCategoryViewModel(x)).ToListAsync();

            var categoryViewModelProperties = GetAllPropertyNameOfCategoryViewModel();
            var requestPropertyName = !string.IsNullOrEmpty(categoryRequestListViewModel.SortName) ? categoryRequestListViewModel.SortName.ToLower() : string.Empty;
            string matchedPropertyName = string.Empty;

            foreach (var categoryViewModelProperty in categoryViewModelProperties)
            {
                var lowerTypeViewModelProperty = categoryViewModelProperty.ToLower();
                if (lowerTypeViewModelProperty.Equals(requestPropertyName))
                {
                    matchedPropertyName = categoryViewModelProperty;
                    break;
                }
            }

            if (string.IsNullOrEmpty(matchedPropertyName))
            {
                matchedPropertyName = "Name";
            }

            var type = typeof(CustomCategoryViewModel);
            var sortProperty = type.GetProperty(matchedPropertyName);

            list = categoryRequestListViewModel.IsDesc ? list.OrderByDescending(x => sortProperty.GetValue(x, null)).ToList() : list.OrderBy(x => sortProperty.GetValue(x, null)).ToList();

            return new PagedList<CustomCategoryViewModel>(list, categoryRequestListViewModel.Offset ?? CommonConstants.Config.DEFAULT_SKIP, categoryRequestListViewModel.Limit ?? CommonConstants.Config.DEFAULT_TAKE);
        }

        public async Task<CategoryViewModel> GetCategoryByIdAsync(Guid id)
        {
            var category = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (category != null)
            {
                return new CategoryViewModel(category);
            }
            return null;
        }
        
        private IQueryable<Category> GetAll()
        {
            var data = _categoryRepository.GetAll()
                .Include(x => x.SubCategories)
                .Where(x => !x.RecordDeleted);
            return data;
        }

        private List<string> GetAllPropertyNameOfCategoryViewModel()
        {
            var categoryViewModel = new CategoryViewModel();
            var type = categoryViewModel.GetType();

            return ReflectionUtilities.GetAllPropertyNamesOfType(type);
        }
    }
}
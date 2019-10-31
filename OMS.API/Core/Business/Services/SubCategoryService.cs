using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Business.Models.Base;
using OMS.Api.Core.Common.Constants;
using OMS.Api.Core.Common.Reflections;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.API.Core.Business.Models.SubCategories;
using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OMS.API.Core.Business.Services
{
    public interface ISubCategoryService
    {
        Task<PagedList<CustomSubCategoryViewModel>> ListSubCategoryAsync(SubCategoryRequestListViewModel subCategoryRequestListViewModel);

        Task<SubCategoryViewModel> GetSubCategoryByIdAsync(Guid id);

        Task<ResponseModel> CreateSubCategoryAsync(SubCategoryManageModel subCategoryManageModel);

        Task<ResponseModel> UpdateSubCategoryAsync(Guid id, SubCategoryManageModel subCategoryManageModel);

        Task<ResponseModel> DeleteSubCategoryAsync(Guid id);
    }

    public class SubCategoryService : ISubCategoryService
    {
        private readonly IRepository<SubCategory> _subCategoryRepository;

        public SubCategoryService(IRepository<SubCategory> subCategoryRepository)
        {
            _subCategoryRepository = subCategoryRepository;
        }

        public async Task<PagedList<CustomSubCategoryViewModel>> ListSubCategoryAsync(SubCategoryRequestListViewModel subCategoryRequestListViewModel)
        {
            var list = await GetAll()
                .Where(x => (string.IsNullOrEmpty(subCategoryRequestListViewModel.Query)
                    || (x.Name.Contains(subCategoryRequestListViewModel.Query))))
                        .Select(x => new CustomSubCategoryViewModel(x)).ToListAsync();

            var categoryViewModelProperties = GetAllPropertyNameOfCategoryViewModel();
            var requestPropertyName = !string.IsNullOrEmpty(subCategoryRequestListViewModel.SortName) ? subCategoryRequestListViewModel.SortName.ToLower() : string.Empty;
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

            var type = typeof(CustomSubCategoryViewModel);
            var sortProperty = type.GetProperty(matchedPropertyName);

            list = subCategoryRequestListViewModel.IsDesc ? list.OrderByDescending(x => sortProperty.GetValue(x, null)).ToList() : list.OrderBy(x => sortProperty.GetValue(x, null)).ToList();

            return new PagedList<CustomSubCategoryViewModel>(list, subCategoryRequestListViewModel.Offset ?? CommonConstants.Config.DEFAULT_SKIP, subCategoryRequestListViewModel.Limit ?? CommonConstants.Config.DEFAULT_TAKE);
        }

        public async Task<SubCategoryViewModel> GetSubCategoryByIdAsync(Guid id)
        {
            var subCategory = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (subCategory != null)
            {
                return new SubCategoryViewModel(subCategory);
            }
            return null;
        }

        public async Task<ResponseModel> CreateSubCategoryAsync(SubCategoryManageModel subCategoryManageModel)
        {
            var subCategory = await _subCategoryRepository.FetchFirstAsync(x => x.Name == subCategoryManageModel.Name && x.CategoryId == subCategoryManageModel.CategoryId);
            if (subCategory != null)
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.BadRequest,
                    Message = "This subcategory has existed. You can try again with update!",
                };
            }
            else
            {
                subCategory = AutoMapper.Mapper.Map<SubCategory>(subCategoryManageModel);

                await _subCategoryRepository.InsertAsync(subCategory);

                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.OK,
                    Data = new CustomSubCategoryViewModel(subCategory)
                };
            }
        }

        public async Task<ResponseModel> UpdateSubCategoryAsync(Guid id, SubCategoryManageModel subCategoryManageModel)
        {
            var subCategory = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (subCategory != null)
            {
                var existedSubCategoryName = await _subCategoryRepository.FetchFirstAsync(x => x.Name == subCategoryManageModel.Name && x.Id != id);
                if (existedSubCategoryName != null)
                {
                    return new ResponseModel()
                    {
                        StatusCode = System.Net.HttpStatusCode.BadRequest,
                        Message = "Subcategory " + subCategoryManageModel.Name + " has already existed. Please try another name!"
                    };
                }
                else
                {
                    subCategoryManageModel.SetSubCategoryModel(subCategory);

                    await _subCategoryRepository.UpdateAsync(subCategory);

                    return new ResponseModel()
                    {
                        StatusCode = System.Net.HttpStatusCode.OK,
                        Data = new CustomSubCategoryViewModel(subCategory)
                    };
                }
            }
            else
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.BadRequest,
                    Message = "This subcategory has not existed. Please try again!",
                };
            }
        }

        public async Task<ResponseModel> DeleteSubCategoryAsync(Guid id)
        {
            var subCategory = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (subCategory != null)
            {
                await _subCategoryRepository.DeleteAsync(id);
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.OK,
                    Data = new CustomSubCategoryViewModel(subCategory)
                };
            }
            else
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.NotFound,
                    Message = "This subcategory has not existed. Please try again!"
                };
            }
        }

        private IQueryable<SubCategory> GetAll()
        {
            return _subCategoryRepository.GetAll()
                .Include(x => x.Category)
                .Include(x => x.Posts)
                .Where(x => !x.RecordDeleted);
        }

        private List<string> GetAllPropertyNameOfCategoryViewModel()
        {
            var subCategoryViewModel = new SubCategoryViewModel();
            var type = subCategoryViewModel.GetType();

            return ReflectionUtilities.GetAllPropertyNamesOfType(type);
        }
    }
}
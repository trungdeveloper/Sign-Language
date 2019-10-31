using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Business.Models.Base;
using OMS.Api.Core.Common.Constants;
using OMS.Api.Core.Common.Reflections;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Common.Constants;
using OMS.API.Core.Entities;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OMS.API.Core.Business.Services
{

    public interface IDictionaryService
    {
        Task<PagedList<PostViewModel>> ListDictionaryPostsAsync(PostRequestListViewModel postRequestListViewModel);
    }

    public class DictionaryService : IDictionaryService
    {
        private readonly IRepository<Post> _postRepository;

        public DictionaryService(IRepository<Post> postRepository)
        {
            _postRepository = postRepository;
        }

        public async Task<PagedList<PostViewModel>> ListDictionaryPostsAsync(PostRequestListViewModel postRequestListViewModel)
        {
            var list = await GetAll()
                .Where(x => (string.IsNullOrEmpty(postRequestListViewModel.Query)
                    || (x.Keyword.Contains(postRequestListViewModel.Query))))
                .Where(x => x.SubCategory.CategoryId.Equals(CategoryConstants.Dictionary))
                        .Select(x => new PostViewModel(x)).ToListAsync();

            var categoryViewModelProperties = GetAllPropertyNameOfCategoryViewModel();
            var requestPropertyName = !string.IsNullOrEmpty(postRequestListViewModel.SortName) ? postRequestListViewModel.SortName.ToLower() : string.Empty;
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
                matchedPropertyName = "Keyword";
            }

            var type = typeof(PostViewModel);
            var sortProperty = type.GetProperty(matchedPropertyName);

            list = postRequestListViewModel.IsDesc ? list.OrderByDescending(x => sortProperty.GetValue(x, null)).ToList() : list.OrderBy(x => sortProperty.GetValue(x, null)).ToList();

            return new PagedList<PostViewModel>(list, postRequestListViewModel.Offset ?? CommonConstants.Config.DEFAULT_SKIP, postRequestListViewModel.Limit ?? CommonConstants.Config.DEFAULT_TAKE);
        }

        private IQueryable<Post> GetAll()
        {
            return _postRepository.GetAll()
                .Include(x => x.SubCategory)
                    .ThenInclude(c => c.Category)
                .Where(x => !x.RecordDeleted);
        }

        private List<string> GetAllPropertyNameOfCategoryViewModel()
        {
            var postViewModel = new PostViewModel();
            var type = postViewModel.GetType();

            return ReflectionUtilities.GetAllPropertyNamesOfType(type);
        }
    }
}

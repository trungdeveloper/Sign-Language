using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Business.Models.Base;
using OMS.Api.Core.Common.Constants;
using OMS.Api.Core.Common.Reflections;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace OMS.API.Core.Business.Services
{
    public interface IPostService
    {
        Task<PagedList<PostViewModel>> ListPostAsync(PostRequestListViewModel postRequestListViewModel);

        Task<PostViewModel> GetPostByIdAsync(Guid id);

        Task<ResponseModel> CreatePostAsync(PostManageModel postManageModel);

        Task<ResponseModel> UpdatePostAsync(Guid id, PostManageModel postManageModel);

        Task<ResponseModel> DeletePostAsync(Guid id);
    }

    public class PostService : IPostService
    {
        private readonly IRepository<Post> _postRepository;

        public PostService(IRepository<Post> postRepository)
        {
            _postRepository = postRepository;
        }

        public async Task<PagedList<PostViewModel>> ListPostAsync(PostRequestListViewModel postRequestListViewModel)
        {
            var list = await GetAll()
                .Where(x => (string.IsNullOrEmpty(postRequestListViewModel.Query)
                    || (x.Keyword.Contains(postRequestListViewModel.Query))))
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

        public async Task<PostViewModel> GetPostByIdAsync(Guid id)
        {
            var post = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (post != null)
            {
                return new PostViewModel(post);
            }
            return null;
        }

        public async Task<ResponseModel> CreatePostAsync(PostManageModel postManageModel)
        {
            var post = await _postRepository.FetchFirstAsync(x => x.Keyword == postManageModel.Keyword && x.SubCategoryId == postManageModel.SubCategoryId);
            if (post != null)
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.BadRequest,
                    Message = "This post has existed. You can try again with update!",
                };
            }
            else
            {
                post = AutoMapper.Mapper.Map<Post>(postManageModel);

                await _postRepository.InsertAsync(post);

                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.OK,
                    Data = new PostViewModel(post)
                };
            }
        }

        public async Task<ResponseModel> UpdatePostAsync(Guid id, PostManageModel postManageModel)
        {
            var post = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (post != null)
            {
                var existedPost = await _postRepository.FetchFirstAsync(x => x.Keyword == postManageModel.Keyword && x.Id != id);
                if (existedPost != null)
                {
                    return new ResponseModel()
                    {
                        StatusCode = System.Net.HttpStatusCode.BadRequest,
                        Message = "This post keyword " + postManageModel.Keyword + " has already existed. Please try another name!"
                    };
                }
                else
                {
                    postManageModel.SetPostModel(post);

                    await _postRepository.UpdateAsync(post);

                    return new ResponseModel()
                    {
                        StatusCode = System.Net.HttpStatusCode.OK,
                        Data = new PostViewModel(post)
                    };
                }
            }
            else
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.BadRequest,
                    Message = "This post has not existed. Please try again!",
                };
            }
        }

        public async Task<ResponseModel> DeletePostAsync(Guid id)
        {
            var post = await GetAll().FirstOrDefaultAsync(x => x.Id == id);
            if (post != null)
            {
                await _postRepository.DeleteAsync(id);
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.OK,
                    Data = new PostViewModel(post)
                };
            }
            else
            {
                return new ResponseModel()
                {
                    StatusCode = System.Net.HttpStatusCode.NotFound,
                    Message = "This post has not existed. Please try again!"
                };
            }
        }

        private IQueryable<Post> GetAll()
        {
            return _postRepository.GetAll()
                .Include(x => x.SubCategory)
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
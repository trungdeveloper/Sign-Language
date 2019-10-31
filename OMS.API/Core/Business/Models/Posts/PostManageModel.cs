using OMS.Api.Core.Business.IoC;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;

namespace OMS.API.Core.Business.Models.Posts
{
    public class PostManageModel : IValidatableObject
    {
        public PostManageModel() { }

        public PostManageModel(Post post)
        {
            if (post != null)
            {
                Keyword = post.Keyword;
                Image = post.Image;
                Video = post.Video;
                SubCategoryId = post.SubCategoryId;
            }
        }

        public void SetPostModel(Post post)
        {
            post.Keyword = Keyword;
            post.Image = Image;
            post.Video = Video;
            post.SubCategoryId = SubCategoryId;
        }

        public string Keyword { get; set; }

        public string Image { get; set; }

        public string Video { get; set; }

        public Guid SubCategoryId { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {

            if (SubCategoryId == null || SubCategoryId == Guid.Empty)
            {
                yield return new ValidationResult("Subcategory is required!");
            }
            else
            {
                var subCategoryRepository = IoCHelper.GetInstance<IRepository<SubCategory>>();

                var subCategory = subCategoryRepository.GetAll().FirstOrDefault(x => x.Id == SubCategoryId);

                if (subCategory == null)
                {
                    yield return new ValidationResult("Subcategory is not found!", new string[] { "SubCategoryId" });
                }
            }

            if (string.IsNullOrEmpty(Keyword))
            {
                yield return new ValidationResult("Keyword is required!");
            }
        }
    }
}

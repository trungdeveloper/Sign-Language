using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;

namespace OMS.API.Core.Business.Models.SubCategories
{
    public class SubCategoryViewModel
    {
        public SubCategoryViewModel() { }

        public SubCategoryViewModel(SubCategory subCategory) : this()
        {
            if (subCategory != null)
            {
                CategoryId = subCategory.CategoryId;
                Id = subCategory.Id;
                Name = subCategory.Name;
                Posts = subCategory.Posts.Select(x => new PostViewModel(x)).ToList();
            }
        }

        public Guid CategoryId { get; set; }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Image { get; set; }

        public List<PostViewModel> Posts { get; set; }
    }
}

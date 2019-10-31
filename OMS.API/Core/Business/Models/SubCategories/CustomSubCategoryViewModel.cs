using OMS.API.Core.Entities;
using System;

namespace OMS.API.Core.Business.Models.SubCategories
{
    public class CustomSubCategoryViewModel
    {
        public CustomSubCategoryViewModel() { }

        public CustomSubCategoryViewModel(SubCategory subCategory) : this()
        {
            if (subCategory != null)
            {
                CategoryId = subCategory.CategoryId;
                Id = subCategory.Id;
                Name = subCategory.Name;
                Image = subCategory.Image;
            }
        }

        public Guid CategoryId { get; set; }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Image { get; set; }
    }
}

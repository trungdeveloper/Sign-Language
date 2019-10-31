using OMS.Api.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;

namespace OMS.API.Core.Business.Models.Categories
{
    public class CategoryViewModel
    {
        public CategoryViewModel() { }

        public CategoryViewModel(Category category) : this()
        {
            if (category != null)
            {
                Id = category.Id;
                Name = category.Name;
                Description = category.Description;
                SubCategories = category.SubCategories.Where(x => !x.RecordDeleted).Select(x => new CustomSubCategoryViewModel(x)).ToList();
            }
        }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public List<CustomSubCategoryViewModel> SubCategories { get; set; }
    }
}

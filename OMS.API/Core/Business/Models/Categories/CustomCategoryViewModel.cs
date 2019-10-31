using OMS.Api.Core.Entities;
using System;

namespace OMS.API.Core.Business.Models.Categories
{
    public class CustomCategoryViewModel
    {
        public CustomCategoryViewModel() { }

        public CustomCategoryViewModel(Category category) : this()
        {
            if (category != null)
            {
                Id = category.Id;
                Name = category.Name;
                Description = category.Description;
            }
        }

        public Guid Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }
    }
}

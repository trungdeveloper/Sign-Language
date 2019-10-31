using OMS.Api.Core.Business.IoC;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.Api.Core.Entities;
using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;

namespace OMS.API.Core.Business.Models.SubCategories
{
    public class SubCategoryManageModel : IValidatableObject
    {
        public SubCategoryManageModel() { }

        public SubCategoryManageModel(SubCategory subCategory)
        {
            if (subCategory != null)
            {
                Name = subCategory.Name;
                Image = subCategory.Image;
                CategoryId = subCategory.CategoryId;
            }
        }

        public void SetSubCategoryModel(SubCategory subCategory)
        {
            subCategory.Name = Name;
            subCategory.Image = Image;
            subCategory.CategoryId = CategoryId;
        }

        public string Name { get; set; }

        public string Image { get; set; }

        public Guid CategoryId { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (CategoryId == null || CategoryId == Guid.Empty)
            {
                yield return new ValidationResult("Category is required!");
            }
            else
            {
                var categoryRepository = IoCHelper.GetInstance<IRepository<Category>>();

                var category = categoryRepository.GetAll().FirstOrDefault(x => x.Id == CategoryId);

                if (category == null)
                {
                    yield return new ValidationResult("Category is not found!", new string[] { "CategoryId" });
                }
            }
        }
    }
}
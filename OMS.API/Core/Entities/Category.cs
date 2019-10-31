using OMS.API.Core.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OMS.Api.Core.Entities
{
    [Table("Category")]
    public class Category : BaseEntity
    {
        public Category() { }

        public Category(Guid id, string name, string description)
        {
            if (id != null)
            {
                Id = id;
            }
            if (!string.IsNullOrEmpty(name))
            {
                Name = name;
            }
            if (!string.IsNullOrEmpty(description))
            {
                Description = description;
            }
        }

        public Category(Category category)
        {
            if (category != null)
            {
                Id = category.Id;
                Name = category.Name;
                Description = category.Description;
            }
        }

        [StringLength(255)]
        [Required]
        public string Name { get; set; }

        [StringLength(512)]
        public string Description { get; set; }

        public ICollection<SubCategory> SubCategories { get; set; }
    }
}

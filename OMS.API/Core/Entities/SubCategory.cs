using OMS.Api.Core.Entities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OMS.API.Core.Entities
{
    [Table("SubCategory")]
    public class SubCategory : BaseEntity
    {
        public SubCategory() { }

        public SubCategory(SubCategory subCategory)
        {
            if (subCategory != null)
            {
                Id = subCategory.Id;
                CategoryId = subCategory.CategoryId;
                Name = subCategory.Name;
            }
        }

        [StringLength(255)]
        [Required]
        public string Name { get; set; }

        [StringLength(512)]
        public string Image { get; set; }

        [Required]
        public Guid CategoryId { get; set; }
        public Category Category { get; set; }

        public ICollection<Post> Posts { get; set; }
    }
}
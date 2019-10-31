using OMS.Api.Core.Entities;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace OMS.API.Core.Entities
{
    [Table("Post")]
    public class Post : BaseEntity
    {
        public Post() { }

        public Post(Post post)
        {
            if (post != null)
            {
                // Gán vô cho object
            }
        }

        [StringLength(255)]
        [Required]
        public string Keyword { get; set; }

        [StringLength(255)]
        public string Image { get; set; }

        [StringLength(255)]
        public string Video { get; set; }

        public Guid SubCategoryId { get; set; }

        public SubCategory SubCategory { get; set; }
    }
}

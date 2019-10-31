using OMS.API.Core.Entities;
using System;

namespace OMS.API.Core.Business.Models.Posts
{
    public class PostViewModel
    {
        public PostViewModel() { }

        public PostViewModel(Post post) : this()
        {
            if (post != null)
            {
                SubCategoryId = post.SubCategoryId;
                Id = post.Id;
                Keyword = post.Keyword;
                Image = post.Image;
                Video = post.Video;
            }
        }

        public Guid SubCategoryId { get; set; }

        public Guid Id { get; set; }

        public string Keyword { get; set; }

        public string Image { get; set; }

        public string Video { get; set; }
    }
}

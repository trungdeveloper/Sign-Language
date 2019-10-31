using OMS.Api.Core.Business.Models.Base;

namespace OMS.API.Core.Business.Models.Posts
{
    public class PostRequestListViewModel : RequestListViewModel
    {
        public PostRequestListViewModel() : base() { }

        public string Query { get; set; }
        public bool? IsActive { get; set; }
    }
}
using OMS.Api.Core.Business.Models.Base;

namespace OMS.API.Core.Business.Models.Categories
{
    public class CategoryRequestListViewModel : RequestListViewModel
    {
        public CategoryRequestListViewModel() : base() { }

        public string Query { get; set; }
        public bool? IsActive { get; set; }
    }
}
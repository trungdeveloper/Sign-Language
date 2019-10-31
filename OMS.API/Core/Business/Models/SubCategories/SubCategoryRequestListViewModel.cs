using OMS.Api.Core.Business.Models.Base;

namespace OMS.API.Core.Business.Models.SubCategories
{
    public class SubCategoryRequestListViewModel : RequestListViewModel
    {
        public SubCategoryRequestListViewModel() : base() { }

        public string Query { get; set; }
        public bool? IsActive { get; set; }
    }
}
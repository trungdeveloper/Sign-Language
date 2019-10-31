using System.Net;

namespace OMS.Api.Core.DataAccess.Repositories
{
    public class ResponseModel
    {
        public HttpStatusCode StatusCode { get; set; }
        public object Data { get; set; }
        public string Message { get; set; }
    }
}

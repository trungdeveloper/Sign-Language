using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;

namespace OMS.API.Controllers
{
    [EnableCors("CorsPolicy")]
    public class HandleController
    {
        [Route("")]
        [Route("api")]
        public string Get()
        {
            return "Chào mừng bạn đã ghé thăm trang web của chúng tôi.";
        }
    }
}

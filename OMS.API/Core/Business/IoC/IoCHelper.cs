using Microsoft.Extensions.DependencyInjection;
using System.Collections.Generic;

namespace OMS.Api.Core.Business.IoC
{
    public class IoCHelper
    {
        private static ServiceProvider _serviceProvider;

        public static void SetServiceProvider(ServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
        }

        public static T GetInstance<T>() where T : class
        {
            return _serviceProvider.GetService<T>();
        }

        public static IEnumerable<T> GetInstances<T>() where T : class
        {
            return _serviceProvider.GetServices<T>();
        }
    }
}

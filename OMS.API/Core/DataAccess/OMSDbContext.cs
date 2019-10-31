using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Entities;
using OMS.API.Core.Entities;

namespace OMS.Api.Core.DataAccess
{
    public class OMSDbContext : DbContext
    {
        public OMSDbContext(DbContextOptions<OMSDbContext> options) : base(options) { }

        public DbSet<Category> Categories { get; set; }

        public DbSet<SubCategory> SubCategories { get; set; }

        public DbSet<Post> Posts { get; set; }
    }
}

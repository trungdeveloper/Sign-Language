using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using OMS.Api.Core.DataAccess;
using OMS.Api.Core.DataAccess.Repositories;
using OMS.Api.Core.Business.IoC;
using Newtonsoft.Json.Serialization;
using Newtonsoft.Json;
using AutoMapper;
using OMS.Api.Core.Business.Models;
using System;
using OMS.API.Core.Business.Services;
using OMS.Api.Core.Entities;
using OMS.Api.Core.Common.Extensions;
using OMS.API.Core.Entities;
using OMS.API.Core.Business.Models.SubCategories;
using OMS.API.Core.Business.Models.Posts;
using OMS.API.Core.Common.Constants;

namespace OMS.Api
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        [Obsolete]
        public void ConfigureServices(IServiceCollection services)
        {
            // Add service and create Policy with options
            services.AddCors(options =>
            {
                options.AddPolicy("CorsPolicy",
                  builder => builder.AllowAnyOrigin()
                    .AllowAnyMethod()
                    .AllowAnyHeader()
                    .AllowCredentials());
            });

            services.AddMvc().AddJsonOptions(opt =>
            {
                opt.SerializerSettings.ContractResolver = new CamelCasePropertyNamesContractResolver();
            })
              .AddJsonOptions(opt =>
              {
                  opt.SerializerSettings.DateTimeZoneHandling = DateTimeZoneHandling.Utc;
              });

            services.AddSingleton(Configuration);
            services.Configure<AppSettings>(Configuration.GetSection("AppSettings"));

            Mapper.Initialize(config =>
            {
                config.CreateMap<SubCategory, SubCategoryManageModel>().ReverseMap();
                config.CreateMap<Post, PostManageModel>().ReverseMap();
            });

            services.AddDbContext<OMSDbContext>(options =>
            {
                options.UseSqlServer(Configuration.GetConnectionString("DefaultConnectionString"));
            });

            //Register Repository
            services.AddScoped(typeof(IRepository<>), typeof(Repository<>));

            // Register service
            services.AddScoped<ICategoryService, CategoryService>();
            services.AddScoped<ISubCategoryService, SubCategoryService>();
            services.AddScoped<IPostService, PostService>();
            services.AddScoped<IDictionaryService, DictionaryService>();

            // Set Service Provider for IoC Helper
            IoCHelper.SetServiceProvider(services.BuildServiceProvider());

            services.AddAuthentication(Microsoft.AspNetCore.Server.IISIntegration.IISDefaults.AuthenticationScheme);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            // global policy - assign here or on each controller
            app.UseCors("CorsPolicy");

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseHsts();
            }

            app.UseHttpsRedirection();

            app.UseMvc();

            app.UseAuthentication();

            // Run Migration
            RunMigration(app);

            //Initial Data
            InitialData();
        }

        private void RunMigration(IApplicationBuilder app)
        {
            using (var scope = app.ApplicationServices.GetService<IServiceScopeFactory>().CreateScope())
            {
                scope.ServiceProvider.GetRequiredService<OMSDbContext>().Database.Migrate();
            }
        }

        private void InitialData()
        {
            var categoryRepository = IoCHelper.GetInstance<IRepository<Category>>();

            var categories = new[]
            {
                new Category {
                    Id = CategoryConstants.Basic,
                    Name = "Basic",
                    Description = "Including alphabet and numbers",
                    SubCategories = new[]
                    {
                        new SubCategory {
                            Name = "Alphabet",
                            Posts = new[]
                            {
                                new Post {
                                    Keyword = "A",
                                    Video = "https://www.handspeak.com/word/a/a-abc.mp4"
                                },
                                new Post {
                                    Keyword = "B",
                                    Video = "https://www.handspeak.com/word/b/b-abc.mp4"
                                },
                                new Post {
                                    Keyword = "C",
                                    Video = "https://www.handspeak.com/word/c/c-abc.mp4"
                                },
                                new Post {
                                    Keyword = "D",
                                    Video = "https://www.handspeak.com/word/d/d-abc.mp4"
                                },
                                new Post {
                                    Keyword = "E",
                                    Video = "https://www.handspeak.com/word/e/e-abc.mp4"
                                },
                                new Post {
                                    Keyword = "F",
                                    Video = "https://www.handspeak.com/word/f/f-abc.mp4"
                                },
                                new Post {
                                    Keyword = "G",
                                    Video = "https://www.handspeak.com/word/g/g-abc.mp4"
                                },
                                new Post {
                                    Keyword = "H",
                                    Video = "https://www.handspeak.com/word/h/h-abc.mp4"
                                },
                                new Post {
                                    Keyword = "I",
                                    Video = "https://www.handspeak.com/word/i/i-abc.mp4"
                                },
                                new Post {
                                    Keyword = "J",
                                    Video = "https://www.handspeak.com/word/j/j-abc.mp4"
                                },
                                new Post {
                                    Keyword = "K",
                                    Video = "https://www.handspeak.com/word/k/k-abc.mp4"
                                },
                                new Post {
                                    Keyword = "L",
                                    Video = "https://www.handspeak.com/word/l/l-abc.mp4"
                                },
                                new Post {
                                    Keyword = "M",
                                    Video = "https://www.handspeak.com/word/m/m-abc.mp4"
                                },
                                new Post {
                                    Keyword = "N",
                                    Video = "https://www.handspeak.com/word/n/n-abc.mp4"
                                },
                                new Post {
                                    Keyword = "O",
                                    Video = "https://www.handspeak.com/word/o/o-abc.mp4"
                                },
                                new Post {
                                    Keyword = "P",
                                    Video = "https://www.handspeak.com/word/p/p-abc.mp4"
                                },
                                new Post {
                                    Keyword = "Q",
                                    Video = "https://www.handspeak.com/word/q/q-abc.mp4"
                                },
                                new Post {
                                    Keyword = "R",
                                    Video = "https://www.handspeak.com/word/r/r-abc.mp4"
                                },
                                new Post {
                                    Keyword = "S",
                                    Video = "https://www.handspeak.com/word/s/s-abc.mp4"
                                },
                                new Post {
                                    Keyword = "T",
                                    Video = "https://www.handspeak.com/word/t/t-abc.mp4"
                                },
                                new Post {
                                    Keyword = "U",
                                    Video = "https://www.handspeak.com/word/u/u-abc.mp4"
                                },
                                new Post {
                                    Keyword = "V",
                                    Video = "https://www.handspeak.com/word/v/v-abc.mp4"
                                },
                                new Post {
                                    Keyword = "X",
                                    Video = "https://www.handspeak.com/word/x/x-abc.mp4"
                                },
                                new Post {
                                    Keyword = "Y",
                                    Video = "https://www.handspeak.com/word/y/y-abc.mp4"
                                },
                                new Post {
                                    Keyword = "Z",
                                    Video = "https://www.handspeak.com/word/z/z-abc.mp4"
                                }
                            }
                        },
                        new SubCategory {
                            Name = "Numbers"
                        }
                    }
                },
                new Category {
                    Id = CategoryConstants.Dictionary,
                    Name = "Dictionary",
                    Description = "Including more than 500 words frequently used listed by many different topic",
                    SubCategories = new[]
                    {
                        new SubCategory {
                            Name = "Family"
                        },
                        new SubCategory {
                            Name = "Meeting"
                        },
                        new SubCategory {
                            Name = "Education"
                        },
                        new SubCategory {
                            Name = "Sports"
                        },
                        new SubCategory {
                            Name = "Animals"
                        },
                        new SubCategory {
                            Name = "Body & Heath"
                        },
                        new SubCategory {
                            Name = "Fashion"
                        },
                        new SubCategory {
                            Name = "Traffic"
                        }
                    }
                },
                new Category {
                    Id = CategoryConstants.Tutorial,
                    Name = "Tutorial",
                    Description = "Including many sentences frequently used in daily life",
                    SubCategories = new[]
                    {
                        new SubCategory {
                            Name = "Hospital"
                        },
                        new SubCategory {
                            Name = "School"
                        },
                        new SubCategory {
                            Name = "Asking for Directions"
                        },
                        new SubCategory {
                            Name = "Restaurent"
                        },
                        new SubCategory {
                            Name = "Hotel"
                        },
                        new SubCategory {
                            Name = "Market"
                        },
                        new SubCategory {
                            Name = "Movie"
                        },
                        new SubCategory {
                            Name = "Music"
                        }
                    }
                }
            };

            categoryRepository.GetDbContext().Categories.AddIfNotExist(x => x.Name, categories);
            categoryRepository.GetDbContext().SaveChanges();
        }
    }
}
using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace OMS.Api.Core.DataAccess.Repositories
{
    public class Repository<T> : IRepository<T> where T : BaseEntity
    {
        protected OMSDbContext DataContext { get; set; }

        public OMSDbContext DbContext
        {
            get { return DataContext; }
        }

        public Repository(OMSDbContext entities)
        {
            DataContext = entities;
        }

        public OMSDbContext GetDbContext()
        {
            return DbContext;
        }

        // GET
        public virtual IQueryable<T> GetAll()
        {
            return DataContext.Set<T>().Where(i => !i.RecordDeleted).OrderBy(i => i.RecordOrder);
        }

        public async Task<T> GetByIdAsync(Guid? id)
        {
            return await DataContext.Set<T>().FirstOrDefaultAsync(i => i.Id == id);
        }

        public virtual IQueryable<T> Fetch(Expression<Func<T, bool>> expression)
        {
            return GetAll().Where(expression).OrderBy(i => i.RecordOrder);
        }

        public async Task<T> FetchFirstAsync(Expression<Func<T, bool>> expression)
        {
            return await GetAll().FirstOrDefaultAsync(expression);
        }

        // ADD
        public async Task<ResponseModel> InsertAsync(T entity)
        {
            var response = new ResponseModel();

            var dbSet = DataContext.Set<T>();
            entity.RecordActive = true;
            dbSet.Add(entity);
            await DataContext.SaveChangesAsync();
            response.Data = entity;
            response.Message = "Inserted success!";
            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        public async Task<ResponseModel> InsertAsync(IEnumerable<T> entities)
        {
            var response = new ResponseModel();

            var dbSet = DataContext.Set<T>();
            foreach (var entity in entities)
            {
                dbSet.Add(entity);
            }
            await DataContext.SaveChangesAsync();
            response.Message = "Inserted success!";
            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        // UPDATE
        public async Task<ResponseModel> UpdateAsync(T entity)
        {
            var response = new ResponseModel();
            entity.UpdatedOn = DateTime.UtcNow;

            await DataContext.SaveChangesAsync();
            response.Data = entity;
            response.Message = "Updated success!";
            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        // DELETE
        public async Task<ResponseModel> DeleteAsync(T entity)
        {
            var response = new ResponseModel();

            if (entity == null)
            {
                response.StatusCode = System.Net.HttpStatusCode.NotFound;
                return response;
            }

            entity.RecordActive = false;
            entity.RecordDeleted = true;

            await DataContext.SaveChangesAsync();

            response.Message = "Deleted success!";
            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        public async Task<ResponseModel> DeleteAsync(IEnumerable<T> entities)
        {
            var response = new ResponseModel();

            if (entities == null || !entities.Any())
            {
                response.StatusCode = System.Net.HttpStatusCode.NotFound;
                return response;
            }

            foreach (var entity in entities)
            {
                entity.RecordActive = false;
                entity.RecordDeleted = true;
            }

            await DataContext.SaveChangesAsync();

            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        public async Task<ResponseModel> DeleteAsync(IEnumerable<Guid> ids)
        {
            var response = new ResponseModel();

            if (ids == null || !ids.Any())
            {
                response.StatusCode = System.Net.HttpStatusCode.NotFound;
                return response;
            }
            var entities = Fetch(e => ids.Contains(e.Id));

            foreach (var entity in entities)
            {
                entity.RecordActive = false;
                entity.RecordDeleted = true;
            }

            await DataContext.SaveChangesAsync();
            response.StatusCode = System.Net.HttpStatusCode.OK;
            return response;
        }

        public async Task<ResponseModel> DeleteAsync(Guid id)
        {
            var response = new ResponseModel();
            var entity = await GetByIdAsync(id);
            entity.RecordActive = false;
            entity.RecordDeleted = true;
            await DataContext.SaveChangesAsync();
            response.Message = "Deleted success!";
            response.StatusCode = System.Net.HttpStatusCode.OK;
            response.Data = entity;
            return response;
        }

        public Task<ResponseModel> ExcuteSqlAsync(string sql)
        {
            throw new NotImplementedException();
        }

        public async Task<ResponseModel> SetRecordDeletedAsync(Guid id)
        {
            var entity = await GetByIdAsync(id);
            entity.RecordDeleted = true;
            return await UpdateAsync(entity);
        }

        public async Task<ResponseModel> SetRecordInactiveAsync(Guid id)
        {
            var entity = await GetByIdAsync(id);
            entity.RecordActive = false;
            return await UpdateAsync(entity);
        }
    }
}

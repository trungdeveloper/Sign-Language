using Microsoft.EntityFrameworkCore;
using OMS.Api.Core.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace OMS.Api.Core.Common.Extensions
{
    public static class MigrationExtensions
    {
        public static void AddIfNotExist<TEntity>(this DbSet<TEntity> set,
            Expression<Func<TEntity, object>> identifierExpression,
            params TEntity[] entities) where TEntity : BaseEntity
        {

            foreach (var entity in entities)
            {
                var v = identifierExpression.Compile()(entity);
                Expression<Func<TEntity, bool>> predicate = Expression.Lambda<Func<TEntity, bool>>(Expression.Equal(identifierExpression.Body, Expression.Constant(v)), identifierExpression.Parameters);

                var entry = set.FirstOrDefault(predicate);
                if (entry == null)
                {
                    entity.CreatedOn = DateTime.UtcNow;
                    entity.RecordActive = true;
                    entity.RecordDeleted = false;
                    set.Add(entity);
                }
            }
        }
        public static void AddIfNotExist<TEntity>(this DbSet<TEntity> set,
            List<Expression<Func<TEntity, object>>> identifierExpressions,
            params TEntity[] entities) where TEntity : BaseEntity
        {

            foreach (var entity in entities)
            {
                IQueryable<TEntity> entries = set;
                foreach (var identifierExpression in identifierExpressions)
                {
                    var v = identifierExpression.Compile()(entity);
                    Expression<Func<TEntity, bool>> predicate = Expression.Lambda<Func<TEntity, bool>>(Expression.Equal(identifierExpression.Body, Expression.Constant(v)), identifierExpression.Parameters);
                    entries = entries.Where(predicate);
                }

                if (entries != null)
                {
                    var entry = entries.FirstOrDefault();
                    if (entry == null)
                    {
                        entity.CreatedOn = DateTime.UtcNow;
                        entity.RecordActive = true;
                        entity.RecordDeleted = false;
                        set.Add(entity);
                    }
                }
            }
        }
    }
}

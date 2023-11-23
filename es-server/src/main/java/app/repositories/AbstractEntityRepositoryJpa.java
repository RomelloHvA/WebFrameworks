package app.repositories;

import app.exceptions.ResourceNotFound;
import app.models.Identifiable;
import app.models.Scooter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractEntityRepositoryJpa <E extends Identifiable> implements EntityRepository<E>{

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> entityClass;

    public AbstractEntityRepositoryJpa(Class<E> entityClass) {
        this.entityClass = entityClass;
        System.out.println("Created " + this.getClass().getName() + "<" + this.entityClass.getSimpleName() + ">");
    }

    @Override
    public List<E> findAll() {
        TypedQuery<E> query = this.entityManager.createQuery("select e from " + entityClass.getSimpleName() + " e", entityClass);
        return query.getResultList();
    }

    @Override
    public E findById(long id) throws ResourceNotFound {
        try {
            E entity = entityManager.find(entityClass, id);

            if (entity == null) {
                throw new ResourceNotFound( entityClass.getName() +" not found with id: " + id);
            }

            return entity;
        } catch (Exception e) {
            throw new ResourceNotFound("Error finding " + entityClass.getName() +" with id: " + id, e);
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws ResourceNotFound {
        try {
            if (entity.getId() == 0) {
                entityManager.persist(entity);
                return entity;
            } else {
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            throw new ResourceNotFound("Error saving " + entityClass.getName() +" with id: " + entity.getId(), e);
        }
    }

    @Override
    public boolean deleteById(long id) throws ResourceNotFound {
        E entity = entityManager.find(entityClass, id);
        if (entity == null) {
            throw new ResourceNotFound(entityClass.getName() + " not found with id: " + id);
        }
        try {
            entityManager.remove(entity);
            return true;
        } catch (Exception e) {
            throw new ResourceNotFound("Error deleting " + entityClass.getName() +" with id: " + id, e);
        }
    }
}

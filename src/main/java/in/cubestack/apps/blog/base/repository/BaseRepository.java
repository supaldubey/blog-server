package in.cubestack.apps.blog.base.repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class BaseRepository<T> {

    private final EntityManager entityManager;
    private final Class<T> clazz;

    public BaseRepository(EntityManager entityManager, Class<T> clazz) {
        this.entityManager = entityManager;
        this.clazz = clazz;
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    public T findOne(Long id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public void update(T entity) {
        entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(Long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

}

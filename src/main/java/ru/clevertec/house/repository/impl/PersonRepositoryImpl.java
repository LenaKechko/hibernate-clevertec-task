package ru.clevertec.house.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.repository.IRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonRepositoryImpl implements IRepository<Person> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class)
                .setFirstResult(0)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public Optional<Person> findByUuid(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Person p WHERE p.uuid = :uuidParam", Person.class)
                .setParameter("uuidParam", uuid)
                .uniqueResultOptional();
    }

    @Override
    public UUID save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
        return person.getUuid();
    }

    @Override
    public void update(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(person);
    }

    @Override
    public void delete(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("DELETE Person WHERE uuid = :uuidParam")
                .setParameter("uuidParam", uuid)
                .executeUpdate();
    }
}

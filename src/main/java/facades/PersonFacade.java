/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * @author SJUBE
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

//    @Override
    public PersonDTO getPersonById(Long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            if (p == null) {
                throw new PersonNotFoundException(String.format("Person with the id: (%d) not found :/ ", id));
            } else {
                return new PersonDTO(p);
            }
        } finally {
            em.close();

        }
    }

    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        TypedQuery tqh = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = tqh.getResultList();
        List<PersonDTO> persDTO = new ArrayList();
        persons.forEach((Person person) -> {
            persDTO.add(new PersonDTO(person));
        });
        return persDTO;
    }

    public PersonDTO addPerson(String fname, String lname, String phone) {

        Person person = new Person(fname, lname, phone, new Date());
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(person);
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(person);
    }

    public PersonDTO deletePerson(Long id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new PersonNotFoundException(String.format("Person with id: (%d) not found", id));
        } else {

            try {
                em.getTransaction().begin();
                em.remove(p);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new PersonDTO(p);
    }

    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();

        Person pierce = em.find(Person.class, p.getId());
        if (pierce == null) {
            throw new PersonNotFoundException(String.format("Person not found, so they coulden't be edited", pierce));
        } else {
            try {
                em.getTransaction().begin();
                pierce.setFname(p.getfName());
                pierce.setLname(p.getlName());
                pierce.setPhone(p.getPhone());
                pierce.setLastEdited();
                em.getTransaction().commit();

            } finally {
                em.close();

            }
        }

        return new PersonDTO(pierce);
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.persist(new Person("Mariylyn", "Manson", "12243647", new Date()));
            em.persist(new Person("Peter", "Parker", "81680085", new Date()));
            em.persist(new Person("Mary", "Jane", "42424242", new Date()));

            em.getTransaction().commit();
        } finally {
            em.close();

        }
    }

}

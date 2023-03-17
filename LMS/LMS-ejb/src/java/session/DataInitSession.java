/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.Member;
import entity.Staff;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author junwe
 */
@Singleton
@LocalBean
@Startup
public class DataInitSession {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private StaffSessionLocal staffSessionLocal;

    public void persist(Object object) {
        em.persist(object);
    }

    @PostConstruct
    public void postConstruct() {
        if (em.find(Staff.class, 1l) == null) {
            Staff staffA = new Staff();
            em.persist(staffA);
            staffA.setFirstName("Eric");
            staffA.setLastName("Some");
            staffA.setUserName("eric");
            staffA.setPassword("password");
            Staff staffB = new Staff();
            em.persist(staffB);
            staffB.setFirstName("Sarah");
            staffB.setLastName("Brightman");
            staffB.setUserName("sarah");
            staffB.setPassword("password");
        }

        if (em.find(Book.class, 1l) == null) {
            Book bookA = new Book();
            bookA.setTitle("Anna Karenina");
            bookA.setAuthor("Leo Tolstoy");
            bookA.setIsbn("0451528611");
            bookA.setOnLoan(false);
            em.persist(bookA);
            Book bookB = new Book();
            bookB.setTitle("Madame Bovary");
            bookB.setAuthor("Gustave Flaubert");
            bookB.setIsbn("979-8649042031");
            bookB.setOnLoan(false);
            em.persist(bookB);
            Book bookC = new Book();
            bookC.setTitle("Hamlet");
            bookC.setAuthor("William Shakespeare");
            bookC.setIsbn("1980625026");
            bookC.setOnLoan(false);
            em.persist(bookC);
            Book bookD = new Book();
            bookD.setTitle("The Hobbit");
            bookD.setAuthor("J R R Tolkien");
            bookD.setIsbn("9780007458424");
            bookD.setOnLoan(false);
            em.persist(bookD);
            Book bookE = new Book();
            bookE.setTitle("Great Expectations");
            bookE.setAuthor("Charles Dickens");
            bookE.setIsbn("1521853592");
            em.persist(bookE);
            Book bookF = new Book();
            bookF.setTitle("Pride and Prejudice");
            bookF.setAuthor("Jane Austen");
            bookF.setIsbn("979-8653642272");
            bookF.setOnLoan(false);
            em.persist(bookF);
            Book bookG = new Book();
            bookG.setTitle("Wuthering Heights");
            bookG.setAuthor("Emily Brontë");
            bookG.setIsbn("3961300224");
            bookG.setOnLoan(false);
            em.persist(bookG);
        }

        if (em.find(Member.class, 1l) == null) {
            Member memberA = new Member();
            em.persist(memberA);
            memberA.setFirstName("Tony");
            memberA.setLastName("Shade");
            memberA.setGender('M');
            memberA.setAge(31);
            memberA.setIdentityNo("S8900678A");
            memberA.setPhone("83722773");
            memberA.setAddress("13 Jurong East, Ave 3");
            Member memberB = new Member();
            em.persist(memberB);
            memberB.setFirstName("Dewi");
            memberB.setLastName("Tan");
            memberB.setGender('F');
            memberB.setAge(35);
            memberB.setIdentityNo("S8581028X");
            memberB.setPhone("94602711");
            memberB.setAddress("15 Computing Dr");

        }
    }
}

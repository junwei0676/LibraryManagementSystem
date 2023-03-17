/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import entity.Staff;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sun.net.www.content.audio.x_aiff;
import util.exception.BookAlreadyExistsException;
import util.exception.BookNotFoundException;
import util.exception.BookReturnedException;
import util.exception.BookUnavailableException;
import util.exception.FineNotPaidException;
import util.exception.InvalidLoginException;
import util.exception.LendingNotFoundException;
import util.exception.MemberAlreadyExistsException;
import util.exception.MemberNotFoundException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author junwe
 */
@Stateless
public class StaffSession implements StaffSessionLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Staff login(String username, String password) throws InvalidLoginException, StaffNotFoundException {
        try {
            Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
            query.setParameter("inUsername", username);
            Staff staff = (Staff) query.getSingleResult();
            if (staff.getPassword().equals(password)) {
                return staff;
            } else {
                throw new InvalidLoginException("Incorrect password!");
            }
        } catch (NoResultException ex) {
            throw new StaffNotFoundException("No such staff!");
        }
    }

    @Override
    public Long registerMember(String firstName, String lastName, Character gender, Integer age, String identity, String phone, String address) throws MemberAlreadyExistsException {
        Query q = em.createQuery("SELECT m FROM Member m WHERE m.identityNo = :inIdentity");
        q.setParameter("inIdentity", identity);
        try {
            Member m = (Member) q.getSingleResult();
            throw new MemberAlreadyExistsException("Member has already been registered!");
        } catch (NoResultException ex) {
            Member member = new Member(firstName, lastName, gender, age, identity, phone, address);
            em.persist(member);
            em.flush();
            return member.getMemberId();
        }
    }

    @Override
    public List<Member> searchMembersByIdentity(String identity) {
        Query q;
        if (identity != null) {
            q = em.createQuery("SELECT m FROM Member m WHERE "
                    + "LOWER(m.identity) LIKE :identity");
            q.setParameter("identity", "%" + identity.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT m FROM Member m");
        }

        return q.getResultList();
    }

    @Override
    public List<Book> searchBooks(String name) {
        Query q;
        if (name != null) {
            q = em.createQuery("SELECT b FROM Book b WHERE "
                    + "LOWER(b.name) LIKE :name");
            q.setParameter("name", "%" + name.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT b FROM Book b");
        }

        return q.getResultList();
    }

    @Override
    public void addBook(String title, String author, String isbn) throws BookAlreadyExistsException {
        try {
            Query q = em.createQuery("SELECT b FROM Book b WHERE b.title = :inTitle AND b.author = :inAuthor AND b.isbn = :isbn");
            q.setParameter("inTitle", title);
            q.setParameter("inAuthor", author);
            q.setParameter("isbn", isbn);
            Book result = (Book) q.getSingleResult();
            throw new BookAlreadyExistsException("Book already exists in system!");

        } catch (NoResultException ex) {
            Book newBook = new Book();
            newBook.setAuthor(author);
            newBook.setIsbn(isbn);
            newBook.setTitle(title);
            newBook.setOnLoan(false);
            em.persist(newBook);
        }

    }

    @Override
    public Long lendBook(Long bookId, Long memberId) throws BookNotFoundException, MemberNotFoundException, BookUnavailableException {
        Book book = em.find(Book.class, bookId);
        if (book == null) {
            throw new BookNotFoundException("No such book!");
        }
        Member member = em.find(Member.class, memberId);
        if (member == null) {
            throw new MemberNotFoundException("No such member!");
        }
        List<LendAndReturn> lendRecords = book.getLending();
        LendAndReturn latestRecord = null;
        if (lendRecords.size() != 0) {
            latestRecord = lendRecords.get(lendRecords.size() - 1);
        }

        if (latestRecord != null) {
            if (latestRecord.getReturnDate() != null) {
                LendAndReturn newRecord = new LendAndReturn(LocalDate.now(), member, book);
                newRecord.setFineAmount(0);
                em.persist(newRecord);
                book.getLending().add(newRecord);
                book.setOnLoan(true);
                member.getLending().add(newRecord);
                em.flush();
                return newRecord.getLendId();
            } else {
                throw new BookUnavailableException("Book is already on loan.");
            }
        } else {
            LendAndReturn newRecord = new LendAndReturn(LocalDate.now(), member, book);
            newRecord.setFineAmount(0);
            em.persist(newRecord);
            book.getLending().add(newRecord);
            member.getLending().add(newRecord);
            book.setOnLoan(true);
            em.flush();
            return newRecord.getLendId();
        }

    }

    @Override
    public double viewFine(Long lendId) throws LendingNotFoundException {
        double fineAmount = 0;

        LendAndReturn loan = em.find(LendAndReturn.class, lendId);
        if (loan != null) {
            if (loan.getReturnDate() == null) {
                LocalDate returnDate = LocalDate.now();
                LocalDate loanDate = loan.getLendDate();
                Long daysOverdue = Duration.between(loanDate.plusDays(14l).atStartOfDay(), returnDate.atStartOfDay()).toDays();
                if (daysOverdue > 0) {
                    fineAmount = daysOverdue * 0.5;
                }
                loan.setFineAmount(fineAmount);
                return fineAmount;
            } else {
                return 0;
            }
        } else {
            throw new LendingNotFoundException("There is no such loan.");
        }

    }

    @Override
    public void returnBook(Long lendId, double fine) throws FineNotPaidException, LendingNotFoundException, BookReturnedException {
        LendAndReturn loan = em.find(LendAndReturn.class, lendId);
        if (loan != null) {
            if (loan.getReturnDate() != null) {
                throw new BookReturnedException("Book has already been returned");
            }

            double dueFines = viewFine(lendId);
            if (fine == dueFines) {
                loan.setFineAmount(0);
                loan.setReturnDate(LocalDate.now());
                loan.getBook().setOnLoan(false);
            } else {
                loan.setFineAmount(dueFines);
                throw new FineNotPaidException("Please pay correct fine amount of ($" + dueFines + ") before returning");
            }

        } else {
            throw new LendingNotFoundException("There is no such loan.");
        }
    }

    @Override
    public List<LendAndReturn> getLoans() {
        return em.createQuery("SELECT l FROM LendAndReturn l").getResultList();
    }

    @Override
    public int numberOngoingLoans() {
        try {
            Query q = em.createQuery("SELECT l FROM LendAndReturn l WHERE l.returnDate  = NULL");
            List<LendAndReturn> ongoingLoans = q.getResultList();
            return ongoingLoans.size();
        } catch (NoResultException ex) {
            return 0;
        }
    }

    @Override
    public int numberOfOverdueLoans() {
        try {
            List<LendAndReturn> loans = getLoans();
            for (LendAndReturn loan : loans) {
                try {
                    if (loan.getReturnDate() == null) {
                        viewFine(loan.getLendId());
                    }
                } catch (LendingNotFoundException ex) {
                }
            }
            Query q = em.createQuery("SELECT l FROM LendAndReturn l WHERE l.fineAmount > :inFineAmount");
            q.setParameter("inFineAmount", 0);
            List<LendAndReturn> overdueLoans = q.getResultList();
            return overdueLoans.size();
        } catch (NoResultException ex) {
            return 0;
        }
    }

    @Override
    public String sumOfFines() {
        try {
            List<LendAndReturn> loans = getLoans();
            for (LendAndReturn loan : loans) {
                try {
                    if (loan.getReturnDate() == null) {
                        viewFine(loan.getLendId());
                    }
                } catch (LendingNotFoundException ex) {
                }
            }
            Query q = em.createQuery("SELECT l FROM LendAndReturn l WHERE l.fineAmount > :inFineAmount");
            q.setParameter("inFineAmount", 0);
            List<LendAndReturn> overdueLoans = q.getResultList();
            double sum = 0;
            for (LendAndReturn l : overdueLoans) {
                sum += l.getFineAmount();
            }
            DecimalFormat df = new DecimalFormat("0.00");
            return "$" + df.format(sum);
        } catch (NoResultException ex) {
            return "$0.00";
        }
    }
}

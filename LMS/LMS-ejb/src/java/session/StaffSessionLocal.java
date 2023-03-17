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
import java.util.List;
import javax.ejb.Local;
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
@Local
public interface StaffSessionLocal {

    public Staff login(String username, String password) throws InvalidLoginException, StaffNotFoundException;

    public Long registerMember(String firstName, String lastName, Character gender, Integer age, String identity, String phone, String address) throws MemberAlreadyExistsException;

    public Long lendBook(Long bookId, Long memberId) throws BookNotFoundException, MemberNotFoundException, BookUnavailableException;

    public double viewFine(Long lendId) throws LendingNotFoundException ;

    public void returnBook(Long lendId, double fine) throws FineNotPaidException, LendingNotFoundException, BookReturnedException;

    public List<Member> searchMembersByIdentity(String identity);

    public List<Book> searchBooks(String name);

    public List<LendAndReturn> getLoans();

    public void addBook(String title, String author, String isbn) throws BookAlreadyExistsException;

    public int numberOngoingLoans();

    public int numberOfOverdueLoans();

    public String sumOfFines();
    
}

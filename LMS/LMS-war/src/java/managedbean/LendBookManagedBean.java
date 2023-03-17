/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import entity.Member;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import session.StaffSessionLocal;
import util.exception.BookNotFoundException;
import util.exception.BookUnavailableException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author junwe
 */
@Named(value = "lendBookManagedBean")
@ViewScoped
public class LendBookManagedBean implements Serializable {

    @EJB
    private StaffSessionLocal staffSession;

    private Long bookId;
    private Long memberId;
    private List<LendAndReturn> loans;
    private List<Book> books;
    private List<Book> filteredBooks;
    private Integer bookSize;
    private Integer ongoingLoans;
    /**
     * Creates a new instance of LendBookManagedBean
     */
    public LendBookManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        books = staffSession.searchBooks(null);
        bookSize = books.size();
        loans = staffSession.getLoans();
        ongoingLoans = staffSession.numberOngoingLoans();
    }
    
    public void lendBook(){
        try {
            staffSession.lendBook(bookId, memberId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful Loan", "Successfully loaned book. Return within 14 days to avoid fine."));
        } catch (BookNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Book Not Found", ex.getMessage()));
        } catch (MemberNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Member Not Found", ex.getMessage()));
        } catch(BookUnavailableException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Book Unavailable", ex.getMessage()));
        }
    }
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        Book book = (Book) value;
        return book.getAuthor().toLowerCase().contains(filterText)
                || book.getIsbn().toLowerCase().contains(filterText)
                || book.getTitle().toLowerCase().contains(filterText)
                || book.getIsbn().toLowerCase().contains(filterText);
    }
    
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<LendAndReturn> getLoans() {
        return loans;
    }

    public void setLoans(List<LendAndReturn> loans) {
        this.loans = loans;
    }

    public Integer getBookSize() {
        return bookSize;
    }

    public void setBookSize(Integer bookSize) {
        this.bookSize = bookSize;
    }

    public Integer getOngoingLoans() {
        return ongoingLoans;
    }

    public void setOngoingLoans(Integer ongoingLoans) {
        this.ongoingLoans = ongoingLoans;
    }

    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }

    public void setFilteredBooks(List<Book> filteredBooks) {
        this.filteredBooks = filteredBooks;
    }
    
    
    
}

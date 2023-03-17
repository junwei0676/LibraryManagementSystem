/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author junwe
 */
@Entity
public class LendAndReturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendId;
    private Long memberId;
    private Long bookId;
    private LocalDate lendDate;
    private LocalDate returnDate;
    private double fineAmount;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Book book;

    public LendAndReturn() {
    }

    public LendAndReturn(LocalDate lendDate, Member member, Book book) {
        this.lendDate = lendDate;
        this.member = member;
        this.memberId = member.getMemberId();
        this.book = book;
        this.bookId = book.getBookId();
        this.returnDate = null;
        this.fineAmount = 0;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    public Long getMemberId() {
        return this.member.getMemberId();
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBookId() {
        return this.book.getBookId();
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendId != null ? lendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendId fields are not set
        if (!(object instanceof LendAndReturn)) {
            return false;
        }
        LendAndReturn other = (LendAndReturn) object;
        if ((this.lendId == null && other.lendId != null) || (this.lendId != null && !this.lendId.equals(other.lendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LendAndReturn[ id=" + lendId + " ]";
    }
    
}

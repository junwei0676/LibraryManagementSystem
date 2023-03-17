/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import session.StaffSessionLocal;
import util.exception.BookReturnedException;
import util.exception.FineNotPaidException;
import util.exception.LendingNotFoundException;

/**
 *
 * @author junwe
 */
@Named(value = "viewFineManagedBean")
@ViewScoped
public class ViewFineManagedBean implements Serializable {

    @EJB
    private StaffSessionLocal staffSession;

    private Long lendId;
    private double fine;
    private String sumOfFines;
    private int numberOfOverdueLoans;

    /**
     * Creates a new instance of ViewFineManagedBean
     */
    public ViewFineManagedBean() {
    }
    @PostConstruct
    public void init() {
        numberOfOverdueLoans = staffSession.numberOfOverdueLoans();
        sumOfFines = staffSession.sumOfFines();
    }

    public void viewFine() {
        try {
            fine = staffSession.viewFine(lendId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fine Amount", getFineString()));
        } catch (LendingNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No such loan", e.getMessage()));
        }

    }

    public void returnBook() {
        try {
            staffSession.returnBook(lendId, fine);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Book returned"));
        } catch (FineNotPaidException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Pay Correct Fine Amount", ex.getMessage()));
        } catch (LendingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No such loan", ex.getMessage()));
        } catch (BookReturnedException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Book has been returned", ex.getMessage()));
        }
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }
    
    public double getFine(){
        return fine;
    }

    public String getFineString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "$" + df.format(fine);
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public String getSumOfFines() {
        return sumOfFines;
    }

    public void setSumOfFines(String sumOfFines) {
        this.sumOfFines = sumOfFines;
    }

    public int getNumberOfOverdueLoans() {
        return numberOfOverdueLoans;
    }

    public void setNumberOfOverdueLoans(int numberOfOverdueLoans) {
        this.numberOfOverdueLoans = numberOfOverdueLoans;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Member;
import java.io.Serializable;
import static java.lang.Integer.getInteger;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.StaffSessionLocal;
import javax.faces.view.ViewScoped;
import org.primefaces.util.LangUtils;
import util.exception.BookAlreadyExistsException;
import util.exception.MemberAlreadyExistsException;

/**
 *
 * @author junwe
 */
@Named(value = "staffManagedBean")
@ViewScoped
public class StaffManagedBean implements Serializable {

    @EJB
    private StaffSessionLocal staffSession;

    /**
     * Creates a new instance of StaffManagedBean
     */
    
    private String firstName;
    private String lastName;
    private Character gender;
    private Integer age;
    private String identity;
    private String phone;
    private String address;
    private List<Member> members;
    private List<Member> filteredMembers;
    private Integer memberSize;
    private String searchString;
    private String title;
    private String isbn;
    private String author;
    
    
    public StaffManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        
        if (searchString == null || searchString.equals("")) {
            members = staffSession.searchMembersByIdentity(null);
            memberSize = members.size();
        } else {
            members = staffSession.searchMembersByIdentity(searchString);
            memberSize = members.size();
        }
    }
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        Member member = (Member) value;
        return member.getFirstName().toLowerCase().contains(filterText)
                || member.getLastName().toLowerCase().contains(filterText)
                || member.getAddress().toLowerCase().contains(filterText)
                || member.getIdentityNo().toLowerCase().contains(filterText)
                || member.getPhone().toLowerCase().contains(filterText);
    }
    
    public void handleSearch(){
        init();
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void addMember(){
        try{
            staffSession.registerMember(firstName, lastName, gender, age, identity, phone, address);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Member Registered", "Member has been registered successfully!"));
        } catch (MemberAlreadyExistsException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Member Exists", ex.getMessage()));
        }
    }
    
    public void addBook(){
        try{
            staffSession.addBook(title, author, isbn);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Book Added", "Book had been added successfully!"));
        }catch(BookAlreadyExistsException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Book Exists", ex.getMessage()));
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getMemberSize() {
        return memberSize;
    }

    public void setMemberSize(Integer memberSize) {
        this.memberSize = memberSize;
    }

    public List<Member> getFilteredMembers() {
        return filteredMembers;
    }

    public void setFilteredMembers(List<Member> filteredMembers) {
        this.filteredMembers = filteredMembers;
    }
    
    
    
}

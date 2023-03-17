/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Staff;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.StaffSessionLocal;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

/**
 *
 * @author junwe
 */
@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private StaffSessionLocal staffSession;

    private String username = null;
    private String password = null;
    private Long userId = -1l;

    /**
     * Creates a new instance of AuthenticationManagedBean
     */
    public AuthenticationManagedBean() {
    }

    public String login() {
        try {
            Staff loginStaff = staffSession.login(username, password);
            userId = loginStaff.getStaffId();
        } catch (StaffNotFoundException e) {
            userId = -1l;
        } catch (InvalidLoginException ex) {
            userId = -1l;
        }
        if (userId != -1l) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Log In Successful"));
            return "/secret/homePage.xhtml?faces-redirect=true";
        } else {
            username = null;
            password = null;
            userId = -1l;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to log in"));
            return "login.xhtml";
        }
    }
    public String logout() {
        username = null;
        password = null;
        userId = -1l;

        return "/login.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    
}

package com.mmabas77.web.domain.frontend;

import java.io.Serializable;

public class FeedbackPojo implements Serializable {

    /* Serial Version UID For Serializable */
    private static final long serialVersionUID = 1L;

    String email;
    String fname;
    String lname;
    String feedback;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FeedbackPojo{");
        sb.append("email='").append(email).append('\'');
        sb.append(", fname='").append(fname).append('\'');
        sb.append(", lname='").append(lname).append('\'');
        sb.append(", feedback='").append(feedback).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

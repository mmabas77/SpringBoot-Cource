package com.mmabas77.backend.persistence.domain.backend;

import com.mmabas77.backend.persistence.converters.LocalDateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken implements Serializable {

    /* Serial Version UID For Serializable */
    private static final long serialVersionUID = 1L;

    /* The Application Logger*/
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

    private static final int DEFAULT_EXPIRE_LENGTH = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime expireDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(@NotNull LocalDateTime creationDateTime,
                              @NotNull String token,
                              @NotNull User user,
                              @NotNull int expireInMinutes) {
        if (expireInMinutes == 0) {
            LOG.warn("Expire In Minutes Is Null , Assign {} As Default",
                    DEFAULT_EXPIRE_LENGTH);
            expireInMinutes = DEFAULT_EXPIRE_LENGTH;
        }

        this.token = token;
        this.user = user;
        this.expireDate = creationDateTime.plusMinutes(expireInMinutes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

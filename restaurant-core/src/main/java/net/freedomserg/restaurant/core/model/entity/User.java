package net.freedomserg.restaurant.core.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "ssoId", unique=true, nullable=false)
    private String ssoId;

    @Column(name = "pass", nullable=false)
    private String password;

    @Column(name = "state", nullable=false)
    private String state=UserState.ACTIVE.getState();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profile_to_user",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "profile_id")})
    private Set<UserProfile> userProfiles = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != user.id) return false;
        return ssoId.equals(user.ssoId);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + ssoId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", ssoId='" + ssoId + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                ", userProfiles=" + userProfiles +
                '}';
    }
}

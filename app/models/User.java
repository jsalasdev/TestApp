package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends Model {

    @Id
    @Column(name = "idusers")
    public Integer id;
    public String name;
    public String surname;
    public String email;
    public String password;
    public Double latitude;
    public Double longitude;
    @Column(name = "country")
    public String countryCode;

    public static Finder<Integer, User> finder = new Finder<>(User.class);

}

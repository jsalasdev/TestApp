package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LandingUser extends Model {

    @Id
    public Integer id;
    public String name;
    public Double latitude;
    public Double longitude;
    public String language;
    public Integer id_sector;
    public Integer id_subsector;
    public Integer id_profession;
    public String email;
    public String type_user;
    public String telephone;
    public String surname;

    public static Finder<Integer, LandingUser> find = new Finder<Integer, LandingUser>(LandingUser.class);

}

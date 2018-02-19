package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profession extends Model {

    @Id
    public Integer id;
    public String name;

    public static Finder<Integer, Profession> find = new Finder<Integer, Profession>(Profession.class);

}

package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sector extends Model{

    @Id
    public Integer id;
    public String name;
    public String url;
    public Integer id_sector;

    public static Finder<Integer, Sector> find = new Finder<Integer, Sector>(Sector.class);

}

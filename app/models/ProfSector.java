package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prof_sector")
public class ProfSector extends Model {

    @Id
    public Integer idProfession;

    public Integer idSector;

    static public Finder<Integer, ProfSector> finder = new Finder<>(ProfSector.class);

}

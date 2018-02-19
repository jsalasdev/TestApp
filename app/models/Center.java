package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "centers")
public class Center extends Model {

    @Id
    @Column(name = "idcompanies")
    public Integer id;
    public String name;
    @Column(name = "centerNumber")
    public String center_number;
    public Double latitude;
    public Double longitude;
    @Column(name = "country")
    public String countryCode;

    public static Finder<Integer, Center> finder = new Finder<>(Center.class);

}
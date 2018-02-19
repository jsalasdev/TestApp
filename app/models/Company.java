package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends Model {

    @Id
    @Column(name = "idcompanies")
    public Integer id;
    public String name;
    @Column(name = "companyNumber")
    public String company_number;
    public Double latitude;
    public Double longitude;
    @Column(name = "country")
    public String countryCode;

    public static Finder<Integer, Company> finder = new Finder<>(Company.class);

}

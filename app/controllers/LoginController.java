package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.ebean.Expr;
import io.ebean.annotation.Transactional;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Validations;
import views.html.index3;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class LoginController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result index() {
        return ok(index3.render());
    }

    public Result getProfession() {
        List<Profession> professionList = Profession.find.all();
        return ok(Json.toJson(professionList));
    }

    public Result getAllUsers() {
        return ok(Json.toJson(LandingUser.find.all()));
    }

    @Transactional(readOnly = true)
    public Result getUser(Integer id) {
        LandingUser user = LandingUser.find.byId(id);
        if (user == null) {
            return badRequest(response(false, null, "Not found user"));
        }
        List<LandingUser> users = new ArrayList<>();
        users.add(user);
        return ok(response(true, users, ""));
    }

    @Transactional(readOnly = true)
    public Result getSectors() {
        List<Sector> list = Sector.find.query().where()
                .ilike("id_sector", "0").findList();

        if (list != null && list.size() < 1) {
            return badRequest(response(false, null, "Not found sectors"));
        } else {
            return ok(response(true, list, ""));
        }
    }

    @Transactional
    public Result saveSector() {
        Form<Sector> form = formFactory.form(Sector.class);
        Form<Sector> sector = form.bindFromRequest();
        Sector sectorModel = sector.get();
        sectorModel.id_sector = 0;
        ObjectNode result = Json.newObject();
        List<String> errors = validateSector(sectorModel);
        if (sector.hasErrors() || errors.size() > 0) {
            List<Object> listErrors = new ArrayList<>();
            result.put("status", "KO");
            for (int i = 0; i < errors.size(); i++) {
                listErrors.add(errors.get(i));

            }
            result.put("errors: ", Json.toJson(listErrors));
            result.put("result", "");
            return badRequest(result);
        } else {
            List<Sector> sectorModelList = new ArrayList<>();
            sectorModelList.add(sectorModel);
            sectorModel.save();
            return ok(response(true, sectorModelList, ""));
        }
    }

    @Transactional
    public Result getSubsectors(String id) {
        if (!id.equalsIgnoreCase("0")) {
            List<Sector> list = Sector.find.query().where(Expr.like("id_sector", id))
                    .findList();
            if (list != null && list.size() < 1) {
                return badRequest(response(false, null, "Not found subsectors"));
            } else {
                return ok(response(true, list, ""));
            }
        } else {
            return badRequest(response(false, null, "Not found subsectors"));
        }
    }

    public Result getWorkers(Double latitude, Double longitude) {
        Double[] address = getAddress(latitude, longitude);

        List<Company> listCompanies = Company.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<Center> listCenters = Center.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<User> listUsers = User.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        ObjectNode result = Json.newObject();

        if ((listUsers != null && listUsers.size() > 0) || (listCompanies != null && listCompanies.size() > 0) || (listCenters != null && listCenters.size() > 0)) {
            result.put("status", "OK");
            result.put("error", "");

            ObjectNode node = Json.newObject();

            if (listCompanies != null && listCompanies.size() > 0) {
                node.put("companies", Json.toJson(listCompanies));
            }

            if (listUsers != null && listUsers.size() > 0) {
                node.put("users", Json.toJson(listUsers));
            }

            if (listCenters != null && listCenters.size() > 0) {
                node.put("centers", Json.toJson(listCenters));
            }

            if (node.size() > 0) {
                result.put("result", node);
            }

            return ok(result);
        } else {
            return badRequest(response(false, null, "Not objects found"));
        }
    }

    @Transactional
    public Result getCompanies(Double latitude, Double longitude) {
        Double[] address = getAddress(latitude, longitude);

        List<User> listUsers = User.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<Center> listCenters = Center.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();


        ObjectNode result = Json.newObject();

        if ((listUsers != null && listUsers.size() > 0) || (listCenters != null && listCenters.size() > 0)) {
            result.put("status", "OK");
            result.put("error", "");

            ObjectNode node = Json.newObject();

            if (listUsers != null && listUsers.size() > 0) {
                node.put("users", Json.toJson(listUsers));
            }

            if (listCenters != null && listCenters.size() > 0) {
                node.put("centers", Json.toJson(listCenters));
            }

            if (node.size() > 0) {
                result.put("result", node);
            }

            return ok(result);
        } else {
            return badRequest(response(false, null, "Not objects found"));
        }
    }

    @Transactional
    public Result getCenters(Double latitude, Double longitude) {
        Double[] address = getAddress(latitude, longitude);

        List<Company> listCompanies = Company.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<User> listUser = User.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        ObjectNode result = Json.newObject();

        if ((listCompanies != null && listCompanies.size() > 0) || (listUser != null && listUser.size() > 0)) {
            result.put("status", "OK");
            result.put("error", "");

            ObjectNode node = Json.newObject();

            if (listCompanies != null && listCompanies.size() > 0) {
                node.put("companies", Json.toJson(listCompanies));
            }

            if (listUser != null && listUser.size() > 0) {
                node.put("users", Json.toJson(listUser));
            }

            if (node.size() > 0) {
                result.put("result", node);
            }

            return ok(result);
        } else {
            return badRequest(response(false, null, "Not objects found"));
        }
    }

    @Transactional
    public Result getProfessions(String idSector) {
        String idSubsector = formFactory.form().bindFromRequest().get("id_subsector");
        List<Profession> list;
        ObjectNode result = Json.newObject();

        String finalId;

        if (idSubsector != null && !idSubsector.equalsIgnoreCase("0")) {
            finalId = idSubsector;
        } else {
            finalId = idSector;
        }

        list = getProfessionsQuery(finalId);

        if (list != null && list.size() > 0) {
            return ok(response(true, list, ""));
        } else {
            return badRequest(response(false, null, "Not professions"));
        }
    }


    public List<Profession> getProfessionsQuery(String id) {
        List<ProfSector> list = ProfSector.finder.query().where(Expr.like("id_sector", id))
                .findList();
        List<Profession> professions = new ArrayList<>();

        if (list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                Profession profession = Profession.find.byId(list.get(i).idProfession);
                professions.add(profession);
            }
        }
        return professions;
    }

    @Transactional
    public Result getTotalUsers(String countryCode) {

        ObjectNode result = Json.newObject();

        List<Center> listCenter = Center.finder.query().where(Expr.like("country", countryCode))
                .findList();

        List<Company> listCompany = Company.finder.query().where(Expr.like("country", countryCode))
                .findList();

        List<User> listUser = User.finder.query().where(Expr.like("country", countryCode))
                .findList();

        if (listCenter.size() > 0 || listCompany.size() > 0 || listUser.size() > 0) {

            List<Object> list = new ArrayList<>();
            ObjectNode prueba = Json.newObject();

            result.put("status", "OK");
            result.put("error", "");

            if (listCenter != null && listCenter.size() > 0) {
                prueba.put("centers", Json.toJson(listCenter.size()));
            }
            if (listCompany != null && listCompany.size() > 0) {
                prueba.put("companies", Json.toJson(listCompany.size()));
            }
            if (listUser != null && listUser.size() > 0) {
                prueba.put("users", Json.toJson(listUser.size()));
            }

            result.put("result", prueba);


            return ok(result);
        } else {
            result.put("status", "KO");
            result.put("error", "Not users in this country.");
            result.put("result", Json.toJson(new ArrayList<String>()));
            return badRequest(result);
        }
    }

    @Transactional
    public Result saveUser() {
        Form<LandingUser> form = formFactory.form(LandingUser.class);
        Form<LandingUser> user = form.bindFromRequest();
        LandingUser userModel = user.get();
        ObjectNode result = Json.newObject();
        List<String> errors = validateUser(userModel);
        if (user.hasErrors() || errors.size() > 0) {
            List<Object> listErrors = new ArrayList<>();

            result.put("status", "KO");
            for (int i = 0; i < errors.size(); i++) {
                listErrors.add(errors.get(i));
            }
            result.put("errors", Json.toJson(listErrors));
            result.put("result", Json.toJson(new ArrayList<String>()));
            return badRequest(result);
        } else {
            List<Object> listResult = new ArrayList<>();
            listResult.add(userModel);
            result.put("status", "OK");
            result.put("error", "");
            result.put("result", Json.toJson(listResult));
            userModel.save();
            return ok(result);
        }
    }

    public Double[] getAddress(Double latitude, Double longitude) {
        Double kmInLongitudeDegree = 111.320 * Math.cos(latitude / 180.0 * Math.PI);

        Double radiusInKm = 4.00;

        Double deltaLat = radiusInKm / 111.1;
        Double deltaLong = radiusInKm / kmInLongitudeDegree;

        Double minLat = latitude - deltaLat;
        Double maxLat = latitude + deltaLat;
        Double minLong = longitude - deltaLong;
        Double maxLong = longitude + deltaLong;

        Double[] adress = {minLat, maxLat, minLong, maxLong};
        return adress;
    }

    public Result getUsersFromLocation(Double latitude, Double longitude) {
        Double[] address = getAddress(latitude, longitude);

        List<Center> listCenter = Center.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<User> listUser = User.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<Company> listCompany = Company.finder.query().where(Expr.gt("latitude", address[0]))
                .where(Expr.lt("latitude", address[1]))
                .where(Expr.gt("longitude", address[2]))
                .where(Expr.lt("longitude", address[3]))
                .findList();

        List<Object> list = new ArrayList<>();

        if (listCenter != null && listCenter.size() > 0) {
            list.add(listCenter);
        }
        if (listCompany != null && listCompany.size() > 0) {
            list.add(listUser);
        }
        if (listUser != null && listUser.size() > 0) {
            list.add(listUser);
        }

        if (list.size() > 0) {
            return ok(response(true, list, ""));
        } else {
            return badRequest(response(false, null, "Not users around"));
        }
    }

    private ArrayList<String> validateUser(LandingUser user) {

        ArrayList<String> errors = new ArrayList();

        if (user.name == null || user.name.equalsIgnoreCase("")) {
            errors.add("Name not correct.");
        }

        if (user.surname == null || user.surname.equalsIgnoreCase("")) {
            errors.add("Surname not correct.");
        }

        if (user.email == null || user.email.equalsIgnoreCase("") || !Validations.validateEmail(user.email)) {
            errors.add("Email not correct.");
        }

        if (user.language == null || user.language.equalsIgnoreCase("") || user.language.length() > 2) {
            errors.add("Language not correct.");
        }

        if (user.latitude == null || user.longitude == null || user.latitude == 0 || user.longitude == 0) {
            errors.add("Insert a correct latitude and longitude.");
        }

        if (user.id_sector == null || user.id_sector.intValue() == 0) {
            errors.add("Insert id_sector.");
        }

        if (user.id_profession == null || user.id_profession.intValue() == 0) {
            errors.add("Insert id_profession.");
        }

        if (user.type_user == null || user.type_user.equalsIgnoreCase("") || (!user.type_user.equals("WORKER")
                && !user.type_user.equals("COMPANY") && !user.type_user.equals("LEARNING"))) {
            errors.add("Insert a type_user correct.");
        }

        return errors;
    }

    private ArrayList<String> validateSector(Sector user) {
        ArrayList<String> errors = new ArrayList();
        if (user.name == null || user.name.equalsIgnoreCase("")) {
            errors.add("Name not correct.");
        }
        if (user.name == null || user.name.equalsIgnoreCase("")) {
            errors.add("URL not correct.");
        }
        return errors;
    }


    public ObjectNode response(boolean correct, List<? extends Object> list, String errors) {
        ObjectNode result = Json.newObject();
        if (correct) {
            result.put("status", "OK");
            result.put("error", "");
            result.put("result", Json.toJson(list));
        } else {
            result.put("status", "KO");
            result.put("error", errors);
            result.put("result", Json.toJson(new ArrayList<String>()));
        }
        return result;
    }
}


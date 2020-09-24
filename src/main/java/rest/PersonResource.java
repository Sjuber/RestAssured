/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.FacadeExample;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author SJUBE
 */
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getById(@PathParam("id") Long id) throws exceptions.PersonNotFoundException {
        PersonDTO perD = FACADE.getPersonById(id);
        return GSON.toJson(perD);

    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        List<PersonDTO> pertos = FACADE.getAllPersons();
        return GSON.toJson(pertos);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String person) {

        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO p = FACADE.addPerson(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone());
        return Response.ok(p).build();

    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removPerson(@PathParam("id") Long id) throws PersonNotFoundException {
        PersonDTO pDelende = FACADE.deletePerson(id);
//        PersonDTO pDelende = FACADE.deletePerson(id);
        return GSON.toJson(pDelende);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePerson(@PathParam("id") Long id, String person) throws PersonNotFoundException {
        PersonDTO Carthago = GSON.fromJson(person, PersonDTO.class);

        Carthago.setId(id);
        PersonDTO Est = FACADE.editPerson(Carthago);
        return GSON.toJson(Est);
    }
}

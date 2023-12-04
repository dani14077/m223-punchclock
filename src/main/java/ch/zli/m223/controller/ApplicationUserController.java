package ch.zli.m223.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Credential;
import ch.zli.m223.service.ApplicationUserService;
import ch.zli.m223.service.SessionService;

@Path("/users")
@Tag(name = "Users", description = "Handling of users")
public class ApplicationUserController {

    @Inject
    ApplicationUserService userService;

    @Inject
    SessionService sessionService;

    @GET
    @RolesAllowed( "user" )
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all users.", description = "Returns a list of all users.")
    public List<ApplicationUser> index() {
        return userService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new user. Also known as registration.", description = "Creates a new user and returns the newly added user.")
    @PermitAll
    public ApplicationUser create(ApplicationUser user) {
        user.setRole("user");
        return userService.createUser(user);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(summary = "Logs in a user.", description = "Authenticates a user and returns a token.")
    public Response loginUser(Credential credential) {
        return sessionService.authenticate(credential);
    }

    @Path("/{id}")
    @RolesAllowed( "user" )
    @DELETE
    @Operation(summary = "Deletes an user.", description = "Deletes an user by its id.")
    public void delete(@PathParam("id") Long id) {
        userService.deleteUser(id);
    }

    @Path("/{id}")
    @RolesAllowed({ "user" })
    @PUT
    @Operation(summary = "Updates an user.", description = "Updates an user by its id.")
    public ApplicationUser update(@PathParam("id") Long id, ApplicationUser user) {
        user.setId(id);
        return userService.updateUser(user);
    }
}

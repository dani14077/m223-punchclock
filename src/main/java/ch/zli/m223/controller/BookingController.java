package ch.zli.m223.controller;

import java.util.List;

// import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Booking;
import ch.zli.m223.service.BookingService;

@Path("/bookings")
@Tag(name = "Bookings", description = "Handling of bookings")
@RolesAllowed({ "user", "admin" })
public class BookingController {

    @Inject
    BookingService bookingService;

    @GET
    @RolesAllowed({ "user", "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Index all bookings.", 
        description = "Returns a list of all bookings."
    )
    public List<Booking> index() {
        return bookingService.findAll();
    }

    @POST
    @RolesAllowed({ "user", "admin" })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "Creates a new booking.", 
        description = "Creates a new booking and returns the newly added booking."
    )
    public Booking create(Booking booking) {
      if (booking.getApplicationUser() == null || booking.getApplicationUser().getId() == null) {
            throw new BadRequestException("ApplicationUser must be set for the booking.");
        }

        return bookingService.createBooking(booking);
    }

    @Path("/{id}")
    @RolesAllowed({ "user", "admin" })
    @DELETE
    @Operation(
        summary = "Deletes a booking.",
        description = "Deletes a booking by its id."
    )
    public void delete(@PathParam("id") Long id) {
        bookingService.deleteBooking(id);
    }

    @Path("/{id}")
    @RolesAllowed( "admin" )
    @PUT
    @Operation(
        summary = "Updates a booking.",
        description = "Updates a booking by its id."
    )
    public Booking updateBooking(@PathParam("id") Long id, Booking booking) {
        booking.setId(id);
        return bookingService.updateBooking(booking);
    }
}

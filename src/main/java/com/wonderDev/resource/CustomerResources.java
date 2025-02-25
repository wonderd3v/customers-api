package com.wonderDev.resource;

import com.wonderDev.dto.CreateCustomerDto;
import com.wonderDev.dto.CustomerDto;
import com.wonderDev.dto.ResourceResponse;
import com.wonderDev.dto.UpdateCustomerDto;
import com.wonderDev.service.CustomerService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class CustomerResources {

    @Inject
    CustomerService customerService;

    @GET
    @Path("/all")
    @Operation(summary = "Obtener todos los clientes", description = "Retorna la lista de todos los clientes sin paginación")
    @APIResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente", content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    public Response getAll() {
        return Response.ok(ResourceResponse.success("Clientes obtenidos exitosamente", customerService.getAllCustomers())).build();
    }

    @GET
    @Operation(summary = "Obtener clientes paginados", description = "Retorna una lista paginada de clientes")
    @APIResponse(responseCode = "200", description = "Lista paginada obtenida correctamente", content = @Content(schema = @Schema(implementation = ResourceResponse.class)))
    public Response getPaginated(@QueryParam("page") @DefaultValue("1") int page,
                                 @QueryParam("size") @DefaultValue("10") int size) {
        return Response.ok(customerService.getCustomersPaginated(page, size)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Busca un cliente por su ID")
    @APIResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(ResourceResponse.success("Cliente encontrado", customerService.getCustomerById(id))).build();
    }

    @GET
    @Path("/getByEmail")
    @Operation(summary = "Obtener cliente por correo electrónico", description = "Busca un cliente por su dirección de correo")
    @APIResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
    public Response getByEmail(@QueryParam("email") String email) {
        return Response.ok(ResourceResponse.success("Cliente encontrado", customerService.getCustomerByEmail(email))).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en la base de datos")
    @APIResponse(responseCode = "201", description = "Cliente creado exitosamente", content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    @APIResponse(responseCode = "400", description = "Error de validación")
    public Response create(@Valid CreateCustomerDto dto) {
        return Response.status(Response.Status.CREATED).entity(ResourceResponse.success("Cliente creado exitosamente", customerService.createCustomer(dto))).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un cliente", description = "Modifica los datos de un cliente existente")
    @APIResponse(responseCode = "200", description = "Cliente actualizado correctamente", content = @Content(schema = @Schema(implementation = CustomerDto.class)))
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
    public Response update(@PathParam("id") Long id, @Valid UpdateCustomerDto dto) {
        return Response.ok(ResourceResponse.success("Cliente actualizado exitosamente", customerService.updateCustomer(id, dto))).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente de la base de datos")
    @APIResponse(responseCode = "200", description = "Cliente eliminado correctamente")
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
    public Response delete(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
        return Response.ok(ResourceResponse.success("Cliente eliminado exitosamente")).build();
    }
}

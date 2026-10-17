package com.lootchaser.infraestructure.rest;

import com.lootchaser.domain.Store;
import com.lootchaser.domain.StoreRepository;
import com.lootchaser.infraestructure.rest.dto.StoreRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/stores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Stores", description = "Gerenciamento de lojas")
public class StoreResource {

    @Inject
    StoreRepository storeRepository;

    @GET
    @Operation(summary = "Listar lojas paginadas com filtro por nome")
    public Response listAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("name") String nameFilter) {
        List<Store> stores = storeRepository.listAll(page, size, nameFilter);
        return Response.ok(stores).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar loja por ID")
    public Response getById(@PathParam("id") Long id) {
        return storeRepository.findById(id)
                .map(store -> Response.ok(store).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Operation(summary = "Cadastrar nova loja")
    public Response create(@Valid StoreRequest request) {
        Store store = new Store(null, request.name, request.domain, request.baseUrl, null);
        Store saved = storeRepository.save(store);
        return Response.created(URI.create("/stores/" + saved.id()))
                .entity(saved)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar loja existente")
    public Response update(@PathParam("id") Long id, @Valid StoreRequest request) {
        Store store = new Store(id, request.name, request.domain, request.baseUrl, null);
        Store updated = storeRepository.update(id, store);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover loja por ID")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = storeRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}

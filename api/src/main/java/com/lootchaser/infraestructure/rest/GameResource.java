package com.lootchaser.infraestructure.rest;

import com.lootchaser.domain.Game;
import com.lootchaser.domain.GameRepository;
import com.lootchaser.infraestructure.rest.dto.GameRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Games", description = "Gerenciamento do catálogo de jogos")
public class GameResource {

    @Inject
    GameRepository gameRepository;

    @GET
    @Operation(summary = "Listar jogos paginados com filtros por título e ID da loja")
    public Response listAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("title") String titleFilter,
            @QueryParam("storeId") Long storeIdFilter) {
        List<Game> games = gameRepository.listAll(page, size, titleFilter, storeIdFilter);
        return Response.ok(games).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar jogo por ID")
    public Response getById(@PathParam("id") Long id) {
        return gameRepository.findById(id)
                .map(game -> Response.ok(game).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Operation(summary = "Cadastrar novo jogo")
    public Response create(@Valid GameRequest request) {
        Game game = new Game(null, request.title, request.genre, request.basePrice, request.storeId, null);
        Game saved = gameRepository.save(game);
        return Response.created(URI.create("/games/" + saved.id()))
                .entity(saved)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Atualizar jogo existente")
    public Response update(@PathParam("id") Long id, @Valid GameRequest request) {
        Game game = new Game(id, request.title, request.genre, request.basePrice, request.storeId, null);
        Game updated = gameRepository.update(id, game);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Remover jogo por ID")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = gameRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
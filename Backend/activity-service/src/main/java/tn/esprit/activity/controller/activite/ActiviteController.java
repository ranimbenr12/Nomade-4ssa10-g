package tn.esprit.activity.controller.activite;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.activity.dto.activite.ActiviteRequest;
import tn.esprit.activity.dto.activite.ActiviteResponse;
import tn.esprit.activity.entity.activite.TypeActivite;
import tn.esprit.activity.service.activite.ActiviteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActiviteController {

    private final ActiviteService activiteService;

    public ActiviteController(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

    // GET /api/activities?hotelId=1&type=SPA  (filtres optionnels)
    @GetMapping
    public List<ActiviteResponse> findAll(@RequestParam(required = false) Long hotelId,
                                          @RequestParam(required = false) TypeActivite type) {
        return activiteService.findAll(hotelId, type);
    }

    // GET /api/activities/{id}
    @GetMapping("/{id}")
    public ActiviteResponse findById(@PathVariable String id) {
        return activiteService.findById(id);
    }

    // POST /api/activities  → 201 Created
    @PostMapping
    public ResponseEntity<ActiviteResponse> create(@Valid @RequestBody ActiviteRequest request) {
        ActiviteResponse created = activiteService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    // PUT /api/activities/{id}
    @PutMapping("/{id}")
    public ActiviteResponse update(@PathVariable String id, @Valid @RequestBody ActiviteRequest request) {
        return activiteService.update(id, request);
    }

    // DELETE /api/activities/{id}  → 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        activiteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
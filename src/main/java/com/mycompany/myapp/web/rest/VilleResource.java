package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ville;
import com.mycompany.myapp.service.VilleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ville.
 */
@RestController
@RequestMapping("/api")
public class VilleResource {

    private final Logger log = LoggerFactory.getLogger(VilleResource.class);

    private static final String ENTITY_NAME = "ville";

    private final VilleService villeService;

    public VilleResource(VilleService villeService) {
        this.villeService = villeService;
    }

    /**
     * POST  /villes : Create a new ville.
     *
     * @param ville the ville to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ville, or with status 400 (Bad Request) if the ville has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/villes")
    @Timed
    public ResponseEntity<Ville> createVille(@RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to save Ville : {}", ville);
        if (ville.getId() != null) {
            throw new BadRequestAlertException("A new ville cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ville result = villeService.save(ville);
        return ResponseEntity.created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /villes : Updates an existing ville.
     *
     * @param ville the ville to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ville,
     * or with status 400 (Bad Request) if the ville is not valid,
     * or with status 500 (Internal Server Error) if the ville couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/villes")
    @Timed
    public ResponseEntity<Ville> updateVille(@RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to update Ville : {}", ville);
        if (ville.getId() == null) {
            return createVille(ville);
        }
        Ville result = villeService.save(ville);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ville.getId().toString()))
            .body(result);
    }

    /**
     * GET  /villes : get all the villes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of villes in body
     */
    @GetMapping("/villes")
    @Timed
    public List<Ville> getAllVilles() {
        log.debug("REST request to get all Villes");
        return villeService.findAll();
        }

    /**
     * GET  /villes/:id : get the "id" ville.
     *
     * @param id the id of the ville to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ville, or with status 404 (Not Found)
     */
    @GetMapping("/villes/{id}")
    @Timed
    public ResponseEntity<Ville> getVille(@PathVariable Long id) {
        log.debug("REST request to get Ville : {}", id);
        Ville ville = villeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ville));
    }

    /**
     * DELETE  /villes/:id : delete the "id" ville.
     *
     * @param id the id of the ville to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/villes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        log.debug("REST request to delete Ville : {}", id);
        villeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

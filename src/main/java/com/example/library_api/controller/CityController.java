package com.example.library_api.controller;

import com.example.library_api.model.City;
import com.example.library_api.model.LibraryBranch;
import com.example.library_api.repository.CityRepository;
import com.example.library_api.repository.LibraryBranchRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityRepository cityRepo;
    private final LibraryBranchRepository branchRepo;

    public CityController(CityRepository cityRepo, LibraryBranchRepository branchRepo) {
        this.cityRepo = cityRepo;
        this.branchRepo = branchRepo;
    }

    @GetMapping
    public List<City> all() { return cityRepo.findAll(); }

    @PostMapping
    public City create(@Valid @RequestBody City city) { return cityRepo.save(city); }

    @GetMapping("/{id}")
    public ResponseEntity<City> get(@PathVariable Long id) {
        return cityRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @Valid @RequestBody City updated) {
        return cityRepo.findById(id).map(city -> {
            city.setName(updated.getName());
            city.setState(updated.getState());
            city.setPopulation(updated.getPopulation());
            return ResponseEntity.ok(cityRepo.save(city));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cityRepo.existsById(id)) return ResponseEntity.notFound().build();
        cityRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/branches")
    public ResponseEntity<Set<LibraryBranch>> branchesInCity(@PathVariable Long id) {
        if (!cityRepo.existsById(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(branchRepo.findByCityId(id));
    }
}

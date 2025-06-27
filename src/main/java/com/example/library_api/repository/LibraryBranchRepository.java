package com.example.library_api.repository;
import com.example.library_api.model.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;
public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Long> {
    Set<LibraryBranch> findByCityId(Long cityId);
}

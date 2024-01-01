package com.mfm.gansys.repositories;

import com.mfm.gansys.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    boolean existsById(Long id);
}

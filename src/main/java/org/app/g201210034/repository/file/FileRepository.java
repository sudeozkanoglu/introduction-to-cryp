package org.app.g201210034.repository.file;

import org.app.g201210034.model.file.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<List<File>> findFileByCarId(Long carId);

    boolean existsByCarIdAndFileName(Long carId, String fileName);

}

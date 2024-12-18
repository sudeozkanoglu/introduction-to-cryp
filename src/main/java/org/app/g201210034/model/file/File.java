package org.app.g201210034.model.file;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_download_uri")
    private String fileDownloadUri;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size")
    private Long size;

    @Column(name = "car_id")
    private Long carId;

}

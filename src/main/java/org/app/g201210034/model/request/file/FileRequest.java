package org.app.g201210034.model.request.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
    private Long carId;
}

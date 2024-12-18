package org.app.g201210034.service.file;

import jakarta.servlet.http.HttpServletRequest;
import org.app.g201210034.model.file.File;
import org.app.g201210034.model.request.file.FileRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    Result saveFile(FileRequest fileRequest);
    ResponseEntity<Resource> downloadFile(Long carId, String fileName, HttpServletRequest request);
    void uploadSingleFile(MultipartFile file, Long carId);
    DataResult<List<File>> getFileByCarId(Long carId);
    DataResult<Boolean> existFindByFileNameAndCarId(Long carId, String filename);
}

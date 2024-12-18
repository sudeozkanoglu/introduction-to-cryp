package org.app.g201210034.service.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.file.File;
import org.app.g201210034.model.request.file.FileRequest;
import org.app.g201210034.repository.file.FileRepository;
import org.app.g201210034.results.*;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {
    private final FileStorageService fileStorageService;
    private final FileRepository fileRepository;
    @Override
    public Result saveFile(FileRequest fileRequest) {
        if(fileRequest.getFileName() == null) {
            return Result.showMessage(Result.SERVER_ERROR, ResultMessageType.ERROR, "Please enter the file name.");
        }
        if(fileRequest.getCarId() == null) {
            return Result.showMessage(Result.SERVER_ERROR, ResultMessageType.ERROR, "Please enter the car id.");
        }

        File fileNew = new File();
        fileNew.setFileName(fileRequest.getFileName());
        fileNew.setFileType(fileRequest.getFileType());
        fileNew.setSize(fileRequest.getSize());
        fileNew.setFileDownloadUri(fileRequest.getFileDownloadUri());
        fileNew.setCarId(fileRequest.getCarId());
        fileRepository.save(fileNew);
        return Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "File saved successfully.");

    }

    @Override
    public ResponseEntity<Resource> downloadFile(Long carId, String fileName, HttpServletRequest request) {
        Path file;
        if(carId == null)
            throw new RuntimeException("Car Id is null");
        file = fileStorageService.createFile(carId);
        Resource resource = fileStorageService.loadFileAsResource(file, fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Override
    public void uploadSingleFile(MultipartFile file, Long carId) {
        Path path;
        if(carId != null)
            path = fileStorageService.createFile(carId);
        else
            throw new RuntimeException("Car Id is null");
        fileStorageService.storeFile(path, file);
    }

    @Override
    public DataResult<List<File>> getFileByCarId(Long carId) {
        if(carId != null) {
            Optional<List<File>> files = fileRepository.findFileByCarId(carId);
            return new SuccessDataResult<>(files.get(), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "Files found."));
        }
        return new ErrorDataResult<>(Result.showMessage(Result.SERVER_ERROR, ResultMessageType.ERROR, "Car Id is null."));
    }

    @Override
    public DataResult<Boolean> existFindByFileNameAndCarId(Long carId, String filename) {
        return new SuccessDataResult<>(fileRepository.existsByCarIdAndFileName(carId, filename), Result.showMessage(Result.SUCCESS, ResultMessageType.SUCCESS, "File found."));
    }
}

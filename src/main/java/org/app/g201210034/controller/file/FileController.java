package org.app.g201210034.controller.file;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.app.g201210034.model.file.File;
import org.app.g201210034.model.request.file.FileRequest;
import org.app.g201210034.results.DataResult;
import org.app.g201210034.results.Result;
import org.app.g201210034.service.file.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file/v1")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("getFileByCarId")
    public DataResult<List<File>> getFileByCarId(@RequestParam Long carId) {
        return fileService.getFileByCarId(carId);
    }

    @PostMapping("saveFile")
    public Result saveFile(@RequestBody FileRequest fileRequest) {
        return fileService.saveFile(fileRequest);
    }

    @GetMapping("/download-file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@RequestParam Long carId, @PathVariable String fileName, HttpServletRequest request) {
        return fileService.downloadFile(carId, fileName, request);
    }
}

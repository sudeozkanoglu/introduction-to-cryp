package org.app.g201210034.service.file;

import org.app.g201210034.results.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {


	public Path createFile(Long carId) {
		String currentUsersHomeDir;
		if (carId != null)
			currentUsersHomeDir = System.getProperty("user.dir") + "/Uploads/" + carId;
		else
			throw new FileStorageException("Car Id is null");

		try {
			Path fileStorageLocation = Paths.get(currentUsersHomeDir)
					.toAbsolutePath().normalize();
			 Files.createDirectories(fileStorageLocation);
			return fileStorageLocation;
		} catch (Exception ex) {
			throw new FileStorageException("Unable to create the directory where the uploaded files will be stored.", ex);
		}
	}

	public String storeFile(Path fileStorageLocation, MultipartFile file) {

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}


	public Resource loadFileAsResource(Path fileStorageLocation,String fileName) {
		try {
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
			}
		} catch (MalformedURLException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}


}

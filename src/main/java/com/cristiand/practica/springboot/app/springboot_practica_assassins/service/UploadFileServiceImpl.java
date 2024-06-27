package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementación del servicio para la gestión de carga, consulta y eliminación
 * de archivos.
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {

	private final static String DIRECTORY_UPLOAD = "uploads";

	/**
	 * Carga una foto según su nombre.
	 *
	 * @param namePhoto Nombre de la foto a cargar.
	 * @return Recurso que representa la foto.
	 * @throws MalformedURLException Si ocurre un error al crear la URL del recurso.
	 */
	@Override
	public Resource loadPhoto(String namePhoto) throws CustomAssassinException, MalformedURLException {
		Path filePath = getPathPhoto(namePhoto);

		try {
			// Intenta cargar la foto desde el sistema de archivos
			Resource resource = new UrlResource(filePath.toUri());

			// Si la foto no existe o no es legible, carga una imagen predeterminada
			if (!resource.exists() || !resource.isReadable()) {
				filePath = Paths.get("src/main/resources/static/images").resolve("no-user.jpg").toAbsolutePath();
				resource = new UrlResource(filePath.toUri());
				throw new CustomAssassinException("No se pudo cargar la imagen: " + namePhoto, ErrorCode.NOT_FOUND);
			}

			return resource;
		} catch (MalformedURLException e) {
			throw new MalformedURLException("Error al cargar la foto: " + e.getMessage());
		}
	}

	/**
	 * Copia un archivo de imagen al directorio de carga.
	 *
	 * @param archive Archivo de imagen a copiar.
	 * @return Nombre único del archivo guardado.
	 * @throws IllegalArgumentException Si el archivo es nulo o vacío.
	 * @throws IOException              Si ocurre un error de IO durante la copia.
	 * @throws Exception                Para otras excepciones inesperadas durante
	 *                                  la copia.
	 */
	@Override
	public String copyPhoto(MultipartFile archive)
			throws IllegalArgumentException, NullPointerException, IOException, Exception {
		// Verificar que el archivo de imagen no sea nulo o vacío
		if (archive == null || archive.isEmpty()) {
			throw new IllegalArgumentException("El archivo de imagen no debe ser nulo o vacío.");
		}

		// Obtener el nombre original del archivo
		String originalFilename = archive.getOriginalFilename();
		if (originalFilename == null) {
			throw new NullPointerException("El nombre original del archivo de imagen es nulo.");
		}

		// Generar un nombre único para el archivo de imagen
		String nameArchive = UUID.randomUUID().toString() + "_" + originalFilename.replace(" ", "");

		// Obtener la ruta de destino para guardar el archivo
		Path rutaArchive = getPathPhoto(nameArchive);

		// Intentar copiar el archivo
		try {
			Files.copy(archive.getInputStream(), rutaArchive);
			return nameArchive; // Devolver el nombre del archivo guardado
		} catch (IOException e) {
			// Capturar excepción de IO y relanzarla con un mensaje adecuado
			throw new IOException("Error al copiar la foto de perfil: " + e.getMessage(), e);
		} catch (Exception e) {
			// Capturar cualquier otra excepción inesperada y relanzarla como IOException
			throw new Exception("Error inesperado al copiar la foto de perfil: " + e.getMessage(), e);
		}
	}

	/**
	 * Elimina una foto del directorio de carga.
	 *
	 * @param namePhoto Nombre de la foto a eliminar.
	 * @return true si la eliminación fue exitosa, false si no.
	 */
	@Override
	public boolean deletePhoto(String namePhoto) throws CustomAssassinException, IllegalArgumentException {
		if (namePhoto != null && !namePhoto.isEmpty()) {
			Path rutaFotoAnterior = Paths.get(DIRECTORY_UPLOAD).resolve(namePhoto).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				if (archivoFotoAnterior.delete()) {
					return true;
				} else {
					throw new CustomAssassinException("No se pudo eliminar la foto: " + namePhoto,
							ErrorCode.INTERNAL_SERVER_ERROR);
				}
			} else {
				throw new CustomAssassinException("La foto no existe o no se puede leer: " + namePhoto,
						ErrorCode.NOT_FOUND);
			}
		} else {
			throw new IllegalArgumentException("El nombre de la foto no debe ser nulo o vacío.");
		}
	}

	/**
	 * Obtiene la ruta completa de la foto en el directorio de carga.
	 *
	 * @param namePhoto Nombre de la foto.
	 * @return Ruta completa de la foto.
	 */
	@Override
	public Path getPathPhoto(String namePhoto) {
		return Paths.get(DIRECTORY_UPLOAD).resolve(namePhoto).toAbsolutePath();
	}

}

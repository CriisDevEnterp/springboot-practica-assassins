package com.cristiand.practica.springboot.app.springboot_practica_assassins.service;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz para el servicio de carga, consulta y eliminación de archivos.
 */
public interface UploadFileService {

	/**
	 * Carga una foto según su nombre.
	 *
	 * @param namePhoto Nombre de la foto a cargar.
	 * @return Recurso que representa la foto.
	 * @throws CustomAssassinException Si ocurre un error específico relacionado con
	 *                                 la carga de la foto.
	 * @throws MalformedURLException   Si ocurre un error al crear la URL del
	 *                                 recurso.
	 */
	Resource loadPhoto(String namePhoto) throws CustomAssassinException, MalformedURLException;

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
	String copyPhoto(MultipartFile archive) throws IllegalArgumentException, IOException, Exception;

	/**
	 * Elimina una foto del directorio de carga.
	 *
	 * @param namePhoto Nombre de la foto a eliminar.
	 * @return true si la eliminación fue exitosa, false si no.
	 * @throws CustomAssassinException  Si ocurre un error específico relacionado
	 *                                  con la eliminación de la foto.
	 * @throws IllegalArgumentException Si el nombre de la foto es nulo o vacío.
	 */
	boolean deletePhoto(String namePhoto) throws CustomAssassinException, IllegalArgumentException;

	/**
	 * Obtiene la ruta completa de la foto en el directorio de carga.
	 *
	 * @param namePhoto Nombre de la foto.
	 * @return Ruta completa de la foto.
	 */
	Path getPathPhoto(String namePhoto);

}

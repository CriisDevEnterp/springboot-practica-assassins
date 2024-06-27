package com.cristiand.practica.springboot.app.springboot_practica_assassins.util;

import com.cristiand.practica.springboot.app.springboot_practica_assassins.entity.User;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.enums.ErrorCode;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.exception.domain.CustomAssassinException;
import com.cristiand.practica.springboot.app.springboot_practica_assassins.service.UploadFileService;
import jakarta.validation.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Clase utilitaria para manejar la carga y validación de imágenes de perfil de
 * usuario.
 * Esta clase es un componente de Spring y se utiliza para procesar y asignar
 * imágenes de perfil a objetos User.
 */
@Component
public class UploadFileUtil {

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * Maneja la imagen de perfil subida y la asigna al usuario dado.
     *
     * @param profileImage La imagen de perfil cargada como MultipartFile.
     * @param user         El objeto User al que se le asignará la imagen de perfil.
     * @return La ruta de la imagen de perfil asignada al usuario.
     * @throws ValidationException      Si la imagen cargada no es válida o no es
     *                                  una imagen.
     * @throws IOException              Si ocurre un error de entrada/salida durante
     *                                  el procesamiento de la imagen.
     * @throws IllegalArgumentException Si se proporcionan argumentos ilegales al
     *                                  método.
     * @throws NullPointerException     Si se intenta acceder a un objeto nulo.
     * @throws Exception                Para manejar otros errores no especificados
     *                                  específicamente.
     */
    public String handleProfileImage(MultipartFile profileImage, User user)
            throws ValidationException, IOException, IllegalArgumentException, NullPointerException, Exception {
        try {
            String profileImagePath;
            if (profileImage != null && !profileImage.isEmpty()) {
                String contentType = profileImage.getContentType();
                // Verificación de que el archivo subido es una imagen y que el tipo MIME no es
                // nulo.
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new ValidationException("El archivo debe ser una imagen.");
                }

                // Copiar la imagen utilizando el servicio de carga de archivos.
                String fileName = uploadFileService.copyPhoto(profileImage);
                profileImagePath = "uploads/" + fileName; // Ruta relativa a la carpeta raíz del proyecto.

            } else {
                // Ruta de imagen predeterminada si no se proporciona una imagen.
                profileImagePath = "static/images/no-user.jpg";
                // Verificar si existe el recurso de imagen predeterminada.
                if (!doesResourceExist(profileImagePath)) {
                    throw new IOException(
                            "La imagen predeterminada no se encuentra en la ruta: " + profileImagePath + ".");
                }

                // Verificación de que la imagen predeterminada es una imagen válida.
                if (!isImageFile(profileImagePath)) {
                    throw new IOException("El archivo predeterminado no es una imagen válida.");
                }
            }

            // Devuelve la ruta de la imagen.
            return profileImagePath;
        } catch (IOException e) {
            throw new IOException("Error al establecer la foto de perfil. " + e.getMessage());
        }
    }

    /**
     * Verifica si un recurso especificado por su ruta existe en el classpath.
     *
     * @param resourcePath La ruta del recurso a verificar.
     * @return true si el recurso existe, false si no.
     */
    private boolean doesResourceExist(String resourcePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResource(resourcePath) != null;
    }

    /**
     * Verifica si un archivo especificado por su ruta es una imagen válida.
     *
     * @param resourcePath La ruta del archivo a verificar.
     * @return true si el archivo es una imagen válida, false si no.
     * @throws ValidationException Si hay errores durante la validación del tipo de
     *                             archivo.
     */
    private boolean isImageFile(String resourcePath) throws ValidationException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceUrl = classLoader.getResource(resourcePath);
        if (resourceUrl != null) {
            try {
                // Obtener el tipo MIME del archivo.
                Path path = Paths.get(resourceUrl.toURI());
                String mimeType = Files.probeContentType(path);
                return mimeType != null && mimeType.startsWith("image/");
            } catch (Exception e) {
                // Capturar errores durante la verificación del tipo de archivo.
                throw new CustomAssassinException("Error al verificar el tipo de archivo: " + e.getMessage(),
                        ErrorCode.DATA_PROCESSING);
            }
        }
        return false;
    }

}

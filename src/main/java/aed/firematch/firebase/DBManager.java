package aed.firematch.firebase;

import aed.firematch.ui.modelos.Genero;
import aed.firematch.ui.modelos.Usuario;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class DBManager {
    private ImageManager imageManager;
    private Firestore db;

    public DBManager() {
        imageManager = new ImageManager();

        try {
            // Cargar el archivo JSON desde la carpeta resources
            InputStream serviceAccount = DBManager.class.getClassLoader()
                    .getResourceAsStream("firematch-firebase-adminsdk.json");

            if (serviceAccount == null) {
                throw new RuntimeException("Archivo de credenciales no encontrado en resources");
            }

            // Configura Firebase
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://firematch.firebaseio.com") // Asegúrate de que el URL es correcto
                    .build();

            if (FirebaseApp.getApps().isEmpty()) { // Evita inicializar múltiples veces
                FirebaseApp.initializeApp(options);
            }

            // Obtener una instancia de Firestore
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea un usuario en Firestore dentro de la colección "FireMatch/Usuarios"
     */
    public void crearUsuario(Usuario usuario) {
        if (usuario == null) {
            System.err.println("Error: El usuario no puede ser nulo.");
            return;
        }

        String idUsuario = String.valueOf(usuario.getId());

        // Referencia a la colección de usuarios
        CollectionReference usuariosRef = db.collection("FireMatch").document("Usuarios").collection("ListaUsuarios");
        DocumentReference usuarioDoc = usuariosRef.document(idUsuario);

        // Datos del usuario en un mapa (sin características, porque será una subcolección)
        Map<String, Object> usuarioData = new HashMap<>();
        usuarioData.put("nombre", usuario.getNombre());
        usuarioData.put("apellidos", usuario.getApellidos());
        usuarioData.put("edad", usuario.getEdad());
        usuarioData.put("nickname", usuario.getNickname());
        usuarioData.put("email", usuario.getEmail());
        usuarioData.put("password", usuario.getPassword());
        usuarioData.put("descripcion", usuario.getDescripcion());
        usuarioData.put("genero", usuario.getGenero().name());
        usuarioData.put("fotoPerfil", null); // Se manejará en Firebase Storage

        try {
            // Verificar si el usuario ya existe
            if (!usuarioDoc.get().get().exists()) {
                usuarioDoc.set(usuarioData).get(); // Guardar los datos básicos
                System.out.println("✅ Usuario creado con éxito: " + idUsuario);

                // Crear la subcolección "Caracteristicas" con IDs numerados
                if (usuario.getCaracteristicas() != null && !usuario.getCaracteristicas().isEmpty()) {
                    CollectionReference caracteristicasRef = usuarioDoc.collection("Caracteristicas");

                    int index = 1;
                    for (String caracteristica : usuario.getCaracteristicas()) {
                        String idCaracteristica = "caracteristica" + index; // ID fijo numerado

                        Map<String, Object> data = new HashMap<>();
                        data.put("valor", caracteristica);
                        caracteristicasRef.document(idCaracteristica).set(data).get(); // Guardar con ID fijo

                        index++;
                    }
                    System.out.println("✅ Características agregadas para el usuario: " + idUsuario);
                }

                // Crear la subcolección "Gustos" con IDs numerados
                if (usuario.getGustos() != null && !usuario.getGustos().isEmpty()) {
                    CollectionReference gustosRef = usuarioDoc.collection("Gustos");

                    int index = 1;
                    for (Genero gusto : usuario.getGustos()) {
                        String idGusto = "gusto" + index; // ID fijo numerado

                        Map<String, Object> data = new HashMap<>();
                        data.put("valor", gusto.name());
                        gustosRef.document(idGusto).set(data).get(); // Guardar con ID fijo

                        index++;
                    }
                    System.out.println("✅ Gustos agregados para el usuario: " + idUsuario);
                }
            } else {
                System.out.println("⚠️ El usuario ya existe: " + idUsuario);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("❌ Error al verificar/crear usuario: " + e.getMessage());
        }
    }

    public boolean login(String usuario, String password) {
        CollectionReference usuariosRef = db.collection("FireMatch").document("Usuarios").collection("ListaUsuarios");
        Query query = usuariosRef.whereEqualTo("email", usuario).whereEqualTo("password", password);

        try {
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                System.out.println("Usuario autenticado: " + document.getId());
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
        }
        return false;
    }

    public Usuario getUserByEmail(String email) throws ExecutionException, InterruptedException {
        CollectionReference usuariosRef = db.collection("FireMatch").document("Usuarios").collection("ListaUsuarios");
        Query query = usuariosRef.whereEqualTo("email", email);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Usuario usuario = document.toObject(Usuario.class);
            return usuario;
        }
        return null;
    }

    public List<Usuario> obtenerUsuariosAleatorios(String emailLogeado) {
        List<Usuario> usuarios = new ArrayList<>();
        CollectionReference usuariosRef = db.collection("FireMatch").document("Usuarios").collection("ListaUsuarios");

        try {
            ApiFuture<QuerySnapshot> querySnapshot = usuariosRef.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Usuario usuario = document.toObject(Usuario.class);
                if (usuario != null && !usuario.getEmail().equals(emailLogeado)) {
                    usuario.setId(Integer.parseInt(document.getId())); // Asegúrate de que el ID se establece correctamente
                    usuarios.add(usuario);
                }
            }
            Collections.shuffle(usuarios); // Mezclar la lista para obtener un orden aleatorio
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error al obtener usuarios aleatorios: " + e.getMessage());
        }

        return usuarios;
    }

    public void cargarImagenesUsuarios(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            int id = usuario.getId();
            String imagePath = imageManager.getImagePath(id);
            if (imagePath != null) {
                System.out.println("Loading image from path: " + imagePath); // Debug statement
                InputStream imageStream = getClass().getClassLoader().getResourceAsStream(imagePath);
                if (imageStream != null) {
                    Image image = new Image(imageStream);
                    usuario.setFotoPerfil(image);
                    System.out.println("Image loaded successfully for user ID: " + id); // Debug statement
                } else {
                    System.err.println("No se encontró la imagen para el usuario con ID: " + id);
                }
            } else {
                System.err.println("No image path found for user ID: " + id);
            }
        }
    }

    public void logout() {
        FirebaseApp.getInstance().delete();
    }
}

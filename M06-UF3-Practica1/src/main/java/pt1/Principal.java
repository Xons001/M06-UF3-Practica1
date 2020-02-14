package pt1;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Principal {

	public static void main(String[] args) {
		MongoClient mongo = crearConexion();
		MongoDatabase database = mongo.getDatabase("Tienda");
		String tabla, nombre;
		int precio;
		Scanner lector = new Scanner(System.in);
		if (mongo != null) {
			boolean salir = false;
			while(salir == false) {
				System.out.println("=============================================");
				System.out.println("Menu");
				System.out.println("1.-Insertar documentos");
				System.out.println("2.-Modificar documentos");
				System.out.println("3.-Eliminar documentos");
				System.out.println("4.-Buscar documento");
				System.out.println("5.-Salir");
				System.out.println("=============================================");
				int pos = lector.nextInt();

				switch (pos) {
				case 1:
					System.out.print("Escribe la tabla a la que quieres insertar los datos: ");
					tabla = lector.nextLine();
					lector.nextLine();
					System.out.print("Inserta el nombre de la consola: ");
					nombre = lector.nextLine();
					System.out.print("Inserta su precio: ");
					precio = lector.nextInt();

					insertarColeccion(mongo, database, nombre, precio, tabla);
					break;
				case 2:
					System.out.print("Escribe la tabla a la que quieres modificar los datos: ");
					tabla = lector.nextLine();
					lector.nextLine();
					System.out.print("Escribe el nombre de la consola: ");
					nombre = lector.nextLine();
					System.out.print("Escribe el nuevo nombre: ");
					String nuevoNombre = lector.nextLine();
					
					modificarDocumento(mongo, database, nombre, nuevoNombre, tabla);
					break;
				case 3:

					break;

				case 4:

					break;

				case 5:
					System.out.println("Fin del programa");
					salir = true;
					break;

				default:
					System.out.println("No has insertado el numero de forma correcta");
					break;
				}

			}
		} else {
			System.out.println("Error: Conexión no establecida");
		}
	}

	public static MongoClient crearConexion() {
		System.out.println("Conexión MongoDB");
		MongoClientURI uri = new MongoClientURI("mongodb+srv://Xons001:1234@clusterprueba-7onhf.mongodb.net/test?retryWrites=true&w=majority");

		MongoClient mongoClient = new MongoClient(uri);
		return mongoClient;
	}

	public static void insertarColeccion(MongoClient mongo, MongoDatabase database, String nombre, double precio, String tabla) {

		// Select the collection
		MongoCollection<Document> collection = database.getCollection(tabla);

		Document document = new Document("nombre", nombre).append("precio", precio);
		// Insert the document in the collection
		collection.insertOne(document);
		System.out.println("datos insertados");
	}

	public static void modificarDocumento(MongoClient mongo, MongoDatabase database, String nombre, String nuevoNombre, String tabla) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection(tabla);

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Create the document to specify the update
		Document updateDocument = new Document("$set", new Document("nombre", nuevoNombre));

		// Find one person and delete
		collection.findOneAndUpdate(findDocument, updateDocument);
	}
}

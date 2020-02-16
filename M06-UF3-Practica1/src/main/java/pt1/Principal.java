package pt1;

import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Principal {

	public static void main(String[] args) {
		MongoClient mongo = crearConexion();
		MongoDatabase database = mongo.getDatabase("Tienda");
		String nombre, plataforma, nuevoNombre;
		int precio, posTabla;
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
				System.out.println("5.-Multiples resultados de la busqueda");
				System.out.println("6.-Salir");
				System.out.println("=============================================");
				int pos = lector.nextInt();

				switch (pos) {
				case 1:
					System.out.println("-------------------------------------------------------------");
					System.out.println("En que tabla quieres insertar los datos, pulsa el numero: ");
					System.out.println("1.-Consolas");
					System.out.println("2.-Juegos");
					System.out.println("-------------------------------------------------------------");
					posTabla = lector.nextInt(); 
					switch (posTabla) {
					case 1:
						lector.nextLine();
						System.out.print("Inserta el nombre de la consola: ");
						nombre = lector.nextLine();
						System.out.print("Inserta su precio: ");
						precio = lector.nextInt();

						insertarDocumentoConsolas(mongo, database, nombre, precio);
						break;

					case 2:
						lector.nextLine();
						System.out.print("Inserta el nombre del juego: ");
						nombre = lector.nextLine();
						System.out.print("Inserta el nombre de la plataforma:");
						plataforma = lector.nextLine();
						System.out.print("Inserta su precio: ");
						precio = lector.nextInt();
						
						insertarDocumentoJuegos(mongo, database, nombre, precio, plataforma);
						break;

					default:
						System.out.println("No escogiste bien el numero de la tabla");
						break;
					}
					
					break;
				case 2:
					System.out.println("-------------------------------------------------------------");
					System.out.println("En que tabla quieres modificar los datos, pulsa el numero: ");
					System.out.println("1.-Consolas");
					System.out.println("2.-Juegos");
					System.out.println("-------------------------------------------------------------");
					posTabla = lector.nextInt(); 
					switch (posTabla) {
					case 1:
						lector.nextLine();
						System.out.print("Escribe el nombre de la consola: ");
						nombre = lector.nextLine();

						System.out.print("Escribe el nuevo nombre: ");
						nuevoNombre = lector.nextLine();

						modificarDocumentoConsolas(mongo, database, nombre, nuevoNombre);
						break;

					case 2:
						lector.nextLine();
						System.out.print("Escribe el nombre del juego: ");
						nombre = lector.nextLine();
						
						System.out.print("Escribe el nuevo nombre: ");
						nuevoNombre = lector.nextLine();

						modificarDocumentoJuegos(mongo, database, nombre, nuevoNombre);
						break;

					default:
						System.out.println("No escogiste bien el numero de la tabla");
						break;
					}
					
					
					break;
				case 3:

					System.out.println("-------------------------------------------------------------");
					System.out.println("En que tabla quieres eliminar los datos, pulsa el numero: ");
					System.out.println("1.-Consolas");
					System.out.println("2.-Juegos");
					System.out.println("-------------------------------------------------------------");
					posTabla = lector.nextInt(); 
					switch (posTabla) {
					case 1:
						lector.nextLine();
						System.out.print("Escribe el nombre de la consola: ");
						nombre = lector.nextLine();
						
						eliminarDocumentoConsolas(mongo, database, nombre);
						break;

					case 2:
						lector.nextLine();
						System.out.print("Escribe el nombre del juego: ");
						nombre = lector.nextLine();

						eliminarDocumentoJuegos(mongo, database, nombre);
						break;

					default:
						System.out.println("No escogiste bien el numero de la tabla");
						break;
					}
					break;

				case 4:
					System.out.println("-------------------------------------------------------------");
					System.out.println("En que tabla quieres buscar un dato, pulsa el numero: ");
					System.out.println("1.-Consolas");
					System.out.println("2.-Juegos");
					System.out.println("-------------------------------------------------------------");
					posTabla = lector.nextInt(); 
					switch (posTabla) {
					case 1:
						lector.nextLine();
						System.out.print("Escribe el nombre de la consola: ");
						nombre = lector.nextLine();
						
						System.out.println("Consola encontrada: " + buscarDocumento(mongo, database, nombre, "Consolas"));
						break;

					case 2:
						lector.nextLine();
						System.out.print("Escribe el nombre del juego: ");
						nombre = lector.nextLine();

						System.out.println("Juego encontrado: " + buscarDocumento(mongo, database, nombre, "Consolas"));
						break;

					default:
						System.out.println("No escogiste bien el numero de la tabla");
						break;
					}
					break;

				case 5:
					System.out.println("-------------------------------------------------------------");
					System.out.println("En que tabla quieres buscar multiples datos, pulsa el numero: ");
					System.out.println("1.-Consolas");
					System.out.println("2.-Juegos");
					System.out.println("-------------------------------------------------------------");
					posTabla = lector.nextInt(); 
					switch (posTabla) {
					case 1:
						lector.nextLine();
						System.out.print("Escribe el nombre de la consola: ");
						nombre = lector.nextLine();
						
						buscarMultiplesDocumentos(mongo, database, nombre, "Consolas");
						break;

					case 2:
						lector.nextLine();
						System.out.print("Escribe el nombre del juego: ");
						nombre = lector.nextLine();

						buscarMultiplesDocumentos(mongo, database, nombre, "Juegos");
						break;

					default:
						System.out.println("No escogiste bien el numero de la tabla");
						break;
					}					
					break;
					
				case 6:
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
	

	public static void insertarDocumentoConsolas(MongoClient mongo, MongoDatabase database, String nombre, double precio) {

		// Select the collection
		MongoCollection<Document> collection = database.getCollection("Consolas");

		Document document = new Document("nombre", nombre).append("precio", precio);
		// Insert the document in the collection
		collection.insertOne(document);
		System.out.println("datos de la consola insertados");
	}

	public static void insertarDocumentoJuegos(MongoClient mongo, MongoDatabase database, String nombre, double precio, String plataforma) {

		// Select the collection
		MongoCollection<Document> collection = database.getCollection("Juegos");

		Document document = new Document("nombre", nombre).append("precio", precio).append("plataforma", plataforma);
		// Insert the document in the collection
		collection.insertOne(document);
		System.out.println("datos del juego insertados");
	}
	
	public static void modificarDocumentoConsolas(MongoClient mongo, MongoDatabase database, String nombre, String nuevoNombre) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection("Consolas");

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Create the document to specify the update
		Document updateDocument = new Document("$set", new Document("nombre", nuevoNombre));

		// Find one person and delete
		collection.findOneAndUpdate(findDocument, updateDocument);
	}
	public static void modificarDocumentoJuegos(MongoClient mongo, MongoDatabase database, String nombre, String nuevoNombre) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection("Juegos");

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Create the document to specify the update
		Document updateDocument = new Document("$set", new Document("nombre", nuevoNombre));

		// Find one person and delete
		collection.findOneAndUpdate(findDocument, updateDocument);
	}
	
	public static void eliminarDocumentoConsolas(MongoClient mongo, MongoDatabase database, String nombre) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection("Consolas");

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Find one person and delete
		collection.findOneAndDelete(findDocument);
	}
	public static void eliminarDocumentoJuegos(MongoClient mongo, MongoDatabase database, String nombre) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection("Juegos");

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Find one person and delete
		collection.findOneAndDelete(findDocument);
	}
	
	public static String buscarDocumento(MongoClient mongo, MongoDatabase database, String nombre, String tabla) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection(tabla);

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);

		// Document to store query results
	    FindIterable<Document> resultDocument = collection.find(findDocument);

	    // Return the name of the first returned document
	    return resultDocument.first().toJson();
	}
	public static void buscarMultiplesDocumentos(MongoClient mongo, MongoDatabase database, String nombre, String tabla) {
		// Select the "Tienda" collection
		MongoCollection<Document> collection = database.getCollection(tabla);

		// Create the document to specify find criteria
		Document findDocument = new Document("nombre", nombre);
		
		// Document to store query results
	    MongoCursor<Document> resultDocument = collection.find(findDocument).iterator();

	    System.out.println("Lista de precios por nombre: " + nombre);
	    
	    // Iterate over the results printing each document
	    while (resultDocument.hasNext()) {
	      System.out.println(resultDocument.next().getDouble("precio"));
	    }
	}
}

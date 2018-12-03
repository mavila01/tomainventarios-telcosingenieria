package telcos.proyectos.tomainventarios;

public class config {
    public static final String IP = "http://172.16.11.19";
    public static final String PUERTO_HOST = "";
    public static final String CARPETA = "/Toma_Inventario/app/webServices";

    public static final String GET_MATERIALES = IP + PUERTO_HOST + CARPETA + "/obtener_materiales.php";
    public static final String GET_ESTADO = IP + PUERTO_HOST + CARPETA + "/obtener_estado.php";
    public static final String GET_NODO = IP + PUERTO_HOST + CARPETA + "/obtener_nodos.php";
}

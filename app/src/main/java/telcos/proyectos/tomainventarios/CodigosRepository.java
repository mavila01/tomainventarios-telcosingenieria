package telcos.proyectos.tomainventarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodigosRepository {
    private static CodigosRepository repository = new CodigosRepository();
    private HashMap<String, Codigos> codis = new HashMap<>();

    public static CodigosRepository getInstance() {
        return repository;
    }

    private CodigosRepository() {
        saveCodigos(new Codigos("1024468", "CONECTOR LOOKING"));
        saveCodigos(new Codigos("1036544", "CONECTOR DE CONTINUIDAD"));
        saveCodigos(new Codigos("4025698", "DECODIFICADOR DE CLARO"));
    }

    private void saveCodigos(Codigos codes) {
        codis.put(codes.getmCod(), codes);
    }

    public List<Codigos> getCodigos() {
        return new ArrayList<>(codis.values());
    }
}

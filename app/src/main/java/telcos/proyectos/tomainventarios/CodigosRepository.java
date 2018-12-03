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
        saveCodigos(new Codigos("1024468","CONECTOR LOOKING",0));
        saveCodigos(new Codigos("1036544","CONECTOR DE CONTINUIDAD",0));
        saveCodigos(new Codigos("4025698","DECODIFICADOR DE CLARO",0));
        saveCodigos(new Codigos("1026200","ATENUADOR MEZCLADOR",0));
        saveCodigos(new Codigos("1025815","ACOPLADOR DIRECCIONAL",0));
        saveCodigos(new Codigos("10003282","ADAPTADOR - TORQUE 7/16",0));
        saveCodigos(new Codigos("1000328","ADAPTADOR - TORQUE 5/16",0));
        saveCodigos(new Codigos("10003286","ADAPTADOR - TORQUE 7/13",0));
        saveCodigos(new Codigos("10003232","ADAPTADOR TORQUE 7/16",0));
        saveCodigos(new Codigos("100032","ADAPTADOR TORQUE 16",0));
        saveCodigos(new Codigos("103282","ADAPTADOR - TORQUE 6/16",0));
        saveCodigos(new Codigos("1000234","MOSQUETON",0));
        saveCodigos(new Codigos("1000235","MOSQUETON",0));
        saveCodigos(new Codigos("1000236","MOSQUETON",0));
        saveCodigos(new Codigos("100023447","MOSQUETON",0));
        saveCodigos(new Codigos("10002377","MOSQUETON",0));
        saveCodigos(new Codigos("100023774","MOSQUETON",0));
    }

    private void saveCodigos(Codigos codes) {
        codis.put(codes.getmCod(),codes);
    }

    public List<Codigos> getCodigos() {
        return new ArrayList<>(codis.values());
    }

}

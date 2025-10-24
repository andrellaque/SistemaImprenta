package pe.edu.utp.sistemaimprenta.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.List;
import pe.edu.utp.sistemaimprenta.dao.AuditDao;
import pe.edu.utp.sistemaimprenta.model.Audit;
import pe.edu.utp.sistemaimprenta.util.FxmlPath;

public class AuditController {

    @FXML private VBox contenedorAuditoria;

    public void initialize() {
        cargarAuditorias();
    }

    private void cargarAuditorias() {
        AuditDao dao = new AuditDao();
        List<Audit> lista = dao.listar(); 

        contenedorAuditoria.getChildren().clear();
        //if (lista!=null){
        for (Audit item : lista) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FxmlPath.AUDIT_ITEM.getPath()));
                HBox itemBox = loader.load();

                AuditItemController controller = loader.getController();
                controller.setData(item);

                contenedorAuditoria.getChildren().add(itemBox);
            } catch (Exception e) {
                e.printStackTrace();
            }
       // }
        }
    }
}

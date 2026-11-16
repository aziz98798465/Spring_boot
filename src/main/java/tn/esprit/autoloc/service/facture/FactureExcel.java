package tn.esprit.autoloc.service.facture;

public class FactureExcel implements Facture {

    @Override
    public byte[] generer() {
        return "Facture Excel".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}

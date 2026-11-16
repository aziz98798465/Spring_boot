package tn.esprit.autoloc.service.facture;

public class FacturePdf implements Facture {

    @Override
    public byte[] generer() {
        return "Facture PDF".getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}

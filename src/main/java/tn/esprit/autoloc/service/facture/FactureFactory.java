package tn.esprit.autoloc.service.facture;

public class FactureFactory {

    public Facture creer(String format) {
        if (format == null) {
            throw new IllegalArgumentException("Le format est obligatoire");
        }

        return switch (format.toLowerCase()) {
            case "pdf" -> new FacturePdf();
            case "excel" -> new FactureExcel();
            default -> throw new IllegalArgumentException(
                    "Format de facture inconnu : " + format);
        };
    }
}

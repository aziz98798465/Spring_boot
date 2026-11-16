
package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50,
            message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50,
            message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    @Pattern(regexp = "\\d{8}",
            message = "Le téléphone doit contenir exactement 8 chiffres")
    private String telephone;

    @NotBlank(message = "Le numéro de permis est obligatoire")
    private String numPermis;

    @NotNull(message = "La date d'inscription est obligatoire")
    private LocalDate dateInscription;

    @JsonIgnore
    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    private List<Reservation> reservations = new ArrayList<>();
}

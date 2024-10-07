
package com.backend_java.demo;

import java.time.LocalDate;

public record TacheToSave(
    String titre,
    String description,
    LocalDate dateEcheance,
    String statut
) {}

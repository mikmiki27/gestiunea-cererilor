package com.example.gestiuneacererilor.data.restmanager.data

data class NewProfesorRequestBody(
    val profesor: Profesor
)

class Profesor {
    constructor(
        id: String,
        email: String,
        nume: String,
        prenume: String,
        cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?,
        nr_studenti_echipa_licenta: String?,
        nr_studenti_echipa_disertatie: String?,
        facultate: String
    )

    constructor(
        email: String,
        nume: String,
        prenume: String,
        facultate: String,
        cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?,
        nr_studenti_echipa_licenta: String?,
        nr_studenti_echipa_disertatie: String?
    )

    val id: String = ""
    val email: String = ""
    val nume: String = ""
    val prenume: String = ""
    val facultate: String = ""
    val cerinte_suplimentare_licenta: String? = ""
    val cerinte_suplimentare_disertatie: String? = ""
    val nr_studenti_echipa_licenta: String? = ""
    val nr_studenti_echipa_disertatie: String? = ""
}

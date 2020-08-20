package com.example.gestiuneacererilor.data.restmanager.data

class NewProfesorRequestBody {
    val id: String = ""
    val nume: String = ""
    val prenume: String = ""
    val email: String = ""
    var cerinte_suplimentare_licenta: String? = ""
    var cerinte_suplimentare_disertatie: String? = ""
    val facultate: String = ""
    var nr_studenti_echipa_licenta: String? = ""
    var nr_studenti_echipa_disertatie: String? = ""
    var studenti_licenta_acceptati: String? = ""
    var studenti_disertatie_acceptati: String? = ""

    constructor(
        nume: String,
        prenume: String,
        email: String,
        cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?,
        facultate: String,
        nr_studenti_echipa_licenta: String?,
        nr_studenti_echipa_disertatie: String?,
        studenti_licenta_acceptati: String?,
        studenti_disertatie_acceptati: String?
    )

    constructor(
        id: String,
        nume: String,
        prenume: String,
        email: String,
        cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?,
        facultate: String,
        nr_studenti_echipa_licenta: String?,
        nr_studenti_echipa_disertatie: String?,
        studenti_licenta_acceptati: String?,
        studenti_disertatie_acceptati: String?
    )
}

data class Profesor(
    val nume: String,
    val prenume: String,
    val email: String,
    var cerinte_suplimentare_licenta: String?,
    var cerinte_suplimentare_disertatie: String?,
    val facultate: String,
    var nr_studenti_echipa_licenta: String?,
    var nr_studenti_echipa_disertatie: String?,
    var studenti_licenta_acceptati: String?,
    var studenti_disertatie_acceptati: String?
) {
    constructor(
        id: String,
        nume: String,
        prenume: String,
        email: String,
        cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?,
        facultate: String,
        nr_studenti_echipa_licenta: String?,
        nr_studenti_echipa_disertatie: String?,
        studenti_licenta_acceptati: String?,
        studenti_disertatie_acceptati: String?
    )
            : this(
        nume,
        prenume,
        email,
        cerinte_suplimentare_licenta,
        cerinte_suplimentare_disertatie,
        facultate,
        nr_studenti_echipa_licenta,
        nr_studenti_echipa_disertatie,
        studenti_licenta_acceptati,
        studenti_disertatie_acceptati
    )
}
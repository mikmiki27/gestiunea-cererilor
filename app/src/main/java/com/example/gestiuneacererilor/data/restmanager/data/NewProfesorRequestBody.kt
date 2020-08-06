package com.example.gestiuneacererilor.data.restmanager.data

data class NewProfesorRequestBody(
    val email: String,
    val nume: String,
    val prenume: String,
    val facultate: String,
    val cerinte_suplimentare_licenta: String?,
    val cerinte_suplimentare_disertatie: String?,
    val nr_studenti_echipa_licenta: String?,
    val nr_studenti_echipa_disertatie: String?
)

data class Profesor(
    val email: String,
    val nume: String,
    val prenume: String,
    val facultate: String,
    val cerinte_suplimentare_licenta: String?,
    val cerinte_suplimentare_disertatie: String?,
    val nr_studenti_echipa_licenta: String?,
    val nr_studenti_echipa_disertatie: String?) {
    constructor(id: String, email: String, nume: String, prenume: String, facultate: String, cerinte_suplimentare_licenta: String?,
        cerinte_suplimentare_disertatie: String?, nr_studenti_echipa_licenta: String?, nr_studenti_echipa_disertatie: String?)
            : this(email, nume, prenume, facultate, cerinte_suplimentare_licenta, cerinte_suplimentare_disertatie,
        nr_studenti_echipa_licenta, nr_studenti_echipa_disertatie)
}
package com.example.gestiuneacererilor.data.restmanager.data

import java.io.Serializable

class Professor : Serializable {
    var id: String = ""
    var nume: String = ""
    var prenume: String = ""
    var email: String = ""
    var cerinte_suplimentare_licenta: String? = ""
    var cerinte_suplimentare_disertatie: String? = ""
    var facultate: String = ""
    var nr_studenti_echipa_licenta: String? = "0"
    var nr_studenti_echipa_disertatie: String? = "0"
    var studenti_licenta_acceptati: String? = ""
    var studenti_disertatie_acceptati: String? = ""

    constructor(nume: String, prenume: String, email: String, cerinte_suplimentare_licenta: String?, cerinte_suplimentare_disertatie: String?, facultate: String, nr_studenti_echipa_licenta: String?, nr_studenti_echipa_disertatie: String?, studenti_licenta_acceptati: String?, studenti_disertatie_acceptati: String?) {
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.cerinte_suplimentare_licenta = cerinte_suplimentare_licenta
        this.cerinte_suplimentare_disertatie = cerinte_suplimentare_disertatie
        this.facultate = facultate
        this.nr_studenti_echipa_licenta = nr_studenti_echipa_licenta
        this.nr_studenti_echipa_disertatie = nr_studenti_echipa_disertatie
        this.studenti_licenta_acceptati = studenti_licenta_acceptati
        this.studenti_disertatie_acceptati = studenti_disertatie_acceptati
    }

    constructor(nume: String, prenume: String, email: String, facultate: String) {
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.facultate = facultate
    }

    constructor(id: String, nume: String, prenume: String, email: String, cerinte_suplimentare_licenta: String?, cerinte_suplimentare_disertatie: String?, facultate: String, nr_studenti_echipa_licenta: String?, nr_studenti_echipa_disertatie: String?, studenti_licenta_acceptati: String?, studenti_disertatie_acceptati: String?) {
        this.id = id
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.cerinte_suplimentare_licenta = cerinte_suplimentare_licenta
        this.cerinte_suplimentare_disertatie = cerinte_suplimentare_disertatie
        this.facultate = facultate
        this.nr_studenti_echipa_licenta = nr_studenti_echipa_licenta
        this.nr_studenti_echipa_disertatie = nr_studenti_echipa_disertatie
        this.studenti_licenta_acceptati = studenti_licenta_acceptati
        this.studenti_disertatie_acceptati = studenti_disertatie_acceptati
    }

    constructor()
}

/*
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
    ) : this(
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
}*/
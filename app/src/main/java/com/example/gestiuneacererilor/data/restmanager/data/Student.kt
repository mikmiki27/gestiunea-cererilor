package com.example.gestiuneacererilor.data.restmanager.data

class Student {
    var id: String = ""
    var nume: String = ""
    var prenume: String = ""
    var email: String = ""
    var facultate: String = ""
    var an: String = ""
    var ciclu: String = ""
    var profesor_coordonator: String? = ""
    var profesor_coordonator_full_name: String? = ""
    var id_profesor_coordonator: String? = ""
    var titlu_lucrare: String? = ""

    constructor(nume: String, prenume: String, email: String, facultate: String, an: String, ciclu: String, profesor_coordonator: String?, profesor_coordonator_full_name: String?, id_profesor_coordonator: String?, titlu_lucrare: String?) {
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.facultate = facultate
        this.an = an
        this.ciclu = ciclu
        this.profesor_coordonator = profesor_coordonator
        this.profesor_coordonator_full_name = profesor_coordonator_full_name
        this.id_profesor_coordonator = id_profesor_coordonator
        this.titlu_lucrare = titlu_lucrare
    }

    constructor(id: String, nume: String, prenume: String, email: String, facultate: String, an: String, ciclu: String, profesor_coordonator: String?, profesor_coordonator_full_name: String?, id_profesor_coordonator: String?, titlu_lucrare: String?) {
        this.id = id
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.facultate = facultate
        this.an = an
        this.ciclu = ciclu
        this.profesor_coordonator = profesor_coordonator
        this.profesor_coordonator_full_name = profesor_coordonator_full_name
        this.id_profesor_coordonator = id_profesor_coordonator
        this.titlu_lucrare = titlu_lucrare
    }

    constructor(nume: String, prenume: String, email: String, facultate: String, an: String, ciclu: String) {
        this.nume = nume
        this.prenume = prenume
        this.email = email
        this.facultate = facultate
        this.an = an
        this.ciclu = ciclu
    }

    constructor()
}

/*
data class Student(
    val nume: String,
    val prenume: String,
    val email: String,
    val facultate: String,
    val an: String,
    val ciclu: String,
    var profesor_coordonator: String?,
    var titlu_lucrare: String?
) {
    constructor(
        id: String,
        nume: String,
        prenume: String,
        email: String,
        facultate: String,
        an: String,
        ciclu: String,
        profesor_coordonator: String?,
        titlu_lucrare: String?
    ) : this(nume, prenume, email, facultate, an, ciclu, profesor_coordonator, titlu_lucrare)
}*/
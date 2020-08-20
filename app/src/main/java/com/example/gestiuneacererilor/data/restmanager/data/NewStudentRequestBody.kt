package com.example.gestiuneacererilor.data.restmanager.data

class NewStudentRequestBody {
    val id: String = ""
    val nume: String = ""
    val prenume: String = ""
    val email: String = ""
    val facultate: String = ""
    val an: String = ""
    val ciclu: String = ""
    var profesor_coordonator: String? = ""
    var titlu_lucrare: String? = ""

    constructor(
        nume: String,
        prenume: String,
        email: String,
        facultate: String,
        an: String,
        ciclu: String,
        profesor_coordonator: String?,
        titlu_lucrare: String?
    )

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
    )
}

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
}
package com.example.gestiuneacererilor.data.restmanager.data

class NewStudentRequestBody {
    val id: String = ""
    val email: String = ""
    val nume: String = ""
    val prenume: String = ""
    val profesor_coordonator: String? = ""
    val facultate: String = ""
    val an: String = ""
    val ciclu: String = ""
    val titlu_lucrare: String? = ""

    constructor(
        email: String,
        nume: String,
        prenume: String,
        profesor_coordonator: String?,
        facultate: String,
        an: String,
        ciclu: String,
        titlu_lucrare: String
    )

    constructor(
        id: String,
        email: String,
        nume: String,
        prenume: String,
        profesor_coordonator: String?,
        facultate: String,
        an: String,
        ciclu: String,
        titlu_lucrare: String
    )
}

data class Student(
    val email: String,
    val nume: String,
    val prenume: String,
    val profesor_coordonator: String?,
    val facultate: String,
    val an: String,
    val ciclu: String,
    val titlu_lucrare: String?
) {
    constructor(
        id: String,
        email: String,
        nume: String,
        prenume: String,
        profesor_coordonator: String?,
        facultate: String,
        an: String,
        ciclu: String,
        titlu_lucrare: String
    ) : this(email, nume, prenume, profesor_coordonator, facultate, an, ciclu, titlu_lucrare)
}

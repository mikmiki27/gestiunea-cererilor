package com.example.gestiuneacererilor.data.restmanager.data

data class Cerere(
    val id: String,
    val student_solicitant: String,
    val id_student: String,
    val email_student_solicitat: String,
    val facultate_student: String,
    val profesor_solicitat: String,
    val email_profesor_solicitat: String,
    val id_profesor: String,
    val status: String,
    val tip_cerere: String,
    val raspuns: String,
    val mentiuni: String
)/* {

    constructor (
        id: String,
        student_solicitant: String,
        id_student: String,
        email_student_solicitat: String,
        profesor_solicitat: String,
        email_profesor_solicitat: String,
        id_profesor: String,
        status: String,
        tip_cerere: String,
        raspuns: String,
        mentiuni: String
    ) : this(
        id,
        student_solicitant,
        id_student,
        email_student_solicitat,
        profesor_solicitat,
        email_profesor_solicitat,
        id_profesor,
        status,
        tip_cerere,
        raspuns,
        mentiuni
    )
}*/
package com.example.gestiuneacererilor.data.restmanager.data

data class Cerere(
    val id: String,
    val student_solicitant: String,
    val id_student: String,
    val email_student_solicitat: String,
    val profesor_solicitat: String,
    val email_profesor_solicitat: String,
    val id_profesor: String,
    val status: String,
    val tip_cerere: String,
    val raspuns: String,
    val mentiuni: String
)
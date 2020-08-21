package com.example.gestiuneacererilor.data.restmanager.data

class Cerere {
    var id: String = ""
    var student_solicitant: String = ""
    var id_student: String = ""
    var email_student_solicitat: String = ""
    var facultate_student: String = ""
    var profesor_solicitat: String = ""
    var email_profesor_solicitat: String = ""
    var id_profesor: String = ""
    var status: String = ""
    var tip_cerere: String = ""
    var raspuns: String = ""
    var mentiuni: String = ""

    constructor (
        id: String,
        student_solicitant: String,
        id_student: String,
        email_student_solicitat: String,
        facultate_student: String,
        profesor_solicitat: String,
        email_profesor_solicitat: String,
        id_profesor: String,
        status: String,
        tip_cerere: String,
        raspuns: String,
        mentiuni: String
    ) {
        this.id = id
        this.student_solicitant = student_solicitant
        this.id_student = id_student
        this.email_student_solicitat = email_student_solicitat
        this.facultate_student = facultate_student
        this.profesor_solicitat = profesor_solicitat
        this.email_profesor_solicitat = email_profesor_solicitat
        this.id_profesor = id_profesor
        this.status = status
        this.tip_cerere = tip_cerere
        this.raspuns = raspuns
        this.mentiuni = mentiuni
    }

    constructor (
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
    )

}
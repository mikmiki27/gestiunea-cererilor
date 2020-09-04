package com.example.gestiuneacererilor.data.restmanager.data

class Sedinta {
    //todo de verificat
    var id: String = ""
    var student_solicitant: String = ""
    var id_student: String = ""
    var email_student_solicitat: String = ""
    var facultate_student: String = ""
    var an_student: String = ""
    var profesor_solicitat: String = ""
    var email_profesor_solicitat: String = ""
    var id_profesor: String = ""
    var status: String = ""
    var raspuns: String = ""
    var motiv: String = ""
    var data: String = ""
    var orai: String = ""
    var orasf: String = ""

    constructor (
        id: String,
        student_solicitant: String,
        id_student: String,
        email_student_solicitat: String,
        facultate_student: String,
        an_student: String,
        profesor_solicitat: String,
        email_profesor_solicitat: String,
        id_profesor: String,
        status: String,
        raspuns: String,
        motiv: String,
        data: String,
        orai: String,
        orasf: String
    ) {
        this.id = id
        this.student_solicitant = student_solicitant
        this.id_student = id_student
        this.email_student_solicitat = email_student_solicitat
        this.facultate_student = facultate_student
        this.an_student = an_student
        this.profesor_solicitat = profesor_solicitat
        this.email_profesor_solicitat = email_profesor_solicitat
        this.id_profesor = id_profesor
        this.status = status
        this.raspuns = raspuns
        this.motiv = motiv
        this.data = data
        this.orai = orai
        this.orasf = orasf
    }

    constructor (
        student_solicitant: String,
        id_student: String,
        email_student_solicitat: String,
        facultate_student: String,
        an_student: String,
        profesor_solicitat: String,
        email_profesor_solicitat: String,
        id_profesor: String,
        status: String,
        motiv: String,
        data: String,
        orai: String,
        orasf: String
    ) {
        this.student_solicitant = student_solicitant
        this.id_student = id_student
        this.email_student_solicitat = email_student_solicitat
        this.facultate_student = facultate_student
        this.an_student = an_student
        this.profesor_solicitat = profesor_solicitat
        this.email_profesor_solicitat = email_profesor_solicitat
        this.id_profesor = id_profesor
        this.status = status
        this.motiv = motiv
        this.data = data
        this.orai = orai
        this.orasf = orasf
    }

    constructor()
}
package com.example.dclassmanagement.data.model

data class MultiChoice(
    val id: String,
    val data: List<MultiChoiceData>

) {
    constructor() : this(
        "1",
        listOf(
            MultiChoiceData(
                1,
                "Android supports which features?",
                "MultiTasking",
                "Bluetooth",
                "Video Calling",
                "All of the above"
            ),
            MultiChoiceData(
                2,
                "Which company bought android?",
                "Apple",
                "Google",
                "Nokia",
                "No Company"
            ),
            MultiChoiceData(
                3,
                "Android doesn't support which format?",
                "MP4",
                "MPEG",
                "AVI",
                "MDPI"
            ),
            MultiChoiceData(
                4,
                "Which of the following file explains what the application consists of?",
                "string.xml",
                "R file",
                "Manifest",
                "Layout file"
            )
        )
    )
}

data class MultiChoiceData(
    val no: Long,
    val question: String,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
)

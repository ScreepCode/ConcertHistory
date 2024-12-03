package de.buseslaar.concerthistory.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun formatEventDate(eventDate: String, formatStyle: FormatStyle = FormatStyle.MEDIUM): String {
    val locale = Locale.getDefault()
    val inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", locale)
    val outputFormatter = DateTimeFormatter.ofLocalizedDate(formatStyle).withLocale(locale)
    val parsedDate = LocalDate.parse(eventDate, inputFormatter)
    return parsedDate.format(outputFormatter)
}
package com.openelements.jmh.store.util;

import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

public class Formatter {

  public static String format2Dec(final HttpServletRequest request, final Number number) {
    Objects.requireNonNull(request);
    Objects.requireNonNull(number);
    final Locale locale = getLocale(request);
    final NumberFormat numberFormat = NumberFormat.getInstance(locale);
    numberFormat.setMaximumFractionDigits(2);
    return numberFormat.format(number.doubleValue());
  }

  public static Locale getLocale(final HttpServletRequest request) {
    Objects.requireNonNull(request);
    return Optional.ofNullable(RequestContextUtils.getLocale(request))
        .orElse(Locale.getDefault());
  }

  public static TimeZone getTimeZone(final HttpServletRequest request) {
    Objects.requireNonNull(request);
    return Optional.ofNullable(RequestContextUtils.getTimeZone(request))
        .orElse(TimeZone.getDefault());
  }

  public static String createShortReadableDateTimePattern(final HttpServletRequest request) {
    Objects.requireNonNull(request);

    final Locale locale = getLocale(request);
    final Chronology chronology = Chronology.ofLocale(locale);

    return DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.SHORT,
        FormatStyle.SHORT, chronology,
        locale);
  }

  public static String toShortReadableForm(final HttpServletRequest request,
      final Instant instant) {
    Objects.requireNonNull(request);
    Objects.requireNonNull(instant);

    final Locale locale = getLocale(request);
    final TimeZone timeZone = getTimeZone(request);
    final ZoneId zoneId = Optional.ofNullable(timeZone.toZoneId())
        .orElse(ZoneId.systemDefault());

    final String pattern = createShortReadableDateTimePattern(request);
    return DateTimeFormatter.ofPattern(pattern).withLocale(locale)
        .withZone(zoneId)
        .format(instant);
  }

  private static final String[] BYTE_UNITS_SUFFIX = {"", "k", "Mb",
      "Gb", "Tb", "Pb"};

  public static String humanReadableBytes(final long bytes) {

    // Record and strip the sign
    final int signum = Long.signum(bytes);
    final long posBytes = bytes * signum;

    // Calculate the power, for either base 1000 or 1024
    final double byteFrac;
    final String unit;
    long order = 0;

    final String[] units = BYTE_UNITS_SUFFIX;
    while (order < units.length
        && posBytes >= 1L << 10L * (order + 1L)) {
      order++;
    }
    byteFrac = posBytes / (double) (1L << (10L * order));
    unit = units[(int) order];

    return String.format(Math.floor(byteFrac) == byteFrac ? "%s%.0f%sB"
        : "%s%.1f%sB", signum == -1 ? "-" : "", byteFrac, unit);
  }


}

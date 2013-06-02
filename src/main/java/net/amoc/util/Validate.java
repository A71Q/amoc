package net.amoc.util;

import org.springframework.validation.BindingResult;

import java.util.Date;

/**
 * @author Atiqur Rahman
 * @since 27/11/2012 11:39 PM
 */
public class Validate {

    public static void requiredField(String data, String field, BindingResult result) {
        if (StringUtils.isEmpty(data)) {
            result.rejectValue(field, "error.required");
        }
    }

    public static void requiredField(Date data, String field, BindingResult result) {
        if (data == null) {
            result.rejectValue(field, "error.required");
        }
    }

    public static void requiredField(Integer data, String field, BindingResult result) {
        if (data == null) {
            result.rejectValue(field, "error.required");
        } else if (data == 0) {
            result.rejectValue(field, "error.invalidSelection");
        }
    }

    public static void dateFromToCompare(Date dateFrom, Date dateTo, BindingResult result, String msgKey) {
        if (dateFrom != null && dateTo != null && dateTo.before(dateFrom)) {
            if (StringUtils.isEmpty(msgKey)) {
                result.reject("error.dateFrom.dateTo");
            } else {
                result.reject(msgKey);
            }
        }
    }

    public static void maxLength(String data, String field, int length, BindingResult result, String msgKey) {

        if (StringUtils.isNotEmpty(data) && data.length() > length) {
            if (StringUtils.isEmpty(msgKey)) {
                result.rejectValue(field, "error.max.allowed.character", new Object[]{length}, "Max Length Exceed!");
            } else {
                result.rejectValue(field, msgKey, new Object[]{length}, "Max Length Exceed!");
            }
        }
    }

    public static void minLength(String data, String field, int length, BindingResult result, String msgKey) {

        if (StringUtils.isNotEmpty(data) && data.length() < length) {
            if (StringUtils.isEmpty(msgKey)) {
                result.rejectValue(field, "error.max.required.character", new Object[]{length}, "Min Length Required!");
            } else {
                result.rejectValue(field, msgKey, new Object[]{length}, "Min Length Required!");
            }
        }
    }
}

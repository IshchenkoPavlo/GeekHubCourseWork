package org.geekhub.pavlo;

public class DocsUtils {
    private final static String[] docTypes = {"Purchase", "Sales"};

    public static String docNameByTypeId(int docId) {
        return docTypes[docId - 1];
    }
}

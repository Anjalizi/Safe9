package com.safe9.bfi.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Database to store patient data
 */
@Database(version = PatientDatabase.VERSION)
public final class PatientDatabase {


    public static final int VERSION = 1;

    @Table(PatientColumns.class)
    public static final String PATIENTS = "patients";

    @Table(ChildColumns.class)
    public static final String CHILDREN = "children";

}
